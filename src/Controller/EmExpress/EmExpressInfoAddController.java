package Controller.EmExpress;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmExpressContactInfoModel;
import Model.EmExpressInfoModel;
import bll.EmExpress.EmExpressInfoOperateBll;
import bll.EmExpress.EmExpressInfoSelectBll;

public class EmExpressInfoAddController {
	private EmExpressInfoSelectBll bll=new EmExpressInfoSelectBll();
	private String flag = (String) Executions.getCurrent().getArg().get("flag");
	private Integer gid=null,cid=null;
	private List<EmExpressContactInfoModel> list=new ArrayList<EmExpressContactInfoModel>();
	private EmExpressContactInfoModel m=new EmExpressContactInfoModel();
	private EmExpressInfoModel model=new EmExpressInfoModel();
	
	public EmExpressInfoAddController()
	{
		//个人快递
		if(flag!=null&&flag.equals("emba"))
		{
			gid=(Integer)Executions.getCurrent().getArg().get("gid");
			cid=(Integer)Executions.getCurrent().getArg().get("cid");
			list=bll.getEmExpressContactInfoList(" and gid="+gid);
		}
		else
		{
			cid=(Integer)Executions.getCurrent().getArg().get("cid");
			list=bll.getEmExpressContactInfoList(" and cid="+cid+" and gid is null");
		}
	}
	
	//新增快递信息方法
	@Command
	public void submit(@BindingParam("win") Window win)
	{
		String str="";
		str=ifEmpety();
		if(!str.equals(""))
		{
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			EmExpressInfoOperateBll blla=new EmExpressInfoOperateBll();
			Integer k=0;
			//个人快递
			if(flag!=null&&flag.equals("emba"))
			{
				k=blla.addExpress(m.getExct_id(), gid,model.getExpr_content() ,
					model.getExpr_rank());
			}
			//公司快递
			else
			{
				k=blla.addCobaseExpress(m.getExct_id(), cid,model.getExpr_content() ,
						model.getExpr_rank());
			}
			if(k>0)
			{
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				//Executions.sendRedirect("EmExpressInfoAdd.zul");
				win.detach();
			}
			else
			{
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	
	//检查字段是否为空
	private String ifEmpety()
	{
		String str="";
		if(model.getExpr_content()==null||model.getExpr_content().equals(""))
		{
			str="请输入邮寄物品";
		}
		else if(m.getExct_address()==null||m.getExct_address().equals(""))
		{
			str="请选择地址";
		}
		return str;
	}
	
	public List<EmExpressContactInfoModel> getList() {
		return list;
	}
	public void setList(List<EmExpressContactInfoModel> list) {
		this.list = list;
	}

	public EmExpressContactInfoModel getM() {
		return m;
	}

	public void setM(EmExpressContactInfoModel m) {
		this.m = m;
	}

	public EmExpressInfoModel getModel() {
		return model;
	}

	public void setModel(EmExpressInfoModel model) {
		this.model = model;
	}
	
	
}
