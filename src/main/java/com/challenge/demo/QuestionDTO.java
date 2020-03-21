package com.challenge.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionDTO extends QuestionBase {
	private Long siteId;

	private int questionType;

	private Date createdAt;

	private Date updatedAt;
	
	public static QuestionDTO build(QuestionPersist question) {
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

	public static List<QuestionDTO> build(List<QuestionPersist> questions) {
		final List<QuestionDTO> ret = new ArrayList<>();

		for (QuestionPersist question : questions) {
			ret.add(build(question));
		}

		return ret;
	}

	public static QuestionPersist createQuestion(final QuestionDTO incomingQuestion, final Site site) {
		final QuestionPersist newQ = new QuestionPersist();
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
