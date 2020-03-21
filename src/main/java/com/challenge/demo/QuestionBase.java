package com.challenge.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@MappedSuperclass
public abstract class QuestionBase {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "question_id")
	private Long questionId;
	
	@Column(name = "encoded_dim_info", nullable = true, columnDefinition = "varchar")
	@Convert(converter = StringListConverter.class)
	private List<String> dimInfo = new ArrayList<>();
	
	@NotBlank
	@Length(min = 0, max = 250)
	private String question;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Long getQuestionId() {
		return questionId;
	}
	
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public List<String> getDimInfo() {
		return dimInfo;
	}
	
	public void setDimInfo(List<String> dimInfo) {
		this.dimInfo = dimInfo;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final QuestionBase question1 = (QuestionBase) o;
		return Objects.equals(questionId, question1.questionId) &&
				Objects.equals(question, question1.question) &&
				Objects.equals(dimInfo, question1.dimInfo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(questionId, question, dimInfo);
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
