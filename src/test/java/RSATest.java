import com.zzrb.util.RSACryptUtil;
import com.zzrb.util.RSASignUtil;
import com.zzrb.util.RSAUtil;
import org.junit.Test;


public class RSATest {

    private static String priv = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIJ5G/RuJvJPR6gPx5vKO/k41plxQd73k4xD+biffnlQwuRCXurjRrtKp1sw5Ttf1eCkGse7kPUN4/tEYfrAwrdNvTQ64xg7wwa08+42na92lJ8W152yK0ohnkbi2Mu519nYEmZI2IOQWtVWowqe8ik3EEXla60gPsuB+ltdsLp5AgMBAAECgYBwFfTzBpJExeDsOyoi5xigoLiTN138WzoRXPAyMCqps7zJ9U6vgTJpgdfGGnpDiLAnfTNYdQUx0I4TdQqrLV/YeRb1xnK//z1/nWQRmqp+DAM1XcCapektHIexDMHbPC3th8+uisabwbYQKkr0Y8Kj6b1OTi4d3P6r39xziSPfAQJBAL5F+/qmaqsDSjC1dG47Ot4Uawo+vgjtbnzADG5ZJ7eSdSsK7IQOBt6l23U6WT2ZPNIUSLzk8W8yJ3AOQe/jXiECQQCvivDNX4dUcKRmXNhWrXfq0Xz0r0eHuNccrEuTtaUw4IBfbxKYcF9YsP0rwG2wx0/oe3RVKYPhoIFIeuegquFZAkAYoys7i2zoc+aZZAWNeHHo66/ohbNJqKTT1nJFn5m69WXiiumRN9e/4K4IAeQoS71KXoIEo4h3o4Djy5TSIRjBAkBYamHI/1GZTTbwu9jXJ34bzrHiSBp+GLJ4JavXVQlD6f+ekcJK92Z+tH5/t+dHv8vWq7+3WTaswPv89rKNsSPZAkAS5Ry/itbHr/9DtXAFSV7jK53gibHgkFT5y9as36kkj76b9xkdO/gaS0w6Y1ZyPZxR190W5VFarVXNKai8UndS";
    private static String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCeRv0bibyT0eoD8ebyjv5ONaZcUHe95OMQ/m4n355UMLkQl7q40a7SqdbMOU7X9XgpBrHu5D1DeP7RGH6wMK3Tb00OuMYO8MGtPPuNp2vdpSfFtedsitKIZ5G4tjLudfZ2BJmSNiDkFrVVqMKnvIpNxBF5WutID7LgfpbXbC6eQIDAQAB";

    @Test
    public void genKeyPair() throws Exception {
        String keyPair = RSAUtil.generateKeyPair();
        System.out.println(keyPair);
    }

    @Test
    public void sign() throws Exception {
        String plainText = "anbo-rsa-888888";
        String sign =  RSASignUtil.sign(plainText, priv);
        System.out.println("sign result : " + sign);
        boolean check = RSASignUtil.verify(plainText, sign, pub);
        System.out.println("sign check result : "+ check);

    }

    @Test
    public void encrypy() throws Exception {
        String plainText = "anbo-rsa-666666";
        String  cipher =  RSACryptUtil.encrypt(plainText, pub);
        System.out.println("encrypt result : " + cipher);
        String plain = RSACryptUtil.decrypt(cipher, priv);
        System.out.println("decrypt result : "+ plain);
    }

}
