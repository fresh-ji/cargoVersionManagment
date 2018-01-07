package com.hd.utils.git.frame.dao;

import com.hd.utils.git.pojo.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Affiliation
 * @author jihang
 * @date 2018/01/06
 */

@Repository
public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {

}
