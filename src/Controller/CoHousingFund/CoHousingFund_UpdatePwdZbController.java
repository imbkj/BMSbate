package Controller.CoHousingFund;

import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundPwdModel;
import Util.SingleBllFactory;
import bll.CoHousingFund.CoHousingFund_PwdBll;

public class CoHousingFund_UpdatePwdZbController {

	private CoHousingFundPwdModel cfpm;
	private CoHousingFund_PwdBll cfpb = SingleBllFactory.getInstance()
			.getCfpb();
	private List<CoHousingFundPwdModel> pwdList;
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	private CoHousingFundPwdChangeModel cfpc = (CoHousingFundPwdChangeModel) Executions
			.getCurrent().getArg().get("cfpc");
	private String remark;
	private String currentime;

	public CoHousingFund_UpdatePwdZbController() {
		pwdList = cfpb.getPwdById(cfpc.getCfpc_cohf_id());
		remark = cfpc.getCfpc_remark();

		currentime = new SimpleDateFormat("yyyyMM").format(System
				.currentTimeMillis());
	}

	@Command
	public void getChfp_id(@BindingParam("pwd") CoHousingFundPwdModel m) {
		cfpm = m;
		System.out.println(cfpm.getChfp_id());
	}

	@Command
	public void submit(@BindingParam("gd") Grid gd,
			@BindingParam("win") Window win) {
		CoHousingFundPwdChangeModel m = new CoHousingFundPwdChangeModel();
		if (cfpm != null) {
			m.setCfpc_remark(remark);
			m.setCfpc_id(cfpc.getCfpc_id());
			m.setCfpc_chfp_id(cfpm.getChfp_id());
			m.setCfpc_zb_name(cfpm.getChfp_zb_name());
			m.setCfpc_zb_number(cfpm.getChfp_zb_number());
			try {
				String[] message = docOC.UpchkHaveTo(gd);
				if (message[0] == "1") {
					boolean flag = cfpb.UpdatePwdZb(m);
					if (flag) {
						docOC.UpsubmitDoc(gd, String.valueOf(cfpc.getCfpc_id()));
						Messagebox.show("添加成功!", "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Messagebox.show("请选择要变更的密钥专办员");
		}
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCurrentime() {
		return currentime;
	}

	public void setCurrentime(String currentime) {
		this.currentime = currentime;
	}

	public List<CoHousingFundPwdModel> getPwdList() {
		return pwdList;
	}

	public void setPwdList(List<CoHousingFundPwdModel> pwdList) {
		this.pwdList = pwdList;
	}

	public CoHousingFundPwdChangeModel getCfpc() {
		return cfpc;
	}

	public void setCfpc(CoHousingFundPwdChangeModel cfpc) {
		this.cfpc = cfpc;
	}

}
