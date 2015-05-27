package AsyncTasks;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.json.JSONException;
import org.json.JSONObject;

import RequestsAndServer.HttpGetRequest;

import utils.ConstService;

import android.os.AsyncTask;



/**
 * Send a request for the Server to get shopping list by id.
 * @author Royi
 *
 */
public class GetShoppingListByIdTask extends AsyncTask<String, Void, JSONObject>
{
	private OnFinishedListener mCaller;
	private String error;
    public interface OnFinishedListener 
    {
        public void onSuccess(JSONObject json);
        public void onError(String error);
    }
    
	public GetShoppingListByIdTask(OnFinishedListener caller)
	{
		mCaller = caller;
	}
	
	@Override
	protected JSONObject doInBackground(String... params) 
	{
		String listId = params[0];
		HttpGetRequest newRequest = new HttpGetRequest(ConstService.SERVER_URL + ConstService.SERVER_GET_LIST_SERVLET);
		newRequest.addParamerters(ConstService.URL_PARAM_LIST_ID, listId);
		JSONObject jsonList = null;
		
		try 
		{
			newRequest.execute();
			jsonList = new JSONObject(newRequest.getResponseBody());
		} catch (Exception e){
			error = e.getMessage();
			e.printStackTrace();
		}
		
		return jsonList;
	}
	
	@Override
	protected void onPostExecute(JSONObject result) 
	{
		super.onPostExecute(result);
		
		if (result != null)
		{
			mCaller.onSuccess(result);
		}
		else
		{
			mCaller.onError(error);
		}
	}
}
