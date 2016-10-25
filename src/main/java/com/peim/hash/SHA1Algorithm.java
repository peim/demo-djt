package com.peim.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class SHA1Algorithm extends HashAlgorithm {

    @Override
    protected MessageDigest getMessageDigest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-1");
    }
}
