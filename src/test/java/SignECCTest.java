import com.zzrb.ecc.*;
import com.zzrb.enumm.CityIdEnum;
import com.zzrb.util.ECCCryptUtil;
import com.zzrb.util.ECCSignUtil;
import com.zzrb.util.ECCUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SignECCTest {

    @Test
    public void testenum(){
        System.out.println(CityIdEnum.getByTypeCode("010").getName());
    }


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
    public void sign() throws Exception {
        AnboECCSign anboECCSign = new AnboECCSign();
        Map<String,String> map = new HashMap<>();

        map.put("accountType","1");
        map.put("address","北京市朝阳区中电发展大厦");
        map.put("name","张三");
        map.put("bankName","招商银行");
        map.put("accountNo","6225888888888888");
        map.put("accountName","张三");
        String sign = anboECCSign.sign(map);
        System.out.println("sign:"+sign);
    }

    @Test
    public void check() throws Exception{
        AnboECCVerify anboECCVerify = new AnboECCVerify();
        Map<String,String> map = new HashMap<>();
        map.put("accountType","1");
        map.put("address","北京市朝阳区中电发展大厦");
        map.put("name","张三");
        map.put("bankName","招商银行");
        map.put("accountNo","6225888888888888");
        map.put("accountName","张三");
        String sign = "MEUCIQCAG2o5crFVilasvP4GmaiW+uHID85+unieDFPl6kdvXwIgS/w+kXXCaqtehBccy2eGPPJhbm/INYGYLQYg7ly7Bio=";
        Boolean check = anboECCVerify.verify(map,sign);
        System.out.println("check:"+check);
    }

    @Test
    public void utilTest() throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("accountType","1");
        map.put("address","北京市朝阳区中电发展大厦");
        map.put("name","张三");
        map.put("bankName","招商银行");
        map.put("accountNo","6225888888888888");
        map.put("accountName","张三");
        String priv = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgEIXzgwZddNW4g83fmOAa1zxR/uy3azknm4hgVomB8rCgCgYIKoZIzj0DAQehRANCAAStuJciKpjarWgVnZFriFWTUKi0oQMcpDHX+tOA4Yw2qruceMAQqhdbqo8MJLJIkoVJP5Fa8awHMcZQQ+lcLCUv";
        String sign = ECCSignUtil.sign(priv,map);
        System.out.println("sign:"+sign);
        String pub = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAErbiXIiqY2q1oFZ2Ra4hVk1CotKEDHKQx1/rTgOGMNqq7nHjAEKoXW6qPDCSySJKFST+RWvGsBzHGUEPpXCwlLw==";
        boolean check = ECCSignUtil.verify(pub,map,sign);
        System.out.println("check:"+check);
    }

    @Test
    public void utilTest2() throws Exception {

        String pub = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAErbiXIiqY2q1oFZ2Ra4hVk1CotKEDHKQx1/rTgOGMNqq7nHjAEKoXW6qPDCSySJKFST+RWvGsBzHGUEPpXCwlLw==";

        String priv = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgEIXzgwZddNW4g83fmOAa1zxR/uy3azknm4hgVomB8rCgCgYIKoZIzj0DAQehRANCAAStuJciKpjarWgVnZFriFWTUKi0oQMcpDHX+tOA4Yw2qruceMAQqhdbqo8MJLJIkoVJP5Fa8awHMcZQQ+lcLCUv";

        String data = "anbo88888";

        String dataEncrypt = ECCCryptUtil.encrypt(data,pub);
        System.out.println("dataEncrypt："+dataEncrypt);
        String dataDecrypt = ECCCryptUtil.decrypt(dataEncrypt,priv);
        System.out.println("dataDecrypt:" + dataDecrypt);
    }
}
