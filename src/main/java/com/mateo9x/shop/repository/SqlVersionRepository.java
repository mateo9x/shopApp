package com.mateo9x.shop.repository;

import com.mateo9x.shop.domain.SqlVersion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlVersionRepository extends JpaRepository<SqlVersion, Long> {

    SqlVersion findFirstBy();

}
