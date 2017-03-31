package bll.SysMessage;

import impl.SysMessageImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import service.SysMessageService;

import dal.CoBase.CoBase_SelectDal;
import dal.SysMessage.Message_OperateDal;
import dal.SysMessage.SysMessage_AddDal;

import Model.CoBaseModel;
import Model.DepartmentListModel;
import Model.LoginModel;
import Model.SysEmailFileModel;
import Model.SysEmailModel;
import Model.SysMessageModel;

public class SysMessage_AddBll {

	// 获取员工列表
	public List<LoginModel> getLoginList(String str) {
		List<LoginModel> list = new ArrayList<LoginModel>();
		SysMessage_AddDal dal = new SysMessage_AddDal();
		list = dal.getLoginList(str);
		return list;
	}

	// 获取部门列表
	public List<DepartmentListModel> getDeptList() throws SQLException {
		List<DepartmentListModel> list = new ArrayList<DepartmentListModel>();
		SysMessage_AddDal dal = new SysMessage_AddDal();
		list = dal.getdeplist();
		return list;
	}

	// 系统短信新增、暂存
	public int SysMessageAdd(SysMessageModel model, List<SysMessageModel> list)
			throws Exception {
		SysMessageService smsv = new SysMessageImpl();
		int issuccess = 0;
		issuccess = smsv.add(model, list);
		return issuccess;
	}

	// 读取收信人信息
	public List<CoBaseModel> getInfoByCid(Integer cid) throws SQLException {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		list = dal.getInfoByCid(cid);
		return list;
	}

	// 读取收信人信息
	public List<CoBaseModel> getInfoByGid(Integer gid) throws SQLException {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		list = dal.getInfoByGid(gid);
		return list;
	}

	// 添加系统发送邮件的记录
	public int addSysEmail(SysEmailModel m) {
		Message_OperateDal dal=new Message_OperateDal();
		return dal.addSysEmail(m);
	}
	
	public int addSysEmailFile(SysEmailFileModel m)
	{
		Message_OperateDal dal=new Message_OperateDal();
		return dal.addSysEmailFile(m);
	}
}
