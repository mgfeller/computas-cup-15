package com.computas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Round0Ru {

    private static final String TARGET = "2835e5d4214caa292f1843ecfbe141cb6d0828d8";
    private static final String RUMANIAN_WORDS_FILE = "/NSF-ordlisten/nouns.txt";

    public static void main(String[] args) throws FileNotFoundException,
            IOException, NoSuchAlgorithmException {
        int bestDistance = 160;
        String bestSolution = "";
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Round0Ru.class.getResourceAsStream(RUMANIAN_WORDS_FILE)))) {
            String line;

            while ((line = br.readLine()) != null) {
                String candidate = line.toLowerCase().split(",")[0];
                sha1.reset();
                sha1.update(candidate.getBytes("utf8"));
                String hash = new BigInteger(1, sha1.digest()).toString(16);
                while (hash.length() < TARGET.length()) {
                    hash = "0" + hash;
                }
                int distance = 0;
                for (int i = 0; i < hash.length(); i++) {
                    if (hash.charAt(i) != TARGET.charAt(i)) {
                        distance++;
                    }
                }
                if (distance < bestDistance) {
                    bestSolution = candidate;
                    bestDistance = distance;
                    System.out
                            .println(bestSolution + " (" + bestDistance + ")");
                    System.out.println("\t" + TARGET);
                    System.out.println("\t" + hash);
                }
                if (bestDistance == 0) {
                    System.out.println("== MATCH FOUND ==");
                    System.exit(0);
                }
            }
        }
    }

}
