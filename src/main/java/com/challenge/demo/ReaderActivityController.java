package com.challenge.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is only used for readers to submit their answers to questions.
 * @author steve
 *
 */
@RestController
@RequestMapping("/answers")
public class ReaderActivityController {

	@Autowired
	ReaderActivityRepository raRepository;
	
	@Autowired
	QuestionAnswerRepository qaRepository;
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ReaderActivity createSite(@RequestBody ReaderActivity readerActivity) {
		// First save the main record of the reader activity.
		readerActivity = raRepository.save(readerActivity);
		
		// Then save reader answers to the question.
		if (readerActivity.getAnswers() != null) {
			for (Long answerId: readerActivity.getAnswers()) {
				QuestionAnswer qa = new QuestionAnswer();
				qa.setReaderActivity(readerActivity.getId());
				qa.setQuestionOption(answerId);
				qaRepository.save(qa);
			}
		}
		
		return readerActivity;
	}
}
