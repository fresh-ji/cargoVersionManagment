package com.hd.utils.git.frame.dao;

import com.hd.utils.git.pojo.GeneralNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Dao GeneralNode
 * @author jihang
 * @date 2018/01/03
 */

@Repository
public interface NodeRepository extends JpaRepository<GeneralNode, Long> {

    /**
     * 根据nodeId查找node
     * @param nodeId 节点id
     * @return 节点们
     */
    @Query("select o from GeneralNode o where o.nodeId=?1")
    List<GeneralNode> findAllByNodeId(Long nodeId);

    /**
     * 根据nodeName查找node
     * @param nodeName 节点名
     * @return 节点们
     */
    @Query("select o from GeneralNode o where o.name=?1")
    List<GeneralNode> findAllByNodeName(String nodeName);

}