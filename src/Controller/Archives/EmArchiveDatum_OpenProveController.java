package Controller.Archives;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.EmArchiveDatumModel;
import Model.EmArchiveRemarkModel;
import Model.TaskProcessViewModel;
import Util.UserInfo;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;

public class EmArchiveDatum_OpenProveController extends SelectorComposer<Component>{
	@Wire
	private Textbox provetxt;
	@Wire
	private Datebox provetime;
	@Wire
	private Combobox provepeop;
	@Wire
	private Window provewin;
	
	List<String> loginlist;
	String id = (String)Executions.getCurrent().getArg().get("daid");
	String tperid =null;
	EmArchive_SelectBll bll=new EmArchive_SelectBll();
	EmArchiveRemarkModel modelr=new EmArchiveRemarkModel();
	List<TaskProcessViewModel> tlist=new ArrayList<TaskProcessViewModel>();
	TaskProcessViewModel tmodel=new TaskProcessViewModel();
	EmArchiveDatumModel frommodel=bll. getEmArchiveDatumInfo(" and eada_id="+id).get(0);
	
	// 重写组件初始化方法
	public void doAfterCompose(Component comp) throws Exception {
		if(Executions.getCurrent().getArg().get("id")!=null)
		{
			tperid=Executions.getCurrent().getArg().get("id").toString();
		}
		tlist=bll.getLastId(tperid);
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		loginlist=bll.getLoginInfo();
		if(!loginlist.isEmpty())
		{
			for(int i=0;i<loginlist.size();i++)
			{
				Comboitem item=new Comboitem();
				item.setLabel(loginlist.get(i));
				item.setParent(provepeop);
			}
		}
		if(!tlist.isEmpty())
		{
			tmodel=tlist.get(0);
		}
		provetxt.setValue(frommodel.getEada_prove());
		provepeop.setValue(frommodel.getEada_drawprovepeople());
		provetime.setValue(frommodel.getEada_drawprovedate());
	}
	
	//出具证明提交事件
	@Listen("onClick =#summit")
	public void summitData(){
		EmArchiveDatum_OperateBll bll=new EmArchiveDatum_OperateBll();
		EmArchiveDatumModel model=new EmArchiveDatumModel();
		model.setEada_id(frommodel.getEada_id());
		model.setEada_prove(provetxt.getValue());
		model.setEada_drawprovedate(provetime.getValue());
		model.setEada_drawprovepeople(provepeop.getValue());
		model.setEada_tapr_id(frommodel.getEada_tapr_id());
		model.setEada_addname(UserInfo.getUsername());
		model.setEada_type("开具证明");
	
		//判断状态，第四步——开证明
		if(tmodel.getWfno_step()==5)
		{
		String str=isEmploy();
		String[] strs=new String[5];
		if(str=="")
		{
			strs=bll.EmArchiveOpenProve(model);
			if(strs[0]=="1")
			{
				Messagebox.show(strs[1],"提示",Messagebox.OK, Messagebox.INFORMATION);
				provewin.detach();
			}
			else
			{
				Messagebox.show(strs[1],"提示",Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
		}
		}
		//第五步——结束
		else if(tmodel.getWfno_step()==6)
		{
			String sql=",eada_final=3";
			updatestate(model,sql,provewin);
		}
			
	}
	private String isEmploy()
	{
		String str="";
		if(provetxt.getValue()==""||provetxt.getValue().equals(""))
		{
			str="请输入证明内容";
		}
		else if(provetime.getValue()==null||provetime.getValue().equals(""))
		{
			str="请选择开证明时间";
		}
		else if(provepeop.getValue()==""||provepeop.getValue().equals(""))
		{
			str="请选择交接人";
		}
		return str;
	}
	
	private String[] updatestate(EmArchiveDatumModel model,String sql, Window win)
	{
		EmArchiveDatum_OperateBll bll=new EmArchiveDatum_OperateBll();
		// 新增业务数据，并返回业务表ID
		String[] str = bll.Accepted(model,sql);
		// 判断业务id是否为空
		if (str[0].equals("1")) {
			// 调用内联页方法submitDoc(Grid gd)
			Messagebox.show("提交成功", "操作提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}
		else
		{
			Messagebox.show("提交失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
		return str;
	}
	
	public List<String> getLoginlist() {
		return loginlist;
	}
	public void setLoginlist(List<String> loginlist) {
		this.loginlist = loginlist;
	}

}
