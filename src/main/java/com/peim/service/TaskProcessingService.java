package com.peim.service;

import com.peim.hash.HashAlgorithm;
import com.peim.hash.HashAlgorithmFactory;
import com.peim.model.Task;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service("taskProcessingService")
public class TaskProcessingService {

    public ListenableFuture<String> process(Task task) {

        HashAlgorithm algo = HashAlgorithmFactory.of(task.getAlgo());

        return new ListenableFuture<String>() {
            @Override
            public void addCallback(ListenableFutureCallback<? super String> callback) {
                try {
                    String result = algo.hash(task);
                    callback.onSuccess(result);
                } catch (Exception e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void addCallback(SuccessCallback<? super String> successCallback, FailureCallback failureCallback) {
                try {
                    String result = algo.hash(task);
                    successCallback.onSuccess(result);
                } catch (Exception e) {
                    failureCallback.onFailure(e);
                }
            }

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public String get() throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };
    }
}
