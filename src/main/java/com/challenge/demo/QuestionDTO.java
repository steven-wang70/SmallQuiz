package com.challenge.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionDTO {

	private Long questionId;

	private Long siteId;

	private int questionType;
	
	private List<String> dimInfo;
	
	private String question;

	private Date createdAt;

	private Date updatedAt;
	
	public static QuestionDTO build(Question question) {
		final QuestionDTO obj = new QuestionDTO();
		obj.setSiteId(question.getSite().getSiteId());
		obj.setQuestionId(question.getQuestionId());
		obj.setQuestionType(question.getQuestionType().getQuestionCode());
		obj.setDimInfo(question.getDimInfo());
		obj.setQuestion(question.getQuestion());
		obj.setUpdatedAt(question.getUpdatedAt());
		obj.setCreatedAt(question.getCreatedAt());

		return obj;
	}

	public static List<QuestionDTO> build(List<Question> questions) {
		final List<QuestionDTO> ret = new ArrayList<>();

		for (Question question : questions) {
			ret.add(build(question));
		}

		return ret;
	}

	public static Question createQuestion(final QuestionDTO incomingQuestion, final Site site) {
		final Question newQ = new Question();
		newQ.setSite(site);
		newQ.setQuestion(incomingQuestion.getQuestion());
		newQ.setQuestionType(QuestionType.fromCode((incomingQuestion.getQuestionType())));
		newQ.setDimInfo(incomingQuestion.getDimInfo());

		return newQ;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(final Long siteId) {
		this.siteId = siteId;
	}

	public int getQuestionType() {
		return questionType;
	}
	
	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}
	
	public List<String> getDimInfo() {
		return dimInfo;
	}
	
	public void setDimInfo(List<String> dimInfo) {
		this.dimInfo = dimInfo;
	}
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(final String question) {
		this.question = question;
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

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(final Long questionId) {
		this.questionId = questionId;
	}
}
