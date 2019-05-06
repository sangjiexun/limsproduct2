package app.zjcclims;
import java.util.ArrayList;
import java.util.List; 
public class Response {
	private int state;
	private List<Course> mlist;
	
	public Response(int s,List<Course> list)
	{
		state=s;
		mlist =list;
	}
	public List<Course> getMlist() {
		return mlist;
	}
	public void setMlist(List<Course> mlist) {
		this.mlist = mlist;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	

}
