package com.wowoohr.calculators.form.auth;

import lombok.Data;

/**
 * @author luolihua
 */
@Data
public class AuthEntryUrlForm extends BaseAuthForm {
    String idCard;
    String idCardName;
    String mobile;
    String returnUrl;

//    private Long corporationId;
//
//    private String cityCode;
//
//    private Short productType;
//
//    private String inlet;
//
//    private String bizNo;
//
//    private String token;
//    private String verifyToken;


}