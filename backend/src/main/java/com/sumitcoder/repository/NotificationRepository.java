package com.sumitcoder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumitcoder.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {



}
