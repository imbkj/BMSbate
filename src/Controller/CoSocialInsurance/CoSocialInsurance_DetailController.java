package Controller.CoSocialInsurance;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoSocialInsurance.CoSocialInsurance_ListBll;
import bll.CoSocialInsurance.CoSocialInsurance_OperateBll;

import Model.CoShebaoModel;
import Model.PubStateRelModel;
import Model.SocialInsuranceClassInfoViewModel;
import Util.DateStringChange;
import Util.DateUtil;
import Util.FileOperate;
import Util.SocialInsuranceCalculator;

public class CoSocialInsurance_DetailController {

	private CoShebaoModel m = new CoShebaoModel();
	Integer daid = 0;
	private String role = "";
	private Double per1;
	private Double per2;
	private List<PubStateRelModel> perhosList;
	private DecimalFormat df = new DecimalFormat(".##");
	private Date ukeytruetime, ukeyfailtime;

	public CoSocialInsurance_DetailController() {
		try {
			CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			setRole(Executions.getCurrent().getArg().get("role").toString());

			setM(bll.getCoShebaoInfo(daid));
			if (m != null && m.getCosb_unemployment_per() != null) {
				setPer1(Double
						.valueOf(df.format(m.getCosb_unemployment_per() * 100)));
			}
			if (m != null && m.getCosb_business_per() != null) {
				setPer2(Double
						.valueOf(df.format(m.getCosb_business_per() * 100)));
			}

			if (m != null && m.getCosb_ukeytruetime() != null) {
				setUkeytruetime(DateStringChange.StringtoDate(
						m.getCosb_ukeytruetime(), "yyyy-MM-dd"));
			}
			
			if (m != null && m.getCosb_ukeyfailtime() != null) {
				setUkeyfailtime(DateStringChange.StringtoDate(
						m.getCosb_ukeyfailtime(), "yyyy-MM-dd"));
			}

			setPerhosList(bll.getHosList(daid, " and pbsr_type='csoi'"));
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

	// 计算失业比例
	public Double getSBSyeCP(Double syecp) {
		Double syePer;

		// 设置执行时间
		Date date = DateStringChange.StringtoDate(m.getOwnmonth() + "01",
				"yyyyMMdd");

		SocialInsuranceCalculator si = new SocialInsuranceCalculator();
		int soin_id = si.getSionId("深户员工", "深圳", "深圳中智经济技术合作有限公司");

		// 获取社保字典库算法map
		Map<String, SocialInsuranceClassInfoViewModel> map = si.getSiAlInfo(
				soin_id, date);

		// 最新失业保险比例
		String sye = ((SocialInsuranceClassInfoViewModel) map.get("失业保险企业"))
				.getSiai_proportion();

		try {
			// 最终失业比例=失业比例 * (1 - 失业下浮比例) 注意：si.dealProprotion()是百分比处理
			syePer = (si.dealProprotion(sye).multiply(
					new BigDecimal(1).subtract(si.dealProprotion(String
							.valueOf(syecp) + "%"))).multiply(new BigDecimal(
					100))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception e) {
			syePer = null;
		}

		return syePer;
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = "";
		if (date != null) {
			str = format.format(date);
		}
		return str;
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		String remark = "";
		String content = "调整";
		per1 = per1 == null ? 0 : per1;
		per2 = per2 == null ? 0 : per2;

		if (ukeytruetime != null) {
			m.setCosb_ukeytruetime(DateToStr(ukeytruetime));
		}
		if (ukeyfailtime != null) {
			m.setCosb_ukeyfailtime(DateToStr(ukeyfailtime));
		}

		if (ukeytruetime != null && ukeyfailtime != null
				&& DateUtil.datediff(ukeytruetime, ukeyfailtime, "d") < 0) {
			Messagebox.show("Ukey有效日期录入有误.", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (per1 / 100 > m.getCosb_unemployment_per()) {
			remark += "失业比例 " + (df.format(m.getCosb_unemployment_per() * 100))
					+ "%上调为 " + (per1) + "%;";
			content += "失业比例、";
		} else if (per1 / 100 < m.getCosb_unemployment_per()) {
			remark += "失业比例 " + (df.format(m.getCosb_unemployment_per() * 100))
					+ "%下调为 " + (per1) + "%;";
			content += "失业比例、";
		}

		if (per2 / 100 > m.getCosb_business_per()) {
			remark += "工伤比例 " + df.format(m.getCosb_business_per() * 100)
					+ "%上调为 " + per2 + "%;";
			content += "工伤比例、";
		} else if (per2 / 100 < m.getCosb_business_per()) {
			remark += "工伤比例 " + df.format(m.getCosb_business_per() * 100)
					+ "%下调为 " + per2 + "%;";
			content += "工伤比例、";
		}
		try {
			//if (!remark.isEmpty()) {
				m.setStatename(content.substring(0, content.length() - 1));
				m.setCosb_remark(remark);
				m.setCosb_unemployment_per(per1 / 100);
				m.setCosb_business_per(per2 / 100);
				CoSocialInsurance_OperateBll bll = new CoSocialInsurance_OperateBll();
				if (bll.UpdatePercent(m)) {
					Messagebox.show("保存成功!", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("保存失败!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			//}
		} catch (Exception e) {
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public CoShebaoModel getM() {
		return m;
	}

	public void setM(CoShebaoModel m) {
		this.m = m;
	}

	public Double getPer1() {
		return per1;
	}

	public void setPer1(Double per1) {
		this.per1 = per1;
	}

	public Double getPer2() {
		return per2;
	}

	public void setPer2(Double per2) {
		this.per2 = per2;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<PubStateRelModel> getPerhosList() {
		return perhosList;
	}

	public void setPerhosList(List<PubStateRelModel> perhosList) {
		this.perhosList = perhosList;
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

}
