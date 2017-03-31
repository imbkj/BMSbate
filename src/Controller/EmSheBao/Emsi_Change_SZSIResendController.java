package Controller.EmSheBao;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

import Model.EmShebaoChangeSZSIModel;
import Util.MonthListUtil;
import Util.RedirectUtil;

public class Emsi_Change_SZSIResendController {
	private final int id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Emsi_SelBll bll = new Emsi_SelBll();
	private EmShebaoChangeSZSIModel ecModel;
	private boolean ifStop;
	private String[] ownmonthList;
	private String ownmonth;

	public Emsi_Change_SZSIResendController() {
		ecModel = bll.getEmSCSZSIData(id);

		ifStop = bll.ifStop();
		// 判断是否停止当月操作社保
		if (ifStop) {
			ownmonthList = bll.getOwnmonthByNow(false);

			MonthListUtil mlu = new MonthListUtil();
			ownmonth = mlu.getNextMonth(String.valueOf(bll
					.getSbUpdateOwnmonth()));// 所属月份加一个月
		} else {
			ownmonthList = bll.getOwnmonthByNow(true);
			ownmonth = ownmonthList[0];
		}
	}

	// 提交
	@Command("changeSZSI")
	public void changeSZSI(@BindingParam("winChangeSZSIR") Window win) {
		try {
			if (checkPage()) {
				ecModel.setOwnmonth(Integer.parseInt(ownmonth));

				// 调用新增方法
				Emsi_OperateBll opbll = new Emsi_OperateBll();
				String[] message = opbll.changeSZSIResend(ecModel);
				if ("1".equals(message[0])) {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();

					RedirectUtil util = new RedirectUtil();
					util.refreshEmUrl("/EmSheBao/Emsi_DeleteChange_List.zul");// url为跳转页面连接
				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				// 弹出提示
				Messagebox.show("您没有修改任何资料，无需提交!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} catch (Exception e) {
			Messagebox.show("特殊变更操作出错。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 检测表单
	private boolean checkPage() {
		boolean b = true;
		if (ecModel.getEscs_change() == null) {
			b = false;
			Messagebox.show("请选择变更类型!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (ownmonth == null) {
			b = false;
			Messagebox.show("请选择所属月份!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (ecModel.getEscs_content() == null
				|| "".equals(ecModel.getEscs_content())) {
			b = false;
			Messagebox
					.show("请填写变更的内容!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (ecModel.getEscs_remark() == null
				|| "".equals(ecModel.getEscs_remark())) {
			b = false;
			Messagebox.show("请填写备注信息!", "操作提示", Messagebox.OK, Messagebox.NONE);
		}
		return b;
	}

	public EmShebaoChangeSZSIModel getEcModel() {
		return ecModel;
	}

	public void setEcModel(EmShebaoChangeSZSIModel ecModel) {
		this.ecModel = ecModel;
	}

	public boolean isIfStop() {
		return ifStop;
	}

	public void setIfStop(boolean ifStop) {
		this.ifStop = ifStop;
	}

	public String[] getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(String[] ownmonthList) {
		this.ownmonthList = ownmonthList;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

}
