package com.challenge.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is specially used for returning questions to display, which contains the whole options.
 * @author steve
 *
 */
public class QuestionDTOforDisplay extends QuestionBase {
	private Long siteId;

	private int questionType;

	private List<QuestionOptionDTOforDisplay> options = null;
	
	public static QuestionDTOforDisplay build(QuestionPersist question) {
		final QuestionDTOforDisplay obj = new QuestionDTOforDisplay();
		obj.setSiteId(question.getSite().getSiteId());
		obj.setQuestionId(question.getQuestionId());
		obj.setQuestionType(question.getQuestionType().getQuestionCode());
		obj.setDimInfo(question.getDimInfo());
		obj.setQuestion(question.getQuestion());
		obj.setOptions(question.getOptions());

		return obj;
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

	public List<QuestionOptionDTOforDisplay> getOptions() {
		return options;
	}
	
	public void setOptions(List<QuestionOptionPersist> options) {
		if (options == null) {
			this.options = null;
			return;
		}
		
		this.options = new ArrayList<QuestionOptionDTOforDisplay>();
		for (QuestionOptionPersist qo : options) {
			this.options.add(QuestionOptionDTOforDisplay.build(qo));
		}
	}
}
