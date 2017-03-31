package Controller.CoBase;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.EmHouse.EmHouseUpdateDal;

import Model.Allinone_ListModel;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoOfferModel;
import Model.EmHouseUpdateModel;
import Model.EmShebaoUpdateModel;
import Model.wtoutFeeModel;
import Util.FileOperate;
import Util.RegexUtil;
import bll.CoBase.CoBase_AllinoneBll;
import bll.CoBase.CoBase_SelectBll;
import bll.EmCommissionOut.Standard.wtoutFeeBll;

public class Cobase_wtfeelistCintroller {
	private int cid = Integer.valueOf(Executions.getCurrent().getArg().get("cid")
			.toString());
	
	private String shortname = "";
	private String name = "";
	private String statename="";
	private Date addtime;
	private String wtfee="";
	private String wtcity="";
	private String bzname="";
 
	
 

	public String getBzname() {
		return bzname;
	}

	public void setBzname(String bzname) {
		this.bzname = bzname;
	}


	private List<wtoutFeeModel> stList;
	private List<wtoutFeeModel> sstList = new ListModelList<>();
	private wtoutFeeBll bll =new wtoutFeeBll();
	private List<String> stateList = new ListModelList<>();

	
	public Cobase_wtfeelistCintroller()
	{
		

		setStList(bll.getmodellist(" and  a.cid="+cid+" "));
		stateList.add("");
		stateList.add("未审核");
		stateList.add("部门经理已审");
		stateList.add("全国项目部已审");
		stateList.add("已生效");
		search();
	}
	
	@Command("search")
	@NotifyChange({ "sstList", "coabList" })
	public void search() {
		
		stList.clear();
		setStList(bll.getmodellist(" and  a.cid="+cid+" "));
		sstList.clear();

		for (wtoutFeeModel m : stList) {
		
			if (!shortname.isEmpty()) {
				if (!RegexUtil.isExists(shortname, m.getCoba_shortname())) {
					continue;
				}
			}
			if (!name.isEmpty()) {
				if (!RegexUtil.isExists(name, m.getWtot_feetitle())) {
					continue;
				}
			}
			
			if (!bzname.isEmpty()) {
				if (!RegexUtil.isExists(name, m.getWtss_title())) {
					continue;
				}
			}
			
			if (!statename.isEmpty()) {
				if (!RegexUtil.isExists(statename, m.getWtot_statestr())) {
					continue;
				}
			}
			if (!wtcity.isEmpty()) {
				if (!RegexUtil.isExists(wtcity, m.getWtss_city())) {
					continue;
				}
			}
			if (!wtfee.isEmpty()) {
				if (!RegexUtil.isExists(wtfee, m.getWtot_fee().toString())) {
					continue;
				}
			}
			
	
			sstList.add(m);
		}
		
	}
	
	
	@Command("add")
	@NotifyChange({ "sstList", "coabList" })
	public void add() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		Window window = (Window) Executions.createComponents("../EmCommissionOut/Standard/Wtfee_add.zul", null, map);
		window.doModal();
		
		search();
	}
	
	@Command("openwin")
	@NotifyChange("sstList")
	public void openwin(@BindingParam("daid") Integer daid,@BindingParam("cid") Integer cid,
			@BindingParam("url") String url) {
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", daid);
		map.put("cid", cid);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();


	}
	
	
	
	@Command("stopwtfee")
	@NotifyChange("sstList")
	public void stopwtfee(@BindingParam("daid") Integer daid,@BindingParam("cid") Integer cid ) {
		String[] str = new String[2];
		str=bll.Wtfeestop(bll.getmodellist(" and  a.Wtot_feeid="+daid +"").get(0));
		
		
	 
			
		if (str[0].equals("1") )
		{
			Messagebox.show(str[1], "操作提示", Messagebox.OK,
					Messagebox.NONE);
		}
		else 
		{
			Messagebox.show(str[1], "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}

		search();
	}
	
	

	

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getWtfee() {
		return wtfee;
	}

	public void setWtfee(String wtfee) {
		this.wtfee = wtfee;
	}

	public String getWtcity() {
		return wtcity;
	}

	public void setWtcity(String wtcity) {
		this.wtcity = wtcity;
	}

	

	public String getShortname() {
		return shortname;
	}


	public void setShortname(String shortname) {
		this.shortname = shortname;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getStatename() {
		return statename;
	}


	public void setStatename(String statename) {
		this.statename = statename;
	}


	public List<wtoutFeeModel> getStList() {
		return stList;
	}


	public void setStList(List<wtoutFeeModel> stList) {
		this.stList = stList;
	}


	public List<wtoutFeeModel> getSstList() {
		return sstList;
	}


	public void setSstList(List<wtoutFeeModel> sstList) {
		this.sstList = sstList;
	}


	public List<String> getStateList() {
		return stateList;
	}


	public void setStateList(List<String> stateList) {
		this.stateList = stateList;
	}

	
	
}
