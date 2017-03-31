package Controller.EmBenefit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmActy_SurveyInfoOperateBll;

import Model.CoBaseModel;
import Model.SurveyContentModel;
import Model.SurveyInfoModel;

public class EmActy_SurveyContentEditListController {
	private List<SurveyInfoModel> list= (List<SurveyInfoModel>) Executions.getCurrent().getArg().get("list");
	 Calendar cal = Calendar.getInstance();
	 int year = cal.get(Calendar.YEAR);
	
	@Command
	public void addrow(@BindingParam("rws") Rows rws,@BindingParam("val") List<SurveyContentModel> m,
			@BindingParam("antype") String antype)
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
			addtxt(row,m);
		}
	}
	
	//提交时间
	@Command
	public void sumit(@BindingParam("gd") Grid gd,@BindingParam("win") Window win,
			@BindingParam("surveytype") String surveytype,@BindingParam("ownyear") Integer ownyear)
	{
		List<SurveyInfoModel> slist=new ArrayList<SurveyInfoModel>();
		int k=gd.getRows().getChildren().size();
		for(int i=0;i<k;i++)
		{
			Cell cel=(Cell) gd.getCell(i, 2);
			Checkbox ck=(Checkbox) cel.getChildren().get(0);
			if(ck.isChecked())
			{
				SurveyInfoModel m=new SurveyInfoModel();
				m.setSury_id(Integer.parseInt(ck.getValue()+""));
				slist.add(m);
			}
		}
		int d=addHistorySurveyInfo(slist,ownyear,surveytype);
		if(d>0)
		{
			Messagebox.show("提交成功","提示",Messagebox.OK,Messagebox.INFORMATION);
			win.detach();
		}
		else
		{
			Messagebox.show("提交shibai ","提示",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	
	//测试获取选择的响
	@Command
	public void summit(@BindingParam("gd") Grid gd)
	{
		int num=gd.getRows().getChildren().size();
		for(int i=0;i<num;i++)
		{
			Cell c=(Cell) gd.getCell(i, 1);
			Label la=(Label) c.getChildren().get(0);
			Grid g= (Grid) c.getChildren().get(1);
			int gn=g.getRows().getChildren().size();
			for(int y=0;y<gn;y++)
			{
				Row r=(Row) g.getRows().getChildren().get(0);
				int t=r.getChildren().size();
				for(int k=0;k<t;k++)
				{
					int h=g.getCell(y, k).getChildren().size();
					for(int l=0;l<h;l++)
					{
						String[] names = g.getCell(y, k).getChildren().get(l).getClass().toString().split("\\.");
						if(names.length>1)
						{
							if(names[names.length-1].equals("Radiogroup")||names[names.length-1]=="Radiogroup")
							{
								Radiogroup cs=(Radiogroup) g.getCell(y, k).getChildren().get(l);
								try{
									System.out.println("classs="+cs.getSelectedItem().getValue()+"");
								}catch(Exception e)
								{
									System.out.println("还有选项没有选择");
								}
							}
							else if(names[names.length-1].equals("Checkbox")||names[names.length-1]=="Checkbox")
							{
								Checkbox ck=(Checkbox) g.getCell(y, k).getChildren().get(l);
								if(ck.isChecked())
								{
									System.out.println("classs="+ck.getValue()+"");
								}
							}
							else if(names[names.length-1].equals("Textbox")||names[names.length-1]=="Textbox")
							{
								Textbox tx=(Textbox) g.getCell(y, k).getChildren().get(l);
								System.out.println("classs="+tx.getValue()+"");
								System.out.println("id="+tx.getId()+"");
							}
						}
					}
				}
			}	
		}
	}
	
	public List<SurveyInfoModel> getList() {
		return list;
	}

	public void setList(List<SurveyInfoModel> list) {
		this.list = list;
	}
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	//生成单选
	private void addradio(Row row,List<SurveyContentModel> m)
	{
		Radiogroup rp=new Radiogroup();
		rp.setWidth("1px");
		for(int i=0;i<m.size();i++)
		{
			SurveyContentModel ml=m.get(i);
			Cell cellbel1=new Cell();
			Radio rd=new Radio(ml.getCont_content());
			rd.setValue(ml.getCont_id());
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
	private void addcheck(Row row,List<SurveyContentModel> m)
	{
		for(int i=0;i<m.size();i++)
		{
			SurveyContentModel ml=m.get(i);
			Cell cellbel1=new Cell();
			Checkbox ck=new Checkbox(ml.getCont_content());
			ck.setValue(ml.getCont_id());
			cellbel1.setParent(row);
			ck.setParent(cellbel1);
		}
	}
	
	//生成填空
	private void addtxt(Row row,List<SurveyContentModel> m)
	{
		Cell cellb=new Cell();
		cellb.setWidth("20px");
		Label l=new Label("答：");
		l.setParent(cellb);
		cellb.setParent(row);
		Cell cellbel1=new Cell();
		cellbel1.setAlign("left");
		Textbox txt=new Textbox();
		txt.setId(m.get(0).getCont_id()+"");
		cellbel1.setParent(row);
		txt.setWidth("80%");
		txt.setParent(cellbel1);
	}
	
	private Integer addHistorySurveyInfo(List<SurveyInfoModel> list,Integer ownyear,String hitl_type)
	{
		EmActy_SurveyInfoOperateBll obll=new EmActy_SurveyInfoOperateBll();
		Integer returnid=null;
		for(int i=0;i<list.size();i++)
		{
			SurveyInfoModel ml=list.get(i);
			int k=obll.EmActy_SurveyHistoryInfoAdd(ml.getSury_id(), ownyear,hitl_type);
			if(k>0)
			{
				returnid=1;
			}
		}
		return returnid;
	}
}
