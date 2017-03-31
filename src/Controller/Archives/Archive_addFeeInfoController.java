package Controller.Archives;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.Archives.Archive_FeeManagerBll;

import Controller.EmPay.EmPay_BaseController;
import Model.EmArchivePaymentModel;
import Model.EmArchiveRemarkModel;

public class Archive_addFeeInfoController {
	private EmArchivePaymentModel epm = (EmArchivePaymentModel) Executions
			.getCurrent().getArg().get("epm");
	private List<EmArchiveRemarkModel> rmList = new ListModelList<>();

	private Window win;

	private Archive_FeeManagerBll bll = new Archive_FeeManagerBll();

	public Archive_addFeeInfoController() {
		setRmList(epm.getEmap_id());
		epm.setTotal(epm.getEmap_file().add(epm.getEmap_hj())
				.add(epm.getEmap_op()));
	}

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		win = winD;
	}

	@Command
	@NotifyChange("rmList")
	public void addremark() {
		Map map = new HashMap();
		map.put("rt", 3);
		map.put("id", epm.getEmap_id());
		map.put("id", epm.getGid());
		Window window = (Window) Executions.createComponents(
				"Archive_Remark.zul", null, map);
		window.doModal();
		setRmList(epm.getEmap_id());
	}

	@Command
	public void mod() {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.modpayment(epm);
							if (i > 0) {
								if (epm.getEmap_idlist() != null
										&& !epm.getEmap_idlist().equals("")) {
									bll.movePaymentMod(epm);
								}

								Messagebox.show("操作成功!", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
							} else {
								Messagebox.show("操作失败!", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});

	}

	public EmArchivePaymentModel getEpm() {
		return epm;
	}

	public void setEpm(EmArchivePaymentModel epm) {
		this.epm = epm;
	}

	public List<EmArchiveRemarkModel> getRmList() {
		return rmList;
	}

	public void setRmList(Integer id) {
		this.rmList = bll.remarklist(id);
	}

}
