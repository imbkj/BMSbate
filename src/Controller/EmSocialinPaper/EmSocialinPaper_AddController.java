package Controller.EmSocialinPaper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmSocialinPaperModel;
import Model.EmbaseModel;
import Util.UserInfo;
import bll.EmSocialinPaper.EmSocialinPaperListBll;
import bll.EmSocialinPaper.EmSocialinPaperOperateBll;

public class EmSocialinPaper_AddController {

	private EmbaseModel em = new EmbaseModel();
	private EmSocialinPaperModel epm = new EmSocialinPaperModel();
	private List<EmSocialinPaperModel> typeList;
	private List<EmSocialinPaperModel> feetype;
	private int emba_id;
	private String title = "";
	private String userid = "";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	private Date filetime = new Date();

	public EmSocialinPaper_AddController() {
		try {
			EmSocialinPaperListBll bll = new EmSocialinPaperListBll();
			emba_id = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			setEm(bll.getEmbaseInfo(emba_id));
			setFeetype(new ListModelList<EmSocialinPaperModel>(
					bll.getEmSocialinPaperState(" and typename='付款状态'")));
			setTypeList(new ListModelList<EmSocialinPaperModel>(
					bll.getEmSocialinPaperState(" and typename='办理状态'")));

			epm.setCid(em.getCid());
			epm.setGid(em.getGid());
			epm.setName(em.getEmba_name());
			epm.setEspa_idcard(em.getEmba_idcard());
			epm.setEspa_feestate("未付");
			epm.setEspa_addname(UserInfo.getUsername());
			epm.setOwnmonth(sdf.format(new Date()));
			if (bll.isExists(" and espa_state=9", em.getGid())) {
				epm.setEspa_type("补办");
				setTitle("补办劳动保障卡 - " + em.getEmba_name());
			} else {
				epm.setEspa_type("新办");
				setTitle("新办劳动保障卡 - " + em.getEmba_name());
			}

			setUserid(UserInfo.getUserid());
		} catch (Exception e) {
			Messagebox.show("页面加载出错,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			System.out.println(e.toString());
		}
	}

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		if (epm.getEspa_feetype() == null || epm.getEspa_feetype().isEmpty()) {
			Messagebox.show("请选择付款方式!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			try {
				EmSocialinPaperOperateBll bll = new EmSocialinPaperOperateBll();
				epm.setEspa_filetime(sdf1.format(filetime));
				String[] str = bll.add(epm, gd);

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
	}

	public EmbaseModel getEm() {
		return em;
	}

	public void setEm(EmbaseModel em) {
		this.em = em;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public EmSocialinPaperModel getEpm() {
		return epm;
	}

	public void setEpm(EmSocialinPaperModel epm) {
		this.epm = epm;
	}

	public List<EmSocialinPaperModel> getFeetype() {
		return feetype;
	}

	public void setFeetype(List<EmSocialinPaperModel> feetype) {
		this.feetype = feetype;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getFiletime() {
		return filetime;
	}

	public void setFiletime(Date filetime) {
		this.filetime = filetime;
	}

	public List<EmSocialinPaperModel> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<EmSocialinPaperModel> typeList) {
		this.typeList = typeList;
	}
}
