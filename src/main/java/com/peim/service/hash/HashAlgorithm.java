package com.peim.service.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class HashAlgorithm {

    public String hash(String src) {
        String hash;
        try {
            MessageDigest md = getMessageDigest();
            md.update(src.getBytes());
            byte byteData[] = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte element : byteData) {
                sb.append(Integer.toString((element & 0xff) + 0x100, 16).substring(1));
            }
            hash = sb.toString();

            Thread.sleep(10000);
        }
        catch (Exception e) {
            hash = e.getMessage();
        }
        return hash;
    }

    protected abstract MessageDigest getMessageDigest() throws NoSuchAlgorithmException;
}
