package Controller.CoReg;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoReg.CoReg_ListBll;
import bll.CoReg.CoReg_OperateBll;

import Model.CoOnlineRegisterInfoModel;
import Util.UserInfo;

public class CoReg_OperateController {

	// 页面传值
	private Integer daid;
	private Integer taprid;

	private CoOnlineRegisterInfoModel m = new CoOnlineRegisterInfoModel();
	private List<CoOnlineRegisterInfoModel> docList = new ListModelList<>();
	private String title = "";
	private String userid;
	private String username;
	private String timestr = "";
	private Date statetime = new Date();
	private Date reg_date = new Date();
	private String height = "";
	private Date rebkdate;
	private String rebkenddate, ifnoeend = "否";
	private boolean visend = true;

	public CoReg_OperateController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			taprid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("id").toString());
			setM(new CoReg_ListBll().getCoregInfo(daid));
			setTitle("状态变更 - " + m.getStatename());
			setUserid(UserInfo.getUserid());
			username = UserInfo.getUsername();
			timestrInit();
			if (m.getCori_state().equals("2") || m.getCori_state().equals("3")) {
				setHeight("200px");
			} else if (m.getCori_state().equals("6")) {
				setHeight("420px");
			} else {
				setHeight("400px");
			}
		} catch (Exception e) {
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void timestrInit() {
		if (m.getCori_if_responsebook() == 1
				|| (m.getCori_if_responsebook() == 0 && m
						.getCori_if_sign_responsebook() == 0)) {
			switch (m.getCori_state()) {
			case "5":
				timestr = "确认材料日期";
				break;
			case "6":
				timestr = "办理完成日期";
				break;
			default:
				break;
			}
		} else if (m.getCori_if_responsebook() == 0
				&& m.getCori_if_sign_responsebook() == 1) {
			switch (m.getCori_state()) {
			case "1":
				timestr = "反馈所需材料日期";
				setDocList(new CoReg_ListBll().getstateList(" and type=2"));
				break;
			case "2":
				timestr = "人事去街道办领表日期";
				break;
			case "3":
				timestr = "客服签收材料日期";
				break;
			case "4":
				timestr = "确认材料日期";
				break;
			case "6":
				timestr = "办理完成日期";
				break;
			default:
				break;
			}
		}
	}

	@Command("lbinit")
	public void lbinit(@BindingParam("lb") Listbox lb) {
		lb.setMultiple(true);
	}

	@Command
	@NotifyChange("visend")
	public void ifend() {
		if (ifnoeend.equals("是")) {
			visend = false;
		} else {
			visend = true;
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 * @param gd
	 * @param ltb
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd, @BindingParam("ltb") Listbox ltb) {
		try {
			String strinfo = "", restr = "", endinfo = "";
			// 反馈材料
			if (m.getCori_state().equals("1")) {
				Set<Listitem> set = ltb.getSelectedItems();
				String need_doc = "";
				for (Listitem lt : set) {
					need_doc += ((CoOnlineRegisterInfoModel) lt.getValue())
							.getStatename() + "，";
				}
				need_doc = need_doc.substring(0, need_doc.length() - 1);
				m.setCori_need_doc(need_doc);
			} else if (m.getCori_state().equals("2")) {
				restr = ",rebk_rs_taketime='" + getStringDate(statetime)
						+ "',rebk_rs_takename='" + UserInfo.getUsername()
						+ "',rebk_state=2";
			} else if (m.getCori_state().equals("3")) {
				restr = ",rebk_client_taketime='" + getStringDate(statetime)
						+ "',rebk_client_takename='" + UserInfo.getUsername()
						+ "',rebk_state=3";
			} else if (m.getCori_state().equals("6")) {
				if (ifnoeend == null || ifnoeend.equals("")) {
					strinfo = "请选择是否延期签订责任书";
				} else {
					m.setCori_reg_date(new SimpleDateFormat("yyyy-MM-dd")
							.format(reg_date));
					m.setCori_if_responsebook(1);
					if (ifnoeend.equals("否")
							&& m.getCori_if_sign_responsebook() == 1) {
						if (rebkdate == null) {
							strinfo = "请选择责任书签订时间";
						} else if (rebkenddate == null
								|| rebkenddate.equals("")) {
							strinfo = "请选择责任书有效期";
						} else {
							restr = ",rebk_signdate='"
									+ getStringDate(rebkdate)
									+ "',rebk_signname='"
									+ UserInfo.getUsername() + "',rebk_limit='"
									+ rebkenddate + "',rebk_state=5";
							endinfo = "end";
						}
					}
				}
			}
			if (strinfo.equals("")) {
			if (m.getCori_state().equals("4")) {
				restr = ",rebk_rs_signtime='" + getStringDate(statetime)
						+ "',rebk_rs_signname='" + UserInfo.getUsername()
						+ "',rebk_state=4";
				m.setCori_state((Integer.parseInt(m.getCori_state()) + 2) + "");
			} else {
				m.setCori_state((Integer.parseInt(m.getCori_state()) + 1) + "");
			}

			if (m.getCori_state().equals("7")) {
				m.setCori_reg_state(1);
			}

			m.setCrsr_statetime(new SimpleDateFormat("yyyy-MM-dd")
					.format(statetime));
			
				String[] str = new CoReg_OperateBll().CoRegUpdateState(m, gd);
				if (str[0].equals("1")) {
					if (!endinfo.equals("") && ifnoeend.equals("否")// ifnoeend等于否跳过补充责任书步骤
							&& m.getCori_if_sign_responsebook() == 1) {
						new CoReg_OperateBll().updateresponebook(restr,
								m.getCori_id());
						// 更新
						new CoReg_OperateBll().CoRegSkipToNext(m,
								Integer.parseInt(str[2]));
					} else if (m.getCori_if_sign_responsebook() == 1) {
						new CoReg_OperateBll().updateresponebook(restr,
								m.getCori_id());
					}
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show(strinfo, "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public Integer getDaid() {
		return daid;
	}

	public void setDaid(Integer daid) {
		this.daid = daid;
	}

	public Integer getTaprid() {
		return taprid;
	}

	public void setTaprid(Integer taprid) {
		this.taprid = taprid;
	}

	public CoOnlineRegisterInfoModel getM() {
		return m;
	}

	public void setM(CoOnlineRegisterInfoModel m) {
		this.m = m;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTimestr() {
		return timestr;
	}

	public void setTimestr(String timestr) {
		this.timestr = timestr;
	}

	public Date getStatetime() {
		return statetime;
	}

	public void setStatetime(Date statetime) {
		this.statetime = statetime;
	}

	public List<CoOnlineRegisterInfoModel> getDocList() {
		return docList;
	}

	public void setDocList(List<CoOnlineRegisterInfoModel> docList) {
		this.docList = docList;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Date getRebkdate() {
		return rebkdate;
	}

	public void setRebkdate(Date rebkdate) {
		this.rebkdate = rebkdate;
	}

	public String getRebkenddate() {
		return rebkenddate;
	}

	public void setRebkenddate(String rebkenddate) {
		this.rebkenddate = rebkenddate;
	}

	public String getIfnoeend() {
		return ifnoeend;
	}

	public void setIfnoeend(String ifnoeend) {
		this.ifnoeend = ifnoeend;
	}

	public boolean isVisend() {
		return visend;
	}

	public void setVisend(boolean visend) {
		this.visend = visend;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}
}
