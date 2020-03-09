# anbo-api-sdk 使用说明

## 运行环境

1. JDK版本要求必须是JDK 8u211及以上
2. 编译工具maven
3. 可预见问题处理方案

    > 如果在用JDK版本不满足版本要求,又不能对JDK版本进行升级.请参照如下步骤操作解决可能会出现的问题:

    ```
    java.security.InvalidKeyException: Illegal key size or default parameters
    at javax.crypto.Cipher.checkCryptoPerm(Cipher.java:1026)
    at javax.crypto.Cipher.implInit(Cipher.java:801)
    at javax.crypto.Cipher.chooseProvider(Cipher.java:864)
    at javax.crypto.Cipher.init(Cipher.java:1249)
    at javax.crypto.Cipher.init(Cipher.java:1186)
    ```

    > 因为超出了JDK 默认的秘钥长度，若需放开，则需要更新 JDK 中相应的 jar 文件（local_policy.jar 、US_export_policy.jar），相应的下载链接如下所示：

    http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html

    http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html

    http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html

    > 请依据相应安装的 JDK 版本进行下载，下载后解压到 JDK 安装目录下的 jre/lib/security 文件夹下，重启应用即可。

## 获取SDK方式

### 一、源码编译

    1.git clone https://github.com/xyz2020/anbo-api-sdk.git
    2.maven下载依赖
    3.maven clean 
    4.maven compile
    5.maven package生成target目录以及其中文件anbo-sdk-*.jar
    6.将jar包引入项目

### 二、直接下载编译后的jar包文件

下载地址：
> https://github.com/xyz2020/anbo-api-sdk/tree/master/download

## 私钥文件说明(平台发放,使用者妥善保管)

    使用者注意：
    1. 将私钥文件放置到项目的资源文件夹（resources）下 
    2. 文件名称要求必须是key_pair.json。
    3. 该私钥文件文件必须由开发者跟项目方申请，才能做api联调，自己创建的公私钥无法联调。项目方会进行公私钥的备案。

私钥文件内容示例:

```json
{
    "priv_key":{
        "type": "anbo/PrivKeyECC",
        "value":"MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgtnckI8teu4j2G7uIREXhGBuG4RskNKT1xj6cIz0X21+gCgYIKoZIzj0DAQehRANCAATneXLI3jQlTMaCZLhDOT6c2eZsikoFKoUbkHXMMp1Q38Fn+ycJZum8a05b2T3g9vEa/QtHEoVLxAuwXDtt3T5D"
    },
    "pub_key":{
        "type": "anbo/PubKeyECC",
        "value":"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE53lyyN40JUzGgmS4Qzk+nNnmbIpKBSqFG5B1zDKdUN/BZ/snCWbpvGtOW9k94PbxGv0LRxKFS8QLsFw7bd0+Qw=="
    }
}
```

## 平台公钥文件(平台发放,使用者保管)

    使用说明:
    1.公钥文件放置在项目的资源文件夹（resources）下
    2.公钥文件名称:anbo_pub_key.json
    3.公钥文件中仅包含平台公钥,用于在调用api接口时对数据进行加密,如有需要也可以进行验签.
    4.在进行与平台对接时候,选择正确的公钥文件,区分测试环境与生产环境.

测试环境公钥文件:

```json
{
  "pub_key":{
    "type": "anbo/PubKeyECC",
    "value":"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAErbiXIiqY2q1oFZ2Ra4hVk1CotKEDHKQx1/rTgOGMNqq7nHjAEKoXW6qPDCSySJKFST+RWvGsBzHGUEPpXCwlLw=="
  }
}
```

生产环境公钥文件:

```json
{
  "pub_key":{
    "type": "anbo/PubKeyECC",
    "value":"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEP8oOm7jIx5HSNozIIjHKytaEy/a5VJksrhjYClmue/p9YRC0v+zVP2ajSoKPmnYoz6SV0MvzwlzzUUU5ZJiOnA=="
  }
}
```

## api-sdk方法说明及示例

### 核心方法

    1.AnboECCSign => String sign(Map<String,String> data)
        执行签名：返回字符串。
        data：需要签名的数据，指定传入map格式。
    2.AnboECCVerify => boolean verify(Map<String,String> data, String sign)
        验证签名：返回true，验签成功；返回false，验签失败。
        data：需要签名的数据，指定传入map格式。
        sign：签名后的字符串
    3.AnboECCEncrypt => String encrypt(String data)
        默认使用平台的公钥进行加密
        data:需要加密的数据
    4.AnboECCDecrypt => String decrypt(String data)
        使用私钥进行数据解密
        data:加密后的数据

> 代码示例

```java
        //签名
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

```

```java
        //验签
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
```

```java
        //加密与解密
        AnboECCEncrypt anboECCEncrypt = new AnboECCEncrypt();
        AnboECCDecrypt anboECCDecrypt = new AnboECCDecrypt();
        String data = "anbo";
        String dataEncrypt = anboECCEncrypt.encrypt(data);
        System.out.println("dataEncrypt："+dataEncrypt);
        String dataDecrypt = anboECCDecrypt.decrypt(dataEncrypt);
        System.out.println("dataDecrypt:" + dataDecrypt);
```