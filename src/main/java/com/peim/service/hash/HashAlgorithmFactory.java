package com.peim.service.hash;

public class HashAlgorithmFactory {

    private final static String MD5 = "md5";
    private final static String SHA1 = "sha-1";
    private final static String SHA256 = "sha-256";

    private HashAlgorithmFactory(){}

    public static HashAlgorithm of(String name) {
        switch (name) {
            case MD5:
                return new MD5Algorithm();
            case SHA1:
                return new SHA1Algorithm();
            case SHA256:
                return new SHA256Algorithm();
            default:
                throw new IllegalArgumentException();
        }
    }
}
