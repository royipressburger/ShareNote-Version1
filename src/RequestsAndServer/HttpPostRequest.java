package RequestsAndServer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

public class HttpPostRequest extends AbstractHttpRequest
{

    private HttpPost httpPost;
    private String body;
    
    public HttpPostRequest(String url)
    {
    	super(url);
    	httpPost = new HttpPost();
    }
    
    public void execute() throws ClientProtocolException, IOException, URISyntaxException
    {
    	addParamsToUrl();
    	httpPost.setURI(new URI(url));
    	httpPost.addHeader("Content-Type", "application/json");
    	httpPost.setEntity(new StringEntity(body));
    	response =  httpClient.execute(httpPost);
    }

	public void setBody(String body) 
	{
		this.body = body;
	}
}
