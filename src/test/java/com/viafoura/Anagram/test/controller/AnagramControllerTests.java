package com.viafoura.Anagram.test.controller;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.HashSet;
import static org.junit.Assert.assertEquals;
import org.skyscreamer.jsonassert.JSONAssert;
import static org.mockito.Mockito.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viafoura.Anagram.controller.CheckAnagramDto;
import com.viafoura.Anagram.controller.GetAnagramsDto;
import com.viafoura.Anagram.domain.AnagramService;
import com.viafoura.Anagram.exception.ErrorInfo;
import com.viafoura.Anagram.test.service.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
@AutoConfigureMockMvc
class AnagramControllerTests {

	@Autowired
	MockMvc mvc;
	
	@MockBean
	AnagramService anagramMockService; 
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	AnagramService testAnagramService = null;

    private static final ObjectMapper om = new ObjectMapper();  
    GetAnagramsDto getAnagramsDto = null;
    CheckAnagramDto checkAnagramDto = null;
	HashSet<String> anagramSetTest = null;
	HashSet<String> anagramSet = null;
    
    @BeforeEach
    public void init() {
    	testAnagramService = new AnagramService();
    	checkAnagramDto = new CheckAnagramDto();
        getAnagramsDto = new GetAnagramsDto();
        getAnagramsDto.setAnagramSet(new HashSet<String>());
        anagramSet = new HashSet<String>();
    }
		 
    @Test
    public void givenValidString_whenAnagramsAreMatched_then200IsReceived() throws Exception  {
        // Given
        final String string1 = "iceman";
        final String string2 = "cinema";
        checkAnagramDto = new CheckAnagramDto();
        checkAnagramDto.setAreAnagrams(true);
        
        when(anagramMockService.isAnagram("iceman", "cinema")).thenReturn(true);
        String expected = om.writeValueAsString(checkAnagramDto);

        ResponseEntity<String> response = restTemplate.getForEntity("/anagrams/" + string1 + "/" + string2, String.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(anagramMockService, times(1)).isAnagram("iceman", "cinema");


            }
    
    @Test
    public void givenValidString_whenAnagramsAreNotMatched_then200IsReceived() throws Exception  {
        // Given
        final String string1 = "heaven";
        final String string2 = "hell";

        checkAnagramDto.setAreAnagrams(false);

        when(anagramMockService.isAnagram("heaven", "hell")).thenReturn(false);
        String expected = om.writeValueAsString(checkAnagramDto);

        ResponseEntity<String> response = restTemplate.getForEntity("/anagrams/" + string1 + "/" + string2, String.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        JSONAssert.assertEquals(expected, response.getBody(), false);
        
        verify(anagramMockService, times(1)).isAnagram("heaven", "hell");
   }
    
    @Test
    public void givenInValidString_then400IsReceived() throws Exception  {
        // Given
        final String string1 = "iceman1";
        final String string2 = "cinema";
        ErrorInfo info = new ErrorInfo("HTTP 404", "BAD REQUEST");
        
        String expected = om.writeValueAsString(info);

        ResponseEntity<String> response = restTemplate.getForEntity("/anagrams/" + string1 + "/" + string2, String.class);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        JSONAssert.assertEquals(expected, response.getBody(), false);
        
        
     }
    
    @Test
    public void givenValidString_whenAllAnagramsAreFetched_then200IsReceived() throws Exception  {
        // Given
        final String string1 = "ABC";
        
        getAnagramsDto.getAnagramSet().add("ABC");
        getAnagramsDto.getAnagramSet().add("ACB");
        getAnagramsDto.getAnagramSet().add("BAC");
        getAnagramsDto.getAnagramSet().add("BCA");
        getAnagramsDto.getAnagramSet().add("CBA");
        getAnagramsDto.getAnagramSet().add("CAB");

        when(anagramMockService.getAllAnagrams("ABC")).thenReturn(getAnagramsDto.getAnagramSet());

        ResponseEntity<GetAnagramsDto> response = restTemplate.getForEntity("/anagrams/" + string1, GetAnagramsDto.class);
        GetAnagramsDto httpEntity = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(httpEntity.getAnagramSet().size(), getAnagramsDto.getAnagramSet().size());


        verify(anagramMockService, times(1)).getAllAnagrams("ABC");


            }    
    
    @Test
    public void givenValidString_whenAllAnagramsAreFetched_thenUniqueAnagramsAreReceived() throws Exception  {
        // Given
        final String string1 = "aBBB";
        
        getAnagramsDto.getAnagramSet().add("aBBB");
        getAnagramsDto.getAnagramSet().add("BaBB");
        getAnagramsDto.getAnagramSet().add("BBaB");
        getAnagramsDto.getAnagramSet().add("BBBa");

        when(anagramMockService.getAllAnagrams("aBBB")).thenReturn(getAnagramsDto.getAnagramSet());

        ResponseEntity<GetAnagramsDto> response = restTemplate.getForEntity("/anagrams/" + string1, GetAnagramsDto.class);
        GetAnagramsDto httpEntity = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(httpEntity.getAnagramSet().size(), getAnagramsDto.getAnagramSet().size());


        verify(anagramMockService, times(1)).getAllAnagrams("aBBB");

            }
    
    
   	@Test
       public void givenValidString_whenAnagramsAreMatching_thenTrueisReturned() throws Exception  {
           // Given
           final String string1 = "iceman";
           final String string2 = "cinema";
           
           boolean isAnagram = testAnagramService.isAnagram(string1, string2);

           assertEquals(isAnagram, true);
           
       }
       
   	@Test
       public void givenValidString_whenAnagramsAreNotMatching_thenFlaseisReturned() throws Exception  {
           // Given
           final String string1 = "iceman";
           final String string2 = "manict";
           
           boolean isAnagram = testAnagramService.isAnagram(string1, string2);

           assertEquals(isAnagram, false);
           
       }
       
       @Test
       public void givenInvalidString_WhenanagramsareChecked_thenFalseIsReturned() throws Exception  {
          
           assertEquals(testAnagramService.isAnagram("", "iceman"), false);
           assertEquals(testAnagramService.isAnagram("cinema", ""), false);
           assertEquals(testAnagramService.isAnagram(null, ""), false);
           
        }
       
       @Test
       public void givenValidString_whenAllAnagramsAreIndetified_thenAnagramsAreReturned() throws Exception  {
           // Given
           final String string1 = "ABC";
           
           anagramSet.add("ABC");
           anagramSet.add("ACB");
           anagramSet.add("BAC");
           anagramSet.add("BCA");
           anagramSet.add("CBA");
           anagramSet.add("CAB");

           anagramSetTest = testAnagramService.getAllAnagrams(string1);
           assertEquals(anagramSetTest.size(), anagramSet.size());
           assertEquals(TestUtil.matchEnteries(anagramSet, anagramSetTest), true);

               }    
       
       @Test
       public void givenValidString_whenAllAnagramsAreFetched_thenUniqueAnagramsAreReturned() throws Exception  {
           // Given
           final String string1 = "aBBB";
           
           anagramSet.add("aBBB");
           anagramSet.add("BaBB");
           anagramSet.add("BBaB");
           anagramSet.add("BBBa");

           anagramSetTest = testAnagramService.getAllAnagrams(string1);
           assertEquals(anagramSetTest.size(), anagramSet.size());
           assertEquals(TestUtil.matchEnteries(anagramSet, anagramSetTest), true);

               }
    
    

}
