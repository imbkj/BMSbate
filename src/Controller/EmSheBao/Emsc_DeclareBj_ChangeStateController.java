package Controller.EmSheBao;

import java.util.ArrayList;
import java.util.List;

import impl.MessageImpl;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import dal.LoginDal;
import dal.EmSheBao.Emsi_OperateDal;

import service.MessageService;

import bll.EmSheBao.EmSheBao_DSelectBll;
import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.EmSheBao.Emsc_DeclareSelBll;
import bll.EmSheBao.Emsi_SelBll;
import bll.SysMessage.Message_SelectBll;
import bll.SysMessage.SysMessage_AddBll;

import Model.EmShebaoBJModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Util.UserInfo;

public class Emsc_DeclareBj_ChangeStateController {
	private final int id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());
	private EmShebaoBJModel bjModel;
	private Emsc_DeclareSelBll selbll;
	// 页面值：原因
	private String reason;
	private boolean canOp = true;

	// 系统短信
	private SysMessage_AddBll smbll = new SysMessage_AddBll();
	private Message_SelectBll sbll = new Message_SelectBll();
	private List<LoginModel> loginList = smbll.getLoginList(" and log_id<>"
			+ UserInfo.getUserid());
	private List<LoginModel> deptlist = new ArrayList<LoginModel>();
	private String selectedname = "";
	private List<LoginModel> selectedlist = new ArrayList<LoginModel>();
	private MessageService msgservice;
	private LoginModel ml = new LoginModel();
	private String msgname = "";
	private List<SysMessageModel> list = new ArrayList<SysMessageModel>();
	private List<SysMessageModel> temList = sbll
			.gettemList(" and pmte_class='社会保险补缴'");
	private LoginDal d = new LoginDal();

	public Emsc_DeclareBj_ChangeStateController() {
		selbll = new Emsc_DeclareSelBll();
		bjModel = selbll.getBjInfoById(id);
		if (bjModel != null) {
			if ((bjModel.getCfCou() > 0 || bjModel.getEcCou() > 0 || bjModel
					.getEmsb_computeridlength() < 7)
					&& bjModel.getEmsb_ifdeclare() != 1)
				canOp = false;
		}

		// 系统短信
		List<LoginModel> llist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(bjModel.getEmsb_addname());
		m.setLog_id(d.getUserIDByname(bjModel.getEmsb_addname()));
		llist.add(m);

		if (llist.size() > 0) {
			for (LoginModel lm : llist) {
				if (lm.getLog_name() != null) {
					ml = lm;
					selectedlist.add(ml);
					getSelectname();
					if (msgname == "") {
						msgname = "'" + lm.getLog_name() + "'";
					} else {
						msgname = msgname + ",'" + lm.getLog_name() + "'";
					}
				}
			}
		}
		msgservice = new MessageImpl("EmShebaoBJ", id);
		deptlist = sbll.getLoginList(msgname);
		list = msgservice.Select();
		// 把登陆人的该业务ID的未读信息改为已读
		msgservice.Read();
	}

	// 添加备忘
	@Command("addFlag")
	@NotifyChange("bjModel")
	public void addFlag(@BindingParam("flag") Textbox txtFlag) {
		if (!"".equals(txtFlag.getValue())) {
			if (!txtFlag.getValue().equals(bjModel.getEmsb_flag())) {
				Emsc_DeclareOperateBll bll = new Emsc_DeclareOperateBll();
				bjModel.setEmsb_flag(txtFlag.getValue());
				int i = bll.UpFlag(bjModel, UserInfo.getUsername());
				if (i == 1) {
					Messagebox.show("备忘添加成功。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				} else {
					Messagebox.show("备忘添加失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("您并未修改备忘信息，无需添加。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		} else {
			Messagebox
					.show("请输入备忘信息。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 修改状态
	@Command("upState")
	public void upState(@BindingParam("state") Radiogroup rgState,
			@BindingParam("win") Window win) {
		try {
			int state = Integer.parseInt(rgState.getSelectedItem().getValue()
					.toString());
			String message = checkPage(state);
			String msg = "";
			if ("".equals(message)) {
				bjModel.setEmsb_ifdeclare(state);
				// 退回时录入原因
				if (state == 3) {
					bjModel.setEmsb_reason(reason);
				}
				Emsc_DeclareOperateBll opbll = new Emsc_DeclareOperateBll();
				String[] mes = opbll.BjDeclareUpState(bjModel,
						UserInfo.getUsername());

				if ("1".equals(mes[0])) {

					//if (state != 1) {// 除了已申报，其他状态都同步
						// 更新相应的医疗补交数据状态
						Emsi_OperateDal eDal = new Emsi_OperateDal();
						boolean ifJLBJ = eDal.getShebaoBJJL(bjModel.getGid(),
								bjModel.getOwnmonth(),
								bjModel.getEmsb_startmonth());
						if (ifJLBJ == true) {
							// 通过养老补交获取医疗补交数据
							EmShebaoBJModel jlM = new EmShebaoBJModel();
							Emsi_SelBll bll = new Emsi_SelBll();
							jlM = bll.getBjJLListByBJid(bjModel.getId());
							jlM.setEmsb_ifdeclare(state);
							jlM.setEmsb_dptime(bjModel.getEmsb_dptime());

							String[] message2 = opbll.BjJLDeclareUpState(jlM,
									UserInfo.getUsername());
							if (!"1".equals(message2[0])) {
								msg = "，但是社保医疗数据操作失败，请联系IT部！";
							}
						}
					//}

					// 退回系统短信
					if (state == 3) {
						EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
						for (LoginModel m : selectedlist) {
							SysMessageModel model = new SysMessageModel();
							model.setSyme_content(reason);// 短信内容
							model.setSyme_log_id(Integer.parseInt(UserInfo
									.getUserid()));// 发件人id
							model.setSymr_name(m.getLog_name());// 收件人姓名
							model.setSymr_log_id(m.getLog_id());
							model.setEmail(1);// 1表示同时发邮件
							model.setSyme_title(bjModel.getEmsb_company()
									+ " 的 " + bjModel.getEmsb_name()
									+ " 社保补交数据被退回");
							// 调用方法
							msgservice.Add(model);
						}
					}

					// 成功提示
					Messagebox.show(mes[1] + msg, "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();
				} else {
					// 错误提示
					Messagebox.show(mes[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			} else {
				Messagebox.show(message, "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 判断页面输入值是否合法
	private String checkPage(int state) {
		String message = "";
		if (state != bjModel.getEmsb_ifdeclare()) {
			if ((state == 6 || state == 1) && bjModel.getEmsb_dptime() == null) {
				message = "请输入交单时间。";
			} else if (state == 3 && selectedlist.size() <= 0) {
				message = "请选择退回短信收件人。";
			} else if (state == 3 && reason == null) {
				message = "请输入退回原因。";
			}
		} else {
			message = "您并未修改申报状态，无需更改。";
		}
		return message;
	}

	// 选择收件人
	@Command
	@NotifyChange("selectedname")
	public void checkname(@BindingParam("bd") Bandbox bx,
			@BindingParam("model") LoginModel model,
			@BindingParam("lbitem") Listitem item) {
		if (item.isSelected()) {
			selectedlist.add(model);
		} else {
			selectedlist.remove(model);
		}
		getSelectname();
	}

	// 把已选择的收件人串起来
	private void getSelectname() {
		selectedname = "";
		for (LoginModel m : selectedlist) {
			if (selectedname == "") {
				selectedname = m.getLog_name();
			} else {
				selectedname = selectedname + ";" + m.getLog_name();
			}
		}
	}

	// 选择模板
	@Command("temselect")
	@NotifyChange("reason")
	public void temselect(@BindingParam("temcb") Combobox temcb) {
		if (temcb.getValue() != null || !temcb.getValue().equals("")) {
			reason = "";
			SysMessageModel m = temcb.getSelectedItem().getValue();
			reason = m.getPmte_content();
		}
	}

	public EmShebaoBJModel getBjModel() {
		return bjModel;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean isCanOp() {
		return canOp;
	}

	public List<LoginModel> getLoginList() {
		return loginList;
	}

	public void setLoginList(List<LoginModel> loginList) {
		this.loginList = loginList;
	}

	public List<LoginModel> getDeptlist() {
		return deptlist;
	}

	public void setDeptlist(List<LoginModel> deptlist) {
		this.deptlist = deptlist;
	}

	public String getSelectedname() {
		return selectedname;
	}

	public void setSelectedname(String selectedname) {
		this.selectedname = selectedname;
	}

	public List<SysMessageModel> getList() {
		return list;
	}

	public void setList(List<SysMessageModel> list) {
		this.list = list;
	}

	public List<SysMessageModel> getTemList() {
		return temList;
	}

	public void setTemList(List<SysMessageModel> temList) {
		this.temList = temList;
	}
}
