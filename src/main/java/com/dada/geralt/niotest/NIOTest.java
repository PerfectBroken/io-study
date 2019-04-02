package com.dada.geralt.niotest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOTest {

    public static void main(String[] args) throws Exception{
        NIOTest test = new NIOTest();
        test.selector();
//        test.bioTest();
    }

    public void bioTest() throws Exception {
        /*
         bio
         */
        ServerSocket socket = new ServerSocket(8081);
        while (true) {
            Socket s = socket.accept();
            s.getChannel();
        }
    }

    public void selector() throws IOException {
        /*
         nio
         */
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//设为非阻塞模式
        ssc.socket().bind(new InetSocketAddress(8081));
        ssc.register(selector, SelectionKey.OP_ACCEPT);//注册监听事件
        while (true) {
            //当有注册的事件到达时，方法返回，否则阻塞。
            selector.select();
            Set selectedKeys = selector.selectedKeys();
            Iterator it = selectedKeys.iterator();
            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();
                if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                    ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssChannel.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                    it.remove();
                } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    while (true) {
                        buffer.clear();
                        int n = sc.read(buffer);//读取数据
                        if (n <= 0) {
                            break;
                        }
                        byte[] a = buffer.array();
                        StringBuilder sb = new StringBuilder();
                        for(int i = 0; i < a.length && a[i+1] != 0; i++) {
                            sb.append((char)a[i]);
                        }
                        System.out.println(sb.toString());

                        buffer.flip();
                    }
                    it.remove();
                }
            }
        }

    }
}
