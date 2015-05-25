package RequestsAndServer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public abstract class AbstractHttpRequest 
{
	protected HttpClient httpClient;
	protected String url;
	protected List<NameValuePair> urlParams;
	protected HttpResponse response;

	public AbstractHttpRequest(String url)
	{
		this.url = url;
		httpClient = new DefaultHttpClient();
		urlParams = new LinkedList<NameValuePair>();
	}

	public void addParamerters(String key, String value)
	{
		urlParams.add(new BasicNameValuePair(key,value));
	}
	
	public void addParamerters(String key, String[] value)
	{
		String val = "";
		for (int i = 0; i < value.length; i++) {
			 val = val + "," + value[i];
		}
		urlParams.add(new BasicNameValuePair(key,val.substring(1)));
	}

	public String getResponseBody() throws HttpResponseException, IOException
	{
		return new BasicResponseHandler().handleResponse(response);
	}

	public HttpResponse getResponse() 
	{
		return response;
	}
	
	protected void addParamsToUrl()
	{
    	if (!urlParams.isEmpty())
    	{
    		url += "?" + URLEncodedUtils.format(urlParams, "utf-8");
    	}
	}
	
	public abstract void execute() throws ClientProtocolException, IOException, URISyntaxException;
}
