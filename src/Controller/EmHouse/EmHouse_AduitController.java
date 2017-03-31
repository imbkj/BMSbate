package Controller.EmHouse;

import impl.WorkflowCore.WfOperateImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;
import bll.EmHouse.EmHouse_EditBll;
import Model.EmHouseChangeModel;


public class EmHouse_AduitController {
	private List<EmHouseChangeModel> list = new ListModelList<>();
	private EmHouse_EditBll bll = new EmHouse_EditBll();

	private Window win = (Window) Path.getComponent("/winaduit");
	private Integer embaId;

	public EmHouse_AduitController() {
		try {
			embaId=Integer.valueOf(Executions.getCurrent().getArg()
					.get("daid").toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		setList();
	}

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		if (winD == null) {
			win = winD;
		}

	}

	@Command
	@NotifyChange("list")
	public void submit(@BindingParam("a") EmHouseChangeModel em) {
		/*
		WfBusinessService wfbs = new EmHouse_EditImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		EmHouseChangeModel ecm = new EmHouseChangeModel();
		ecm.setEmhc_id(em.getEmhc_id());
		ecm.setEmhc_ifdeclare(0);
		ecm.setEmhc_remark(em.getEmhc_remark()+";"+UserInfo.getUsername()+"审核:同意"+"");
		Object[] obj = { "审核", ecm };
		String[] str = wfs.PassToNext(obj, em.getEmhc_tapr_id(),
				UserInfo.getUsername(), "", 0, "");
		if (str[0].equals("1")) {
			Messagebox.show("操作成功.", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			setList();
		}
		*/
		Map map = new HashMap();
		map.put("ecm", em);
		Window window = (Window) Executions
				.createComponents(
						"/EmHouse/Emhouse_AduitRemark.zul",
						null, map);
		window.doModal();
		setList();
	}

	public List<EmHouseChangeModel> getList() {
		return list;
	}

	public void setList() {
		EmHouseChangeModel em = new EmHouseChangeModel();
		em.setEmhc_ifdeclare(5);
		if (embaId!=null) {
			em.setEmhc_id(embaId);
		}
		this.list = bll.getChangeList(em);
	}

}
