package Controller.EmHouse;

import impl.WorkflowCore.WfOperateImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import sun.org.mozilla.javascript.internal.regexp.SubString;
import bll.EmHouse.EmHouseDataListBll;
import bll.EmHouse.EmHouse_EditBJImpl;
import bll.EmHouse.EmHouse_EditBll;
import bll.EmHouse.EmHouse_QueryBll;
import bll.EmHouse.Emhouse_BjBll;

import Model.DownLoadFileModel;
import Model.EmHouseBJModel;
import Model.EmHouseChangeModel;
import Model.EmbaseGDModel;
import Model.LoginModel;
import Util.FileOperate;
import Util.UserInfo;

public class Emhouse_BJDeclareListController {
	private Emhouse_BjBll bll = new Emhouse_BjBll();
	private EmHouse_EditBll ebll = new EmHouse_EditBll();
	private EmHouse_QueryBll blls = new EmHouse_QueryBll();
	private List<EmHouseChangeModel> ownmonthlist = blls.getownmontList();
	String sql = (String) Executions.getCurrent().getArg().get("sql");
	private String str = " and emhb_ifdeclare in (0,1,2)";
	private List<EmHouseBJModel> emhouselist = new ArrayList<EmHouseBJModel>();
	private BigDecimal total = BigDecimal.ZERO;
	private Integer total_num = 0;
	private List<DownLoadFileModel> excelList = new ListModelList<>();
	private List<EmbaseGDModel> gdlist = new ListModelList<>();

	public Emhouse_BJDeclareListController() {
		if (sql != null) {
			str += sql;
		}
		emhouselist = bll.getEmhouseBjInfo(str);
		SumTotal();
	}

	// 根据list计算补缴总金额
	private void SumTotal() {
		total = BigDecimal.ZERO;
		total_num = emhouselist.size();
		for (EmHouseBJModel m : emhouselist) {
			if (m.getEmhb_total() != null) {
				total = total.add(m.getEmhb_total());
			}
		}
	}

	// 关闭窗口
	@Command
	public void closewin(@BindingParam("win") Window win) {
		//win.detach();
		//Executions.sendRedirect("EmHouse_BjQuery.zul");
	}

	@Command
	public void export() {

		String str = bll.export(emhouselist);
		FileOperate.download("OfficeFile/DownLoad/EmHouse/" + str + ".xls");
	}

	// 查询
	@Command
	@NotifyChange({ "emhouselist", "total", "total_num" })
	public void search(@BindingParam("name") String name,
			@BindingParam("gid") String gid,
			@BindingParam("idcard") String idcard,
			@BindingParam("acc") String acc, @BindingParam("com") String com,
			@BindingParam("cid") String cid, @BindingParam("cacc") String cacc,
			@BindingParam("ifdeclare") String ifdeclare,
			@BindingParam("ownmonth") String ownmonth,@BindingParam("single") String single) {
		String sqlselect = "";

		if (name != null && !name.equals("")) {
			sqlselect = sqlselect + " and emhb_name like '%" + name + "%'";
		}
		if (gid != null && !gid.equals("")) {
			sqlselect = sqlselect + " and a.gid ='" + gid + "'";
		}
		if (idcard != null && !idcard.equals("")) {
			sqlselect = sqlselect + " and emhb_idcard ='" + idcard + "'";
		}
		if (acc != null && !acc.equals("")) {
			sqlselect = sqlselect + " and emhb_houseid ='" + acc + "'";
		}
		if (com != null && !com.equals("")) {
			sqlselect = sqlselect + " and emhb_company like'%" + com + "%'";
		}
		if (cid != null && !cid.equals("")) {
			sqlselect = sqlselect + " and a.cid ='" + cid + "'";
		}
		if (cacc != null && !cacc.equals("")) {
			sqlselect = sqlselect + " and emhb_companyid ='" + cacc + "'";
		}
		if (ifdeclare != null && !ifdeclare.equals("")
				&& !ifdeclare.equals("-1") && ifdeclare != "-1") {
			sqlselect = sqlselect + " and emhb_ifdeclare ='" + ifdeclare + "'";
		}
		if (ownmonth != null && !ownmonth.equals("")) {
			sqlselect = sqlselect + " and a.ownmonth ='" + ownmonth + "'";
		}
		if (single != null && !single.equals("")) {
			if (single.equals("中智户")) {
				sqlselect = sqlselect + " and emhb_single=0";
			}else if (single.equals("独立户")) {
				sqlselect = sqlselect + " and emhb_single=1";
			}
			
		}
		sqlselect += " and ((isnull(b.gid,0)=0 and emhb_ifdeclare in (0,1,2)) or (isnull(b.gid,0)>0 and emhb_ifdeclare in (0,1,2)))";

		emhouselist = bll.getEmhouseBjInfo(sqlselect);
		SumTotal();
	}

	// 弹出批量申报窗口
	@Command
	@NotifyChange({ "emhouselist", "total", "total_num" })
	public void emhouseDeclare(@BindingParam("win") Window win,
			@BindingParam("b") Grid gd) {
		final List<EmHouseBJModel> listmodel = new ArrayList<EmHouseBJModel>();
		int n = emhouselist.size() < gd.getPageSize() ? emhouselist.size()
				: (gd.getPageCount() == (gd.getActivePage() + 1)) ? emhouselist
						.size() : ((gd.getActivePage() + 1) * gd.getPageSize());
		for (int i = (gd.getActivePage() * gd.getPageSize()); i < n; i++) {
			if (emhouselist.get(i).isChecked()) {
				EmHouseBJModel ml = emhouselist.get(i);
				listmodel.add(ml);
			}

		}
		if (listmodel.size() < 1) {
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub
							if (Messagebox.Button.OK.equals(event.getButton())) {
								String sql = "0";
								WfBusinessService wfbs = new EmHouse_EditBJImpl();
								WfOperateService wfs = new WfOperateImpl(wfbs);
								for (EmHouseBJModel em : listmodel) {
									if (em.getEmhb_ifdeclare().equals(0)) {
										em.setEmhb_ifdeclare(2);
									} else if (em.getEmhb_ifdeclare().equals(2)) {
										em.setEmhb_declarename(UserInfo
												.getUsername());
										SimpleDateFormat sdf = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss");
										em.setEmhb_declaretime(sdf
												.format(new Date()));
										em.setEmhb_ifdeclare(1);
									}
									Object[] obj = { "申报数据", em };
									String[] str = null;
									if (em.getEmhb_tapr_id() != null) {
										str = wfs.PassToNext(obj,
												em.getEmhb_tapr_id(),
												UserInfo.getUsername(), "",
												em.getCid(), "");
									} else {
										Integer i = ebll.modBJ(em);
										str = new String[5];
										str[0] = i > 0 ? "1" : "0";
									}

									if (!str[0].equals("1")) {
										Messagebox.show(em.getEmhb_company()
												+ "," + em.getEmhb_name()
												+ "提交数据失败.", "提示",
												Messagebox.OK, Messagebox.ERROR);
										return;
									}
									sql += "," + em.getEmhb_id();
								}

								Messagebox.show("提交成功.", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								emhouselist = bll
										.getEmhouseBjInfo(" and emhb_id in ("
												+ sql + ")");
								SumTotal();

							}

						}
					});
		}
	}

	@Command
	public void checkInfo(@BindingParam("a") EmHouseBJModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		map.put("rb", 1);
		Window window = (Window) Executions.createComponents(
				"Emhouse_BjDeclare.zul", null, map);
		window.doModal();
	}

	// 弹出补缴申报窗口
	@Command
	public void returnInfo(@BindingParam("a") EmHouseBJModel model) {
		model.setEmhb_ifdeclare(3);
		Map map = new HashMap<>();
		map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name
		map.put("id", model.getEmhb_id());// 业务表id
		map.put("tablename", "emhousebj");// 业务表名
		map.put("title", model.getEmhb_company() + "," + model.getEmhb_name());
		map.put("clazz", new EmHouse_EditBll());
		map.put("method", "returnBjFlow");
		map.put("pclass", EmHouseBJModel.class);
		map.put("parameter", model);
		map.put("checkName", "退回");

		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(model.getEmhb_addname());

		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();

	}

	@Command
	@NotifyChange({ "emhouselist", "total", "total_num" })
	public void declareInfo(@BindingParam("a") final EmHouseBJModel em) {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							WfBusinessService wfbs = new EmHouse_EditBJImpl();
							WfOperateService wfs = new WfOperateImpl(wfbs);

							if (em.getEmhb_ifdeclare().equals(0)) {
								em.setEmhb_ifdeclare(2);
							} else if (em.getEmhb_ifdeclare().equals(2)) {
								em.setEmhb_ifdeclare(1);
							}
							Object[] obj = { "申报数据", em };
							String[] str = null;
							if (em.getEmhb_tapr_id() != null) {
								str = wfs.PassToNext(obj, em.getEmhb_tapr_id(),
										UserInfo.getUsername(), "",
										em.getCid(), "");
							} else {
								Integer i = ebll.modBJ(em);
								str = new String[5];
								str[0] = i > 0 ? "1" : "0";
							}

							if (!str[0].equals("1")) {
								Messagebox.show("提交失败.", "提示", Messagebox.OK,
										Messagebox.ERROR);
							} else {
								Messagebox.show("提交成功.", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								emhouselist = bll
										.getEmhouseBjInfo(" and emhb_id="
												+ em.getEmhb_id());
								SumTotal();
							}
						}
					}
				});
	}

	// 全选
	@Command("checkall")
	public void checkall(@BindingParam("a") Checkbox ck,
			@BindingParam("b") Grid gd) {
		int n = emhouselist.size() < gd.getPageSize() ? emhouselist.size()
				: (gd.getPageCount() == (gd.getActivePage() + 1)) ? emhouselist
						.size() : ((gd.getActivePage() + 1) * gd.getPageSize());
		
		for (int i = (gd.getActivePage() * gd.getPageSize()); i < n; i++) {
			if (gd.getCell(i, 19).getChildren().size() > 0) {

				Checkbox cb = (Checkbox) gd.getCell(i, 19).getChildren().get(0);
				// System.out.println(cb);
				cb.setChecked(ck.isChecked());
				emhouselist.get(i).setChecked(ck.isChecked());
			}
		}

	}

	// 生成清册
	@Command("declareExcel")
	@NotifyChange({ "emhouselist", "total", "total_num", "excelList" })
	public void declareExcel() {
		boolean b = false;
		String type1 = "";
		String companyid = "";

		gdlist.clear();
		for (EmHouseBJModel em : emhouselist) {
			if (em != null && em.isChecked()) {
				EmbaseGDModel m = new EmbaseGDModel();
				if (type1.equals("")) {
					type1 = "补缴";
					companyid = em.getEmhb_companyid();
				}
				if (!companyid.equals(em.getEmhb_companyid())) {
					Messagebox.show("当前所选数据单位编号不一致", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
				m.setChange("补缴");
				m.setOwnmonth(em.getOwnmonth());
				m.setHsingle(em.getEmhb_single());
				m.setCompany(em.getEmhb_company());
				m.setCompanyid(em.getEmhb_companyid());
				m.setTotal(em.getEmhb_total());
				m.setHouseid(em.getEmhb_houseid());
				m.setStartMonth(em.getEmhb_startmonth());
				m.setStopMonth(em.getEmhb_stopmonth());
				m.setName(em.getEmhb_name());
				m.setBjreason(em.getEmhb_reason());
				b = true;
				gdlist.add(m);
			}
		}
		if (!b) {
			Messagebox.show("请选择员工.", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							EmHouseDataListBll edbll = new EmHouseDataListBll();
							excelList = edbll.createExcel(gdlist);
							if (excelList.size() > 0) {
								Messagebox.show("文件已生成,请选择下载", "操作提示",
										Messagebox.OK, Messagebox.INFORMATION);
							}
							String s = "";
							for (EmHouseBJModel em : emhouselist) {
								if (em.isChecked()) {
									if (excelList.size() > 0) {
										em.setEmhb_excelfile(excelList.get(0).getName());
										ebll.modBJ(em);
									}
									s = s + "," + em.getEmhb_id();
								}
							}
							s = s.substring(1);
							s = " and emhb_id in (" + s + ")";
							emhouselist = bll.getEmhouseBjInfo(s);
							SumTotal();
						}
					}
				});
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

	public List<EmHouseBJModel> getEmhouselist() {
		return emhouselist;
	}

	public void setEmhouselist(List<EmHouseBJModel> emhouselist) {
		this.emhouselist = emhouselist;
	}

	public List<EmHouseChangeModel> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(List<EmHouseChangeModel> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public Integer getTotal_num() {
		return total_num;
	}

	public static String getStringDate(Date d) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(d);
		return dateString;
	}

	public List<DownLoadFileModel> getExcelList() {
		return excelList;
	}

	public void setExcelList(List<DownLoadFileModel> excelList) {
		this.excelList = excelList;
	}

}
