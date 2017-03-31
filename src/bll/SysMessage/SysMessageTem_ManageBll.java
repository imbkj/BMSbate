package bll.SysMessage;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.SysMessage.SysMessageTem_ManageDal;

import Model.SysMessageModel;

public class SysMessageTem_ManageBll {

	// 获取系统短信模板列表
	public ListModelList<SysMessageModel> gettemList(String str) {
		ListModelList<SysMessageModel> list = new ListModelList<SysMessageModel>();
		SysMessageTem_ManageDal dal = new SysMessageTem_ManageDal();
		list = dal.gettemList(str);
		return list;
	}

	// 修改模板
	public int updateTem(SysMessageModel model) {
		int row = 0;
		SysMessageTem_ManageDal dal = new SysMessageTem_ManageDal();
		row = dal.updateTem(model);
		return row;
	}

	// 删除模板
	public int deleteTem(int pmte_id) {
		int row = 0;
		SysMessageTem_ManageDal dal = new SysMessageTem_ManageDal();
		row = dal.deleteTem(pmte_id);
		return row;
	}
	
	public ListModelList<SysMessageModel> gettemListByClass(String name) {
		ListModelList<SysMessageModel> list = new ListModelList<SysMessageModel>();
		SysMessageTem_ManageDal dal = new SysMessageTem_ManageDal();
		list = dal.gettemListByClass(name);
		return list;
	}
}
