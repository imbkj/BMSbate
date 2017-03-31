package Controller.EmHouse;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import bll.EmHouse.EmHouse_InfoBll;

import Model.EmHouseModel;
import Util.IdcardUtil;
import Util.StringFormat;

public class Emhouse_historyController {
	private List<EmHouseModel> list = new ListModelList<>();

	private Integer gid = 0;
	private String houseid = "";
	private String idcard = "";
	
	private EmHouse_InfoBll bll = new EmHouse_InfoBll();

	public Emhouse_historyController() {
		if (Executions.getCurrent().getArg().get("gid") != null) {
			gid = Integer.valueOf(Executions.getCurrent().getArg().get("gid")
					.toString());
		}
		
		if (Executions.getCurrent().getArg().get("idcard") != null) {
			idcard = Executions.getCurrent().getArg().get("idcard")
					.toString();
			idcard=StringFormat.replaceSpace(idcard);
			idcard=IdcardUtil.conver15CardTo18(idcard);
		}
		if (Executions.getCurrent().getArg().get("houseid") != null) {
			houseid = Executions.getCurrent().getArg().get("houseid")
					.toString();
			houseid=StringFormat.replaceSpace(houseid);
	
		}
		list=bll.historyList(gid, idcard,houseid);
		
	}

	public List<EmHouseModel> getList() {
		return list;
	}

	public void setList(List<EmHouseModel> list) {
		this.list = list;
	}

}
