package Controller.Embase;

import impl.SystemControl.Data_PopedomIpml;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.DataPopedomService;

import bll.CoBase.CoBaseLinkMan_SelectBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;
import bll.Embase.EmBase_gdBll;

import Model.CoAgencyLinkmanModel;
import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmBodyCheckModel;
import Model.EmbaseGDModel;
import Model.LoginModel;

public class EmBase_BcListController {
	private List<EmbaseGDModel> gdlist = new ListModelList<>();
	private List<EmbaseGDModel> ownmonthlist = new ListModelList<>();
	private List<EmbaseGDModel> clientlist = new ListModelList<>();
	private List<EmBcSetupModel> hospitallist = new ListModelList<>();
	private List<EmBcSetupAddressModel> addresslist = new ListModelList<>();

	private EmbaseGDModel em = new EmbaseGDModel();
	private EmBase_gdBll bll = new EmBase_gdBll();
	private Window win;

	public List<LoginModel> assistantlist = new ArrayList<LoginModel>();
	private DataPopedomService dpService = new Data_PopedomIpml("16", "", "", "", "", "", "");
	
	public EmBase_BcListController() {
		em.setDeclareName("待确认");
		ownmonthlist = bll.getbcOwnmonthlist();
		clientlist = bll.getbcClientlist();
		hospitallist = bll.getbcHospitalList();

		gdlist = bll.getbclist(em);
		assistantlist=dpService.getdepLoginlist();
	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		win = w;
	}

	// 打开信息详情
	@Command
	@NotifyChange("gdlist")
	public void checkInfo(@BindingParam("a") EmbaseGDModel em) {
		String URL = "/EmBodyCheck/Embc_InfoConfirm.zul";
		Map map = new HashMap<>();
		map.put("daid", em.getDataId());
		map.put("id", em.getTaprId());
		Window window;
		window = (Window) Executions.createComponents(URL, null, map);
		window.doModal();
		search();
	}

	@Command
	public void addreamrk() {
		
	}

	// 打开联系人页面
	@Command
	public void lookinfo(@BindingParam("model") EmbaseGDModel model) {
		if (model.getCosp_bcrp_bclinkname() != null
				&& model.getCosp_bcrp_bclinkname().contains("联系人")) {
			String a[] = model.getCosp_bcrp_bclinkname().split("—");
			if (a.length > 1) {
				Integer cali_id = 0;
				CoBaseLinkMan_SelectBll lmBll = new CoBaseLinkMan_SelectBll();
				List<CoAgencyLinkmanModel> linklist = lmBll
						.getLinkmanByCid(model.getCid(),1);
				for (int i = 0; i < linklist.size(); i++) {
					if (linklist.get(i).getCali_name() != null
							&& linklist.get(i).getCali_name().equals(a[1])) {
						cali_id = linklist.get(i).getCali_id();
					}
				}
				if (cali_id != 0) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("cali_id", String.valueOf(cali_id));
					Window window = (Window) Executions.createComponents(
							"../CoBase/CoBaseLinkMan_Sel.zul", null, map);
					window.doModal();
				}
			}
		}
	}

	// 打开联系人页面
	@Command
	public void lookinfos(@BindingParam("model") EmbaseGDModel model) {
		if (model.getCosp_bcrp_caliname() != null
				&& model.getCosp_bcrp_caliname().contains("联系人")) {
			String a[] = model.getCosp_bcrp_caliname().split("—");
			if (a.length > 1) {
				Integer cali_id = 0;
				CoBaseLinkMan_SelectBll lmBll = new CoBaseLinkMan_SelectBll();
				List<CoAgencyLinkmanModel> linklist = lmBll
						.getLinkmanByCid(model.getCid(),1);
				for (int i = 0; i < linklist.size(); i++) {
					if (linklist.get(i).getCali_name() != null
							&& linklist.get(i).getCali_name().equals(a[1])) {
						cali_id = linklist.get(i).getCali_id();
					}
				}
				if (cali_id != 0) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("cali_id", String.valueOf(cali_id));
					Window window = (Window) Executions.createComponents(
							"../CoBase/CoBaseLinkMan_Sel.zul", null, map);
					window.doModal();
				}
			}
		}
	}

	@Command
	@NotifyChange("gdlist")
	public void signAll(@BindingParam("gd") Grid gd) {
		List<EmbaseGDModel> checkedlist = new ArrayList<EmbaseGDModel>();
		String strid = "";
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 21) != null) {
				Cell cell = (Cell) gd.getCell(i, 21);
				Checkbox ck = (Checkbox) cell.getChildren().get(0);
				if (ck.isChecked()) {
					EmbaseGDModel m = ck.getValue();
					checkedlist.add(m);
					strid = strid + m.getEbcl_id() + ",";
				}
			}
		}
		if (checkedlist.size() <= 0) {
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (strid.length() > 0) {
			strid = strid.substring(0, strid.length() - 1);
			EmBcInfo_SelectBll sbll = new EmBcInfo_SelectBll();
			Integer flag = sbll.isSameChecked(strid);
			if (flag > 1) {
				Messagebox.show("选择的数据状态不一样，不能做批量处理", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (flag == -1 || flag.equals(-1)) {
				Messagebox.show("选择的数据状态不是已体检，不能做批量处理", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}
		Map map = new HashMap<>();
		map.put("list", checkedlist);
		Window window = (Window) Executions.createComponents(
				"../EmBodyCheck/EmBc_ClientSignAll.zul", null, map);
		window.doModal();
		search();
	}

	@Command
	@NotifyChange("gdlist")
	public void search() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (em.getAddtime2() != null) {
			if (!em.getAddtime2().equals("")) {
				em.setAddtime(sdf.format(em.getAddtime2()));
			} else {
				em.setAddtime(null);
			}
		} else {
			em.setAddtime(null);
		}
		if (em.getBookdate2() != null) {
			if (!em.getBookdate2().equals("")) {
				em.setBookdate(sdf.format(em.getBookdate2()));
			} else {
				em.setBookdate(null);
			}
		} else {
			em.setBookdate(null);
		}
		if (em.getIfAddress2() != null && !em.getIfAddress2().equals("")) {
			if (em.getIfAddress2().equals("是")) {
				em.setIfAddress(true);
			} else {
				em.setIfAddress(false);
			}
		} else {
			em.setIfAddress(null);
		}
		if (em.getIfbookdate2() != null && !em.getIfbookdate2().equals("")) {
			if (em.getIfbookdate2().equals("是")) {
				em.setIfbookdate(true);
			} else {
				em.setIfbookdate(false);
			}
		} else {
			em.setIfbookdate(null);
		}
		if (em.getIfFile2() != null && !em.getIfFile2().equals("")) {
			if (em.getIfFile2().equals("是")) {
				em.setIfFile(true);
			} else {
				em.setIfFile(false);
			}
		} else {
			em.setIfFile(null);
		}

		gdlist = bll.getbclist(em);
	}

	@Command
	@NotifyChange({ "gdlist", "addresslist" })
	public void updateAddress(@BindingParam("a") Combobox cb) {
		if (cb.getSelectedItem() != null) {
			EmBcSetupModel m = cb.getSelectedItem().getValue();
			addresslist = bll.getbcAddressList(m.getEbcs_id());
		}

	}

	// 打开短信页面
	@Command
	public void openmobile(@BindingParam("a") EmbaseGDModel m) {
		Map map = new HashMap<>();
		System.out.print(m.getEmba_mobile());
		map.put("mobile", m.getEmba_mobile());
		// map.put("gid", m.getGid());
		Window window;
		window = (Window) Executions.createComponents("../Embase/SMS_Send.zul",
				null, map);
		window.doModal();
	}

	@Command
	@NotifyChange("gdlist")
	public void checkAll(@BindingParam("a") Checkbox cb) {
		Integer n = gdlist.size();
		Integer s = 0;
		Grid gd = (Grid) win.getFellow("gd");
		if (gdlist.size() > gd.getPageSize()) {
			s = gd.getActivePage() * gd.getPageSize();
			n = (gd.getActivePage() + 1) * gd.getPageSize();
		}
		if ((gd.getActivePage() + 1) == gd.getPageCount()) {
			if (n > gdlist.size()) {
				n = gdlist.size();
			}
		} else if (gdlist.size() < gd.getPageSize()) {
			n = gdlist.size();
		}

		for (int i = 0; i < gdlist.size(); i++) {
			if (i < s || i >= n) {
				gdlist.get(i).setChecked(false);
			} else {
				gdlist.get(i).setChecked(cb.isChecked());
			}

		}

	}

	// 签收报告
	@Command
	@NotifyChange("gdlist")
	public void checkreport(@BindingParam("a") EmbaseGDModel em) {
		Map map = new HashMap<>();
		System.out.println(em.getTaprId());
		System.out.println(em.getDataId());
		map.put("id", em.getTaprId());
		map.put("daid", em.getDataId());
		Window window;
		window = (Window) Executions.createComponents(
				"/EmbodyCheck/Embc_SignRepart.zul", null, map);
		window.doModal();
		search();
	}

	// 更新状态
	@Command
	public void mod(@BindingParam("a") EmbaseGDModel em) {
		if (!bll.modinfo(em)) {
			Messagebox.show("更新失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 导出介绍信
	@Command
	public void doDoc(@BindingParam("gd") Grid gd) {
		EmbaseGDModel ml = new EmbaseGDModel();
		Integer k = 0;
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 21) != null) {
				Checkbox cb = (Checkbox) gd.getCell(i, 21).getChildren().get(0);
				if (cb.isChecked()) {
					k++;
					ml = cb.getValue();
				}
			}
		}

		if (k == 0) {
			Messagebox.show("请选择一个员工", "提示", Messagebox.OK, Messagebox.ERROR);
		} else if (k > 1) {
			Messagebox
					.show("一次只能选择一个员工", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			EmBcInfo_SelectBll sbll = new EmBcInfo_SelectBll();
			List<EmBodyCheckModel> blist = sbll
					.getEmBodyCheckInfo(" and ebcl_id=" + ml.getEbcl_id());
			EmBodyCheckModel m = new EmBodyCheckModel();
			if (blist.size() > 0) {
				m = blist.get(0);
			} else {
				Messagebox.show("请选择一个员工", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			// 查询是否有介绍信
			if (m.getEbsa_doc()!=null && !m.getEbsa_doc().equals("")) {
				Map map = new HashMap<>();
				map.put("model", m);
				Window window = (Window) Executions.createComponents(
						"../EmBodyCheck/Embc_OutDoc.zul", null, map);
				window.doModal();
			} else {
				Messagebox.show("该员工选择的医院地址没有介绍信模板", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		}
	}

	public List<EmbaseGDModel> getGdlist() {
		return gdlist;
	}

	public void setGdlist(List<EmbaseGDModel> gdlist) {
		this.gdlist = gdlist;
	}

	public EmbaseGDModel getEm() {
		return em;
	}

	public void setEm(EmbaseGDModel em) {
		this.em = em;
	}

	public List<EmbaseGDModel> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(List<EmbaseGDModel> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

	public List<EmbaseGDModel> getClientlist() {
		return clientlist;
	}

	public void setClientlist(List<EmbaseGDModel> clientlist) {
		this.clientlist = clientlist;
	}

	public List<EmBcSetupModel> getHospitallist() {
		return hospitallist;
	}

	public void setHospitallist(List<EmBcSetupModel> hospitallist) {
		this.hospitallist = hospitallist;
	}

	public List<EmBcSetupAddressModel> getAddresslist() {
		return addresslist;
	}

	public void setAddresslist(List<EmBcSetupAddressModel> addresslist) {
		this.addresslist = addresslist;
	}

	public List<LoginModel> getAssistantlist() {
		return assistantlist;
	}

	public void setAssistantlist(List<LoginModel> assistantlist) {
		this.assistantlist = assistantlist;
	}

}
