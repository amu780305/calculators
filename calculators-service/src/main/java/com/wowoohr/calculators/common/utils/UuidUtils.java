package com.wowoohr.calculators.common.utils;

import com.wowoohr.calculators.common.security.Base58;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.UUID;

/**
 * UUID工具类，见原始的UUID36位通过Base64/58转码获得更短的UUID串，仅22位；<br/>
 * 1.Base64<br/>
 * 由于Base64编码使用的字符包括大小写字母各26个，加上10个数字，和加号“+”，斜杠“/”，一共64个字符。所以才有Base64名字的由来。Base64相当于使用64进制来表示数据，相同长度位数的情况下要比16进制表示更多的内容。<br>
 * 由于UUID标准数据总共是128-bit，所以我们就可以对这个128-bit重新进行Base64编码。<br/>
 * 128-bit的UUID在Java中表示为两个long型数据，可以采用java.util.UUID中的getLeastSignificantBits与getMostSignificantBits分别获得两个long（64-bit）。<br/>
 * 再通过Base64转码就可以获得我们所要的UUID。
 * <br/>
 * <br/>
 * 2.Base58 --> 比特币使用实例 https://en.bitcoin.it/wiki/Base58Check_encoding<br/>
 * Base58编码使用的字符在Base64的基础上剔除了数字0，大写字母I，大写字母O，小写字母l，和加号“+”，斜杠“/”，共58个字符；<br/>
 * 特点：<br/>
 * <pre>
 *     A.该编码的内容由于取出了这个几个字符，在阅读的时候减小了混淆错误概率;
 *     B.这个编码是一个混淆编码，看上去像Base64,但是其实不是。编码效率又高于Base32;
 * </pre>
 *
 */
public abstract class UuidUtils {

    /**
     * 原始UUID串
     * @return {String}
     */
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 获取新的Base64压缩的UUID
     * @return {String} exp:PJCNsMmjQda251IVbEBfRw
     */
    public static String compressedBase64Uuid() {
        UUID uuid = UUID.randomUUID();
        return compressedBase64Uuid(uuid);
    }

    /**
     * 传入一个UUID字符串，压缩Base64
     * @param sUuid {String} exp: d1866a16-87b2-416c-aab1-8d93b4f2e52c
     * @return {String} exp:PJCNsMmjQda251IVbEBfRw
     */
    public static String compressedBase64Uuid(String sUuid) {
        UUID uuid = UUID.fromString(sUuid);
        return compressedBase64Uuid(uuid);
    }

    /**
     * 传入一个UUID，返回Base64压缩结果
     * @param oUuid {UUID}
     * @return {String} exp:PJCNsMmjQda251IVbEBfRw
     */
    public static String compressedBase64Uuid(UUID oUuid) {
        byte[] byUuid = new byte[16];
        long least = oUuid.getLeastSignificantBits();
        long most = oUuid.getMostSignificantBits();
        longToBytes(most, byUuid, 0);
        longToBytes(least, byUuid, 8);
        return Base64.encodeBase64URLSafeString(byUuid);
    }


    /**
     * 将压缩的ID解压为正常UUID字符串
     * @param compressedUuid {String}
     * @return {String}
     */
    public static String unCompressBase64Uuid(String compressedUuid) {
        if (compressedUuid.length() != 22) {
            throw new IllegalArgumentException("Invalid uuid!");
        }
        byte[] byUuid = Base64.decodeBase64(compressedUuid + "==");
        long most = bytesToLong(byUuid, 0);
        long least = bytesToLong(byUuid, 8);
        UUID uuid = new UUID(most, least);
        return uuid.toString();
    }

    ///////////////////////////////////////// Base58 压缩 /////////////////////////////////////////

    /**
     * 获取新的Base58压缩的UUID
     * @return {String} exp:PJCNsMmjQda251IVbEBfRw
     */
    public static String compressedBase58Uuid() {
        UUID uuid = UUID.randomUUID();
        return compressedBase58Uuid(uuid);
    }

    /**
     * 传入一个UUID字符串，压缩Base58
     * @param sUuid {String} exp: d1866a16-87b2-416c-aab1-8d93b4f2e52c
     * @return {String} exp:PJCNsMmjQda251IVbEBfRw
     */
    public static String compressedBase58Uuid(String sUuid) {
        UUID uuid = UUID.fromString(sUuid);
        return compressedBase58Uuid(uuid);
    }

    /**
     * 传入一个UUID，返回Base58压缩结果
     * @param oUuid {UUID}
     * @return {String} exp:PJCNsMmjQda251IVbEBfRw
     */
    public static String compressedBase58Uuid(UUID oUuid) {
        byte[] byUuid = new byte[16];
        long least = oUuid.getLeastSignificantBits();
        long most = oUuid.getMostSignificantBits();
        longToBytes(most, byUuid, 0);
        longToBytes(least, byUuid, 8);
        return Base58.encode(byUuid);
    }


    /**
     * 将压缩的ID解压为正常UUID字符串
     * @param compressedUuid {String}
     * @return {String}
     */
    public static String unCompressBase58Uuid(String compressedUuid) throws Exception {
        if (compressedUuid.length() != 22) {
            throw new IllegalArgumentException("Invalid uuid!");
        }
        byte[] byUuid = Base58.decode(compressedUuid);
        long most = bytesToLong(byUuid, 0);
        long least = bytesToLong(byUuid, 8);
        UUID uuid = new UUID(most, least);
        return uuid.toString();
    }


    private static void longToBytes(long value, byte[] bytes, int offset) {
        for (int i = 7; i > -1; i--) {
            bytes[offset++] = (byte) ((value >> 8 * i) & 0xFF);
        }
    }

    private static long bytesToLong(byte[] bytes, int offset) {
        long value = 0;
        for (int i = 7; i > -1; i--) {
            value |= (((long) bytes[offset++]) & 0xFF) << 8 * i;
        }
        return value;
    }

    /**
     * 原始UUID串,去掉“-”
     * @return {String}
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-","");
    }

}
