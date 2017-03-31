package Controller.EmReg;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import Model.EmRegistrationInfoModel;
import bll.EmReg.EmReg_ListBll;

public class EmReg_ListController {
	private String gid = Executions.getCurrent().getArg().get("gid").toString();
	private EmReg_ListBll bll = new EmReg_ListBll();
	private List<EmRegistrationInfoModel> stoplist = bll
			.getEmRegList(" and a.gid=" + gid);
	private String wherestr = " and type=1 and typename='前道状态'";
	private List<EmRegistrationInfoModel> list = bll.getEmRegList(1, wherestr
			+ " and a.gid=" + gid);
	private EmRegistrationInfoModel regmodel = new EmRegistrationInfoModel();
	// 特殊字段
	private String is_parttime;// 是否兼职
	private String if_unlimited;// 无固定劳动期限
	private String if_resident_con;// 是否添加居住证转换
	private List<EmRegistrationInfoModel> hosList = new ListModelList<>();
	private Integer puzu_id;
	private boolean coreg_ifexist=false;
	private List<EmRegistrationInfoModel> emreglist=new ArrayList<EmRegistrationInfoModel>();
	
	public EmReg_ListController() {
		if (list.size() <= 0) {
			list = bll.getEmRegList(1,
					" and type=1 and typename='后道状态' and a.gid=" + gid);
		}
		if (list.size() > 0) {
			regmodel = list.get(0);
			coreg_ifexist=true;
			setHosList(bll.getStateRelList(
					" and typename='后道状态' and state=1", regmodel.getErin_id()));
			puzu_id = regmodel.getCori_sz_puzu_id() == null ? 12 : regmodel
					.getCori_sz_puzu_id();
		}
	}

	/**
	 * 特殊字段初始化
	 * 
	 */
	public void fieldinit() {
		try {
			if (regmodel.getErin_is_parttime() != null) {
				setIs_parttime(regmodel.getErin_is_parttime() == 1 ? "是" : "否");
			}
			if (regmodel.getErin_if_unlimited() != null) {
				setIf_unlimited(regmodel.getErin_if_unlimited() == 1 ? "是"
						: "否");
			}
			if (regmodel.getErin_if_resident_con() != null) {
				setIf_resident_con(regmodel.getErin_if_resident_con() == 1 ? "是"
						: "否");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("初始化数据出错!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public List<EmRegistrationInfoModel> getStoplist() {
		return stoplist;
	}

	public void setStoplist(List<EmRegistrationInfoModel> stoplist) {
		this.stoplist = stoplist;
	}

	public List<EmRegistrationInfoModel> getList() {
		return list;
	}

	public void setList(List<EmRegistrationInfoModel> list) {
		this.list = list;
	}

	public EmRegistrationInfoModel getRegmodel() {
		return regmodel;
	}

	public void setRegmodel(EmRegistrationInfoModel regmodel) {
		this.regmodel = regmodel;
	}

	public String getIs_parttime() {
		return is_parttime;
	}

	public void setIs_parttime(String is_parttime) {
		this.is_parttime = is_parttime;
	}

	public String getIf_unlimited() {
		return if_unlimited;
	}

	public void setIf_unlimited(String if_unlimited) {
		this.if_unlimited = if_unlimited;
	}

	public String getIf_resident_con() {
		return if_resident_con;
	}

	public void setIf_resident_con(String if_resident_con) {
		this.if_resident_con = if_resident_con;
	}

	public List<EmRegistrationInfoModel> getHosList() {
		return hosList;
	}

	public void setHosList(List<EmRegistrationInfoModel> hosList) {
		this.hosList = hosList;
	}

	public Integer getPuzu_id() {
		return puzu_id;
	}

	public void setPuzu_id(Integer puzu_id) {
		this.puzu_id = puzu_id;
	}

	public boolean isCoreg_ifexist() {
		return coreg_ifexist;
	}

	public void setCoreg_ifexist(boolean coreg_ifexist) {
		this.coreg_ifexist = coreg_ifexist;
	}

	public List<EmRegistrationInfoModel> getEmreglist() {
		return emreglist;
	}

	public void setEmreglist(List<EmRegistrationInfoModel> emreglist) {
		this.emreglist = emreglist;
	}
	
}
