package com.wowoohr.calculators.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Header {

    protected String code;
    protected String memo;

    public Header() {
        super();
    }

    public Header(HeaderEnum header){
        super();
        this.code = header.getCode();
        this.memo = header.getMsg();
    }

    public Header(String code, String msg) {
        super();
        this.code = code;
        this.memo = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @JsonIgnore
    public boolean isSuccess(){
        if(code.equals("200")){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Header [code=" + code + ", memo=" + memo + "]";
    }

}
