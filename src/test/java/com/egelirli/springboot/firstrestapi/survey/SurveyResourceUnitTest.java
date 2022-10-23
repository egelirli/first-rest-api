package com.egelirli.springboot.firstrestapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = SurveyResource.class)
public class SurveyResourceUnitTest {
	private static String SPECIFIC_QUESTION_URL = 
			"http://localhost:8080/surveys/Survey1/questions/Question1";
	
	private static String GENERIC_QUESTION_URL = 
			"http://localhost:8080/surveys/Survey1/questions";
	
	@MockBean
	private SurveyService surveyService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void retrieveSpecificSurveyQuestion_404Scenario() throws Exception {
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).
				                     accept(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(404, mvcResult.getResponse().getStatus());
		
	}	
	
	@Test
	void retrieveSpecificSurveyQuestion_basicScenario() throws Exception {
		
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
		
		
		Question question = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");

		when(surveyService.retriveSurveyQuestion("Survey1", "Question1")).thenReturn(question);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();		
	
		String expectedResponse = """
				{
				
					"id":"Question1",
					"description":"Most Popular Cloud Platform Today",
					"options":["AWS","Azure","Google Cloud","Oracle Cloud"],
					"correctAnswer":"AWS"
				
				}
						
				"""; 
		
		
		MockHttpServletResponse response = mvcResult.getResponse();
		
		assertEquals(200, response.getStatus());
		JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false);
		
		
	}

	
	
	@Test
	void retrieveSurveyQuestions_basicScenario() throws Exception {
		
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders.get(GENERIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
		
		
		//Mock  surveyService START
		Question question1 = new Question("Question1",
		        "Most Popular Cloud Platform Today", Arrays.asList(
		                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		Question question2 = new Question("Question2",
		        "Fastest Growing Cloud Platform", Arrays.asList(
		                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
		List<Question> questList = new ArrayList<>();
		questList.add(question1);
		questList.add(question2);
		when(surveyService.retriveAllSurveyQuestions("Survey1")).thenReturn(questList);
		//Mock  surveyService END
		
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();		
	
		String expectedResponse =
				"""
					[
					  {
					    "id": "Question1"
					  },
					  {
					    "id": "Question2"
					  }
					]
				""";
		
		
		MockHttpServletResponse response = mvcResult.getResponse();
		
		assertEquals(200, response.getStatus());
		JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false);
		
		
	}
	
}
