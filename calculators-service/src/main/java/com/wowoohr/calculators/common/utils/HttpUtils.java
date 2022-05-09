package com.wowoohr.calculators.common.utils;

import com.alibaba.fastjson.JSON;
import com.wowoohr.calculators.common.domain.HeaderEnum;
import com.wowoohr.calculators.form.AdvertisingForm;
import com.wowoohr.core.common.core.error.ServiceException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class HttpUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 完整时间 yyyy-MM-dd HH:mm:ss
     */
    public static final String simple = "yyyy-MM-dd HH:mm:ss";
    public static final String KEY_HEADER_REQ_ID = "random-numbers";

    private static PoolingHttpClientConnectionManager cm;

    static {
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(50);
        new Thread(new ConnectionPoolCheckTask()).start();
    }

    public static CloseableHttpClient getHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(30000)
                .setConnectTimeout(30000)
                .setConnectionRequestTimeout(30000)
                .setStaleConnectionCheckEnabled(true)
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(requestConfig)
                .setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {

                    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                        HeaderElementIterator it = new BasicHeaderElementIterator(
                                response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                        while (it.hasNext()) {
                            HeaderElement he = it.nextElement();
                            String param = he.getName();
                            String value = he.getValue();
                            if (value != null && param.equalsIgnoreCase("timeout")) {
                                try {
                                    return Long.parseLong(value) * 1000;
                                } catch (NumberFormatException ignore) {
                                }
                            }
                        }
                        return 5 * 1000;
                    }

                }).build();

        return httpClient;
    }

    /**
     * JsonPost请求
     *
     * @param obj        json对象
     * @param requestUrl 请求地址
     * @return
     * @throws Exception
     */
    public static String httpClient(Object obj, String requestUrl, AdvertisingForm header) {
        if (logger.isDebugEnabled()) {
//            logger.debug("httpclient缓存概况---------------->" + cm.getTotalStats().toString());
        }
        CloseableHttpClient httpclient = getHttpClient();
        try {
            HttpPost httpPost = new HttpPost(requestUrl);
            Date beginDate = Calendar.getInstance().getTime();
//            logger.info("远程调用参数:" + JSON.toJSONString(obj) + ",URL:" + requestUrl + ", 远程调用开始时间: " + parseStr(beginDate));
            StringEntity postingString = new StringEntity(JSON.toJSONString(obj), "UTF-8");
            httpPost.setEntity(postingString);
            httpPost.setHeader("Content-Type", "application/json");

            httpPost.addHeader("Access-Control-Allow-Origin", "*");//跨域设置

            httpPost.addHeader("User-Agent","httpclient/renliwo");

            if(StringUtils.isNoneBlank(header.getUserId())) {
                httpPost.addHeader("userId", header.getUserId());
            }
            if(StringUtils.isNoneBlank(header.getPermit())) {
                httpPost.addHeader("permit", header.getPermit());
            }
            if(StringUtils.isNoneBlank(header.getAuthCode())) {
                httpPost.addHeader("authCode", header.getAuthCode());
            }
            if(StringUtils.isNoneBlank(header.getAppId())) {
                httpPost.addHeader("appId", header.getAppId());
            }

            String reqId = MDC.get("TRACE_ID");
            if (StringUtils.isNotEmpty(reqId)) {
                httpPost.addHeader(KEY_HEADER_REQ_ID,reqId);
            }

            // 执行请求
            HttpResponse response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            // 返回码异常直接返回系统异常
            if (statusCode != 200 && statusCode != 204) {
                response.getEntity().getContent().close();
                logger.error("请求异常:HttpCode:" + statusCode + ",URL:" + requestUrl);
                throw new RequestAbortedException("请求失败");
            }
            if (statusCode == 204) {
                return null;
            }
            String token = EntityUtils.toString(response.getEntity());
            Long diff = Calendar.getInstance().getTime().getTime() - beginDate.getTime();
            if (diff > 1000 * 3) {
//                logger.error("error远程调用时间过长 远程返回参数:" + token + ",URL:" + requestUrl + ", 远程调用结束时间: " + parseStr(Calendar.getInstance().getTime()));
            } else {
//                logger.info("远程返回参数:" + token + ",URL:" + requestUrl + ", 远程调用结束时间: " + parseStr(Calendar.getInstance().getTime()));
            }
            return token;
        } catch (ServiceException e) {
            logger.error("httpUtils=================================ServiceException:" + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("httpUtils=================================Exception:" + ExceptionUtils.getFullStackTrace(e.getCause()));
            throw new ServiceException(HeaderEnum.WEB_MSG.getMsg());
        }
    }


    /**
     * 发xml字符串请求
     *
     * @param xmlStr     请求xml字符串
     * @param requestUrl 请求地址
     * @return
     */
    public static String httpClientXml(String xmlStr, String requestUrl) {
        if (logger.isDebugEnabled()) {
            logger.debug("httpclient缓存概况---------------->" + cm.getTotalStats().toString());
        }
        CloseableHttpClient httpclient = getHttpClient();

        try {
            HttpPost httpPost = new HttpPost(requestUrl);
            Date beginDate = Calendar.getInstance().getTime();
            logger.info("远程调用参数:" + xmlStr + ",URL:" + requestUrl + ", 远程调用开始时间: " + parseStr(beginDate));
            StringEntity postingString = new StringEntity(xmlStr, "UTF-8");
            httpPost.setEntity(postingString);
            httpPost.setHeader("Content-Type", "text/xml");

            httpPost.addHeader("Access-Control-Allow-Origin", "*");//跨域设置

            httpPost.addHeader("User-Agent","httpclient/renliwo");

            String reqId = MDC.get("TRACE_ID");
            if (StringUtils.isNotEmpty(reqId)) {
                httpPost.addHeader(KEY_HEADER_REQ_ID,reqId);
            }

            // 执行请求
            HttpResponse response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            // 返回码异常直接返回系统异常
            if (statusCode != 200 && statusCode != 204) {
                response.getEntity().getContent().close();
                logger.error("请求异常:HttpCode:" + statusCode + ",URL:" + requestUrl);
                throw new RequestAbortedException("请求失败");
            }
            if (statusCode == 204) {
                return null;
            }
            String token = EntityUtils.toString(response.getEntity());
            Long diff = Calendar.getInstance().getTime().getTime() - beginDate.getTime();
            if (diff > 1000 * 3) {
                logger.error("error远程调用时间过长 远程返回参数:" + token + ",URL:" + requestUrl + ", 远程调用结束时间: " + parseStr(Calendar.getInstance().getTime()));
            } else {
                logger.info("远程返回参数:" + token + ",URL:" + requestUrl + ", 远程调用结束时间: " + parseStr(Calendar.getInstance().getTime()));
            }
            return token;
        } catch (ServiceException e) {
            logger.error("httpUtils=================================ServiceException:" + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("httpUtils=================================Exception:" + ExceptionUtils.getFullStackTrace(e.getCause()));
            throw new ServiceException(HeaderEnum.WEB_MSG.getMsg());
        }
    }

    public static String doHttpPost(String url, Map<String, Object> paramMap) {
        StringEntity stringEntity = null;
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        if (paramMap != null) {
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                NameValuePair kv = new BasicNameValuePair(key, value == null ? "" : value.toString());
                paramList.add(kv);
            }
        }
        try {
            stringEntity = new UrlEncodedFormEntity(paramList, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("logId:" + ",doHttpPost.UnsupportedEncodingException=", e);
        }
        return _httpPost(url, stringEntity);
    }

    private static String _httpPost(String url, HttpEntity entity) {
        CloseableHttpClient httpclient = getHttpClient();

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Access-Control-Allow-Origin", "*");//跨域设置

        httpPost.addHeader("User-Agent","httpclient/renliwo");

        String reqId = MDC.get("TRACE_ID");
        if (StringUtils.isNotEmpty(reqId)) {
            httpPost.addHeader(KEY_HEADER_REQ_ID,reqId);
        }

        httpPost.setEntity(entity);

        try {
            Date beginDate = Calendar.getInstance().getTime();
            HttpResponse response = httpclient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();

            // 返回码异常直接返回系统异常
            if (statusCode != 200 && statusCode != 204) {
                response.getEntity().getContent().close();
                logger.error("请求异常:HttpCode:" + statusCode + ",URL:" + url);
                throw new RequestAbortedException("请求失败");
            }
            if (statusCode == 204) {
                return null;
            }
            String ret = EntityUtils.toString(response.getEntity());
            Long diff = Calendar.getInstance().getTime().getTime() - beginDate.getTime();
            if (diff > 1000 * 3) {
                logger.error("error远程调用时间过长 远程返回参数:" + ret + ",URL:" + url + ", 远程调用结束时间: " + parseStr(Calendar.getInstance().getTime()));
            } else {
                logger.info("远程返回参数:" + ret + ",URL:" + url + ", 远程调用结束时间: " + parseStr(Calendar.getInstance().getTime()));
            }
            return ret;
        } catch (IOException e) {
            throw new ServiceException();
        } catch (Exception e) {
            throw new ServiceException();
        }
    }


    /**
     * 关闭连接池连接
     */
    private static class ConnectionPoolCheckTask implements Runnable {
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100000);
                    cm.closeExpiredConnections();
                    cm.closeIdleConnections(30, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 使用预设格式将DATE转为String
     */
    public static String parseStr(Date date) {
        DateFormat df = new SimpleDateFormat(simple);
        return df.format(date);
    }


    /**
     * JsonPost请求,返回字节数组
     * todo//关闭 wire.log日志,它会把所有的二进制数据输入到磁盘中
     *
     * @param obj        json对象
     * @param requestUrl 请求地址
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] httpClient2bytes(Object obj, String requestUrl) {
        if (logger.isDebugEnabled()) {
            logger.debug("httpclient缓存概况---------------->" + cm.getTotalStats().toString());
        }
//        CloseableHttpClient httpclient = getHttpClient();

        CloseableHttpClient httpClient = getCloseableHttpClient(60000);
        try {
            HttpPost httpPost = new HttpPost(requestUrl);
            Date beginDate = Calendar.getInstance().getTime();
            logger.info("远程调用参数:" + JSON.toJSONString(obj) + ",URL:" + requestUrl + ", 远程调用开始时间: " + parseStr(beginDate));
            StringEntity postingString = new StringEntity(JSON.toJSONString(obj), "UTF-8");
            httpPost.setEntity(postingString);
            httpPost.setHeader("Content-Type", "application/json");

            httpPost.addHeader("Access-Control-Allow-Origin", "*");//跨域设置

            httpPost.addHeader("User-Agent","httpclient/renliwo");

            String reqId = MDC.get("TRACE_ID");
            if (StringUtils.isNotEmpty(reqId)) {
                httpPost.addHeader(KEY_HEADER_REQ_ID,reqId);
            }

            // 执行请求
            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            // 返回码异常直接返回系统异常
            if (statusCode != 200 && statusCode != 204) {
                response.getEntity().getContent().close();
                logger.error("请求异常:HttpCode:" + statusCode + ",URL:" + requestUrl);
                throw new RequestAbortedException("请求失败");
            }
            if (statusCode == 204) {
                return null;
            }
            byte[] bytes = EntityUtils.toByteArray(response.getEntity());
            Long diff = Calendar.getInstance().getTime().getTime() - beginDate.getTime();
            if (diff > 10000 * 6) {
                logger.error("error远程调用时间过长 远程返回参数:字节数据省略," + ",URL:" + requestUrl + ", 远程调用结束时间: " + parseStr(Calendar.getInstance().getTime()));
            } else {
                logger.info("远程返回参数:字节数据省略" + ",URL:" + requestUrl + ", 远程调用结束时间: " + parseStr(Calendar.getInstance().getTime()));
            }
            return bytes;
        } catch (ServiceException e) {
            logger.error("httpUtils=================================ServiceException:" + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("httpUtils=================================Exception:" + ExceptionUtils.getFullStackTrace(e.getCause()));
            throw new ServiceException(HeaderEnum.WEB_MSG.getMsg());
        }
    }

    //设置参数化设置超时时间
    private static CloseableHttpClient getCloseableHttpClient(int timeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout)
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setStaleConnectionCheckEnabled(true)
                .build();

        return HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(requestConfig)
                .setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {

                    @Override
                    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                        HeaderElementIterator it = new BasicHeaderElementIterator(
                                response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                        while (it.hasNext()) {
                            HeaderElement he = it.nextElement();
                            String param = he.getName();
                            String value = he.getValue();
                            if (value != null && param.equalsIgnoreCase("timeout")) {
                                try {
                                    return Long.parseLong(value) * 1000;
                                } catch (NumberFormatException ignore) {
                                }
                            }
                        }
                        return 5 * 1000;
                    }

                }).build();
    }


    /**
     * @param apiUrl      API接口URL
     * @param requestBody JSON对象
     * @return
     * @auther kof wang
     * 发送 SSL POST 请求（HTTPS），JSON形式
     */
    public static String doPostSSL(String apiUrl, String requestBody, Map<String, String> header) {
        CloseableHttpResponse response = null;
        String httpStr = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(30000)
                    .setConnectTimeout(30000)
                    .setConnectionRequestTimeout(30000)
                    .setStaleConnectionCheckEnabled(true)
                    .build();
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(requestBody, "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");

            httpPost.setEntity(stringEntity);
            Iterator<Map.Entry<String, String>> it = header.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.addHeader(key, value);
            }
            httpPost.addHeader("Accept", "application/json");

            httpPost.addHeader("User-Agent","httpclient/renliwo");

            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }

            HttpEntity entity = response.getEntity();

            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;

        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
                @Override
                public void verify(String s, SSLSocket sslSocket) throws IOException {
                    return;
                }

                @Override
                public void verify(String s, X509Certificate x509Certificate) throws SSLException {

                }

                @Override
                public void verify(String s, String[] strings, String[] strings1) throws SSLException {

                }

                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return sslsf;
    }

    /**
     * get
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    @Deprecated
    public static String doGet(String host, String path, String method,
                                     Map<String, String> headers,
                                     Map<String, String> querys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpGet request = new HttpGet(buildUrl(host, path, querys));

        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        CloseableHttpResponse httpResponse = (CloseableHttpResponse)httpClient.execute(request);
        String responseBody= EntityUtils.toString(httpResponse.getEntity());
        request.releaseConnection();
        httpResponse.close();
        httpClient.getConnectionManager().closeIdleConnections(0,TimeUnit.SECONDS);
        httpClient.getConnectionManager().shutdown();
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        return responseBody;
    }

    /**
     * post form
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param bodys
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String host, String path, String method,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      Map<String, String> bodys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        if (bodys != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

            for (String key : bodys.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
            formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            request.setEntity(formEntity);
        }

        return httpClient.execute(request);
    }

    /**
     * Post String
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String host, String path, String method,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      String body)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        if (org.apache.commons.lang.StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }

    /**
     * Post stream
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String host, String path, String method,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      byte[] body)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        if (body != null) {
            request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }

    /**
     * Put String
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPut(String host, String path, String method,
                                     Map<String, String> headers,
                                     Map<String, String> querys,
                                     String body)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPut request = new HttpPut(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        if (org.apache.commons.lang.StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }

    /**
     * Put stream
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPut(String host, String path, String method,
                                     Map<String, String> headers,
                                     Map<String, String> querys,
                                     byte[] body)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPut request = new HttpPut(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        if (body != null) {
            request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }

    /**
     * Delete
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    public static HttpResponse doDelete(String host, String path, String method,
                                        Map<String, String> headers,
                                        Map<String, String> querys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        return httpClient.execute(request);
    }

    public static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!org.apache.commons.lang.StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (org.apache.commons.lang.StringUtils.isBlank(query.getKey()) && !org.apache.commons.lang.StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!org.apache.commons.lang.StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!org.apache.commons.lang.StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }

        return sbUrl.toString();
    }

    private static HttpClient wrapClient(String host) {
        HttpClient httpClient = new DefaultHttpClient();
        if (host.startsWith("https://")) {
            sslClient(httpClient);
        }

        return httpClient;
    }

    private static void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String str) {

                }
                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String str) {

                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            org.apache.http.conn.ssl.SSLSocketFactory ssf = new org.apache.http.conn.ssl.SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

}
