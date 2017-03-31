package Controller.EmSheBao;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import dal.LoginDal;

import Model.EmShebaoChangeForeignerModel;
import Model.LoginModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.CoBase.CoBase_SelectBll;
import bll.EmSheBao.EmSheBao_DOperateBll;
import bll.EmSheBao.EmSheBao_DSelectBll;
import bll.SystemControl.PubRegUserBll;

public class EmSheBao_DForeignerListController {
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();
	private PubRegUserBll prBll = new PubRegUserBll();
	private CoBase_SelectBll csbll = new CoBase_SelectBll();

	private List<EmShebaoChangeForeignerModel> sbDataList;

	private String sql = Executions.getCurrent().getArg().get("sql").toString();

	private int chkDec = Integer.parseInt(Executions.getCurrent().getArg()
			.get("chkDec").toString());
	private String s_state = Executions.getCurrent().getArg().get("s_state")
			.toString();
	private String single = Executions.getCurrent().getArg().get("single")
			.toString();
	private String shebaoId = Executions.getCurrent().getArg().get("shebaoId")
			.toString();
	private int count = 0; // 多少条数据

	private boolean all_chkTF = false;// 全选框是否选中

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	public EmSheBao_DForeignerListController() {
		// 获取页面数据
		sbDataList = dsbll.getEmSForCData("", sql, "");

		try {
			count = sbDataList.size();
		} catch (Exception e) {
			count = 0;
		}
	}

	// 返回
	@Command("pageback")
	public void pageback(@BindingParam("win") Window win) {
		win.detach();
		Window window = (Window) Executions.createComponents(
				"EmSheBao_DForeignerSearch.zul", null, null);
		window.doModal();
	}

	// 判断电脑号长度是否正确
	@Command("chkComputerID")
	public void chkComputerID(@BindingParam("computerID") Textbox computerID) {
		Row row = (Row) computerID.getParent().getParent();
		Checkbox ckb = (Checkbox) row.getChildren().get(16).getChildren()
				.get(0);
		if (computerID.getValue().length() == 7
				|| computerID.getValue().length() == 9) {
			ckb.setDisabled(false);
		} else {
			ckb.setDisabled(true);
			ckb.setChecked(false);
		}
	}

	// 表格每行checkbox全选
	@Command("gdallselect")
	public void gdallselect(@BindingParam("grid") Grid gd,
			@BindingParam("check") boolean check) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			try {
				// 判断是否可以选中
				Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行

				String computerID;
				try {
					Textbox tbcomputerID = (Textbox) gd.getCell(i, 4)
							.getChildren().get(0); // 获取textbox;
					computerID = tbcomputerID.getValue();
				} catch (Exception e) {
					computerID = "";
				}

				String emsc_ifdeclare = ((EmShebaoChangeForeignerModel) row
						.getValue()).getEmsc_ifdeclare();

				if (emsc_ifdeclare.equals("0")
						|| (emsc_ifdeclare.equals("2") && (computerID.length() == 7 || computerID
								.length() == 9))) {
					Checkbox ckb = (Checkbox) gd.getCell(i, 16).getChildren()
							.get(0);
					ckb.setChecked(check);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 小信封
	@Command("msg")
	@NotifyChange("sbDataList")
	public void msg(@BindingParam("model") EmShebaoChangeForeignerModel model) {
		LoginDal d = new LoginDal();

		Map map = new HashMap<>();
		map.put("type", "社会保险");// 业务类型:来自WfClass的wfcl_name
		map.put("id", model.getId());// 业务表id
		map.put("tablename", "EmShebaoChangeForeigner");// 业务表名
		map.put("title", model.getEmsc_company() + " 的 " + model.getEmsc_name()
				+ " (" + model.getGid() + ")的社保外籍人参保数据小信封");
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(model.getEmsc_addname());
		m.setLog_id(d.getUserIDByname(model.getEmsc_addname()));
		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list

		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
		// 刷新
		sbDataList = dsbll.getEmSForCData("", sql, "");
	}

	// 申报社保
	@Command("declareForSingle")
	@NotifyChange({ "sbDataList", "all_chkTF" })
	public void declareForSingle(@BindingParam("dataGrid") Grid dataGrid) {
		String[] message;
		int j = 0;
		int k = 0;
		String msg;

		// 选中项目
		for (int i = 0; i < dataGrid.getRows().getChildren().size(); i++) {
			try {
				Checkbox ckb = (Checkbox) dataGrid.getCell(i, 16).getChildren()
						.get(0); // 获取checkbox

				if (ckb.isChecked()) {
					// 判断是否选中
					Row row = (Row) dataGrid.getRows().getChildren().get(i); // 获取Gird的行
					int id = ((EmShebaoChangeForeignerModel) row.getValue())
							.getId();
					int emsc_tapr_id = ((EmShebaoChangeForeignerModel) row
							.getValue()).getEmsc_tapr_id();
					String emsc_ifdeclare = ((EmShebaoChangeForeignerModel) row
							.getValue()).getEmsc_ifdeclare();
					int cid = ((EmShebaoChangeForeignerModel) row.getValue())
							.getCid();

					// 选中几条数据
					k = k + 1;

					// 申报社保
					if (emsc_ifdeclare.equals("0")) {
						message = dobll.declareForSingle(id, emsc_tapr_id,
								username, cid);
					} else {
						Textbox tbcomputerID = (Textbox) dataGrid.getCell(i, 4)
								.getChildren().get(0); // 获取textbox;
						String computerID = tbcomputerID.getValue();

						message = dobll.declareForSingleSuccess(id,
								emsc_tapr_id, computerID, username);
					}

					// 判断是否成功
					if (message[0].equals("1")) {
						j = j + 1;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (k == 0) {
			// 弹出提示
			Messagebox.show("请选择数据！", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			if (j == k) {
				msg = "操作成功！";
			} else {
				msg = "部分数据操作不成功，请检查！";
			}
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						sbDataList = dsbll.getEmSForCData("", sql, "");
						all_chkTF = false;
					}
				}
			};
			// 弹出提示
			Messagebox.show(msg, "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		}
	}

	// 编辑
	@Command("edit")
	@NotifyChange("sbDataList")
	public void edit(@BindingParam("id") int id) {
		// 专递csiiM
		Map map = new HashMap();
		map.put("daid", id);
		Window window = (Window) Executions.createComponents(
				"Emsc_DetailForeigner.zul", null, map);
		window.doModal();
		// 刷新
		sbDataList = dsbll.getEmSForCData("", sql, "");
	}

	// 批量申报
	@Command("declareForExcel")
	@NotifyChange({ "sbDataList", "all_chkTF" })
	public void declareForExcel(@BindingParam("dataGrid") Grid dataGrid)
			throws BiffException, IOException {
		try {

			// 判断是否数据传递的值是否有效
			if (!s_state.equals("9") && !single.equals("9")) {

				String[] message;
				String company = "";

				// 生成Excel
				String axis = "newps_Foreigner";
				String spell;
				try {
					spell = prBll
							.getLoginInfo(" AND log_name='" + username + "'")
							.get(0).getLog_spell();
				} catch (Exception e) {
					spell = "";
				}

				// 单位编号
				if (single.equals("0")) {
					shebaoId = "391055";
				} else if (single.equals("2")) {
					shebaoId = "167120";
				} else if (single.equals("3")) {// 外包
					shebaoId = "464780";
				} else if (single.equals("4")) {// 派遣
					shebaoId = "464781";
				}
				

				String filename = ""; // 文件名
				String absolutePath; // 服务器地址
				String filetype = ".xls"; // 文件类型
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String nowtime = sdf.format(date);
				int dataCell = 0;
				int dataRow = 4;

				filename = nowtime + "_" + axis + "_" + spell + "_" + shebaoId
						+ filetype;
				absolutePath = FileOperate.getAbsolutePath();

				// 创建exce
				// 读取Excel模板
				Workbook wb = Workbook.getWorkbook(new File(absolutePath
						+ "EmSheBao/File/Templet/" + axis + ".xls"));
				// 使用模板创建
				WritableWorkbook wwb = Workbook.createWorkbook(new File(
						absolutePath + "EmSheBao/File/Download/Declare/"
								+ filename), wb);
				// 生成工作表
				WritableSheet sheet = wwb.getSheet(0);
				Label label = null;

				// 选中项目
				int i = 0;
				int k = 0;
				for (int j = 0; j < dataGrid.getRows().getChildren().size(); j++) {

					try {

						Checkbox ckb = (Checkbox) dataGrid.getCell(j, 16)
								.getChildren().get(0); // 获取checkbox

						// 判断是否选中.
						if (ckb.isChecked()) {

							Row row = (Row) dataGrid.getRows().getChildren()
									.get(j); // 获取Gird的行
							int id = ((EmShebaoChangeForeignerModel) row
									.getValue()).getId();
							int emsc_tapr_id = ((EmShebaoChangeForeignerModel) row
									.getValue()).getEmsc_tapr_id();

							// 获取独立开户的公司名称
							if (j == 0 && single.equals("1")) {
								company = csbll
										.getCobaseinfo(
												" AND a.cid="
														+ String.valueOf(((EmShebaoChangeForeignerModel) row
																.getValue())
																.getCid()))
										.get(0).getCoba_company();
							}

							k = k + 1;

							// 批量申报
							message = dobll.declareForSingleExcel(id,
									emsc_tapr_id, username, nowtime + "_"
											+ axis + "_" + spell + "_"
											+ shebaoId);

							// 判断是否成功
							if (message[0].equals("1")) {
								// 插入excel
								label = new Label(0, i + dataRow,
										String.valueOf(i + 1));
								sheet.addCell(label);
								label = new Label(1, i + dataRow,
										((EmShebaoChangeForeignerModel) row
												.getValue()).getEmsc_name());
								sheet.addCell(label);
								label = new Label(2, i + dataRow,
										((EmShebaoChangeForeignerModel) row
												.getValue()).getEmsc_idcard());
								sheet.addCell(label);
								label = new Label(3, i + dataRow,
										((EmShebaoChangeForeignerModel) row
												.getValue()).getEmba_birth());
								sheet.addCell(label);
								label = new Label(
										4,
										i + dataRow,
										String.valueOf(((EmShebaoChangeForeignerModel) row
												.getValue()).getEmsc_radix()));
								sheet.addCell(label);
								label = new Label(5, i + dataRow,
										((EmShebaoChangeForeignerModel) row
												.getValue()).getEmsc_yl());
								sheet.addCell(label);
								label = new Label(6, i + dataRow,
										((EmShebaoChangeForeignerModel) row
												.getValue()).getEmsc_yltype());
								sheet.addCell(label);
								label = new Label(7, i + dataRow,
										((EmShebaoChangeForeignerModel) row
												.getValue()).getEmsc_gs());
								sheet.addCell(label);
								label = new Label(8, i + dataRow,
										((EmShebaoChangeForeignerModel) row
												.getValue()).getEmsc_sye());
								sheet.addCell(label);
								label = new Label(9, i + dataRow,
										((EmShebaoChangeForeignerModel) row
												.getValue()).getEmsc_syu());
								sheet.addCell(label);
								i++;
							}
						}
					} catch (Exception e) {
						// try checkbox
						e.printStackTrace();
					}

				}

				if (single.equals("0")) {
					label = new Label(2, 1, "391055");
					sheet.addCell(label);
					label = new Label(5, 1, "深圳中智经济技术合作有限公司");
					sheet.addCell(label);

				} else if (single.equals("2")) {
					label = new Label(2, 1, "167120");
					sheet.addCell(label);
					label = new Label(5, 1, "深圳中智经济技术合作有限公司（一）");
					sheet.addCell(label);

				} else if (single.equals("1")) {
					label = new Label(2, 1, shebaoId);
					sheet.addCell(label);
					label = new Label(5, 1, company);
					sheet.addCell(label);

				}
				// 写入数据
				wwb.write();
				// 关闭文件
				wwb.close();

				if (k == 0) {
					// 弹出提示
					Messagebox.show("请选择数据！", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					// 弹出页面
					Map map = new HashMap();
					map.put("filename", nowtime + "_" + axis + "_" + spell
							+ "_" + shebaoId);
					map.put("filetype", filetype);
					map.put("s_state", s_state);
					Window window = (Window) Executions.createComponents(
							"Emsc_Declare_ForExcelList.zul", null, map);
					window.doModal();
					// 刷新
					sbDataList = dsbll.getEmSForCData("", sql, "");

					all_chkTF = false;
				}
			}

			else {
				// 弹出提示
				Messagebox.show("系统出错，请不要作任何操作，通知信息组解决！", "操作提示",
						Messagebox.OK, Messagebox.ERROR);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public List<EmShebaoChangeForeignerModel> getSbDataList() {
		return sbDataList;
	}

	public void setSbDataList(List<EmShebaoChangeForeignerModel> sbDataList) {
		this.sbDataList = sbDataList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getChkDec() {
		return chkDec;
	}

	public void setChkDec(int chkDec) {
		this.chkDec = chkDec;
	}

	public boolean isAll_chkTF() {
		return all_chkTF;
	}

	public void setAll_chkTF(boolean all_chkTF) {
		this.all_chkTF = all_chkTF;
	}

}
