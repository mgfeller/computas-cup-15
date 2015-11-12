package com.computas;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Round2 {

    private static final String TARGET = "b9fe6770fb28c99a96bf1e19faf9edbbfd81a4fd";
    private static final String NORWEGIAN_WORDS_FILE = "/NSF-ordlisten/NSF-ordlisten.txt";
    private static final String RUMANIAN_WORDS_FILE = "/NSF-ordlisten/nouns.txt";


    public static List<String> readNorwegianNouns() throws IOException, NoSuchAlgorithmException {
        final List<String> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Round2.class.getResourceAsStream(NORWEGIAN_WORDS_FILE)))) {
            String line;

            while ((line = br.readLine()) != null) {
                String elements[] = line.toLowerCase().split(" ");
                String candidate = elements[0];
                result.add(candidate);
            }
        }
        return result;
    }

    public static List<String> readRumanianNouns() throws
            IOException, NoSuchAlgorithmException {
        final List<String> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Round2.class.getResourceAsStream(RUMANIAN_WORDS_FILE)))) {
            String line;

            while ((line = br.readLine()) != null) {
                String elements[] = line.split(",");
                String candidate = elements[0];
                String type = elements[1];
                if ("s.n.".equals(type.trim())) {
                    result.add(candidate);
                }
            }
        }
        return result;
    }

    public static String hex2Bin(char c) {
        switch (c) {
            case '0':
                return "0000";
            case '1':
                return "0001";
            case '2':
                return "0010";
            case '3':
                return "0011";
            case '4':
                return "0100";
            case '5':
                return "0101";
            case '6':
                return "0110";
            case '7':
                return "0111";
            case '8':
                return "1000";
            case '9':
                return "1001";
            case 'a':
                return "1010";
            case 'b':
                return "1011";
            case 'c':
                return "1100";
            case 'd':
                return "1101";
            case 'e':
                return "1110";
            case 'f':
                return "1111";
            default:
                return "----";
        }
    }

    public static int calcDistance(String hash) {
        int distance = 0;
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

    public static void main(String[] args) throws
            IOException, NoSuchAlgorithmException {

        List<String> all = readNorwegianNouns();
        Collections.shuffle(all);
        List<String> romanian = readRumanianNouns();

        Collections.shuffle(romanian);
        all.addAll(romanian);
        List<String> no1 = new ArrayList<>(all);
        Collections.shuffle(no1);
        all.addAll(no1);
        List<String> all1 = new ArrayList<>();
        all1.addAll(all);
        Collections.shuffle(all1);
        all.addAll(all1);

        List<String> all2 = new ArrayList<>();
        all2.addAll(all);
        Collections.shuffle(all2);

        List<String> all3 = new ArrayList<>();
        all3.addAll(all);
        Collections.shuffle(all1);
        all.addAll(all1);
        all.addAll(all1);
        Collections.shuffle(all);
        doIt(all);

        Collections.shuffle(all);
        doIt(all);

        Collections.shuffle(all);
        doIt(all);

        Collections.shuffle(all);
        doIt(all);

        Collections.shuffle(all);
        doIt(all);

    }

    public static void doIt(List<String> all) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        int bestDistance = 160;
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        for (int i = 0; i < all.size() - 7; i++) {
            List<String> sub = all.subList(i, i + 3);
            List<String> sub1 = all.subList(i, i + 4);
            List<String> sub2 = all.subList(i, i + 5);

            String candidate = StringUtils.join(sub, ' ');
            if (candidate.length() == 34) {
                String hash = calcHash(candidate, sha1);
                int distance = calcDistance(hash);
                //   System.out.println("34 > " + candidate + " d=" + distance);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    System.out.println(candidate + " " + hash + " d=" + distance);
                }
                if (bestDistance == 0) {
                    System.out.println("== MATCH FOUND ==");
                    System.exit(0);
                }
            }
            Collections.shuffle(sub);
            candidate = StringUtils.join(sub, ' ');
            if (candidate.length() == 34) {
                String hash = calcHash(candidate, sha1);
                int distance = calcDistance(hash);
                //   System.out.println("34 > " + candidate + " d=" + distance);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    System.out.println(candidate + " " + hash + " d=" + distance);
                }
                if (bestDistance == 0) {
                    System.out.println("== MATCH FOUND ==");
                    System.exit(0);
                }
            }

            candidate = StringUtils.join(sub1, ' ');
            if (candidate.length() == 34) {
                String hash = calcHash(candidate, sha1);
                int distance = calcDistance(hash);
                //   System.out.println("34 > " + candidate + " d=" + distance);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    System.out.println(candidate + " " + hash + " d=" + distance);
                }
                if (bestDistance == 0) {
                    System.out.println("== MATCH FOUND ==");
                    System.exit(0);
                }
            }

            Collections.shuffle(sub1);
            candidate = StringUtils.join(sub1, ' ');
            if (candidate.length() == 34) {
                String hash = calcHash(candidate, sha1);
                int distance = calcDistance(hash);
                //   System.out.println("34 > " + candidate + " d=" + distance);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    System.out.println(candidate + " " + hash + " d=" + distance);
                }
                if (bestDistance == 0) {
                    System.out.println("== MATCH FOUND ==");
                    System.exit(0);
                }
            }

            candidate = StringUtils.join(sub2, ' ');
            if (candidate.length() == 34) {
                String hash = calcHash(candidate, sha1);
                int distance = calcDistance(hash);
                //   System.out.println("34 > " + candidate + " d=" + distance);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    System.out.println(candidate + " " + hash + " d=" + distance);
                }
                if (bestDistance == 0) {
                    System.out.println("== MATCH FOUND ==");
                    System.exit(0);
                }
            }

            Collections.shuffle(sub2);
            candidate = StringUtils.join(sub2, ' ');
            if (candidate.length() == 34) {
                String hash = calcHash(candidate, sha1);
                int distance = calcDistance(hash);
                //   System.out.println("34 > " + candidate + " d=" + distance);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    System.out.println(candidate + " " + hash + " d=" + distance);
                }
                if (bestDistance == 0) {
                    System.out.println("== MATCH FOUND ==");
                    System.exit(0);
                }
            }
        }

    }
}
