package com.challenge.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	@Query(value = "SELECT q.* FROM question q WHERE q.site_id = ?1 ORDER BY question_id", nativeQuery = true)
	List<Question> findSiteQuestions(Long siteId);


}