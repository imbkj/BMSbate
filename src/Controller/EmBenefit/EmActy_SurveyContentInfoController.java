package Controller.EmBenefit;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import Model.SurveyContentModel;
import bll.EmBenefit.EmActy_SurveyInfoSelectBll;

public class EmActy_SurveyContentInfoController {
	private Integer sury_id = (Integer) Executions.getCurrent().getArg().get("sury_id");
	private EmActy_SurveyInfoSelectBll bll=new EmActy_SurveyInfoSelectBll();
	private List<SurveyContentModel> list=bll.getSurveyContentInfo(" and cont_suryid="+sury_id);
	@Command
	public void addcell(@BindingParam("rw") Row rw)
	{
		for(int i=0;i<list.size();i++)
		{
			Cell cellbelem=new Cell();
			cellbelem.setWidth("40px");
			Label belem=new Label("");
			cellbelem.setParent(rw);
			belem.setParent(cellbelem);
			
			SurveyContentModel m=list.get(i);
			Cell cellbel1=new Cell();
			cellbel1.setWidth("20px");
			Label bel1=new Label(getAtoZ(i)+"、");
			cellbel1.setParent(rw);
			bel1.setParent(cellbel1);
			
			Cell cellbel2=new Cell();
			Label bel2=new Label(m.getCont_content());
			cellbel2.setParent(rw);
			bel2.setParent(cellbel2);
			System.out.print("\t ");
		}
	}
	public List<SurveyContentModel> getList() {
		return list;
	}
	public void setList(List<SurveyContentModel> list) {
		this.list = list;
	}
	
	private String getAtoZ(int i)
	{
		String str="";
		if(i==0)
		{
			str="A";
		}
		else if(i==1)
		{
			str="B";
		}
		else if(i==2)
		{
			str="C";
		}
		else if(i==3)
		{
			str="D";
		}
		else if(i==4)
		{
			str="E";
		}
		else if(i==5)
		{
			str="F";
		}
		
		
		return str;
	}

}
