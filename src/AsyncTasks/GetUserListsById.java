package AsyncTasks;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import NoteObjects.ShoppingList;
import RequestsAndServer.HttpGetRequest;
import android.os.AsyncTask;

import com.example.noteversion1.utils.ConstService;
import com.google.gson.Gson;

public class GetUserListsById extends AsyncTask<String, Void, List<ShoppingList>>
{
	private OnFinishedListener caller;
	
    public interface OnFinishedListener 
    {
        public void onSuccess(List<ShoppingList> list);
        public void onError();
    }
    
	public GetUserListsById(OnFinishedListener caller)
	{
		this.caller = caller;
	}
	@Override
	protected List<ShoppingList> doInBackground(String... params) 
	{
		String userId = params[0];
		HttpGetRequest request = new HttpGetRequest(ConstService.SERVER_URL + ConstService.SERVER_GET_USER_LISTS_SERVLET);
		request.addParamerters(ConstService.URL_PARAM_USERT_ID, userId);
		
		List<ShoppingList> arrayToReturn = null;
		try {
			request.execute();
			String body = request.getResponseBody();
			ShoppingList[] lists = new Gson().fromJson(body, ShoppingList[].class);
			arrayToReturn = Arrays.asList(lists);
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
	protected void onPostExecute(List<ShoppingList> result) 
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
