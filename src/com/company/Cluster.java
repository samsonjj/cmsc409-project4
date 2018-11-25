package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Cluster {

    double[] weights = null;

    ArrayList<String> sentences;
    ArrayList<ArrayList<String>> sentenceTokens;
    ArrayList<int[]> vectors;
    ArrayList<Integer> correctCluster;

    public Cluster() {
        sentences = new ArrayList<>();
        sentenceTokens = new ArrayList<>();
        vectors = new ArrayList<>();
        correctCluster = new ArrayList<>();
    }

    public void add(int[] vector, String sentence, ArrayList<String> tokens, int correct) {
        if(weights == null) {
            weights = Arrays.stream(vector).asDoubleStream().toArray();
        } else {
            for(int i = 0; i < weights.length; i++) {
                weights[i] = (weights[i] * size() + vector[i]) / (size() + 1);
            }
        }
        sentences.add(sentence);
        sentenceTokens.add(tokens);
        vectors.add(vector);
        correctCluster.add(correct);
    }

    public double getDistance(int[] vector) {
        double sum = 0;
        for(int i = 0; i < weights.length; i++) {
//            sum += Math.pow(weights[i] - vector[i], 2);
            sum -= weights[i] * vector[i];
        }
        //sum = Math.sqrt(sum);
        return sum  / vector.length;
    }

//    public double getDistance(int[] vector) {
//        double sum = 0;
//        int wordCount = 0;
//        for (int i = 0; i < weights.length; i++) {
//            sum += Math.ceil(weights[i]) * vector[i];
//            wordCount += vector[i];
//        }
//        sum *= -1;
//        return sum / wordCount;
//    }

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

    public ArrayList<int[]> getVectors() {
        return vectors;
    }

    public ArrayList<Integer> getCorrectCluster() {
        return correctCluster;
    }

}
