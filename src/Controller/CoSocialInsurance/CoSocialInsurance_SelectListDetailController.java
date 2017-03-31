package Controller.CoSocialInsurance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoSocialInsurance.CoSocialInsurance_ListBll;

import Model.CoShebaoChangeModel;
import Util.FileOperate;

public class CoSocialInsurance_SelectListDetailController {

	private List<CoShebaoChangeModel> csbcList;
	Integer cid = 0;
	String role = "";

	public CoSocialInsurance_SelectListDetailController() {
		try {
			CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

			cid = Integer.parseInt(Executions.getCurrent().getArg().get("cid")
					.toString());
			role = Executions.getCurrent().getArg().get("role").toString();

			setCsbcList(bll.getCoShebaoChangeList(" and a.cid=" + cid));
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("download")
	public void download(@BindingParam("filename") String filename) {
		try {
			String fileurl = "OfficeFile/DownLoad/CoSocialInsurance/"
					+ filename;
			FileOperate.download(fileurl);
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("下载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 申报详情
	 * 
	 * @param m
	 */
	@Command("detail")
	public void detail(@BindingParam("each") CoShebaoChangeModel m) {
		String url = "";
		if (m.getCsbc_addtype().equals("缴存登记")
				|| m.getCsbc_addtype().equals("账户接管")) {
			url = "/CoSocialInsurance/CoSocialInsurance_AddDetail.zul";
		} else if (m.getCsbc_addtype().equals("信息变更")) {
			url = "/CoSocialInsurance/CoSocialInsurance_InforChangeDetail.zul";
		} else if (m.getCsbc_addtype().equals("账户注销")) {
			url = "/CoSocialInsurance/CoSocialInsurance_CancellationDetail.zul";
		}
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("daid", m.getCsbc_id());
		map.put("role", role);

		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	public List<CoShebaoChangeModel> getCsbcList() {
		return csbcList;
	}

	public void setCsbcList(List<CoShebaoChangeModel> csbcList) {
		this.csbcList = csbcList;
	}
}
