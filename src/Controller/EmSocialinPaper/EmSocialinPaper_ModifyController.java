package Controller.EmSocialinPaper;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmSocialinPaper.EmSocialinPaperListBll;
import bll.EmSocialinPaper.EmSocialinPaperOperateBll;
import Model.EmSocialinPaperModel;

public class EmSocialinPaper_ModifyController {

	private int espa_id = 0;
	private String title = "";
	private EmSocialinPaperModel epm = new EmSocialinPaperModel();
	private List<EmSocialinPaperModel> hosList;
	private boolean butVis = true;

	public EmSocialinPaper_ModifyController() {
		try {
			EmSocialinPaperListBll bll = new EmSocialinPaperListBll();
			espa_id = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			setEpm(bll.getEmSocailinPaperInfo(espa_id));
			setHosList(new ListModelList<>(bll.getStateRelList(espa_id)));
			setTitle("编辑 - " + epm.getName());
			setButVis(epm.getEspa_state() == 9 ? false : true);
		} catch (Exception e) {
			Messagebox.show("页面加载出错,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			System.out.println(e.toString());
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		try {
			EmSocialinPaperOperateBll bal = new EmSocialinPaperOperateBll();
			epm.setEspa_id(espa_id);
			String[] str = bal.mod(epm, gd);

			if (str[0].equals("1")) {
				Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("提交出错,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			System.out.println(e.toString());
		}
	}

	public int getEspa_id() {
		return espa_id;
	}

	public void setEspa_id(int espa_id) {
		this.espa_id = espa_id;
	}

	public EmSocialinPaperModel getEpm() {
		return epm;
	}

	public void setEpm(EmSocialinPaperModel epm) {
		this.epm = epm;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<EmSocialinPaperModel> getHosList() {
		return hosList;
	}

	public void setHosList(List<EmSocialinPaperModel> hosList) {
		this.hosList = hosList;
	}

	public boolean isButVis() {
		return butVis;
	}

	public void setButVis(boolean butVis) {
		this.butVis = butVis;
	}

}
