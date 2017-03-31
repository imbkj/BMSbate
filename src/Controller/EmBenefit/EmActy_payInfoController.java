package Controller.EmBenefit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmActySuppilerGiftInfoModel;
import Model.EmWelfareModel;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;
import bll.EmBenefit.EmBenefit_comitListBll;

public class EmActy_payInfoController {
	private List<EmWelfareModel> list = new ListModelList<>();
	private EmBenefit_comitListBll bll = new EmBenefit_comitListBll();
	private Object id =Executions.getCurrent().getArg()
			.get("daid");
	private Object tapr_id = Executions.getCurrent().getArg()
			.get("id");
	
	private EmActy_GiftInfoSelectBll bllgift=new EmActy_GiftInfoSelectBll();
	private List<EmActySuppilerGiftInfoModel> giftlist=bllgift.getEmActyGiftInfo(" and gift_id = "+id);
	private String sortid="",str="",mold="";
	private Window win = (Window) Path.getComponent("/winEmp");
	public EmActy_payInfoController()
	{
		if(giftlist.size()>0)
		{
			sortid=giftlist.get(0).getGift_sortid();
			mold=giftlist.get(0).getGift_type();
			str=" and embf_mold='"+mold+"'";
		}
		list=bll.getLists(str+" and emwf_sortid='"+sortid+"'");
	}
	
	public List<EmWelfareModel> getList() {
		return list;
	}

	public void setList(List<EmWelfareModel> list) {
		this.list = list;
	}
	

	public String getSortid() {
		return sortid;
	}

	public void setSortid(String sortid) {
		this.sortid = sortid;
	}
	
	
	//全选
	@Command("checkall")
	public void checkall(@BindingParam("a") Checkbox cka) {
		Grid gd = (Grid) win.getFellow("gdList");
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
	
	//弹出重新提交页面
	@Command
	public void addgifts(@BindingParam("url") String url,@BindingParam("winEmp") Window winEmp)
	{
		String type="";
		List<EmWelfareModel> lists=new ArrayList<EmWelfareModel>();
		Grid gd = (Grid) win.getFellow("gdList");
		String con="",con1="",flag="";
		int l=0;
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 14)!=null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 14).getChildren().get(0)
						.getChildren().get(0);
				if(ck.isChecked())
				{
					l=l+1;
					EmWelfareModel m=ck.getValue();
					if(l==1)
					{
						con=m.getEmbf_name();
						type=m.getEmbf_mold();
					}
					else
					{
						con1=m.getEmbf_name();
						if(con!=null&&con1!=null)
						{
							if(con.equals(con1)||con==con1)
							{
								flag="";
							}
							else
							{
								flag="选择的福利项目不同";
								break;
							}
						}
					}
					lists.add(m);
				}
			}else {
				return;
			}
		}
		if(flag=="")
		{
			if(lists.size()>0)
			{
				Map map=new HashMap<>();
				map.put("list", lists);
				map.put("gifttype", type);
				map.put("id", id);
				map.put("tarpid", tapr_id);
				winEmp.detach();
				Window window=(Window)Executions.createComponents(url, null, map);
				window.doModal();
				
			}
			else
			{
				Messagebox.show("请先选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("选择的福利项目不同，不能一起采购", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command
	public void summit()
	{
		EmActySuppilerGiftInfoModel m=new EmActySuppilerGiftInfoModel();
		m.setGift_id(31);
		m.setGift_tarpid(476);
		String sql=",gift_state=1";
		EmActy_GiftInfoOperateBll bl=new EmActy_GiftInfoOperateBll();
		String str[]=bl.updateEmActy_GiftInfo(m,sql,"2");
		if(str[0]=="1")
		{
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			//win.detach();
		}
		else
		{
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

}
