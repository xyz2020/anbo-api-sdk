package com.zzrb.enumm;

public enum RegTypeEnum {

    COMPANY("公司",0),
    PERSONAL("个人",1);

    private String name;
    private int code;

    RegTypeEnum(String name, int code) {
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

    public static RegTypeEnum getByTypeCode(Byte code){
        for (RegTypeEnum regTypeEnum : RegTypeEnum.values()) {
            if (regTypeEnum.getCode() == code) {
                return regTypeEnum;
            }
        }
        return null;
    }
    public static String getNameByTypeCode(Byte code){
        for (RegTypeEnum regTypeEnum : RegTypeEnum.values()) {
            if (regTypeEnum.getCode() == code) {
                return regTypeEnum.getName();
            }
        }
        return "";
    }
}
