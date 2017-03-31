package Controller.CoBase;

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

import Model.WtServiceStandardrelationModel;
import Util.RegexUtil;
import bll.EmCommissionOut.Standard.WtServiceStandardBll;

public class Cobase_WtstandardlistController {
	private int cid = Integer.valueOf(Executions.getCurrent().getArg().get("cid")
			.toString());

	private String shortname = "";
	private String name = "";
	private String statename="";
	private Date addtime;
	
	private List<WtServiceStandardrelationModel> stList;
	private List<WtServiceStandardrelationModel> sstList = new ListModelList<>();
	private WtServiceStandardBll bll =new WtServiceStandardBll();
	private List<String> stateList = new ListModelList<>();

	public Cobase_WtstandardlistController()
	{
		setStList(bll.getmodelListonly("and a.cid = "+cid+""));
		stateList.add("");
		stateList.add("已生效");
		stateList.add("未生效");
		search();
	}

	@Command("search")
	@NotifyChange({ "sstList", "coabList" })
	public void search() {
		//Standard_ListBll bll = new Standard_ListBll();
		stList.clear();
		setStList(bll.getmodelListonly("and a.cid = "+cid+""));
		sstList.clear();

		for (WtServiceStandardrelationModel m : stList) {
			
			if (!shortname.isEmpty()) {
				if (!RegexUtil.isExists(shortname, m.getCoba_shortname())) {
					continue;
				}
			}
			if (!name.isEmpty()) {
				if (!RegexUtil.isExists(name, m.getWtss_title())) {
					continue;
				}
			}
			if (!statename.isEmpty()) {
				if (!RegexUtil.isExists(statename, m.getWtss_statename())) {
					continue;
				}
			}
			
			if (addtime != null) {
				if (!new SimpleDateFormat("yyyy-MM-dd").format(addtime).equals(
						m.getWtss_addtime())) {
					continue;
				}
			}
			sstList.add(m);
		}
	}
	
	@Command("openwin")
	@NotifyChange({ "sstList", "coabList" })
	public void openwin(@BindingParam("daid") Integer daid,@BindingParam("cid") Integer cid,
			@BindingParam("url") String url) {
	
		Map<String, Object> map = new HashMap<String, Object>();
		 
		map.put("cid", cid);
		map.put("daid", daid);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		

	}
	
	@Command("add")
	@NotifyChange("sstList")
	public void add() {
	
		Map<String, Object> map = new HashMap<String, Object>();
	 
		map.put("cid", cid);
		Window window = (Window) Executions.createComponents("../EmCommissionOut/Standard/Wtstandard_add.zul", null, map);
		window.doModal();
		search();


	}
	
	@Command("delewt")
	@NotifyChange({ "sstList", "coabList" })
	public void delewt(@BindingParam("daid") Integer daid,@BindingParam("cid") Integer cid,
			@BindingParam("url") String url) {
		WtServiceStandardrelationModel m= new WtServiceStandardrelationModel();
		m.setWtss_id(daid);
	
		if (Messagebox.show("确认要删除此条吗？", "操作提示",
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			
			if (bll.checkrlation(m))
			{
				Messagebox.show("HI，已有服务费关联，不能删除服务要求！", "INFORMATION",
						Messagebox.OK, Messagebox.INFORMATION);
			}
			else
			{	
				int i=0;
			
				i= bll.WtServiceStandarddelete(daid);
				if (i>0)
				{
					Messagebox.show("删除成功！", "INFORMATION",
							Messagebox.OK, Messagebox.INFORMATION);
				}
				else
				{
					Messagebox.show("删除遇到了一个问题，请联系IT的帅哥！", "INFORMATION",
							Messagebox.OK, Messagebox.INFORMATION);
				}
				
			}
		}
		
		search();

	}
	
	
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}


	public String getStatename() {
		return statename;
	}


	public void setStatename(String statename) {
		this.statename = statename;
	}




	public List<String> getStateList() {
		return stateList;
	}




	public void setStateList(List<String> stateList) {
		this.stateList = stateList;
	}





	


	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
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


	public List<WtServiceStandardrelationModel> getStList() {
		return stList;
	}


	public void setStList(List<WtServiceStandardrelationModel> stList) {
		this.stList = stList;
	}


	public List<WtServiceStandardrelationModel> getSstList() {
		return sstList;
	}


	public void setSstList(List<WtServiceStandardrelationModel> sstList) {
		this.sstList = sstList;
	}
	
	


}
