package Controller.ClientRelations.CreditRating;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import bll.CoLatencyClient.CoLatencyClient_AddBll;

import Model.CreditContentInfoModel;
import Model.CreditCriterionModel;

public class CreditContentInfo_EditController extends SelectorComposer<Component>{
	CreditCriterionModel frommodel = (CreditCriterionModel)Executions.getCurrent().getArg().get("model");
	@Wire
	private Textbox conname;
	@Wire
	private Textbox cclass;
	@Wire
	private Checkbox ifvalid;
	@Wire
	private Rows conrows;
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if(frommodel.getCrcr_state()==1)
		{
			ifvalid.setChecked(true);
		}
		CoLatencyClient_AddBll bll=new CoLatencyClient_AddBll();
		List<CreditContentInfoModel> list=bll.getCreditContentInfo(frommodel.getCrcr_id());
		if(!list.isEmpty())
		{
			for(int i=0;i<list.size();i++)
			{
				Row newrow=new Row();
				newrow.setParent(conrows);
				Cell newcell=new Cell();
				Cell newcell2=new Cell();
				newcell.setParent(newrow);
				newcell2.setParent(newrow);
				Textbox tb=new Textbox();
				Checkbox chk=new Checkbox();
				tb.setParent(newcell);
				chk.setParent(newcell2);
				tb.setValue(list.get(i).getCrst_name());
				if(list.get(i).getCrst_state()==1)
				{
					chk.setChecked(true);
				}
				chk.setValue(list.get(i).getCrst_id());
			}
		}
	}
	
	//修改评定标准信息事件
	@Listen("onClick = #summit")
	public void addCreditCriterionInfo(){
		CreditCriterionModel conModel=new CreditCriterionModel();
		List<CreditContentInfoModel> infolist=new ArrayList<CreditContentInfoModel>();
		String str="";
		str=ifEmpty(infolist);
		if(str=="")
		{
			conModel.setCrcr_id(frommodel.getCrcr_id());
			conModel.setCrcr_content(conname.getValue());
			conModel.setCrcr_type(cclass.getValue());
			if(ifvalid.isChecked())
			{
				conModel.setCrcr_state(1);
			}
			else
			{
				conModel.setCrcr_state(0);
			}
			CoLatencyClient_AddBll bll=new CoLatencyClient_AddBll();
			bll.updateCreditCriterion(conModel);
			if(!infolist.isEmpty())
			{
				for(int j=0;j<infolist.size();j++)
				{
					bll.updateCreditContentInfo(infolist.get(j));
				}
			}
			Messagebox.show("修改成功","提示",Messagebox.OK, Messagebox.INFORMATION);
			Executions.sendRedirect("CreditContentInfo_Manager.zul");
		}
		else
		{
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	
	//判断标准信息是否为空
	private String ifEmpty(List<CreditContentInfoModel> infolist)
	{
		String str="";
		if(conname.getValue()==""||conname.getValue().equals(""))
		{
			str="标准名称不能为空";
			conname.focus();
		}
		else if(cclass.getValue()==""||cclass.getValue().equals(""))
		{
			str="标准类型不能为空";
			cclass.focus();
		}
		else
		{
			List rowlist=conrows.getChildren();
			if(!rowlist.isEmpty())
			{
				for(int y=0;y<rowlist.size();y++)
				{
					Row rw=(Row) rowlist.get(y);
					Cell cell1=(Cell) rw.getChildren().get(0);
					Cell cell2=(Cell) rw.getChildren().get(1);
					Textbox tb=(Textbox)cell1.getChildren().get(0);
					Checkbox chk=(Checkbox)cell2.getChildren().get(0);
					if(tb.getValue()==""||tb.getValue().equals(""))
					{
						str="标准内容不能为空";
						tb.focus();
						break;
					}
					else
					{
						CreditContentInfoModel infoModel=new CreditContentInfoModel();
						infoModel.setCrst_name(tb.getValue());
						if(chk.isChecked())
						{
							infoModel.setCrst_state(1);
						}
						else
						{
							infoModel.setCrst_state(0);
						}
						infoModel.setCrst_id(Integer.parseInt(chk.getValue().toString()));
						infolist.add(infoModel);
					}
				}
			}
		}
		
		return str;
	}

}
