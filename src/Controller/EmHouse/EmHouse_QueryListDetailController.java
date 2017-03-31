package Controller.EmHouse;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import bll.EmHouse.EmHouse_QueryListBll;

import Model.EmHouseChangeModel;

public class EmHouse_QueryListDetailController {

	private List<EmHouseChangeModel> list = new ListModelList<>();

	private EmHouse_QueryListBll bll = new EmHouse_QueryListBll();

	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("tid").toString());
	
	public EmHouse_QueryListDetailController() {
		list=bll.getList(id);
	}

	public List<EmHouseChangeModel> getList() {
		return list;
	}

	public void setList(List<EmHouseChangeModel> list) {
		this.list = list;
	}

}
