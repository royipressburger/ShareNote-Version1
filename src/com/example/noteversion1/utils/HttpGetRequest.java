package com.example.noteversion1.utils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * This Class wrap to Apache Http get request to simplify the process
 * @author Royi
 *
 */
public class HttpGetRequest 
{

	private HttpClient httpClient;
    private HttpGet httpGet;
    private String url;
    private List<NameValuePair> urlParams;
    private HttpResponse response;
    
    public HttpGetRequest(String url)
    {
    	this.url = url;
    	httpClient = new DefaultHttpClient();
    	urlParams = new LinkedList<NameValuePair>();
    }
    
    public void execute() throws ClientProtocolException, IOException
    {
    	if (!urlParams.isEmpty())
    	{
    		url += "?" + URLEncodedUtils.format(urlParams, "utf-8");
    	}
    	
    	httpGet = new HttpGet(url);
    	response =  httpClient.execute(httpGet);
    }
    
    public void addParamerters(String key, String value)
    {
    	urlParams.add(new BasicNameValuePair(key,value));
    }
    
    public String getResponseBody() throws HttpResponseException, IOException
    {
    	return new BasicResponseHandler().handleResponse(response);
    }
    
	public HttpResponse getResponse() 
	{
		return response;
	}
}
