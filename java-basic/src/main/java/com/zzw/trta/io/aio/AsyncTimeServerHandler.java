package com.zzw.trta.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhaozhiwei
 * @description
 * @date 2018/9/15 11:16
 */
public class AsyncTimeServerHandler implements Runnable {

    private int port;
    private CountDownLatch countDownLatch;
    private AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The time server is start in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }

    class AcceptCompletionHandler<V, A> implements CompletionHandler<V, A>{

        @Override
        public void completed(V result, A attachment) {

        }

        @Override
        public void failed(Throwable exc, A attachment) {

        }
    }
}
