package AsyncTasks;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;

import android.os.AsyncTask;

import com.example.noteversion1.activities.ShoppingListActivity;
import com.example.noteversion1.utils.ConstService;
import com.example.noteversion1.utils.HttpPostRequest;

public class AddItemToListTask extends AsyncTask<String, Void, Void>
{
	private ShoppingListActivity mCaller;
	
	public AddItemToListTask(ShoppingListActivity caller)
	{
		mCaller = caller;
	}

	@Override
	protected Void doInBackground(String... params) 
	{
		String listId = params[0];
		HttpPostRequest newRequest = new HttpPostRequest(ConstService.SERVER_URL + ConstService.SERVER_ADD_ITEMS_SERVLET);
		newRequest.addParamerters(ConstService.URL_PARAM_LIST_ID, listId);
		//JSONObject body = new JSONObject();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) 
	{
		super.onPostExecute(result);
		mCaller.listWasUpdated();
	}
}
