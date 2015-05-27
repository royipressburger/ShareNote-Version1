package AsyncTasks;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import RequestsAndServer.HttpPostRequest;
import utils.ConstService;
import android.os.AsyncTask;

public class CreateNewUser extends AsyncTask<String, Void, String> 
{
	private OnFinishedListener caller;
	
    public interface OnFinishedListener 
    {
        public void onSuccess(String result);
        public void onError();
    }
    
    public CreateNewUser(OnFinishedListener caller)
    {
    	this.caller = caller;
    }

	@Override
	protected String doInBackground(String... params) 
	{
		String phone = params[0];
		String nick = params[1];
		String androidId = params[2];
		String regId = params[3];
		
		HttpPostRequest request = new HttpPostRequest(ConstService.SERVER_URL + ConstService.SERVER_CREATE_USER_SERVLET);
		JSONObject json = new JSONObject();
		String userId = null;
		
		try {
			json.put(ConstService.USER_ANDROID_ID, androidId);
			json.put(ConstService.USER_NAME, nick);
			json.put(ConstService.USER_PHONE, phone);
			json.put(ConstService.USER_REGESTRATION_ID, regId);
			request.setBody(json.toString());
			request.execute();
			userId = request.getResponseBody();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userId;
	}
	
	@Override
	protected void onPostExecute(String result) 
	{
		if (result != null && !result.isEmpty())
		{
			caller.onSuccess(result.replace("\"", ""));
		}
		else
		{
			caller.onError();
		}
		
		super.onPostExecute(result);
	}
}
