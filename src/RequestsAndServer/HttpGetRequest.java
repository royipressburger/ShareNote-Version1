package RequestsAndServer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

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
    	httpGet.addHeader("Content-Type", "charset=utf-8");
    	response =  httpClient.execute(httpGet);
    }
}
