package Controller.CoCompact.CoCompactSA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoCompactSAModel;
import bll.CoCompact.CoCompactSA.CoCompactSA_SelectBll;

public class CompactSA_InfoSearchController {
	private List ccsaBaseList; // 合同基本信息列表
	private List ccsaAddnameList; // 添加人列表
	private CoCompactSA_SelectBll ccsaBll = new CoCompactSA_SelectBll();
	
	// 查询条件
	private String coba_company;  //公司名称
	private String compactid;    //合同编号
	private String ccsa_addname; //添加人
	private String ccsa_state=""; //协议状态
	
	public CompactSA_InfoSearchController() {
		// 首页加载显示补充协议信息
		ccsaBaseList = ccsaBll.getCCSABaseAll();
		// 查询中添加人下拉框
		ccsaAddnameList = ccsaBll.getCCSAAddname("");
	}
	
	//弹出合同补充协议的详细页面
	@Command("detial")
	@NotifyChange("ccsaBaseList")
	public void detial(@BindingParam("ccsaM") CoCompactSAModel ccsaM){
		//专递ccsaM
		Map map = new HashMap();
		String statename="";
		statename=changeState(ccsaM.getCcsa_state());
		map.put("statename",statename);
		map.put("ccsaM",ccsaM);
		Window window = (Window)Executions.createComponents("CompactSA_Detail.zul",null, map);
		window.doModal();
		//ccsaBaseList = ccsaBll.getCCSABaseAll();
		if(ccsa_state=="10"||ccsa_state.equals("10")||ccsa_state=="-1"||ccsa_state.equals("-1"))
		{
			ccsa_state="";
		}
		ccsaBaseList=ccsaBll.searchCCSABase(coba_company,compactid,ccsa_state,ccsa_addname);
		}
	
	//弹出合同补充协议的修改页面
	@Command("infoupdate")
	@NotifyChange("ccsaBaseList")
	public void infoupdate(@BindingParam("ccsaM") CoCompactSAModel ccsaM){
		//专递ccsaM
		Map map = new HashMap();
		String statename="";
		statename=changeState(ccsaM.getCcsa_state());
		map.put("statename",statename);
		map.put("ccsaM",ccsaM);
		Window window = (Window)Executions.createComponents("CompactSA_Update.zul",null, map);
		window.doModal();
		//ccsaBaseList = ccsaBll.getCCSABaseAll();
		if(ccsa_state=="10"||ccsa_state.equals("10")||ccsa_state=="-1"||ccsa_state.equals("-1"))
		{
			ccsa_state="";
		}
		ccsaBaseList=ccsaBll.searchCCSABase(coba_company,compactid,ccsa_state,ccsa_addname);
		}
	

	//协议查询方法
	@Command("search")
	@NotifyChange("ccsaBaseList")
	public void search(){
		System.out.println("ccsa_state="+ccsa_state);
		if(ccsa_state=="10"||ccsa_state.equals("10")||ccsa_state=="-1"||ccsa_state.equals("-1"))
		{
			ccsa_state="";
		}
		ccsaBaseList=ccsaBll.searchCCSABase(coba_company,compactid,ccsa_state,ccsa_addname);
	}

	
	//补充协议状态由数字转换为中文的方法,返回中文的状态
	private String changeState(int stateid)
	{
		String statename="";
		if(stateid==0)
		{
			statename="待制作协议";
		}
		else if(stateid==1)
		{
			statename="已制作协议";
		}
		else if(stateid==2)
		{
			statename="已审核";
		}
		else if(stateid==3)
		{
			statename="已打印";
		}
		else if(stateid==4)
		{
			statename="已签回";
		}
		else if(stateid==5)
		{
			statename="已归档";
		}
			
		return statename;
	}
	public List getCcsaBaseList() {
		return ccsaBaseList;
	}

	public void setCcsaBaseList(List ccsaBaseList) {
		this.ccsaBaseList = ccsaBaseList;
	}

	public List getCcsaAddnameList() {
		return ccsaAddnameList;
	}

	public void setCcsaAddnameList(List ccsaAddnameList) {
		this.ccsaAddnameList = ccsaAddnameList;
	}

	public CoCompactSA_SelectBll getCcsaBll() {
		return ccsaBll;
	}

	public void setCcsaBll(CoCompactSA_SelectBll ccsaBll) {
		this.ccsaBll = ccsaBll;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public String getCcsa_addname() {
		return ccsa_addname;
	}

	public void setCcsa_addname(String ccsa_addname) {
		this.ccsa_addname = ccsa_addname;
	}

	public String getCompactid() {
		return compactid;
	}

	public void setCompactid(String compactid) {
		this.compactid = compactid;
	}

	public String getCcsa_state() {
		return ccsa_state;
	}

	public void setCcsa_state(String ccsa_state) {
		this.ccsa_state = ccsa_state;
	}
}
