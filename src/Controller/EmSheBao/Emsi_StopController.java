package Controller.EmSheBao;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import Model.EmShebaoUpdateModel;
import Util.DateStringChange;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;
import bll.Embase.Embase_DimissionBll;

public class Emsi_StopController {
	private final int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());
	private Emsi_SelBll bll = new Emsi_SelBll();
	private Embase_DimissionBll dBll = new Embase_DimissionBll();
	
	private String[] ownmonthList;
	private boolean ifStop;
	private EmShebaoUpdateModel sbModel;
	private boolean existsShebao = false;
	private String existsMessage;
	// 页面获取值
	private String ownmonth;
	private String remark;
	private String stopreason;

	public Emsi_StopController() {
		sbModel = bll.getShebaoUpdateByGid(gid);
		if (sbModel == null) {
			existsShebao = true;
			existsMessage = "无此员工的社保信息，不能作停交!";
			return;
		} else {
			ifStop = bll.ifStop();
			// 判断是否停止当月操作社保
			if (ifStop) {
				ownmonthList = bll.getOwnmonthByNow(false);
			} else {
				ownmonthList = bll.getOwnmonthByNow(true);
			}
			
			// 将格式转换为上月底，如201505转为2015-4-30
			ownmonthList = dBll.getSBStopMonth(ownmonthList);
		}
	}

	// 提交
	@Command("stop")
	public void stop(@BindingParam("win") Window win,
			@BindingParam("rg") Radiogroup rg) {
		try {
			if (checkPage()) {
				DateStringChange dsc = new DateStringChange();
				EmShebaoUpdateModel m = sbModel;
				m.setOwnmonth(Integer.parseInt(dsc.ownmonthAddoneMonth(dsc
						.DatetoSting(dsc.StringtoDate(ownmonth, "yyyy-MM-dd"), "yyyyMM"))));
				m.setEsiu_remark(remark);
				m.setEsiu_addname(UserInfo.getUsername());
				m.setEsiu_stopreason(stopreason);
				m.setIfdeclare(Integer.parseInt(rg.getSelectedItem().getValue()
						.toString()));
				// 调用新增方法
				Emsi_OperateBll opbll = new Emsi_OperateBll();
				String[] message = opbll.stop(m);
				if (message[0].equals("1")) {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();
					RedirectUtil util = new RedirectUtil();
					util.refreshEmUrl("/EmSheBao/Emsi_index.zul");// url为跳转页面连接
				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			Messagebox.show("社保停交出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 检测表单
	private boolean checkPage() {
		boolean b = true;
		if (ownmonth == null) {
			b = false;
			Messagebox.show("请选择服务终止日!", "操作提示", Messagebox.OK, Messagebox.NONE);
		}
		// else if (sbModel.getEsiu_computerid() == null
		// || "".equals(sbModel.getEsiu_computerid())) {
		// b = false;
		// Messagebox.show("电脑号不能为空，如是新增人员，请等待社保局审核数据后系统更新电脑号!", "操作提示",
		// Messagebox.OK, Messagebox.NONE);
		// }
		else if (stopreason == null) {
			b = false;
			Messagebox.show("请选择停交原因!", "操作提示", Messagebox.OK, Messagebox.NONE);
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

	public String getStopreason() {
		return stopreason;
	}

	public void setStopreason(String stopreason) {
		this.stopreason = stopreason;
	}

	public boolean isExistsShebao() {
		return existsShebao;
	}

	public String getExistsMessage() {
		return existsMessage;
	}
}
