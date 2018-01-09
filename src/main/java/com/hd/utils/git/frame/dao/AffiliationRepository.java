package com.hd.utils.git.frame.dao;

import com.hd.utils.git.pojo.Affiliation;
import com.hd.utils.git.pojo.GeneralNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Affiliation
 * @author jihang
 * @date 2018/01/06
 */

@Repository
public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {
    /**
     * 根据nodeId查找node
     * @param branchId 分支节点id
     * @return 节点们
     */
    @Query("select o from Affiliation o where o.childId=?1 AND o.childType=3")
    List<Affiliation> findAllByBranchId(Long branchId);

    @Query("select o from Affiliation o where o.parentId=?1 AND o.parentType=2")
    List<Affiliation> findAllByTaskId(Long parentId);
}