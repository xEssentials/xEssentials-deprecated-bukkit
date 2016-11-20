package tv.mineinthebox.simpleserver;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * The HttpClient is responsible for fetching the sent header from the client
 */
public class HttpClient {

	private final String[] args;

	/*
	 * [0]GET /index.html HTTP/1.1
	 * [1]Host: 127.0.0.1:8080
	 * [2]User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0
	 * [3]Accept-Language: nl,en-US;q=0.7,en;q=0.3
	 * [4] postdata...
	 */

	/**
	 * the constructor of HttpClient, its encouraged not to instance it at your own
	 * 
	 * @param args the arguments such as Content-Length and other header data
	 */
	public HttpClient(String[] args) {
		this.args = args;
	}

	/**
	 * returns the requested url by the client
	 * 
	 * @return String
	 */
	public String getUrl() {
		return args[0].replace("GET ", "").replace("POST ", "").replace(" HTTP/1.1", "");
	}

	/**
	 * returns true whenever the request was based on get otherwise false
	 * 
	 * @return boolean
	 */
	public boolean isGet() {
		return args[0].startsWith("GET");
	}

	/**
	 * returns true whenever the request was based on post otherwise false
	 * @return boolean
	 */
	public boolean isPost() {
		return args[0].startsWith("POST");
	}

	/**
	 * returns the host mentoid in the clients header
	 * 
	 * @return String
	 */
	public String getHost() {
		return args[1];
	}

	/**
	 * returns the browsers agent of the client
	 * 
	 * @return String
	 */
	public String getUserAgent() {
		return args[2];
	}

	/**
	 * returns the browsers language of the client
	 * 
	 * @return String
	 */
	public String getLanguage() {
		return args[3].split(":")[1].split(",")[0];
	}

	/**
	 * returns true if the request has form parameters for either POST and GET
	 * 
	 * @return boolean
	 */
	public boolean hasFormParameters() {
		if(args.length == 5) {
			return (args[4] != null || getUrl().matches("?(.*)=") ? true : false);
		}
		return false;
	}

	/**
	 * returns the wrapped version of parameters whereas the keys are the input field names and the values are the values of the fields
	 * 
	 * @return Map<String, String>
	 * @see HttpClient#hasFormParameters()
	 */
	public Map<String, String> getFormParameters() {
		Map<String, String> map = new HashMap<String, String>();

		if(getUrl().matches("?(.*)=")) {
			String data = getUrl().substring(getUrl().indexOf("?"), getUrl().length());
			if(data.contains("&")) {
				String[] sets = data.split("&");
				for(String set : sets) {
					String[] entryset = set.split("=");
					try {
						String key = URLDecoder.decode(entryset[0], "UTF-8");
						String value = URLDecoder.decode(entryset[1], "UTF-8");
						map.put(key, value);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				String[] entryset = data.split("=");
				try {
					String key = URLDecoder.decode(entryset[0], "UTF-8");
					String value = URLDecoder.decode(entryset[1], "UTF-8");
					map.put(key, value);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			if(args[4].contains("&")) {
				String[] sets = args[4].split("&");
				for(String set : sets) {
					String[] entryset = set.split("=");
					try {
						String key = URLDecoder.decode(entryset[0], "UTF-8");
						String value = URLDecoder.decode(entryset[1], "UTF-8");
						map.put(key, value);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				String[] entryset = args[4].split("=");

				try {
					String key = URLDecoder.decode(entryset[0], "UTF-8");
					String value = URLDecoder.decode(entryset[1], "UTF-8");
					map.put(key, value);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
}
