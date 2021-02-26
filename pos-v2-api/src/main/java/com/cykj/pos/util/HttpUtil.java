package com.cykj.pos.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {

	public static String doGet(String geturl, String params) {
		String realUrl = geturl + "?" + params;
		System.out.println(realUrl);

		try {
			// 1.通过�? URL 上调�? openConnection 方法创建连接对象
			URL url = new URL(realUrl);
			URLConnection conn = url.openConnection();

			// 2.处理设置参数和一般请求属�?
			// 2.1设置参数
			// 可以根据请求的需要设置参�?
			conn.setUseCaches(false);
			conn.setConnectTimeout(5000); // 请求超时时间

			// 2.2请求属�??
			// 设置通用的请求属�? 更多的头字段信息可以查阅HTTP协议
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");

			conn.setRequestProperty("contentType", "UTF-8");
			// 3.使用 connect 方法建立到远程对象的实际连接�?
			conn.connect();

			// 4.远程对象变为可用。远程对象的头字段和内容变为可访问�??
			// 4.1获取响应的头字段
			Map<String, List<String>> headers = conn.getHeaderFields();
			System.out.println(headers); // 输出头字�?

			// 4.2获取响应正文
			BufferedReader reader = null;
			StringBuffer resultBuffer = new StringBuffer();
			String tempLine = null;

			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
			// System.out.println(resultBuffer);
			reader.close();
			return resultBuffer.toString();
		} catch (MalformedURLException e) {
			// TODO 自动生成�? catch �?
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成�? catch �?
			e.printStackTrace();
		} finally {

		}
		return null;

	}

	public static String doPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连�?
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属�?
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("contentType", "UTF-8");
			// 发�?�POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发�?�请求参�?
			out.print(param);
			System.out.println(param + "##################################");
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响�?
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += "/n" + line;
			}
		} catch (Exception e) {
			System.out.println("发�?�POST请求出现异常�?" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流�?�输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String getOsAndBrowserInfo(HttpServletRequest request) {
		String browserDetails = request.getHeader("User-Agent");
		String userAgent = browserDetails;
		String user = userAgent.toLowerCase();

		String os = "";
		String browser = "";

		// =================OS Info=======================
		if (userAgent.toLowerCase().indexOf("windows") >= 0) {
			os += "Windows";
		}
		if (userAgent.toLowerCase().indexOf("micromessenger") >= 0) {
			os += "weixin";
		}
		if (userAgent.toLowerCase().indexOf("mac") >= 0) {
			os += "Mac";
		}
		if (userAgent.toLowerCase().indexOf("x11") >= 0) {
			os += "Unix";
		}
		if (userAgent.toLowerCase().indexOf("android") >= 0) {
			os += "Android";
		}
		if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
			os += "IPhone";
		}
		{
			os += "UnKnown, More-Info: " + userAgent;
		}
		// ===============Browser===========================
		if (user.contains("edge")) {
			browser = (userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("msie")) {
			String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
			browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
		} else if (user.contains("safari") && user.contains("version")) {
			browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-"
					+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		} else if (user.contains("opr") || user.contains("opera")) {
			if (user.contains("opera")) {
				browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-"
						+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			} else if (user.contains("opr")) {
				browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
						.replace("OPR", "Opera");
			}

		} else if (user.contains("chrome")) {
			browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
		} else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)
				|| (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1)
				|| (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
			browser = "Netscape-?";

		} else if (user.contains("firefox")) {
			browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("rv")) {
			String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
			browser = "IE" + IEVersion.substring(0, IEVersion.length() - 1);
		} else {
			browser = "UnKnown, More-Info: " + userAgent;
		}

		return os + " --- " + browser;
	}

	public static void main(String[] args) {
		String s = "云南";
		s = URLEncoder.encode(s);

		String str = "http://localhost:8080/tw_system/PushController_4S/sendMessage_Sign.action";
		String parm = "ticker=" + s
				+ "&title=bbbbb&text=bbbbb&extrafield_key=bbbbb&extrafield_value=bbbb&app_type=2&alias=77f15c6704974bd1a8dd3dd9e71c10da";

		HttpUtil httpUtil = new HttpUtil();
		// String str ="http://www.sohu.com";
		httpUtil.doGet(str, parm);
	}

}
