package utils;

public class ConstService 
{
	//SERVER CONST
	public static final String SERVER_URL = "http://194.153.101.72:8888";
	public static final String SERVER_GET_LIST_SERVLET = "/getList";
	public static final String SERVER_ADD_ITEMS_SERVLET = "/addItems";
	public static final String SERVER_CREATE_LIST_SERVLET = "/createList";
	public static final String SERVER_GET_USER_LISTS_SERVLET = "/getUserLists";
	public static final String SERVER_CREATE_USER_SERVLET = "/createUser"; 
	public static final String SERVER_GET_UNSIGNED_USERS = "/getUnsignedUsers";
	public static final String URL_PARAM_LIST_ID = "listid";
	public static final String URL_PARAM_USERT_ID = "userid";
	public static final String URL_PARAM_USERS_LIST = "usersList";
	public static final String URL_PARAM_USER_PHONE = "phone";
	
	//JSON CONST
	//LIST
	public static final String LIST_NAME = "name";
	public static final String LIST_ITEMS = "items";
	public static final String LIST_USERS = "users";
	
	public static final String USER_NAME = "name";
	public static final String USER_PHONE = "phone";
	public static final String USER_ANDROID_ID = "aid";
	public static final String USER_REGESTRATION_ID = "regId";
	
	public static final String BUNDLE_NEW_LIST = "newList";
	public static final String BUNDLE_LIST_ID = "listId";
	
	public static final String PREF_USER_ID = "userid";
	public static final String PREF_USER_NICK = "nick";
	public static final String PREF_DEFAULT = "default";
	public static final String PREF_PHONE_NUM = "phone";

	public static final String PREF_REGESTRATION_ID = "regid";
	public static final String PREF_REGESTRATION_VERSION_KEY = "regVersion";
	
	
	public static final String ITEM_ADDER_PATTERN = "&(.*)$";
	public static final String ITEM_ADDER_SEPERATOR = "&";
	
	public static final String APP_SENDER_ID = "1057836653958";
	public static final String REGESTATION_VERSION = "1";	
}
