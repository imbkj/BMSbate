package Controller.CoSocialInsurance;

import java.text.DecimalFormat;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.CoSocialInsurance.CoSocialInsurance_ListBll;
import Model.CoShebaoChangeModel;
import Model.PubStateRelModel;
import Util.FileOperate;

public class CoSocialInsurance_AddDetailController {

	private CoShebaoChangeModel m = new CoShebaoChangeModel();
	private List<PubStateRelModel> hosList = new ListModelList<>();
	Integer daid = 0;
	private String per1 = "";
	private String per2 = "";
	private DecimalFormat df = new DecimalFormat(".##");

	public CoSocialInsurance_AddDetailController() {
		try {
			CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setM(bll.getCoShebaoChangeInfo(daid));
			String str = "";
			if (m.getCsbc_addtype().equals("缴存登记")) {
				str = " and pbsr_type='csoiadd'";
			} else if (m.getCsbc_addtype().equals("账户接管")) {
				str = " and pbsr_type='csoiadd1'";
			}
			setHosList(bll.getHosList(daid, str));

			setPer1(df.format(m.getCsbc_unemployment_per() * 100) + "%");
			setPer2(df.format(m.getCsbc_business_per() * 100) + "%");
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

	public CoShebaoChangeModel getM() {
		return m;
	}

	public void setM(CoShebaoChangeModel m) {
		this.m = m;
	}

	public List<PubStateRelModel> getHosList() {
		return hosList;
	}

	public void setHosList(List<PubStateRelModel> hosList) {
		this.hosList = hosList;
	}

	public String getPer1() {
		return per1;
	}

	public void setPer1(String per1) {
		this.per1 = per1;
	}

	public String getPer2() {
		return per2;
	}

	public void setPer2(String per2) {
		this.per2 = per2;
	}
}
