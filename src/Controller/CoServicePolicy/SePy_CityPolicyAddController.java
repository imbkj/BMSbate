package Controller.CoServicePolicy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoServicePolicy.SePy_CityPolicyOperateBll;

import Model.CoAgencyBaseCityRelModel;
import Model.CoPolicyNoticeFileModel;
import Model.CoServicePolicyFileModel;
import Model.CoServicePolicyModel;
import Util.FileOperate;
import Util.UserInfo;

public class SePy_CityPolicyAddController {
	private String type = (String)Executions.getCurrent().getArg().get("type");
	private String classname = (String)Executions.getCurrent().getArg().get("classname");
	private CoAgencyBaseCityRelModel citymodel = (CoAgencyBaseCityRelModel)Executions.getCurrent().getArg().get("model");
	private List<CoServicePolicyFileModel> filelist=new ArrayList<CoServicePolicyFileModel>();
	private List<Media> mlist=new ArrayList<Media>();
	private boolean filegdvisible=false,viablerw=false;//控制已选择行的可见性
	private CoServicePolicyModel model=new CoServicePolicyModel();
	private Date sminwagedate,minwagedate,standarddate,hourwagedate;
	
	//添加文件
	@Command
	@NotifyChange({"filegdvisible","filelist"})
	public void upfile(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent ev)
	{	
		Media media=ev.getMedia();
		if(media!=null)
		{
			CoServicePolicyFileModel fl=new CoServicePolicyFileModel();
			Integer k=0;
			//检查是否已选择文件
			for(CoServicePolicyFileModel m:filelist)
			{
				if(m.getFile_title().equals(media.getName()))
				{
					k=1;
					break;
				}
			}
			if(k==0)
			{
				fl.setFile_title(media.getName());
				filelist.add(fl);
				mlist.add(media);
			}
			else
			{
				Messagebox.show("该文件已在已选列表中，不能重复选择", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		if(filelist.size()>0)
		{
			filegdvisible=true;
		}
		else
		{
			filegdvisible=false;
		}
	}
	
	//删除已选择文件
		@Command
		@NotifyChange({"filegdvisible","filelist"})
		public void del(@BindingParam("model") CoServicePolicyFileModel fmodel)
		{
			try{
				if(fmodel!=null&&filelist.size()>0)
				{
					if(filelist.contains(fmodel))
					{
						filelist.remove(fmodel);
						for(Media m:mlist)
						{
							if(m.getName().equals(fmodel.getFile_title()))
							{
								mlist.remove(m);
								break;
							}
						}
					}
				}
			}
			catch(Exception e)
			{
				System.out.println("错误："+e.getMessage());
			}
			if(filelist.size()>0)
			{
				filegdvisible=true;
			}
			else
			{
				filegdvisible=false;
			}
		}
	
	//选择类型方法
	@Command
	@NotifyChange("viablerw")
	public void changetype(@BindingParam("type") String type)
	{
		if(type!=null&&type.equals("当地工资标准"))
		{
			viablerw=true;
		}
		else
		{
			viablerw=false;
		}
	}
	
	//提交
	@Command
	public void summit(@BindingParam("gd") Grid gd,@BindingParam("win") Window win)
	{
		model.setSypo_cityid(citymodel.getId());
		model.setSypo_cabc_id(citymodel.getCabc_id());
		//先把文件上传到服务器
		String strinfo=isEmploy();
		if(strinfo=="")
		{
			//检查已选文件列表中是否还有文件类型没有选择
//			strinfo=isSelectedType(gd);
//			if(strinfo=="")
//			{
//				
//			}
//			else
//			{
//				Messagebox.show(strinfo, "提示", Messagebox.OK, Messagebox.ERROR);
//			}
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;
			String om=""+month;
			if(month<10)
			{
				om="0"+month;
			}
			String strowm=year+""+om;
			model.setOwnmonth(Integer.parseInt(strowm));
			model.setSypo_class(type);
			if(type!=null)
			{
				if(type.equals("政府政策"))
				{
					model.setSypo_city(citymodel.getCity());
				}
				else
				{
					model.setSypo_city(citymodel.getCity());
					model.setSypo_agencies(classname);
				}
			}
			String username=UserInfo.getUsername();
			model.setSypo_addname(username);
			SePy_CityPolicyOperateBll obll=new SePy_CityPolicyOperateBll();
			int id=obll.CoServicePolicyAdd(model);
			Integer l=0;
			if(id>0)
			{
				//添加政策通知文件信息
				for(int i=0;i<filelist.size();i++)
				{
					CoServicePolicyFileModel flm=filelist.get(i);
					flm.setFile_pono_id(id);
					flm.setFile_addname(username);
					l=l+obll.CoServicePolicyFileAdd(flm);
					if(l<=0)
					{
						break;
					}
				}
				if(l<filelist.size())
				{
					obll.updateCoServicePolicyFileState(id);
					obll.updateCoServicePolicyState(id);
					Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
				}
				else
				{
					//Executions.sendRedirect("/CoServicePolicy/SePy_CityPolicyAdd.zul");
					Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
					win.detach();
				}
			}
			else
			{
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show(strinfo, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//检查必填项是否已经填写
	private String isEmploy()
	{
		String str="";
		if(model.getSypo_type()==null||model.getSypo_type().equals(""))
		{
			str="请选择服务政策类型";
		}
		else if(model.getSypo_title()==null||model.getSypo_title().equals(""))
		{
			str="请填写标题";
		}
		else if(model.getSypo_content()==null||model.getSypo_content().equals(""))
		{
			str="请填写内容";
		}
		else
		{
			Integer k=savefile();
			if(k<filelist.size())
			{
				str="文件上传失败";
			}
			else
			{
				if(sminwagedate!=null)
				{
					model.setSypo_sminwagedate(DateToStr(sminwagedate));
				}
				if(minwagedate!=null)
				{
					model.setSypo_minwagedate(DateToStr(minwagedate));
				}
				if(standarddate!=null)
				{
					model.setSypo_standarddate(DateToStr(standarddate));
				}
				if(hourwagedate!=null)
				{
					model.setSypo_hourwagedate(DateToStr(hourwagedate));
				}
			}
		}
		return str;
	}
	
	//保存文件到服务器方法
		private int savefile()
		{
			Integer k=0;
			String RelativePath="CoServicePolicy/file/uploadfile/";
			String realPath=FileOperate.getAbsolutePath() +RelativePath;
			if(filelist.size()>0)
			{
				for(int i=0;i<filelist.size();i++)
				{
					CoServicePolicyFileModel ml=filelist.get(i);
					String file=realPath+ml.getFile_title();
					Media media=null;
					for(Media m:mlist)
					{
						if(m.getName().equals(ml.getFile_title()))
						{
							media=m;
							break;
						}
					}
					if(media!=null)
					{
						boolean flag=FileOperate.upload(media, RelativePath+media.getName());
						if(flag)
						{
							k=k+1;
							filelist.get(i).setFile_url(file);
						}
					}
				}
			}
			return k;
		}
	
	//检查已选文件是否已经选择了类型
		private String isSelectedType(Grid gd)
		{
			String str="";
			for(int i=0;i<gd.getRows().getChildren().size();i++)
			{
				if(gd.getCell(i, 1)!=null)
				{
					Combobox cb=(Combobox) gd.getCell(i, 1).getChildren().get(0);
					if(cb.getValue()==null||cb.getValue().equals(""))
					{
						str="选中的文件列表中还有文件类型没有选择";
						break;
					}
				}
			}
			return str;
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
	public List<CoServicePolicyFileModel> getFilelist() {
		return filelist;
	}
	public void setFilelist(List<CoServicePolicyFileModel> filelist) {
		this.filelist = filelist;
	}
	public boolean isFilegdvisible() {
		return filegdvisible;
	}
	public void setFilegdvisible(boolean filegdvisible) {
		this.filegdvisible = filegdvisible;
	}

	public boolean isViablerw() {
		return viablerw;
	}

	public void setViablerw(boolean viablerw) {
		this.viablerw = viablerw;
	}

	public CoServicePolicyModel getModel() {
		return model;
	}

	public void setModel(CoServicePolicyModel model) {
		this.model = model;
	}

	public Date getSminwagedate() {
		return sminwagedate;
	}

	public void setSminwagedate(Date sminwagedate) {
		this.sminwagedate = sminwagedate;
	}

	public Date getMinwagedate() {
		return minwagedate;
	}

	public void setMinwagedate(Date minwagedate) {
		this.minwagedate = minwagedate;
	}

	public Date getStandarddate() {
		return standarddate;
	}

	public void setStandarddate(Date standarddate) {
		this.standarddate = standarddate;
	}

	public Date getHourwagedate() {
		return hourwagedate;
	}

	public void setHourwagedate(Date hourwagedate) {
		this.hourwagedate = hourwagedate;
	}
	
}
