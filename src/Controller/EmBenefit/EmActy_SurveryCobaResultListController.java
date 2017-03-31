package Controller.EmBenefit;

import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import bll.EmBenefit.EmActy_SurveryHistorySelectBll;

import Model.CoBaseModel;
import Model.SurveyHistoryContentInfoModel;
import Model.SurveyHistoryTitleInfoModel;

public class EmActy_SurveryCobaResultListController {
	private CoBaseModel cm = (CoBaseModel) Executions.getCurrent().getArg().get("model");
	private EmActy_SurveryHistorySelectBll bll=new EmActy_SurveryHistorySelectBll();
	private List<SurveyHistoryTitleInfoModel> list=bll.getSurveyHistoryTitleInfo("");
	List<SurveyHistoryContentInfoModel> rlist=bll.getSurveyResultInfo(" and a.cid="+cm.getCid());
	@Command 
	public void addrow(@BindingParam("rws") Rows rws,@BindingParam("val") List<SurveyHistoryContentInfoModel> m,
			@BindingParam("antype") String antype,@BindingParam("id") Integer id)
	{
		Row row=new Row();
		row.setParent(rws);
		if(antype.equals("1")||antype=="1")
		{
			addradio(row,m);
		}
		else if(antype.equals("2")||antype=="2")
		{
			addcheck(row,m);
		}
		else if(antype.equals("3")||antype=="3")
		{
			addtxt(row,m,id);
		}
	}
	
	//生成单选
	private void addradio(Row row,List<SurveyHistoryContentInfoModel> m)
	{
		Radiogroup rp=new Radiogroup();
		rp.setWidth("1px");
		for(int i=0;i<m.size();i++)
		{
			SurveyHistoryContentInfoModel ml=m.get(i);
			Cell cellbel1=new Cell();
			Radio rd=new Radio(ml.getHicn_content());
			rd.setSelected(isSelected(ml.getHicn_titleid(), ml.getHicn_id()));
			if(!rd.isSelected()||rd.isSelected()==false)
			{
				rd.setDisabled(true);
			}
			rd.setValue(ml.getHicn_id());
			cellbel1.setParent(row);
			rd.setParent(cellbel1);
			rd.setRadiogroup(rp);
		}
		Cell c=new Cell();
		c.setParent(row);
		rp.setParent(c);
		c.setWidth("1px");
	}
			
	//生成多选
	private void addcheck(Row row,List<SurveyHistoryContentInfoModel> m)
	{
		for(int i=0;i<m.size();i++)
		{
			SurveyHistoryContentInfoModel ml=m.get(i);
			Cell cellbel1=new Cell();
			Checkbox ck=new Checkbox(ml.getHicn_content());
			ck.setChecked(isSelected(ml.getHicn_titleid(), ml.getHicn_id()));
			ck.setValue(ml.getHicn_id());
			if(!ck.isChecked())
			{
				ck.setDisabled(true);
			}
			cellbel1.setParent(row);
			ck.setParent(cellbel1);
		}
	}
		
	//生成填空
	private void addtxt(Row row,List<SurveyHistoryContentInfoModel> m,Integer id)
	{
		Cell cellb=new Cell();
		cellb.setWidth("20px");
		Label l=new Label("答：");
		l.setParent(cellb);
		cellb.setParent(row);
		Cell cellbel1=new Cell();
		cellbel1.setAlign("left");
		Textbox txt=new Textbox();
		cellbel1.setParent(row);
		txt.setWidth("80%");
		txt.setParent(cellbel1);
		if(m.size()>0)
		{
			txt.setValue(getTxtvalue(id));
		}
	}



	public List<SurveyHistoryTitleInfoModel> getList() {
		return list;
	}
	public void setList(List<SurveyHistoryTitleInfoModel> list) {
		this.list = list;
	}
	
	private boolean isSelected(Integer titid,Integer contid)
	{
		boolean flag=false;
		for(int i=0;i<rlist.size();i++)
		{
			SurveyHistoryContentInfoModel m=rlist.get(i);
			if((m.getRelt_titlid().equals(titid)||m.getRelt_titlid()==titid)
					&&(m.getRelt_contid().equals(contid)||m.getRelt_contid()==contid))
			{
				flag=true;
			}
		}
		return flag;
	}
	
	private String getTxtvalue(Integer titid)
	{
		String str="";
		for(int i=0;i<rlist.size();i++)
		{
			SurveyHistoryContentInfoModel m=rlist.get(i);
			if((m.getRelt_titlid().equals(titid)||m.getRelt_titlid()==titid)
					&&(m.getRelt_contid()==null||m.getRelt_contid().equals(0)))
			{
				str=m.getRelt_txtcon();
			}
		}
		return str;
	}
}
