package com.wowoohr.calculators.utils;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author chenhui
 * @version UrlUtils: UrlUtils.java, v 0.1 2021年07月09日 下午4:39 chenhui Exp $
 */
public class UrlUtils {


    public static class UrlEntity {
        /**
         * 基础url
         */
        public String baseUrl;
        /**
         * url参数
         */
        public Map<String, String> params;
    }

    /**
     * 解析url
     *
     * @param url
     * @return
     */
    public static UrlEntity parse(String url) {
        UrlEntity entity = new UrlEntity();
        if (url == null) {
            return entity;
        }
        url = url.trim();
        if (url.equals("")) {
            return entity;
        }
        String[] urlParts = url.split("\\?");
        entity.baseUrl = urlParts[0];
        //没有参数
        if (urlParts.length == 1) {
            return entity;
        }
        //有参数
        String[] params = urlParts[1].split("&");
        entity.params = new HashMap<>();
        for (String param : params) {
            String[] keyValue = param.split("=");
            if(keyValue.length > 1) {
                entity.params.put(keyValue[0], keyValue[1]);
            }else if(keyValue.length ==1){
                entity.params.put(keyValue[0],"2088");
            }
        }

        return entity;
    }



    /**
     * 向url链接追加参数
     * @param url
     * @param params Map<String, String>
     * @return
     */
    public static String appendParams(String url, Map<String, String> params){
        if(StringUtils.isBlank(url)){
            return "";
        }else if(MapUtils.isEmpty(params)){
            return url.trim();
        }else{
            StringBuffer sb = new StringBuffer("");
            Set<String> keys = params.keySet();
            for (String key : keys) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);

            url = url.trim();
            int length = url.length();
            int index = url.indexOf("?");
            if(index > -1){//url说明有问号
                if((length - 1) == index){//url最后一个符号为？，如：http://wwww.baidu.com?
                    url += sb.toString();
                }else{//情况为：http://wwww.baidu.com?aa=11
                    url += "&" + sb.toString();
                }
            }else{//url后面没有问号，如：http://wwww.baidu.com
                url += "?" + sb.toString();
            }
            return url;
        }
    }

    /**
     * 向url链接追加参数(单个)
     * @param url
     * @param name String
     * @param value String
     * @return
     */
    public static String appendParam(String url, String name, String value){
        if(StringUtils.isBlank(url)){
            return "";
        }else if(StringUtils.isBlank(name)){
            return url.trim();
        }else{
            Map<String, String> params = new HashMap<String, String>();
            params.put(name, value);
            return appendParams(url, params);
        }
    }

    /**
     * 移除url链接的多个参数
     * @param url String
     * @param paramNames String[]
     * @return
     */
    public static String removeParams(String url, String... paramNames){
        if(StringUtils.isBlank(url)){
            return "";
        }else if(null == paramNames || paramNames.length == 0){
            return url.trim();
        }else{
            url = url.trim();
            int length = url.length();
            int index = url.indexOf("?");
            if(index > -1){//url说明有问号
                if((length - 1) == index){//url最后一个符号为？，如：http://wwww.baidu.com?
                    return url;
                }else{//情况为：http://wwww.baidu.com?aa=11或http://wwww.baidu.com?aa=或http://wwww.baidu.com?aa
                    String baseUrl = url.substring(0, index);
                    String paramsString = url.substring(index + 1);
                    String[] params = paramsString.split("&");
                    if(null != paramNames && paramNames.length != 0){
                        Map<String, String> paramsMap = new HashMap<String, String>();
                        for (String param : params) {
                            if(!StringUtils.isBlank(param)){
                                String[] oneParam = param.split("=");
                                String paramName = oneParam[0];
                                int count = 0;
                                for(int i=0; i<paramNames.length; i++){
                                    if(paramNames[i].equals(paramName)){
                                        break;
                                    }
                                    count ++;
                                }
                                if(count == paramNames.length){
                                    paramsMap.put(paramName, (oneParam.length > 1)?oneParam[1]:"");
                                }
                            }
                        }
                        if(!MapUtils.isEmpty(paramsMap)){
                            StringBuffer paramBuffer = new StringBuffer(baseUrl);
                            paramBuffer.append("?");
                            Set<String> set = paramsMap.keySet();
                            for (String paramName : set) {
                                paramBuffer.append(paramName).append("=").append(paramsMap.get(paramName)).append("&");
                            }
                            paramBuffer.deleteCharAt(paramBuffer.length() - 1);
                            return paramBuffer.toString();
                        }
                        return baseUrl;
                    }
                }
            }
            return url;
        }
    }
}
 /*   public static void main(String[] args) {
        JSONObject json = JSONObject.parseObject("{\"web\": {\"path\": \"alipays://platformapi/startapp?appId=2018060760267955&query=source%3dzixundibubanner\"}, \"toMini\": {\"path\": \"alipays://platformapi/startapp?appId=2018060760267955&query=source%3dzixundibubanner\", \"appid\": \"2018060760267955\", \"param\": \"\"}, \"toAlipay\": {\"path\": \"alipays://platformapi/startapp?appId=2018060760267955&query=source%3dzixundibubanner\"}, \"hyperLinks\": {\"path\": \"alipays://platformapi/startapp?appId=2018060760267955&query=source%3dzixundibubanner\"}}");
        for(String key :json.keySet()){
            if(key.equals("toMini")){
                JSONObject json1 =  json.getJSONObject(key);
                String path = json1.getString("path");
                String action_name = "index_recommend156_action";
                //  path = "{\"path\":\""+path +"\",\"type:\""+"\"web\"}";
                // System.out.println(path);
                //重新拼接
                clickUrlVo linkObj = new clickUrlVo();
                linkObj.setType("toMini");
                linkObj.setAppId(json1.getString("appid"));
                linkObj.setExtraData(json1.getString("param"));
                String s =UrlUtils.removeParams(path,"query");
                s = UrlUtils.appendParam(s,"query", URLEncoder.encode("source="+action_name));
                linkObj.setPath(s);
                System.out.println("ok");
            }
        }
    }*/