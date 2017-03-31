package Controller.EmBodyCheck;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmBodyCheckModel;
import Model.TaskProcessViewModel;
import Util.UserInfo;
import bll.Archives.EmArchive_SelectBll;
import bll.EmBodyCheck.EmBcInfo_OperateBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;

public class Embc_UpdateController {
	private String idstr = (String) Executions.getCurrent().getArg()
			.get("idstr");
	private List<EmBodyCheckModel> list = (List<EmBodyCheckModel>) Executions
			.getCurrent().getArg().get("list");
	private Date declaredate, plandate, drawformdate, showformdate,
			drawreportdate, completeDate, showreportdate, balancedate;
	private String showformpeople, showreportpeople;
	private BigDecimal fee;
	private int state = 0, kstate = 0;
	private EmBcInfo_SelectBll sebll = new EmBcInfo_SelectBll();
	private Integer stateid = sebll.getStateId(idstr);
	private EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();

	// 申报提交
	@Command
	public void Edit(@BindingParam("win") Window win) {
		String sql = "";
		if (declaredate != null) {
			sql = sql + ",ebcl_linkdate='" + changedate(declaredate) + "'";// 联系医院时间
		}
		if (plandate != null) {
			sql = sql + ",ebcl_plandate='" + changedate(plandate) + "'";// 安排体检时间
		}
		if (completeDate != null) {
			sql = sql + ",ebcl_completedate='" + changedate(completeDate) + "'";// 完成体检时间
			sql = sql + ",ebcl_showreportdate='" + changedate(completeDate)
					+ "'";
		}

		if (balancedate != null) {
			sql = sql + ",ebcl_balancedate='" + changedate(balancedate) + "'";
		}
		if (fee != null) {
			sql = sql + ",ebcl_finalcharge='" + fee + "'";
		}
		skipnext();
		Integer k = obll.EditEmbodyChecklist(idstr, sql);
		if (k > 0) {
			Messagebox
					.show("修改成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show("修改失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 判断任务单是否跟着走下一步
	private void skipnext() {
		Integer step = null;
		String sqls = "";
		// 判断结算时间是否为空，不为空则则跳到结算完成步骤
		if (balancedate != null) {
			step = 9;
			sqls = ",ebcl_state=11";
			if (balancedate == null) {
				sqls = sqls + ",ebcl_balancedate=getdate()";
			}
		} else if (completeDate != null) {
			step = 5;
			sqls = ",ebcl_state=4";
		} else if (plandate != null) {
			step = 4;
			sqls = ",ebcl_state=3";
		} else if (declaredate != null) {
			step = 3;
			sqls = ",ebcl_state=10";
		}
		for (EmBodyCheckModel m : list) {
			// if (m.getEbcl_balancedate() == null)
			if (m.getEmbc_tapr_id() != null && !sqls.equals("")
					&& !step.equals(9) && step != 9) {
				if (step != null) {
					Integer nowstep = null;
					TaskProcessViewModel tmodel = new TaskProcessViewModel();
					EmArchive_SelectBll abll = new EmArchive_SelectBll();
					List<TaskProcessViewModel> tlist = abll.getLastId(m
							.getEmbc_tapr_id().toString());
					if (!tlist.isEmpty()) {
						tmodel = tlist.get(0);
						nowstep = tmodel.getWfno_step();
					}
					String[] str = obll.EmBodyCheckSkip(m, sqls, step, nowstep);
				}
			} else {
				obll.updateEmbodyChecklist(m.getEbcl_id(), sqls);
			}
		}

	}

	// 判断任务单是否跟着走下一步
	private void passnext() {
		if (stateid != null && stateid == 2 && declaredate != null)// 待申报——判断联系医院时间是否为空——》变为预约中
		{
			kstate = 1;
			state = 10;
			String sqls = ",ebcl_state=" + state;
			for (EmBodyCheckModel m : list) {
				// if (m.getEbcl_linkdate() == null)
				if (m.getEmbc_tapr_id() != null) {
					obll.EmBodyCheckEdit(m, sqls, "3");
				} else {
					obll.updateEmbodyChecklist(m.getEbcl_id(), sqls);
				}
			}
		} else if (stateid != null && stateid == 10 && plandate != null)// 预约中——判断安排体检时间是否为空——》变为体检中
		{
			kstate = 1;
			state = 3;
			String sqls = ",ebcl_state=" + state;
			for (EmBodyCheckModel m : list) {
				// if (m.getEbcl_plandate() == null)
				if (m.getEmbc_tapr_id() != null) {
					obll.EmBodyCheckEdit(m, sqls, "3");
				} else {
					obll.updateEmbodyChecklist(m.getEbcl_id(), sqls);
				}
			}
		} else if (stateid != null && stateid == 3 && completeDate != null)// 体检中——判断后道收取报告时间是否为空——》变为已体检
		{
			kstate = 1;
			state = 4;
			String sqls = ",ebcl_state=" + state;
			for (EmBodyCheckModel m : list) {
				// if (m.getEbcl_drawreportdate() == null)
				if (m.getEmbc_tapr_id() != null) {
					obll.EmBodyCheckEdit(m, sqls, "3");
				} else {
					obll.updateEmbodyChecklist(m.getEbcl_id(), sqls);
				}
			}
		} else if (stateid != null && stateid == 4 && showreportdate != null)// 已体检——判断客服收取报告时间是否为空——》变为客服已收报告
		{
			kstate = 1;
			state = 12;
			String sqls = ",ebcl_clientshowdate='" + changedate(showreportdate)
					+ "',ebcl_state=" + state;
			if (showreportpeople != null && !showreportpeople.equals("")) {
				sqls = sqls + ",ebcl_showclient='" + showreportpeople + "'";
			}
			for (EmBodyCheckModel m : list) {
				// if (m.getEbcl_clientshowdate() == null)
				if (m.getEmbc_tapr_id() != null) {
					obll.EmBodyCheckEdit(m, sqls, "3");
				} else {
					obll.updateEmbodyChecklist(m.getEbcl_id(), sqls);
				}
			}
		} else if (stateid != null && stateid == 12 && balancedate != null
				&& fee != null)// 客服已收报告——判断客服收取报告时间是否为空——》变为已结算
		{
			kstate = 1;
			state = 11;
			String sqls = ",ebcl_state=" + state;
			for (EmBodyCheckModel m : list) {
				// if (m.getEbcl_balancedate() == null)
				if (m.getEmbc_tapr_id() != null) {
					obll.EmBodyCheckEdit(m, sqls, "3");
				} else {
					obll.updateEmbodyChecklist(m.getEbcl_id(), sqls);
				}
			}
		}
	}

	private String changedate(Date d) {
		String formatDate = "";
		if (d != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			formatDate = df.format(d);
		}
		return formatDate;
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

	public String getShowformpeople() {
		return showformpeople;
	}

	public void setShowformpeople(String showformpeople) {
		this.showformpeople = showformpeople;
	}

	public String getShowreportpeople() {
		return showreportpeople;
	}

	public void setShowreportpeople(String showreportpeople) {
		this.showreportpeople = showreportpeople;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

}
