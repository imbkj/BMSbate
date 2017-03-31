package Controller.EmBenefit;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmActy_SurveyInfoOperateBll;

import Model.SurveyContentModel;
import Model.SurveyInfoModel;

public class EmActy_SurveryInfoEditController {
	private SurveyInfoModel m = (SurveyInfoModel) Executions.getCurrent().getArg().get("model");
	
	
	//修改提交方法
	@Command
	public void edit(@BindingParam("title") String title,@BindingParam("suryorder") String suryorder,
			@BindingParam("gd") Grid gd,@BindingParam("win") Window win,
			@BindingParam("answertype") String answertype)
	{
		EmActy_SurveyInfoOperateBll bll=new EmActy_SurveyInfoOperateBll();
		SurveyInfoModel model=new SurveyInfoModel();
		model.setSury_id(m.getSury_id());
		model.setSury_title(title);
		model.setSury_answertype(answertype);
		if(suryorder!=null&&!suryorder.equals("")&&suryorder!="")
		{
			model.setSury_order(Integer.parseInt(suryorder));
		}
		int k=bll.updateSurveyTitleInfo(model);
		
		if(k>0)
		{
			int t=0;
			int rownum=gd.getRows().getChildren().size();
			for(int i=0;i<rownum;i++)
			{
				SurveyContentModel cmodel=new SurveyContentModel();
				Cell cel1=(Cell) gd.getCell(i, 1);
				Cell cel2=(Cell) gd.getCell(i, 2);
				Cell cel3=(Cell) gd.getCell(i, 3);
				Textbox txt1=(Textbox) cel1.getChildren().get(0);
				Textbox txt2=(Textbox) cel2.getChildren().get(0);
				Checkbox ck=(Checkbox) cel3.getChildren().get(0);
				if(ck.isChecked())
				{
					cmodel.setCont_state(1);
				}
				else
				{
					cmodel.setCont_state(0);
				}
				
				cmodel.setCont_content(txt1.getValue());
				if(txt2.getValue()!=null&&!txt2.getValue().equals("")&&txt2.getValue()!="")
				{
					cmodel.setCont_order(Integer.parseInt(txt2.getValue()));
				}
				if(txt1.getId()!=null&&!txt1.getId().equals("")&&txt1.getId()!="")
				{
					cmodel.setCont_id(Integer.parseInt(txt1.getId()));
				}
				int y=bll.updateSurveyContentInfo(cmodel);
				if(y>0)
				{
					t=1;
				}
			}
			if(t>0)
			{
				Messagebox.show("修改成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("题目修改成功，内容修改失败", "提示", Messagebox.OK, Messagebox.ERROR);
				win.detach();
			}
			
		}
		else
		{
			Messagebox.show("修改失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public SurveyInfoModel getM() {
		return m;
	}

	public void setM(SurveyInfoModel m) {
		this.m = m;
	}
	
}
