package Controller.EmBodyCheck;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.Archives.EmArchive_SelectBll;
import bll.EmBodyCheck.EmBcInfo_OperateBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;
import bll.EmBodyCheck.EmbcItem_SelectBll;

import Model.EmBodyCheckItemModel;
import Model.EmBodyCheckModel;
import Model.EmbaseModel;
import Model.TaskProcessViewModel;

public class EmBc_InfoUpdateController {
	private EmBodyCheckModel model = (EmBodyCheckModel) Executions.getCurrent()
			.getArg().get("model");
	private Map map = Executions.getCurrent().getArg();
	private EmbaseModel emmodel = new EmbaseModel();
	private EmBcInfo_SelectBll selectbll = new EmBcInfo_SelectBll();
	// 员工信息
	private List<EmbaseModel> embaselist = new ArrayList<EmbaseModel>();
	private EmbcItem_SelectBll itembll = new EmbcItem_SelectBll();
	// 体检项目信息
	private List<EmBodyCheckItemModel> itemlist = new ArrayList<EmBodyCheckItemModel>();
	private String embctype = "";// 体检类型
	private Date declaredate, plandate, drawformdate, showformdate,
			drawreportdate, completeDate, showreportdate, balancedate;

	private Integer stateid;
	private int state = 0, kstate = 0;

	private EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();

	public EmBc_InfoUpdateController() {
		stateid = model.getEbcl_state();
		if (model.getGid() != null && model.getGid() > 0) {
			embaselist = selectbll.getEmBaseInfo(" and emba_state=1 and gid="
					+ model.getGid());
			if (embaselist.size() > 0) {
				emmodel = embaselist.get(0);
			}
		}
		if (model != null) {
			embctype = chengeEmbcType(model);
			declaredate = StrToDate(model.getEbcl_linkdate());
			plandate = StrToDate(model.getEbcl_plandate());
			drawformdate = StrToDate(model.getEbcl_drawformdate());
			showformdate = StrToDate(model.getEbcl_showformdate());
			drawreportdate = StrToDate(model.getEmcl_drawreportdate());
			completeDate = StrToDate(model.getEbcl_completedate());
			showreportdate = StrToDate(model.getEbcl_showreportdate());
			declaredate = StrToDate(model.getEbcl_linkdate());
			balancedate = StrToDate(model.getEbcl_balancedate());
		}
		itemlist = itembll.getEmBcItemInfo(" and ebit_id in("
				+ model.getEbcl_itemnums() + ")");
	}

	// 申报提交
	@Command
	public void Edit(@BindingParam("win") Window win) {
		Datebox declaredate = (Datebox) win.getFellow("declaredate");// 申报时间
		Datebox plandate = (Datebox) win.getFellow("plandate");// 安排体检时间
		Datebox completeDate = (Datebox) win.getFellow("completeDate");// 完成体检时间
		String sql = "";
		if (declaredate.getValue() != null) {
			sql = sql + ",ebcl_linkdate='" + changedate(declaredate.getValue())
					+ "'";
		}
		if (plandate.getValue() != null) {
			sql = sql + ",ebcl_plandate='" + changedate(plandate.getValue())
					+ "'";
		}

		if (completeDate.getValue() != null) {
			sql = sql + ",ebcl_completedate='"
					+ changedate(completeDate.getValue()) + "'";
			sql = sql + ",ebcl_showreportdate='"
					+ changedate(completeDate.getValue()) + "'";
		}

		if (balancedate != null) {
			sql = sql + ",ebcl_balancedate='" + changedate(balancedate) + "'";
		}
		if (model.getEbcl_bcid() != null) {
			sql = sql + ",ebcl_bcid='" + model.getEbcl_bcid() + "'";
		}
		if (model.getEbcl_finalcharge() != null) {
			if (model.getEbcl_finalcharge().compareTo(BigDecimal.ZERO) == 1) {
				sql = sql + ",ebcl_finalcharge='" + model.getEbcl_finalcharge()
						+ "'";
			}
		}
		skipnext();
		EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();
		Integer k = obll.updateEmbodyChecklist(model.getEbcl_id(), sql);
		if (k > 0) {
			map.put("flag", "1");
			Messagebox
					.show("修改成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			map.put("flag", "0");
			Messagebox.show("修改失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 判断任务单是否跟着走下一步
	private void skipnext() {
		Integer step = null;
		Integer nowstep = null;
		TaskProcessViewModel tmodel = new TaskProcessViewModel();
		if (model.getEmbc_tapr_id() != null) {
			EmArchive_SelectBll abll = new EmArchive_SelectBll();
			List<TaskProcessViewModel> tlist = abll.getLastId(model
					.getEmbc_tapr_id().toString());
			if (!tlist.isEmpty()) {
				tmodel = tlist.get(0);
				nowstep = tmodel.getWfno_step();
			}
		}
		String sqls = "";
		// 判断结算时间是否为空，不为空则则跳到结算完成步骤
		if (balancedate != null) {
			step = 9;
			sqls = ",ebcl_state=11";
		} else if (completeDate != null
				|| (model.getEbcl_bcid() != null && !model.getEbcl_bcid()
						.equals("0"))) {
			step = 5;
			sqls = ",ebcl_state=4";
			if (completeDate == null) {
				sqls = sqls + ",ebcl_completedate='" + changedate(completeDate)
						+ "'";
				sqls = sqls + ",ebcl_showreportdate='"
						+ changedate(completeDate) + "'";
			}
		} else if (plandate != null) {
			step = 4;
			sqls = ",ebcl_state=3";
		} else if (declaredate != null) {
			step = 3;
			sqls = ",ebcl_state=10";
		}
		if (step != null && !sqls.equals("") && !step.equals(9) && step != 9) {
			String[] str = obll.EmBodyCheckSkip(model, sqls, step, nowstep);
		} else {
			obll.updateEmbodyChecklist(model.getEbcl_id(), sqls);
		}
	}

	// 判断任务单是否跟着走下一步
	private void passnext() {

		if (stateid != null && stateid == 2 && declaredate != null)// 待申报——判断联系医院时间是否为空——》变为预约中
		{
			kstate = 1;
			state = 10;
			String sqls = ",ebcl_state=" + state;
			obll.EmBodyCheckEdit(model, sqls, "3");
		} else if (stateid != null && stateid == 10 && plandate != null)// 预约中——判断安排体检时间是否为空——》变为体检中
		{
			kstate = 1;
			state = 3;
			String sqls = ",ebcl_state=" + state;
			obll.EmBodyCheckEdit(model, sqls, "3");
		} else if (stateid != null && stateid == 3 && completeDate != null)// 体检中——判断后道收取报告时间是否为空——》变为已体检
		{
			kstate = 1;
			state = 4;
			String sqls = ",ebcl_state=" + state;
			obll.EmBodyCheckEdit(model, sqls, "3");
		} else if (stateid != null && stateid == 4 && showreportdate != null)// 已体检——判断客服收取报告时间是否为空——》变为客服已收报告
		{
			kstate = 1;
			state = 12;
			String sqls = ",ebcl_clientshowdate='" + changedate(showreportdate)
					+ "',ebcl_state=" + state;
			if (model.getEbcl_showreportpeople() != null
					&& !model.getEbcl_showreportpeople().equals("")) {
				sqls = sqls + ",ebcl_showclient='"
						+ model.getEbcl_showreportpeople() + "'";
			}
			obll.EmBodyCheckEdit(model, sqls, "3");
		} else if (stateid != null && stateid == 12 && balancedate != null
				&& model.getEbcl_finalcharge() != null)// 客服已收报告——判断客服收取报告时间是否为空——》变为已结算
		{
			kstate = 1;
			state = 11;
			String sqls = ",ebcl_state=" + state;
			obll.EmBodyCheckEdit(model, sqls, "3");
		}

	}

	// 把体检类型有数字转换成中文
	private String chengeEmbcType(EmBodyCheckModel m) {
		String type = "";
		if (m.getEbcl_type() != null) {
			if (m.getEbcl_type() == 0) {
				type = "单次体检";
			} else if (m.getEbcl_type() == 1) {
				type = "入职体检";
			} else if (m.getEbcl_type() == 2) {
				type = "年度体检";
			}
		}
		return type;
	}

	public EmBodyCheckModel getModel() {
		return model;
	}

	public void setModel(EmBodyCheckModel model) {
		this.model = model;
	}

	public EmbaseModel getEmmodel() {
		return emmodel;
	}

	public void setEmmodel(EmbaseModel emmodel) {
		this.emmodel = emmodel;
	}

	public List<EmBodyCheckItemModel> getItemlist() {
		return itemlist;
	}

	public void setItemlist(List<EmBodyCheckItemModel> itemlist) {
		this.itemlist = itemlist;
	}

	public String getEmbctype() {
		return embctype;
	}

	public void setEmbctype(String embctype) {
		this.embctype = embctype;
	}

	public Date getDeclaredate() {
		return declaredate;
	}

	public void setDeclaredate(Date declaredate) {
		this.declaredate = declaredate;
	}

	public Date getPlandate() {
		return plandate;
	}

	public void setPlandate(Date plandate) {
		this.plandate = plandate;
	}

	public Date getDrawformdate() {
		return drawformdate;
	}

	public void setDrawformdate(Date drawformdate) {
		this.drawformdate = drawformdate;
	}

	public Date getShowformdate() {
		return showformdate;
	}

	public void setShowformdate(Date showformdate) {
		this.showformdate = showformdate;
	}

	public Date getDrawreportdate() {
		return drawreportdate;
	}

	public void setDrawreportdate(Date drawreportdate) {
		this.drawreportdate = drawreportdate;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public Date getShowreportdate() {
		return showreportdate;
	}

	public void setShowreportdate(Date showreportdate) {
		this.showreportdate = showreportdate;
	}

	public Date getBalancedate() {
		return balancedate;
	}

	public void setBalancedate(Date balancedate) {
		this.balancedate = balancedate;
	}

	private String changedate(Date d) {
		String formatDate = "";
		if (d != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			formatDate = df.format(d);
		}
		return formatDate;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			if (str != null && !str.equals("")) {
				date = format.parse(str);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
