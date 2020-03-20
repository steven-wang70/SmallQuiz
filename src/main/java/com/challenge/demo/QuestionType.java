package com.challenge.demo;

/**
 * Definition of question types that will be presented to customers.
 * In the presentation, different question types have different display
 * layout and validation rule.
 * @author steve
 *
 */
public enum QuestionType {
	/**
	 *	### Trivia Question
	 *	> Which team won the 2017 superbowl?
	 *	
	 *	|||
	 *	| :---------- | :---------: |
	 *	| Falcons   | [ ] |
	 *	| Patriots  | [X] |
	 *	
	 *	Only one correct answer with two to four possible answers in this question type.
	 */
	TRIVIA(1),
	
	/**
	 *	### Pool Question
	 *	> What's your favorite car brand?
	 *	
	 *	|||
	 *	| :---------- | :---------: |
	 *	| Nissan    | [ ] |
	 *	| Honda     | [ ] |
	 *	| Audi      | [X] |
	 *	| BMW       | [ ] |
	 *	No correct answer with two to four possible answers in this question type.
	 */
	POOL(2),
	/**
	 *	### CheckBox Question
	 *	> What colors do you like? 
	 *	
	 *	|||
	 *	| :---------- | :---------: |
	 *	| Red           | [ ] |
	 *	| Blue          | [X] |
	 *	| Yellow        | [ ] |
	 *	| Green         | [ ] |
	 *	| Black         | [X] |
	 *	| Purple        | [ ] |
	 *	Is an objective question with up to ten possible answers.  This style of question 
	 *	allows for multiple correct responses
	 */
	
	CHECKBOX(3),
	/**
	 *	### Matrix Question
	 *	> Please tell us a bit about yourself? 
	 *	
	 *	| Age/Gender        | Male | Female |
	 *	| :----------   | :---------: | :----------: |
	 *	| < 18          | [ ] |[ ] |
	 *	| 18 to 35      | [ ] |[ ] |
	 *	| 35 to 55      | [ ] |[ ] |
	 *	| \> 55          | [X] |[ ] |
	 *	Is an objective question that shows options in a matrix. A visitor can only pick one of 
	 *	the available options, there is no right or wrong answer.*
	 */
	MATRIX(4);
	
    private final int questionCode;

    QuestionType(int questionCode) {
        this.questionCode = questionCode;
    }
    
    public int getQuestionCode() {
        return this.questionCode;
    }
    
    public static QuestionType fromCode(int questionCode) {
    	return values()[questionCode - 1]; // Our code is 1 based
    }
}
