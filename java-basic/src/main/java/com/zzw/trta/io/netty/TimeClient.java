package com.zzw.trta.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

/**
 * @author zhaozhiwei
 * @description
 * @date 2018/9/17 0:00
 */
@Slf4j
public class TimeClient {

    public void connect(int port, String host) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            ChannelFuture cf = bootstrap.connect(host, port).sync();
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws URISyntaxException {
        System.out.println(TimeClientHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        System.out.println(TimeClientHandler.class.getProtectionDomain().getCodeSource().getLocation());
//        new TimeClient().connect(9090, "127.0.0.1");
    }

    private class TimeClientHandler extends ChannelInboundHandlerAdapter {

        private final ByteBuf firstMessage;

        public TimeClientHandler() {
            firstMessage = Unpooled.copiedBuffer("QUERY TIME ORDER", StandardCharsets.UTF_8);
        }

        @Override
        public void channelRead(ChannelHandlerContext context, Object o) {
            ByteBuf buf = (ByteBuf) o;
            String body = buf.toString(StandardCharsets.UTF_8);
            System.out.println("Now is : " + body);
        }

        @Override
        public void channelActive(ChannelHandlerContext context) {
            context.writeAndFlush(firstMessage);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext context){
            context.close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
            log.error("TimeClientHandler.error", cause);
            cause.printStackTrace();
            context.close();
        }
    }
}
