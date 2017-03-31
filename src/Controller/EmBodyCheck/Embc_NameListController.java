package Controller.EmBodyCheck;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import Model.EmBcItemGroupModel;
import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmBodyCheckCommitModel;
import Model.EmBodyCheckItemModel;
import Model.loginroleModel;
import Util.DateUtil;
import Util.UserInfo;
import bll.EmBodyCheck.EmBcInfo_OperateBll;
import bll.EmBodyCheck.EmBc_CommitSelectBll;

public class Embc_NameListController {

	private List<EmBodyCheckCommitModel> list = new ListModelList<>();
	// 客服信息列表
	private List<loginroleModel> clientlist = new ListModelList<>();
	private List<EmBcItemGroupModel> groupList = new ListModelList<>();

	// 体检组合
	private List<EmBcItemGroupModel> CubeList = new ListModelList<>();

	// 体检医院
	private List<EmBcSetupModel> suList = new ListModelList<>();
	// 医院地址
	private List<EmBcSetupAddressModel> saList = new ListModelList<>();
	private EmBc_CommitSelectBll bll = new EmBc_CommitSelectBll();
	private EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();
	// 所属月份
	private List<EmBodyCheckCommitModel> ownmonthlist = new ListModelList<>();

	private Window win = (Window) Path.getComponent("/winYearCheck");

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private EmBodyCheckCommitModel ebm = new EmBodyCheckCommitModel();
	private boolean btnAdd = true;

	public Embc_NameListController() {
		setClientlist(null);
		if (clientlist.size() > 0) {
			ebm.setCoba_client(clientlist.get(0).getLog_name());
		}
		// setList();
		setSuList();
		list = bll.getEmBodyCheckCommitInfo(ebm);
		ownmonthlist = bll.ownmonthlist();
	}

	@Command
	@NotifyChange({ "saList", "cubeList" })
	public void getUpdateSAList(@BindingParam("a") Combobox cb) {
		if (cb.getSelectedItem() != null
				&& !cb.getSelectedItem().getValue().equals("")) {
			btnAdd = false;
			setSaList(Integer.valueOf(cb.getSelectedItem().getValue()
					.toString()));

			Combobox cbItem = (Combobox) win.getFellow("cubeItem");
			Combobox cbAddress = (Combobox) win.getFellow("address");

			cbAddress.setValue("");
			cbItem.setValue("");
			for (EmBodyCheckCommitModel m : list) {
				updateItem(m, 0);
			}
			SearchCube();
			btnAdd = true;
		}

	}

	@Command
	@NotifyChange("list")
	public void modList() {
		Grid gd = (Grid) win.getFellow("gd");
		Combobox cbHospital = (Combobox) win.getFellow("hospital");
		Combobox cbAddress = (Combobox) win.getFellow("address");
		Combobox cbCube = (Combobox) win.getFellow("cubeItem");
		Datebox dbBookdate = (Datebox) win.getFellow("bookdate");
		Checkbox ckb = (Checkbox) win.getFellow("allcheck");
		if (list.size() > 0) {
			Integer start = 0;
			Integer end = list.size();
			for (int i = start; i < end; i++) {
				if (list.get(i).isChecked()) {
					if (cbHospital.getSelectedItem() != null) {
						if (list.get(i).getEbcc_hospital() != null) {
							if (!cbHospital.getSelectedItem().getValue()
									.equals(list.get(i).getEbcc_hospital())) {
								list.get(i).setEbcc_groupid(0);
								list.get(i).setEbcc_itemid(0);
							}
						}

						list.get(i).setEbcc_hospital(
								(Integer) cbHospital.getSelectedItem()
										.getValue());

					}

					if (cbAddress.getSelectedItem() != null) {
						list.get(i).setEbcc_address(
								(Integer) cbAddress.getSelectedItem()
										.getValue());
					} else {
						if (cbHospital.getSelectedItem() != null) {
							list.get(i).setEbcc_address(0);
						}
					}
					if (dbBookdate.getValue() != null) {
						list.get(i).setEbcc_bookdate(
								sdf.format(dbBookdate.getValue()));
					}

					if (!list.get(i).isLock()) {

						if (cbCube.getSelectedItem() != null) {
							EmBcItemGroupModel m = cbCube.getSelectedItem()
									.getValue();
							list.get(i).setEbcc_groupid(m.getEbig_id());
							list.get(i).setEbcc_itemid(m.getEigl_ebit_id());
						}
					}

					bll.modCommitInfo(list.get(i));
				}
			}
		}
		ckb.setChecked(false);

		setList(ebm);
	}

	@Command
	@NotifyChange({ "list", "cubeList" })
	public void search() {

		if ((ebm.getCoba_company() != null && !ebm.getCoba_company().equals(""))
				|| (ebm.getEmba_name() != null && !ebm.getEmba_name()
						.equals(""))
				|| (ebm.getCoba_client() != null && !ebm.getCoba_client()
						.equals(""))) {

			list = bll.getEmBodyCheckCommitInfo(ebm);
			Combobox cbItem = (Combobox) win.getFellow("cubeItem");

			cbItem.setValue("");
			SearchCube();

		} else {
			Messagebox.show("请选择搜索条件.", "提示", Messagebox.OK, Messagebox.ERROR);

		}

	}

	@Command
	@NotifyChange("cubeList")
	public void updateItem(@BindingParam("a") EmBodyCheckCommitModel em,
			@BindingParam("b") Integer c) {
		if (em.isChecked()) {

			Combobox cbHospital = (Combobox) win.getFellow("hospital");
			if (cbHospital.getValue() != null
					&& !cbHospital.getValue().equals("")) {
				List<EmBodyCheckItemModel> empList = bll.getbcItem(
						em.getGid(),
						em.getEmba_sex(),
						em.getEmba_marital(),
						Integer.valueOf(cbHospital.getSelectedItem().getValue()
								.toString()));
				if (empList.size() > 0) {
					em.setEbcc_itemid(empList.get(0).getEbit_id());
					em.setItems(empList.get(0).getEbit_name());
					em.setLock(true);
					BindUtils.postNotifyChange(null, null, em, "items");
				}

			}
		} else {
			em.setEbcc_itemid(null);
			em.setItems(null);
			em.setLock(false);
			BindUtils.postNotifyChange(null, null, em, "items");
		}
		if (c != null && c.equals(1)) {
			SearchCube();
		}
	}

	// 全选
	@Command
	public void checkall(@BindingParam("ck") Checkbox ck) {
		btnAdd = false;
		for (EmBodyCheckCommitModel m : list) {
			m.setChecked(ck.isChecked());
			updateItem(m, 0);
			BindUtils.postNotifyChange(null, null, m, "checked");
		}

		SearchCube();
		btnAdd = true;
	}

	@Command
	public void getItemList(@BindingParam("a") EmBodyCheckCommitModel em,
			@BindingParam("b") Combobox cb) {

		if (em.getEbcc_hospital() != null && !em.getEbcc_hospital().equals("")) {
			setGroupList(em.getEbcc_hospital(), em.getCid());
		} else {
			groupList = null;
		}

		em.setList(groupList);
		BindUtils.postNotifyChange(null, null, em, "list");

	}

	@Command
	public void modItem(@BindingParam("a") Combobox cb,
			@BindingParam("b") EmBodyCheckCommitModel em) {

		EmBcItemGroupModel egm = new EmBcItemGroupModel();
		EmBodyCheckCommitModel ecm = new EmBodyCheckCommitModel();
		if (cb.getSelectedItem() != null) {
			egm = cb.getSelectedItem().getValue();
		}
		if (egm.getEbig_id() != null) {
			ecm.setEbcc_groupid(egm.getEbig_id());
			ecm.setEbcc_itemid(0);
		}
		if (egm.getEigl_ebit_id() != null) {
			ecm.setEbcc_itemid(egm.getEigl_ebit_id());
			ecm.setEbcc_groupid(0);
		}

		ecm.setEbcc_id(em.getEbcc_id());
		bll.modCommitInfo(ecm);
		// setList();

	}

	// 搜索组合
	@Command
	@NotifyChange("cubeList")
	public void SearchCube() {
		Combobox cbHospital = (Combobox) win.getFellow("hospital");
		Integer cid = null;
		for (EmBodyCheckCommitModel m : list) {
			if (m.isChecked()) {
				if (cid == null) {
					cid = m.getCid();
				} else if (!m.getCid().equals(cid)) {
					cid = null;
					break;
				}

			}
		}

		if (cbHospital.getSelectedItem() != null) {
			
			CubeList = bll.cubeList(
					ebm.getCoba_company(),
					cid,
					ebm.getEmba_name(),
					ebm.getCoba_client(),
					Integer.valueOf(cbHospital.getSelectedItem().getValue()
							.toString()));
			CubeList.add(null);
		}
	}

	@Command
	@NotifyChange("list")
	public void addall() {
		if (btnAdd) {

			Grid gd = (Grid) win.getFellow("gd");
			boolean b = true;
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (EmBodyCheckCommitModel em : list) {

				if (em.isChecked()) {

					if (em.getEbcc_bookdate() != null
							&& !em.getEbcc_bookdate().equals("")) {
						Date bookdate = new Date();
						try {
							bookdate = sdf.parse(em.getEbcc_bookdate());
						} catch (Exception e) { // TODO Auto-generated
														// catch block
							e.printStackTrace();
						}
						if (UserInfo.getDepID() != "8") {
							if (DateUtil.datediff(DateUtil.getDate(d, 4),
									bookdate, "d") < 0) {
								Messagebox.show("请选择4个工作日以后的日期.", "提示",
										Messagebox.OK, Messagebox.ERROR);
								return;
							}
						}
						if (em.getEbcc_bookdate() != null) {
							if (em.getWk() != null && em.getWk().equals(0)) {
								Messagebox.show(
										em.getEmba_name() + ","
												+ em.getAddress() + "("
												+ em.getEbcc_bookdate()
												+ ")当日休息,请重新选择.", "提示",
										Messagebox.OK, Messagebox.ERROR);
								return;
							}
						}
					}

					if (em.getEbcc_hospital() == null
							|| (em.getEbcc_itemid() == null && em
									.getEbcc_groupid() == null)) {
						Messagebox.show("数据未录入完整", "提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
					if (!bll.linkInfo(em.getCid())) {
						Messagebox.show(em.getCoba_company()
								+ "未录入操作约定中的体检联系信息.", "提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}

					String[] str = obll.EmBodyCheckAdd2(em);
					if (!str[0].equals("1")) {

						b = false;
						Messagebox.show(em.getEmba_name() + "录入失败", "提示",
								Messagebox.OK, Messagebox.ERROR);
						return;
					}

				}
			}

			if (b) {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				setList(ebm);
			} else {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}

		} else {
			System.out.println("***");
		}
	}

	@Command
	@NotifyChange("list")
	public void delList() {
		Messagebox.show("确认删除数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							if (list.size() > 0) {
								
								Integer start = 0;
								Integer end = list.size();
								for (int i = start; i < end; i++) {
									if (list.get(i).isChecked()) {
										bll.del(list.get(i).getEbcc_id());
									}
								}
								setList(ebm);
							}

						}
					}
				});

	}

	public List<EmBodyCheckCommitModel> getList() {
		return list;
	}

	public void setList(EmBodyCheckCommitModel em) {
		this.list = bll.getEmBodyCheckCommitInfo(em);
	}

	public List<EmBcSetupModel> getSuList() {
		return suList;
	}

	public void setSuList() {
		EmBcSetupModel ebsm = new EmBcSetupModel();
		ebsm.setEbcs_state(1);
		this.suList = bll.getSUList(ebsm);
	}

	public List<EmBcSetupAddressModel> getSaList() {
		return saList;
	}

	public void setSaList(Integer hospital) {
		EmBcSetupAddressModel ebam = new EmBcSetupAddressModel();
		ebam.setEbsa_ebcs_id(hospital);
		ebam.setEbsa_state(1);
		this.saList = bll.getSAList(ebam);
	}

	public List<EmBcItemGroupModel> getGroupList() {
		return groupList;
	}

	public void setGroupList(Integer hospital, Integer cid) {
		this.groupList = bll.getGroupList(hospital, cid);
	}

	public List<loginroleModel> getClientlist() {
		return clientlist;
	}

	public void setClientlist(Integer userid) {
		this.clientlist = bll.clientList(userid);
	}

	public EmBodyCheckCommitModel getEbm() {
		return ebm;
	}

	public void setEbm(EmBodyCheckCommitModel ebm) {
		this.ebm = ebm;
	}

	public List<EmBcItemGroupModel> getCubeList() {
		return CubeList;
	}

	public void setCubeList(List<EmBcItemGroupModel> cubeList) {
		CubeList = cubeList;
	}

	public List<EmBodyCheckCommitModel> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(List<EmBodyCheckCommitModel> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

}
