package com.challenge.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionOptionRepository extends JpaRepository<QuestionOptionPersist, Long> {
}
