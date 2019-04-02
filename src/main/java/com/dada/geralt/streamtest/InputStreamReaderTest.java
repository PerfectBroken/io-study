package com.dada.geralt.streamtest;

import sun.nio.cs.StreamDecoder;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

public class InputStreamReaderTest {

    /*
     基于字节的I/O操作接口
     InputStream
     OutputStream

     基于字符的I/O操作接口
     Writer
     Reader

     字节与字符接口转换
     StreamDecoder？
     StreamEncoder？
     */
    public static void main(String[] args) throws Exception{
//        readDirected();
        readFromReader();
    }

    public static void readFromReader() throws Exception {
        FileInputStream fis = new FileInputStream("/data0/logs/staff-online/staff-online.log");
        InputStreamReader reader = new InputStreamReader(fis, "utf-8");
        char[] c = new char[2500];
        reader.read(c);
        System.out.println(new String(c));
    }

    public static void readDirected() throws Exception{
        FileInputStream fis = new FileInputStream("/data0/logs/staff-online/staff-online.log");
        StringBuilder stringBuilder = new StringBuilder();
        int c;
        while((c=fis.read()) != -1) {
            stringBuilder.append((char)c);
        }
        System.out.println(stringBuilder.toString());
    }

}
