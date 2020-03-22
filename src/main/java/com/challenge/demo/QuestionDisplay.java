package com.challenge.demo;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Use this entity to track the last question displayed.
 * When a new question is requested, we always return the next question,
 * and update this entity.
 * If there is no entry for a reader/site yet, we create a new entry for
 * this entity, and use the first one in the order of question id as the
 * current one.
 * @author steve
 *
 */
@Entity
@Table(name = "question_display")
@EntityListeners(AuditingEntityListener.class)
public class QuestionDisplay {

	QuestionDisplay(SitePersist site, UUID readerUUID) {
		this.siteId = site.getSiteId();
		this.readerUUID = readerUUID;
	}
	
	public QuestionDisplay() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, name = "reader_uuid")
	private UUID readerUUID;

	@JoinColumn(name = "site_id", table = "site", referencedColumnName = "site_id")
	private Long siteId;

	@JoinColumn(name = "question_id", table = "question", referencedColumnName = "question_id")
	private Long questionId;

	public Long getLastQuestion() {
		return questionId;
	}
	
	public void setLastQuestion(Long lastQuestion) {
		this.questionId = lastQuestion;
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final QuestionDisplay display = (QuestionDisplay) o;
		return Objects.equals(id, display.id) &&
				Objects.equals(readerUUID, display.readerUUID) &&
				Objects.equals(siteId, display.siteId) &&
				Objects.equals(questionId, display.questionId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, readerUUID, siteId, questionId);
	}
}
