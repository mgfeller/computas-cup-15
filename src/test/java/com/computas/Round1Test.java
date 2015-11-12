package com.computas;

import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mgf on 12.11.2015.
 */
public class Round1Test {

    @Test
    public void test2() {
        System.err.println(Integer.toBinaryString('f'));
    }


    @Test
    public void test() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        String hash = Round1.calcHash("mann barbat", sha1);
        System.out.println("Hash: " + hash);
        int d = Round1.calcDistance(hash);
        Assert.assertEquals(81, d);
    }
}
