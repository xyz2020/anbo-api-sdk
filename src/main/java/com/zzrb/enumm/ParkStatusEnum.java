package com.zzrb.enumm;

public enum ParkStatusEnum {

    ON("停用",0),
    OFF("启用",1);

    private String name;
    private int code;

    ParkStatusEnum(String name, int code) {
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

    public static ParkStatusEnum getByTypeCode(Byte code){
        for (ParkStatusEnum parkStatusEnum : ParkStatusEnum.values()) {
            if (parkStatusEnum.getCode() == code) {
                return parkStatusEnum;
            }
        }
        return null;
    }
    public static String getNameByTypeCode(Byte code){
        for (ParkStatusEnum parkStatusEnum : ParkStatusEnum.values()) {
            if (parkStatusEnum.getCode() == code) {
                return parkStatusEnum.getName();
            }
        }
        return "";
    }
}
