package Controller.EmBaseCompact;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import Model.Allinone_ListModel;
import Model.EmBaseCompactListModel;
import bll.EmBaseCompact.EmBaseCompact_BaseBll;

public class EmBaseCompact_BaseinfoController {

	EmBaseCompact_BaseBll bll = new EmBaseCompact_BaseBll();
	
	private ListModelList<Allinone_ListModel> emcompact_chlist;// 显示劳动合同变更数据
	

	public EmBaseCompact_BaseinfoController() throws SQLException {
		String ebco_id = "0";
		try {
			ebco_id = Executions.getCurrent().getArg().get("ebco_id")
					.toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}
				
		emcompact_chlist = new ListModelList<Allinone_ListModel>(
				bll.getemcompact_chlist(ebco_id));// 显示劳动合同数据
	}
	
	
	
	// 查看劳动合同合同
	@Command("emcompact_check")
	public void emcompact_check(@BindingParam("emco") Allinone_ListModel emco) {
		Map arg = new HashMap();
		arg.put("ebcc_id", emco.getAll_t8());
		Window wnd = (Window) Executions.createComponents(
				"../EmBaseCompact/EmBaseCompact_CheckDoc.zul", null, arg);
		wnd.doModal();
	}


	public ListModelList<Allinone_ListModel> getEmcompact_chlist() {
		return emcompact_chlist;
	}

	public void setEmcompact_chlist(
			ListModelList<Allinone_ListModel> emcompact_chlist) {
		this.emcompact_chlist = emcompact_chlist;
	}
	
	
}
