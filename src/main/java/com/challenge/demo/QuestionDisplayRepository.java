package com.challenge.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface QuestionDisplayRepository extends JpaRepository<QuestionDisplay, Long> {

	@Query(value = "SELECT qd.* FROM question_display qd WHERE qd.site_id = ?1 and qd.reader_uuid = ?2", nativeQuery = true)
	QuestionDisplay findByUuid(Long siteId, UUID readerUUID);
}