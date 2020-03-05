package com.zzrb.enumm;


/**
 * 广告类型
 */
public enum AdTypeEnum {
    WORD("文字链接",0),
    PICTURE("图片链接",1);

    private String name;
    private int code;

    AdTypeEnum(String name, int code) {
        this.name = name;
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public static AdTypeEnum getByTypeCode(Byte code){
        for (AdTypeEnum adTypeEnum : AdTypeEnum.values()) {
            if (adTypeEnum.getCode() == code) {
                return adTypeEnum;
            }
        }
        return null;
    }
    public static String getNameByTypeCode(Byte code){
        for (AdTypeEnum adTypeEnum : AdTypeEnum.values()) {
            if (adTypeEnum.getCode() == code) {
                return adTypeEnum.getName();
            }
        }
        return "";
    }
}
