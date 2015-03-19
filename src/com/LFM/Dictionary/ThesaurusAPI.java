package com.LFM.Dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ThesaurusAPI {
	String file;
	public static HashMap<String,Set<String>> thesaurus = new HashMap<String,Set<String>>();
	
	public HashMap<String, Set<String>> getThesaurus() {
		return thesaurus;
	}

	public ThesaurusAPI(String file) {
		this.file = file;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			//Add each line in dictionary
			while (line != null) {
				String[] parser = line.split("\t");
				String word = parser[0];
				String synonymList = parser[1];
				String synonyms[] = synonymList.split(",");
				for(int index = 0; index < synonyms.length; index++){
					addToThesaurus(word,synonyms[index]);
				}
				line = br.readLine();
			}
		}
		catch (Exception e){
			System.out.println("Issue with file reading "+e);
			System.exit(1);
		}
	}

	public boolean addToThesaurus(String word, String synonym) {
		boolean isAdded = false;
		boolean wordExists = false;
		boolean synonymExist = false;
		Set<String> synonyms = ThesaurusAPI.thesaurus.get(word);
		if(synonyms== null || !synonyms.contains(synonym)){
			//Synonym is new add to dictionary
			//Check if given word and synonyms are present in dictionary
			wordExists = checkWordExistance(word);
			if(wordExists){
				synonymExist = checkWordExistance(synonym);
				if(synonymExist){
					//Both word and synonym exist in dictionary. So add the synonym in to list
					if(synonyms==null){
						synonyms = new HashSet<String>();
					}
					synonyms.add(synonym);
					ThesaurusAPI.thesaurus.put(word, synonyms);
					//Implement commutative and transitive
					checkCommutative(word,synonym);
					checkTransitive(word,synonym);
					isAdded = true;
				}
			}
			
		}
		else{
			//No need to print as already a synonym
			//System.out.println(" "+ synonym+" is already a synonym for "+word );
		}
		return isAdded;
	}
	
	private void checkTransitive(String word, String synonym) {
		Set<String> synonyms_for_word = ThesaurusAPI.thesaurus.get(word);
		Set<String> synonyms_for_synonym = ThesaurusAPI.thesaurus.get(synonym);
		// if a is synonymous with b and b is synonymous with c, then a is synonymous with c
		if(synonyms_for_synonym != null){
			for (String s : synonyms_for_synonym) {
				//If a synonym is not present in words list add it
			    if(!s.equalsIgnoreCase(word) && (synonyms_for_word == null || !synonyms_for_word.contains(s))){
			    	if(synonyms_for_word==null){
			    		synonyms_for_word = new HashSet<String>();
					}
			    	synonyms_for_word.add(s);
			    	ThesaurusAPI.thesaurus.put(word, synonyms_for_word);
			    }
			}
		}
		if(synonyms_for_word != null){
			for (String s : synonyms_for_word) {
			    if(!s.equalsIgnoreCase(synonym) && (synonyms_for_synonym==null || !synonyms_for_synonym.contains(s))){
			    	if(synonyms_for_synonym==null){
			    		synonyms_for_synonym = new HashSet<String>();
					}
			    	synonyms_for_synonym.add(s);
			    	ThesaurusAPI.thesaurus.put(synonym, synonyms_for_synonym);
			    }
			}
		}
	}

	private void checkCommutative(String word, String synonym) {
		//Check if given word is synonym for current synonym
		Set<String> synonyms = ThesaurusAPI.thesaurus.get(synonym);
		//if not add the word as synonym for current synonym
		if(synonyms == null || !synonym.contains(word)){
			if(synonyms==null){
				synonyms = new HashSet<String>();
			}
			synonyms.add(word);
			ThesaurusAPI.thesaurus.put(synonym, synonyms);
		}
		
	}

	private boolean checkWordExistance(String word) {
		boolean wordExists = true;
		if(!DictionaryAPI.dictionary.containsKey(word)){
			System.out.println(" "+word+" does not exist in dictionary. A word should exist in dictionary to add synonyms");
			wordExists = false;
		}
		return wordExists;
	}

	public void getSynonyms(String word) {
		Set<String> synonymList = ThesaurusAPI.thesaurus.get(word);
		if(synonymList == null || synonymList.size() <= 0){
			System.out.println("Synonyms of "+word+" does not exist in Thesaurus");
		}
		else{
			Iterator<String> iterator = synonymList.iterator();
			while (iterator.hasNext()) {
			    System.out.println(iterator.next());
			}
		}
	}
	
}
