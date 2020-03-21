package com.challenge.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QuestionOptionDTOforDisplay extends QuestionOptionBase {

	private Long questionId;

	public static QuestionOptionDTOforDisplay build(final QuestionOptionPersist save) {
		final QuestionOptionDTOforDisplay newQaDto = new QuestionOptionDTOforDisplay();

		newQaDto.setId(save.getId());
		newQaDto.setOption(save.getOption());
		newQaDto.setOptionIndex(save.getOptionIndex());
		newQaDto.setOptionDim(save.getOptionDim());
		newQaDto.setIsCorrectOption(save.isCorrectOption());
		newQaDto.setQuestionId(save.getQuestion().getQuestionId());

		return newQaDto;
	}

	/**
	 * Helper class to sort the QuestionOptionDTO object first by optionDim, then by optionIndex.
	 * @author steve
	 *
	 */
	private static class CompareQuestionOption implements Comparator<QuestionOptionDTOforDisplay>
	{
	    public int compare(QuestionOptionDTOforDisplay a, QuestionOptionDTOforDisplay b)
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

	public static List<QuestionOptionDTOforDisplay> build(final List<QuestionOptionPersist> options) {
		final List<QuestionOptionDTOforDisplay> ret = new ArrayList<>();
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
}
