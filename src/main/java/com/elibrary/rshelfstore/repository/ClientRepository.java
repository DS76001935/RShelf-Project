package com.elibrary.rshelfstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.elibrary.rshelfstore.model.Client;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	@Query(value = "SELECT c.* FROM Client c WHERE c.clientId = :clientId AND c.isDelete = 0", nativeQuery = true)
	Client getById(@Param("clientId") Long id);

	@Query(value = "SELECT c.* FROM Client c WHERE c.firstName like %:keyword% AND c.isDelete = 0", nativeQuery = true)
	List<Client> findClientByName(@Param("keyword") String keyword);

	Client findClientByEmail(String email);
	
	Client findClientByClientId(Long clientId);
	
	Client findClientByUsername(String username);
}
