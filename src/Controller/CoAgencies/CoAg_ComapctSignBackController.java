package Controller.CoAgencies;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoAgencyCompactModel;
import bll.CoAgencies.CoAg_CompactOperateBll;
import bll.CoAgencies.CoAg_CompactSelectBll;

public class CoAg_ComapctSignBackController {
	private String id = (String)Executions.getCurrent().getArg().get("daid");
	private String tperid = (String)Executions.getCurrent().getArg().get("id");
	private CoAg_CompactSelectBll bll=new CoAg_CompactSelectBll();
	private CoAgencyCompactModel model=bll.getCoAgencyCompactModel(Integer.parseInt(id));
	private Date signbacktime;
	
	
	@Command
	public void summit(@BindingParam("win") Window win)
	{
		if(signbacktime!=null)
		{
			model.setCoct_signbacktime(getStringDate(signbacktime));
			CoAg_CompactOperateBll obll=new CoAg_CompactOperateBll();
			String[] str=obll.SignBackComapct(model);
			if(str[0]=="1")
			{
				Messagebox.show("签回成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("请选择签回时间", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public CoAgencyCompactModel getModel() {
		return model;
	}
	public void setModel(CoAgencyCompactModel model) {
		this.model = model;
	}
	public Date getSignbacktime() {
		return signbacktime;
	}
	public void setSignbacktime(Date signbacktime) {
		this.signbacktime = signbacktime;
	}
	
	/**
	   * 获取现在时间
	   * 
	   * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	   */
	public static String getStringDate(Date d) {
		String dateString="";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(d!=null)
		{
			dateString = formatter.format(d);
		}
		return dateString;
	}
	
}
