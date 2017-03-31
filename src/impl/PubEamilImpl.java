package impl;

import dal.PubEmailDal;
import Model.PubEmailModel;
import service.PubEmailService;

public class PubEamilImpl implements PubEmailService {

	private PubEmailModel md = new PubEmailModel();
	private int gid;
	private String titel;
	private String content;
	private String username;
	private int ifhtml;

	public PubEamilImpl() {

	}

	public PubEamilImpl(PubEmailModel md) {
		super();
		this.md = md;
	}
	
	public PubEamilImpl(int gid,String titel, String content,String username, int ifhtml) {
		super();
		this.gid=gid;
		this.titel=titel;
		this.content=content;
		this.username=username;
		this.ifhtml=ifhtml;
		
	}

	@Override
	public int EmailAdd() {
		int row = 0;
		PubEmailDal dal = new PubEmailDal();
		row = dal.EmailAdd(md);
		return row;
	}

	public PubEmailModel getMd() {
		return md;
	}

	public void setMd(PubEmailModel md) {
		this.md = md;
	}

	@Override
	public int EmailAdd(int gid,String titel, String content,String username, int ifhtml,int esda_id,String email) {
		// TODO Auto-generated method stub
		int row = 0;
		PubEmailDal dal = new PubEmailDal();
		row = dal.EmailAdd(gid,titel,content,username, ifhtml,esda_id,email);
		return row;
	}


}
