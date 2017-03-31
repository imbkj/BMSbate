package Controller.CoSocialInsurance;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.CoSocialInsurance.CoSocialInsurance_ListBll;

import Model.CoShebaoChangeModel;
import Model.CoShebaoInforChangeModel;
import Model.PubStateRelModel;
import Util.FileOperate;

public class CoSocialInsurance_InforChangeDetailController {

	private CoShebaoChangeModel m = new CoShebaoChangeModel();
	private List<CoShebaoInforChangeModel> list = new ListModelList<>();
	private List<PubStateRelModel> hosList = new ListModelList<>();
	Integer daid = 0;

	public CoSocialInsurance_InforChangeDetailController() {
		try {
			CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setM(bll.getCoShebaoChangeInfo(daid));
			setList(bll.getInforChangeList(daid, ""));
			setHosList(bll.getHosList(daid, " and pbsr_type='csoichange'"));

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

	public List<CoShebaoInforChangeModel> getList() {
		return list;
	}

	public void setList(List<CoShebaoInforChangeModel> list) {
		this.list = list;
	}

	public List<PubStateRelModel> getHosList() {
		return hosList;
	}

	public void setHosList(List<PubStateRelModel> hosList) {
		this.hosList = hosList;
	}
}
