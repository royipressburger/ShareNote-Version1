package NoteObjects;


public class NoteContactInList
{
	private NoteContact userInfo;
	private int color;
	
	public NoteContactInList(NoteContact userInfo, int color) 
	{
		this.userInfo = userInfo;
		this.color = color;
	}

	public NoteContact getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(NoteContact userInfo) {
		this.userInfo = userInfo;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	

}
