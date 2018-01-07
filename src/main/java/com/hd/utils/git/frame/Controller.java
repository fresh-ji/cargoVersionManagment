package com.hd.utils.git.frame;

import com.hd.utils.git.business.Project;
import com.hd.utils.git.business.Task;
import com.hd.utils.git.common.ServerResponse;
import com.hd.utils.git.frame.dao.AffiliationRepository;
import com.hd.utils.git.frame.dao.NodeRepository;
import com.hd.utils.git.frame.dao.SnapshotRepository;
import com.hd.utils.git.pojo.GeneralNode;
import com.hd.utils.git.pojo.NodeSnapshot;
import com.hd.utils.git.pojo.NodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        Project pj = new Project();
        ServerResponse<String> sr = pj.addCargo(projectId, pj.projectPath);
        if(sr.isSuccess()) {
            String projectName = Long.toString(projectId);
            //写node信息
            GeneralNode node = new GeneralNode();
            node.setNodeId(projectId);
            node.setName(projectName);
            node.setType(NodeType.PROJECT.getType());
            node.setRepoPath(pj.projectPath + projectName);
            node.setUser(userName);
            this.nodeRepository.save(node);
            //写版本信息
            NodeSnapshot ns = new NodeSnapshot();
            ns.setNodeId(projectId);
            ns.setEdition(1);
            ns.setVersionName(sr.getData());
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
        Task ta = new Task();
        GeneralNode project = this.nodeRepository.findOne(projectId);
        if(project == null) {
            return ServerResponse.createByErrorMessage("project not found!");
        }
        //这里的cargo是root端
        /*
        ServerResponse<String> sr = ta.addCargo(taskId,
                project.getRepoPath() + "/");
        if(sr.isSuccess()) {
            String taskName = Long.toString(taskId);
            //写node信息
            GeneralNode node = new GeneralNode();
            node.setNodeId(taskId);
            node.setName(taskName);
            node.setType(NodeType.TASK.getType());
            //这里的repo是master端
            node.setRepoPath(ta.taskPath + taskName);
            node.setUser(userName);
            this.nodeRepository.save(node);
            //写从属信息
            Affiliation aff = new Affiliation();
            aff.setParentId(projectId);
            aff.setChildId(taskId);
            aff.setParentType(NodeType.PROJECT.getType());
            aff.setChildType(NodeType.TASK.getType());
            this.affiliationRepository.save(aff);
            //写版本信息
            NodeSnapshot ns = new NodeSnapshot();
            ns.setNodeId(taskId);
            ns.setEdition(1);
            ns.setVersionName(sr.getData());
            this.snapshotRepository.save(ns);
        }
        */
        return ServerResponse.createBySuccess();
    }
}
