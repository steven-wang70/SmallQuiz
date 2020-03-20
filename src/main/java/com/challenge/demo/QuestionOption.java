package com.challenge.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "question_option")
@EntityListeners(AuditingEntityListener.class)
public class QuestionOption implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "question_option_id")
	private Long id;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id", referencedColumnName = "question_id")
	private Question question;

	private String option;

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean isCorrectOption;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

	public QuestionOption() {
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(final Question question) {
		this.question = question;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getOption() {
		return option;
	}

	public void setOption(final String Option) {
		this.option = Option;
	}

	public boolean isCorrectOption() {
		return isCorrectOption;
	}

	public void setIsCorrectOption(boolean isCorrectOption) {
		this.isCorrectOption = isCorrectOption;
	}

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public Date getCreatedAt() {
		return createdAt;
	}

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final QuestionOption that = (QuestionOption) o;
		return isCorrectOption == that.isCorrectOption &&
				Objects.equals(id, that.id) &&
				Objects.equals(question, that.question) &&
				Objects.equals(option, that.option) &&
				Objects.equals(createdAt, that.createdAt) &&
				Objects.equals(updatedAt, that.updatedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, question, option, isCorrectOption, createdAt, updatedAt);
	}
}
