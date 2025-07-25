package com.emresahin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.emresahin.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long>{
	
	Page<File> findAll(Pageable pageable);
	
	@Query(value = "from File", nativeQuery = false)
	List<File> findAllFiles();
	
	@Query("FROM File s WHERE s.id = :FileID")
	Optional<File> findFileById(@Param("FileID") Long FileID);



}
