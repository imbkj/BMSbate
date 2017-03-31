package Controller.EmCensus.EmDh;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmDhModel;
import Model.TaskProcessViewModel;
import bll.Archives.EmArchive_SelectBll;
import bll.EmCensus.EmDh.EmDh_OperateBll;
import bll.EmCensus.EmDh.EmDh_SelectBll;

public class Emdh_Bys_StateUpdateAllController {
	List<EmDhModel> molist = (List<EmDhModel>)Executions.getCurrent().getArg().get("molist");
	EmDhModel dmodel = (EmDhModel)Executions.getCurrent().getArg().get("model");
	List<TaskProcessViewModel> tlist=new ArrayList<TaskProcessViewModel>();
	TaskProcessViewModel tmodel=new TaskProcessViewModel();;
	EmDh_SelectBll bll=new EmDh_SelectBll();
	Date now=new Date();
	EmArchive_SelectBll abll=new EmArchive_SelectBll();
	
	String[] strs=new String[5];
	String sqls="";
	
	public Emdh_Bys_StateUpdateAllController()
	{
		tlist=abll.getLastId(dmodel.getEmdh_taprid()+"");
		if(!tlist.isEmpty())
		{
			tmodel=tlist.get(0);
		}
	}
	
	//调户申请（毕业生接收）更新状态
	@Command("updatebysstate")
	public void updatebysstate(@BindingParam("win") final Window win,
		@BindingParam("time6") final Date time6,@BindingParam("time7") final Date time7,
		@BindingParam("time8") final Date time8,@BindingParam("time9") final Date time9,
		@BindingParam("time11") final Date time11,@BindingParam("time12") final Date time12){
		final EmDh_OperateBll opbll=new EmDh_OperateBll();
        for(int i=0;i<molist.size();i++)
		{
			EmDhModel models=molist.get(i);
			models.setStates(tmodel.getWfno_name());
			String sql="";
			//第六步——表格盖章
			if(tmodel.getWfno_step()==6)
			{
				if(time6==null)
				{
					strs[1]="请选择表格盖章返还日期";
					break;
				}
				else
				{
					sql=",emdh_time6='"+timechange(time6)+"'";
					strs=opbll.EmDhupdate(models,sql,0);
				}
			}
			//第七步——上报材料
			else if(tmodel.getWfno_step()==7)
			{
				if(time7==null)
				{
					strs[1]="请选择上报材料日期";
					break;
				}
				else
				{
					sql=",emdh_time7='"+timechange(time7)+"'";
					strs=opbll.EmDhupdate(models,sql,0);
				}
			}
			//第八步——接收函下达
			else if(tmodel.getWfno_step()==8)
			{
				if(time8==null)
				{
					strs[1]="请选择接收函下达日期";
					break;
				}
				else
				{
					sql=",emdh_time8='"+timechange(time8)+"'";
					strs=opbll.EmDhupdate(models,sql,0);
				}
			}
			//第九步——员工领取接收函
			else if(tmodel.getWfno_step()==9)
			{
				if(time9==null)
				{
					strs[1]="请选择员工领取接收函日期";
					break;
				}
				else
				{
					sql=",emdh_time9='"+timechange(time9)+"'";
					strs=opbll.EmDhupdate(models,sql,0);
				}
			}
			
			//第十一步——员工报到
			else if(tmodel.getWfno_step()==11)
			{
				if(time11==null)
				{
					strs[1]="请选择员工报到日期";
					break;
				}
				else
				{
					sql=",emdh_time11='"+timechange(time11)+"',emdh_state=5";
					strs=opbll.EmDhupdate(models,sql,0);
				}
			}
			//第十二步——员工领取介绍信
			else if(tmodel.getWfno_step()==12)
			{
				if(time12==null)
				{
					strs[1]="请选择员工领取介绍信日期";
					break;
				}
				else
				{
					sql=",emdh_time12='"+timechange(time12)+"',emdh_state=6";
					strs=opbll.EmDhupdate(models,sql,0);
				}
			}
		}
        if (strs[0].equals("1")) {
			Messagebox.show("提交成功", "操作提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}
		else
		{
			Messagebox.show(strs[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
		
	public TaskProcessViewModel getTmodel() {
		return tmodel;
		}

	public void setTmodel(TaskProcessViewModel tmodel) {
		this.tmodel = tmodel;
		}

	//时间格式转换
	private java.sql.Date timechange(Date d)
	{
		java.sql.Date da=null;
		if(d!=null&&!d.equals(""))
		{
			java.sql.Date date=new java.sql.Date(d.getTime());
			da=date;
		}
		return da;
	}
}
