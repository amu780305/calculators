package com.wowoohr.calculators.common.domain;

import com.google.common.collect.Maps;

import java.util.Map;

public enum HeaderEnum {

    /**
     * 系统级错误
     */
    SUC_MSG("200", ""),// 不可更改
    DUBBLE_MSG("0000","重复操作(操作成功)"),// 不可更改
    SYS_MSG("0001", "系统错误"),// 不可更改
    PARAM_MSG("0002", "参数错误"),
    DAO_MSG("0003", "数据异常"),
    SERVICE_MSG("0004", "服务异常"),
    WEB_MSG("0005", "网络异常,请重试"),
    WEB_REPEAT_SUBMIT("0006", "重复提交"),
    INVALID_REQUEST("0007", "非法请求"),
    NET_NOT_STABLE("0008", "网络不稳定，请稍后再试"),
    SIGN_ERROR("0009","签名错误！"),
    APPID_ERROR("0010" ,"应用的唯一标识错误"),
    DATE_ERROR("0011" ,"日期转换错误"),
    RESOLVER_FILE("0012" ,"解析文件错误"),
    SUPPLIER_EOORO("0013" ,"供应商异常"),
    UNKNOW_ERROR("9999","未知异常"),

    /**
     * 用户相关
     */
    INVALID_SESSION("1001", "你已经退出登录!"), // 不可更改
    INVALID_USER_PASSWORD("1002","用户名或密码错误!"),//不可更改
    USER_NOT_EXIST("1003","用户名不存在!"),
    USER_IS_LOGIN("1004" ,"当前用户已经登录"),
    USER_SECOND_LOGIN("1005" ,"用户多地同时登录"),


    /*************** 文件相关code，区间范围[2001, 3000] *************************/
    FILE_UPLOAD_STREAM_ERROR("2001","上传文件流错误，请重新上传"),
    FILE_UPLOAD_BASE64_ERROR("2003","上传文件内容错误，请重新上传"),
    FILE_EXT_ERROR("2005", "拍摄不符合要求，请重新拍摄"),
    FILE_IDCARD_FRONT_SIDE_ERROR("2006", "请重新上传正面身份证"),
    FILE_IDCARD_BACK_SIDE_ERROR("2007", "请重新上传反面身份证"),
    FILE_EXCEED_LIMIT("2008", "图片超过2M限制，请重新上传"),
    FILE_UPLOAD_ERROR("2009", "文件上传失败"),

    FILE_DOWNLOAD_ERROR("2012","文件下载异常"),

    CREATE_DOWNLOAD_TASK_ERROR("2033","创建下载任务失败"),
    CREATE_DOWNLOAD_TASK_SUCCESS("2034","创建下载任务成功"),

    /**
     *
     */
    INVOKE_EXCEPTION("3001"," 调用错误"),

    RESOURCES_ARE_OCCUPIED("4001","资源被占用"),


    /*************** 认证相关code，区间范围[6001, 7000] *************************/
    /**
     * 认证 - 身份认证，code区间范围[6001, 7200]
     */
    IDENTITY_OCR_FRONT_CHANNEL_CODE_NULL("6001", "渠道未配置"),
    IDENTITY_IDCARD_FRONT_TYPE_ERROR("6011", "图片类型错误"),
    IDENTITY_IDCARD_FRONT_BASE64_ERROR("6012", "图片数据错误"),
    IDENTITY_IDCARD_NAME_ERROR("6013", "姓名长度为1到15个字，允许输入中英文和·"),
    IDENTITY_IDCARD_CERT_NO_NULL("6014", "无效的证件号码"),
    IDENTITY_IDCARD_PIC_NULL("6015", "身份证照片不能为空"),
    IDENTITY_IDCARD_AGE_ERROR("6016", "年龄未大于16周岁"),
    IDENTITY_IDCARD_UPLOAD_TOO_MANY("6019", "当日上传次数过多，请稍后再试"),

    /**
     * 认证 - X认证，code区间范围[7201, 7400]
     */

    /**
     * 认证 - Y认证，code区间范围[7401, 7600]
     */


    ;

    private String code;

    private String msg;


    HeaderEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
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

    private static final Map<String,HeaderEnum> nameIndex =
            Maps.newHashMapWithExpectedSize(HeaderEnum.values().length);
    static {
        for (HeaderEnum headerEnum : HeaderEnum.values()){
            nameIndex.put(headerEnum.getCode(),headerEnum);
        }
    }

    public static HeaderEnum lookupByName(String name){
        return nameIndex.get(name);
    }

}
