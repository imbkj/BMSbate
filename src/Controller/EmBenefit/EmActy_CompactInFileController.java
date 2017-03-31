package Controller.EmBenefit;

import impl.WorkflowCore.WfOperateImpl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import com.zhuozhengsoft.pageoffice.zoomseal.User;

import service.WorkflowCore.WfOperateService;

import bll.EmBenefit.EmActy_compactAddImpl;
import bll.EmBenefit.EmActy_compactAuditBll;

import Model.EmActyCompactModel;
import Util.DateStringChange;
import Util.UserInfo;

public class EmActy_CompactInFileController {
	private List<EmActyCompactModel> compactlist = new ListModelList<>();
	private EmActyCompactModel ecm = new EmActyCompactModel();
	private Integer id = 0;
	private Integer taprid = 0;

	private Window win;

	private EmActy_compactAuditBll bll = new EmActy_compactAuditBll();

	public EmActy_CompactInFileController() {
		if (Executions.getCurrent().getArg().get("daid") != null) {
			id = Integer.valueOf(Executions.getCurrent().getArg().get("daid")
					.toString());
		}
		if (Executions.getCurrent().getArg().get("id") != null) {
			taprid = Integer.valueOf(Executions.getCurrent().getArg().get("id")
					.toString());
		}
		EmActyCompactModel em = new EmActyCompactModel();
		em.setEaco_id(id);
		compactlist = bll.getCompactList(em, false);
		if (compactlist.size() > 0) {
			ecm = compactlist.get(0);
		}
	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		win = w;
	}

	@Command
	public void submit() {
		if (ecm.getEaco_filedate2() == null
				|| ecm.getEaco_filedate2().equals("")) {
			Messagebox
					.show("请选择归档日期.", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			ecm.setEaco_filedate(DateStringChange.DatetoSting(
					ecm.getEaco_filedate2(), "yyyy-MM-dd"));
		}
		if (ecm.getEaco_fileId() == null || ecm.getEaco_fileId().equals("")) {
			Messagebox
					.show("请选择归档编号.", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

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
							ecm.setEaco_modname(UserInfo.getUsername());
							ecm.setEaco_state(7);
							Object[] obj = { "5", ecm };
							String[] str = wf.PassToNext(obj, taprid,
									UserInfo.getUsername(), "", 0, "");
							if (str[0].equals("1")) {
								Messagebox.show("操作成功", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();

							} else {
								Messagebox.show("操作失败", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}

						}
					}
				});

	}

	public EmActyCompactModel getEcm() {
		return ecm;
	}

	public void setEcm(EmActyCompactModel ecm) {
		this.ecm = ecm;
	}

}
