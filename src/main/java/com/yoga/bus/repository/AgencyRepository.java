package com.yoga.bus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yoga.bus.models.Agency;
import com.yoga.bus.models.User;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {

	Agency findByOwner(User owner);

	@Query(value = "SELECT * FROM agency WHERE owner_user_id = :ownerId", nativeQuery = true)
	List<Agency> findByOwnerUserId(Long ownerId);
}