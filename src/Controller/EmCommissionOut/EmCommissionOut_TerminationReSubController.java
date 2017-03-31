package Controller.EmCommissionOut;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.EmCommissionOut.EmCommissionOut_OperateDal;

import Model.CI_Insurant_ListModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Util.DateStringChange;
import Util.DateUtil;
import Util.SocialInsuranceEmCommissionOut;
import Util.UserInfo;
import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;

public class EmCommissionOut_TerminationReSubController {
	private EmCommissionOutChangeModel m = new EmCommissionOutChangeModel();
	Integer daid = 0;
	private List<EmCommissionOutFeeDetailChangeModel> feeList = new ListModelList<>();
	private List<String> causeList = new ListModelList<>();
	private ListModelList<EmCommissionOutChangeModel> tali_list;// 显示所选商保连带人类型
	SocialInsuranceEmCommissionOut calUtil = new SocialInsuranceEmCommissionOut();

	public EmCommissionOut_TerminationReSubController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 数据初始化
	 * 
	 */
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			setM(bll.getEmCommOutChangeInfo(daid, ""));
			m.setEcoc_remark1("");
			setFeeList(bll.getFeeDetailChangeList(" and eofc_ecoc_id=" + daid));
			for (EmCommissionOutFeeDetailChangeModel m : feeList) {
				if (m.getEofc_start_date() != null) {
					m.setTempDate(new Date());
				}
			}

			causeList.add("辞职");
			causeList.add("取消委托");
			causeList.add("终止劳动合同（自愿）");
			causeList.add("终止劳动合同（非自愿）");
			causeList.add("协商解除劳动合同（自愿）");
			causeList.add("协商解除劳动合同（非自愿）");
			causeList.add("退休");
			causeList.add("死亡");
			causeList.add("公司自行管理，无需退工");
			causeList.add("公司自行管理，需办退工");
			causeList.add("转其他公司管理，无需退工");
			causeList.add("转其他公司管理，需办退工");
			causeList.add("转用工单位（同个委托地，A公司转B公司）");
System.out.println("aaaaa");
System.out.println(m.getEcoc_tapr_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 时间批量修改
	 * 
	 * @param date
	 */
	@Command("dateAll")
	@NotifyChange({ "feeList" })
	public void dateAll(@BindingParam("date") Date date,
			@BindingParam("index") Integer index,
			@BindingParam("class") String sicl_class) {
		if (sicl_class.equals("all")) {
			date = feeList.get(0).getTempDate();
			index = 0;
		}
		for (int i = index + 1; i < feeList.size(); i++) {
			if (feeList.get(i).getSicl_class().equals(sicl_class)
					|| sicl_class.equals("all")) {
				feeList.get(i).setTempDate(date);
			}
		}
	}

	@Command("dateAll1")
	@NotifyChange({ "feeList" })
	public void dateAll1(@BindingParam("date") Date date,
			@BindingParam("index") Integer index,
			@BindingParam("class") String sicl_class) {
		if (sicl_class.equals("all")) {
			date = feeList.get(0).getTempDate1();
			index = 0;
		}
		for (int i = index + 1; i < feeList.size(); i++) {
			if (feeList.get(i).getSicl_class().equals(sicl_class)
					|| sicl_class.equals("all")) {
				feeList.get(i).setTempDate1(date);
			}
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();

		m.setEcoc_addname(UserInfo.getUsername());
		m.setEcoc_state(1);
		m.setType(m.getEcoc_type());
		m.setEcoc_remark(m.getEcoc_remark1());
		m.setRemark("重新提交;" + m.getEcoc_remark1());

		try {
			for (EmCommissionOutFeeDetailChangeModel m : feeList) {
				if (m.getTempDate() != null) {
					m.setEofc_stop_date(DateStringChange.DatetoSting(
							DateUtil.getLastDay(m.getTempDate()), "yyyy-MM-dd"));
				}
			}
			String[] str = bll.resub(m, feeList);

			if (str[0].equals("1")) {
				Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	// 终止流程
		@Command
		public void stopList(@BindingParam("win") Window win) {
			EmCommissionOut_OperateDal dal = new EmCommissionOut_OperateDal();
			try {
				tali_list = new ListModelList<EmCommissionOutChangeModel>(
						dal.gettali_list(m.getEcoc_tapr_id()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("tali_id", String.valueOf(tali_list.get(0).getC1()));
			map.put("tali_name", String.valueOf(tali_list.get(0).getCoab_name()));
			Window window = (Window) Executions.createComponents(
					"Task_StopTask.zul", null, map);
			window.doModal();
			win.detach();
		}

	public final EmCommissionOutChangeModel getM() {
		return m;
	}

	public final List<EmCommissionOutFeeDetailChangeModel> getFeeList() {
		return feeList;
	}

	public final void setM(EmCommissionOutChangeModel m) {
		this.m = m;
	}

	public final void setFeeList(
			List<EmCommissionOutFeeDetailChangeModel> feeList) {
		this.feeList = feeList;
	}

	public List<String> getCauseList() {
		return causeList;
	}

	public void setCauseList(List<String> causeList) {
		this.causeList = causeList;
	}
}
