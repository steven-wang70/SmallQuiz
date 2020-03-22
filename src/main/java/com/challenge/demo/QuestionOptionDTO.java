package com.challenge.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class QuestionOptionDTO extends QuestionOptionBase {

	private Long questionId;

	private Date createdAt;

	private Date updatedAt;

	public static QuestionOptionPersist transform(final QuestionOptionDTO newQADto, final QuestionPersist question) {
		final QuestionOptionPersist newQa = new QuestionOptionPersist();
		newQa.setOption(newQADto.getOption());
		newQa.setOptionIndex(newQADto.getOptionIndex());
		newQa.setOptionDim(newQADto.getOptionDim());
		newQa.setIsCorrectOption(newQADto.getIsCorrectOption());
		newQa.setQuestion(question);
		
		return newQa;
	}

	public static QuestionOptionDTO build(final QuestionOptionPersist save) {
		final QuestionOptionDTO newQaDto = new QuestionOptionDTO();

		newQaDto.setId(save.getId());
		newQaDto.setOption(save.getOption());
		newQaDto.setOptionIndex(save.getOptionIndex());
		newQaDto.setOptionDim(save.getOptionDim());
		newQaDto.setIsCorrectOption(save.getIsCorrectOption());
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
	    	byte aDim = a.getOptionDim() == null ? 0 : a.getOptionDim().byteValue();
	    	byte bDim = b.getOptionDim() == null ? 0 : b.getOptionDim().byteValue();

	    	if (aDim < bDim) {
	    		return -1;
	    	} else if (aDim > bDim) {
	    		return 1;
	    	} else {
	    		byte aIndex = a.getOptionIndex() == null ? 0 : a.getOptionIndex().byteValue();
	    		byte bIndex = b.getOptionIndex() == null ? 0 : b.getOptionIndex().byteValue();
	    		return aIndex - bIndex;
	    	}
	    } 
	} 

	public static List<QuestionOptionDTO> build(final List<QuestionOptionPersist> options) {
		final List<QuestionOptionDTO> ret = new ArrayList<>();
		for (QuestionOptionPersist qa : options) {
			ret.add(build(qa));
		}
		
		// Sort the result to the order that we want users see it.
		Collections.sort(ret, new CompareQuestionOption());
		return ret;
	}

	@Override
	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(final Long questionId) {
		this.questionId = questionId;
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
