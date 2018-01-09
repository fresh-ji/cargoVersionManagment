package com.hd.utils.git.frame.dao;

import com.hd.utils.git.pojo.NodeSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Dao NodeSnapshot
 * @author jihang
 * @date 2018/01/06
 */

@Repository
public interface SnapshotRepository extends JpaRepository<NodeSnapshot, Long> {

    /**
     * 找某节点的最新版本
     * @param nodeId 节点id
     * @return 节点
     */
    @Query("select MAX(edition) from NodeSnapshot o where o.nodeId=?1")
    NodeSnapshot findRecentByNodeId(Long nodeId);

}