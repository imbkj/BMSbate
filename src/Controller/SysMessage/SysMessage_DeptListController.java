package Controller.SysMessage;

import java.util.List;

import Model.LoginModel;
import bll.SysMessage.Message_SelectBll;

public class SysMessage_DeptListController {
	private Message_SelectBll bll=new Message_SelectBll();
	private List<LoginModel> deptlist=bll.getDepartmentList();
	public List<LoginModel> getDeptlist() {
		return deptlist;
	}
	public void setDeptlist(List<LoginModel> deptlist) {
		this.deptlist = deptlist;
	}
	
	
}
