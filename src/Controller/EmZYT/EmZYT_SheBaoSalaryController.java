package Controller.EmZYT;

import impl.CheckStringImpl;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.CheckStringService;

import Model.EmShebaoUpdateModel;
import Model.EmZYTModel;
import Util.DateStringChange;
import Util.UserInfo;
import bll.EmSheBao.Emsi_SelBll;
import bll.EmZYT.EmZYT_OperateBll;

public class EmZYT_SheBaoSalaryController {
	private EmZYTModel emztM = (EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM");
	private Emsi_SelBll bll = new Emsi_SelBll();
	private EmZYT_OperateBll oBll = new EmZYT_OperateBll();
	private String[] ownmonthList;
	private boolean ifStop;
	private EmShebaoUpdateModel sbModel;
	private boolean existsShebao = false;
	private String existsMessage;
	// 页面获取值
	private String ownmonth;
	private String remark;
	private int radix;

	private Window windim;
	
	public EmZYT_SheBaoSalaryController() {
		sbModel = bll.getShebaoUpdateByGid(emztM.getGid());
		if (sbModel == null) {
			existsShebao = true;
			existsMessage = "无此员工的社保信息，不能作工资修改!";
			return;
		} else {
			
			CheckStringService ch = new CheckStringImpl();
			if (ch.isNum(emztM.getEmzt_ylradix())) {// 判断委托单基数是否为整数
				
				ifStop = bll.ifStop();
				// 判断是否停止当月操作社保
				if (ifStop) {
					ownmonthList = bll.getOwnmonthByNow(false);
				} else {
					ownmonthList = bll.getOwnmonthByNow(true);
				}

				// 检查委托单委托缴费月是否可操作
				chkzytOwnmonth();

				radix = Integer.parseInt(emztM.getEmzt_ylradix());
				
			}
		
		}
	}

	
	@Command
	public void readInfo(@BindingParam("a") Window win) {
		if (windim == null) {
			windim = win;
		}
		
		CheckStringService ch = new CheckStringImpl();
		if (!ch.isNum(emztM.getEmzt_ylradix())) {
			windim.detach();
			Messagebox.show("委托单的养老数据存在小数，请更正!", "提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}
	
	// 检查委托单委托缴费月是否可操作
	public void chkzytOwnmonth() {
		DateStringChange dsc = new DateStringChange();
		String zytOwnmonth;

		try {
			zytOwnmonth = dsc.DatetoSting(
					dsc.StringtoDate(emztM.getEmzt_ylstart(), "yyyy-MM-dd"),
					"yyyyMM");
		} catch (Exception e) {
			zytOwnmonth = "";
		}

		if (ownmonthList.length > 0) {
			for (String om : ownmonthList) {// 遍历所属月份下拉框
				if (om.equals(zytOwnmonth)) {// 判断委托单委托缴费月是否包含在所属月份下拉框里
					ownmonth = zytOwnmonth;
					break;
				}
			}
		}

	}

	// 提交
	@Command("salaryUp")
	public void salaryUp() {
		try {

			if (checkPage()) {

				// 将接口数据插入社保model
				sbModel.setOwnmonth(emztM.getOwnmonth());
				sbModel.setEsiu_remark(emztM.getEmzt_remark());
				sbModel.setEsiu_addname(UserInfo.getUsername());
				sbModel.setIfdeclare(0);
				sbModel.setEsiu_radix(Integer.parseInt(emztM.getEmzt_ylradix()));

				String[] message = oBll.upEmZYTSheBaoSalary(emztM, sbModel);

				if (message[0].equals("1")) {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);

					// 调用父页面关闭窗口
					BindUtils.postGlobalCommand(null, null, "winClose", null);
				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			Messagebox.show("社保基数调整出错。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
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
