package Controller.EmBodyCheck;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bll.EmBodyCheck.EmbcItem_SelectBll;

import Model.EmBcItemGroupModel;
import Model.EmBodyCheckItemModel;

public class EmBc_ItemGroupInfoController {
	private List<EmBodyCheckItemModel> list = new ListModelList<>();
	
	private EmbcItem_SelectBll bll = new EmbcItem_SelectBll();
	private EmBcItemGroupModel ebgm = (EmBcItemGroupModel) Executions.getCurrent().getArg()
			.get("model");
	
	
	
	private Window win = (Window) Path.getComponent("/winInfo");
	
	public EmBc_ItemGroupInfoController(){
		EmBodyCheckItemModel em = new EmBodyCheckItemModel();
		em.setEigl_ebig_id(ebgm.getEbig_id());
		List<EmBodyCheckItemModel> itemlist = bll.getEbItemList(em);
		
		if (itemlist.size()>0) {
			String idlist ="";
			for (int i = 0; i < itemlist.size(); i++) {
				idlist=idlist+","+itemlist.get(i).getEbit_id();
			}
			idlist=idlist.substring(1);
			setList(idlist);
			getxzcontent();
		}
		
	}
	
	//合并限制内容
	private void getxzcontent()
	{
		for(EmBodyCheckItemModel m:list)
		{
			if(m.getSex()!=null)
			{
				if(!m.getSex().equals("无限制"))
				{
					m.setXzcontent(m.getSex());
				}
			}
			if(m.getMarry()!=null);
			{
				if(!m.getMarry().equals("无限制"))
				{
					if(m.getXzcontent()==null||m.getXzcontent().equals(""))
					{
						m.setXzcontent(m.getMarry());
					}
					else
					{
						m.setXzcontent(m.getXzcontent()+","+m.getMarry());
					}
				}
			}
		}
	}

	public List<EmBodyCheckItemModel> getList() {
		return list;
	}

	public void setList(String idList) {
		EmBodyCheckItemModel em = new EmBodyCheckItemModel();
		em.setIdList(idList);
		this.list = bll.getEmbcItem(em);
	}

	public EmBcItemGroupModel getEbgm() {
		return ebgm;
	}

	public void setEbgm(EmBcItemGroupModel ebgm) {
		this.ebgm = ebgm;
	}

	
	
	
}
