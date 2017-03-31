package Controller.EmCommissionOut;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;

import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutFeeDetailModel;
import Model.EmCommissionOutModel;
import Util.DateStringChange;
import Util.DateUtil;
import Util.RedirectUtil;
import Util.UserInfo;

public class EmCommissionOut_TerminationController {

	private EmCommissionOutModel m = new EmCommissionOutModel();
	private EmCommissionOutChangeModel cm = new EmCommissionOutChangeModel();
	private List<EmCommissionOutFeeDetailChangeModel> cfeeList = new ListModelList<>();
	private List<String> causeList = new ListModelList<>();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	BigDecimal zero = BigDecimal.ZERO;

	public EmCommissionOut_TerminationController() {
		try {
			m = (EmCommissionOutModel) Executions.getCurrent()
					.getAttribute("m");
			m.setFeeList(new EmCommissionOutListBll()
					.getFeeDetailList(" and eofd_ecou_id=" + m.getEcou_id()));

			termination_init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 绑定终止原因列表
	 * 
	 */
	public void termination_init() {
		UpdateToChange();

		// 终止原因列表
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
		causeList.add("福利地转移");
		//cm.setEcoc_stop_cause(causeList.get(0));
	}

	/**
	 * 在册数据复制到变更数据
	 * 
	 */
	public void UpdateToChange() {
		try {
			cm.setEmba_name(m.getEmba_name());
			cm.setGid(m.getGid());
			cm.setCid(m.getCid());
			cm.setEcoc_ecou_id(m.getEcou_id());
			cm.setEcoc_soin_id(m.getEcou_soin_id());
			cm.setEcoc_ecos_id(m.getEcou_ecos_id());
			cm.setEcoc_addtype("离职");
			cm.setEcoc_type("ecocter");
			cm.setEcoc_idcard(m.getEcou_idcard());
			cm.setEcoc_email(m.getEcou_email());
			cm.setEcoc_phone(m.getEcou_phone());
			cm.setEcoc_mobile(m.getEcou_mobile());
			cm.setEcoc_in_date(m.getEcou_in_date());
			cm.setEcoc_com_phone(m.getEcou_com_phone());
			cm.setEcoc_com_principal(m.getEcou_com_principal());
			cm.setEcoc_com_company(m.getEcou_com_company());
			cm.setEcoc_domicile(m.getEcou_domicile());
			cm.setEcoc_compact_jud(m.getEcou_compact_jud());
			cm.setEcoc_compact_f(m.getEcou_compact_f());
			cm.setEcoc_compact_l(m.getEcou_compact_l());
			cm.setEcoc_salary(m.getEcou_salary());
			cm.setEcoc_sb_base(m.getEcou_sb_base());
			cm.setEcoc_house_base(m.getEcou_house_base());
			cm.setEcoc_sb_em_sum(zero);
			cm.setEcoc_sb_co_sum(zero);
			cm.setEcoc_sb_sum(zero);
			cm.setEcoc_gjj_em_sum(zero);
			cm.setEcoc_gjj_co_sum(zero);
			cm.setEcoc_gjj_sum(zero);
			cm.setEcoc_welfare_sum(zero);
			cm.setEcoc_service_fee(zero);
			cm.setEcoc_file_fee(zero);
			cm.setEcoc_sum(zero);
			cm.setEcoc_stop_date(null);
			cm.setEcoc_stop_cause(m.getEcou_stop_cause());
			cm.setEcoc_cancel_cause(m.getEcou_cancel_cause());
			cm.setEcoc_laststate(0);
			cm.setEcoc_state(1);
			cm.setEcoc_client(m.getEcou_client());
			cm.setEcoc_addname(UserInfo.getUsername());
			cm.setEcoc_remark("");

			for (EmCommissionOutFeeDetailModel m1 : m.getFeeList()) {
				EmCommissionOutFeeDetailChangeModel cm1 = new EmCommissionOutFeeDetailChangeModel();

				cm1.setEofc_eofd_id(m1.getEofd_id());
				cm1.setEofc_sicl_id(m1.getEofd_sicl_id());
				cm1.setEofc_ecop_id(m1.getEofd_ecop_id());
				cm1.setEofc_name(m1.getEofd_name());
				cm1.setEofc_content(m1.getEofd_content());
				cm1.setEofc_em_base(m1.getEofd_em_base());
				cm1.setEofc_co_base(m1.getEofd_co_base());
				cm1.setEofc_cp(m1.getEofd_cp());
				cm1.setEofc_op(m1.getEofd_op());
				cm1.setEofc_em_sum(m1.getEofd_em_sum());
				cm1.setEofc_co_sum(m1.getEofd_co_sum());
				cm1.setEofc_month_sum(m1.getEofd_month_sum());
				cm1.setEofc_start_date(m1.getEofd_start_date());
				cm1.setEofc_state(1);
//				cm1.setTempDate(m1.getTempDate() == null ? null : DateUtil
//						.getLastDay(m1.getTempDate()));
				
				cm1.setTempDate(null);
				
				cm1.setSicl_class(m1.getSicl_class());

				cfeeList.add(cm1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对起始日排序(从小到大)
	 * 
	 * @return List<Date>
	 */
	public List<Date> OrderByStartDate() {
		List<Date> list = new ArrayList<>();
		for (EmCommissionOutFeeDetailChangeModel m1 : cfeeList) {
			if (m1.getTempDate() != null) {
				list.add(m1.getTempDate());
			}
		}

		if (list.size() > 0) {
			for (int i = 0; i < list.size() - 1; i++) {
				for (int j = 0; j < list.size() - 1 - i; j++) {
					if (list.get(j).after(list.get(j + 1))) {
						Date date = list.get(j);
						list.set(j, list.get(j + 1));
						list.set(j + 1, date);
					}
				}
			}
		}

		return list;
	}

	/**
	 * 时间批量修改
	 * 
	 * @param date
	 */
	@Command("dateAll")
	@NotifyChange({ "cfeeList" })
	public void dateAll(@BindingParam("date") Date date,
			@BindingParam("index") Integer index,
			@BindingParam("class") String sicl_class) {
		try {
			if (sicl_class.equals("all")) {
				date = cfeeList.get(0).getTempDate();
				index = 0;
			}
			for (int i = index + 1; i < cfeeList.size(); i++) {
				if (cfeeList.get(i).getSicl_class().equals(sicl_class)
						|| sicl_class.equals("all")) {
					cfeeList.get(i).setTempDate(date);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提交
	 * 
	 */
	@Command("submit")
	@NotifyChange({ "cm", "cfeeList", "causeList" })
	public void submit() {

		EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();
		try {
			for (EmCommissionOutFeeDetailChangeModel m1 : cfeeList) {
				if(m1.getTempDate() == null){
					Messagebox.show("请选择停缴时间！", "INFORMATION",
							Messagebox.OK, Messagebox.ERROR);
					return;
				}	
				
				m1.setEofc_stop_date(m1.getTempDate() == null ? null
						: DateStringChange.DatetoSting(
								DateUtil.getLastDay(m1.getTempDate()),
								"yyyy-MM-dd"));
			}
			
			if(cm.getEcoc_stop_date() == null){
				Messagebox.show("请选择离职时间！", "INFORMATION",
						Messagebox.OK, Messagebox.ERROR);
				return;
			}	
			if(cm.getEcoc_stop_cause().equals("")){
				Messagebox.show("请选择离职原因！", "INFORMATION",
						Messagebox.OK, Messagebox.ERROR);
				return;
			}	
			
			cm.setEcoc_title_date(DateStringChange.DatetoSting(
					cm.getEcoc_stop_date(), "yyyy-MM-dd"));
			cm.setEcoc_stop_cause(cm.getEcoc_stop_cause());
			cm.setFeeList(cfeeList);
			cm.setEcoc_remark(cm.getEcoc_remark());
			
			String[] str = bll.termination(cm, cm.getEmba_name());

			if (str[0].equals("1")) {
				Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				cfeeList.clear();
				cm = null;
				causeList.clear();
				
				RedirectUtil util = new RedirectUtil();
				util.refreshEmUrl("/EmCommissionOut/EmCommissionOut_EmSelectList.zul");// url为跳转页面连接
			} else if (str[0].equals("0")) {
				Messagebox.show(str[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<String> getCauseList() {
		return causeList;
	}

	public void setCauseList(List<String> causeList) {
		this.causeList = causeList;
	}

	public final EmCommissionOutModel getM() {
		return m;
	}

	public final EmCommissionOutChangeModel getCm() {
		return cm;
	}

	public final List<EmCommissionOutFeeDetailChangeModel> getCfeeList() {
		return cfeeList;
	}

	public final void setM(EmCommissionOutModel m) {
		this.m = m;
	}

	public final void setCm(EmCommissionOutChangeModel cm) {
		this.cm = cm;
	}

	public final void setCfeeList(
			List<EmCommissionOutFeeDetailChangeModel> cfeeList) {
		this.cfeeList = cfeeList;
	}
}
