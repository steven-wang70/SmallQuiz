package com.challenge.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class QuestionOptionDTO {

	private Long id;

	private Long questionId;

	private String option;
	
	private Byte optionIndex;
	
	private Byte optionDim;

	private boolean isCorrectOption;

	private Date createdAt;

	private Date updatedAt;

	public static QuestionOption transform(final QuestionOptionDTO newQADto, final QuestionPersist question) {
		final QuestionOption newQa = new QuestionOption();
		newQa.setOption(newQADto.getOption());
		newQa.setOptionIndex(newQADto.getOptionIndex());
		newQa.setOptionDim(newQADto.getOptionDim());
		newQa.setIsCorrectOption(newQADto.getIsCorrectOption());
		newQa.setQuestion(question);
		
		return newQa;
	}

	public static QuestionOptionDTO build(final QuestionOption save) {
		final QuestionOptionDTO newQaDto = new QuestionOptionDTO();

		newQaDto.setId(save.getId());
		newQaDto.setOption(save.getOption());
		newQaDto.setOptionIndex(save.getOptionIndex());
		newQaDto.setOptionDim(save.getOptionDim());
		newQaDto.setIsCorrectOption(save.isCorrectOption());
		newQaDto.setCreatedAt(save.getCreatedAt());
		newQaDto.setUpdatedAt(save.getUpdatedAt());
		newQaDto.setQuestionId(save.getQuestion().getQuestionId());

		return newQaDto;
	}

	/**
	 * Helper class to sort the QuestionOptionDTO object first by optionDim, then by optionIndex.
	 * @author steve
	 *
	 */
	private static class CompareQuestionOption implements Comparator<QuestionOptionDTO>
	{
	    public int compare(QuestionOptionDTO a, QuestionOptionDTO b)
	    {
	    	byte aDim = a.optionDim == null ? 0 : a.optionDim.byteValue();
	    	byte bDim = b.optionDim == null ? 0 : b.optionDim.byteValue();

	    	if (aDim < bDim) {
	    		return -1;
	    	} else if (aDim > bDim) {
	    		return 1;
	    	} else {
	    		byte aIndex = a.optionIndex == null ? 0 : a.optionIndex.byteValue();
	    		byte bIndex = b.optionIndex == null ? 0 : b.optionIndex.byteValue();
	    		return aIndex - bIndex;
	    	}
	    } 
	} 

	public static List<QuestionOptionDTO> build(final List<QuestionOption> options) {
		final List<QuestionOptionDTO> ret = new ArrayList<>();
		for (QuestionOption qa : options) {
			ret.add(build(qa));
		}
		
		// Sort the result to the order that we want users see it.
		Collections.sort(ret, new CompareQuestionOption());
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

	public Byte getOptionIndex() {
		return optionIndex;
	}
	
	public void setOptionIndex(Byte optionIndex) {
		this.optionIndex = optionIndex;
	}
	
	public Byte getOptionDim() {
		return optionDim;
	}
	
	public void setOptionDim(Byte optionDim) {
		this.optionDim = optionDim;
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
