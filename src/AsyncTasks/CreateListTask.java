package asyncTasks;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.HttpResponseException;
import org.json.JSONException;

import requestsAndServer.HttpPostRequest;

import utils.ConstService;
import utils.Utils;


import NoteObjects.ShoppingList;
import android.os.AsyncTask;

public class CreateListTask extends AsyncTask<ShoppingList, Void, String>
{
	private OnFinishedListener caller;
	
    public interface OnFinishedListener 
    {
        public void onSuccess(String result);
        public void onError();
    }
    
    public CreateListTask(OnFinishedListener caller)
    {
    	this.caller = caller;
    }
    
	@Override
	protected String doInBackground(ShoppingList... params) 
	{
		ShoppingList listToCreate = params[0];
		HttpPostRequest newRequest = new HttpPostRequest(ConstService.SERVER_URL + ConstService.SERVER_CREATE_LIST_SERVLET);
		
		String listId = null;
		try {
			newRequest.setBody(Utils.shoppingListToCreateToJson(listToCreate));
			newRequest.execute();
			listId = newRequest.getResponseBody();
		} 
		catch (HttpResponseException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listId;
	}
	
	@Override
	protected void onPostExecute(String result) 
	{
		super.onPostExecute(result);
		if (result != null && !result.isEmpty() && !result.equals("fail"))
		{
			caller.onSuccess(result);
		}
		else 
		{
			caller.onError();
		}
	}
	
}
