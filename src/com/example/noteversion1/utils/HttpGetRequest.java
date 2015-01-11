package com.example.noteversion1.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
public class HttpGetRequest extends AbstractHttpRequest
{

    private HttpGet httpGet;
    public HttpGetRequest(String url)
    {
    	super(url);
    	httpGet = new HttpGet();
    }
    
    public void execute() throws ClientProtocolException, IOException, URISyntaxException
    {
    	addParamsToUrl();
    	httpGet.setURI(new URI(url));
    	response =  httpClient.execute(httpGet);
    }
}
