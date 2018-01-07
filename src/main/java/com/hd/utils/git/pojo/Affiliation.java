package com.hd.utils.git.pojo;

import javax.persistence.*;

/**
 * 节点从属关系类
 * @author jihang
 * @date 2018/01/06
 */

@Entity
public class Affiliation {

    /**数据库id，作为唯一识别*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**主节点id*/
    @Column(name = "parent_id")
    private Long parentId;
    /**从节点id*/
    @Column(name = "child_id")
    private Long childId;
    /**主节点类型*/
    @Column(name = "parent_type")
    private Integer parentType;
    /**从节点类型*/
    @Column(name = "child_type")
    private Integer childType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public Integer getParentType() {
        return parentType;
    }

    public void setParentType(Integer parentType) {
        this.parentType = parentType;
    }

    public Integer getChildType() {
        return childType;
    }

    public void setChildType(Integer childType) {
        this.childType = childType;
    }
}