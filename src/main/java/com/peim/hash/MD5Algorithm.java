package com.peim.hash;

import com.peim.model.Task;

class MD5Algorithm implements HashAlgorithm {

    @Override
    public String hash(Task task) {
        try {
            Thread.sleep(2000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "md5-hash";
    }
}
