package com.peim.service.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class MD5Algorithm extends HashAlgorithm {

    @Override
    protected MessageDigest getMessageDigest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("MD5");
    }
}
