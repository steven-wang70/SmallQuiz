package com.challenge.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionOptionDTO {

	private Long id;

	private Long questionId;

	private String option;

	private boolean isCorrectOption;

	private Date createdAt;

	private Date updatedAt;

	public static QuestionOption transform(final QuestionOptionDTO newQADto, final Question question) {
		final QuestionOption newQa = new QuestionOption();
		newQa.setOption(newQADto.getOption());
		newQa.setIsCorrectOption(newQADto.getIsCorrectOption());
		newQa.setQuestion(question);

		return newQa;
	}

	public static QuestionOptionDTO build(final QuestionOption save) {
		final QuestionOptionDTO newQaDto = new QuestionOptionDTO();

		newQaDto.setId(save.getId());
		newQaDto.setOption(save.getOption());
		newQaDto.setIsCorrectOption(save.isCorrectOption());
		newQaDto.setCreatedAt(save.getCreatedAt());
		newQaDto.setUpdatedAt(save.getUpdatedAt());
		newQaDto.setQuestionId(save.getQuestion().getQuestionId());

		return newQaDto;
	}

	public static List<QuestionOptionDTO> build(final List<QuestionOption> options) {
		final List<QuestionOptionDTO> ret = new ArrayList<>();
		for (QuestionOption qa : options) {
			ret.add(build(qa));
		}
		return ret;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(final Long questionId) {
		this.questionId = questionId;
	}

	public String getOption() {
		return option;
	}

	public void setOption(final String option) {
		this.option = option;
	}

	public boolean getIsCorrectOption() {
		return isCorrectOption;
	}

	public void setIsCorrectOption(final boolean correctOption) {
		isCorrectOption = correctOption;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(final Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(final Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
