import com.zzrb.ecc.*;
import com.zzrb.enumm.CityIdEnum;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SignECCTest {

    @Test
    public void testenum(){
        System.out.println(CityIdEnum.getByTypeCode("010").getName());
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
    public void genKeyPair() throws Exception {
        String keyPair = AnboECCKey.generateKeyPair();
        System.out.println(keyPair);
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
        System.out.println("map:"+map);

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
        String sign = "MEUCIQCOPHb+g+jTgcPHApVxBZn4ducOXknNOkjK2oZQQhX+MwIgbnIzi/+cZzm9388t3fK6FWpYsjGsgveAPfMDFS+IKPk=";
        String pubKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE53lyyN40JUzGgmS4Qzk+nNnmbIpKBSqFG5B1zDKdUN/BZ/snCWbpvGtOW9k94PbxGv0LRxKFS8QLsFw7bd0+Qw==";
        Boolean check = anboECCVerify.verify(pubKey,map,sign);

        System.out.println("check:"+check);
    }

}
