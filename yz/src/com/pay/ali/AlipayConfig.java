package com.pay.ali;

import com.pay.PayPropertySupport;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "";
	// 商户的私钥
	public static String key = "";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\alipay\\";
	

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";
	
	public static final String app_id = PayPropertySupport.getProperty("pay.ali.appId");;
	
	public static final String seller_id = PayPropertySupport.getProperty("pay.ali.sellerId");
	
	
	//public static final String private_key = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI25oLygr76lr0MLpX6s8o7MQD34TtolUXINAIqGd8UH6d+s+RN9VcebYsa/hfzBc5kpMoWOgE8svMfsWHHD0TOz8SkghxZO7r6MrQjSqqfeqlMcr1ww2KNmtfJ12DnNwDuM2bsRM6YnbredNFP+aIMPN/+AsJaVbZrAyy+i9FQ7AgMBAAECgYBIj6u+J78TKx0qhIH882tUtTqG/+TPR6AFdqa6RdJ4bG7PeG4mDlum0OWnwFNHb3xSBIe7XZRP9SXKbjVyOcGXo8EJZzL4ytBcWLLbM/dLFbwFIHJJ1MpkC6OImWLv/Ho+jLhfM4VoPET/EiTRjbPJtQH3kfyvaMPyALyoYq7BYQJBAN45+BRBehPCCoM9WjB7OnW1u/mRpFQSahu6+WalxYDgahMiMx3gW5Li14x6hzeHArvvgK2lJyeslAkjUPu1JVkCQQCjQ6RIMOtOCot4a8LoDWg2zlRcPCR7nmBPVRc0P9tB0zTdeR9V8IWS707ObSQdhsK2CrUV8Cgkl5O0ibG0kw+zAkBZMWsgticJRxhxFG7ehpcwJ4EK6inKlJmCB8mSKXbcAzaP92oM07/tdJ6PA57B6q5uP/Klv2O3yMyh3krivBIBAkB6D2cyRciWRxHwzPgiD2v7lZTChIBuH4E3rpEzkg41j1c1wYlo8fKbYOjVMgnPPWIzQZwTM8J/YQef8VX3f/87AkBGwPRgWksYHk5q/im68W346ZNVXXBDejo0meb7uSlj9ijww0EkZBQirYTP/UXslcYtkTGWV9yBmFGgvSgGTTFU";
	
	public static final String private_key = PayPropertySupport.getProperty("pay.ali.privateKey");
	
	public static final String notify_url = PayPropertySupport.getProperty("pay.ali.notifyUrl");
	
	public static final String return_url = PayPropertySupport.getProperty("pay.ali.returnUrl");
	
	public static final String refund_url = PayPropertySupport.getProperty("pay.ali.returnUrl");
	
	
	//public static final String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNuaC8oK++pa9DC6V+rPKOzEA9+E7aJVFyDQCKhnfFB+nfrPkTfVXHm2LGv4X8wXOZKTKFjoBPLLzH7Fhxw9Ezs/EpIIcWTu6+jK0I0qqn3qpTHK9cMNijZrXyddg5zcA7jNm7ETOmJ263nTRT/miDDzf/gLCWlW2awMsvovRUOwIDAQAB";

	public static final String ali_public_key = PayPropertySupport.getProperty("pay.ali.publicKey");
	
	public static final String ALIPAY = "alipay";
	
	public static final String WXPAY = "wxpay";

}
