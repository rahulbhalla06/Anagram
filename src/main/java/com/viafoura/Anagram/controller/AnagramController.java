package com.viafoura.Anagram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.viafoura.Anagram.service.AnagramService;

@RestController
public class AnagramController {
	
	@Autowired
	public AnagramService anagramService;
	
	

}
