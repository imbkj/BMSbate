package Controller.EmBenefit;

import java.math.BigDecimal;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import Model.SurveyContentModel;
import Model.SurveyInfoModel;
import bll.EmBenefit.EmActy_SurveyInfoOperateBll;
import bll.EmBenefit.EmActy_SurveyInfoSelectBll;

public class EmActy_SurverInfoAddController {
	private EmActy_SurveyInfoSelectBll bll=new EmActy_SurveyInfoSelectBll();
	private Integer orderid=bll.getMaxOrder()+1;
	
	@Command
	public void addsurver(@BindingParam("title") String title,@BindingParam("order") Integer order,
			@BindingParam("gd") Grid gd,@BindingParam("answertype") String answertype)
	{
		if(title!=null&&!title.equals("")&&title!="")
		{
			EmActy_SurveyInfoOperateBll obll=new EmActy_SurveyInfoOperateBll();
			SurveyInfoModel m=new SurveyInfoModel();
			m.setSury_title(title);
			m.setSury_order(order);
			m.setSury_answertype(answertype);
			Integer suryid=obll.EmActy_SurveyTitleAdd(m);
			if(suryid>0)
			{
				int rwnum=gd.getRows().getChildren().size();
				if(rwnum>0)
				{
					for(int i=0;i<rwnum;i++)
					{
						Cell cl=(Cell) gd.getCell(i, 1);
						Textbox tx=(Textbox) cl.getChildren().get(0);
						Cell cl3=(Cell) gd.getCell(i, 3);
						Intbox tx3=(Intbox) cl3.getChildren().get(0);
						Cell cl5=(Cell) gd.getCell(i, 5);
						Intbox ix=(Intbox) cl5.getChildren().get(0);
						if(tx.getValue()!=null&&!tx.getValue().equals("")&&tx.getValue()!="")
						{
							SurveyContentModel cm=new SurveyContentModel();
							cm.setCont_suryid(suryid);
							cm.setCont_content(tx.getValue());
							if(tx3.getValue()!=null&&!tx3.getValue().equals(""))
							{
								BigDecimal score=new BigDecimal(tx3.getValue()); 
								cm.setCont_score(score);
							}
							cm.setCont_order(ix.getValue());
							obll.EmActy_SurveyContentAdd(cm);
						}
					}
					Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
					Executions.sendRedirect("/EmBenefit/EmActy_SurverInfoAdd.zul");
				}
				else
				{
					Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
				}
			}
		}
		else
		{
			Messagebox.show("请输入题目", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	

}
