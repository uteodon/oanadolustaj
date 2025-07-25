package com.emresahin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.emresahin.entity.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);
	
	@Query(value = "from User", nativeQuery = false)
	List<User> findAllUser();
	
	@Query("FROM User s WHERE s.id = :UserID")
	Optional<User> findUserById(@Param("UserID") Integer UserID);

}
