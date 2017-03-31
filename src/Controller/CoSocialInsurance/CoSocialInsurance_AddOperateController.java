package Controller.CoSocialInsurance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoSocialInsurance.CoSocialInsurance_ListBll;
import bll.CoSocialInsurance.CoSocialInsurance_OperateBll;
import Model.CoShebaoChangeModel;
import Util.FileOperate;
import Util.UserInfo;

public class CoSocialInsurance_AddOperateController {

	private CoShebaoChangeModel m = new CoShebaoChangeModel();
	Integer daid = 0;
	private Integer state = 0;
	private String filename = "";
	private Media media;
	private double per1 = 0;
	private double per2 = 0;

	private List<String> csbc_sbaddstr; // 执行人

	private Date ukeytruetime, ukeyfailtime;

	public CoSocialInsurance_AddOperateController() {
		try {
			CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setCsbc_sbaddstr(bll.getShebaoArealist());
			setM(bll.getCoShebaoChangeInfo(daid));
			if (m.getCsbc_state() == 5 || m.getCsbc_state() == 6) {
				state = m.getCsbc_laststate();
			} else {
				state = m.getCsbc_state();
			}
			// setPer1(m.getCsbc_unemployment_per() * 100);默认初始值改为：0%
			// setPer2(m.getCsbc_business_per() * 100); 默认初始值改为：0.4%
			setPer2(0.4);// 默认初始值改为：0.4%
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("browse")
	@NotifyChange({ "filename" })
	public void browse(@BindingParam("media") Media media) {
		if (media.getFormat().equals("pdf")) {
			setFilename(media.getName());
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
				if (m.getCosb_ukey() != null
						&& (m.getCosb_ukey().equals("是") || m.getCosb_ukey()
								.equals("有"))) {
					if (ukeytruetime == null) {
						Messagebox.show("请选择UKEY生效时间!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
						break flagA;
					} else if (ukeyfailtime == null) {
						Messagebox.show("请选择UKEY失效时间!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
						break flagA;
					}
				}
				if (filename.isEmpty()) {
					Messagebox.show("请选择上传文件!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					break flagA;
				} else {
					String fileurl = "OfficeFile/DownLoad/CoSocialInsurance/"
							+ filename;
					try {
						FileOperate.upload(media, fileurl);
					} catch (Exception e) {
						Messagebox.show("上传失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
						e.printStackTrace();
						break flagA;
					}
					m.setCsbc_pdf(filename);
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
				String[] str = new String[5];
				if (state == 1) {
					if (ukeytruetime != null && !ukeytruetime.equals("")) {
						m.setCosb_ukeytruetime(datetostr(ukeytruetime));
					}
					if (ukeyfailtime != null && !ukeyfailtime.equals("")) {
						m.setCosb_ukeyfailtime(datetostr(ukeyfailtime));
					}
					str = new CoSocialInsurance_OperateBll().UpdateState(m, gd);
				} else {
					str = new CoSocialInsurance_OperateBll().UpdateState(m, gd);
				}
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
		m.setCsbc_unemployment_per(per1 / 100);
		m.setCsbc_business_per(per2 / 100);
	}

	public List<String> getCsbc_sbaddstr() {
		return csbc_sbaddstr;
	}

	public void setCsbc_sbaddstr(List<String> csbc_sbaddstr) {
		this.csbc_sbaddstr = csbc_sbaddstr;
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public double getPer1() {
		return per1;
	}

	public void setPer1(double per1) {
		this.per1 = per1;
	}

	public double getPer2() {
		return per2;
	}

	public void setPer2(double per2) {
		this.per2 = per2;
	}

	public Date getUkeytruetime() {
		return ukeytruetime;
	}

	public void setUkeytruetime(Date ukeytruetime) {
		this.ukeytruetime = ukeytruetime;
	}

	public Date getUkeyfailtime() {
		return ukeyfailtime;
	}

	public void setUkeyfailtime(Date ukeyfailtime) {
		this.ukeyfailtime = ukeyfailtime;
	}

	private String datetostr(Date date) {
		String str = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (date != null) {
				str = format.format(date);
			}
		} catch (Exception e) {

		}
		return str;
	}
}
