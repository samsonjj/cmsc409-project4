package com.company;

import java.io.File;
import java.util.*;

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
                String sentence = sentenceScanner.nextLine();
                if(!sentence.trim().equals("")) sentences.add(sentence);
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

        // Create a list of words,
        ArrayList<String> wordList;

        HashSet<String> dictionary = new HashSet<>();
        for(ArrayList<String> tokens : sentence_tokens) {
            for(String token : tokens) {
                dictionary.add(token);
            }
        }
        wordList = new ArrayList(dictionary);
        Collections.sort(wordList);

        // Create feature vector for each sentence
        int[][] featureVectors = new int[sentence_tokens.size()][wordList.size()];
        for(int i = 0; i < sentence_tokens.size(); i++) {
            HashMap<String, Integer> wordCount = new HashMap<>();
            for(String token : sentence_tokens.get(i)) {
                Integer count = wordCount.get(token);
                if(count == null) {
                    wordCount.put(token, 1);
                } else {
                    wordCount.put(token, count + 1);
                }
            }
            for(int j = 0; j < wordList.size(); j++) {
                String feature = wordList.get(j);
                Integer count = wordCount.get(feature);
                featureVectors[i][j] = (count == null ? 0 : count);
            }
        }


        System.out.println("number of unique words is " + wordList.size());
        System.out.println("wordList: " + wordList);

        System.out.println("\nsentence tokens: ");
        for(int i = 0; i < sentence_tokens.size(); i++) {
            System.out.print(i + ") ");
            printlnTokenArray(sentence_tokens.get(i));
        }

        System.out.println("\nfeature vectors: ");
        for(int i = 0; i < featureVectors.length; i++) {
            System.out.print(i + ") ");
            System.out.println(Arrays.toString(featureVectors[i]));
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
