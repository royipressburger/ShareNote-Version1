package AsyncTasks;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import utils.ConstService;
import RequestsAndServer.HttpGetRequest;
import android.os.AsyncTask;

import com.google.gson.Gson;

public class UnsignedUsers extends AsyncTask<String, Void, List<String>>{
	
private OnFinishedListener caller;
	
    public interface OnFinishedListener 
    {
        public void onSuccess(List<String> result);
        public void onError();
    }
    
	public UnsignedUsers(OnFinishedListener caller)
	{
		this.caller = caller;
	}
	@Override
	protected List<String> doInBackground(String... params) 
	{

		System.out.println("!@#!@#!@#!@#");
		
		HttpGetRequest request = new HttpGetRequest(ConstService.SERVER_URL + ConstService.SERVER_GET_UNSIGNED_USERS);
		request.addParamerters(ConstService.URL_PARAM_USERS_LIST, params);
		
		List<String> arrayToReturn = null;
		try {
			request.execute();
			String body = request.getResponseBody();
			String[] unsigned = new Gson().fromJson(body, String[].class);
			arrayToReturn = Arrays.asList(unsigned);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return arrayToReturn;
	}
	
	@Override
	protected void onPostExecute(List<String> result) 
	{
		super.onPostExecute(result);
		if (result != null)
		{
			caller.onSuccess(result);
		}
		else 
		{
			caller.onError();
		}
	}
}
