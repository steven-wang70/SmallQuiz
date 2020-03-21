package com.challenge.demo;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class QuestionOptionBase {

	/**
	 * This is the unique id of the option. It does not affect the order of display.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "question_option_id")
	private Long id;

	private String option;

	/**
	 * The index of the option. The display is sorted by this index ascendantly.
	 */
	@Column(name = "option_index", nullable = false, columnDefinition = "TINYINT")
	private Byte optionIndex;

	/**
	 * option dimension.
	 * When this value is null by default, the option is displayed top to bottom 
	 * just as normal. Otherwise, when it is 1, it will display left to right.
	 * Dim value 1 is used to support matrix question type.
	 */
	@Column(name = "option_dim", nullable = true, columnDefinition = "TINYINT")
	private Byte optionDim;

	/**
	 * Whether this option is a correct answer.
	 * Right now we are using it in trivia questions. In the future, can be used 
	 * in other question types except matrix question.
	 * 
	 * In the current design, since we are adding options to the service one at a time, 
	 * we could not guarantee there is one and only one correct option in trivia question.
	 * Users need to make sure they are actually have one and only one correct option for
	 * question.
	 * To fix this issue, we need to make users submit all options for a question in a single
	 * call. This can be considered later since it is beyond current task.
	 */
	@Column(nullable = true, columnDefinition = "TINYINT(1)")
	private boolean isCorrectOption;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Byte getOptionIndex() {
		return optionIndex;
	}

	public void setOptionIndex(final Byte optionIndex) {
		this.optionIndex = optionIndex;
	}

	public Byte getOptionDim() {
		return optionDim;
	}

	/**
	 * Valid value of optionDim can only be null, 0, or 1. Otherwise, it will throw exception.
	 * @param optionDim
	 */
	public static boolean ValidateOptionDim(final Byte optionDim) {
		return optionDim == null || optionDim == 0 || optionDim == 1;
	}

	public void setOptionDim(final Byte optionDim) {
		this.optionDim = optionDim;
	}

	public String getOption() {
		return option;
	}

	public void setOption(final String Option) {
		this.option = Option;
	}

	public boolean isCorrectOption() {
		return isCorrectOption;
	}

	public void setIsCorrectOption(boolean isCorrectOption) {
		this.isCorrectOption = isCorrectOption;
	}

	public abstract Long getQuestionId();
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final QuestionOptionBase that = (QuestionOptionBase) o;
		return isCorrectOption == that.isCorrectOption &&
				Objects.equals(id, that.id) &&
				Objects.equals(getQuestionId(), that.getQuestionId()) &&
				Objects.equals(option, that.option);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, getQuestionId(), option, isCorrectOption);
	}

}
