package com.hd.utils.git.mainframe;

import com.hd.utils.git.pojo.GeneralNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Dao
 * @author jihang
 * @date 2018/01/03
 */

@Repository
public interface MyRepository extends JpaRepository<GeneralNode, Long> {

}