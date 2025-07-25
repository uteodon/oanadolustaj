package com.emresahin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emresahin.entity.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long>{

}
