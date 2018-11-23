package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    static final String INPUT_FILE_NAME = "sentences.txt";
    static final String STOP_WORDS_FILE_NAME = "stop_words.txt";

    public static void main(String[] args) {

        System.out.println("Starting...");

        ArrayList<String> sentences = new ArrayList<>();
        HashSet<String> stopWords = new HashSet<>();

        // Read in sentences
        try(Scanner sentenceScanner = new Scanner(new File(INPUT_FILE_NAME)); Scanner stopScanner = new Scanner(new File(STOP_WORDS_FILE_NAME))) {
            while(sentenceScanner.hasNextLine()) {
                sentences.add(sentenceScanner.nextLine());
            }
            while(stopScanner.hasNext()) {
                stopWords.add(stopScanner.next());
            }
        } catch(Exception e) {
            System.out.println("error reading file \"" + INPUT_FILE_NAME + "\"");
        }

        ArrayList<ArrayList<String>> sentence_tokens = new ArrayList<>();

        // Remove punctuation, special characters, numbers
        for(int i = 0; i < sentences.size(); i++) {
            String sentence = sentences.get(i);
            sentence = sentence.replaceAll("[^a-zA-Z ]", "");
            sentence = sentence.toLowerCase();
            sentences.set(i, sentence);
        }

        // Split by spaces
        for(int i = 0; i < sentences.size(); i++) {
            sentence_tokens.add(new ArrayList<String>(Arrays.asList(sentences.get(i).split(" +"))));
        }

        // Remove stop words
        for(ArrayList<String> tokens : sentence_tokens) {
            int currentIndex = 0;
            while(currentIndex < tokens.size()) {
                if(stopWords.contains(tokens.get(currentIndex))) {
                    tokens.remove(currentIndex);
                }
                else {
                    currentIndex++;
                }
            }
        }

        // Stem each token
        for(int i = 0; i < sentence_tokens.size(); i++) {
            for(int j = 0; j < sentence_tokens.get(i).size(); j++) {
                String token = sentence_tokens.get(i).get(j);
                Stemmer s = new Stemmer();
                for(int k = 0; k < token.length(); k++) {
                    s.add(token.charAt(k));
                }
                s.stem();
                String result = s.toString();
                sentence_tokens.get(i).set(j, result);
            }
        }

        for(ArrayList<String> tokens : sentence_tokens) {
            printlnTokenArray(tokens);
        }


        System.out.println("Stopping...");

    }

    public static void printlnTokenArray(Iterable<String> tokens) {
        boolean first = true;
        for(String token : tokens) {
            if(first) {
                System.out.print(token);
                first = false;
            }
            else System.out.print(" " + token);
        }
        System.out.println();
    }
}
