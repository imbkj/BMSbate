package Controller.CoHousingFund;

import java.text.SimpleDateFormat;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoHousingFundPwdChangeModel;
import Util.SingleBllFactory;
import bll.CoHousingFund.CoHousingFund_PwdBll;

public class CoHouse_UpdateYearLimitController {

	private String currentime;
	private CoHousingFund_PwdBll cfpb = SingleBllFactory.getInstance()
			.getCfpb();
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	private CoHousingFundPwdChangeModel cfpc = (CoHousingFundPwdChangeModel) Executions
			.getCurrent().getArg().get("cfpc");
	private String remark;
	private int limit;

	public CoHouse_UpdateYearLimitController() {

		remark = cfpc.getCfpc_remark();
		currentime = new SimpleDateFormat("yyyyMM").format(System
				.currentTimeMillis());
	}

	@Command
	public void yearLimit(@BindingParam("Limit") int limit) {
		this.limit = limit;
	}

	@Command
	public void submit(@BindingParam("gd1") Grid gd1,
			@BindingParam("gd") Grid gd, @BindingParam("win") Window win) {
		CoHousingFundPwdChangeModel m = new CoHousingFundPwdChangeModel();
		m.setCfpc_remark(remark);
		m.setCfpc_yearlimit(limit);
		m.setCfpc_id(cfpc.getCfpc_id());
		if (cfpc.getCfpc_addtype().equals("申请数字证书")) {
			try {
				String[] message = docOC.UpchkHaveTo(gd);
				if (message[0] == "1") {
					boolean flag = cfpb.UpdateLimit(m);
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
		} else if (cfpc.getCfpc_addtype().equals("数字证书续期")) {
			try {
				String[] message = docOC.UpchkHaveTo(gd);
				if (message[0] == "1") {
					boolean flag = cfpb.UpdateLimit1(m);
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
		}

	}

	public String getCurrentime() {
		return currentime;
	}

	public void setCurrentime(String currentime) {
		this.currentime = currentime;
	}

	public CoHousingFundPwdChangeModel getCfpc() {
		return cfpc;
	}

	public void setCfpc(CoHousingFundPwdChangeModel cfpc) {
		this.cfpc = cfpc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
