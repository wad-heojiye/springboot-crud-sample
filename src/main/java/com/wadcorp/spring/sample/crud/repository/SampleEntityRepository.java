package com.wadcorp.spring.sample.crud.repository;

import com.wadcorp.spring.sample.crud.entity.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleEntityRepository extends JpaRepository<SampleEntity, Long> {

}
