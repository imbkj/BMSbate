package Controller.EmBaseCompact;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import bll.EmBaseCompact.EmBaseCompact_UpBll;
import Model.EmBaseCompactBModel;

public class EmBaseCompact_UpController {
	private String gid = "";
	private String ebcc_id = "";
	EmBaseCompact_UpBll bll = new EmBaseCompact_UpBll();
	
	public EmBaseCompact_UpController() {
		try {
			gid = Executions.getCurrent().getArg().get("gid").toString();
		} catch (Exception e) {
			gid = "0";
		}
	}

	private ListModelList<EmBaseCompactBModel> compact_base;// 显示所选合同
	private ListModelList<EmBaseCompactBModel> compact_editbase;// 显示所选合同

	@Init
	public void init() throws SQLException {
		compact_base = new ListModelList<EmBaseCompactBModel>(
				bll.compact_base(gid));// 显示所选合同
		
		try {
			ebcc_id = Executions.getCurrent().getArg().get("ebcc_id1").toString();
		} catch (Exception e) {
			ebcc_id = "0";
		}
		compact_editbase = new ListModelList<EmBaseCompactBModel>(
				bll.compact_editbase(ebcc_id));// 显示所选合同
		
	}

	public ListModelList<EmBaseCompactBModel> getCompact_base() {
		return compact_base;
	}

	public void setCompact_base(ListModelList<EmBaseCompactBModel> compact_base) {
		this.compact_base = compact_base;
	}

	public ListModelList<EmBaseCompactBModel> getCompact_editbase() {
		return compact_editbase;
	}

	public void setCompact_editbase(
			ListModelList<EmBaseCompactBModel> compact_editbase) {
		this.compact_editbase = compact_editbase;
	}

}
