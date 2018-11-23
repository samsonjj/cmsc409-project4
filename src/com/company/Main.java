package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static final String INPUT_FILE_NAME = "sentences.txt";

    public static void main(String[] args) {

        System.out.println("Starting...");

        ArrayList<String> sentences = new ArrayList<>();

        // Read in sentences
        try(Scanner fileScanner = new Scanner(new File(INPUT_FILE_NAME))) {
            while(fileScanner.hasNextLine()) {
                sentences.add(fileScanner.nextLine());
            }
        } catch(Exception e) {
            System.out.println("error reading file \"" + INPUT_FILE_NAME + "\"");
        }

        ArrayList<String[]> sentence_tokens = new ArrayList<>();

        // Remove punctuation, special characters, numbers
        for(int i = 0; i < sentences.size(); i++) {
            String sentence = sentences.get(i);
            sentence = sentence.replaceAll("[^a-zA-Z ]", "");
            sentence = sentence.toLowerCase();
            sentences.set(i, sentence);
        }

        // Split by spaces
        for(int i = 0; i < sentences.size(); i++) {
            sentence_tokens.add(sentences.get(i).split(" +"));
        }

        // Stem each token
        for(int i = 0; i < sentence_tokens.size(); i++) {
            for(int j = 0; j < sentence_tokens.get(i).length; j++) {
                String token = sentence_tokens.get(i)[j];
                Stemmer s = new Stemmer();
                for(int k = 0; k < token.length(); k++) {
                    s.add(token.charAt(k));
                }
                s.stem();
                String result = s.toString();
                sentence_tokens.get(i)[j] = result;
            }
        }

        for(String[] tokens : sentence_tokens) {
            printlnTokenArray(tokens);
        }


        System.out.println("Stopping...");

    }

    public static void printlnTokenArray(String[] tokens) {
        for(int i = 0; i < tokens.length; i++) {
            if(i == 0) System.out.print(tokens[i]);
            else System.out.print(" " + tokens[i]);
        }
        System.out.println();
    }
}
