package Controller.EmBenefit;

import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;
import Model.EmWelfareModel;
import Util.CobaseUtil;
import bll.EmBenefit.EmBenefit_comitListBll;

public class EmActy_EmbaseInfoListController {
	private EmBenefit_comitListBll bll=new EmBenefit_comitListBll();
	public List<EmWelfareModel> cobaseList = new ListModelList<>();
	private List<String> clientList = new ListModelList<>();
	private List<String> nameList = new ListModelList<>();
	private List<String> itemList = new ListModelList<>();
	private List<EmWelfareModel> list=bll.getWfLists("","");
	
	
	public EmActy_EmbaseInfoListController()
	{
		clientList=bll.getClientList();
		itemList=bll.getEmbfnameList();
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
			strcon=strcon+" and emwf_name='"+name+"'";
			str=str+" and emwf_name='"+name+"'";
		}
		if(content!=null&&!content.equals(""))
		{
			strcon=strcon+" and prod_name='"+content+"'";
			str=str+" and emwf_gift_id in(select prod_id from EmActySupProductInfo where prod_name='"+content+"')";
		}
		list=bll.getWfLists(str,strcon);
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
}
