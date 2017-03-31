package Controller.CoSocialInsurance;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoShebaoChangeModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.CoSocialInsurance.CoSocialInsurance_ListBll;
import bll.CoSocialInsurance.CoSocialInsurance_OperateBll;

public class CoSocialInsurance_CancellationOperateController {
	private CoShebaoChangeModel m = new CoShebaoChangeModel();
	Integer daid = 0;
	private Integer state = 0;
	private Media media;
	private String ukey = "";

	public CoSocialInsurance_CancellationOperateController() {
		try {
			CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setM(bll.getCoShebaoChangeInfo(daid));

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
		ukey = m.getCosb_ukey();
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

	// 提交
	@Command
	public void summitbtn(@BindingParam("win") Window win) {
		if (m.getCsbc_sorid() == null || m.getCsbc_sorid().equals("")) {
			Messagebox.show("请输入社保账号!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else if (m.getCsbc_pwd() == null || m.getCsbc_pwd().equals("")) {
			Messagebox.show("请输入社保密码!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else if (m.getCsbc_sorid() == null || m.getCsbc_sorid().equals("")) {
			if (ukey != null && !ukey.equals("")) {
				Messagebox.show("请输入密钥密码!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			m.setCsbc_state(3);
			m.setCsbc_addname(UserInfo.getUsername());
			try {
				String[] str = new CoSocialInsurance_OperateBll().UpdateState(
						m, null);
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
		m.setCsbc_state(3);
		m.setCsbc_addname(UserInfo.getUsername());
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
