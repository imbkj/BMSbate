package Controller.ClientRelations.CreditRating;

import impl.UserInfoImpl;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import bll.ClientRelations.CreditRating.CreditContentInfo_AddBll;

import service.UserInfoService;

import Model.CoLatencyClientModel;
import Model.CreditContentInfoModel;
import Model.CreditCriterionModel;

public class CreditContentInfo_AddController extends SelectorComposer<Component> {
	@Wire
	private Textbox conname;
	@Wire
	private Textbox cclass;
	@Wire
	private Rows conrows;
	@Wire
	private Grid coninfo;
	List<CreditContentInfoModel> creModel;
	Session session =  Executions.getCurrent().getDesktop().getSession();
	UserInfoService uservice=new UserInfoImpl(session);
	String username=uservice.getUsername();
	CreditCriterionModel frommodel = (CreditCriterionModel)Executions.getCurrent().getArg().get("model");
	
	//添加信息标准信息
	@Listen("onClick = #sumit")
	public void addCreditCriterion(){
	
		String str="";
		if(conname.getValue()==""||conname.getValue().equals(""))
		{
			str="标准名称不能为空";
		}
		else if(cclass.getValue()==""&&cclass.getValue().equals(""))
		{
			str="标准类型不能为空";
		}
		if(str=="")
		{
			creModel=new ArrayList<CreditContentInfoModel>();
			str=getConInfo(creModel,username);
			if(str=="")
			{
				CreditCriterionModel modes=new CreditCriterionModel();
				modes.setCrcr_content(conname.getValue());
				modes.setCrcr_type(cclass.getValue());
				modes.setCrcr_addname(username);
				CreditContentInfo_AddBll bll=new CreditContentInfo_AddBll();
				int k=0;
				k=bll.addCreditCriterion(modes);
				if(k>0)
				{
					if(!creModel.isEmpty())
					{
						for(int n=0;n<creModel.size();n++)
						{
							bll.addCreditContentInfo(creModel.get(n), k);
						}
					}
					Messagebox.show("添加成功","提示",Messagebox.OK, Messagebox.INFORMATION);
					Executions.sendRedirect("CreditContentInfo_Manager.zul");	
				}
			}
			else
			{
				Messagebox.show(str,"提示",Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		else
		{
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	
	//获取评价内容的信息
	public String getConInfo(List<CreditContentInfoModel> modellist,String username)
	{
		String str="";
		int nums=conrows.getChildren().size();
		if(nums>0)
		{
			
			for(int i=0;i<nums;i++)
			{
				Textbox tx=(Textbox)(coninfo.getCell(i,1).getChildren().get(0));
				if(tx.getValue()!=""&&!tx.getValue().equals(""))
				{
					CreditContentInfoModel model=new CreditContentInfoModel();
					model.setCrst_name(tx.getValue());
					model.setCrst_addname(username);
					modellist.add(model);
				}
			}
		}
		return str;
	}
	
	//添加评论内容信息事件
	@Listen("onClick = #sumitj")
	public void addCreditCriterionInfo(){
		int id=0;
		id=frommodel.getCrcr_id();
		creModel=new ArrayList<CreditContentInfoModel>();
		int nums=conrows.getChildren().size();
		if(nums>0)
		{
			for(int i=0;i<nums;i++)
			{
				Textbox tx=(Textbox)(coninfo.getCell(i,1).getChildren().get(0));
				if(tx.getValue()!=""&&!tx.getValue().equals(""))
				{
					CreditContentInfoModel model=new CreditContentInfoModel();
					model.setCrst_name(tx.getValue());
					model.setCrst_addname(username);
					creModel.add(model);
				}
			}
			if(!creModel.isEmpty())
			{
				CreditContentInfo_AddBll bll=new CreditContentInfo_AddBll();
				if(id>0)
				{
					for(int n=0;n<creModel.size();n++)
					{
						bll.addCreditContentInfo(creModel.get(n), id);
					}
				}
			}
			Messagebox.show("添加成功","提示",Messagebox.OK, Messagebox.INFORMATION);
			Executions.sendRedirect("CreditContentInfo_Manager.zul");	
		}
	}

}
