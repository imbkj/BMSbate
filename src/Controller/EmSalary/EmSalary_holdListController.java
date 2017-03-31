package Controller.EmSalary;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import bll.EmSalary.EmSalary_AudtingOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;
import Model.EmSalaryDataModel;
import Model.PubCodeConversionModel;
import Util.CheckString;
import Util.RegexUtil;
import Util.UserInfo;

public class EmSalary_holdListController {
	private List<EmSalaryDataModel> salaryList;
	private List<EmSalaryDataModel> salaryWinList;
	private List<PubCodeConversionModel> usageList;

	public EmSalary_holdListController() {
		EmSalary_SalarySelBll bll = new EmSalary_SalarySelBll();
		usageList = bll.getCodeConversion();
		salaryList = bll.getSalaryDataIfhold();
		salaryWinList = salaryList;
	}

	// 退回
	@Command("returnSalary")
	@NotifyChange("salaryWinList")
	public void returnSalary(@BindingParam("model") EmSalaryDataModel model) {
		EmSalary_AudtingOperateBll opBll = new EmSalary_AudtingOperateBll();
		EmSalary_SalarySelBll sBll = new EmSalary_SalarySelBll();
		try {
			EmSalaryDataModel em = new EmSalaryDataModel();
			em = sBll.getEmSalaryTaskInfo(model.getEsda_id());

			List<Integer[]> list = new ArrayList<Integer[]>();
			list.add(new Integer[] { em.getTbrb_id(), em.getTbrb_customInt() });

			if (list.size() != 0) {
				String[] message = opBll.payAudtingOperate(list,
						UserInfo.getUsername(), em.getTaba_id(),
						em.getTapr_id(), 2);
				if ("1".equals(message[0])) {
					//将暂停发放状态更新回0
					opBll.holdAudting(model);
					
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					// 刷新
					salaryList = sBll.getSalaryDataIfhold();
					salaryWinList = salaryList;	

				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请操作您需要退回的数据。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("工资退回出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 转已审核
	@Command("audtingSalary")
	@NotifyChange("salaryWinList")
	public void audtingSalary(@BindingParam("model") EmSalaryDataModel model) {
		EmSalary_AudtingOperateBll eoBll = new EmSalary_AudtingOperateBll();
		EmSalary_SalarySelBll bll = new EmSalary_SalarySelBll();

		String[] message = eoBll.holdAudting(model);

		if ("1".equals(message[0])) {
			// 弹出提示
			Messagebox.show(message[1], "操作提示", Messagebox.OK, Messagebox.NONE);
		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

		salaryList = bll.getSalaryDataIfhold();
		salaryWinList = salaryList;
	}

	// 检索
	@Command("changeList")
	@NotifyChange("salaryWinList")
	public void changeList(@BindingParam("ownmonth") Intbox ibOwnmonth,
			@BindingParam("cid") Intbox ibCid,
			@BindingParam("shortname") Textbox shortname,
			@BindingParam("gid") Intbox ibGid,
			@BindingParam("emname") Textbox emname,
			@BindingParam("usage") Combobox usage) {
		int gid = 0;
		int ownmonth = 0;
		int cid = 0;
		try {
			if (ibOwnmonth.getValue() != null) {
				ownmonth = ibOwnmonth.getValue();
			}
			if (ibCid.getValue() != null) {
				cid = ibCid.getValue();
			}
			if (ibGid.getValue() != null) {
				gid = ibGid.getValue();
			}
			if (ownmonth != 0 || cid != 0 || "".equals(shortname.getValue())
					|| gid != 0 || "".equals(emname.getValue())
					|| "".equals(emname.getValue())) {
				salaryWinList = getNewList(ownmonth, cid, shortname.getValue(),
						gid, emname.getValue(), usage.getValue());
			} else {
				salaryWinList = salaryList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 检索数据
	private List<EmSalaryDataModel> getNewList(int ownmonth, int cid,
			String shortname, int gid, String emname, String usage) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		int i = 6;
		for (EmSalaryDataModel m : salaryList) {
			i = 6;
			// 所属月份
			if (ownmonth == 0) {
				i = i - 1;
			} else if (ownmonth == m.getOwnmonth()) {
				i = i - 1;
			}
			// cid
			if (cid == 0) {
				i = i - 1;
			} else if (cid == m.getCid()) {
				i = i - 1;
			}
			// 公司简称
			try {
				if (shortname == null || "".equals(shortname)) {
					i = i - 1;
				} else {
					if (CheckString.isChinese(shortname)
							|| CheckString.isLetter(shortname)) {
						if (RegexUtil
								.isExists(shortname, m.getCoba_shortname())
								|| RegexUtil.isExists(shortname,
										m.getCoba_namespell())) {
							i = i - 1;
						}
					}
				}
			} catch (Exception e) {

			}
			// gid
			if (gid == 0) {
				i = i - 1;
			} else if (gid == m.getGid()) {
				i = i - 1;
			}
			// 员工姓名
			try {
				if (emname == null || "".equals(emname)) {
					i = i - 1;
				} else {
					if (CheckString.isChinese(emname)
							|| CheckString.isLetter(emname)) {
						if (RegexUtil.isExists(emname, m.getName())
								|| RegexUtil
										.isExists(emname, m.getEmba_spell())) {
							i = i - 1;
						}
					}
				}
			} catch (Exception e) {

			}
			// 用途
			try {
				if (usage == null || "".equals(usage)) {
					i = i - 1;
				} else if (usage.equals(m.getEsda_usage_typestr())) {
					i = i - 1;
				}
			} catch (Exception e) {

			}
			if (i == 0) {
				list.add(m);
			}
		}
		return list;
	}

	public List<EmSalaryDataModel> getSalaryWinList() {
		return salaryWinList;
	}

	public List<PubCodeConversionModel> getUsageList() {
		return usageList;
	}

}
