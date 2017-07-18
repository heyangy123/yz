package com.pay;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.base.CoreConstants;
import com.unionpay.acp.sdk.SDKConfig;

/**
 * 支付宝配置文件加载
 */
public class PayPropertySupport {

	private static final String CONFIG_FILE = CoreConstants.PAY_PATH + "pay.properties";
	private static final String CONFIG_FILE_UPMP = CoreConstants.PAY_PATH;
	protected static Log logger = LogFactory.getLog(PayPropertySupport.class);

	protected static Properties p = new Properties();

	static {
		init(CONFIG_FILE);
		SDKConfig.getConfig().loadPropertiesFromPath(PayPropertySupport.class.getClassLoader().getResource(CONFIG_FILE_UPMP).getPath());
	}

	protected static void init(String propertyFileName) {
		InputStream in = null;
		try {
			in = PayPropertySupport.class.getClassLoader().getResourceAsStream(propertyFileName);
			if (in != null) {
				p.load(in);
				if (logger.isInfoEnabled()) {
					logger.info("支付宝配置文件加载成功~");
				}
			}
		} catch (IOException e) {
			logger.error("装载 [" + propertyFileName + "] 异常:" + e.getMessage(), e);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					in = null;
					logger.error("释放 [" + propertyFileName + "] 异常:" + e.getMessage(), e);
				}
		}
	}

	public static String getProperty(String key, String defaultValue) {
		return StringUtils.defaultIfEmpty(p.getProperty(key), defaultValue);
	}

	public static String getProperty(String key) {
		return p.getProperty(key);
	}

	public static void reload() {
		reload(CONFIG_FILE);
	}

	public static void reload(String filename) {
		if (p != null) {
			p.clear();
		}
		init(filename);
	}
}
