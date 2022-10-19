package com.egelirli.springboot.firstrestapi.survey;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



@RestController
public class SurveyResource {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private SurveyService   surveyService;
	
	private SurveyResource(SurveyService surveyService) {
		this.surveyService = surveyService;
		
	}
	
	 @RequestMapping("/surveys")
	 private List<Survey> retriveAllSurveys(){
		 return surveyService.retriveAllSurveys();
	 }

	 @RequestMapping("/surveys/{surveyId}")
	 private Survey retriveBySurveyId(@PathVariable String surveyId){
		 
		 Survey sur = surveyService.retriveSurvey(surveyId);
		 if(sur != null) {
			 return sur;
		 }else {
			 throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		 }
		  
	 }

	 @RequestMapping("/surveys/{surveyId}/questions")
	 private List<Question> retriveAllQuestionsForSurvey(@PathVariable String surveyId){
		 List<Question> quests =  surveyService.retriveAllSurveyQuestions(surveyId);
		 if(quests != null) {
			 return quests;
		 }else {
			 throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		 }
	 }

	 @RequestMapping("/surveys/{surveyId}/questions/{quesionId}")
	 private Question retriveQuestion(
			    @PathVariable String surveyId,
			    @PathVariable String quesionId){
		 
		 Question quest =  surveyService.retriveSurveyQuestion(surveyId, quesionId);
		 if(quest != null) {
			 return quest;
		 }else {
			 throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		 }
	 }
	 
	 @RequestMapping(value="/surveys/{surveyId}/questions", method=RequestMethod.POST)
	 public void addSurveyQuestion(
			    @PathVariable String surveyId, @RequestBody Question question){
		 
		 logger.debug("In addSurveyQuestion surveyId : {} Question : {]", 
				 										surveyId,question.getId());
		 surveyService.addSurveyQuestion(surveyId, question);

	 }

	 
}
