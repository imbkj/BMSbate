package Controller.CoPolicyNotice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.CoPolicyNotice.PoNo_SelectBll;

import Model.CoPolicyNoticeModel;

public class Pono_PubInfoListController {
	private Object cpnr_type = Executions.getCurrent().getArg()
			.get("cpnt_type");// 业务类型
	private Object cpnr_data_id = Executions.getCurrent().getArg().get("cpnr_data_id");// 业务id
	private PoNo_SelectBll bll = new PoNo_SelectBll();
	private String sql = " and cpnr_type='"+cpnr_type+"' and cpnr_data_id="+cpnr_data_id;
	private List<CoPolicyNoticeModel> list = bll.getNoticeRelationList(sql);

	// 打开详细页面
	@Command
	public void detail(@BindingParam("model") CoPolicyNoticeModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"../CoPolicyNotice/Pono_DetailInfo.zul", null, map);
		window.doModal();
	}

	public List<CoPolicyNoticeModel> getList() {
		return list;
	}

	public void setList(List<CoPolicyNoticeModel> list) {
		this.list = list;
	}

}
