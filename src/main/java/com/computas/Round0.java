package com.computas;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Round0 {

    private static final String TARGET = "2fde9dda888bf16911424cc05e764b67f2d30d4d";
    private static final String NORWEGIAN_WORDS_FILE = "/NSF-ordlisten/NSF-ordlisten.txt";

    public static void main(String[] args) throws FileNotFoundException,
            IOException, NoSuchAlgorithmException {
        int bestDistance = 160;
        String bestSolution = "";
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Round0.class.getResourceAsStream(NORWEGIAN_WORDS_FILE)))) {
            String line;

            while ((line = br.readLine()) != null) {
                String candidate = line.toLowerCase().split(" ")[0];
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
