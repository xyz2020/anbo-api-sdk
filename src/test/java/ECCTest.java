import com.zzrb.ecc.*;
import com.zzrb.util.ECCCryptUtil;
import com.zzrb.util.ECCSignUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ECCTest {

    public static String pub = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEBsURnGaG8Sa5fQKI/S+3l9hD59Tto1mq/2MZUvQWGYvOnmqQkHi4eNb9QLZUXrlyw4euVBFD6N+f7DCSDoW1IQ==";
    public static String priv = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgZVMYWGu9DY2aIBI4z4GmoYYmRoHRCNwxI8QP1NhW8I+gCgYIKoZIzj0DAQehRANCAAQGxRGcZobxJrl9Aoj9L7eX2EPn1O2jWar/YxlS9BYZi86eapCQeLh41v1AtlReuXLDh65UEUPo35/sMJIOhbUh";
    public static String anboPubKey_test = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAErbiXIiqY2q1oFZ2Ra4hVk1CotKEDHKQx1/rTgOGMNqq7nHjAEKoXW6qPDCSySJKFST+RWvGsBzHGUEPpXCwlLw==";

    @Test
    public void genKeyPair() throws Exception {
        String keyPair = AnboECCKey.generateKeyPair();
        System.out.println(keyPair);
    }

    @Test
    public void crypt() throws Exception {
        AnboECCEncrypt anboECCEncrypt = new AnboECCEncrypt();
        AnboECCDecrypt anboECCDecrypt = new AnboECCDecrypt();
        String data = "anbo";
        String dataEncrypt = anboECCEncrypt.encrypt(data);
        System.out.println("dataEncrypt："+dataEncrypt);
        String dataDecrypt = anboECCDecrypt.decrypt(dataEncrypt);
        System.out.println("dataDecrypt:" + dataDecrypt);
    }


    @Test
    public void check() throws Exception{

        Map<String,String> map = new HashMap<>();
        map.put("name", "paytest01");//用户名称
        map.put("regType", "0");//注册类型
        map.put("cityId", "0311");//所在城市
        map.put("accountType", "0");//客户类型？？？？0:车场; 1:媒介; 3:代理商  ？？？
        map.put("contactName", "煤老板");//联系人
        map.put("contactMobile", "15588888888");//联系电话
        map.put("source", "1");//客户来源
        map.put("manager", "1");//客户经理
        map.put("accountName", "张三单");//开户人名称
        map.put("bankName", "工商银行");//银行名称
        map.put("accountNo", "62220202000888811");//银行名称
        map.put("password", "BL7D83gY+wOcx9nR4OVyGVogl8zZ0Smafo6F+WqR5Gr181DV/5sJZ1QCoTVV0egkSmPt8W2rdoA+cFd0PfynxncUgnQGx78h+o+qd9nd/DdvBdcIWhoV7IiM9g==");//登录密码
        map.put("publicKey", "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEBsURnGaG8Sa5fQKI/S+3l9hD59Tto1mq/2MZUvQWGYvOnmqQkHi4eNb9QLZUXrlyw4euVBFD6N+f7DCSDoW1IQ==");//公钥

        String sign = "MEYCIQD6UkhGpbDQ/50tnxAzmJ7UbMPSR6AazCSKX0uU7Rk2GQIhAIdVBjdvAJVVHBVVeaitvsYaw8VeMGfOESfrNZULX6dy";
        boolean check = ECCSignUtil.verify(pub,map,sign);
        System.out.println("check:"+check);
    }

    @Test
    public void signVerify() throws Exception {

        Map<String,String> map = new HashMap<>();
        map.put("name", "paytest01");//用户名称
        map.put("regType", "0");//注册类型
        map.put("cityId", "0311");//所在城市
        map.put("accountType", "0");//客户类型？？？？0:车场; 1:媒介; 3:代理商  ？？？
        map.put("contactName", "煤老板");//联系人
        map.put("contactMobile", "15588888888");//联系电话
        map.put("source", "");//客户来源
        map.put("manager", "");//客户经理
        map.put("accountName", "张三单");//开户人名称
        map.put("bankName", "工商银行");//银行名称
        map.put("accountNo", "62220202000888811");//银行名称
        map.put("password", ECCCryptUtil.encrypt("123456", anboPubKey_test));//登录密码
        map.put("publicKey", "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEBsURnGaG8Sa5fQKI/S+3l9hD59Tto1mq/2MZUvQWGYvOnmqQkHi4eNb9QLZUXrlyw4euVBFD6N+f7DCSDoW1IQ==");//公钥

        String sign = ECCSignUtil.sign(priv,map);
        System.out.println("sign:"+sign);
        boolean check = ECCSignUtil.verify(pub,map,sign);
        System.out.println("check:"+check);
    }

    @Test
    public void commonCrypt() throws Exception {
            String data = "123456";
            String dataEncrypt = ECCCryptUtil.encrypt(data,pub);
            System.out.println("dataEncrypt："+ dataEncrypt);
            String dataDecrypt = ECCCryptUtil.decrypt(dataEncrypt,priv);
            System.out.println("dataDecrypt:" + dataDecrypt);
    }

    @Test
    public void testDecrypt() throws Exception {
        String priv = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgEIXzgwZddNW4g83fmOAa1zxR/uy3azknm4hgVomB8rCgCgYIKoZIzj0DAQehRANCAAStuJciKpjarWgVnZFriFWTUKi0oQMcpDHX+tOA4Yw2qruceMAQqhdbqo8MJLJIkoVJP5Fa8awHMcZQQ+lcLCUv";
//        String dataEncrypt = "BH0i5/igGWslt133QV3czJV+dDbOBBCAL3XaMhqh3Na+H/eBHlwQFvPOViVF0IpjZUPB2jXAQXalOYOW0IMppvWbG/uSuYwuUSqL0uDTpl2YszlYp/85Z6MQ2w==";
        String dataEncrypt = "BL7D83gY+wOcx9nR4OVyGVogl8zZ0Smafo6F+WqR5Gr181DV/5sJZ1QCoTVV0egkSmPt8W2rdoA+cFd0PfynxncUgnQGx78h+o+qd9nd/DdvBdcIWhoV7IiM9g==";
        String dataDecrypt = ECCCryptUtil.decrypt(dataEncrypt,priv);
        System.out.println("dataDecrypt:" + dataDecrypt);
    }
}
