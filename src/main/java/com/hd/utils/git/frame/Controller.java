package com.hd.utils.git.frame;

import com.hd.utils.git.business.Project;
import com.hd.utils.git.business.Task;
import com.hd.utils.git.common.ServerResponse;
import com.hd.utils.git.frame.dao.AffiliationRepository;
import com.hd.utils.git.frame.dao.NodeRepository;
import com.hd.utils.git.frame.dao.SnapshotRepository;
import com.hd.utils.git.pojo.*;
import com.hd.utils.git.responsetype.ForkResult;
import com.hd.utils.git.responsetype.PushResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller
 * @author jihang
 * @date 2017/12/29
 */

@RestController
public class Controller {

    private final NodeRepository nodeRepository;
    private final SnapshotRepository snapshotRepository;
    private final AffiliationRepository affiliationRepository;

    @Autowired
    public Controller(NodeRepository nodeRepository,
                      SnapshotRepository snapshotRepository,
                      AffiliationRepository affiliationRepository) {
        this.nodeRepository = nodeRepository;
        this.snapshotRepository = snapshotRepository;
        this.affiliationRepository = affiliationRepository;
    }

    /**
     * 新建项目
     * @param projectId 任务id
     * @param userName 项目负责人
     * @return 创建结果
     * @throws Throwable 扔
     */
    @GetMapping("/addProject/{projectId}/{userName}")
    public ServerResponse addProject(@PathVariable Long projectId,
                                     @PathVariable String userName) throws Throwable {
        ServerResponse<String> sr = Project.addCargo(projectId, Project.PROJECT_PATH);
        if(sr.isSuccess()) {
            String projectName = Long.toString(projectId);
            //写node信息
            GeneralNode node = new GeneralNode();
            node.setNodeId(projectId);
            node.setName(projectName);
            node.setType(NodeType.PROJECT.getType());
            node.setVersion(1);
            node.setRepoPath(Project.PROJECT_PATH + projectName);
            node.setUser(userName);
            this.nodeRepository.save(node);
            //写版本信息
            NodeSnapshot ns = new NodeSnapshot();
            ns.setNodeId(projectId);
            ns.setEdition(1);
            ns.setVersionName(sr.getData());
            ns.setMergeEdition(1);
            this.snapshotRepository.save(ns);
        }
        return ServerResponse.createBySuccess();
    }

    /**
     * 新建任务
     * @param taskId 任务id
     * @param projectId 任务所属项目id
     * @param userName 任务负责人
     * @return 创建结果
     * @throws Throwable 扔
     */
    @GetMapping("/addTask/{taskId}/{projectId}/{userName}")
    public ServerResponse addTask(@PathVariable Long taskId,
                                  @PathVariable Long projectId,
                                  @PathVariable String userName) throws Throwable {
        List<GeneralNode> projects = this.nodeRepository.findAllByNodeId(projectId);
        if(projects.size() == 0) {
            return ServerResponse.createByErrorMessage("project not found!");
        }
        if(projects.size() != 1) {
            return ServerResponse.createByErrorMessage("project not unique!");
        }
        //这里的cargo是root端
        ServerResponse<String> sr = Task.addCargo(taskId,
                projects.get(0).getRepoPath() + "\\");
        if(sr.isSuccess()) {
            String taskName = Long.toString(taskId);
            //写node信息
            GeneralNode node = new GeneralNode();
            node.setNodeId(taskId);
            node.setName(taskName);
            node.setType(NodeType.TASK.getType());
            node.setVersion(1);
            //这里的repo是master端
            node.setRepoPath(Task.TASK_PATH + taskName);
            node.setUser(userName);
            this.nodeRepository.save(node);
            //写从属信息
            Affiliation aff = new Affiliation();
            aff.setParentId(projectId);
            aff.setChildId(taskId);
            aff.setParentType(NodeType.PROJECT.getType());
            aff.setChildType(NodeType.TASK.getType());
            aff.setWhetherPush(false);
            this.affiliationRepository.save(aff);
            //写版本信息
            NodeSnapshot ns = new NodeSnapshot();
            ns.setNodeId(taskId);
            ns.setEdition(1);
            ns.setVersionName(sr.getData());
            ns.setMergeEdition(1);
            this.snapshotRepository.save(ns);
        }
        return ServerResponse.createBySuccess();
    }

    /**
     * fork任务
     * @param userName 请求人
     * @param taskId 任务id
     * @return ForkResult 结果
     * @throws Throwable 扔
     */
    @GetMapping("/forkTask/{userName}/{taskId}")
    public ServerResponse<ForkResult> forkTask(@PathVariable String userName,
                                               @PathVariable Long taskId) throws Throwable {
        List<GeneralNode> tasks = this.nodeRepository.findAllByNodeId(taskId);
        if(tasks.size() == 0) {
            return ServerResponse.createByErrorMessage("task not found!");
        }
        if(tasks.size() != 1) {
            return ServerResponse.createByErrorMessage("task not unique!");
        }
        ServerResponse<ForkResult> sr = Task.forkCargo(userName,
                tasks.get(0).getRepoPath());
        if(sr.isSuccess()) {
            //写node信息
            GeneralNode node = new GeneralNode();
            node.setNodeId(sr.getData().branchId);
            node.setName(sr.getData().branchName);
            node.setType(NodeType.BRANCH.getType());
            node.setVersion(1);
            node.setRepoPath(sr.getData().repoUri);
            node.setUser(userName);
            this.nodeRepository.save(node);
            //写从属信息
            Affiliation aff = new Affiliation();
            aff.setParentId(taskId);
            aff.setChildId(sr.getData().branchId);
            aff.setParentType(NodeType.TASK.getType());
            aff.setChildType(NodeType.BRANCH.getType());
            aff.setWhetherPush(false);
            this.affiliationRepository.save(aff);
            //写版本信息
            NodeSnapshot ns = new NodeSnapshot();
            ns.setNodeId(sr.getData().branchId);
            ns.setEdition(1);
            ns.setVersionName(sr.getData().versionName);
            ns.setMergeEdition(tasks.get(0).getVersion());
            this.snapshotRepository.save(ns);
        }
        return sr;
    }

    /**
     * 通知新push请求
     * @param branchId 分支id
     * @return List<PushResult> 结果
     */
    @GetMapping("/pushInform/{branchId}")
    public ServerResponse pushInform(@PathVariable Long branchId) throws Throwable {
        List<GeneralNode> branches = this.nodeRepository.findAllByNodeId(branchId);
        if(branches.size() == 0) {
            return ServerResponse.createByErrorMessage("branch not found!");
        }
        if(branches.size() != 1) {
            return ServerResponse.createByErrorMessage("branch not unique!");
        }

        List<Affiliation> tasks = this.affiliationRepository.findAllByBranchId(branchId);
        if(tasks.size() == 0) {
            return ServerResponse.createByErrorMessage("task not found!");
        }
        if(tasks.size() != 1) {
            return ServerResponse.createByErrorMessage("task not unique!");
        }

        ServerResponse<String> sr = Task.pushInform(branches.get(0).getName(),
                branches.get(0).getRepoPath());

        //修改node信息
        branches.get(0).setVersion(branches.get(0).getVersion() + 1);
        //修改push位状态
        Affiliation aff = tasks.get(0);
        aff.setWhetherPush(true);
        //修改snapshot
        NodeSnapshot ns = new NodeSnapshot();
        ns.setNodeId(branchId);
        ns.setEdition(branches.get(0).getVersion());
        ns.setVersionName(sr.getData());
        ns.setMergeEdition(0);
        this.snapshotRepository.save(ns);
        return ServerResponse.createBySuccess();
    }

    /**
     * 查看任务待合并列表
     * @param userName 请求人
     * @param taskId 任务id
     * @return List<PushResult> 结果
     */
    @GetMapping("/taskPushInfo/{userName}/{taskId}")
    public ServerResponse<List<PushResult>> taskPushInfo(@PathVariable String userName,
                                                         @PathVariable Long taskId) throws Throwable {
        List<GeneralNode> tasks = this.nodeRepository.findAllByNodeId(taskId);
        if(tasks.size() == 0) {
            return ServerResponse.createByErrorMessage("task not found!");
        }
        if(tasks.size() != 1) {
            return ServerResponse.createByErrorMessage("task not unique!");
        }
        if(!tasks.get(0).getUser().equals(userName)) {
            return ServerResponse.createByErrorMessage("user wrong!");
        }
        List<String> branchList = new ArrayList<>();
        for(Affiliation aff: this.affiliationRepository.findAllByTaskId(taskId)) {
            if(aff.getWhetherPush()) {
                List<GeneralNode> gn = this.nodeRepository.findAllByNodeId(aff.getChildId());
                branchList.add(gn.get(0).getName());
            }
        }
        return Task.taskPushInfo(branchList, tasks.get(0).getRepoPath());
    }
}