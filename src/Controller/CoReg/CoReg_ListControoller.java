package Controller.CoReg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import Model.CoOnlineRegisterInfoModel;
import bll.CoReg.CoReg_ListBll;

public class CoReg_ListControoller {
	private CoOnlineRegisterInfoModel com = new CoOnlineRegisterInfoModel();
	private List<CoOnlineRegisterInfoModel> hosList = new ListModelList<>();

	// 下拉绑定列表
	private List<String> pubindustryList = new ListModelList<>();

	private boolean wtimeVis = false;
	private boolean wtimeVis1 = false;
	private boolean wtimeVis2 = false;
	private boolean jsVis = true;

	// 特殊字段
	private String if_build = "是";
	private String if_pass = "是";
	private String if_tell = "是";
	private String if_give = "是";
	private String if_build_sign_in = "是";
	private String if_ot = "否";
	private String if_arrear = "是";
	private String if_lowest = "是";
	private String if_ar_ot = "是";
	private String if_join_ss = "否";
	private String if_happen_ld = "否";
	private String if_kid = "否";
	private String if_union = "否";
	private String if_responsebook = "否";
	private String if_sign_responsebook = "否";
	private List<CoOnlineRegisterInfoModel> list=new ArrayList<CoOnlineRegisterInfoModel>();

	// 界面传值
	Integer cid = 0;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	private boolean coreg_ifexist=false;

	public CoReg_ListControoller() {
		try {
			CoReg_ListBll bll = new CoReg_ListBll();
			cid = Integer.valueOf(Executions.getCurrent().getArg().get("cid")
					.toString());
			CoOnlineRegisterInfoModel coregmodel = bll.coregInfo(cid, "");
			if (coregmodel.getCori_id() != null) {
				coreg_ifexist=true;
				setCom(bll.getCoregInfo(coregmodel.getCori_id()));
				setHosList(bll.getStateRelList(" and typename='后道状态'", coregmodel.getCori_id()));

				if_build = com.getCori_if_build() == 1 ? "是" : "否";
				if_pass = com.getCori_if_pass() == 1 ? "是" : "否";
				if_tell = com.getCori_if_tell() == 1 ? "是" : "否";
				if_give = com.getCori_if_give() == 1 ? "是" : "否";
				if_build_sign_in = com.getCori_if_build_sign_in() == 1 ? "是"
						: "否";
				if_ot = com.getCori_if_ot() == 1 ? "是" : "否";
				if_arrear = com.getCori_if_arrear() == 1 ? "是" : "否";
				if_lowest = com.getCori_if_lowest() == 1 ? "是" : "否";
				if_ar_ot = com.getCori_if_ar_ot() == 1 ? "是" : "否";
				if_join_ss = com.getCori_if_join_ss() == 1 ? "是" : "否";
				if_happen_ld = com.getCori_if_happen_ld() == 1 ? "是" : "否";
				if_kid = com.getCori_if_kid() == 1 ? "是" : "否";
				if_union = com.getCori_if_union() == 1 ? "是" : "否";
				if_responsebook = com.getCori_if_responsebook() == 1 ? "是"
						: "否";
				if_sign_responsebook = com.getCori_if_sign_responsebook() == 1 ? "是"
						: "否";

				setPubindustryList(bll.getPubIndustry());
			}

		} catch (Exception e) {
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public CoOnlineRegisterInfoModel getCom() {
		return com;
	}

	public void setCom(CoOnlineRegisterInfoModel com) {
		this.com = com;
	}

	public List<String> getPubindustryList() {
		return pubindustryList;
	}

	public void setPubindustryList(List<String> pubindustryList) {
		this.pubindustryList = pubindustryList;
	}

	public boolean isWtimeVis() {
		return wtimeVis;
	}

	public void setWtimeVis(boolean wtimeVis) {
		this.wtimeVis = wtimeVis;
	}

	public boolean isWtimeVis1() {
		return wtimeVis1;
	}

	public void setWtimeVis1(boolean wtimeVis1) {
		this.wtimeVis1 = wtimeVis1;
	}

	public boolean isWtimeVis2() {
		return wtimeVis2;
	}

	public void setWtimeVis2(boolean wtimeVis2) {
		this.wtimeVis2 = wtimeVis2;
	}

	public boolean isJsVis() {
		return jsVis;
	}

	public void setJsVis(boolean jsVis) {
		this.jsVis = jsVis;
	}

	public String getIf_build() {
		return if_build;
	}

	public void setIf_build(String if_build) {
		this.if_build = if_build;
	}

	public String getIf_pass() {
		return if_pass;
	}

	public void setIf_pass(String if_pass) {
		this.if_pass = if_pass;
	}

	public String getIf_tell() {
		return if_tell;
	}

	public void setIf_tell(String if_tell) {
		this.if_tell = if_tell;
	}

	public String getIf_give() {
		return if_give;
	}

	public void setIf_give(String if_give) {
		this.if_give = if_give;
	}

	public String getIf_build_sign_in() {
		return if_build_sign_in;
	}

	public void setIf_build_sign_in(String if_build_sign_in) {
		this.if_build_sign_in = if_build_sign_in;
	}

	public String getIf_ot() {
		return if_ot;
	}

	public void setIf_ot(String if_ot) {
		this.if_ot = if_ot;
	}

	public String getIf_arrear() {
		return if_arrear;
	}

	public void setIf_arrear(String if_arrear) {
		this.if_arrear = if_arrear;
	}

	public String getIf_lowest() {
		return if_lowest;
	}

	public void setIf_lowest(String if_lowest) {
		this.if_lowest = if_lowest;
	}

	public String getIf_ar_ot() {
		return if_ar_ot;
	}

	public void setIf_ar_ot(String if_ar_ot) {
		this.if_ar_ot = if_ar_ot;
	}

	public String getIf_join_ss() {
		return if_join_ss;
	}

	public void setIf_join_ss(String if_join_ss) {
		this.if_join_ss = if_join_ss;
	}

	public String getIf_happen_ld() {
		return if_happen_ld;
	}

	public void setIf_happen_ld(String if_happen_ld) {
		this.if_happen_ld = if_happen_ld;
	}

	public String getIf_kid() {
		return if_kid;
	}

	public void setIf_kid(String if_kid) {
		this.if_kid = if_kid;
	}

	public String getIf_union() {
		return if_union;
	}

	public void setIf_union(String if_union) {
		this.if_union = if_union;
	}

	public String getIf_responsebook() {
		return if_responsebook;
	}

	public void setIf_responsebook(String if_responsebook) {
		this.if_responsebook = if_responsebook;
	}

	public String getIf_sign_responsebook() {
		return if_sign_responsebook;
	}

	public void setIf_sign_responsebook(String if_sign_responsebook) {
		this.if_sign_responsebook = if_sign_responsebook;
	}

	public List<CoOnlineRegisterInfoModel> getHosList() {
		return hosList;
	}

	public void setHosList(List<CoOnlineRegisterInfoModel> hosList) {
		this.hosList = hosList;
	}

	public boolean isCoreg_ifexist() {
		return coreg_ifexist;
	}

	public void setCoreg_ifexist(boolean coreg_ifexist) {
		this.coreg_ifexist = coreg_ifexist;
	}

	public List<CoOnlineRegisterInfoModel> getList() {
		return list;
	}

	public void setList(List<CoOnlineRegisterInfoModel> list) {
		this.list = list;
	}
	
}
