package csu.lch.violetapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * API签名工具类
 *
 * @author violetRcl
 */

public class SignUtils {

    /**
     * 生成签名
     *
     * @param body 用户参数
     * @param secretKey 密钥
     * @return 签名
     */
    public static String generateSign(String body, String secretKey){
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String str = body + secretKey;
// 5393554e94bf0eb6436f240a4fd71282
        return md5.digestHex(str);
    }
}

