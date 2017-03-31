package Controller.EmSheBao;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmShebaoChangeSZSIModel;
import Model.EmShebaoUpdateModel;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

public class Emsi_Change_SZSIController {
	private final int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());
	private Emsi_SelBll bll = new Emsi_SelBll();
	private String[] ownmonthList;
	private boolean ifStop;
	private EmShebaoUpdateModel sbModel;
	private EmShebaoChangeSZSIModel ecModel;
	private boolean existsShebao = false;
	private String existsMessage;
	// 页面获取值
	private String ownmonth;
	private String changetype;
	private String change;
	private String changecon;
	private String changebefore;
	private String changebeforeCon;
	private boolean ifchange;
	private String emcs_s8;
	private String remark;
	private boolean changebeforeConRendonly = true;

	public Emsi_Change_SZSIController() {
		sbModel = bll.getShebaoUpdateByGid(gid);
		if (sbModel == null) {
			existsShebao = true;
			existsMessage = "无此员工的社保信息，不能作变更!";
			return;
		} else {
			ifStop = bll.ifStop();
			// 判断是否停止当月操作社保
			if (ifStop) {
				ownmonthList = bll.getOwnmonthByNow(false);
			} else {
				ownmonthList = bll.getOwnmonthByNow(true);
			}
			ifchange = false;
			change = "请先选择变更类型";
		}
	}

	public EmShebaoUpdateModel getSbModel() {
		return sbModel;
	}

	// 选择变更类型
	@Command("changetype")
	@NotifyChange({ "change", "changecon", "ifchange", "changebefore",
			"changebeforeCon", "changebeforeConRendonly" })
	public void changetype() {
		ifchange = true;
		switch (changetype) {
		case "变更户籍":
			changebeforeConRendonly = true;
			change = "变更后户籍";
			changebeforeCon = sbModel.getEsiu_hj();
			changebefore = "变更前户籍";
			if ("市内城镇".equals(changebeforeCon))
				changecon = "市外城镇";
			else
				changecon = "市内城镇";
			break;
		case "变更姓名":
			changebeforeConRendonly = true;
			change = "变更后姓名";
			changecon = "";
			changebeforeCon = sbModel.getEsiu_name();
			changebefore = "变更前姓名";
			break;
		case "变更身份证号码":
			changebeforeConRendonly = true;
			change = "变更后身份证号码";
			changecon = "";
			changebeforeCon = sbModel.getEsiu_idcard();
			changebefore = "变更前身份证号码";
			break;
		case "恢复医疗卡号":
			changebeforeConRendonly = true;
			change = "恢复后医疗卡号";
			changecon = "";
			changebeforeCon = sbModel.getEsiu_lbid();
			changebefore = "变更前医疗卡号";
			break;
		case "合并电脑号":
			changebeforeConRendonly = false;
			change = "合并后保留电脑号";
			changecon = "";
			changebeforeCon = sbModel.getEsiu_computerid();
			changebefore = "变更前电脑号";
			break;
		case "变更职别":
			changebeforeConRendonly = true;
			change = "变更后职别";
			changecon = "";
			changebeforeCon = sbModel.getEsiu_officialrank();
			changebefore = "变更前职别";
			break;
		default:
			break;
		}
	}

	// 提交
	@Command("changeSZSI")
	public void changeSZSI(@BindingParam("win") Window win) {
		try {
			if (checkPage()) {
				ecModel = new EmShebaoChangeSZSIModel();
				String con = checkCon();
				if (!"".equals(con)) {
					ecModel.setGid(gid);
					ecModel.setOwnmonth(Integer.parseInt(ownmonth));
					ecModel.setEscs_change(changetype);
					ecModel.setEscs_content(con);
					ecModel.setEscs_s8(emcs_s8);
					ecModel.setEscs_addname(UserInfo.getUsername());
					ecModel.setEscs_remark(remark);
					ecModel.setEscs_name(sbModel.getEsiu_name());

					// 调用新增方法
					Emsi_OperateBll opbll = new Emsi_OperateBll();
					String[] message = opbll.changeSZSI(ecModel);
					if ("1".equals(message[0])) {
												
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
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			Messagebox.show("特殊变更操作出错。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 检测表单
	private boolean checkPage() {
		boolean b = true;
		if (changetype == null) {
			b = false;
			Messagebox.show("请选择变更类型!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (ownmonth == null) {
			b = false;
			Messagebox.show("请选择所属月份!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (changecon == null || "".equals(changecon)) {
			b = false;
			Messagebox
					.show("请填写变更的内容!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (remark == null || "".equals(remark)) {
			b = false;
			Messagebox.show("请填写备注信息!", "操作提示", Messagebox.OK, Messagebox.NONE);
		}
		return b;
	}

	// 检测修改内容
	private String checkCon() {
		String con = "";
		switch (changetype) {
		case "变更户籍":
			if (!changecon.equals(sbModel.getEsiu_hj())) {
				con = "户籍由“" + sbModel.getEsiu_hj() + "”改为“" + changecon + "”";
				ecModel.setEscs_changehj(changecon);
			}
			break;
		case "变更姓名":
			if (!changecon.equals(sbModel.getEsiu_name())) {
				con = "姓名由“" + sbModel.getEsiu_name() + "”改为“" + changecon
						+ "”";
				ecModel.setEscs_changename(changecon);
			}
			break;
		case "变更身份证号码":
			if (!changecon.equals(sbModel.getEsiu_idcard())) {
				con = "身份证由“" + sbModel.getEsiu_idcard() + "”改为“" + changecon
						+ "”";
				ecModel.setEscs_changeidcard(changecon);
			}
			break;
		case "恢复医疗卡号":
			con = "恢复医疗卡号";
			ecModel.setEscs_changeylid(changecon);
			break;
		case "合并电脑号":
			if (!changecon.equals(changebeforeCon)) {
				con = "电脑号由“" + changebeforeCon + "”合并为“" + changecon + "”";
				ecModel.setEscs_changecid(changecon);
			}
			break;
		case "变更职别":
			if (!changecon.equals(sbModel.getEsiu_officialrank())) {
				con = "职别由“" + sbModel.getEsiu_officialrank() + "”改为“"
						+ changecon + "”";
				ecModel.setEscs_changeofficialrank(changecon);
			}
			break;
		default:
			break;
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

	public boolean isIfStop() {
		return ifStop;
	}

	public String getChangetype() {
		return changetype;
	}

	public void setChangetype(String changetype) {
		this.changetype = changetype;
	}

	public String getChangecon() {
		return changecon;
	}

	public void setChangecon(String changecon) {
		this.changecon = changecon;
	}

	public String getChange() {
		return change;
	}

	public boolean isIfchange() {
		return ifchange;
	}

	public String getEmcs_s8() {
		return emcs_s8;
	}

	public void setEmcs_s8(String emcs_s8) {
		this.emcs_s8 = emcs_s8;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getChangebefore() {
		return changebefore;
	}

	public void setChangebeforeCon(String changebeforeCon) {
		this.changebeforeCon = changebeforeCon;
	}

	public boolean isExistsShebao() {
		return existsShebao;
	}

	public String getExistsMessage() {
		return existsMessage;
	}

	public String getChangebeforeCon() {
		return changebeforeCon;
	}

	public boolean isChangebeforeConRendonly() {
		return changebeforeConRendonly;
	}

}
