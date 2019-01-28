package com.zzw.trta.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zhaozhiwei
 * @description
 * @date 2018/9/11 下午2:57
 */
public class MultiplexerTimeServer implements Runnable {

    private Selector selector;
    private ServerSocketChannel ssc;
    private volatile boolean stop = false;

    public MultiplexerTimeServer(int port) {
        try {
            stop = false;
            selector = Selector.open();
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.socket().bind(new InetSocketAddress("127.0.0.1", port), 1024);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start on port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.stop = true;
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel sc = ssc.accept();
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);
        }
        if (key.isReadable()) {
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer bf = ByteBuffer.allocate(1024);
            int read = sc.read(bf);
            if (read > 0) {
                bf.flip();
                byte[] bytes = new byte[bf.remaining()];
                bf.get(bytes);
                String body = new String(bytes, StandardCharsets.UTF_8);
                System.out.println("The time server receive order : " + body);
                String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? LocalDateTime.now().toString() : "BAD QUERY";
                doWrite(sc, currentTime);
            } else if (read < 0) {
                key.cancel();
                sc.close();
            }
        }
    }

    private void doWrite(SocketChannel sc, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer bf = ByteBuffer.allocate(bytes.length);
            bf.put(bytes);
            bf.flip();
            sc.write(bf);
        }
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        MultiplexerTimeServer mt = new MultiplexerTimeServer(port);
        new Thread(mt, "NIO-TIMESERVER-001").start();
    }
}
