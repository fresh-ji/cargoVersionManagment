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
    @Column(name = "project_id")
    private Long projectId;
    /**节点名*/
    @Column(name = "name")
    private String name;
    /**节点类型*/
    @Column(name = "type")
    private Integer type;
    /**节点版本*/
    @Column(name = "edition")
    private Integer edition;
    /**节点对应的库目录路径，为带名字的全路径*/
    @Column(name = "repo_path")
    private String repoPath;
    /**节点负责人*/
    @Column(name = "user")
    private String user;
    /**父节点id，子节点id序列在各子类内*/
    @Column(name = "parent_id")
    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}