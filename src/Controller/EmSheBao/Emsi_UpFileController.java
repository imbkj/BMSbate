package Controller.EmSheBao;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import Model.EmShebaoUpdateModel;
import Model.EmbaseModel;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

public class Emsi_UpFileController {
	private final int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());
	private Emsi_SelBll bll = new Emsi_SelBll();
	private String[] ownmonthList;
	private boolean ifStop;
	private EmShebaoUpdateModel sbModel;
	private EmbaseModel emModel;
	private int ifsame = 0;
	private boolean existsShebao = false;
	private String existsMessage;
	// 页面获取值
	private String ownmonth;
	private String yltype;
	private String remark;
	private String mobile;
	private boolean mobileCk = false;

	public Emsi_UpFileController() {
		sbModel = bll.getShebaoUpdateByGid(gid);
		if (sbModel == null) {
			existsShebao = true;
			existsMessage = "无此员工的社保信息，不能作档案修改!";
			return;
		} else {
			ifStop = bll.ifStop();
			// 判断是否停止当月操作社保
			if (ifStop) {
				ownmonthList = bll.getOwnmonthByNow(false);
			} else {
				ownmonthList = bll.getOwnmonthByNow(true);
			}
			emModel = bll.getEmBase(gid);
			mobile = emModel.getEmba_mobile();
			yltype = sbModel.getEsiu_yltype();
		}
	}

	// 提交
	@Command("upfile")
	public void upfile(@BindingParam("win") Window win,
			@BindingParam("rg") Radiogroup rg) {
		try {
			if (checkPage()) {
				String con = checkItem();
				if (!"".equals(con)) {
					EmShebaoUpdateModel m = sbModel;
					m.setOwnmonth(Integer.parseInt(ownmonth));
					m.setEsiu_remark(remark);
					m.setEsiu_addname(UserInfo.getUsername());
					m.setEsiu_yltype(yltype);
					m.setMobile(mobile);
					m.setEsiu_ifsame(ifsame);
					m.setIfdeclare(Integer.parseInt(rg.getSelectedItem()
							.getValue().toString()));

					// 调用数据处理方法
					Emsi_OperateBll opbll = new Emsi_OperateBll();
					String[] message = opbll.upFile(m, con, 0);
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
				} else {
					// 弹出提示
					Messagebox.show("您没有修改任何资料，无需提交!", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("档案修改出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
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
		} else if (mobile == null) {
			b = false;
			Messagebox
					.show("请输入参保人手机!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if ("市内城镇".equals(sbModel.getEsiu_hj())
				|| "市内农村".equals(sbModel.getEsiu_hj())) {
			if (!"一档".equals(yltype)) {
				b = false;
				Messagebox.show("深户员工必须参加一档医疗!", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		}
		return b;
	}

	// 对比项目，看修改过哪几项
	private String checkItem() {
		String con = "";
		if (!yltype.equals(sbModel.getEsiu_yltype())) {
			con = con + "更改医疗类型为：" + yltype + ";";
		}
		if (mobile != null && mobileCk) {
			con = "更新参保手机号:" + mobile + ";";
			ifsame = 1;
		}
		return con;
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

	public String getMobile() {
		return mobile;
	}

	public String getYltype() {
		return yltype;
	}

	public void setYltype(String yltype) {
		this.yltype = yltype;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isExistsShebao() {
		return existsShebao;
	}

	public String getExistsMessage() {
		return existsMessage;
	}

	public boolean isMobileCk() {
		return mobileCk;
	}

	public void setMobileCk(boolean mobileCk) {
		this.mobileCk = mobileCk;
	}

}
