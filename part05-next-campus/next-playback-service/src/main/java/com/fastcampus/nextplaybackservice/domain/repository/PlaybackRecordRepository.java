package com.fastcampus.nextplaybackservice.domain.repository;

import com.fastcampus.nextplaybackservice.domain.entity.PlaybackRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaybackRecordRepository extends JpaRepository<PlaybackRecord, Long> {
}