package com.hd.utils.git.pojo;

import javax.persistence.*;

/**
 * 通用节点类
 * @author jihang
 * @date 2017/11/20
 */

@Entity
public class GeneralNode {

    /**数据库id，作为唯一识别*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**节点id*/
    @Column(name = "node_id")
    private Long nodeId;
    /**节点名*/
    @Column(name = "name")
    private String name;
    /**节点类型*/
    @Column(name = "type")
    private Integer type;
    /**结点版本数*/
    @Column(name = "version")
    private Integer version;
    /**节点对应的库目录路径，为带名字的全路径*/
    @Column(name = "repo_path")
    private String repoPath;
    /**节点负责人*/
    @Column(name = "user")
    private String user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getRepoPath() {
        return repoPath;
    }

    public void setRepoPath(String repoPath) {
        this.repoPath = repoPath;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}