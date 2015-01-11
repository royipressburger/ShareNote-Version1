package AsyncTasks;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;

import com.example.noteversion1.activities.ShoppingListActivity;
import com.example.noteversion1.utils.ConstService;
import com.example.noteversion1.utils.HttpGetRequest;
import com.example.noteversion1.utils.ShoppingList;
import com.example.noteversion1.utils.Utils;


/**
 * Send a request for the Server to get shopping list by id.
 * @author Royi
 *
 */
public class GetShoppingListByIdTask extends AsyncTask<String, Void, JSONObject>
{
	private ShoppingListActivity mCaller;
	
	public GetShoppingListByIdTask(ShoppingListActivity caller)
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
		} catch (HttpResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
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
		
		
		return jsonList;
	}
	
	@Override
	protected void onPostExecute(JSONObject result) 
	{
		ShoppingList listToSet = new ShoppingList();
		try {
			listToSet.setName(result.getString(ConstService.LIST_NAME));
			listToSet.setItems(Utils.jsonArrayToList(result.getJSONArray(ConstService.LIST_ITEMS)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mCaller.setShoppingList(listToSet);
		mCaller.asyncTaskGetListEndded();
	}
}
