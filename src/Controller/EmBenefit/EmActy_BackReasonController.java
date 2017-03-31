package Controller.EmBenefit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import impl.WorkflowCore.WfOperateImpl;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import service.WorkflowCore.WfOperateService;

import Model.EmActyCompactModel;
import Util.UserInfo;
import bll.EmBenefit.EmActy_compactAddImpl;
import bll.EmBenefit.EmActy_compactAuditBll;

public class EmActy_BackReasonController {

	private Integer id = 0;
	private Integer taclId = 0;
	private Integer taprId = 0;
	private String remark = "";
	private String company = "";
	private String compactid = "";
	private Window win;

	private EmActy_compactAuditBll bll = new EmActy_compactAuditBll();

	public EmActy_BackReasonController() {
		if (Executions.getCurrent().getParameter("daid") != null) {
			id = Integer.valueOf(Executions.getCurrent().getParameter("daid")
					.toString());
			EmActyCompactModel em = new EmActyCompactModel();
			em.setEaco_id(id);
			List<EmActyCompactModel> compactlist = bll
					.getCompactList(em, false);
			if (compactlist.size() > 0) {
				company = compactlist.get(0).getEaco_name();
				compactid = compactlist.get(0).getEaco_compactid();
			}
		}

		if (Executions.getCurrent().getParameter("id") != null) {
			taprId = Integer.valueOf(Executions.getCurrent().getParameter("id")
					.toString());
		}

		if (Executions.getCurrent().getParameter("taclId") != null) {
			taclId = Integer.valueOf(Executions.getCurrent()
					.getParameter("taclId").toString());
		}

	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		win = w;
	}

	@Command
	public void submit() {
		Messagebox.show("确认退回数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							WfOperateService wf = new WfOperateImpl(
									new EmActy_compactAddImpl());
							EmActyCompactModel em = new EmActyCompactModel();
							em.setEaco_id(id);
							em.setEaco_state(5);
							em.setEaco_backreason(remark);
							em.setEaco_modname(UserInfo.getUsername());
							Object[] obj = { 6, em };
							String[] str = wf.ReturnToPrev(obj, taprId,
									UserInfo.getUsername(), "");
							if (str[0].equals("1")) {
								Messagebox.show("操作成功", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
								Map map = new HashMap();
								map.put("id", taclId);
								Window window = (Window) Executions
										.createComponents(
												"../Taskflow/Task_ContentList.zul",
												null, map);
								window.doModal();
							} else {
								Messagebox.show("操作失败", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}

						}
					}
				});
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompactid() {
		return compactid;
	}

	public void setCompactid(String compactid) {
		this.compactid = compactid;
	}

}
