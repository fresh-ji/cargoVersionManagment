package com.hd.utils.git.pojo;

import javax.persistence.*;

/**
 * 节点快照类
 * @author jihang
 * @date 2018/01/06
 */

@Entity
public class NodeSnapshot {

    /**数据库id，作为唯一识别*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**节点id*/
    @Column(name = "node_id")
    private Long nodeId;
    /**本版本号*/
    @Column(name = "edition")
    private Integer edition;
    /**本版本名*/
    @Column(name = "version_name")
    private String versionName;

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

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
