package NoteObjects;


public class NoteContactInList
{
	private NoteContact userInfo;
	private boolean isSelected;
	
	public NoteContactInList(NoteContact userInfo) 
	{
		this.userInfo = userInfo;
		isSelected = false;
	}

	public NoteContact getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(NoteContact userInfo) {
		this.userInfo = userInfo;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

}
