package Controller.CoQuotation;

import impl.MessageImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.MessageService;
import dal.LoginDal;

import Model.CoLatencyClientModel;
import Model.CoOfferModel;
import Model.SysMessageModel;
import Util.UserInfo;
import bll.CoQuotation.CoQuotation_List1Bll;
import bll.CoQuotation.CoofferOperateBll;

public class CoQuotation_AuditingController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tarpid = (String) Executions.getCurrent().getArg().get("id");
	private CoQuotation_List1Bll bll = new CoQuotation_List1Bll();
	private CoLatencyClientModel colamodel = new CoLatencyClientModel();
	private List<CoOfferModel> list = new ArrayList<CoOfferModel>();
	private CoOfferModel coofmodel = new CoOfferModel();
	private String clientsize;

	public CoQuotation_AuditingController() throws NumberFormatException,
			SQLException {
		if (id != null) {
			list = bll.getCoOfferInfoList(Integer.parseInt(id));
			if (list.size() > 0) {
				coofmodel = list.get(0);
				colamodel = bll.getModel(coofmodel.getCoof_cola_id());
			}
		}
	}

	// 弹出预览页面
	@Command("yulan")
	public void yulan() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("model", coofmodel);
		Window window = (Window) Executions.createComponents(
				"/CoQuotation/Quotation_ExcelAdd.zul", null, map);
		window.doModal();
	}

	// 弹出查看页面
	@Command("chakan")
	public void chakan() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coofid", coofmodel.getCoof_id());
		Window window = (Window) Executions.createComponents(
				"/CoQuotation/CoQuotationInfoOperate.zul", null, map);
		window.doModal();
	}

	// 审核
	@Command
	public void audit(@BindingParam("win") Window win) {
		if (tarpid != null) {
			coofmodel.setCoof_auditing_name(UserInfo.getUsername());
			CoofferOperateBll obll = new CoofferOperateBll();
			String[] str = obll.CoofferAuditUpdate(coofmodel,
					Integer.parseInt(tarpid));
			if (str[0] == "1") {
				String content=colamodel.getCola_company()+"的报价单："+coofmodel.getCoof_name()+" 已审核完成，可以做下一步操作了。";
				String tittle=colamodel.getCola_company()+"的报价单："+coofmodel.getCoof_name()+"报价单审核";
				sendMsg(coofmodel.getCoof_auditaddname(),content,tittle);
				Messagebox.show("审核成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("审核失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("没有任务单流程", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 打开潜在客户信息
	@Command
	public void cola() {
		Map map = new HashMap<>();
		map.put("cola", colamodel);
		Window window = (Window) Executions.createComponents(
				"../CoLatencyClient/CoLatencyClientInfo.zul", null, map);
		window.doModal();
	}

	// 退回
	@Command
	public void back(@BindingParam("win") Window win) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("tarpid", tarpid);
		map.put("model", coofmodel);
		map.put("flag", "0");
		Window window = (Window) Executions.createComponents(
				"../CoQuotation/CoQuotation_AuditBack.zul", null, map);
		window.doModal();
		if (map.get("flag") == "1") {
			win.detach();
		}
	}

	// 发短信
	private void sendMsg(String symr_name, String content, String tittle) {
		// 发短信
		MessageService msgservice = new MessageImpl("CoOffer",
				Integer.parseInt(id));
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

	public CoLatencyClientModel getColamodel() {
		return colamodel;
	}

	public void setColamodel(CoLatencyClientModel colamodel) {
		this.colamodel = colamodel;
	}

	public CoOfferModel getCoofmodel() {
		return coofmodel;
	}

	public void setCoofmodel(CoOfferModel coofmodel) {
		this.coofmodel = coofmodel;
	}

	public String getClientsize() {
		return clientsize;
	}

	public void setClientsize(String clientsize) {
		this.clientsize = clientsize;
	}

}
