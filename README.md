# anbo-api-sdk 使用说明

## 运行环境

1. JDK版本1.8及以上
2. 编译工具maven

## 获取SDK方式

### 一、源码编译

    1.git clone https://github.com/xyz2020/anbo-api-sdk.git
    2.maven下载依赖
    3.maven clean 
    4.maven compile
    5.maven package生成target目录以及其中文件sign-*.jar
    6.将jar包引入项目

### 二、直接下载编译后的jar包文件

下载地址：
> https://github.com/xyz2020/anbo-api-sdk/tree/master/download

## 私钥文件说明(平台发放,使用者妥善保管)

> 将私钥文件放置到项目的资源文件夹（resources）下.

    使用者注意：
    1. 文件名称要求必须是key_pair.json。
    2. 该私钥文件文件必须由开发者跟项目方申请，才能做api联调，自己创建的公私钥无法联调。项目方会进行公私钥的备案。

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

## 平台公钥文件(SDK中默认带有平台公钥)

    注意:
    1.公钥文件放置在项目的资源文件夹（resources）下
    2.公钥文件名称:anbo_pub_key.json
    3.公钥文件中仅包含平台公钥,用于在调用api接口时对数据进行加密.

公钥文件内容示例:

```json
{
  "pub_key":{
    "type": "anbo/PubKeyECC",
    "value":"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE53lyyN40JUzGgmS4Qzk+nNnmbIpKBSqFG5B1zDKdUN/BZ/snCWbpvGtOW9k94PbxGv0LRxKFS8QLsFw7bd0+Qw=="
  }
}
```

## api-sdk方法说明及示例

### 核心方法

    1.AnboECCKey => String generateKeyPair()
        创建密钥对
    2.AnboECCSign => String sign(Map<String,String> data)
        执行签名：返回字符串。
        data：需要签名的数据，指定传入map格式。
    3.AnboECCVerify => boolean verify(String publKeyStr, Map<String,String> data, String sign)
        验证签名：返回true，验签成功；返回false，验签失败。
        publKeyStr：公钥
        data：需要签名的数据，指定传入map格式。
        sign：签名后的字符串
    4.AnboECCEncrypt => String encrypt(String data)
        默认使用平台的公钥进行加密
        data:需要加密的数据
    5.AnboECCDecrypt => String decrypt(String data)
        平台使用私钥进行数据解密
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
        String sign = "MEUCIQCOPHb+g+jTgcPHApVxBZn4ducOXknNOkjK2oZQQhX+MwIgbnIzi/+cZzm9388t3fK6FWpYsjGsgveAPfMDFS+IKPk=";
        String pubKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE53lyyN40JUzGgmS4Qzk+nNnmbIpKBSqFG5B1zDKdUN/BZ/snCWbpvGtOW9k94PbxGv0LRxKFS8QLsFw7bd0+Qw==";
        Boolean check = anboECCVerify.verify(pubKey,map,sign);
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