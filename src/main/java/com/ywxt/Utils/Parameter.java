package com.ywxt.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Parameter {
    private Parameter() {
    }

    // 环境变量["dev":"测试";"prod":"正式"]
    public static String ENV = "dev";
    // redis
    public static String redisHost = "127.0.0.1";
    public static int redisPort = 63790;
    public static String redisName = "master";
    // redis-key
    public static String redisKeyUserToken = "USER_TOKEN_{token}";
    public static int redisTllUserToken = 60 * 60 * 1000 * 2;

    // 登陆有效时间（ms）
    public static int loginTtlMs = 60 * 60 * 1000 * 2;
    // 登陆token密钥
    public static String authoTokenKey = "ywxt2019v1";
    // md5加密key（不要随意改动）
    public static String md5Key = "ywxt2019";

    // telegram bot
    public static String telegramBotUrl = "https://api.telegram.org/bot739033903:AAHMeAiArCp6jmQIE3u3MT_wkV_vFX2oNK4";
    public static Map<String, String> telegramBotActions = new HashMap<String, String>() {{
        put("SEND_MESSAGE", "/sendMessage");
    }};
    public static String telegramChatId = "-374037814";

    /**
     * godaddy account
     */
    public static String godaddyUrl = "https://api.godaddy.com";
    public static Map<String, String> godaddyActions = new HashMap<String, String>() {{
        put("GET_DOMAIN_LIST", "/v1/domains");
        put("GET_CERTIFICATE_LIST", "/v1/certificates");
    }};

    // 报警阈值参数
    public static Map<String, String> alertThresholds = new HashMap<String, String>() {{
        // ali余额阈值
        put("ALI_ACCOUNT_BALANCE", "10000.00");
        // ali ecs 服务器剩余时间阈值（单位 天）
        put("ALI_ECS_EXPIRED_DAY", "30");
        // godaddy domain 域名剩余时间阈值（单位 天）
        put("GODADDY_DOMAIN_EXPIRED_DAY", "30");
        // godaddy certificate 证书剩余时间阈值（单位 天）
        put("GODADDY_CERTIFICATE_EXPIRED_DAY", "30");
    }};

    // message 消息
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
    public static Map<String, String> MessageActions = new HashMap<String, String>() {{
        put("ERROR", df.format(new Date()) + "\r\n错误类：{class}\r\n错误信息{message}");
        put("ALI_ACCOUNT_NO_MONEY", df.format(new Date()) + "\r\n您好，阿里云账号余额已少于" + Parameter.alertThresholds.get("ALI_ACCOUNT_BALANCE") + "元，请及时充值。\r\n友情链接：https://www.aliyun.com/\r\n账号：{accountName}\r\n当前余额{balance}");
        put("ALI_ECS_EXPIRED", df.format(new Date()) + "\r\n您好，阿里云服务器有效时间已少于" + Parameter.alertThresholds.get("ALI_ECS_EXPIRED_DAY") + "天，请及时续费。\r\n友情链接：https://www.aliyun.com/\r\n账号：{accountName}\r\n服务器ID:{ecsId}\r\n服务器名称:{ecsName}\r\n过期时间：{expiredTime}");
        put("GODADDY_DOMAIN_EXPIRED", df.format(new Date()) + "\r\n您好，Godaddy域名有效时间已少于" + Parameter.alertThresholds.get("GODADDY_DOMAIN_EXPIRED_DAY") + "天，请及时续费。\r\n友情链接：https://www.godaddy.com/\r\n账号：{accountName}\r\n域名ID:{domainId}\r\n域名:{domain}\r\n过期时间：{expiredTime}\r\n到期是否受保护：{expirationProtected}");
        put("GODADDY_CERTIFICATE_EXPIRED", df.format(new Date()) + "\r\n您好，Godaddy证书有效时间已少于" + Parameter.alertThresholds.get("GODADDY_CERTIFICATE_EXPIRED_DAY") + "天，请及时续费。\r\n友情链接：https://www.godaddy.com/\r\n账号：{accountName}\r\n证书ID:{certificateId}\r\n域名:{domain}\r\n过期时间：{expiredTime}\r\n主体备选域名：{subjectAlternativeNames}");
    }};
    public static Map<String, String> MessageTitles = new HashMap<String, String>() {{
        put("ALI_ACCOUNT_NO_MONEY", "续费");
        put("ALI_ECS_EXPIRED", "过期");
        put("GODADDY_DOMAIN_EXPIRED", "过期");
        put("GODADDY_CERTIFICATE_EXPIRED", "过期");
    }};
}
