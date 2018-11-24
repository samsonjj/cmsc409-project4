package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Cluster {

    double[] weights = null;

    ArrayList<String> sentences;
    ArrayList<ArrayList<String>> sentenceTokens;

    public Cluster() {
        sentences = new ArrayList<>();
        sentenceTokens = new ArrayList<>();
    }

    public void add(int[] vector, String sentence, ArrayList<String> tokens) {
        if(weights == null) {
            weights = Arrays.stream(vector).asDoubleStream().toArray();
        } else {
            for(int i = 0; i < weights.length; i++) {
                weights[i] = (weights[i] * size() + vector[i]) / (size() + 1);
            }
        }
        sentences.add(sentence);
        sentenceTokens.add(tokens);
    }

    public double getDistance(int[] vector) {
        double sum = 0;
        for(int i = 0; i < weights.length; i++) {
            sum += Math.pow(weights[i] - vector[i], 2);
        }
        sum = Math.sqrt(sum);
        return sum;
    }

    public int size() {
        return sentences.size();
    }

    public double[] getWeights() {
        return weights;
    }

    public ArrayList<String> getSentences() {
        return sentences;
    }

    public ArrayList<ArrayList<String>> getSentenceTokens() {
        return sentenceTokens;
    }

}
