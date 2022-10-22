package com.egelirli.springboot.firstrestapi.survey;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



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
	 public ResponseEntity<Object> addSurveyQuestion(
			    @PathVariable String surveyId, @RequestBody Question question){
		 
		 logger.debug("In addSurveyQuestion surveyId : {} Question : {]", 
				 										surveyId,question.getId());
		
		 String questionId = surveyService.addSurveyQuestion(surveyId, question);
		 
		 URI locatıon = URI.create("/surveys/" + surveyId + "/questions/" + questionId);
		//URI locatıon =  ServletUriComponentsBuilder.
		//		 fromCurrentRequest().path("/{questionId}").build(questionId);
		return ResponseEntity.created(locatıon ).build();

	 }

	 @RequestMapping(value="/surveys/{surveyId}/questions/{questionId}", method=RequestMethod.DELETE)
	 public ResponseEntity<Object>  deleteSurveyQuestion(
			    @PathVariable String surveyId,
			    @PathVariable String questionId){
		 
		 if(surveyService.deleteSurveyQuestion(surveyId, questionId)) {
			 return ResponseEntity.noContent().build();
		 }else {
			 //throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			 return ResponseEntity.notFound().build();
		 }
	 }

	 @RequestMapping(value="/surveys/{surveyId}/questions/{questionId}", method=RequestMethod.PUT)
	 public ResponseEntity<Object>  modifySurveyQuestion(
			    @PathVariable String surveyId,
			    @PathVariable String questionId,
			    @RequestBody Question question){
		 
		 if(surveyService.modifySurveyQuestion(surveyId, questionId,question)) {
			 return ResponseEntity.ok().build();
		 }else {
			 //throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			 return ResponseEntity.notFound().build();
		 }
	 }
	 
	 
}
