package com.egelirli.springboot.firstrestapi.survey;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Survey {
	public Survey() {

	}

	public Survey(String id, String title, String description, List<Question> questions) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.questions = questions;
	}

	private String id;
	private String title;
	private String description;
	private List<Question> questions;

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public Question getQuestion(String quesionId) {
		
		  Predicate<? super Question> predicate =
				  		q ->  q.getId().equalsIgnoreCase(quesionId);
				  		
		Optional<Question> quest =
				  		questions.stream().filter(predicate).findFirst();		  		
		if(!quest.isEmpty()) {
			return quest.get();
		}else {
			return null;
		}
		
	}
	
	
	@Override
	public String toString() {
		return "Survey [id=" + id + ", title=" + title + ", description=" + description + ", questions=" + questions
				+ "]";
	}

	
}
