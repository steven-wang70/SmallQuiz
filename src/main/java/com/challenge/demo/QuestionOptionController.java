package com.challenge.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is used for partners to update and delete their question options.
 * @author steve
 *
 */
@RestController
@RequestMapping("/options")
public class QuestionOptionController {
	@Autowired
	QuestionOptionRepository qaRepository;

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<QuestionOptionDTO> updateQuestionOption(@RequestBody QuestionOptionPersist incomingQuestionOption, @PathVariable(value = "id") Long optionId) {
		return qaRepository
				.findById(optionId)
				.map(questionOption -> {
					questionOption.setOption(incomingQuestionOption.getOption());
					return new ResponseEntity<>(QuestionOptionDTO.build(qaRepository.save(questionOption)), HttpStatus.OK);
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<QuestionOptionDTO> deleteQuestionOption(@PathVariable(value = "id") Long optionId) {
		return qaRepository
				.findById(optionId)
				.map(questionOption -> {
					qaRepository.delete(questionOption);
					return ResponseEntity.ok(QuestionOptionDTO.build(questionOption));
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}
