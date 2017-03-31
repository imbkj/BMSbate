package Controller.EmSheBao;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmShebaoBJModel;
import bll.EmSheBao.Emsc_DeclareSelBll;

public class Emsi_Bj_ListController {
	private String sql = Executions.getCurrent().getArg().get("sql").toString();
	private int ownmonth = Integer.parseInt(Executions.getCurrent().getArg()
			.get("ownmonth").toString());
	private List<EmShebaoBJModel> bjList;
	private int count;

	public Emsi_Bj_ListController() {
		Emsc_DeclareSelBll selBll = new Emsc_DeclareSelBll();
		bjList = selBll.getBjList(sql, ownmonth);
		count = bjList.size();
	}

	public List<EmShebaoBJModel> getBjList() {
		return bjList;
	}

	public int getCount() {
		return count;
	}

}
