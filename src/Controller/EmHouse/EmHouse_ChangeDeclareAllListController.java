package Controller.EmHouse;

import impl.WorkflowCore.WfOperateImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import bll.EmHouse.EmHouseChangeGjjConfirmImpl;
import bll.EmHouse.EmHouse_ChangeGjjBll;
import Model.EmHouseChangeGJJModel;
import Util.UserInfo;

public class EmHouse_ChangeDeclareAllListController {
	List<EmHouseChangeGJJModel> list = (List<EmHouseChangeGJJModel>)Executions.getCurrent().getArg().get("listmodel");

	//全选
	@Command
	public void radioSelectAll(@BindingParam("r") String selectval,@BindingParam("declarelist") Grid declarelist)
	{	int j=0;
		if(selectval.equals("0")||selectval=="0")
		{
			j=0;
		}
		else if(selectval.equals("2")||selectval=="2")
		{
			j=1;
		}
		else if(selectval.equals("1")||selectval=="1")
		{
			j=2;
		}
		else if(selectval.equals("3")||selectval=="3")
		{
			j=3;
		}
		for(int i=0;i<list.size();i++)
		{
			Label label = (Label) declarelist.getCell(i,2).getChildren().get(0);
			String id=label.getValue();
			Radiogroup rg=(Radiogroup) declarelist.getCell(i,4).getChildren().get(0);
			rg.setSelectedIndex(j);
		}
	}
	
	@Command
	public void declareAll(@BindingParam("declarelist") Grid declarelist,
			@BindingParam("windeclareall") Window windeclareall)
	{
		String date = getStringDate(new Date());
		EmHouse_ChangeGjjBll bll=new EmHouse_ChangeGjjBll();
		int k=0;
		for(int i=0;i<list.size();i++)
		{
			int y=0;
			Label label = (Label) declarelist.getCell(i,2).getChildren().get(0);
			String id=label.getValue();
			Radiogroup rg=(Radiogroup) declarelist.getCell(i,4).getChildren().get(0);
			String declareid=rg.getSelectedItem().getValue();
			WfBusinessService wfbs = new EmHouseChangeGjjConfirmImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			
			if (list.get(i).getEhcg_ifdeclare().equals(0)) {
				if (declareid.equals("1")) {
					list.get(i).setEhcg_declareName(UserInfo.getUsername());
					list.get(i).setEhcg_ifdeclare(1);
					Object[] obj = { "申报数据", list.get(i) };
					wfs.OverTask(obj, list.get(i).getEhcg_tapr_id(), UserInfo.getUsername(), "");
				}else if (declareid.equals("2")) {
					list.get(i).setEhcg_ifdeclare(1);
					Object[] obj = { "申报数据", list.get(i) };
					wfs.SkipToNext(obj, list.get(i).getEhcg_tapr_id(), UserInfo.getUsername(), "", 0, "");
					
				}

			}else if (list.get(i).getEhcg_ifdeclare().equals(1)) {
				if (declareid.equals("0")) {
					list.get(i).setEhcg_ifdeclare(0);
					Object[] obj = { "退回数据", list.get(i) };
					wfs.ReturnToN(obj, list.get(i).getEhcg_tapr_id(), 3, UserInfo.getUsername());
				}else if (declareid.equals("2")) {
					list.get(i).setEhcg_ifdeclare(2);
					Object[] obj = { "退回数据", list.get(i) };
					wfs.ReturnToPrev(obj, list.get(i).getEhcg_tapr_id(), UserInfo.getUsername(), "");
				}
			
			}else if (list.get(i).getEhcg_ifdeclare().equals(2)) {
				if (declareid.equals("0")) {
					list.get(i).setEhcg_ifdeclare(0);
					Object[] obj = { "退回数据", list.get(i) };
					wfs.ReturnToPrev(obj, list.get(i).getEhcg_tapr_id(), UserInfo.getUsername(), "");
				}else if (declareid.equals("1")) {
					list.get(i).setEhcg_declareName(UserInfo.getUsername());
					list.get(i).setEhcg_ifdeclare(2);
					Object[] obj = { "申报数据", list.get(i) };
					wfs.PassToNext(obj, list.get(i).getEhcg_tapr_id(), UserInfo.getUsername(), "", 0, "");
				}
				
			}

			y=bll.UpdateEmHouse_ChangeGjjInfo(id, declareid);
			k=k+y;
		}
		if (k > 0) {
			Messagebox.show("申报成功", "提示",
					Messagebox.OK,
					Messagebox.INFORMATION);
			windeclareall.detach();
		} else {
			Messagebox.show("申报失败", "提示",
				Messagebox.OK,
				Messagebox.ERROR);
		}

}
	
	public List<EmHouseChangeGJJModel> getList() {
		return list;
	}

	public void setList(List<EmHouseChangeGJJModel> list) {
		this.list = list;
	}
	public static String getStringDate(Date d) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(d);
		return dateString;
	}
}
