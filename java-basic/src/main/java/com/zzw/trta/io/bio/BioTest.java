package com.zzw.trta.io.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO测试类
 *
 * @author zhaozhiwei
 * @date 2019/3/23 0:29
 */
public class BioTest {

    public static void main(String[] args) throws Exception {

        //1、创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
        ServerSocket serverSocket = new ServerSocket(8080);
        //2、调用accept()方法开始监听，等待客户端的连接
        Socket socket = serverSocket.accept();
        //3、获取输入流，并读取客户端信息
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String info = null;
        while ((info = br.readLine()) != null && "".equals(info)) {
            System.out.println(info);
        }
        //4、获取输出流，响应客户端的请求
        OutputStream os = socket.getOutputStream();
        String response = "HTTP/1.1 200 OK\n" +
                "Content-Type: text/html\n" +
                "\r\n" +
                "<html><body>" +
                "Hello World!" +
                "</body></html>";
        os.write(response.getBytes());
        //5、关闭资源
        os.close();
        br.close();
        isr.close();
        is.close();
        socket.close();
        serverSocket.close();
    }
}
