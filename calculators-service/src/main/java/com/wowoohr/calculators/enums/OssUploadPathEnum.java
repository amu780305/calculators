package com.wowoohr.calculators.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * oss上传路径前缀
 * @author luolihua
 */
@AllArgsConstructor
@Getter
public enum OssUploadPathEnum {

    /**
     * 身份证
     */
    ID_CARD("id_card", "upload/identity/%s/"),

    /**
     * 用户投诉
     */
    USER_COMPLAINT("user_complaint", "upload/userComplaint/%s/"),
    
    /**
     * 默认
     */
    DEFAULT("default", "upload/file/%s/"),

    ;
    String snrCode;
    String path;

    public static OssUploadPathEnum getByCode(String snrCode) {
        return Optional.ofNullable(Arrays.stream(values()).filter(e -> e.getSnrCode().equals(snrCode)).findFirst().get()).orElse(DEFAULT);
    }

    public String getPath(Object... objs) {
        return String.format(path, objs);
    }


}
