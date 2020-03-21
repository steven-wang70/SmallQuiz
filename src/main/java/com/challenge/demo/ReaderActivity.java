package com.challenge.demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Use this class to keep readers' responses to questions.
 * We use the word "reader" instead of "user" to avoid confusion between different
 * users. In such scenario, there are two types of user. One is the content provider,
 * another one is content consumer. They are both users to this service.
 * @author steve
 *
 */
@Entity
@Table(name = "reader_activity")
@EntityListeners(AuditingEntityListener.class)
public class ReaderActivity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * This is the unique id of the option. It does not affect the order of display.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "activity_id")
	private Long id;

	@Column(nullable = false, name = "reader_uuid")
	private UUID readerUUID;

	@JoinColumn(name = "question_id", table = "question", referencedColumnName = "question_id")
	private Long questionId;

	// Ignore this field in persistence. We will manually persist it to another table.
	@Transient
	private List<Long> answers = new ArrayList<>();

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public UUID getReaderUUID() {
		return readerUUID;
	}
	
	public Long getQuestionId() {
		return questionId;
	}
	
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	
	public void setReaderUUID(UUID readerUUID) {
		this.readerUUID = readerUUID;
	}
	
	public List<Long> getAnswers() {
		return answers;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ReaderActivity activity = (ReaderActivity) o;
		return Objects.equals(id, activity.id) &&
				Objects.equals(readerUUID, activity.readerUUID) &&
				Objects.equals(answers, activity.answers);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, readerUUID, answers);
	}
}
