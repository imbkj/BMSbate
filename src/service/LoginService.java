package service;

import java.util.List;


import Model.LoginModel;



public interface LoginService {
	public abstract  List<LoginModel> getlist();
	public abstract  LoginModel getmodel();
}
