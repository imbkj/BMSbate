package Controller.Archives;

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
import Util.UserInfo;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.CoLatencyClient.CoLatencyClient_AddBll;

public class EmArchiveDatum_PositiveController  extends SelectorComposer<Component>{
	@Wire
	private Textbox receivecom;
	@Wire
	private Datebox sendformdate;
	@Wire
	private Datebox receivedate;
	@Wire
	private Datebox takebackformdate;
	@Wire
	private Datebox positivedate;
	@Wire
	private Combobox takepeop;
	@Wire
	private Window positivewin;
	
	CoLatencyClient_AddBll loginbll=new CoLatencyClient_AddBll();
	List<String> loginlist;
	EmArchiveDatumModel frommodel = (EmArchiveDatumModel)Executions.getCurrent().getArg().get("model");
	
	// 重写组件初始化方法
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		loginlist=loginbll.getLoginInfo();
		if(!loginlist.isEmpty())
		{
			for(int i=0;i<loginlist.size();i++)
			{
				Comboitem item=new Comboitem();
				item.setLabel(loginlist.get(i));
				item.setParent(takepeop);
			}
		}
	}
	
	//转正定级提交事件
	@Listen("onClick =#summit")
	public void summitData(){
		String str=isEmpoy();
		if(str=="")
		{
			String[] strs=new String[5];
			EmArchiveDatum_OperateBll bll=new EmArchiveDatum_OperateBll();
			EmArchiveDatumModel model=new EmArchiveDatumModel();
			model.setEada_id(frommodel.getEada_id());
			model.setEada_drawformdate(sendformdate.getValue());
			model.setEada_drawformpeople(takepeop.getValue());
			model.setEada_rsetup(receivecom.getValue());
			model.setEada_rdate(receivedate.getValue());
			model.setEada_returnformdate(takebackformdate.getValue());
			model.setEada_transactdate(positivedate.getValue());
			model.setEada_addname(UserInfo.getUsername());
			EmArchiveDatum_OperateBll blls=new EmArchiveDatum_OperateBll();
			strs=blls.EmArchivePositive(model,"","","");
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.INFORMATION);
			positivewin.detach();
    	}
		else
		{
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	private String isEmpoy()
	{
		String str="";
		if(sendformdate.getValue()==null)
		{
			str="请选择发表格日期";
		}
		else if(takepeop.getValue()==null||takepeop.getValue()==""||takepeop.getValue().equals(""))
		{
			str="请选择接收人";
		}
		else if(receivecom.getValue()==null||receivecom.getValue()==""||receivecom.getValue().equals(""))
		{
			str="请填入毕业生接收单位";
		}
		else if(receivecom.getValue()==null||receivecom.getValue()==""||receivecom.getValue().equals(""))
		{
			str="请填入毕业生接收单位";
		}
		else if(receivedate.getValue()==null||receivedate.getValue().equals(""))
		{
			str="请填入毕业生接收日期";
		}
		else if(takebackformdate.getValue()==null||takebackformdate.getValue().equals(""))
		{
			str="请填入回收表格日期";
		}
		else if(positivedate.getValue()==null||positivedate.getValue().equals(""))
		{
			str="请填入转正日期";
		}
		return str;
	}
}
