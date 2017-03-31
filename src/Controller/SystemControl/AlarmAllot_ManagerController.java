package Controller.SystemControl;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import bll.SystemControl.AlarmAllotBll;
import Model.AlarmClassModel;
import Model.AlarmInfoModel;
import Model.AlarmRuleModel;
import Model.DepartmentListModel;
import Model.loginroleModel;

public class AlarmAllot_ManagerController {
	private List<AlarmClassModel> acmList; // 项目类别列表1
	private List<AlarmClassModel> acmList2; // 项目类别列表2
	private List<AlarmInfoModel> aimList; // 项目名称
	private List<loginroleModel> rmList; // 角色列表
	private List<DepartmentListModel> dlmList; // 部门列表1
	private List<DepartmentListModel> dlmList2; // 部门列表2
	private List<AlarmRuleModel> rdList; // 角色&部门列表
	private List<AlarmRuleModel> rdSelectList; // 已选角色&部门列表
	private List<AlarmRuleModel> aaList; // 项目类别&名称列表
	private List<AlarmRuleModel> aaSelectList; // 已选项目类别&名称列表
	AlarmAllotBll bll = new AlarmAllotBll();

	// 初始化
	public AlarmAllot_ManagerController() {
		setAcmList();
		setAcmList2();
		setAimList(0, "");
		setRmList(0);
		setDlmList();
		setDlmList2();
		setRdList(1, 0, "", "0", "", "");
		setRdSelectList(1, 1, "", "0", "", "");
		setAaList(2, 0, "", "0", "", "");
		setAaSelectList(2, 1, "", "0", "", "");
	}

	// 获取项目名称
	@Command
	@NotifyChange({ "aimList", "rdList", "rdSelectList" })
	public void getItemName(@BindingParam("a") AlarmClassModel acm,
			@BindingParam("b") Combobox cb) {
		if (acm != null) {
			cb.setValue("");
			setRdList(1, 0, "", "0", "", "");
			setRdSelectList(1, 1, "", "0", "", "");
			setAimList(acm.getAlcl_id(), "");
		}
	}

	// 更新项目名称
	@Command
	@NotifyChange("aimList")
	public void updateItemName(@BindingParam("a") InputEvent e,
			@BindingParam("b") AlarmClassModel acm) {
		Integer ac = 0;
		if (acm != null) {
			ac = acm.getAlcl_id();
		}

		setAimList(ac, e.getValue());
	}

	// 获取角色列表
	@Command
	@NotifyChange({ "rdList", "rdSelectList" })
	public void getRudList(@BindingParam("a") AlarmInfoModel aim,
			@BindingParam("b") AlarmClassModel acm) {
		String ac = "";
		String ai = "";
		if (acm != null) {
			ac = acm.getAlcl_id().toString();
		}

		if (aim != null) {
			ai = aim.getAlin_id().toString();
		}

		ac = ac.equals("0") ? "" : ac;
		ai = ai == "" ? "0" : ai;

		setRdList(1, 0, ac, ai, "", "");
		setRdSelectList(1, 1, ac, ai, "", "");
	}

	// 获取角色
	@Command
	@NotifyChange("rmList")
	public void getRolName(@BindingParam("a") DepartmentListModel dpm,
			@BindingParam("b") Combobox cb) {
		if (dpm != null) {
			cb.setValue("");
			setRmList(Integer.valueOf(dpm.getDep_id()));
		}
	}

	// 获取项目列表
	@Command
	@NotifyChange({ "aaList", "aaSelectList" })
	public void getItemList(@BindingParam("a") loginroleModel lrm,
			@BindingParam("b") DepartmentListModel dpm) {
		String lr = "";
		String dp = "";
		if (lrm != null) {
			lr = lrm.getRol_id().toString();
		}

		if (dpm != null) {
			dp = Integer.valueOf(dpm.getDep_id()).toString();
		}
		lr = lr == "" ? "0" : lr;
		dp = dp.equals("0") ? "" : dp;

		setAaList(2, 0, dp, lr, "", "");
		setAaSelectList(2, 1, dp, lr, "", "");

	}

	// 搜索角色列表
	@Command
	@NotifyChange({ "rdList", "rdSelectList" })
	public void SearchRdList(@BindingParam("a") Textbox tb,
			@BindingParam("b") DepartmentListModel dpm,
			@BindingParam("c") AlarmInfoModel aim,
			@BindingParam("d") AlarmClassModel acm) {
		String ac = "";
		String ai = "";
		String dp = "";

		if (acm != null) {
			ac = acm.getAlcl_id().toString();
		}

		if (aim != null) {
			ai = aim.getAlin_id().toString();
		}
		if (dpm != null) {
			dp = Integer.valueOf(dpm.getDep_id()).toString();
		}

		ac = ac.equals("0") ? "" : ac;
		ai = ai == "" ? "0" : ai;
		dp = dp.equals("0") ? "" : dp;

		setRdList(1, 0, ac, ai, tb.getValue(), dp);
		setRdSelectList(1, 1, ac, ai, tb.getValue(), dp);
	}

	// 搜索项目列表
	@Command
	@NotifyChange({ "aaList", "aaSelectList" })
	public void SearchAAList(@BindingParam("a") Textbox tb,
			@BindingParam("b") loginroleModel lrm,
			@BindingParam("c") DepartmentListModel dpm,
			@BindingParam("d") AlarmClassModel acm) {
		String lr = "";
		String dp = "";
		String ac = "";

		if (acm != null) {
			ac = acm.getAlcl_id().toString();
		}

		if (lrm != null) {
			lr = lrm.getRol_id().toString();
		}
		if (dpm != null) {
			dp = Integer.valueOf(dpm.getDep_id()).toString();
		}

		ac = ac.equals("0") ? "" : ac;
		lr = lr == "" ? "0" : lr;
		dp = dp.equals("0") ? "" : dp;

		setAaList(2, 0, dp, lr, tb.getValue(), ac);
		setAaSelectList(2, 1, dp, lr, tb.getValue(), ac);
	}

	// 单选角色列表
	@Command
	@NotifyChange({ "rdList", "rdSelectList" })
	public void ltrRdList(@BindingParam("a") AlarmRuleModel alm) {
		if (!rdSelectList.contains(alm)) {
			rdSelectList.add(alm);
			rdList.remove(alm);
		}
	}

	// 移除角色列表
	@Command
	@NotifyChange({ "rdList", "rdSelectList" })
	public void rtlRdList(@BindingParam("a") AlarmRuleModel alm) {
		if (!rdList.contains(alm)) {
			rdList.add(alm);
			rdSelectList.remove(alm);
		}
	}

	// 全选角色列表
	@Command
	@NotifyChange({ "rdList", "rdSelectList" })
	public void ltrRdAllList() {
		if (rdList.size() > 0) {
			rdSelectList.addAll(rdList);
			rdList.clear();
		}
	}

	// 清空角色列表
	@Command
	@NotifyChange({ "rdList", "rdSelectList" })
	public void rtlRdAllList(@BindingParam("a") AlarmRuleModel alm) {
		if (rdSelectList.size() > 0) {
			rdList.addAll(rdSelectList);
			rdSelectList.clear();
		}
	}

	// 单选项目列表
	@Command
	@NotifyChange({ "aaList", "aaSelectList" })
	public void ltrItemList(@BindingParam("a") AlarmRuleModel alm) {
		if (!aaSelectList.contains(alm)) {
			aaSelectList.add(alm);
			aaList.remove(alm);
		}
	}

	// 移除项目列表
	@Command
	@NotifyChange({ "aaList", "aaSelectList" })
	public void rtlItemList(@BindingParam("a") AlarmRuleModel alm) {
		if (!aaList.contains(alm)) {
			aaList.add(alm);
			aaSelectList.remove(alm);
		}
	}

	// 全选项目列表
	@Command
	@NotifyChange({ "aaList", "aaSelectList" })
	public void ltrItemAllList() {
		if (aaList.size() > 0) {
			aaSelectList.addAll(aaList);
			aaList.clear();
		}
	}

	// 清空项目列表
	@Command
	@NotifyChange({ "aaList", "aaSelectList" })
	public void rtlItemAllList(@BindingParam("a") AlarmRuleModel alm) {
		if (aaSelectList.size() > 0) {
			aaList.addAll(aaSelectList);
			aaSelectList.clear();
		}
	}

	// 按项目更新列表
	@Command
	@NotifyChange({ "aaList", "aaSelectList" })
	public void rdSubmit(@BindingParam("a") Combobox cb) {
		Integer i = 0;

		if (cb.getSelectedIndex() >= 0) {
			AlarmInfoModel aim = cb.getSelectedItem().getValue();
			i = bll.UpdateAlarmRule(rdList, rdSelectList);
			if (i > 0) {
				Messagebox.show("更新成功", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				Messagebox.show("请选择需要更新的角色", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择需要更新的项目", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 按角色更新列表
	@Command
	@NotifyChange({ "rdList", "rdSelectList" })
	public void aaSubmit(@BindingParam("a") Combobox cbRol,
			@BindingParam("b") Combobox cbDep) {
		Integer i = 0;
		Integer dpId = 0;
		if (cbRol.getSelectedIndex() >= 0) {
			loginroleModel lr = cbRol.getSelectedItem().getValue();

			i = bll.UpdateAlarmRule(aaList, aaSelectList);
			if (i > 0) {
				Messagebox.show("更新成功", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				Messagebox.show("请选择需要更新的项目", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择需要更新的角色", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public List<AlarmClassModel> getAcmList() {
		return acmList;
	}

	public void setAcmList() {
		this.acmList = new ListModelList<AlarmClassModel>(
				bll.getAlarmClassAllList());
	}

	public List<AlarmClassModel> getAcmList2() {
		return acmList2;
	}

	public void setAcmList2() {
		this.acmList2 = new ListModelList<AlarmClassModel>(
				bll.getAlarmClassAllList());
	}

	public List<AlarmInfoModel> getAimList() {
		return aimList;
	}

	public void setAimList(Integer id, String name) {
		this.aimList = new ListModelList<AlarmInfoModel>(bll.getAlarmInfoList(
				id, name));
	}

	public List<loginroleModel> getRmList() {
		return rmList;
	}

	public void setRmList(Integer id) {
		this.rmList = new ListModelList<loginroleModel>(
				bll.getloginroleListById(id));
	}

	public List<DepartmentListModel> getDlmList() {
		return dlmList;
	}

	public void setDlmList() {
		this.dlmList = new ListModelList<DepartmentListModel>(
				bll.getDepartmentList());
	}

	public List<DepartmentListModel> getDlmList2() {
		return dlmList2;
	}

	public void setDlmList2() {
		this.dlmList2 = new ListModelList<DepartmentListModel>(
				bll.getDepartmentList());
	}

	public List<AlarmRuleModel> getRdList() {
		return rdList;
	}

	public void setRdList(Integer itype, Integer iState, String alclId,
			String alinId, String rolName, String depId) {
		this.rdList = new ListModelList<AlarmRuleModel>(bll.getAlarmRule(itype,
				iState, "", depId, alclId, alinId, rolName, ""));
	}

	public List<AlarmRuleModel> getAaList() {
		return aaList;
	}

	public void setAaList(Integer itype, Integer iState, String depId,
			String rolId, String alinName, String alclId) {
		this.aaList = new ListModelList<AlarmRuleModel>(bll.getAlarmRule(itype,
				iState, rolId, depId, alclId, "", "", alinName));
	}

	public List<AlarmRuleModel> getRdSelectList() {
		return rdSelectList;
	}

	public void setRdSelectList(Integer itype, Integer iState, String alclId,
			String alinId, String rolName, String depId) {
		this.rdSelectList = new ListModelList<AlarmRuleModel>(bll.getAlarmRule(
				itype, iState, "", depId, alclId, alinId, rolName, ""));
	}

	public List<AlarmRuleModel> getAaSelectList() {
		return aaSelectList;
	}

	public void setAaSelectList(Integer itype, Integer iState, String depId,
			String rolId, String alinName, String alclId) {
		this.aaSelectList = new ListModelList<AlarmRuleModel>(bll.getAlarmRule(
				itype, iState, rolId, depId, alclId, "", "", alinName));
	}

}
