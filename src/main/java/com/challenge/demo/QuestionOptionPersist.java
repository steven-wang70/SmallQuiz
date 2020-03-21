package com.challenge.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "question_option")
@EntityListeners(AuditingEntityListener.class)
public class QuestionOptionPersist extends QuestionOptionBase {

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id", referencedColumnName = "question_id")
	private QuestionPersist question;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

	public QuestionOptionPersist() {
	}

	public QuestionPersist getQuestion() {
		return question;
	}

	@Override
	public Long getQuestionId() {
		return question.getQuestionId();
	}
	
	public void setQuestion(final QuestionPersist question) {
		this.question = question;
	}

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public Date getCreatedAt() {
		return createdAt;
	}

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public Date getUpdatedAt() {
		return updatedAt;
	}
}
