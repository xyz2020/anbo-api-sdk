package com.zzrb.enumm;

public enum AdPosStatusEnum {

    ON("停用",0),
    OFF("启用",1);

    private String name;
    private int code;

    AdPosStatusEnum(String name, int code) {
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

    public static AdPosStatusEnum getByTypeCode(Byte code){
        for (AdPosStatusEnum adPosStatusEnum : AdPosStatusEnum.values()) {
            if (adPosStatusEnum.getCode() == code) {
                return adPosStatusEnum;
            }
        }
        return null;
    }
    public static String getNameByTypeCode(Byte code){
        for (AdPosStatusEnum adPosStatusEnum : AdPosStatusEnum.values()) {
            if (adPosStatusEnum.getCode() == code) {
                return adPosStatusEnum.getName();
            }
        }
        return "";
    }
}
