package com.LFM.Dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class DictionaryAPI {
	String file;
	public static HashMap<String,String> dictionary = new HashMap<String,String>();
	
	public HashMap<String, String> getDictionary() {
		return dictionary;
	}

	public DictionaryAPI(String file) {
		this.file = file;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			//Add each line in dictionary
			while (line != null) {
				String[] parser = line.split("\t");
				String word = parser[0];
				String meaning = parser[1];
				addToDictionary(word,meaning);
				line = br.readLine();
			}
		}
		catch (Exception e){
			System.out.println("Issue with file reading "+e);
			System.exit(1);
		}
		
		
	}

	public void addToDictionary(String word, String meaning) {
		String value = DictionaryAPI.dictionary.get(word);
		if(value != null){
			System.out.println("Given word "+ word+" exists in dictionary already");
		}
		else{
			DictionaryAPI.dictionary.put(word, meaning);
		}
	}
	
	public void getMeaning(String word) {
		String value = DictionaryAPI.dictionary.get(word);
		if(value != null){
			System.out.println(value);
		}
		else{
			System.out.println("Given word "+ word + " does not exist in dictionary");
		}
	}
}
