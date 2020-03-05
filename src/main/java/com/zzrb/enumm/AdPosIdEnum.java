package com.zzrb.enumm;

public enum AdPosIdEnum {

    ENTERPARK("入场推送",1),
    EXITPARK("出场推送",2),
    INPUTCARNUM("输入车牌",3),
    PAYPAGE("支付页面",4),
    PAYSUCCESS("付款成功",5);

    private String name;
    private int code;

    AdPosIdEnum(String name, int code) {
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

    public static AdPosIdEnum getByTypeCode(int code){
        for (AdPosIdEnum adPosIdEnum : AdPosIdEnum.values()) {
            if (adPosIdEnum.getCode() == code) {
                return adPosIdEnum;
            }
        }
        return null;
    }
    public static String getNameByTypeCode(int code){
        for (AdPosIdEnum adPosIdEnum : AdPosIdEnum.values()) {
            if (adPosIdEnum.getCode() == code) {
                return adPosIdEnum.getName();
            }
        }
        return "";
    }
}
