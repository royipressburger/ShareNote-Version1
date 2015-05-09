package activities;

import utils.ConstService;
import utils.SharedPref;
import utils.Utils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import AsyncTasks.CreateNewUser;

import com.idc.milab.mrnote.R;

public class RegisterActivity extends AbsractAppActivity {

	private EditText nickName;
	private EditText phoneNumber;
	private TextView phoneCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		checkIfNewUser();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		phoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
		nickName = (EditText) findViewById(R.id.editTextNickName);
		phoneCode = (TextView) findViewById(R.id.textViewPhoneCode);
		phoneCode.setText("+" + GetCountryZipCode());
	}

	private void checkIfNewUser() 
	{
		SharedPref.initSharedPrefs(getApplicationContext());
		String userid = SharedPref.getSharedPrefsString(ConstService.PREF_USER_ID, ConstService.PREF_DEFAULT);
		if (!userid.equals(ConstService.PREF_DEFAULT))
		{
			launchMainWindow();
		}
	}

	private void launchMainWindow() 
	{
		finish();
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(intent);
	}

	@Override
	public void onButtonNextClicked(View v) 
	{
		if (validateInputs())
		{
			createNewUser();
		}
		else 
		{
			Utils.toastMessage("Please fill name and phone", getApplicationContext());
		}
	}

	@SuppressLint("NewApi")
	private void createNewUser() 
	{
		final String phone = PhoneNumberUtils.formatNumberToE164(phoneCode.getText().toString() + phoneNumber.getText().toString(), "972"); 
		String nick = nickName.getText().toString();
		String android_id = Secure.getString(getApplicationContext().getContentResolver(),Secure.ANDROID_ID); 
		CreateNewUser.OnFinishedListener listenner = new CreateNewUser.OnFinishedListener() 
		{
			@Override
			public void onSuccess(String result) 
			{
				Utils.toastMessage("User Added", getApplicationContext());
				SharedPref.setSharedPrefsString(ConstService.PREF_USER_ID, result);
//				SharedPref.setSharedPrefsString(ConstService.PREF_USER_ID, result);
				SharedPref.setSharedPrefsString(ConstService.PREF_PHONE_NUM, phone);
				launchMainWindow();
			}
			
			@Override
			public void onError() 
			{
				Utils.toastMessage("Cannot create User", getApplicationContext());
			}
		};
		
		CreateNewUser task = new CreateNewUser(listenner);
		task.execute(phone, nick, android_id);
	}

	private boolean validateInputs() 
	{
		boolean legalPhone = phoneNumber.getText().toString() != null && !phoneNumber.getText().toString().isEmpty();
		boolean legalNick = nickName.getText().toString() != null && !nickName.getText().toString().isEmpty();
		
		return legalNick && legalPhone;
	}

	private String GetCountryZipCode(){
		String CountryID="";
		String CountryZipCode="";

		TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		//getNetworkCountryIso
		CountryID = manager.getSimCountryIso().toUpperCase();
		String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
		for(int i=0;i<rl.length;i++){
			String[] g=rl[i].split(",");
			if(g[1].trim().equals(CountryID.trim())){
				CountryZipCode=g[0];
				break;  
			}
		}
		return CountryZipCode;
	}
}
