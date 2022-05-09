package com.wowoohr.calculators.common.security;
import com.wowoohr.core.common.core.utils.Exceptions;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

/**
 * 封装各种格式的编码解码工具类.
 *
 * 1.Commons-Codec的 hex/base64 编码
 * 2.自制的base62 编码
 * 3.Commons-Lang的xml/html escape
 * 4.JDK提供的URLEncoder
 */
public abstract class EncoderUtils {

    private static final String DEFAULT_URL_ENCODING = "UTF-8";
    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    public static final String KEY_SHA = "SHA";

    /**
     *  MD5编码
     * @param str {String}
     * @return {String}
     */
    public static String encodeMD5(String str) {
        return Md5Utils.hash(str);
    }

    /**
     * Hex编码.
     */
    public static String encodeHex(byte[] str) {
        return Hex.encodeHexString(str);
    }

    /**
     * Hex解码.
     */
    public static byte[] decodeHex(String str) {
        try {
            return Hex.decodeHex(str.toCharArray());
        } catch (DecoderException e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(byte[] str) {
        return Base64.encodeBase64String(str);
    }

    /**
     * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
     */
    public static String encodeUrlSafeBase64(byte[] str) {
        return Base64.encodeBase64URLSafeString(str);
    }

    /**
     * Base64解码.
     * @return byte[]
     */
    public static byte[] decodeBase64(String str) {
        return Base64.decodeBase64(str);
    }

    /**
     * Base64解码
     * @param str 输入
     * @return 返回解码后的字符串
     * @throws UnsupportedEncodingException
     */
    public static String decodeBase64ToStr (String str) throws UnsupportedEncodingException {
        return decodeBase64ToStr(str, DEFAULT_URL_ENCODING);
    }

    /**
     * Base64解码
     * @param str 输入
     * @param encodeType 返回转化后的字符串
     * @return 返回解码后的字符串
     * @throws UnsupportedEncodingException
     */
    public static String decodeBase64ToStr (String str, String encodeType) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(encodeType)) {
            encodeType = DEFAULT_URL_ENCODING;
        }
        return new String(Base64.decodeBase64(str), DEFAULT_URL_ENCODING);
    }

    /**
     * Base62编码。
     */
    public static String encodeBase62(byte[] str) {
        char[] chars = new char[str.length];
        for (int i = 0; i < str.length; i++) {
            chars[i] = BASE62[(str[i] & 0xFF) % BASE62.length];
        }
        return new String(chars);
    }

    /**
     * Html 转码.
     */
    public static String escapeHtml(String html) {
        return StringEscapeUtils.escapeHtml4(html);
    }

    /**
     * Html 解码.
     */
    public static String unescapeHtml(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }

    /**
     * Xml 转码.
     */
    public static String escapeXml(String xml) {
        return StringEscapeUtils.escapeXml11(xml);
    }

    /**
     * Xml 解码.
     */
    public static String unescapeXml(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    /**
     * URL 编码, Encode默认为UTF-8.
     */
    public static String urlEncode(String part) {
        try {
            return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     */
    public static String urlDecode(String part) {

        try {
            return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * SHA加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encodeSHA(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);
        return sha.digest();
    }

    /**
     * Turns array of bytes into string
     * @param buf Array of bytes to convert to hex string
     * @return Generated hex string
     */
    public static String asHex(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }

}