package Controller.EmHouse;

import impl.SystemControl.Data_PopedomIpml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.biff.drawing.ComboBox;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.DataPopedomService;

import bll.CoBase.CoBaseLinkMan_SelectBll;
import bll.EmHouse.EmHouseDataListBll;

import Model.CoAgencyLinkmanModel;
import Model.DownLoadFileModel;
import Model.EmbaseGDModel;
import Model.LoginModel;
import Util.DateStringChange;

public class EmHouseDataListController {

	private List<EmbaseGDModel> gdlist = new ListModelList<>();
	private List<EmbaseGDModel> ownmonthlist = new ListModelList<>();
	private List<EmbaseGDModel> clientList = new ListModelList<>();
	private List<EmbaseGDModel> tsdayList = new ListModelList<>();
	private List<EmbaseGDModel> contactList = new ListModelList<>();
	private List<EmbaseGDModel> dataList = new ListModelList<>();
	private List<DownLoadFileModel> excelList = new ListModelList<>();
	private EmbaseGDModel egd = new EmbaseGDModel();

	private boolean dis = false;

	private Window win;

	private EmHouseDataListBll bll = new EmHouseDataListBll();
	
	public List<LoginModel> assistantlist = new ArrayList<LoginModel>();
	private DataPopedomService dpService = new Data_PopedomIpml("16", "", "", "", "", "", "");

	public EmHouseDataListController() {
		egd.setOwnmonth(Integer.valueOf(DateStringChange
				.Datestringnow("yyyyMM")));
		gdlist = bll.gdList(egd);
		ownmonthlist = bll.distinctList("a.ownmonth", "a.ownmonth desc");
		clientList = bll.distinctList("coba_client client", " coba_client");
		tsdayList = bll.distinctList("cohf_tsday tsday", " cohf_tsday");
		contactList = bll.distinctList(
				"isnull(emgd_contactstate,'(空白)')  contactstate",
				"contactstate");
		dataList = bll.distinctList("isnull(emgd_clstate,'(空白)') clstate",
				"clstate");
		assistantlist=dpService.getdepLoginlist();
	}

	@Command
	@NotifyChange("gdlist")
	public void search() {
		gdlist = bll.gdList(egd);
		egd.setChecked(false);
		BindUtils.postNotifyChange(null, null, egd, "checked");
	}

	@Command
	@NotifyChange({ "dis", "egd" })
	public void updateProgress() {
		if (egd.getChange().equals("调入")) {

			dis = true;
		} else {
			dis = false;
			egd.setProgress(null);
		}
	}

	@Command
	@NotifyChange("excelList")
	public void export() {
		String type1 = "";
		String companyid = "";
		boolean b = false;
		List<EmbaseGDModel> list = new ListModelList<>();
		for (EmbaseGDModel m : gdlist) {
			if (m.getChange() != null && !m.equals("") && m.isChecked()) {
				if (type1.equals("")) {
					type1 = m.getChange();
					companyid = m.getCompanyid();
				} else if (!type1.equals(m.getChange())) {
					Messagebox.show("当前所选数据类型不一致", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
				if (!companyid.equals(m.getCompanyid())) {
					Messagebox.show("当前所选数据单位编号不一致", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}

				b = true;
				list.add(m);
			}
		}
		if (b) {
			excelList = bll.createExcel(list);
			egd.setChecked(false);
			BindUtils.postNotifyChange(null, null, egd, "checked");
			if (excelList.size() > 0) {
				Messagebox.show("文件已生成,请选择下载", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}

		} else {
			Messagebox.show("请选择需要导出的数据", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	@Command
	public void checkall(@BindingParam("a") Grid gd) {
		Integer page = gd.getActivePage();
		Integer start = page * gd.getPageSize();
		Integer end = start + gd.getPageSize();

		for (int i = 0; i < gdlist.size(); i++) {
			if (i >= start && i < end) {
				gdlist.get(i).setChecked(egd.isChecked());
				BindUtils
						.postNotifyChange(null, null, gdlist.get(i), "checked");
			} else {
				gdlist.get(i).setChecked(false);
				BindUtils
						.postNotifyChange(null, null, gdlist.get(i), "checked");
			}
		}
	}

	@Command
	public void dl(@BindingParam("a") Comboitem ci) {
		try {
			Filedownload.save(new File(ci.getValue().toString()), null);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Command
	public void remark(@BindingParam("a") EmbaseGDModel model) {
		Map map = new HashMap();
		map.put("egm", model);

		Window window = (Window) Executions.createComponents(
				"EmHouseDateRemark.zul", null, map);
		window.doModal();
		BindUtils.postNotifyChange(null, null, model, "remarkFlag");
		BindUtils.postNotifyChange(null, null, model, "remarkFlag2");
	}
	
	@Command
	public void link(@BindingParam("a") EmbaseGDModel model){
		if (model.getCosp_sing_caliname().indexOf("指定")>=0) {
			Integer cali_id = 0;
			CoBaseLinkMan_SelectBll lmBll = new CoBaseLinkMan_SelectBll();
			List<CoAgencyLinkmanModel> linklist = lmBll.getLinkmanByCid(model.getCid(),1);
			String a[] = model.getCosp_sing_caliname().split("—");
			for (int i = 0; i < linklist.size(); i++) {
				if (linklist.get(i).getCali_name().equals(a[1])) {
					cali_id = linklist.get(i).getCali_id();
				}
			}
			if (cali_id != 0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("cali_id", String.valueOf(cali_id));
				Window window = (Window) Executions.createComponents(
						"../Cobase/CoBaseLinkMan_Sel.zul", null, map);
				window.doModal();
			}
		}
	}

	@Command
	@NotifyChange({ "contactList", "dataList" })
	public void mod(@BindingParam("a") EmbaseGDModel model) {
		bll.modinfo(model);
		contactList = bll
				.distinctList("emgd_contactstate", "emgd_contactstate");
		dataList = bll.distinctList("emgd_clstate", "emgd_clstate");
	}

	@Command
	public void winDa(@BindingParam("a") Window w) {
		win = w;
	}

	public List<EmbaseGDModel> getGdlist() {
		return gdlist;
	}

	public void setGdlist(List<EmbaseGDModel> gdlist) {
		this.gdlist = gdlist;
	}

	public EmbaseGDModel getEgd() {
		return egd;
	}

	public void setEgd(EmbaseGDModel egd) {
		this.egd = egd;
	}

	public List<EmbaseGDModel> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(List<EmbaseGDModel> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

	public List<EmbaseGDModel> getClientList() {
		return clientList;
	}

	public void setClientList(List<EmbaseGDModel> clientList) {
		this.clientList = clientList;
	}

	public List<EmbaseGDModel> getTsdayList() {
		return tsdayList;
	}

	public void setTsdayList(List<EmbaseGDModel> tsdayList) {
		this.tsdayList = tsdayList;
	}

	public List<EmbaseGDModel> getContactList() {
		return contactList;
	}

	public void setContactList(List<EmbaseGDModel> contactList) {
		this.contactList = contactList;
	}

	public List<EmbaseGDModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<EmbaseGDModel> dataList) {
		this.dataList = dataList;
	}

	public boolean isDis() {
		return dis;
	}

	public void setDis(boolean dis) {
		this.dis = dis;
	}

	public List<DownLoadFileModel> getExcelList() {
		return excelList;
	}

	public void setExcelList(List<DownLoadFileModel> excelList) {
		this.excelList = excelList;
	}

	public List<LoginModel> getAssistantlist() {
		return assistantlist;
	}

	public void setAssistantlist(List<LoginModel> assistantlist) {
		this.assistantlist = assistantlist;
	}

}
