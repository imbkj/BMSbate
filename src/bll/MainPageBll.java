package bll;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.MainPageDal;

import Model.PubCallModel;

public class MainPageBll {
	//获取待办事宜列表
	public static List<PubCallModel> getpubcallList(String username) throws SQLException {
		List<PubCallModel> list = new ArrayList<PubCallModel>();
		MainPageDal dal = new MainPageDal();
		list = dal.getpubcallList(username);
		return list;
	}
}
