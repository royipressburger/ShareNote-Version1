package AsyncTasks;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import utils.ConstService;
import utils.SharedPref;
import RequestsAndServer.HttpPostRequest;
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class CreateNewUser extends AsyncTask<String, Void, String> 
{
	private OnFinishedListener listener;
	private Context context;
    public interface OnFinishedListener 
    {
        public void onSuccess(String result);
        public void onError();
    }
    
    public CreateNewUser(OnFinishedListener listner, Context caller)
    {
    	this.listener = listner;
    	context = caller;
    }

	@Override
	protected String doInBackground(String... params) 
	{
		String phone = params[0];
		String nick = params[1];
		String androidId = params[2];
		String regId = params[3];
		if (regId.equals(ConstService.PREF_DEFAULT))
		{
			try 
			{
				regId = GoogleCloudMessaging.getInstance(context).register(ConstService.APP_SENDER_ID);
			} 
			catch (IOException e) 
			{
				
			}
		}
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
			listener.onSuccess(result.replace("\"", ""));
		}
		else
		{
			listener.onError();
		}
		
		super.onPostExecute(result);
	}
}
