package com.wowoohr.calculators.common.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {

    private static final Logger logger = LoggerFactory.getLogger(Md5Utils.class);


    private static byte[] md5(String s) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes("UTF-8"));
            return algorithm.digest();
        } catch (Exception e) {
            logger.warn("MD5 Error...", e);
        }
        return null;
    }

    private static String toHex(byte hash[]) {
        if (hash == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String hash(String s) {
        try {
            return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
        } catch (Exception e) {
            logger.warn("not supported charset...{}", e);
            return s;
        }
    }
    
    public static String getMd5ByFile(File file) {
        String value = null;
        FileInputStream in = null;
        
        try {
            in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            logger.warn("对文件做MD5摘要运算失败:", e);
        } finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.warn("对文件做MD5摘要运算失败:", e);
                }
            }
        }
        return value;
    }
    
    /**
     * 把明文的字节码变成摘要字节码
     * @param obj 明文的字节码
     * @return 摘要的字节码
     */
    public static byte[] encrypt(byte[] obj){
        byte[] encodes = null ;
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(obj);
            encodes = md5.digest();
        } catch (NoSuchAlgorithmException e){
            logger.warn("encrypt():", e);
        }
        return encodes ;
    }

    /**
     * 小写字符串
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encrypt(String str) throws UnsupportedEncodingException {
        return encrypt(str, false);
    }

    /**
     * 大写字符串
     * @param str {String} 需加密字段
     * @param bUpperCase {boolean} 是否需要返回大写内容
     * @return {String}
     * @throws UnsupportedEncodingException
     */
    public static String encrypt(String str, boolean bUpperCase) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        byte[] byteEncodes = encrypt(str.getBytes("UTF-8"));
        StringBuilder buf = new StringBuilder("");
        int i;
        for (int offset = 0; offset < byteEncodes.length; offset++) {
            i = byteEncodes[offset];
            if(i<0) {
                i += 256;
            }
            if(i<16) {
                buf.append("0");
            }
            if (bUpperCase) {
                buf.append(Integer.toHexString(i).toUpperCase());
            } else {
                buf.append(Integer.toHexString(i));
            }

        }
        return buf.toString();
    }

}
