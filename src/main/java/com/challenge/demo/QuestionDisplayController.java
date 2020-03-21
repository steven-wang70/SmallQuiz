package com.challenge.demo;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nextquestion")
public class QuestionDisplayController {

	@Autowired
	SiteRepository siteRepository;

	@Autowired
	QuestionRepository qRepository;

	@Autowired
	QuestionDisplayRepository qdRepository;

	/**
	 * This GET method is called by reader's browser to pull next available question.
	 * The returned question contains all required informations including all options.
	 * @param siteId
	 * @param readerid
	 * @return
	 */
	@GetMapping("/{siteuuid}?reader={readeruuid}")
	public ResponseEntity<QuestionDTOforDisplay> getNextQuestion(@PathVariable(value = "siteuuid") String strSiteUUID, @PathVariable(value = "readeruuid") String strReaderUUID) {
		UUID siteUUID = UUID.fromString(strSiteUUID);
		UUID readerUUID = UUID.fromString(strReaderUUID);
		if (siteUUID == null || readerUUID == null) {
			return ResponseEntity.notFound().build();
		}
		
		Site site = siteRepository.findByUuid(siteUUID);
		if (site == null) {
			return ResponseEntity.notFound().build();
		}

		// Get question list of the site.
		List<Question> questions = qRepository.findSiteQuestions(site.getSiteId());
		if (questions == null || questions.size() == 0) {
			return ResponseEntity.notFound().build();
		}

		// Find the last question displayed.
		QuestionDisplay lastQuestion = qdRepository.findByUuid(site.getSiteId(), readerUUID);
		Question questionToDisplay = null;
		if (lastQuestion == null) {
			lastQuestion = new QuestionDisplay(site, readerUUID);
			questionToDisplay = questions.get(0);
		} else {
			// Find the next question.
			if (questions.get(questions.size() - 1) == questionToDisplay) {
				// This is the last question. Rewind back to the first one.
				questionToDisplay = questions.get(0);
			} else {
				for (Question q : questions) {
					if (q.getQuestionId() > lastQuestion.getLastQuestion()) {
						questionToDisplay = q;
						break;
					}
				}

				// If we could not find one, just select the first one.
				if (questionToDisplay == null) {
					questionToDisplay = questions.get(0);
				}
			}
		}

		// Save the last question for future reference.
		lastQuestion.setLastQuestion(questionToDisplay.getQuestionId());
		qdRepository.save(lastQuestion);

		return ResponseEntity.ok(QuestionDTOforDisplay.build(questionToDisplay));
	}

}
