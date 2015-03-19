package com.LFM.Dictionary;

import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String dictionaryInput = "input\\dictionary.txt";
		String thesaurusInput = "input\\thesaurus.txt";
		System.out.println("Welcome to DictionaryAPI/ThesaurusAPI implementation");
		System.out.println("Building Dictionary/Thesaurus..");
		DictionaryAPI dictionary = new DictionaryAPI(dictionaryInput);
		ThesaurusAPI thesaurus = new ThesaurusAPI(thesaurusInput);
		System.out.println("Dictionary Built. Below are list of operations to select");
		int choice = 0;
		while(choice >= 0){
			System.out.println("1. LookUp for Meaning in Dictionary");
			System.out.println("2. LookUp for Synonym in Thesaurus");
			System.out.println("3. Add a word to Dictionary");
			System.out.println("4. Add a word to Thesaurus");
			System.out.println("Enter your choice(1 or 2 or 3 or 4) Press -1 to exit");
			choice = in.nextInt();
			String lookUpWord;
			switch (choice) {
			case 1:
				System.out.println("Enter Word: ");
				lookUpWord = in.next();
				dictionary.getMeaning(lookUpWord);
				break;
			case 2:
				System.out.println("Enter Word: ");
				lookUpWord = in.next();
				thesaurus.getSynonyms(lookUpWord);
				break;
			case 3:
				System.out.println("Enter Word: ");
				String word = in.next();
				System.out.println("Enter Meaning of "+word+":");
				String meaning = in.next();
				dictionary.addToDictionary(word, meaning);
				break;
			case 4:
				System.out.println("Enter Word: ");
				String word_thesaurus = in.next();
				System.out.println("Enter Synonym to add for "+word_thesaurus);
				String synonyms = in.next();
				thesaurus.addToThesaurus(word_thesaurus, synonyms);
				break;
			default:
				System.out.println("Invalid choice. Exiting");
				System.exit(0);
				break;
			}
		}
		
	}
	
}
