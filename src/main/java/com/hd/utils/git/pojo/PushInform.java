package com.hd.utils.git.pojo;

import javax.persistence.*;

/**
 * 处理push类
 * @author jihang
 * @date 2018/01/08
 */

@Entity
public class PushInform {

    /**数据库id，作为唯一识别*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**节点名称*/
    @Column(name = "node_id")
    private Long nodeId;

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
}
