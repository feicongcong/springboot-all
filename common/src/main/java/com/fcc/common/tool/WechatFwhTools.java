package com.fcc.common.tool;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.UUID;

/**
 * @Description: 微信服务号工具类
 * @Author: CC.F
 * @Date: 16:05 2018/9/4
 */
public class WechatFwhTools {
    /**
     * 参数
     */
    public static final String PARAM_TOUSERNAME = "ToUserName";
    public static final String PARAM_FROMUSERNAME = "FromUserName";
    public static final String PARAM_CREATETIME = "CreateTime";
    public static final String PARAM_MSGTYPE = "MsgType";
    public static final String PARAM_CONTENT = "Content";
    public static final String PARAM_ACCESSTOKEN = "access_token";
    public static final String PARAM_ERRCODE = "errcode";
    public static final String PARAM_ERRMSG = "errmsg";
    public static final String PARAM_TOUSER = "touser";
    public static final String PARAM_EVENT = "Event";
    public static final String PARAM_TICKET = "ticket";


    /**
     * SHA1加密字符
     */
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);

        for (int j = 0; j < len; ++j) {
            buf.append(HEX_DIGITS[bytes[j] >> 4 & 15]);
            buf.append(HEX_DIGITS[bytes[j] & 15]);
        }

        return buf.toString();
    }

    /**
     * SHA1加密
     *
     * @param str
     * @return
     */
    public static String SHA1Encode(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                MessageDigest e = MessageDigest.getInstance("SHA1");
                e.update(str.getBytes());
                return getFormattedText(e.digest());
            } catch (Exception var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    /**
     * 校验签名
     *
     * @param token
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {
        ArrayList params = new ArrayList();
        params.add(token);
        params.add(timestamp);
        params.add(nonce);
        Collections.sort(params);
        String temp = WechatFwhTools.SHA1Encode((String) params.get(0) + (String) params.get(1) + (String) params.get(2));
        return temp.equals(signature);
    }

    /**
     * 生成 uuid， 即用来标识一笔单，也用做 nonce_str
     *
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

    /**
     * 参数进行字典排序(treeMap默认字典排序)
     *
     * @param params
     * @return
     */
    public static String getMapStrBySort(TreeMap<String, String> params) {
        StringBuilder s2s = new StringBuilder();
        for (String k : params.keySet()) {
            s2s.append(k).append("=").append(params.get(k)).append("&");
        }
        return s2s.toString().substring(0, s2s.length() - 1);
    }
}
