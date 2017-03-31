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

public class CoAg_CompactArchiveController {
	private String id = (String)Executions.getCurrent().getArg().get("daid");
	private String tperid = (String)Executions.getCurrent().getArg().get("id");
	private CoAg_CompactSelectBll bll=new CoAg_CompactSelectBll();
	private CoAgencyCompactModel model=bll.getCoAgencyCompactModel(Integer.parseInt(id));
	private Date archivedate;
	public CoAg_CompactArchiveController()
	{
		model.setCoct_en_amount("1");
		model.setCoct_ch_amount("1");
	}
	
	@Command
	public void summit(@BindingParam("win") Window win)
	{
		String strinfo=ifEmpty(model);
		if(strinfo==null||strinfo.equals(""))
		{
			model.setCoct_archivetime(getStringDate(archivedate));
			CoAg_CompactOperateBll obll=new CoAg_CompactOperateBll();
			String[] str=obll.ArchiveComapct(model);
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
			Messagebox.show(strinfo, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	private String ifEmpty(CoAgencyCompactModel m)
	{
		String str="";
		if(archivedate==null)
		{
			str="请选择归档日期";
		}
		else if(m.getCoct_archivenumber()==null||m.getCoct_archivenumber().equals(""))
		{
			str="请填写存档编号";
		}
		else if(m.getCoct_ch_amount()==null||m.getCoct_ch_amount().equals(""))
		{
			str="请填写中文合同份数";
		}
		else if(m.getCoct_en_amount()==null||m.getCoct_en_amount().equals(""))
		{
			str="请填写英文合同份数";
		}
		return str;
	}
	public CoAgencyCompactModel getModel() {
		return model;
	}
	public void setModel(CoAgencyCompactModel model) {
		this.model = model;
	}
	public Date getArchivedate() {
		return archivedate;
	}
	public void setArchivedate(Date archivedate) {
		this.archivedate = archivedate;
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
