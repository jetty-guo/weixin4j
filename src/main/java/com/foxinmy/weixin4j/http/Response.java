package com.foxinmy.weixin4j.http;

import java.io.InputStream;
import java.io.Serializable;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.WeixinProxy;
import com.foxinmy.weixin4j.msg.ErrorMessage;
import com.thoughtworks.xstream.XStream;

public class Response {

	private final String ERROR_CODE_KEY = "errcode";
	private final String ERROR_MSG_KEY = "errmsg";

	private String text;
	private int statusCode;
	private String statusText;
	private byte[] body;
	private InputStream stream;

	public Response(String text) {
		this.text = text;
	}

	public String getAsString() {
		return text;
	}

	public JSONObject getAsJson() {
		return JSON.parseObject(text);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAsObject(Class<? extends Serializable> clazz) {
		return (T) JSON.parseObject(text, clazz);
	}

	public Object getAsXml() {
		XStream xs = new XStream();
		xs.autodetectAnnotations(true);
		return xs.fromXML(text);
	}

	/**
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E5%8F%A3%E9%A2%91%E7%8E%87%E9%99%90%E5%88%B6%E8%AF%B4%E6%98%8E"
	 * >全局返回码</a> {"errcode":45009,"errmsg":"api freq out of limit"}
	 * 
	 * @return
	 * @throws DocumentException
	 */
	public ErrorMessage getErrorMsg() throws DocumentException {
		JSONObject jsonObj = getAsJson();
		if (jsonObj.containsKey(ERROR_CODE_KEY)) {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(WeixinProxy.class
					.getResourceAsStream("error.xml"));
			Node node = doc.getRootElement().selectSingleNode(
					String.format("error[@code='%d']",
							jsonObj.getInteger(ERROR_CODE_KEY)));
			if (node != null) {
				return new ErrorMessage(jsonObj.getInteger(ERROR_CODE_KEY),
						jsonObj.getString(ERROR_MSG_KEY), node.getStringValue());
			}
			return new ErrorMessage(jsonObj.getInteger(ERROR_CODE_KEY),
					"unknown error", "未知错误");
		}

		return new ErrorMessage(0, "request success", "");
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[Response text=").append(text);
		sb.append(", statusCode=").append(statusCode);
		sb.append(", statusText=").append(statusText).append("]");
		return sb.toString();
	}

}