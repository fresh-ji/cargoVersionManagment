package com.hd.utils.git.frame.dao;

import com.hd.utils.git.pojo.GeneralNode;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Dao GeneralNode
 * @author jihang
 * @date 2018/01/03
 */

@Repository
public interface NodeRepository extends JpaRepository<GeneralNode, Long> {

}