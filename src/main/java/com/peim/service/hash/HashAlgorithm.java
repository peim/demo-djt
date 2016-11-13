package com.peim.service.hash;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class HashAlgorithm implements Serializable {

    public String hash(String line) {
        String hash;
        try {
            MessageDigest md = getMessageDigest();
            md.update(line.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte element : md.digest()) {
                sb.append(Integer.toString((element & 0xff) + 0x100, 16).substring(1));
            }
            hash = sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            hash = e.getMessage();
        }
        return hash;
    }

    protected abstract MessageDigest getMessageDigest() throws NoSuchAlgorithmException;
}
