import com.zzrb.sign.JDK8SignECC;
import org.junit.Test;

import java.util.HashMap;

public class SignECCTest {

    @Test
    public void sign() throws Exception {

        JDK8SignECC jdk8SignECC = new JDK8SignECC();
        HashMap<String,String> map = new HashMap<>();

        map.put("accountType","1");
        map.put("address","北京市朝阳区中电发展大厦");
        map.put("name","张三");
        map.put("bankName","招商银行");
        map.put("accountNo","6225888888888888");
        map.put("accountName","张三");

        System.out.println("datamap:"+map);
        String sign = jdk8SignECC.sign(map);
        System.out.println("sign:"+sign);

        String pubKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE1SmTD9+hRzZB4NXZnfPpRkRayW8RUQ0JeLAJEqap07C8MLe/jC4nP4b7SsNBfEzneZAdmn6gfXY7DwAXInBR6w==";
        Boolean check = JDK8SignECC.verify(pubKey,map,sign);
        System.out.println("check:"+check);

    }

}
