package com.computas;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Round1 {

    private static final String TARGET = "bca5222466a6e2c519031949bdf3a550525d5d9d";
    private static final String NORWEGIAN_WORDS_FILE = "/NSF-ordlisten/NSF-ordlisten.txt";
    private static final String RUMANIAN_WORDS_FILE = "/NSF-ordlisten/nouns.txt";


    public static List<String> readNorwegianNouns() throws FileNotFoundException,
            IOException, NoSuchAlgorithmException {
        final List<String> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Round1.class.getResourceAsStream(NORWEGIAN_WORDS_FILE)))) {
            String line;

            while ((line = br.readLine()) != null) {
                String elements[] = line.toLowerCase().split(" ");
                String candidate = elements[0];
                String type = elements[1];
                if ("subst".equals(type.trim())) {
                    result.add(candidate);
                    // System.out.println("no: " + candidate);
                }
            }
        }
        return result;
    }

    public static List<String> readRumanianNouns() throws FileNotFoundException,
            IOException, NoSuchAlgorithmException {
        final List<String> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Round1.class.getResourceAsStream(RUMANIAN_WORDS_FILE)))) {
            String line;

            while ((line = br.readLine()) != null) {
                String elements[] = line.split(",");
                String candidate = elements[0];
                String type = elements[1];
                if ("s.n.".equals(type.trim())) {
                    result.add(candidate);
                    // System.out.println("ru: " + candidate);
                }
            }
        }
        return result;
    }

public static String hex2Bin(char c) {
    switch (c) {
        case '0': return "0000"  ;
        case '1': return "0001";
        case '2': return "0010";
        case '3': return "0011";
        case '4': return "0100";
        case '5': return "0101";
        case '6': return "0110";
        case '7': return "0111";
        case '8': return "1000";
        case '9': return "1001";
        case 'a': return "1010";
        case 'b': return "1011";
        case 'c': return "1100";
        case 'd': return "1101";
        case 'e': return "1110";
        case 'f': return "1111";
        default: return "----";
    }}

    public static int calcDistance(String hash) {
        int distance = 0;
//        System.err.println(new BigInteger(hash.getBytes()).toString(2));
        for (int i = 0; i < hash.length(); i++) {
            String h = hex2Bin(hash.charAt(i));
            String t = hex2Bin(TARGET.charAt(i));

            for (int j = 0; j < h.length(); j++) {
                if (h.charAt(j) != t.charAt(j)) {
                    distance++;
                    if (distance > 50) {
                        return 10000;
                    }
                }
            }
        }
        return distance;
    }

    public static String calcHash(String candidate, MessageDigest sha1) throws UnsupportedEncodingException {
        sha1.reset();
        sha1.update(candidate.getBytes("utf8"));
        String hash = new BigInteger(1, sha1.digest()).toString(16);
        while (hash.length() < TARGET.length()) {
            hash = "0" + hash;
        }
        return hash;
    }

    public static void main(String[] args) throws FileNotFoundException,
            IOException, NoSuchAlgorithmException {
        int bestDistance = 160;
        List<String> norwegian = readNorwegianNouns();
        List<String> romanian = readRumanianNouns();
 //       System.out.println("Norwegian: " + norwegian.size());
 //       System.out.println("Romanian: " + romanian.size());
        String bestSolution = "";
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        for (String no : norwegian) {
            for (String ru : romanian) {
                String candidate = no + " " + ru;
              //  System.err.println("+++ " + candidate);
                String hash = calcHash(candidate, sha1);
                int distance = calcDistance(hash);

                if (distance < bestDistance) {

                    bestSolution = candidate;
                    bestDistance = distance;
                    System.out.println(candidate + " " + hash + " d=" + distance);
//                    System.out
//                            .println(bestSolution + " (" + bestDistance + ")");
//                    System.out.println("\t" + TARGET);
//                    System.out.println("\t" + hash);
                }
                if (bestDistance == 0) {
                    System.out.println("== MATCH FOUND ==");
                    System.exit(0);
                }
            }
        }
    }

}
