package Controller.EmCensus;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmCensusModel;
import bll.EmCensus.EmCensus_OperateBll;
import bll.EmCensus.EmCensus_SelectBll;

public class EmCensus_InfoUpdateController {
	String id = (String)Executions.getCurrent().getArg().get("daid");
	String tperid = (String)Executions.getCurrent().getArg().get("id");
	EmCensus_SelectBll bll=new EmCensus_SelectBll();
	List<EmCensusModel> list=bll.getEmCensusInfo(" and emhj_id="+id);
	EmCensusModel model=new EmCensusModel();

	public EmCensus_InfoUpdateController() {
		// TODO Auto-generated constructor stub
		if(list.size()>0)
		{
			model=list.get(0);
			if(model.getEmhj_taprid()==null)
			{
				model.setEmhj_taprid(Integer.parseInt(tperid));
			}
		}
	}
	
	//确认提交
	@Command
	public void submit(@BindingParam("win") Window win)
	{
		String str="";
		str=ifempty();
		if(str==null||!str.equals(""))
		{
			Messagebox.show(str+"提示", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			String sql=",emhj_type='"+model.getEmhj_type()+"',emhj_place='"+model.getEmhj_place()+"'";
			sql=sql+",emhj_in_class='"+model.getEmhj_in_class()+"',emhj_state=0";
			EmCensus_OperateBll bll=new EmCensus_OperateBll();
			String[] strs=bll.EmCensusUpdate(model, sql,0);
			if(strs[0]=="1")
			{
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show(strs[1], "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}	
	}
	
	
	//判断字段是否为空
	private String ifempty()
	{
		String str="";
		if(model.getEmhj_type()==null||model.getEmhj_type().equals(""))
		{
			str="账户类型不能为空";
		}
		else if(model.getEmhj_place()==null||model.getEmhj_place().equals(""))
		{
			str="户口所在地不能为空";
		}
		else if(model.getEmhj_in_class()==null||model.getEmhj_in_class().equals(""))
		{
			str="入户方式不能为空";
		}
		return str;
	}
	
	//入户方式下拉列表的事件
	@Command
	public void ifvisible(@BindingParam("val") final String val,@BindingParam("rw") final Row rw)
	{
		if(val.equals("随迁入户")||val=="随迁入户"||val.equals("出生入户")||val=="出生入户"||val.equals("夫妻投靠")||val=="夫妻投靠")
		{
			rw.setVisible(true);
		}
		else
		{
			rw.setVisible(false);
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EmCensusModel getModel() {
		return model;
	}

	public void setModel(EmCensusModel model) {
		this.model = model;
	}
	
}
