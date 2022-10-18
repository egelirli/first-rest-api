package com.egelirli.springboot.firstrestapi.survey;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SurveyResource {
    
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

}
