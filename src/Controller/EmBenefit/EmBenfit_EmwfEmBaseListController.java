package Controller.EmBenefit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmBenefitModel;
import Model.EmWelfareModel;
import Util.CobaseUtil;
import bll.EmBenefit.EmBenefit_comitListBll;

public class EmBenfit_EmwfEmBaseListController {
	private EmBenefit_comitListBll bll=new EmBenefit_comitListBll();
	public List<EmWelfareModel> cobaseList = new ListModelList<>();
	private List<String> clientList = new ListModelList<>();
	private List<String> nameList = new ListModelList<>();
	private List<String> itemList = new ListModelList<>();
	private List<EmWelfareModel> list=bll.getWfList(" and (emwf_state=3 or emwf_state=11) ","");
	private String statename="";
	
	
	public EmBenfit_EmwfEmBaseListController()
	{
		clientList=CobaseUtil.getClientList();
		itemList=bll.getEmbfnameList();
	}
	
	@Command("checkall")
	public void checkall(@BindingParam("a") Checkbox cka,@BindingParam("gd") Grid gd) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 11)!=null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 11).getChildren().get(0);
				ck.setChecked(cka.isChecked());		
			}else {
				
				return;
			}

		}
	}
	
	//查询
	@Command
	@NotifyChange("list")
	public void submit(@BindingParam("client") String client,@BindingParam("company") String company,
			@BindingParam("item") String item,@BindingParam("emwfclass") String emwfclass,
			@BindingParam("name") String name,@BindingParam("content") String content)
	{
		String str="",strcon="";
		if(client!=null&&!client.equals(""))
		{
			str=str+" and emwf_client='"+client+"'";
		}
		
		if(company!=null&&!company.equals(""))
		{
			str=str+" and emwf_company like '%"+company+"%'";
		}
		
		if(item!=null&&!item.equals(""))
		{
			str=str+" and embf_name='"+item+"'";
		}
		if(emwfclass!=null&&!emwfclass.equals(""))
		{
			str=str+" and embf_mold='"+emwfclass+"'";
		}
		if(name!=null&&!name.equals(""))
		{
			//strcon=strcon+" and emwf_name='"+name+"'";
			str=str+" and emwf_name='"+name+"'";
		}
		if(content!=null&&!content.equals(""))
		{
			strcon=strcon+" and prod_name='"+content+"'";
		}
		if(statename!=null&&!statename.equals(""))
		{
			if(statename.equals("已确认"))
			{
				str=str+" and emwf_state=3";
				//strcon=strcon+" and emwf_state=3";
			}
			else if(statename.equals("退回"))
			{
				str=str+" and emwf_state=11";
				//strcon=strcon+" and emwf_state=11";
			}
		}
		list=bll.getWfList(" and (emwf_state=3 or emwf_state=11) "+str,strcon);
	}
	
	//福利申请
	@Command
	@NotifyChange("list")
	public void addgift(@BindingParam("url") String url,@BindingParam("gd") Grid gds)
	{
		String type="";
		String con="",con1="",flag="";
		int l=0;
		List<EmWelfareModel> lists=new ArrayList<EmWelfareModel>();
		for (int j = 0; j < gds.getRows().getChildren().size(); j++) {
			if (gds.getCell(j, 0)!=null) {
				Detail de=(Detail) gds.getCell(j, 0);
				if(de.getChildren().size()>0)
				{
				Grid gd=(Grid) de.getChildren().get(0);
		
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 11)!=null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 11).getChildren().get(0);
				if(ck.isChecked())
				{
					l=l+1;
					EmWelfareModel m=ck.getValue();
					if(l==1)
					{
						con=m.getEmbf_name();
						type=m.getEmbf_mold();
					}
					else
					{
						con1=m.getEmbf_name();
						if(con!=null&&con1!=null)
						{
							if(con.equals(con1)||con==con1)
							{
								flag="";
							}
							else
							{
								flag="选择的福利项目不同";
								break;
							}
						}
					}
					lists.add(m);
				}
			}else {
				return;
			}
		}
			}
		}
		}
		if(flag=="")
		{
			if(lists.size()>0)
			{
				Map map=new HashMap<>();
				map.put("list", lists);
				map.put("gifttype", type);
				map.put("con", con);
				Window window=(Window)Executions.createComponents(url, null, map);
				window.doModal();
				list=bll.getWfList(" and (emwf_state=3 or emwf_state=11) ","");
			}
			else
			{
				Messagebox.show("请先选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("选择的福利项目不同，不能一起采购", "提示", Messagebox.OK, Messagebox.ERROR);
		}	
	}
	
	
	public List<EmWelfareModel> getList() {
		return list;
	}
	public void setList(List<EmWelfareModel> list) {
		this.list = list;
	}
	public List<EmWelfareModel> getCobaseList() {
		return cobaseList;
	}
	public void setCobaseList(List<EmWelfareModel> cobaseList) {
		this.cobaseList = cobaseList;
	}
	public List<String> getClientList() {
		return clientList;
	}
	public void setClientList(List<String> clientList) {
		this.clientList = clientList;
	}
	public List<String> getNameList() {
		return nameList;
	}
	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}
	public List<String> getItemList() {
		return itemList;
	}
	public void setItemList(List<String> itemList) {
		this.itemList = itemList;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}
	
	

}
