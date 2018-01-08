package com.hd.utils.git.frame.dao;

import com.hd.utils.git.pojo.PushInform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Dao PushInform
 * @author jihang
 * @date 2018/01/08
 */

@Repository
public interface PushRepository extends JpaRepository<PushInform, Long> {
}
