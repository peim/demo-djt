package com.peim.hash;

public class HashAlgorithmFactory {

    private final static String MD5 = "md5";
    private final static String SHA1 = "sha-1";
    private final static String SHA256 = "sha-256";

    private HashAlgorithmFactory(){}

    public static HashAlgorithm of(String name) {
        HashAlgorithm hashAlgorithm;
        if (name.equals(MD5)) {
            hashAlgorithm = new MD5Algorithm();
        } else if (name.equals(SHA1)) {
            hashAlgorithm = new SHA1Algorithm();
        } else if (name.equals(SHA256)) {
            hashAlgorithm = new SHA256Algorithm();
        } else {
            throw new IllegalArgumentException();
        }
        return hashAlgorithm;
    }
}
