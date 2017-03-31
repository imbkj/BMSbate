package Controller.EmSheBao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import dal.LoginDal;

import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.EmSheBao.Emsc_DeclareSelBll;

import Model.EmShebaoBJModel;
import Model.LoginModel;
import Util.UserInfo;

public class Emsc_DeclareBj_ListController {
	private String sql = Executions.getCurrent().getArg().get("sql").toString();
	private int ownmonth = Integer.parseInt(Executions.getCurrent().getArg()
			.get("ownmonth").toString());
	private int ifExecl = Integer.parseInt(Executions.getCurrent().getArg()
			.get("ifExecl").toString());
	private List<EmShebaoBJModel> bjList;
	private int count;
	private Emsc_DeclareSelBll selBll;
	private boolean all_chkTF = false;// 全选框是否选中

	private int ifUpload = Integer.parseInt(Executions.getCurrent().getArg()
			.get("ifUpload").toString());

	public Emsc_DeclareBj_ListController() {
		selBll = new Emsc_DeclareSelBll();
		bjList = selBll.getBjList(sql, ownmonth);
		count = bjList.size();
	}

	// 表格每行checkbox全选
	@Command("gdallselect")
	public void gdallselect(@BindingParam("grid") Grid gd,
			@BindingParam("check") boolean check) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			try {
				Checkbox ckb = (Checkbox) gd.getCell(i, 16).getChildren()
						.get(0);
				ckb.setChecked(check);
			} catch (Exception e) {
				// 排除不可选内容
			}
		}
	}

	// 返回
	@Command("pageback")
	public void pageback(@BindingParam("win") Window win) {
		win.detach();
		Window window = (Window) Executions.createComponents(
				"Emsc_DeclareBj_Search.zul", null, null);
		window.doModal();
	}

	// 小信封
	@Command("msg")
	@NotifyChange("bjList")
	public void msg(@BindingParam("model") EmShebaoBJModel model) {
		LoginDal d = new LoginDal();

		Map map = new HashMap<>();
		map.put("type", "社会保险");// 业务类型:来自WfClass的wfcl_name
		map.put("id", model.getId());// 业务表id
		map.put("tablename", "EmShebaoBJ");// 业务表名
		map.put("title", model.getEmsb_company() + " 的 " + model.getEmsb_name()
				+ " (" + model.getGid() + ") 的社保补交数据小信封");
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(model.getEmsb_addname());
		m.setLog_id(d.getUserIDByname(model.getEmsb_addname()));
		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list

		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
		// 刷新
		bjList = selBll.getBjList(sql, ownmonth);
	}

	// 小信封(医疗)
	@Command("msgJL")
	@NotifyChange("bjList")
	public void msgJL(@BindingParam("model") EmShebaoBJModel model) {
		LoginDal d = new LoginDal();

		Map map = new HashMap<>();
		map.put("type", "社会保险");// 业务类型:来自WfClass的wfcl_name
		map.put("id", model.getId());// 业务表id
		map.put("tablename", "EmShebaoBJJL");// 业务表名
		map.put("title", model.getEmsb_company() + " 的 " + model.getEmsb_name()
				+ " (" + model.getGid() + ") 的社保医疗补交数据小信封");
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(model.getEmsb_addname());
		m.setLog_id(d.getUserIDByname(model.getEmsb_addname()));
		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list

		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
		// 刷新
		bjList = selBll.getBjList(sql, ownmonth);
	}

	// 批量申报
	@Command("declareSingle")
	@NotifyChange({ "bjList", "all_chkTF" })
	public void declareSingle(@BindingParam("gdBj") Grid gdBj) {
		List<EmShebaoBJModel> list = getPageData(gdBj);
		if (list.size() != 0) {
			Emsc_DeclareOperateBll bll = new Emsc_DeclareOperateBll();
			String[] message = bll.BjDeclare(list, UserInfo.getUsername());
			if ("1".equals(message[0]) || "0".equals(message[0])) {
				bjList = selBll.getBjList(sql, ownmonth);
				all_chkTF = false;
				// 成功提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
			} else {
				// 错误提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			// 未有勾选项提示
			Messagebox.show("请选择需要申报的数据。", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		}
	}

	// 批量上传pdf
	@Command("uploadAll")
	@NotifyChange({ "bjList", "all_chkTF" })
	public void uploadAll(@BindingParam("gdBj") Grid gdBj) {
		List<EmShebaoBJModel> list = getPageData(gdBj);
		if (list.size() != 0) {
			Integer chkD = 0;
			// 只能选择同一员工同一月份未申报的补交数据
			for (EmShebaoBJModel m : list) {
				if (list.get(0).getGid() != m.getGid()
						|| list.get(0).getOwnmonth() != m.getOwnmonth()
						|| m.getEmsb_ifdeclare() != 0) {
					chkD = 1;
					break;
				}
			}

			if (chkD == 0) {
				Map map = new HashMap<>();
				map.put("list", list);// 默认收件人list
				Window window = (Window) Executions.createComponents(
						"Emsc_BJFileUploadAll.zul", null, map);
				window.doModal();
				// 刷新
				bjList = selBll.getBjList(sql, ownmonth);
			} else {
				// 未有勾选项提示
				Messagebox.show("请选择选择 同一员工，同一月份且 未申报 的补交数据。", "操作提示",
						Messagebox.OK, Messagebox.NONE);
			}

		} else {
			// 未有勾选项提示
			Messagebox.show("请选择需要申报的数据。", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		}
	}

	// 交单日期下同
	@Command("allSame")
	public void allSame(@BindingParam("a") Grid gd,
			@BindingParam("rowIndex") Integer num) {
		Datebox db1 = (Datebox) gd.getCell(num, 12).getChildren().get(0)
				.getChildren().get(0);

		if (db1.getValue() != null) {
			for (int i = num + 1; i < gd.getRows().getChildren().size(); i++) {

				if (gd.getCell(i, 12) != null) {
					try {
						Datebox db2 = (Datebox) gd.getCell(i, 12).getChildren()
								.get(0).getChildren().get(0);
						// if (db2.getValue() == null)
						db2.setValue(db1.getValue());
					} catch (Exception e) {
					}
				}
			}
		} else {
			for (int i = num + 1; i < gd.getRows().getChildren().size(); i++) {

			}
		}
	}

	// 获取页面数据
	private List<EmShebaoBJModel> getPageData(Grid gdBj) {
		List<EmShebaoBJModel> list = new ArrayList<EmShebaoBJModel>();
		List<Component> rows = gdBj.getRows().getChildren();
		EmShebaoBJModel m = null;
		Row row;
		for (Object obj : rows) {
			row = (Row) obj;
			try {
				// 判断该行是否勾选
				if (((Checkbox) row.getChildren().get(16).getChildren().get(0))
						.isChecked()) {
					m = (EmShebaoBJModel) row.getValue();
					try {
						if (((Datebox) row.getChildren().get(12).getChildren()
								.get(0).getChildren().get(0)).getValue() != null) {
							m.setEmsb_dptime(((Datebox) row.getChildren()
									.get(12).getChildren().get(0).getChildren()
									.get(0)).getValue());
						}
					} catch (Exception e) {
						// 排除交单日期不正确
					}
					list.add(m);
				}
			} catch (Exception e) {
				// 排除不可勾选的内容
			}
		}
		return list;
	}

	// 弹出备注页面
	@Command("flag")
	public void flag(@BindingParam("id") int id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		Window window = (Window) Executions.createComponents(
				"Emsc_DeclareBj_Flag.zul", null, map);
		window.doModal();
	}

	// 弹出补缴详细信息
	@Command("Detail")
	@NotifyChange("bjList")
	public void Detail(@BindingParam("id") int id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("daid", String.valueOf(id));
		Window window = (Window) Executions.createComponents(
				"Emsc_DeclareBj_ChangeState.zul", null, map);
		window.doModal();
		// 刷新
		bjList = selBll.getBjList(sql, ownmonth);
	}

	// 弹出补缴详细信息
	@Command("DetailJL")
	@NotifyChange("bjList")
	public void DetailJL(@BindingParam("id") int id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("daid", String.valueOf(id));
		Window window = (Window) Executions.createComponents(
				"Emsc_DeclareBjJL_ChangeState.zul", null, map);
		window.doModal();
		// 刷新
		bjList = selBll.getBjList(sql, ownmonth);
	}

	// 上传单子
	@Command("upload")
	@NotifyChange("bjList")
	public void upload(@BindingParam("id") int id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("daid", String.valueOf(id));
		Window window = (Window) Executions.createComponents(
				"Emsc_BJFileUpload.zul", null, map);
		window.doModal();
		// 刷新
		bjList = selBll.getBjList(sql, ownmonth);
	}

	public List<EmShebaoBJModel> getBjList() {
		return bjList;
	}

	public int getCount() {
		return count;
	}

	public int getIfExecl() {
		return ifExecl;
	}

	public boolean isAll_chkTF() {
		return all_chkTF;
	}

	public void setAll_chkTF(boolean all_chkTF) {
		this.all_chkTF = all_chkTF;
	}

	public int getIfUpload() {
		return ifUpload;
	}

	public void setIfUpload(int ifUpload) {
		this.ifUpload = ifUpload;
	}

}
