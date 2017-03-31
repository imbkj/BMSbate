package Controller.EmBenefit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmBenefit_comitEmListBll;
import bll.EmBenefit.EmBenefit_comitListBll;
import bll.EmBenefit.EmBenefit_commitInfoBll;

import Model.EmActyProduceModel;
import Model.EmActyProducetypeModel;
import Model.EmWelfareModel;
import Util.CobaseUtil;

public class EmBenefit_commitListController {
	public List<EmWelfareModel> cobaseList = new ListModelList<>();
	private List<String> clientList = new ListModelList<>();
	private List<String> contentList = new ListModelList<>();
	private List<String> itemList = new ListModelList<>();
	private List<EmWelfareModel> list = new ListModelList<>();
	private String statename = "";

	private EmBenefit_comitListBll bll = new EmBenefit_comitListBll();
	private EmBenefit_comitEmListBll countbll=new EmBenefit_comitEmListBll();
	private String tjstr = "",strsq="";
	// 获取统计数据
	private List<EmWelfareModel> countlist = new ArrayList<EmWelfareModel>();
	private EmBenefit_commitInfoBll pbll = new EmBenefit_commitInfoBll();
	private List<EmActyProduceModel> plist = pbll.getEmActyProduce();
	private List<EmActyProducetypeModel> ptlist = new ArrayList<EmActyProducetypeModel>();
	private EmActyProduceModel pmodel=new EmActyProduceModel();
	private EmActyProducetypeModel ptm=new EmActyProducetypeModel();
	private boolean visType=false;
	
	public EmBenefit_commitListController() {
		clientList = CobaseUtil.getClientList();
		itemList = bll.getEmbfname();
		contentList = bll.getEmwfnameList();
		list = bll.getEmWelfareList(" and (emwf_state=3 or emwf_state=11) ");
		// 获取统计数据
		countlist = countbll.getEmWelfareCount(" and (emwf_state=3 or emwf_state=11) ");
		//tjstr = bll.getWfCount(countlist);
		getWfCount();
		if (tjstr == null || tjstr.equals("")) {
			tjstr = "没有数据";
		}
	}

	// 获取统计数据
	private void getWfCount() {
		tjstr = "";
		for (EmWelfareModel countm : countlist) {
			if(countm.getEmwf_producefo()!=null&&!countm.getEmwf_producefo().equals(""))
			tjstr=tjstr+countm.getEmwf_producefo()+ "　　" ;
		}

	}

	@Command("mod")
	@NotifyChange({ "list", "tjstr" })
	public void mod(@BindingParam("a") EmWelfareModel em) {

		Map map = new HashMap();
		map.put("daid", em.getEmwf_id());
		map.put("id", em.getEmwf_tapr_id());
		Window window = (Window) Executions.createComponents(
				"EmBenefit_comitInfo.zul", null, map);
		window.doModal();
		list = bll.getEmWelfareList(" and (emwf_state=3 or emwf_state=11) ");
		tjstr = bll.getWfCount(list);
	}

	// 查询
	@Command
	@NotifyChange({ "list", "tjstr" })
	public void submit(@BindingParam("client") String client,
			@BindingParam("company") String company,
			@BindingParam("item") String item,
			@BindingParam("emwfclass") String emwfclass,
			@BindingParam("name") String name,
			@BindingParam("content") Combobox content,
			@BindingParam("pt") Combobox pt) {
		String str = "";
		strsq="";
		if (client != null && !client.equals("")) {
			str = str + " and emwf_client='" + client + "'";
		}

		if (company != null && !company.equals("")) {
			str = str + " and emwf_company like '%" + company + "%'";
		}

		if (item != null && !item.equals("")) {
			str = str + " and embf_name='" + item + "'";
		}
		if (emwfclass != null && !emwfclass.equals("")) {
			str = str + " and embf_mold='" + emwfclass + "'";
		}
		if (name != null && !name.equals("")) {
			// strcon=strcon+" and emwf_name='"+name+"'";
			str = str + " and emwf_name='" + name + "'";
		}
		if (content != null) {
			if(content.getSelectedItem()!=null)
			{
				EmActyProduceModel am=content.getSelectedItem().getValue();
				str = str + " and emwf_prod_id=" +am.getProd_id();
			}
		}
		if (pt != null) {
			if(pt.getSelectedItem()!=null)
			{
				EmActyProducetypeModel tm=pt.getSelectedItem().getValue();
				str = str + " and emwf_prty_id=" +tm.getPrty_id();
			}
		}
		if (statename != null && !statename.equals("")) {
			if (statename.equals("已确认")) {
				str = str + " and emwf_state=3";
				// strcon=strcon+" and emwf_state=3";
			} else if (statename.equals("退回")) {
				str = str + " and emwf_state=11";
				// strcon=strcon+" and emwf_state=11";
			}
		}
		list = bll.getEmWelfareList(" and (emwf_state=3 or emwf_state=11) "
				+ str);
		strsq=str;
		tjstr = bll.getWfCount(list);
	}

	@Command("checkall")
	public void checkall(@BindingParam("a") Checkbox cka,
			@BindingParam("gd") Grid gd) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 15) != null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 15).getChildren().get(0);
				ck.setChecked(cka.isChecked());

			} else {

				return;
			}

		}
	}
	
	@Command("selectPd")
	@NotifyChange({"ptlist","pmodel","ptm","visType"})
	public void selectPd(@BindingParam("a") Combobox cb,@BindingParam("pt") Combobox pt) {
		if (cb.getSelectedItem() != null) {
			pmodel=cb.getSelectedItem().getValue();
			ptlist=pmodel.getPtypeList();
			pt.setValue("");
			ptm=null;
			
			if(ptlist.size()>0)
			{
				visType=true;
			}
			else
			{
				visType=false;
			}
		}
	}
	
	@Command()
	@NotifyChange("ptm")
	public void selectPtd(@BindingParam("pt") Combobox pt) {
		if (pt.getSelectedItem() != null) {
			ptm=pt.getSelectedItem().getValue();
		}
	}

	// 福利申请
	@Command
	@NotifyChange({ "list", "tjstr" })
	public void addgift(@BindingParam("url") String url,
			@BindingParam("gd") Grid gd) {
		String type = "";
		String con = "", con1 = "", flag = "", cont;
		int l = 0;
		List<EmWelfareModel> lists = new ArrayList<EmWelfareModel>();
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 15) != null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 15).getChildren().get(0);
				if (ck.isChecked()) {
					l = l + 1;
					EmWelfareModel m = ck.getValue();
					if (l == 1) {
						con = m.getEmbf_name();
						type = m.getEmbf_mold();
						cont = m.getProductName();
					} else {
						con1 = m.getEmbf_name();
						cont = m.getProductName();
						if (con != null && con1 != null) {
							if (con.equals(con1) || con == con1) {
								flag = "";
							} else {
								flag = "选择的福利项目不同，不能一起采购";
								break;
							}
						}
					}
					lists.add(m);
				}
			}
		}
		if (flag == "") {
			if (lists.size() > 0) {
				Map map = new HashMap<>();
				map.put("list", lists);
				map.put("gifttype", type);
				map.put("con", con);
				Window window = (Window) Executions.createComponents(url, null,
						map);
				window.doModal();
				list = bll
						.getEmWelfareList(" and (emwf_state=3 or emwf_state=11) ");
				tjstr = bll.getWfCount(list);
			} else {
				Messagebox
						.show("请先选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show(flag, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//选择供应商
	@Command
	@NotifyChange({ "list", "tjstr" })
	public void selSuper(@BindingParam("gd") Grid gd)
	{
		List<EmWelfareModel> checklist = new ListModelList<>();
		String idstr = "";
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 15) != null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 15).getChildren().get(0);
				if (ck.isChecked()) {
					EmWelfareModel m = ck.getValue();
					checklist.add(m);
					if (idstr == "") {
						idstr = m.getEmwf_id() + "";
					} else {
						idstr = idstr + "," + m.getEmwf_id() + "";
					}
				}
			}
		}
		if (checklist.size() > 0) {
			Map map = new HashMap<>();
			map.put("list", checklist);
			Window window = (Window) Executions.createComponents(
					"/EmBenefit/EmBenefit_selSup.zul", null, map);
			window.doModal();
			list = bll
					.getEmWelfareList(" and (emwf_state=3 or emwf_state=11) "+strsq);
			tjstr = bll.getWfCount(list);
		} else {
			Messagebox.show("请先选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 退回已确认的名单
	@Command
	@NotifyChange({ "list", "tjstr" })
	public void back(@BindingParam("gd") Grid gd) {
		List<EmWelfareModel> checklist = new ListModelList<>();
		String idstr = "";
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 15) != null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 15).getChildren().get(0);
				if (ck.isChecked()) {
					EmWelfareModel m = ck.getValue();
					checklist.add(m);
					if (idstr == "") {
						idstr = m.getEmwf_id() + "";
					} else {
						idstr = idstr + "," + m.getEmwf_id() + "";
					}
				}
			}
		}
		if (checklist.size() > 0) {
			// 查询退回原因是否相同
			if (bll.ifBackCase(idstr)) {
				Map map = new HashMap<>();
				map.put("list", checklist);
				Window window = (Window) Executions.createComponents(
						"/EmBenefit/EmBenefit_comitback.zul", null, map);
				window.doModal();
				list = bll
						.getEmWelfareList(" and (emwf_state=3 or emwf_state=11) ");
				tjstr = bll.getWfCount(list);
			} else {
				Messagebox.show("选择的数据退回原因不同，不能批量退回", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请先选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<EmWelfareModel> getList() {
		return list;
	}

	public void setList(List<EmWelfareModel> list) {
		this.list = list;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public List<EmWelfareModel> getCobaseList() {
		return cobaseList;
	}

	public void setCobaseList(List<EmWelfareModel> cobaseList) {
		this.cobaseList = cobaseList;
	}

	public List<String> getClientList() {
		return clientList;
	}

	public void setClientList(List<String> clientList) {
		this.clientList = clientList;
	}

	public List<String> getContentList() {
		return contentList;
	}

	public void setContentList(List<String> contentList) {
		this.contentList = contentList;
	}

	public List<String> getItemList() {
		return itemList;
	}

	public void setItemList(List<String> itemList) {
		this.itemList = itemList;
	}

	public String getTjstr() {
		return tjstr;
	}

	public void setTjstr(String tjstr) {
		this.tjstr = tjstr;
	}

	public List<EmActyProduceModel> getPlist() {
		return plist;
	}

	public void setPlist(List<EmActyProduceModel> plist) {
		this.plist = plist;
	}

	public List<EmActyProducetypeModel> getPtlist() {
		return ptlist;
	}

	public void setPtlist(List<EmActyProducetypeModel> ptlist) {
		this.ptlist = ptlist;
	}

	public EmActyProduceModel getPmodel() {
		return pmodel;
	}

	public void setPmodel(EmActyProduceModel pmodel) {
		this.pmodel = pmodel;
	}

	public EmActyProducetypeModel getPtm() {
		return ptm;
	}

	public void setPtm(EmActyProducetypeModel ptm) {
		this.ptm = ptm;
	}

	public boolean isVisType() {
		return visType;
	}

	public void setVisType(boolean visType) {
		this.visType = visType;
	}
	
}
