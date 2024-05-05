package com.fastcampus.nextplaybackservice.domain.repository;

import com.fastcampus.nextplaybackservice.domain.entity.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventLogRepository extends JpaRepository<EventLog, String> {
}