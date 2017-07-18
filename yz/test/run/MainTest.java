package run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import com.base.util.SpringContextUtil;
import com.unionpay.acp.sdk.HttpClient;

public class MainTest {

	public static void main(String[] args) throws Exception {
		//SpringContextUtil.setSpringContext(new FileSystemXmlApplicationContext("test/applicationContext.xml"));
		
//		SpringContextUtil.setSpringContext(new FileSystemXmlApplicationContext("config/spring/spring-main.xml"));
//		Map<String,Object> ret = SpringContextUtil.getBeansWithAnnotation(Controller.class);
//		for (int i = 0; i < ret.keySet().size(); i++) {
//			System.out.println(ret.keySet().toArray()[i]);
//		}

		URL url = new URL("http://192.168.1.159:8080/fx/mobile?methodno=MQuickShopping&debug=1&deviceid=1");
		InputStreamReader isr = new InputStreamReader(url.openStream());
		BufferedReader br = new BufferedReader(isr);
		String str;
		StringBuffer buf = new StringBuffer();
		while ((str = br.readLine()) != null) {
			buf.append(str);
		}
		System.out.println(buf.toString());
	}

}
