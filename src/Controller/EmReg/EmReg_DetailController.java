package Controller.EmReg;

import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.EmReg.EmReg_ListBll;

import Model.EmRegistrationInfoModel;

public class EmReg_DetailController {

	// 页面传值
	Integer daid = 0;
	String role = "";// hd 后道 qd 前道

	private EmRegistrationInfoModel erm = new EmRegistrationInfoModel();
	private List<EmRegistrationInfoModel> hosList = new ListModelList<>();

	private Integer puzu_id;

	// 特殊字段
	private String is_parttime;// 是否兼职
	private String if_unlimited;// 无固定劳动期限
	private String if_resident_con;// 是否添加居住证转换

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public EmReg_DetailController() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			role = Executions.getCurrent().getArg().get("role").toString();

			setErm(bll.getEmRegInfo(daid, ""));
			if (role.equals("hd")) {
				setHosList(bll.getStateRelList(
						" and typename='后道状态' and state=1", daid));
			} else if (role.equals("qd")) {
				setHosList(bll.getStateRelList(
						" and typename='前道状态' and state=1", daid));
			}

			if (erm.getErin_hjtype().equals("本市城镇")) {
				puzu_id = erm.getCori_sz_puzu_id() == null ? 12 : erm
						.getCori_sz_puzu_id();
			} else {
				puzu_id = erm.getCori_wd_puzu_id() == null ? 22 : erm
						.getCori_wd_puzu_id();
			}

			fieldinit();

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 特殊字段初始化
	 * 
	 */
	public void fieldinit() {
		try {
			setIs_parttime(erm.getErin_is_parttime() == 1 ? "是" : "否");
			setIf_unlimited(erm.getErin_if_unlimited() == 1 ? "是" : "否");
			setIf_resident_con(erm.getErin_if_resident_con() == 1 ? "是" : "否");
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("初始化数据出错!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public EmRegistrationInfoModel getErm() {
		return erm;
	}

	public void setErm(EmRegistrationInfoModel erm) {
		this.erm = erm;
	}

	public List<EmRegistrationInfoModel> getHosList() {
		return hosList;
	}

	public void setHosList(List<EmRegistrationInfoModel> hosList) {
		this.hosList = hosList;
	}

	public String getIs_parttime() {
		return is_parttime;
	}

	public void setIs_parttime(String is_parttime) {
		this.is_parttime = is_parttime;
	}

	public String getIf_resident_con() {
		return if_resident_con;
	}

	public void setIf_resident_con(String if_resident_con) {
		this.if_resident_con = if_resident_con;
	}

	public String getIf_unlimited() {
		return if_unlimited;
	}

	public void setIf_unlimited(String if_unlimited) {
		this.if_unlimited = if_unlimited;
	}

	public Integer getPuzu_id() {
		return puzu_id;
	}

	public void setPuzu_id(Integer puzu_id) {
		this.puzu_id = puzu_id;
	}
}
