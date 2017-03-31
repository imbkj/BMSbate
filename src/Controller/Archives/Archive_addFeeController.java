package Controller.Archives;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.Archives.Archive_FeeManagerBll;

import Controller.EmSheBaocard.newExcelImpl;
import Model.CoBaseModel;
import Model.EmArchiveModel;
import Model.EmArchivePaymentModel;
import Model.EmArchiveSetupModel;
import Util.DateStringChange;
import Util.DateUtil;
import Util.UserInfo;

public class Archive_addFeeController {
	private List<EmArchivePaymentModel> list = new ListModelList<>();
	private List<CoBaseModel> clientList = new ListModelList<>();
	private List<CoBaseModel> companyList = new ListModelList<>();
	private List<EmArchiveSetupModel> setupList = new ListModelList<>();
	private Window win = (Window) Path.getComponent("/winfeeAdd");

	private Archive_FeeManagerBll bll = new Archive_FeeManagerBll();

	private EmArchivePaymentModel eam = new EmArchivePaymentModel();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	//
	public Archive_addFeeController() {
		// EmArchivePaymentModel em = new EmArchivePaymentModel();
		eam.setEmap_state(1);
		eam.setEmap_archivetype("委托人才");
		// setList(em);
		setClientList();
		setCompanyList(null);
		setSetupList();
	}

	// 更新公司列表
	@Command
	@NotifyChange("companyList")
	public void updatecompany() {
		Combobox cb = (Combobox) win.getFellow("client");
		if (cb.getSelectedItem() != null) {
			setCompanyList(cb.getSelectedItem().getLabel());
		}
	}

	// 全选
	@Command
	public void checkall(@BindingParam("a") Checkbox cka) throws ParseException {
		Grid gd = (Grid) win.getFellow("gd");
		Integer page = gd.getActivePage();
		Integer pageSize = gd.getPageSize();
		Integer row = pageSize > gd.getRows().getChildren().size() ? gd
				.getRows().getChildren().size() : pageSize;

		for (int i = 0; i < row; i++) {
			Checkbox ck = (Checkbox) gd.getCell(page * pageSize + i, 12)
					.getChildren().get(0);
			ck.setChecked(cka.isChecked());
			list.get(page * pageSize + i).setChecked(cka.isChecked());
			if (ck.isChecked()) {
				getcharge(list.get(page * pageSize + i), ck);
			}

		}
	}

	@Command
	@NotifyChange("list")
	public void Search() {
		Combobox cbClient = (Combobox) win.getFellow("client");
		Combobox cbCompany = (Combobox) win.getFellow("company");
		Combobox cbSetup = (Combobox) win.getFellow("setup");
		Combobox cbColhj = (Combobox) win.getFellow("colhj");
		Datebox cbCD1 = (Datebox) win.getFellow("cd1");
		Datebox cbCD2 = (Datebox) win.getFellow("cd2");
		Textbox tbName = (Textbox) win.getFellow("emp");
		eam.setEmap_state(1);
		eam.setEmap_archivetype("委托人才");
		if (cbClient.getSelectedItem() != null) {
			eam.setEmap_client(cbClient.getSelectedItem().getLabel());
		} else {
			eam.setEmap_client(null);
		}

		if (cbCompany.getSelectedItem() != null) {
			eam.setCid(Integer.valueOf(cbCompany.getSelectedItem().getValue()
					.toString()));
		} else {
			eam.setCid(null);
		}

		if (cbSetup.getSelectedItem() != null) {
			eam.setEmap_fileplace(cbSetup.getSelectedItem().getLabel());
		} else {
			eam.setEmap_fileplace(null);
		}

		if (cbColhj.getSelectedItem() != null) {
			eam.setEmap_colhj(Integer.valueOf(cbColhj.getSelectedItem()
					.getValue().toString()));
		} else {
			eam.setEmap_colhj(null);
		}

		if (tbName.getValue() != null) {
			eam.setEmap_name(tbName.getValue());
		} else {
			eam.setEmap_name(null);
		}

		if (cbCD1.getValue() != null) {
			eam.setCd1(cbCD1.getValue());
		} else {
			eam.setCd1(null);
		}
		if (cbCD2.getValue() != null) {
			eam.setCd2(cbCD2.getValue());
		} else {
			eam.setCd2(null);
		}
		setList(eam);
	}
	

	/**
	 * @Title: getcharge
	 * @Description: TODO(获取续费时段并计算续费)
	 * @param em
	 * @param ck
	 * @return void 返回类型
	 * @throws
	 */
	@Command
	public void getcharge(@BindingParam("a") EmArchivePaymentModel em,
			@BindingParam("b") Object o) throws ParseException {
		Checkbox ck = null;
		if (o.getClass().equals(Checkbox.class)) {
			ck = (Checkbox) o;
		} else if (o.getClass().equals(Datebox.class)) {
			Datebox db = (Datebox) o;
			ck = (Checkbox) db.getParent().getParent().getChildren().get(12)
					.getChildren().get(0);
			em.setEmap_cdate(sdf.format(db.getValue()));
		}
		if (ck != null && ck.isChecked()) {
			Integer row = ((Row) ck.getParent().getParent()).getIndex();
			searchDate(em, row);
		}
	}

	@Command
	public void copysd(@BindingParam("a") EmArchivePaymentModel em,
			@BindingParam("b") Image img, @BindingParam("c") String type,
			@BindingParam("rowIndex") Integer row) throws WrongValueException,
			ParseException {
		Grid gd = (Grid) win.getFellow("gd");
		Integer size = 0;
		if (list.size() < gd.getPageSize()) {
			size = list.size();
		} else if ((gd.getActivePage() + 1) == gd.getPageCount()) {
			size = list.size();
		} else {
			size = (gd.getActivePage() + 1) * gd.getPageSize();
		}
		Cell cell = (Cell) img.getParent();
		row++;

		for (int i = row; i < size; i++) {
			if (type.equals("date")) {
				if (em.getEmap_cdate() != null) {
					Datebox db = (Datebox) gd.getCell(i, 8).getChildren()
							.get(0);
					db.setValue(sdf.parse(em.getEmap_cdate()));
					list.get(i).setEmap_cdate(em.getEmap_cdate());
				}
			} else if (type.equals("file")) {
				BigDecimal file = em.getEmap_file();
				list.get(i).setEmap_file(file);
				BindUtils
						.postNotifyChange(null, null, list.get(i), "emap_file");
			} else if (type.equals("hj")) {
				BigDecimal hj = em.getEmap_hj();
				list.get(i).setEmap_hj(hj);
				BindUtils.postNotifyChange(null, null, list.get(i), "emap_hj");
			} else if (type.equals("op")) {
				BigDecimal op = em.getEmap_op();
				list.get(i).setEmap_op(op);
				BindUtils.postNotifyChange(null, null, list.get(i), "emap_op");
			}
		}

	}

	public void searchDate(EmArchivePaymentModel em, Integer row)
			throws ParseException {
		Grid gd = (Grid) win.getFellow("gd");
		EmArchiveSetupModel easm = new EmArchiveSetupModel();
		easm.setEase_state(1);
		easm.setEase_name(em.getEmap_fileplace());
		List<EmArchiveSetupModel> eList = new ListModelList<>();
		eList = bll.getSetUpList(easm); // 获取计费时段
		if (eList.size() == 0) {
			easm.setEase_name("其他");
			eList = bll.getSetUpList(easm); // 搜索不到人才机构时自动匹配"其他"
		}

		if (eList.size() > 0) {
			if (em.getEmap_sdate() != null
					&& eList.get(0).getEase_payment() != null) {
				if (em.getEmap_cdate() == null) {
					Date d = DateUtil.getDateAdd(sdf.parse(em.getEmap_sdate()),
							"M", eList.get(0).getEase_payment());
					((Datebox) gd.getCell(row, 8).getChildren().get(0))
							.setValue(d);
					em.setEmap_cdate(sdf.format(d));
				}

				BigDecimal[] charge = bll.sumTotal(em, eList.get(0));
				// 更新档案费

				//if (em.getEmap_file().equals(new BigDecimal(0))) {
					((Doublebox) gd.getCell(row, 9).getChildren().get(0))
							.setValue(charge[0].doubleValue());
					em.setEmap_file(new BigDecimal(charge[0].doubleValue()));
				//}
				// 更新户口费
				//if (em.getEmap_hj().equals(new BigDecimal(0))) {
					((Doublebox) gd.getCell(row, 10).getChildren().get(0))
							.setValue(charge[1].doubleValue());
					em.setEmap_hj(new BigDecimal(charge[1].doubleValue()));
				//}
				// 更新滞纳金
				//if (em.getEmap_op().equals(new BigDecimal(0))) {
					((Doublebox) gd.getCell(row, 11).getChildren().get(0))
							.setValue(charge[2].doubleValue());
					em.setEmap_op(new BigDecimal(charge[2].doubleValue()));
				//}

			}

		}
	}

	public Date changeDate(String s) {
		Date d = null;
		if (s != null) {
			try {
				d = sdf.parse(s);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return d;
	}

	@Command
	@NotifyChange("list")
	public void submit() {
		boolean check = false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isChecked()) {
				check = true;
				break;
			}
		}
		if (check) {

			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub
							if (Messagebox.Button.OK.equals(event.getButton())) {
								boolean b = true;
								Integer z = 0;
								for (int i = 0; i < list.size(); i++) {
									if (list.get(i).isChecked()) {
										list.get(i).setEmap_addname(
												UserInfo.getUsername());
										Integer j = bll.addpayment(list.get(i));

										if (j > 0) {
											z++;
										} else {
											b = false;
										}
									}
								}
								if (b) {
									Messagebox.show("成功添加" + z + "人!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									Search();
								} else {
									Messagebox.show("操作失败!", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
								}
							}
						}
					});
		} else {
			Messagebox.show("请选择需要添加费用的员工.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public List<EmArchivePaymentModel> getList() {
		return list;
	}

	public void setList(EmArchivePaymentModel em) {
		this.list = bll.getArchiveList(em);
	}

	public List<CoBaseModel> getClientList() {
		return clientList;
	}

	public void setClientList() {
		CoBaseModel cm = new CoBaseModel();
		cm.setCoba_servicestate(1);
		this.clientList = bll.getClientList(cm);
	}

	public List<CoBaseModel> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(String client) {
		CoBaseModel cm = new CoBaseModel();
		cm.setCoba_servicestate(1);
		cm.setCoba_client(client);
		this.companyList = bll.getComapnyList(cm);
	}

	public List<EmArchiveSetupModel> getSetupList() {
		return setupList;
	}

	public void setSetupList() {
		EmArchiveSetupModel em = new EmArchiveSetupModel();
		em.setEase_state(1);
		this.setupList = bll.getSetUpList(em);
	}

}
