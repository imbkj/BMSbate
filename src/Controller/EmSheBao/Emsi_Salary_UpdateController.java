package Controller.EmSheBao;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;
import Model.EmShebaoUpdateModel;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

public class Emsi_Salary_UpdateController {
	private final int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());
	private Emsi_SelBll bll = new Emsi_SelBll();
	private String[] ownmonthList;
	private boolean ifStop;
	private EmShebaoUpdateModel sbModel;
	private boolean existsShebao = false;
	private String existsMessage;
	// 页面获取值
	private String ownmonth;
	private String remark;
	private int radix;

	public Emsi_Salary_UpdateController() {
		sbModel = bll.getShebaoUpdateByGid(gid);
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
			radix = sbModel.getEsiu_radix();
		}
	}

	// 提交
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
				m.setIfdeclare(Integer.parseInt(rg.getSelectedItem()
							.getValue().toString()));
				// 调用新增方法
				Emsi_OperateBll opbll = new Emsi_OperateBll();
				String[] message = opbll.upSalary(m);
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
			Messagebox.show("修改工资出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 检测表单
	private boolean checkPage() {
		boolean b = true;
		if (ownmonth == null) {
			b = false;
			Messagebox.show("请选择所属月份!", "操作提示", Messagebox.OK, Messagebox.NONE);
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
		}
		return b;
	}

	public int getGid() {
		return gid;
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

	public boolean isIfStop() {
		return ifStop;
	}

	public EmShebaoUpdateModel getSbModel() {
		return sbModel;
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
}
