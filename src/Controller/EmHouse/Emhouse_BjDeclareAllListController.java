package Controller.EmHouse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import bll.EmHouse.Emhouse_BjBll;

import Model.EmHouseBJModel;
import Util.UserInfo;

public class Emhouse_BjDeclareAllListController {
	List<EmHouseBJModel> listmodel = (List<EmHouseBJModel>)Executions.getCurrent().getArg().get("listmodel");
	private String idstr="";
	public Emhouse_BjDeclareAllListController()
	{
		if(!listmodel.isEmpty())
		{
			for(int i=0;i<listmodel.size();i++)
			{
				if(i==0)
				{
					idstr=listmodel.get(i).getEmhb_id()+"";
				}
				else
				{
					idstr=idstr+","+listmodel.get(i).getEmhb_id();
				}
			}
		}
	}
	//全选
	@Command
	public void radioSelectAll(@BindingParam("r") String selectval,@BindingParam("declarelist") Grid declarelist)
	{	int j=0;
		if(selectval.equals("0")||selectval=="0")
		{
			j=0;
		}
		else if(selectval.equals("2")||selectval=="2")
		{
			j=1;
		}
		else if(selectval.equals("1")||selectval=="1")
		{
			j=2;
		}
		else if(selectval.equals("3")||selectval=="3")
		{
			j=3;
		}
		for(int i=0;i<listmodel.size();i++)
		{
			Label label = (Label) declarelist.getCell(i,2).getChildren().get(0);
			String id=label.getValue();
			Radiogroup rg=(Radiogroup) declarelist.getCell(i,4).getChildren().get(0);
			rg.setSelectedIndex(j);
		}
	}
	
	@Command
	public void declareAll(@BindingParam("declarelist") Grid declarelist)
	{
		String date = getStringDate(new Date());
		String str=ifExistBackContent(declarelist);
		if(str.equals("")&&str=="")
		{
			Emhouse_BjBll bll = new Emhouse_BjBll();
			int k=1;
			for(int i=0;i<listmodel.size();i++)
			{
				Label label = (Label) declarelist.getCell(i,2).getChildren().get(0);
				String id=label.getValue();
				Radiogroup rg=(Radiogroup) declarelist.getCell(i,4).getChildren().get(0);
				String declareid=rg.getSelectedItem().getValue();
				EmHouseBJModel m=new EmHouseBJModel();
				m.setEmhb_id(Integer.parseInt(id));
				m.setEmhb_ifdeclare(Integer.parseInt(declareid));
				m.setEmhb_declarename(UserInfo
					.getUsername());
				m.setEmhb_declaretime(date);
				if(declareid.equals("3")||declareid=="3")
				{
					Textbox tx=(Textbox) declarelist.getCell(i,5).getChildren().get(0);
					m.setEmhb_backreason(tx.getValue());
				}
				int y= bll.EmhouseBjDeclare(m);
				if(y<1)
				{
					k=0;
				}
			}
			if (k > 0) {
				Messagebox.show("申报成功", "提示",
						Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				Messagebox.show("申报失败", "提示",
						Messagebox.OK,
						Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	private String ifExistBackContent(Grid declarelist)
	{
		String str="";
		for(int i=0;i<listmodel.size();i++)
		{
			Textbox tx=new Textbox();
			Radiogroup rg=(Radiogroup) declarelist.getCell(i,4).getChildren().get(0);
			String declareid=rg.getSelectedItem().getValue();
			if(declareid.equals("3")||declareid=="3")
			{
				tx=(Textbox) declarelist.getCell(i,5).getChildren().get(0);
				if(tx.getValue().equals("")||tx.getValue()=="")
				{
					str="退回的数据必须填写退回原因";
					break;
				}
			}
		}
		return str;
	}
	
	public List<EmHouseBJModel> getListmodel() {
		return listmodel;
	}

	public void setListmodel(List<EmHouseBJModel> listmodel) {
		this.listmodel = listmodel;
	}

	public String getIdstr() {
		return idstr;
	}
	public void setIdstr(String idstr) {
		this.idstr = idstr;
	}
	public static String getStringDate(Date d) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(d);
		return dateString;
	}
	
}
