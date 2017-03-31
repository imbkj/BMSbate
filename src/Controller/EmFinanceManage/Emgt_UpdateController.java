package Controller.EmFinanceManage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmGatheringModel;
import Util.UserInfo;
import bll.EmFinanceManage.emgt_OperateBll;

public class Emgt_UpdateController {
	private EmGatheringModel model = (EmGatheringModel) Executions.getCurrent().getArg().get("model");
	private emgt_OperateBll bll=new emgt_OperateBll();
	private Date ownmonth;
	
	public Emgt_UpdateController()
	{
		  if(model.getOwnmonth()!=null)
		  {
			  ownmonth=StringToDate(model.getOwnmonth()+"");
		  }
		  
	}
	
	
	//提交
	@Command
	public void summit(@BindingParam("win") Window win)
	{
		if(ownmonth==null)
		{
			Messagebox.show("请输入所属月份", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else if(model.getEmgt_type()==null||model.getEmgt_type().equals(""))
		{
			Messagebox.show("请输入收费类型", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else if(model.getEmgt_paytype()==null||model.getEmgt_paytype().equals(""))
		{
			Messagebox.show("请输入收费方式", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else if(model.getEmgt_fee()==null)
		{
			Messagebox.show("请输入金额", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			model.setOwnmonth(Integer.parseInt(datechangestr(ownmonth)));
			Integer k=bll.EmGatheringUpdate(model);
			if(k>0)
			{
				Messagebox.show("修改成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("修改失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	
	
	public EmGatheringModel getModel() {
		return model;
	}
	public void setModel(EmGatheringModel model) {
		this.model = model;
	}
	
	
	public Date getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Date ownmonth) {
		this.ownmonth = ownmonth;
	}

	/** 
     * 将yyyy-MM-dd HH:mm:ss 时间格式字符串转化成时间 
     * @return 
     */  
    public static Date StringToDate(String month){  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");  
        Date s=null;  
        try {  
             s= formatter.parse(month);  
        } catch (ParseException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        System.out.println("TIME:::"+s);  
        return s;  
    } 
    
    private String datechangestr(Date d)
	{
		String dateString="";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		if(d!=null&&!d.equals(""))
		{
			dateString = formatter.format(d);
		}
		return dateString;
	}
}
