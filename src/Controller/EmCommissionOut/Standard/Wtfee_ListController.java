package Controller.EmCommissionOut.Standard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.EmCommissionOut.Standard.wtoutFeeDal;

import Model.WtServiceStandardrelationModel;
import bll.EmCommissionOut.Standard.WtServiceStandardBll;
import Model.wtoutFeeModel;
import Util.RegexUtil;
import Util.UserInfo;
import bll.EmCommissionOut.Standard.wtoutFeeBll;

public class Wtfee_ListController {
	
	private String cid = "";
	private String shortname = "";
	private String name = "";
	private String statename="";
	private Date addtime;
	private String wtfee="";
	private String wtcity="";
	private String coab_name="";
	private String addname="";
	
	
	private List<wtoutFeeModel> stList;
	private List<wtoutFeeModel> stListqg;
	private List<wtoutFeeModel> sstList = new ListModelList<>();
	private List<wtoutFeeModel> sstListqg = new ListModelList<>();
	
	private wtoutFeeBll bll =new wtoutFeeBll();
	private List<String> stateList = new ListModelList<>();

	
	public Wtfee_ListController()
	{
		setStList(bll.getmodellist(""));
		setStListqg(bll.getmodellist(" and wtot_examinenameqg is not null"));
		
		stateList.add("");
		stateList.add("未审核");
		stateList.add("部门经理已审");
		stateList.add("全国项目部已审");
		stateList.add("已生效");
		search();
	}
	
	@Command("search")
	@NotifyChange({ "sstList", "sstListqg","coabList" })
	public void search() {
		
		sstList.clear();
		sstListqg.clear();
		
		for (wtoutFeeModel m : stList) {
			if (!cid.isEmpty()) {
				if (!RegexUtil.isExists(cid, m.getCid() + "")) {
					continue;
				}
			}
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
			if (!addname.isEmpty()) {
				if (!RegexUtil.isExists(addname, m.getWtot_addname())) {
					continue ;  
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
		
		
		
		for (wtoutFeeModel mqg : stListqg) {
			if (!cid.isEmpty()) {
				if (!RegexUtil.isExists(cid, mqg.getCid() + "")) {
					continue;
				}
			}
			if (!shortname.isEmpty()) {
				if (!RegexUtil.isExists(shortname, mqg.getCoba_shortname())) {
					continue;
				}
			}
			if (!name.isEmpty()) {
				if (!RegexUtil.isExists(name, mqg.getWtot_feetitle())) {
					continue;
				}
			}
			if (!statename.isEmpty()) {
				if (!RegexUtil.isExists(statename, mqg.getWtot_statestr())) {
					continue;
				}
			}
			if (!wtcity.isEmpty()) {
				if (!RegexUtil.isExists(wtcity, mqg.getWtss_city())) {
					continue;
				}
			}
			if (!wtfee.isEmpty()) {
				if (!RegexUtil.isExists(wtfee, mqg.getWtot_fee().toString())) {
					continue;
				}
			}
			
			if (!addname.isEmpty()) {
				if (!RegexUtil.isExists(addname, mqg.getWtot_addname())) {
					continue ;  
			}
			}
			sstListqg.add(mqg);
		}
		
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


	}
	

	

	public String getCoab_name() {
		return coab_name;
	}

	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

	public List<wtoutFeeModel> getStListqg() {
		return stListqg;
	}

	public void setStListqg(List<wtoutFeeModel> stListqg) {
		this.stListqg = stListqg;
	}

	public List<wtoutFeeModel> getSstListqg() {
		return sstListqg;
	}

	public void setSstListqg(List<wtoutFeeModel> sstListqg) {
		this.sstListqg = sstListqg;
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

	public String getCid() {
		return cid;
	}


	public void setCid(String cid) {
		this.cid = cid;
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

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	
	
}
