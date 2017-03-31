package service;

public interface PubEmailService {
	
	public abstract int EmailAdd();
	
	public int EmailAdd(int gid,String titel,String content,String username,int ifhtml,int esda_id,String email);

}
