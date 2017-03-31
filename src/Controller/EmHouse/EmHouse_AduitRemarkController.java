package Controller.EmHouse;

import impl.WorkflowCore.WfOperateImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmHouse.EmHouse_EditImpl;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.EmHouseChangeModel;
import Util.DateStringChange;
import Util.UserInfo;

public class EmHouse_AduitRemarkController {
	private EmHouseChangeModel ecm = (EmHouseChangeModel) Executions
			.getCurrent().getArg().get("ecm");

	private Window win = (Window) Path.getComponent("winar");

	private String remark="";

	public EmHouse_AduitRemarkController() {

	}

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		if (winD == null) {
			win = winD;
		}

	}

	@Command
	public void turnback() {
		if (remark.equals("")) {
			Messagebox
					.show("请录入退回原因.", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			Calendar calendar = new GregorianCalendar();
			Date nowDate = new Date(); // 获取当前时间
			calendar.setTime(nowDate);

			EmHouseChangeModel em = new EmHouseChangeModel();
			em.setEmhc_id(ecm.getEmhc_id());
			em.setEmhc_ifdeclare(3);
			em.setEmhc_iffifteen(1);
			em.setEmhc_remark(ecm.getEmhc_addname() + "备注:"
					+ ecm.getEmhc_remark() + "("
					+ DateStringChange.DatetoSting(nowDate, "yyyy-MM-") + ")/n"
					+ UserInfo.getUsername() + "审核:" + remark + "("
					+ DateStringChange.DatetoSting(nowDate, "yyyy-MM-") + ")");
			WfBusinessService wfbs = new EmHouse_EditImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			Object[] obj = { "退回数据", em };
			String[] str = wfs.ReturnToN(obj, ecm.getEmhc_tapr_id(), 2,
					UserInfo.getUsername());
			if (str[0].equals("1")) {
				Messagebox.show("操作成功.", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("操作失败.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	@Command
	public void aduit() {
		if (remark.equals("")) {
			Messagebox
					.show("请录入审核意见.", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			Calendar calendar = new GregorianCalendar();
			Date nowDate = new Date(); // 获取当前时间
			calendar.setTime(nowDate);

			EmHouseChangeModel em = new EmHouseChangeModel();
			em.setEmhc_id(ecm.getEmhc_id());
			em.setEmhc_ifdeclare(0);
			em.setEmhc_iffifteen(1);
			em.setEmhc_remark(ecm.getEmhc_addname() + "备注:"
					+ ecm.getEmhc_remark() + "("
					+ DateStringChange.DatetoSting(nowDate, "yyyy-MM-") + ")/n"
					+ UserInfo.getUsername() + "审核:" + remark + "("
					+ DateStringChange.DatetoSting(nowDate, "yyyy-MM-") + ")");
			WfBusinessService wfbs = new EmHouse_EditImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			Object[] obj = { "审核", em };
			String[] str = wfs.PassToNext(obj, ecm.getEmhc_tapr_id(),
					UserInfo.getUsername(), "", 0, "");
			if (str[0].equals("1")) {
				Messagebox.show("操作成功.", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("操作失败.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
