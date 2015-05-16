package AsyncTasks;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;

import RequestsAndServer.HttpPostRequest;

import utils.ConstService;

import android.os.AsyncTask;


public class AddItemToListTask extends AsyncTask<String, Void, Boolean>
{
	private OnFinishedListener mCaller;
	
    public interface OnFinishedListener 
    {
        public void onSuccess();
        public void onError();
    }
    
	public AddItemToListTask(OnFinishedListener caller)
	{
		mCaller = caller;
	}

	@Override
	protected Boolean doInBackground(String... params) 
	{
		String listId = params[0];
		HttpPostRequest newRequest = new HttpPostRequest(ConstService.SERVER_URL + ConstService.SERVER_ADD_ITEMS_SERVLET);
		newRequest.addParamerters(ConstService.URL_PARAM_LIST_ID, listId);
		
		boolean isSuccess = true;
		JSONArray itemsArray = new JSONArray();
		for (int i = 1; i < params.length; i++) 
		{
			itemsArray.put(params[i]);
		}
		
			//body.put(ConstService.LIST_ITEMS, itemsArray);
		newRequest.setBody(itemsArray.toString());
		try {
			newRequest.execute();
		} catch (ClientProtocolException e) {
			isSuccess = false;
			e.printStackTrace();
		} catch (IOException e) {
			isSuccess = false;
			e.printStackTrace();
		} catch (URISyntaxException e) {
			isSuccess = false;
			e.printStackTrace();
		}
		
		return isSuccess;
	}
	
	@Override
	protected void onPostExecute(Boolean result) 
	{
		super.onPostExecute(result);
		if (result)
		{
			mCaller.onSuccess();
		}
		else 
		{
			mCaller.onError();
		}
	}
}
