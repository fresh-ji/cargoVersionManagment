package com.hd.utils.git.pojo;

import org.eclipse.jgit.lib.Ref;

import javax.persistence.Column;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 项目节点类
 * @author jihang
 * @date 2017/12/15
 */
/*
public class ProjectNode extends AbstractNode {

    private Set<Long> children;
    @Column
    private byte[] childrenBytes;

    public ProjectNode(Long nodeId, String nodeName,
                       String nodeRepoPath, String nodeUser,
                       Long nodeParentId) {
        id = nodeId;
        name = nodeName;
        type = NodeType.PROJECT.getType();
        version = 1;
        repoPath = nodeRepoPath;
        user = nodeUser;
        refs = new LinkedList<>();
        parentId = nodeParentId;
        children = new HashSet<>();
    }

    public Set<Long> getChildren() {
        return children;
    }

    public void addChild(Long taskId) {
        children.add(taskId);
    }

    void transFromChildren() throws Throwable {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(refs);
        childrenBytes = baos.toByteArray();
    }

    void transToChildren() throws Throwable {
        ByteArrayInputStream bais = new ByteArrayInputStream(childrenBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        children = (Set<Long>)ois.readObject();
    }
}
*/