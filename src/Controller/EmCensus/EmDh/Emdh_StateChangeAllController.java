package Controller.EmCensus.EmDh;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmDhModel;
import Model.TaskProcessViewModel;
import bll.Archives.EmArchive_SelectBll;
import bll.EmCensus.EmDh.EmDh_OperateBll;
import bll.EmCensus.EmDh.EmDh_SelectBll;

public class Emdh_StateChangeAllController {
	List<EmDhModel> molist = (List<EmDhModel>)Executions.getCurrent().getArg().get("molist");
	EmDhModel dmodel = (EmDhModel)Executions.getCurrent().getArg().get("model");
	List<TaskProcessViewModel> tlist=new ArrayList<TaskProcessViewModel>();
	TaskProcessViewModel tmodel=new TaskProcessViewModel();;
	EmDh_SelectBll bll=new EmDh_SelectBll();
	Date now=new Date();
	EmArchive_SelectBll abll=new EmArchive_SelectBll();
	
	String[] strs=new String[5];
	String sqls="";
	private String tittles="勾选积分入户正式材料";
	
	public Emdh_StateChangeAllController()
	{
		tlist=abll.getLastId(dmodel.getEmdh_taprid()+"");
		if(!tlist.isEmpty())
		{
			tmodel=tlist.get(0);
		}
		strs[0]="0";
	}
	
	//暂存提交按钮
	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("docGrid") Grid docGrid) {
		DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
		// 调用内联页方法chkHaveTo(Grid gd)
		try {
			String[] message = docOC.UpchkHaveTo(docGrid);
			// 判断材料必选项是否已选
			if (message[0].equals("1")) {
				for(int i=0;i<molist.size();i++)
				{
					message = docOC.UpsubmitDoc(docGrid,molist.get(i).getId()+"");
				}
				if (message[0].equals("1")) {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK, Messagebox.INFORMATION);
					win.detach();
				}
				else
				{
					Messagebox.show("提交失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
				}
			}
			else
			{
				Messagebox.show("有必选材料没有选,请选择", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	//交接材料确认提交
	@Command("submitconfirm")
	public void submitconfirm(@BindingParam("win") final Window win,
		@BindingParam("docGrid") final Grid docGrid,@BindingParam("emdh_time") final Date emdh_time) throws Exception {
		final DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
		String[] message = docOC.UpchkHaveTo(docGrid);
		if(emdh_time==null)
		{
			Messagebox.show("请选择材料交齐时间", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
		else if(message[0]!="1")
		{
			Messagebox.show("有必选材料没有选,请选择", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			     public void onEvent(ClickEvent event) throws Exception {
			           if(Messagebox.Button.YES.equals(event.getButton())) {
			            	EmDh_OperateBll opbll=new EmDh_OperateBll();
			            	String[] str=new String[5];
			            	
			            	for(int i=0;i<molist.size();i++)
							{
			            		EmDhModel m=molist.get(i); 
			            		m.setStates(tmodel.getWfno_name());
			            		String sql=",emdh_time2='"+timechange(emdh_time)+"'";
			            		str=opbll.EmDhupdate(m,sql,0);
			            		if(str[0]=="1")
			            		{
			            			docOC.UpsubmitDoc(docGrid,m.getId()+"");
			            		}
							}
			            	if (str[0].equals("1")) {
			            		// 调用内联页方法submitDoc(Grid gd)
								Messagebox.show("提交成功", "操作提示", Messagebox.OK, Messagebox.INFORMATION);
								win.detach();
							}
							else
							{
								Messagebox.show("提交失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
							}
			              }
			         }
			   };
			Messagebox.show("确认交齐材料并提交?", "提示", new Messagebox.Button[]{
			    		  Messagebox.Button.YES, Messagebox.Button.NO }, Messagebox.QUESTION, clickListener);
		}
	}
	
	//更新状态
	@Command("updatestate")
	public void updatestate(@BindingParam("win") final Window win,@BindingParam("time3") final Date time3,
			@BindingParam("dhtype") final String dhtype,@BindingParam("time4") final Date time4,
			@BindingParam("time5") final Date time5,@BindingParam("time6") final Date time6,
			@BindingParam("time8") final Date time8,@BindingParam("time9") final Date time9,
			@BindingParam("time10") final Date time10){
			final EmDh_OperateBll opbll=new EmDh_OperateBll();
        	List<TaskProcessViewModel> tlist=null;
        	for(int i=0;i<molist.size();i++)
			{
				final EmDhModel models=molist.get(i);
				models.setStates(tmodel.getWfno_name());
				String sql="";
				int k=0;
				
				//第三步
				if(tmodel.getWfno_step()==3)
				{
					if(dhtype.equals("")||dhtype=="")
					{
						strs[1]="请选择调户方式";
						break;
					}
					else
					{
						if(dhtype.equals("毕业生接收")||dhtype=="毕业生接收")
						{
							if(!models.getEmdh_dhtype().equals("毕业生接收")&&models.getEmdh_dhtype()!="毕业生接收")
							{
								sqls=",emdh_doc=20,emdh_dhtype='"+dhtype+"',emdh_state=1";
								k=1;
							}
						}
						else
						{
							if(models.getEmdh_dhtype().equals("毕业生接收")||models.getEmdh_dhtype()=="毕业生接收")
							{
								sqls=",emdh_doc=18,emdh_dhtype='"+dhtype+"',emdh_state=1";
								k=1;
							}
						}
						
						//k=1表示选择的调干方式与客服提交的不同，要退回并重新建任务单
						if(k==1)
						{
							if(i==0)
							{
							models.setEmdh_dhtype(dhtype+"");
							EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						          public void onEvent(ClickEvent event) throws Exception {
						               if(Messagebox.Button.YES.equals(event.getButton())) {
						            	   strs=opbll.EmDhupdate(models,sqls,1);
						               }
						          }
							};
							Messagebox.show("选择的调户方式和客服提交的不同，如果提交流程将退回到第一步", "提示", new Messagebox.Button[]{
						    		  Messagebox.Button.YES, Messagebox.Button.NO }, Messagebox.QUESTION, clickListener);
							}
							else
							{
								strs=opbll.EmDhupdate(models,sqls,1);
							}
						}
						else
						{
							if(time3==null)
							{
								strs[1]="请选择条件审查日期";
								break;
							}	
							else
							{
								sql=",emdh_time3='"+timechange(time3)+"',emdh_state=2";
								strs=opbll.EmDhupdate(models,sql,0);
							}
						}
					}
				}
				//第四步
				else if(tmodel.getWfno_step()==4)
				{
					if(time4==null)
					{
						strs[1]="请选择信息预审日期";
						break;
					}
					else
					{
						sql=",emdh_time4='"+timechange(time4)+"',emdh_state=3";
						strs=opbll.EmDhupdate(models,sql,0);
					}
				}
				//第五步
				else if(tmodel.getWfno_step()==5)
				{
					if(time5==null)
					{
						strs[1]="请选择预审通过日期";
						break;
					}
					else
					{
						sql=",emdh_time5='"+timechange(time5)+"',emdh_state=4";
						strs=opbll.EmDhupdate(models,sql,0);
					}
				}
				//第六步
				else if(tmodel.getWfno_step()==6)
				{
					if(time6==null)
					{
						strs[1]="请选择信息预审日期";
						break;
					}
					else
					{
						sql=",emdh_time6='"+timechange(time6)+"'";
						strs=opbll.EmDhupdate(models,sql,0);
					}
				}
				//第八步
				else if(tmodel.getWfno_step()==8)
				{
					if(time8==null)
					{
						strs[1]="请选择信息预审日期";
						break;
					}
					else
					{
						sql=",emdh_time8='"+timechange(time8)+"',emdh_state=5";
						strs=opbll.EmDhupdate(models,sql,0);
					}
				}
				
				//第九步
				else if(tmodel.getWfno_step()==9)
				{
					if(time9==null)
					{
						strs[2]="请选择调令下达日期";
						break;
					}
					else
					{
						sql=",emdh_time9='"+timechange(time9)+"',emdh_state=6";
						strs=opbll.EmDhupdate(models,sql,0);
					}
				}
				
				//第十步——结束
				else if(tmodel.getWfno_step()==10)
				{
					sql=",emdh_time10='"+timechange(time10)+"'";
					strs=opbll.EmDhupdate(models,sql,0);
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
