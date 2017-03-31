package Controller.EmSheBao;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import Model.EmSheBaoChangeModel;
import Model.EmShebaoUpdateModel;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

public class Emsi_Edit_Salary_UpdateController {
	private final int changeId = Integer.parseInt(Executions.getCurrent()
			.getArg().get("daid").toString());
	private Emsi_SelBll bll = new Emsi_SelBll();
	private String[] ownmonthList;
	private boolean ifaud;
	private boolean ifStop;
	private EmSheBaoChangeModel ecModel;
	private EmShebaoUpdateModel sbModel;
	private boolean existsShebao = false;
	private String existsMessage;
	// 页面获取值
	private String ownmonth;
	private String remark;
	private int radix;
	private boolean aud;

	public Emsi_Edit_Salary_UpdateController() {
		ecModel = bll.getChangById(changeId);
		sbModel = bll.getShebaoUpdateByGid(ecModel.getGid());
		if (sbModel == null) {
			existsShebao = true;
			existsMessage = "无此员工的社保信息，不能作工资修改!";
			return;
		} else {
			ifStop = bll.ifStop();
			// 判断是否停止当月操作社保
			if (ifStop) {
				ownmonthList = bll.getOwnmonthByNow(false);
			} else {
				ownmonthList = bll.getOwnmonthByNow(true);
			}
			ifaud = bll.ifAud();
			aud = ifaud;
			ownmonth = String.valueOf(ecModel.getOwnmonth());
			radix = ecModel.getEmsc_radix();
			remark = ecModel.getEmsc_remark();
		}
	}

	// 所属月份改变判断数据是否需要审核
	@Command("ownmonthChange")
	@NotifyChange("aud")
	public void ownmonthChange() {
		if (bll.ifaud(ownmonth)) {
			aud = false;
		} else {
			aud = ifaud;
		}
	}

	// 编辑提交
	@Command("salaryUp")
	public void salaryUp(@BindingParam("win") Window win,
			@BindingParam("rg") Radiogroup rg) {
		try {
			if (checkPage()) {
				EmShebaoUpdateModel m = sbModel;
				m.setOwnmonth(Integer.parseInt(ownmonth));
				m.setEsiu_remark(remark);
				m.setEsiu_addname(UserInfo.getUsername());
				m.setEsiu_radix(this.radix);
				if (aud) {
					m.setIfdeclare(5);
				} else {
					m.setIfdeclare(Integer.parseInt(rg.getSelectedItem()
							.getValue().toString()));
				}
				// 调用编辑方法
				Emsi_OperateBll opbll = new Emsi_OperateBll();
				String[] message = opbll.editUpSalary(m, changeId,
						ecModel.getEmsc_tapr_id());
				if ("1".equals(message[0])) {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();
					RedirectUtil util=new RedirectUtil();
					util.refreshEmUrl("/EmSheBao/Emsi_index.zul");//url为跳转页面连接

				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("修改工资出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 检测表单
	private boolean checkPage() {
		boolean b = true;
		if (!checkOwnmonth()) {
			b = false;
			Messagebox.show("当前所属月份，已无法申报，请重新选择所属月份!", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		} else if (sbModel.getEsiu_computerid() == null
				|| "".equals(sbModel.getEsiu_computerid())) {
			b = false;
			Messagebox.show("电脑号不能为空，如是新增人员，请等待社保局审核数据后系统更新电脑号!", "操作提示",
					Messagebox.OK, Messagebox.NONE);
		} else if (radix == sbModel.getEsiu_radix()) {
			b = false;
			Messagebox.show("您没有修改月工资总额，请确认是否修改!", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		} else if (radix == 0) {
			b = false;
			Messagebox
					.show("请输入月工资总额!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (radix > 99999) {
			b = false;
			Messagebox.show("月工资总额不能大于99999元,如需申报十万元以上基数,请联系福利组!", "操作提示",
					Messagebox.OK, Messagebox.NONE);
		} else if (remark == null && aud) {
			b = false;
			Messagebox.show("该变更需审核，请填写申请原因!", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		}
		return b;
	}

	// 检测所属月份是否可提交
	private boolean checkOwnmonth() {
		try {
			for (String o : ownmonthList) {
				if (o.equals(ownmonth))
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String[] getOwnmonthList() {
		return ownmonthList;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isAud() {
		return aud;
	}

	public void setAud(boolean aud) {
		this.aud = aud;
	}

	public boolean isIfStop() {
		return ifStop;
	}

	public EmSheBaoChangeModel getEcModel() {
		return ecModel;
	}

	public int getRadix() {
		return radix;
	}

	public void setRadix(int radix) {
		this.radix = radix;
	}

	public boolean isExistsShebao() {
		return existsShebao;
	}

	public String getExistsMessage() {
		return existsMessage;
	}

	public EmShebaoUpdateModel getSbModel() {
		return sbModel;
	}

}
