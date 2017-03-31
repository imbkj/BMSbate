package Controller.EmCensus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmCensusModel;
import Model.EmbaseModel;
import bll.EmCensus.EmCensus_OperateBll;
import bll.EmCensus.EmCensus_SelectBll;

public class EmCensus_ClientComfirmComtroller {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tperid ;

	EmCensus_SelectBll bll = new EmCensus_SelectBll();
	List<EmCensusModel> list = bll.getEmCensusInfo(" and emhj_id=" + id);
	EmCensusModel model = new EmCensusModel();

	public EmCensus_ClientComfirmComtroller() {
		if( Executions.getCurrent().getArg().get("id")!=null)
		{
			tperid= (String)Executions.getCurrent().getArg().get("id");
		}
		if (list.size() > 0) {
			model = list.get(0);
			if (model.getEmhj_taprid() == null&&tperid!=null&&!tperid.equals("")) {
				model.setEmhj_taprid(Integer.parseInt(tperid));
			}
		}
	}

	// 确认提交
	@Command
	public void submit(@BindingParam("win") Window win) {
		String sql = ",emhj_state=0";
		model.setOperateinfo("客服确认落户信息");
		EmCensus_OperateBll bll = new EmCensus_OperateBll();
		String[] strs = bll.EmCensusUpdate(model, sql, 0);
		if (strs[0] == "1") {
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show(strs[1], "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 退回
	@Command
	public void back(@BindingParam("win") Window win) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("tarpid", tperid);
		map.put("flag", "1");
		map.put("addname", model.getEmhj_addname());
		Window window = (Window) Executions.createComponents(
				"../EmCensus/EmCensus_ClientBack.zul", null, map);
		window.doModal();
		if (map.get("flag").equals("2")) {
			win.detach();
		}
	}

	// 查看报价单
	@Command
	public void check() {
		EmbaseModel embaModel=bll.getEmbaId(model.getGid());
		Map map = new HashMap<>();
		map.put("embaId", embaModel.getEmba_id());

		Window window = (Window) Executions.createComponents(
				"../Embase/EmQuotation.zul", null, map);
		window.doModal();
	}

	public EmCensusModel getModel() {
		return model;
	}

	public void setModel(EmCensusModel model) {
		this.model = model;
	}

}
