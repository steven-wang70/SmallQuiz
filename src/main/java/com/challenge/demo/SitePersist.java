package com.challenge.demo;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "site")
@EntityListeners(AuditingEntityListener.class)
public class SitePersist extends SiteBase {

	@OneToMany(mappedBy = "site", fetch = FetchType.EAGER)
	private List<QuestionPersist> questions = new ArrayList<>();
	
	public List<QuestionPersist> getQuestions() {
		return questions;
	}
	
	public void setQuestions(List<QuestionPersist> questions) {
		this.questions = questions;
	}
}