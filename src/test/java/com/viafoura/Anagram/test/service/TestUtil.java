package com.viafoura.Anagram.test.service;

import java.util.HashSet;
import java.util.Iterator;

public class TestUtil {
	
	 public static boolean matchEnteries(HashSet<String> set1, HashSet<String> st2) {
		 try {
			 Iterator<String> it1 = set1.iterator();
			 while(it1.hasNext()) {
				 if(!st2.contains(it1.next())){
					 return false;
				 }
			 }
			 return true;
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 return false;
	 }

}
