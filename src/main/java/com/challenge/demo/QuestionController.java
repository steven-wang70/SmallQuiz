package com.challenge.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	SiteRepository siteRepository;

	@Autowired
	QuestionOptionRepository qaRepository;

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<QuestionDTO> updateQuestion(@RequestBody QuestionPersist incomingQuestion, @PathVariable(value = "id") Long questionId) {

		return questionRepository
				.findById(questionId)
				.map(question -> {
					question.setQuestion(incomingQuestion.getQuestion());
					question.setSite(incomingQuestion.getSite());
					question.setQuestionType(incomingQuestion.getQuestionType());
					question.setDimInfo(incomingQuestion.getDimInfo());
					return new ResponseEntity<>(QuestionDTO.build(questionRepository.save(question)), HttpStatus.OK);
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<QuestionDTO> deleteQuestion(@PathVariable(value = "id") Long questionId) {
		return questionRepository
				.findById(questionId)
				.map(question -> {
					questionRepository.delete(question);
					return ResponseEntity.ok(QuestionDTO.build(question));
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable(value = "id") Long questionId) {
		return questionRepository
				.findById(questionId)
				.map(question -> ResponseEntity.ok(QuestionDTO.build(question)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/{id}/options")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<QuestionOptionDTO> createQuestionOption(@PathVariable(value = "id") Long questionId,
																   @RequestBody QuestionOptionDTO newQADto) {
		if (!QuestionOptionPersist.ValidateOptionDim(newQADto.getOptionDim())) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}

		return questionRepository
				.findById(questionId)
				.map(question -> {
					final QuestionOptionPersist newQa = QuestionOptionDTO.transform(newQADto, question);
					return new ResponseEntity<>(QuestionOptionDTO.build(qaRepository.save(newQa)), HttpStatus.CREATED);
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}/options")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<QuestionOptionDTO>> getQuestionOptions(@PathVariable(value = "id") Long questionId) {
		return questionRepository
				.findById(questionId)
				.map(question -> ResponseEntity.ok(QuestionOptionDTO.build(question.getOptions())))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}