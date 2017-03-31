package impl.SystemControl;

import java.util.ArrayList;
import java.util.List;


import Model.LoginModel;
import service.LoginService;
import bll.SystemControl.Data_PopedomBll;

public class LoginIpml implements LoginService {
	private String Log_name;
	private int log_id;
	
	public LoginIpml(String str)
	{
		Log_name=str;
	}
	
	public LoginIpml(int id)
	{
		log_id=id;
	}


	@Override
	public List<LoginModel> getlist() {
		
		Data_PopedomBll datapbll =new Data_PopedomBll();
		return datapbll.getloginlist();
		
	}

	@Override
	public LoginModel getmodel() {
		// TODO Auto-generated method stub
		return null;
	}

}
