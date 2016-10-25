package com.peim.hash;

import com.peim.model.Task;

class SHA256Algorithm implements HashAlgorithm {

    @Override
    public String hash(Task task) {
        try {
            Thread.sleep(3000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "sha-256-hash";
    }
}
