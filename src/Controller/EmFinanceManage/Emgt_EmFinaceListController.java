package Controller.EmFinanceManage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.EmGatheringModel;
import Model.EmbaseModel;
import bll.EmFinanceManage.emgt_selectBll;

public class Emgt_EmFinaceListController {
	private Integer emba_gid=Integer.parseInt(Executions.getCurrent().getArg().get("gid").toString());
	private emgt_selectBll bll = new emgt_selectBll();
	private List<EmGatheringModel> list = bll.getEmGatheringList(" and a.gid="+emba_gid);
	private EmbaseModel model=new EmbaseModel();
	
	public Emgt_EmFinaceListController()
	{
		List<EmbaseModel> emlist=bll.getEmbaseList(" and a.gid="+emba_gid);
		if(emlist.size()>0)
		{
			model=emlist.get(0);
		}
	}
	
	// 打开更新
	@Command
	@NotifyChange("list")
	public void openupdate(@BindingParam("model") EmGatheringModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window;
		window = (Window) Executions.createComponents("../EmFinanceManage/Emgt_Update.zul", null,
				map);
		window.doModal();
		list = bll.getEmGatheringList(" and a.gid="+emba_gid);
	}
	
	//弹出新增收款
	@Command
	@NotifyChange("list")
	public void add()
	{
		Map map = new HashMap<>();
		map.put("model",model);
		Window window;
		window = (Window) Executions.createComponents("../EmFinanceManage/Emgt_Add.zul", null,
				map);
		window.doModal();
		list = bll.getEmGatheringList(" and a.gid=" + emba_gid);
	}

	public List<EmGatheringModel> getList() {
		return list;
	}

	public void setList(List<EmGatheringModel> list) {
		this.list = list;
	}

	public EmbaseModel getModel() {
		return model;
	}

	public void setModel(EmbaseModel model) {
		this.model = model;
	}

	/**
	* 日期转换成字符串
	* @param date 
	* @return str
	*/
	public static String DateToStr(Date date) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
	   String str ="";
	   if(date!=null)
	   {
		   str = format.format(date);
	   }
	   return str;
	}
}
