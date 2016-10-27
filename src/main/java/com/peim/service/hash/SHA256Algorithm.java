package com.peim.service.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class SHA256Algorithm extends HashAlgorithm {

    @Override
    protected MessageDigest getMessageDigest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-256");
    }
}
