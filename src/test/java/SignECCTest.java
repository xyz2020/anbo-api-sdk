import com.zzrb.sign.AnboECCKey;
import com.zzrb.sign.AnboECCSign;
import com.zzrb.sign.AnboECCVerify;
import com.zzrb.sign.BaseECC;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SignECCTest {

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
        Map<String,String> map = new HashMap<>();
        map.put("accountType","1");
        map.put("address","北京市朝阳区中电发展大厦");
        map.put("name","张三");
        map.put("bankName","招商银行");
        map.put("accountNo","6225888888888888");
        map.put("accountName","张三");
        String sign = "MEQCIGyJWpeBk+i64XTF9c4TE96LP+W4O4BYHSBFLEEYIC6VAiB8XYSItDaiaDgzvpsehs1RQ54l30h2YPYwF87Z5kC/nQ==";
        String pubKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE1SmTD9+hRzZB4NXZnfPpRkRayW8RUQ0JeLAJEqap07C8MLe/jC4nP4b7SsNBfEzneZAdmn6gfXY7DwAXInBR6w==";
        Boolean check = AnboECCVerify.verify(pubKey,map,sign);
        System.out.println("check:"+check);
    }

}
