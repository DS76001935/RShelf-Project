package com.elibrary.rshelfstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elibrary.rshelfstore.model.Client;
import com.elibrary.rshelfstore.model.VerificationToken;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findVerificationTokenByToken(String token);
    List<VerificationToken> findAllVerificationTokenByClient(Client client);
}
