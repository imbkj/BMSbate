package Controller.EmHouse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import Controller.CoHousingFund.CoHouse_pwd_ApplyCheckController;
import Model.CoHousingFundModel;
import Model.EmHouseSetupModel;
import Util.UserInfo;
import bll.EmHouse.Emhouse_InstallBll;

public class Emhouse_InstallController {
	private Emhouse_InstallBll bll = new Emhouse_InstallBll();
	private EmHouseSetupModel model = new EmHouseSetupModel();
	private CoHousingFundModel chm = new CoHousingFundModel();

	private List<CoHousingFundModel> list = new ListModelList<>();

	public Emhouse_InstallController() {
		model = bll.getEmHouseSetupInfo();
		list = bll.lastInfo();
	}

	@Command
	@NotifyChange("model")
	public void updateinstall(@BindingParam("lastday") String lastday,
			@BindingParam("lastdaybj") String lastdaybj,
			@BindingParam("salay") String salay,
			@BindingParam("onair") String onair,
			@BindingParam("onairbj") String onairbj,
			@BindingParam("reason") String reason,
			@BindingParam("reasonbj") String reasonbj) {
		if (lastday == null || lastday.equals("")) {
			Messagebox.show("请输入公积金变更最后截止日", "提示", Messagebox.OK,
					Messagebox.ERROR);
		}

		else if (lastdaybj == null || lastdaybj.equals("")) {
			Messagebox.show("请输入公积金补缴最后截止日", "提示", Messagebox.OK,
					Messagebox.ERROR);
		} else if (salay == null || salay.equals("")) {
			Messagebox.show("请是否开放公积金年度调基端口", "提示", Messagebox.OK,
					Messagebox.ERROR);
		} else if (onair == null || onair.equals("")) {
			Messagebox.show("请选择是否禁止变更增删改", "提示", Messagebox.OK,
					Messagebox.ERROR);
		} else if (onairbj == null || onairbj.equals("")) {
			Messagebox.show("请选择是否禁止补缴增删改", "提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			Date date = new Date();
			String username = UserInfo.getUsername();
			String updatestr = changeDate(date) + "  " + username + "修改";
			EmHouseSetupModel updatemodel = new EmHouseSetupModel();
			updatemodel.setId(model.getId());
			updatemodel.setAuditday(14);
			updatemodel.setSalay(Integer.parseInt(salay));
			updatemodel.setLastday(Integer.parseInt(lastday));
			updatemodel.setLastdaybj(Integer.parseInt(lastdaybj));
			updatemodel.setOnair(Integer.parseInt(onair));
			updatemodel.setOnairbj(Integer.parseInt(onairbj));
			updatemodel.setReason(reason);
			updatemodel.setReasonbj(reasonbj);
			if (lastday.equals(model.getLastday() + "")
					|| lastday == model.getLastday() + "") {
				updatemodel.setLastdayname(model.getLastdayname());
			} else {
				updatemodel.setLastdayname(updatestr);
			}

			updatemodel.setAuditdayname(updatestr);

			if (lastdaybj.equals(model.getLastdaybj() + "")
					|| lastdaybj == model.getLastdaybj() + "") {
				updatemodel.setLastdaynamebj(model.getLastdaynamebj());
			} else {
				updatemodel.setLastdaynamebj(updatestr);
			}

			if (salay.equals(model.getSalay() + "")
					|| salay == model.getSalay() + "") {
				updatemodel.setSalayname(model.getSalayname());
			} else {
				updatemodel.setSalayname(updatestr);
			}

			if ((onair.equals(model.getOnair() + "") || onair == model
					.getOnair() + "")
					&& (reason.equals(model.getReason() + "") || reason == model
							.getReason() + "")) {
				updatemodel.setOnairname(model.getOnairname());
			} else {
				updatemodel.setOnairname(updatestr);
			}

			if ((onairbj.equals(model.getOnairbj() + "") || onairbj == model
					.getOnairbj() + "")
					&& (reasonbj.equals(model.getReasonbj() + "") || reasonbj == model
							.getReasonbj() + "")) {
				updatemodel.setOnairnamebj(model.getOnairnamebj());
			} else {
				updatemodel.setOnairnamebj(updatestr);
			}

			int k = bll.updateEmhouseInstall(updatemodel);
			if (k > 0) {
				Messagebox.show("更新成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				Messagebox.show("更新失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
			model = bll.getEmHouseSetupInfo();
		}
	}

	@Command
	@NotifyChange("list")
	public void search() {
		list = bll.lastInfo(chm);
	}

	@Command
	public void mod(@BindingParam("a") CoHousingFundModel m) {
		Integer i = bll.modLastDay(m);
		if (i > 0) {
			Messagebox
					.show("更新成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} else {
			Messagebox.show("更新失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmHouseSetupModel getModel() {
		return model;
	}

	public void setModel(EmHouseSetupModel model) {
		this.model = model;
	}

	private String changeDate(Date d) {
		String t = "";
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		t = time.format(d);
		return t;
	}

	public List<CoHousingFundModel> getList() {
		return list;
	}

	public void setList(List<CoHousingFundModel> list) {
		this.list = list;
	}

	public CoHousingFundModel getChm() {
		return chm;
	}

	public void setChm(CoHousingFundModel chm) {
		this.chm = chm;
	}

}
