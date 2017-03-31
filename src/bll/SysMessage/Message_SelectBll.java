package bll.SysMessage;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.SysMessage.Message_SelectDal;

import Model.LoginModel;
import Model.SysMessageModel;

public class Message_SelectBll {
	private Message_SelectDal dal = new Message_SelectDal();

	// 查询部门
	public List<LoginModel> getDepartmentList() {
		return dal.getDepartmentList();
	}

	// 获取部门和部门员工信息
	public List<LoginModel> getLoginList(String name) {
		List<LoginModel> list = getDepartmentList();
		for (LoginModel m : list) {
			m.setLoginlist(getLoginList(m.getDep_id(), name));
		}
		return list;
	}

	// 根据部门编号查询员工
	public List<LoginModel> getLoginList(Integer dep_id, String name) {
		return dal.getLoginList(dep_id, name);
	}

	// 获取模板
	public List<SysMessageModel> gettemList(String str) {
		return dal.gettemList(str);
	}

	public boolean ifExistMessage() {
		return dal.ifExistMessage();
	}
}
