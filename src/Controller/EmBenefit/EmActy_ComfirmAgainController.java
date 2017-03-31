package Controller.EmBenefit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

import Model.EmActySuppilerGiftInfoModel;
import Model.EmWelfareModel;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;
import bll.EmBenefit.EmBenefit_comitListBll;

public class EmActy_ComfirmAgainController {
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private List<EmWelfareModel> list = new ListModelList<>();
	private EmBenefit_comitListBll bll = new EmBenefit_comitListBll();
	
	private EmActy_GiftInfoSelectBll blls=new EmActy_GiftInfoSelectBll();
	private List<EmActySuppilerGiftInfoModel> lists=blls.getEmActyGiftInfo(" and gift_id="+id);
	private Window win = (Window) Path.getComponent("/winEmp");
	private String sortid="",sql="";
	
	public EmActy_ComfirmAgainController()
	{
		if(lists.size()>0)
		{
			sortid=lists.get(0).getGift_sortid();
			sql=" and emwf_state=9  and  emwf_sortid='" + sortid+"'";
			list=bll.getEmWelfareList(sql);
		}
	}
	
	//单个员工确认名单
	@Command
	@NotifyChange("list")
	public void confirm(@BindingParam("model") EmWelfareModel model)
	{
		String sqlup=",emwf_state=13";
		Integer k=updateInfo(model.getEmwf_id(),sqlup);
		if(k>0)
		{
			//Messagebox.show("确认成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			list=bll.getEmWelfareList(sql);
			if(list.size()<=0)
			{
				String[] strs=tonext();
				if(strs[0]=="1")
				{
					Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}
				else
				{
					List<Integer> li=new ArrayList<Integer>();
					li.add(model.getEmwf_id());
					updateState(li);
					list=bll.getEmWelfareList(sql);
					Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
				}
			}
			else
			{
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				list=bll.getEmWelfareList(sql);
			}
		}
		else
		{
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//批量确认名单
	@Command
	@NotifyChange("list")
	public void allconfirm(@BindingParam("gd") Grid gd)
	{
		String sqlup=",emwf_state=13";
		Integer k=0,j=0;
		List<Integer> li=new ArrayList<Integer>();
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 14)!=null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 14).getChildren().get(0)
					.getChildren().get(0);	
				j=j+1;
				EmWelfareModel model=ck.getValue();
				li.add(model.getEmwf_id());
				k=k+updateInfo(model.getEmwf_id(),sqlup);
			}else {	
				return;
			}
		}
		if(k==j)
		{
			
			list=bll.getEmWelfareList(sql);
			if(list.size()<=0)
			{
				String[] strs=tonext();
				if(strs[0]=="1")
				{
					Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}
				else
				{
					updateState(li);
					list=bll.getEmWelfareList(sql);
					Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
				}
			}
		}
		else
		{
			updateState(li);
			list=bll.getEmWelfareList(sql);
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//更新福利信息方法
	private Integer updateInfo(Integer emwf_id,String sql)
	{
		
		EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
		Integer k=0;
		k=obll.updateEmWelfareInfo(sql,emwf_id);
		return k;
	}
	
	//流程转到下一步
	private String[] tonext()
	{
		String[] str=new String[5];
		EmActySuppilerGiftInfoModel m=new EmActySuppilerGiftInfoModel();
		m.setGift_id(id);
		m.setGift_tarpid(tapr_id);
		String strsql="";
		m.setGift_remark("再次确认名单");
		EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
		str=obll.updateEmActy_GiftInfos(m, strsql,"3");
		win.detach();
		return str;
	}
	
	//把状态撤回到提交前
	private Integer updateState(List<Integer> li)
	{
		//当提交失败时把状态改回来
		String sql=",emwf_state=9";
		Integer k=0,j=0;
		for (int g = 0; g < li.size(); g++) {
			j=j+1;
			Integer id=li.get(g);
			k=k+updateInfo(id,sql);
		}
		return k;
	}
	
	//全选
	@Command("checkall")
	public void checkall(@BindingParam("a") Checkbox cka,@BindingParam("gd") Grid gd) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 14)!=null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 14).getChildren().get(0)
					.getChildren().get(0);
				ck.setChecked(cka.isChecked());		
			}else {	
				return;
			}
		}
	}
	
	//报名取消
	@Command
	@NotifyChange("list")
	public void cancel(@BindingParam("win") Window win,@BindingParam("model") final EmWelfareModel model)
	{
		Map map=new HashMap<>();
		map.put("id", model.getEmwf_id());
		Window window =(Window)Executions.createComponents("../EmBenefit/EmActy_Cancel.zul", null, map);
		window.doModal();
		list=bll.getEmWelfareList(sql);
	}

	public List<EmWelfareModel> getList() {
		return list;
	}

	public void setList(List<EmWelfareModel> list) {
		this.list = list;
	}
		
}
