package com.wowoohr.calculators.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@ApiModel(value="ApiResponseT",description="返回类")
public class ApiResponseT<T> {
    private static Logger logger = LoggerFactory.getLogger(ApiResponseT.class);
    @ApiModelProperty(value = "code")
    protected String code;
    @ApiModelProperty(value = "描述")
    protected String msg;
    protected String subCode;
    protected String subMsg;
    @ApiModelProperty(value = "对象")
    protected T result;

    public ApiResponseT() {
        super();
    }

    public ApiResponseT(String code, String msg){
        super();
        this.code = code;
        this.msg = msg;
//        logger.info("------------------------------------------------返回数据------------------------------------------------");
//        Gson gson = new GsonBuilder().create();
//        logger.info(gson.toJson(this));
    }

    public ApiResponseT(String code, String msg, T result){
        super();
        this.code = code;
        this.msg = msg;
        this.result = result;
//        logger.info("------------------------------------------------返回数据------------------------------------------------");
//        Gson gson = new GsonBuilder().create();
//        logger.info(gson.toJson(this));
    }

    public ApiResponseT(Header header) {
        this(header.getCode(),header.getMemo());
    }

    public ApiResponseT(Header header, T result) {
        this(header.getCode(),header.getMemo(),result);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static ApiResponseT success() {
        return new ApiResponseT(new Header(HeaderEnum.SUC_MSG));
    }

    public static ApiResponseT error(){
        return new ApiResponseT(new Header(HeaderEnum.SERVICE_MSG));
    }

    public static ApiResponseT error(HeaderEnum headerEnum){
        return new ApiResponseT(new Header(headerEnum.getCode(), headerEnum.getMsg()));
    }


    public static ApiResponseT error(String errorCode, String errorMessage){
        return new ApiResponseT(new Header(errorCode, errorMessage));
    }

    public static<T> ApiResponseT success(T param) {
        return new ApiResponseT(new Header(HeaderEnum.SUC_MSG), param);
    }

    public static<T> ApiResponseT success(String key, T param) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, param);
        return new ApiResponseT(new Header(HeaderEnum.SUC_MSG), map);
    }


    public static<T> ApiResponseT success(T[][] pairs) {
        return new ApiResponseT(new Header(HeaderEnum.SUC_MSG), pairs);
    }
    public static<T> ApiResponseT success(HeaderEnum headerEnum, String key, T param) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, param);
        return new ApiResponseT(new Header(headerEnum), map);
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public void setSubMsg(String subMsg) {
        this.subMsg = subMsg;
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", result=" + ((result instanceof String)?("\'" + result + "\'" ):result) +
                '}';
    }
}
