package Controller.EmSheBaocard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmShebaoCardInfoModel;
import Util.UserInfo;
import bll.EmSheBaocard.EmShebaoCardInfoOperateBll;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

public class Sbcd_CenterTakeCardController {
	private String id = (String)Executions.getCurrent().getArg().get("daid");
	private String tperid = (String)Executions.getCurrent().getArg().get("id");
	private EmShebaoCardInfoSelectBll bll=new EmShebaoCardInfoSelectBll();
	private EmShebaoCardInfoModel m=new EmShebaoCardInfoModel();
	private List<EmShebaoCardInfoModel> list=bll.getEmShebaoCardInfoList(" and sbcd_id="+id);
	private Date centertaketime=new Date();
	
	public Sbcd_CenterTakeCardController()
	{
		if(list.size()>0)
		{
			m=list.get(0);
			if(m.getSbcd_centertakename()==null||m.getSbcd_centertakename().equals(""))
			{
				m.setSbcd_centertakename(UserInfo.getUsername());
			}
		}
	}
	
	@Command
	public void flsummit(@BindingParam("win") Window win)
	{
		if(m.getSbcd_centertakename()==null||m.getSbcd_centertakename().equals(""))
		{
			Messagebox.show("请输入服务中心领卡人", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else if(centertaketime==null)
		{
			Messagebox.show("请选择服务中心领卡时间", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			Integer stateid=bll.getState("中心签收");
			m.setSbcd_centertaketime(DateToStr(centertaketime));
			String sql=",sbcd_centertakename='"+m.getSbcd_centertakename()+"'";
			sql=sql+",sbcd_centertaketime='"+m.getSbcd_centertaketime()+"'";
			sql=sql+",sbcd_stateid='"+stateid+"'";
			EmShebaoCardInfoOperateBll obll=new EmShebaoCardInfoOperateBll();
			String str[]=obll.EmShebaoCardUpdate(m, sql);
			if(str[0]=="1")
			{
				Messagebox.show("提交成功", "提示", Messagebox.OK,Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	
	/**
	* 日期转换成字符串
	* @param date 
	* @return str
	*/
	public static String DateToStr(Date date) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	   String str ="";
	   if(date!=null)
	   {
		   str = format.format(date);
	   }
	   return str;
	}

	public EmShebaoCardInfoModel getM() {
		return m;
	}

	public void setM(EmShebaoCardInfoModel m) {
		this.m = m;
	}

	public Date getCentertaketime() {
		return centertaketime;
	}

	public void setCentertaketime(Date centertaketime) {
		this.centertaketime = centertaketime;
	}

}
