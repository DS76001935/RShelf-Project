package com.elibrary.rshelfstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elibrary.rshelfstore.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
