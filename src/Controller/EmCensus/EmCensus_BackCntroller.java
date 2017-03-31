package Controller.EmCensus;

import impl.MessageImpl;

import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.MessageService;
import dal.LoginDal;

import Model.BackCauseInfoModel;
import Model.EmCensusModel;
import Model.SysMessageModel;
import Util.UserInfo;
import bll.Back.BackAddBll;
import bll.EmCensus.EmCensus_OperateBll;
import bll.EmCensus.EmCensus_SelectBll;

public class EmCensus_BackCntroller {
	private String backcause;
	private String id = Executions.getCurrent().getArg().get("id").toString();
	private String tarpid;
	private EmCensusModel mol = new EmCensusModel();
	private Map map = Executions.getCurrent().getArg();

	public EmCensus_BackCntroller() {
		if (Executions.getCurrent().getArg().get("tarpid") != null
				&& !Executions.getCurrent().getArg().get("tarpid").equals("")) {
			tarpid = Executions.getCurrent().getArg().get("tarpid").toString();
		}
		EmCensus_SelectBll bll = new EmCensus_SelectBll();
		List<EmCensusModel> list = bll.getEmCensusInfo(" and emhj_id=" + id);
		if (list.size() > 0) {
			mol = list.get(0);
		}
	}

	// 退回
	@Command
	public void back(@BindingParam("win") Window win) {
		if (backcause != null && !backcause.equals("")) {
			EmCensus_OperateBll bll = new EmCensus_OperateBll();
			BackCauseInfoModel model = new BackCauseInfoModel();
			model.setBack_cause(backcause);
			model.setBack_prof_id(Integer.parseInt(id));
			model.setBack_type("hj");
			BackAddBll bbll = new BackAddBll();
			Integer k = bbll.back(model);
			if (k > 0) {
				String sql = ",emhj_state=4";
				EmCensusModel m = new EmCensusModel();
				m.setEmhj_id(Integer.parseInt(id));
				if(tarpid!=null&&!tarpid.equals(""))
				{
					m.setEmhj_taprid(Integer.parseInt(tarpid));
				}
				String[] str = bll.EmCensusback(m, sql);
				if (str[0] == "1") {
					String content = mol.getEmhj_name() + "的落户信息被"
							+ UserInfo.getUsername() + "退回";
					String tittle = mol.getEmhj_name() + "——落户信息退回";
					sendMsg(mol.getEmhj_addname(), content, tittle, "EmCensus");
					Messagebox.show("退回成功", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("退回失败", "提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} else {
			Messagebox.show("请输入退回原因", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 客服确认时退回并终止流程
	@Command
	public void clientback(@BindingParam("win") Window win) {
		if (backcause != null && !backcause.equals("")) {
			EmCensus_OperateBll bll = new EmCensus_OperateBll();
			BackCauseInfoModel model = new BackCauseInfoModel();
			model.setBack_cause(backcause);
			model.setBack_prof_id(Integer.parseInt(id));
			model.setBack_type("hj");
			BackAddBll bbll = new BackAddBll();
			Integer k = bbll.back(model);
			if (k > 0) {
				String sql = ",emhj_state=4";
				EmCensusModel m = new EmCensusModel();
				m.setEmhj_id(Integer.parseInt(id));
				if(tarpid!=null&&!tarpid.equals(""))
				{
					m.setEmhj_taprid(Integer.parseInt(tarpid));
				}
				String[] str = bll.EmCensusClientback(m, sql);
				if (str[0] == "1") {
					map.put("flag", "2");
					String content = mol.getEmhj_name() + "的落户信息被"
							+ UserInfo.getUsername() + "退回并终止了该员工的落户流程";
					String tittle = mol.getEmhj_name() + "——落户信息退回";
					sendMsg(mol.getEmhj_addname(), content, tittle, "EmCensus");
					Messagebox.show("退回成功", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("退回失败", "提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} else {
			Messagebox.show("请输入退回原因", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getBackcause() {
		return backcause;
	}

	public void setBackcause(String backcause) {
		this.backcause = backcause;
	}

	// 发短信
	private void sendMsg(String symr_name, String content, String tittle,
			String table) {
		// 发短信
		MessageService msgservice = new MessageImpl(table, Integer.parseInt(id));
		SysMessageModel msgmodel = new SysMessageModel();
		msgmodel.setSyme_content(content);// 短信内容
		msgmodel.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));// 发件人id
		msgmodel.setSyme_title(tittle);
		LoginDal d = new LoginDal();
		msgmodel.setSymr_name(symr_name);// 收件人姓名
		msgmodel.setSymr_log_id(d.getUserIDByname(symr_name));
		msgmodel.setEmail(0);
		msgmodel.setEmailtitle(tittle);
		msgservice.Add(msgmodel);
	}

}
