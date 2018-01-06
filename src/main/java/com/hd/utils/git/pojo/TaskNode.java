package com.hd.utils.git.pojo;

import com.hd.utils.git.responsetype.TaskVersion;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**任务节点类
 * @author jihang
 * @date 2017/12/15
 */
/*
public class TaskNode extends AbstractNode {

    private Set<Long> dataChildren;
    private Set<Long> branchChildren;
    //private LinkedList<TaskVersion> versionList;

    public TaskNode(Long nodeId, String nodeName,
                    String nodeRepoPath, String nodeUser,
                    Long nodeParentId) {
        id = nodeId;
        name = nodeName;
        type = NodeType.TASK.getType();
        version = 1;
        repoPath = nodeRepoPath;
        user = nodeUser;
        refs = new LinkedList<>();
        parentId = nodeParentId;
        dataChildren = new HashSet<>();
        branchChildren = new HashSet<>();
        //versionList = new LinkedList<>();
    }

    public Set<Long> getDataChildren() {
        return dataChildren;
    }

    public void addDataChild(Long dataId) {
        dataChildren.add(dataId);
    }

    public void addBranchChild(Long branchId) {
        dataChildren.add(branchId);
    }

    public Set<Long> getBranchChildren() {
        return branchChildren;
    }

    //public LinkedList<TaskVersion> getVersionList() {
    //    return versionList;
    //}

    //public void addVersion(TaskVersion tv) {
    //    versionList.push(tv);
    //}
}
*/