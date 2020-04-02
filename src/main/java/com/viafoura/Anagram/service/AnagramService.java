package com.viafoura.Anagram.service;

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


	/** 
	 * Swap Characters at position 
	 * @param a string value 
	 * @param i position 1 
	 * @param j position 2 
	 * @return swapped string 
	 */
	private String swap(String a, int i, int j) 
	{ 
		char temp; 
		char[] charArray = a.toCharArray(); 
		temp = charArray[i] ; 
		charArray[i] = charArray[j]; 
		charArray[j] = temp; 
		return String.valueOf(charArray); 
	}

	/** 
	 * permutation function 
	 * @param str string to calculate permutation for 
	 * @param l starting index 
	 * @param r end index 
	 */
	public void permute(String str, int l, int r) 
	{ 
		if (l == r) 
			anagramSet.add(str); 
		else
		{ 
			for (int i = l; i <= r; i++) 
			{ 
				str = swap(str,l,i); 
				permute(str, l+1, r); 
				str = swap(str,l,i); 
			} 
		} 
	} 

}
