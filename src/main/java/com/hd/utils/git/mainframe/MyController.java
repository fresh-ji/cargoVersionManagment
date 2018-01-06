package com.hd.utils.git.mainframe;

import com.hd.utils.git.business.Project;
import com.hd.utils.git.common.ServerResponse;
import com.hd.utils.git.pojo.GeneralNode;
import com.hd.utils.git.pojo.NodeType;
import org.eclipse.jgit.lib.Ref;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller
 * @author jihang
 * @date 2017/12/29
 */

@RestController
public class MyController {

    @Autowired
    private MyRepository myRepository;

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
        ServerResponse<Ref> sr = pj.addCargo(projectId, pj.projectPath);
        if(sr.isSuccess()) {
            String projectName = Long.toString(projectId);
            GeneralNode node = new GeneralNode();
            node.setProjectId(projectId);
            node.setName(projectName);
            node.setType(NodeType.PROJECT.getType());
            node.setEdition(1);
            node.setRepoPath(pj.projectPath + projectName);
            node.setUser(userName);
            node.setParentId(Integer.toUnsignedLong(0));
            this.myRepository.save(node);
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
    /*
    @GetMapping("/addTask/{TaskId}&{projectId}&{userName}")
    public ServerResponse addTask(@PathVariable Long taskId,
                                  @PathVariable Long projectId,
                                  @PathVariable String userName) throws Throwable {
        Task ta = new Task();

        ProjectNode parent = RootNode.getRoot().findProjectById(projectId);
        if(parent == null) {
            return ServerResponse.createByErrorMessage("project id not found!");
        }
        //这里的cargo是root端
        ServerResponse<Ref> sr = ta.addCargo(Long.toString(taskId),
                parent.getRepoPath() + "/");
        if(sr.isSuccess()) {
            TaskNode node = new TaskNode(taskName, taskId);
            //这里的repo是master端
            node.setRepoPath(ta.taskPath + Long.toString(taskId));
            node.parent = parent;
            parent.childList.push(node);

            Ref ref = sr.getData();
            node.setRef(ref);
            TaskVersion tv = new TaskVersion();
            tv.user = "Task Administrator";
            tv.mergeTime = 0;
            tv.different = null;
            node.versionList.push(tv);
        }

        return sr;
    }
    */
}
