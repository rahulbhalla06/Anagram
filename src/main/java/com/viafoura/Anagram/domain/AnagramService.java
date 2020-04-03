package com.viafoura.Anagram.domain;

import java.util.HashSet;

import org.springframework.stereotype.Service;

@Service
public class AnagramService {
	private HashSet<String> anagramSet = null;
	
	public AnagramService() {
		anagramSet = new HashSet<String>();
	}
	
	public boolean isAnagram(String s1, String s2) {
		StringBuilder strb = null;
		String arrs1[] = null;

		try {
			
			if(s1 == null || s1 == null || s1.isEmpty() || s2.isEmpty()) {
				return false;
			}
			
			s1=s1.toLowerCase();
			s2=s2.toLowerCase();
			strb = new StringBuilder(s2);
			arrs1 = s1.split("");

			if (s1.length() == s2.length()) {
				for (String s : arrs1) {
					int index = strb.indexOf(s);
					if (index >= 0)
						strb.deleteCharAt(index);
				}
				if (strb.length() == 0)
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	private String swapChars(String str, int i, int j) 
	{ 
		StringBuilder sb = new StringBuilder(str); 
        sb.setCharAt(i, str.charAt(j)); 
        sb.setCharAt(j, str.charAt(i)); 
        return sb.toString(); 
	}

	public HashSet<String> getAllAnagrams(String str) {
		anagramSet.clear();
		getAllAnagrams(str, 0, str.length()-1);
		return anagramSet;
	}
	
	private void getAllAnagrams(String str, int start, int end) 
	{ 
		if (start == end) 
			anagramSet.add(str); // To get unique anagrams
		else
		{ 
			for (int i = start; i <= end; i++) 
			{ 
				str = swapChars(str,start,i); 
				getAllAnagrams(str, start+1, end); 
				str = swapChars(str,start,i); 
			} 
		} 
	} 

}
