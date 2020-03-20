package com.challenge.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "question")
@EntityListeners(AuditingEntityListener.class)
public class Question implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "question_id")
	private Long questionId;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "site_id", referencedColumnName = "site_id")
	private Site site;

	@Column(name = "question_type", nullable = false, columnDefinition = "TINYINT")
	private QuestionType questionType;
	
	@Column(name = "encoded_dim_info", nullable = true, columnDefinition = "varchar")
	@Convert(converter = StringListConverter.class)
	private List<String> dimInfo = new ArrayList<>();
	
	@NotBlank
	@Length(min = 0, max = 250)
	private String question;

	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
	private List<QuestionOption> options = new ArrayList<>();

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Long getQuestionId() {
		return questionId;
	}

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public Date getCreatedAt() {
		return createdAt;
	}

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public List<String> getDimInfo() {
		return dimInfo;
	}
	
	public void setDimInfo(List<String> dimInfo) {
		this.dimInfo = dimInfo;
	}
	
	public List<QuestionOption> getOptions() {
		return options;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Question question1 = (Question) o;
		return Objects.equals(questionId, question1.questionId) &&
				Objects.equals(site, question1.site) &&
				Objects.equals(question, question1.question) &&
				Objects.equals(dimInfo, question1.dimInfo) &&
				Objects.equals(options, question1.options) &&
				Objects.equals(createdAt, question1.createdAt) &&
				Objects.equals(updatedAt, question1.updatedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(questionId, site, question, dimInfo, options, createdAt, updatedAt);
	}
	
	/**
	 * This converter class will convert the string array to encoded \n separated, and 
	 * verse visa.
	 * Keep in mind the current implementation is not rigorous enough. If users use the \n in their
	 * title, it may break it. Need to fine tune later.
	 * @author steve
	 *
	 */
	@Converter
	public static class StringListConverter implements AttributeConverter<List<String>, String> {
		@Override
		public String convertToDatabaseColumn(List<String> attribute) {
			if (attribute == null  || attribute.size() == 0) return null;
			
	        return String.join("\n", attribute);
		}

	    @Override
	    public List<String> convertToEntityAttribute(String encoded) {
	    	if (encoded == null || encoded.equals("")) return null;
	    	
	    	return Arrays.asList(encoded.split("\n"));
	    }
	}
}
