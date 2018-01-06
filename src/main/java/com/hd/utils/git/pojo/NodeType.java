package com.hd.utils.git.pojo;

/**
 * 节点类型枚举
 * @author jihang
 * @date 2017/12/14
 */

public enum NodeType {

    /**
     * 根节点、项目节点、任务节点和数据节点
     */
    PROJECT(1),
    TASK(2),
    BRANCH(3),
    DATA(4);

    private final Integer type;

    NodeType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}