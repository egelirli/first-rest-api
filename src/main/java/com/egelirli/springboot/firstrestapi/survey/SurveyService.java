package com.egelirli.springboot.firstrestapi.survey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;



@Service
public class SurveyService {
	private static List<Survey> surveys = new ArrayList<>();
	
	static {
	
		Question question1 = new Question("Question1",
		        "Most Popular Cloud Platform Today", Arrays.asList(
		                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		Question question2 = new Question("Question2",
		        "Fastest Growing Cloud Platform", Arrays.asList(
		                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
		Question question3 = new Question("Question3",
		        "Most Popular DevOps Tool", Arrays.asList(
		                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

		List<Question> questions = new ArrayList<>(Arrays.asList(question1,
		        question2, question3));

		Survey survey = new Survey("Survey1", "My Favorite Survey",
		        "Description of the Survey", questions);

		surveys.add(survey);
	}

	public List<Survey> retriveAllSurveys() {
		return surveys;
	}

	public Survey retriveSurvey(String surveyId) {
		
		Predicate<? super Survey> predicate = 
				t -> t.getId().equalsIgnoreCase(surveyId);
				
		Optional<Survey> surv = 
				   surveys.stream().filter(predicate).findFirst();
		if(!surv.isEmpty()) {
			return surv.get();
		}else {
			return null;
		}
	
	}

	public List<Question> retriveAllSurveyQuestions(String surveyId) {
		
		List<Question> retList = null;
		Survey surv = retriveSurvey(surveyId);
		if(surv != null) {
			retList = surv.getQuestions();
		}
		return retList;
	}

	public Question retriveSurveyQuestion(String surveyId, String quesionId) {
		Question quest = null;
		
		Survey surv = retriveSurvey(surveyId);
		if(surv != null) {
			quest = surv.getQuestion(quesionId);
		}
		
		return quest;
	}

	public boolean addSurveyQuestion(String surveyId, Question question) {
		boolean isAdded = false;
		Survey surv = retriveSurvey(surveyId);
		if(surv != null) {
			isAdded = surv.getQuestions().add(question);
		}
		return isAdded;				
		
	}	
}
