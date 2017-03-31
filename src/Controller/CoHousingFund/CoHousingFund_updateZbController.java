package Controller.CoHousingFund;

import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoHousingFund.CoHousingFundZbBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoHousingFundZbChangeModel;
import Model.CoHousingFundZbModel;
import Util.SingleBllFactory;

/**
 * 
 * @author Administrator
 * 
 */
public class CoHousingFund_updateZbController {

	private List<CoHousingFundZbModel> zbListBycohf_id;
	private CoHousingFundZbBll cfzb = SingleBllFactory.getInstance().getChzb();
	private CoHousingFundZbChangeModel cfzc = (CoHousingFundZbChangeModel) Executions
			.getCurrent().getArg().get("cfzc");
	private String remark;
	private CoHousingFundZbModel cfzm;
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();

	public CoHousingFund_updateZbController() {
		zbListBycohf_id = cfzb.getZbListBycohf_id(cfzc.getCfzc_cohf_id());
		remark = cfzc.getCfzc_remark();
	}

	@Command
	public void getZbid(@BindingParam("zb") CoHousingFundZbModel m) {
		cfzm = m;
	}

	@Command
	public void submit(@BindingParam("gd") Grid gd,
			@BindingParam("win") Window win) {
		try {
			String[] message = docOC.UpchkHaveTo(gd);
			if (message[0] == "1") {
				boolean flag = cfzb.UpdateZb(cfzm.getChfz_id(),
						cfzc.getCfzc_id());
				if (flag) {
					docOC.UpsubmitDoc(gd, String.valueOf(cfzc.getCfzc_id()));
					Messagebox.show("添加成功!", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<CoHousingFundZbModel> getZbListBycohf_id() {
		return zbListBycohf_id;
	}

	public void setZbListBycohf_id(List<CoHousingFundZbModel> zbListBycohf_id) {
		this.zbListBycohf_id = zbListBycohf_id;
	}

	public CoHousingFundZbChangeModel getCfzc() {
		return cfzc;
	}

	public void setCfzc(CoHousingFundZbChangeModel cfzc) {
		this.cfzc = cfzc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
