package com.viafoura.Anagram.controller;

import java.util.HashSet;

public class GetAnagramsDto {
	private HashSet<String> AnagramSet;


	public HashSet<String> getAnagramSet() {
		return AnagramSet;
	}

	public void setAnagramSet(HashSet<String> anagramSet) {
		AnagramSet = anagramSet;
	}
}
