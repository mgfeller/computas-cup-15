package com.computas;

import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mgf on 12.11.2015.
 */
public class Round2Test {

    @Test
    public void test2() {
        System.err.println(Integer.toBinaryString('f'));
    }


    @Test
    public void test() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        String hash = Round2.calcHash("mann barbat kvinne consfințită", sha1);
        System.out.println("Hash: " + hash);
        int d = Round2.calcDistance(hash);
        Assert.assertEquals(86, d);
    }
}
