package com.challenge.demo;

import java.io.Serializable;
import java.util.Objects;

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

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Use this entity to capture readers' answer to the online questions.
 * Each this entity hold an option of a question.
 * @author steve
 *
 */
@Entity
@Table(name = "question_answer")
@EntityListeners(AuditingEntityListener.class)
public class QuestionAnswer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "answer_id")
	private Long id;

	@JoinColumn(name = "activity_id", table = "reader_Activity", referencedColumnName = "activity_id")
	private Long readerActivity;

	@JoinColumn(name = "question_option_id", table = "reader_Activity", referencedColumnName = "question_option_id")
	private Long questionOption;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getReaderActivity() {
		return readerActivity;
	}
	
	public void setReaderActivity(Long readerActivity) {
		this.readerActivity = readerActivity;
	}
	
	public Long getQuestionOption() {
		return questionOption;
	}
	
	public void setQuestionOption(Long questionOption) {
		this.questionOption = questionOption;
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final QuestionAnswer answer = (QuestionAnswer) o;
		return Objects.equals(id, answer.id) &&
				Objects.equals(readerActivity, answer.readerActivity) &&
				Objects.equals(questionOption, answer.questionOption);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, readerActivity, questionOption);
	}
}
