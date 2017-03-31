package Controller.CoSocialInsurance;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoSocialInsurance.CoSocialInsurance_ListBll;
import bll.CoSocialInsurance.CoSocialInsurance_OperateBll;

import Model.CoShebaoChangeModel;
import Model.CoShebaoInforChangeModel;
import Util.FileOperate;
import Util.UserInfo;

public class CoSocialInsurance_InfoChangeOperateController {

	private CoShebaoChangeModel m = new CoShebaoChangeModel();
	private List<CoShebaoInforChangeModel> list = new ListModelList<>();
	Integer daid = 0;
	private Integer state = 0;
	private Media media;

	public CoSocialInsurance_InfoChangeOperateController() {
		try {
			CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setM(bll.getCoShebaoChangeInfo(daid));
			setList(bll.getInforChangeList(daid, ""));

			if (m.getCsbc_state() == 5 || m.getCsbc_state() == 6) {
				setState(m.getCsbc_laststate());
			} else {
				setState(m.getCsbc_state());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("browse")
	@NotifyChange({ "m" })
	public void browse(@BindingParam("media") Media media) {
		if (media.getFormat().equals("pdf")) {
			m.setCsbc_pdf(media.getName());
			setMedia(media);
		} else {
			Messagebox.show("此文件不是pdf类型,請檢查!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 * @param gd
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		flagA: {
			if (state == 1) {
				if (m.getCsbc_pdf() == null || m.getCsbc_pdf().isEmpty()) {
					Messagebox.show("请选择上传文件!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					break flagA;
				} else {
					String fileurl = "OfficeFile/DownLoad/CoSocialInsurance/"
							+ m.getCsbc_pdf();
					try {
						FileOperate.upload(media, fileurl);
					} catch (Exception e) {
						Messagebox.show("上传失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
						e.printStackTrace();
						break flagA;
					}
				}
			}
			try {
				fieldhandle();
			} catch (Exception e) {
				Messagebox.show("数据处理出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				e.printStackTrace();
				break flagA;
			}

			try {
				String[] str = new CoSocialInsurance_OperateBll().UpdateState(
						m, gd);
				if (str[0].equals("1")) {
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 数据处理
	 * 
	 */
	public void fieldhandle() {
		m.setCsbc_state(state + 1);
		m.setCsbc_addname(UserInfo.getUsername());
	}

	public List<CoShebaoInforChangeModel> getList() {
		return list;
	}

	public void setList(List<CoShebaoInforChangeModel> list) {
		this.list = list;
	}

	public CoShebaoChangeModel getM() {
		return m;
	}

	public void setM(CoShebaoChangeModel m) {
		this.m = m;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}
}
