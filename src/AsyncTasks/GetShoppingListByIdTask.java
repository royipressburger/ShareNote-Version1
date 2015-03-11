package AsyncTasks;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.json.JSONException;
import org.json.JSONObject;

import RequestsAndServer.HttpGetRequest;
import android.os.AsyncTask;

import com.example.noteversion1.utils.ConstService;


/**
 * Send a request for the Server to get shopping list by id.
 * @author Royi
 *
 */
public class GetShoppingListByIdTask extends AsyncTask<String, Void, JSONObject>
{
	private OnFinishedListener mCaller;
	
    public interface OnFinishedListener 
    {
        public void onSuccess(JSONObject json);
        public void onError();
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
		super.onPostExecute(result);
		
		if (result != null)
		{
			mCaller.onSuccess(result);
		}
		else
		{
			mCaller.onError();
		}
	}
}
