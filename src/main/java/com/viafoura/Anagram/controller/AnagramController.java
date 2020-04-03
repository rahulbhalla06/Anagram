package com.viafoura.Anagram.controller;

import java.util.HashSet;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viafoura.Anagram.domain.AnagramService;
import com.viafoura.Anagram.exception.InvalidStringException;
import com.viafoura.Anagram.util.AnagramUtil;

@RestController
public class AnagramController {
	
	@Autowired
	public AnagramService anagramService;
	
	@RequestMapping(path = "/anagrams/{string1}/{string2}", method=RequestMethod.GET, produces="application/json")
	public CheckAnagramDto isAnagrams(@PathVariable("string1") String string1, @PathVariable("string2") String string2) {
		boolean isAnagram = false;
		CheckAnagramDto checkAnagramDto = null; 
		
		if(!AnagramUtil.isStringValid(string1) || !AnagramUtil.isStringValid(string2)) {
			throw new InvalidStringException("Input string is invalid");
		}
		isAnagram = anagramService.isAnagram(string1, string2);
		checkAnagramDto = new CheckAnagramDto();
		checkAnagramDto.setAreAnagrams(isAnagram);
		return checkAnagramDto;
	}
	
	@RequestMapping(path = "/anagrams/{string1}", method=RequestMethod.GET, produces="application/json")
	public GetAnagramsDto getAllAnagrams(@PathVariable("string1") String string1) {
		GetAnagramsDto checkAnagramDto = null; 
		HashSet<String> anagramSet = null;
		
		if(!AnagramUtil.isStringValid(string1)) {
			throw new InvalidStringException("Input string is invalid");
		}
		anagramSet = anagramService.getAllAnagrams(string1);
		checkAnagramDto = new GetAnagramsDto();
		checkAnagramDto.setAnagramSet(anagramSet);
		return checkAnagramDto;
	}
	 

}
