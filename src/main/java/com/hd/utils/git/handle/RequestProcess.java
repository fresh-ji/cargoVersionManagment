package com.hd.utils.git.handle;

import com.hd.utils.git.pojo.*;
import com.hd.utils.git.business.Project;
//import com.hd.utils.git.business.Task;
import com.hd.utils.git.responsetype.*;
import com.hd.utils.git.common.ServerResponse;
import org.eclipse.jgit.lib.Ref;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求处理类
 * @author jihang
 * @date 2017/11/22
 */

public class RequestProcess {

    /**获取任务各分支最新更改->接口*/
    /*

    */

    //目前不支持项目的lookChange
    //public ServerResponse<String> lookProjectFile(String fileName, int commitHash, int projectId) throws Throwable {
    //    Project pj = new Project();
    //    TreeNode node = findNode(projectId);
    //    return pj.lookFile(fileName, commitHash,
    //
    //
    //
    // node.repoPath);
    //}

    /**获取任务某分支某文件更改结果->接口*/
    /*
    public static ServerResponse<ChangeInfo> checkTaskChange(String fileName, String branchName, Long taskId) throws Throwable {
        Task ta = new Task();
        TaskNode node = RootNode.getRoot().findTaskById(taskId);
        if(node == null) {
            return ServerResponse.createByErrorMessage(
                    "Unable to check task change, task id not found");
        }
        return ta.checkChange(fileName, branchName, node.getRepoPath());
    }
    */

    //目前不支持项目的merge
    //public ServerResponse<Integer> processProjectCommit(int commitHash, int projectId) throws Throwable {
    //    Project pj = new Project();
    //    TreeNode node = findNode(projectId);
    //    ServerResponse<Integer> sr = pj.mergeCargo(commitHash, node.repoPath);
    //    return sr;
    //}

    /**任务按照分支合并->接口*/
    /*
    public static ServerResponse mergeBranch(String branchName, Long taskId) throws Throwable {
        Task ta = new Task();
        TaskNode node = RootNode.getRoot().findTaskById(taskId);
        if(node == null) {
            return ServerResponse.createByErrorMessage(
                    "Unable to process commit, task id not found");
        }
        ServerResponse<MergeInfo> sr1 = ta.mergeCargoByBranch(branchName, node.getRepoPath());

        BranchNode bNode = RootNode.getRoot().findBranchByName(branchName);

        TaskVersion tv = new TaskVersion();
        Ref ref = sr1.getData().taskNewRef;
        node.setRef(ref);
        node.updateVersion();
        tv.user = bNode.getUser();
        tv.different = null;
        node.versionList.push(tv);
        bNode.setRef(sr1.getData().branchNewRef);
        bNode.updateVersion();
        return sr1;
    }
    */

    /**获取任务版本历史->接口*/
    /*
    public static ServerResponse<List<TaskVersion>> getTaskHistory(Long taskId) throws Throwable {
        TaskNode node = RootNode.getRoot().findTaskById(taskId);
        if(node == null) {
            return ServerResponse.createByErrorMessage(
                    "Unable to get task history, task id not found");
        }
        return ServerResponse.createBySuccess(node.versionList);
    }
    */

    //目前不支持任务向项目推送
    //Task向Project推送->接口，只有Task才有，和上一个都是merge过程，返回值应该是boolean
    //public void pushTask(int taskId) throws Throwable {
    //    Task ta = new Task();
    //    TreeNode node = findNode(taskId);
    //    ta.pushTask(node.repoPath, node.parent.repoPath + "/" + node.name);
    //}

}