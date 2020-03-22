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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/sites")
public class SiteController {

	@Autowired
	SiteRepository siteRepository;

	@Autowired
	QuestionRepository questionRepository;
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResponseEntity<SiteDTO> createSite(@RequestBody SiteDTO createSite) {
		createSite.setSiteUUID(UUID.randomUUID());
		final SitePersist newSite = SiteDTO.transform(createSite);
		return new ResponseEntity<>(SiteDTO.build(siteRepository.save(newSite)), HttpStatus.CREATED);
	}

	@GetMapping()
	public ResponseEntity<List<SiteDTO>> getSites() {
		return Optional
				.ofNullable(siteRepository.findAll())
				.map(sites -> ResponseEntity.ok(SiteDTO.build(sites)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<SiteDTO> updateSite(@RequestBody SiteDTO updatedSite, @PathVariable(value = "id") Long siteId) {
		return siteRepository
				.findById(siteId)
				.map(site -> {
					site.setUrl(updatedSite.getUrl());
					return new ResponseEntity<>(SiteDTO.build(siteRepository.save(site)), HttpStatus.OK);
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<SiteDTO> deleteSite(@PathVariable(value = "id") Long siteId) {
		return siteRepository
				.findById(siteId)
				.map(site -> {
					siteRepository.delete(site);
					return ResponseEntity.ok(SiteDTO.build(site));
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<SiteDTO> getSiteById(@PathVariable(value = "id") Long siteId) {
		return siteRepository
				.findById(siteId)
				.map(site -> ResponseEntity.ok(SiteDTO.build(site)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/{id}/questions")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<QuestionDTO> createSiteQuestion(@PathVariable(value = "id") Long siteId,
																   @RequestBody QuestionDTO newQDto) {
		return siteRepository
				.findById(siteId)
				.map(site -> {
					final QuestionPersist newQ = QuestionDTO.transform(newQDto, site);
					return new ResponseEntity<>(QuestionDTO.build(questionRepository.save(newQ)), HttpStatus.CREATED);
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}/questions")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<QuestionDTO>> getSiteQuestions(@PathVariable(value = "id") Long siteId) {
		return siteRepository
				.findById(siteId)
				.map(site -> ResponseEntity.ok(QuestionDTO.build(site.getQuestions())))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}


}
