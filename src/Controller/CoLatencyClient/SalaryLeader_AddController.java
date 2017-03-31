package Controller.CoLatencyClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import Model.CoBaseModel;
import Model.CoLatencyClientModel;
import Model.CoServiceRequestModel;
import Model.PubTradeModel;
import Model.SalaryPaperCoModel;
import Util.UserInfo;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.CoLatencyClient.CoServiceRequestOperateBll;
import bll.CoLatencyClient.CoServiceRequestSelectBll;
import bll.EmSalary.ItemFormula_OperateBll;
import bll.SalaryPaper.SalaryPaperBll;
import bll.SystemControl.Data_PopedomBll;

public class SalaryLeader_AddController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tperid = (String) Executions.getCurrent().getArg().get("id");
	private CoBaseModel cmodel = new CoBaseModel();
	private CoLatencyClient_AddBll bll = new CoLatencyClient_AddBll();
	private CoServiceRequestSelectBll sbll = new CoServiceRequestSelectBll();
	private List<PubTradeModel> tradelist = bll.getTradeIndo();
	private CoServiceRequestModel servicemodel = new CoServiceRequestModel();
	private List<String> datelistt = new ArrayList<String>();// 天的列表
	private CoLatencyClientModel frommodel = (CoLatencyClientModel) Executions
			.getCurrent().getArg().get("cola");
	private String sign = "";
	private List<String> loginlist = new ArrayList<String>();

	private SalaryPaperCoModel cosalarysetm = new SalaryPaperCoModel();
	private SalaryPaperBll spb = new SalaryPaperBll();
	
	public SalaryLeader_AddController() {
		servicemodel = sbll.getRequestInfo(" and csqe_id=" + id);
		cmodel = sbll.getCobaseInfo(" and cid=" + servicemodel.getCid());
		loginlist = sbll.getSalarylist();
		datelistt = datelistAdd();
		
		try {
			cosalarysetm = spb.getmodellist(servicemodel.getCid()).get(0);
		} catch (Exception e) {

		}
	}

	private List<String> datelistAdd() {
		List<String> li = new ArrayList<String>();
		for (int i = 1; i <= 31; i++) {
			li.add(i + "日");
		}
		return li;
	}

	// 分配薪酬负责人
	@Command
	public void summit(@BindingParam("win") Window win,
			@BindingParam("salaryleader") String salaryleader,
			@BindingParam("salaryduty") String salaryduty) {
		if (salaryleader != null && !salaryleader.equals("")) {
			if (salaryduty != null && !salaryduty.equals("")) {
				CoServiceRequestOperateBll obll = new CoServiceRequestOperateBll();
				String sql = ",csqe_salaryleader='" + salaryleader.substring(1)
						+ "',csqe_salaryassort='" + UserInfo.getUsername()
						+ "'";

				sql = sql + ",csqe_assorttime='" + DateToStr(new Date()) + "'";
				sql = sql + ",csqe_salaryduty='" + salaryduty.substring(1)
						+ "'";
				String[] str = new String[5];
				str = obll.CoServiceRequest_update(servicemodel, sql,
						Integer.parseInt(tperid));
				if (str[0] == "1") {
					Messagebox.show("提交成功", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("提交失败", "提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请选择薪酬负责人", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择薪酬审核人", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 分配薪酬负责人和薪酬审核人
	@Command
	public void summitend(@BindingParam("win") Window win) {
		if (servicemodel.getCsqe_salaryleader() == null
				|| servicemodel.getCsqe_salaryleader().equals("")) {
			Messagebox.show("请选择薪酬负责人", "提示", Messagebox.OK, Messagebox.ERROR);
		} else if (servicemodel.getCsqe_auditname() == null
				|| servicemodel.getCsqe_auditname().equals("")) {
			Messagebox.show("请选择薪酬审核人", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			CoServiceRequestOperateBll obll = new CoServiceRequestOperateBll();
			String[] str = new String[5];
			String sql = ",csqe_salaryleader='"
					+ servicemodel.getCsqe_salaryleader().substring(1)
					+ "',csqe_auditname='"
					+ servicemodel.getCsqe_auditname().substring(1) + "'";
			str = obll.CoServiceRequest_update(servicemodel, sql,
					Integer.parseInt(tperid));
			
			if (str[0] == "1") {
				//新增电子工资单设置表数据
				cosalarysetm.setCid(servicemodel.getCid());
				spb.coinfoadd(cosalarysetm);
				
				// 添加工资项目设置流程
				ItemFormula_OperateBll utbll = new ItemFormula_OperateBll();
				utbll.clcAddSalaryItemId(cmodel.getCid(),
						Integer.parseInt(Util.DateStringChange.getOwnmonth()),
						UserInfo.getUsername());
				obll.updateCobase(servicemodel.getCsqe_salaryleader()
						.substring(1), servicemodel.getCsqe_auditname()
						.substring(1), servicemodel.getCid());
				// 薪酬负责人权限添加
				Data_PopedomBll ddll = new Data_PopedomBll();
				ddll.Data_Popedomcsadd(servicemodel.getCid(), servicemodel
						.getCsqe_salaryleader().substring(1));
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 修改email工资单类型
	@Command
	@NotifyChange("cosalarysetm")
	public void changeGZEmailtype(@BindingParam("emailtype") String emailtype) {
		if (emailtype.equals("附件pdf") || emailtype.equals("附件excel")) {
			cosalarysetm.setCoss_emailouto(0);
			Messagebox.show(
					"附件pdf 和 附件excel 类型的工资单暂时不能自动发送，已将“Email工资单是否自动发送”改成“否”！",
					"操作提示", Messagebox.OK, Messagebox.INFORMATION);
		}

	}

	// 工资单显示判断

	@Command
	public void setvisble(@BindingParam("grid1") Row gdzz,
			@BindingParam("grid2") Row gdemail,
			@BindingParam("comb") Combobox cm) {
		if (cm.getValue().equals("E-mail工资单")) {
			gdzz.setVisible(false);
			gdemail.setVisible(true);
		}
		else if (cm.getValue().equals("密封工资单")) {
			gdzz.setVisible(true);
			gdemail.setVisible(false);
		} else {
			gdzz.setVisible(false);
			gdemail.setVisible(false);
		}

	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public String DateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = "";
		if (date != null && !date.equals("")) {
			str = format.format(date);
		}
		return str;
	}

	public CoBaseModel getCmodel() {
		return cmodel;
	}

	public void setCmodel(CoBaseModel cmodel) {
		this.cmodel = cmodel;
	}

	public List<PubTradeModel> getTradelist() {
		return tradelist;
	}

	public void setTradelist(List<PubTradeModel> tradelist) {
		this.tradelist = tradelist;
	}

	public CoServiceRequestModel getServicemodel() {
		return servicemodel;
	}

	public void setServicemodel(CoServiceRequestModel servicemodel) {
		this.servicemodel = servicemodel;
	}

	public List<String> getDatelistt() {
		return datelistt;
	}

	public void setDatelistt(List<String> datelistt) {
		this.datelistt = datelistt;
	}

	public CoLatencyClientModel getFrommodel() {
		return frommodel;
	}

	public void setFrommodel(CoLatencyClientModel frommodel) {
		this.frommodel = frommodel;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public List<String> getLoginlist() {
		return loginlist;
	}

	public void setLoginlist(List<String> loginlist) {
		this.loginlist = loginlist;
	}

	public SalaryPaperCoModel getCosalarysetm() {
		return cosalarysetm;
	}

	public void setCosalarysetm(SalaryPaperCoModel cosalarysetm) {
		this.cosalarysetm = cosalarysetm;
	}

}
