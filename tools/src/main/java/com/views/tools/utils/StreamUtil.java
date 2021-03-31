package com.views.tools.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description: 流操作工具类
 */
@SuppressWarnings("unused")
public class StreamUtil {
    /**
     * 将流另存为文件
     */
    public void streamSaveAsFile(InputStream is, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int    len;
            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
                if (null != fos) {
                    fos.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new RuntimeException(e2);
            }
        }
    }

    /**
     * 文件流读入字符串中
     */
    static public String streamToString(InputStream in) throws IOException {
        StringBuilder builder = new StringBuilder();
        byte[]        bytes   = new byte[4096];
        for (int n; (n = in.read(bytes)) != -1; ) {
            builder.append(new String(bytes, 0, n));
        }
        return builder.toString();
    }

    /**
     * 文件流转字节数组
     */
    public static byte[] stream2Byte(InputStream is) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int                   len;
        byte[]                bytes  = new byte[1024];
        while ((len = is.read(bytes, 0, bytes.length)) != -1) {
            stream.write(bytes, 0, len);
        }
        return stream.toByteArray();
    }

    /**
     * InputStream 转为 byte
     */
    public static byte[] inputStream2Byte(InputStream inStream) throws Exception {
        int count = 0;
        while (count == 0) {
            count = inStream.available();
        }
        byte[] bytes = new byte[count];
        inStream.read(bytes);
        return bytes;
    }

    /**
     * byte 转为 InputStream
     */
    public static InputStream byte2InputStream(byte[] bytes) throws Exception {
        return new ByteArrayInputStream(bytes);
    }
}