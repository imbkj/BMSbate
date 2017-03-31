package Controller.CoPolicyNotice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import bll.CoPolicyNotice.PoNo_OperateBll;
import bll.CoPolicyNotice.PoNo_SelectBll;

import Model.CoPolicyNoticeModel;
import Util.UserInfo;

public class Pono_PubInfoAddController {
	private Object pono_city = Executions.getCurrent().getArg()
			.get("pono_city");// 城市
	private Object pono_type = Executions.getCurrent().getArg()
			.get("pono_type");// 通知类型
	private Object pono_agencies = Executions.getCurrent().getArg()
			.get("pono_agencies");// 机构
	private Object ownmonth = Executions.getCurrent().getArg().get("ownmonth");// 所属月份
	private Object cpnt_type = Executions.getCurrent().getArg()
			.get("cpnt_type");// 业务类型
	private Object cpnr_data_id = Executions.getCurrent().getArg().get("id");// 业务id

	private PoNo_SelectBll bll = new PoNo_SelectBll();
	private List<CoPolicyNoticeModel> list = new ArrayList<CoPolicyNoticeModel>();

	public Pono_PubInfoAddController() {
		
		list = bll.getList(getSql());
	}

	// 选择事件
	@Command
	public void infoselect(@BindingParam("lb") Listbox lb) {
		Set<Listitem> itemlist = lb.getSelectedItems();
		PoNo_OperateBll bll = new PoNo_OperateBll();
		if (cpnr_data_id != null && !cpnr_data_id.equals("")
				&& cpnt_type != null) {
			bll.delNoticeRelation(cpnt_type.toString(),
					Integer.parseInt(cpnr_data_id.toString()));
			String username = UserInfo.getUsername();
			for (Listitem item : itemlist) {
				if (item != null) {
					CoPolicyNoticeModel m = item.getValue();
					m.setCpnr_addname(username);
					m.setCpnr_data_id(Integer.parseInt(cpnr_data_id.toString()));
					m.setCpnr_pono_id(m.getPono_id());
					m.setCpnr_type(cpnt_type.toString());
					Integer k = bll.CoPolicyNoticeRelation(m);
				}
			}
		}
	}

	// 提交:
	//业务ID：data_id，业务类型：data_type
	public Integer InfoAdd(Listbox lb,Integer data_id,String data_type) {
		Set<Listitem> itemlist = lb.getSelectedItems();
		PoNo_OperateBll bll = new PoNo_OperateBll();
		Integer k=0;
		if (data_id != null && !data_id.equals("")
				&& data_type != null) {
			bll.delNoticeRelation(data_type.toString(),
					Integer.parseInt(data_id.toString()));
			String username = UserInfo.getUsername();
			for (Listitem item : itemlist) {
				if (item != null) {
					CoPolicyNoticeModel m = item.getValue();
					m.setCpnr_addname(username);
					m.setCpnr_data_id(Integer.parseInt(data_id.toString()));
					m.setCpnr_pono_id(m.getPono_id());
					m.setCpnr_type(data_type.toString());
					k=k+ bll.CoPolicyNoticeRelation(m);
				}
			}
		}
		return k;
	}
	
	@GlobalCommand("refreshlist")
	@NotifyChange("list")
	public void refreshlist(@BindingParam("pono_city") String pono_city,
			@BindingParam("pono_agencies") String pono_agencies)
	{	
		//map.put("pono_city","");//城市
		//map.put("pono_agencies","");//机构
		//BindUtils.postGlobalCommand(null, null, "refreshlist", map);
	
		try {
			this.pono_city=pono_city;
			this.pono_agencies=pono_agencies;
			list = bll.getList(getSql());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getSql()
	{
		String sl="";
		if (pono_city != null) {
			sl = sl + " and pono_city='" + pono_city + "'";
		}
		if (pono_agencies != null) {
			sl = sl + " and pono_agencies='" + pono_agencies + "'";
		}
		if (ownmonth != null && !ownmonth.equals("")) {
			sl = sl + " and ownmonth='" + ownmonth + "'";
		}
		if (pono_type != null && !pono_type.equals("")) {
			sl = sl + " and pono_type='" + pono_type + "'";
		}
		return sl;
	}

	// 打开详细页面
	@Command
	public void detail(@BindingParam("model") CoPolicyNoticeModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"../CoPolicyNotice/Pono_DetailInfo.zul", null, map);
		window.doModal();
	}

	public List<CoPolicyNoticeModel> getList() {
		return list;
	}

	public void setList(List<CoPolicyNoticeModel> list) {
		this.list = list;
	}

}
