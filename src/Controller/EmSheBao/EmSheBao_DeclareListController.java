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
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import dal.LoginDal;

import Model.EmSheBaoChangeModel;
import Model.LoginModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.CoBase.CoBase_SelectBll;
import bll.EmSheBao.EmSheBao_DOperateBll;
import bll.EmSheBao.EmSheBao_DSelectBll;
import bll.SystemControl.PubRegUserBll;

public class EmSheBao_DeclareListController {
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();
	private List<EmSheBaoChangeModel> sbDataList;
	private PubRegUserBll prBll = new PubRegUserBll();
	private CoBase_SelectBll csbll = new CoBase_SelectBll();

	private String sql = Executions.getCurrent().getArg().get("sql").toString();
	private String top = Executions.getCurrent().getArg().get("top").toString();
	private String order = Executions.getCurrent().getArg().get("order")
			.toString();
	private String bjOwnmonth = Executions.getCurrent().getArg()
			.get("bjOwnmonth").toString();
	private String shebaoId = Executions.getCurrent().getArg().get("shebaoId")
			.toString();
	private int ifs = Integer.parseInt(Executions.getCurrent().getArg()
			.get("ifs").toString());
	private int chkDec = Integer.parseInt(Executions.getCurrent().getArg()
			.get("chkDec").toString());
	private String change = Executions.getCurrent().getArg().get("change")
			.toString();

	private String single = Executions.getCurrent().getArg().get("single")
			.toString();
	private int count = 0; // 多少条数据

	private boolean all_chkTF = false;// 全选框是否选中

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	public EmSheBao_DeclareListController() {
		// 获取页面数据
		sbDataList = dsbll.getEmSCData(top, sql, order, bjOwnmonth);

		try {
			count = sbDataList.size();
		} catch (Exception e) {
			count = 0;
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

				String emsc_ifdeclare;
				try {
					emsc_ifdeclare = ((EmSheBaoChangeModel) row.getValue())
							.getEmsc_ifdeclare();
				} catch (Exception e) {
					emsc_ifdeclare = "1";
				}

				String emsc_ifsame;
				try {
					emsc_ifsame = ((EmSheBaoChangeModel) row.getValue())
							.getEmsc_ifsame();
				} catch (Exception e) {
					emsc_ifsame = "1";
				}

				if (emsc_ifdeclare.equals("0")
						&& (ifs == 1 || !"1".equals(emsc_ifsame))) {
					Checkbox ckb = (Checkbox) gd.getCell(i, 16).getChildren()
							.get(0);
					ckb.setChecked(check);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 返回
	@Command("pageback")
	public void pageback(@BindingParam("win") Window win) {
		win.detach();
		Window window = (Window) Executions.createComponents(
				"EmSheBao_DeclareSearch.zul", null, null);
		window.doModal();
	}

	// 编辑
	@Command("edit")
	@NotifyChange("sbDataList")
	public void edit(@BindingParam("id") int id) {
		Map map = new HashMap();
		map.put("daid", id);
		Window window = (Window) Executions.createComponents("Emsc_Detail.zul",
				null, map);
		window.doModal();
		// 刷新
		sbDataList = dsbll.getEmSCData(top, sql, order, bjOwnmonth);
	}

	// 小信封
	@Command("msg")
	@NotifyChange("sbDataList")
	public void msg(@BindingParam("model") EmSheBaoChangeModel sbData) {
		LoginDal d = new LoginDal();

		Map map = new HashMap<>();
		map.put("type", "社会保险");// 业务类型:来自WfClass的wfcl_name
		map.put("id", sbData.getId());// 业务表id
		map.put("tablename", "EmShebaoChange");// 业务表名
		map.put("title",
				sbData.getEmsc_company() + " 的 " + sbData.getEmsc_name() + " ("
						+ sbData.getGid() + ")的社保变更数据小信封");
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(sbData.getEmsc_addname());
		m.setLog_id(d.getUserIDByname(sbData.getEmsc_addname()));
		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list

		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
		// 刷新
		sbDataList = dsbll.getEmSCData(top, sql, order, bjOwnmonth);
	}

	// 申报社保
	@Command("declareSingle")
	@NotifyChange({ "sbDataList", "all_chkTF" })
	public void declareSingle(@BindingParam("dataGrid") Grid dataGrid) {
		String[] message;
		int j = 0;
		int k = 0;
		String msg;

		// 选中项目
		for (int i = 0; i < dataGrid.getRows().getChildren().size(); i++) {
			try {
				Checkbox ckb; // 获取checkbox
				try {
					ckb = (Checkbox) dataGrid.getCell(i, 16).getChildren()
							.get(0); // 获取checkbox
				} catch (Exception e) {
					ckb = null;
				}

				if (ckb != null) {
					if (ckb.isChecked()) {
						// 判断是否选中
						Row row = (Row) dataGrid.getRows().getChildren().get(i); // 获取Gird的行
						int id = ((EmSheBaoChangeModel) row.getValue()).getId();
						int emsc_tapr_id = ((EmSheBaoChangeModel) row
								.getValue()).getEmsc_tapr_id();
						String emsc_ifdeclare = ((EmSheBaoChangeModel) row
								.getValue()).getEmsc_ifdeclare();
						int cid = ((EmSheBaoChangeModel) row.getValue())
								.getCid();

						// 选中几条数据
						k = k + 1;

						// 申报社保
						if ("0".equals(emsc_ifdeclare)) {
							message = dobll.declareSingle(id, emsc_tapr_id,
									username, cid);
						} else {
							message = dobll.declareSingle(id, username);
						}

						// 判断是否成功
						if (message[0].equals("1")) {
							j = j + 1;
						}
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
						sbDataList = dsbll.getEmSCData(top, sql, order,
								bjOwnmonth);
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

	// 生成单项模板
	@Command("declareSingleExcel")
	@NotifyChange({ "sbDataList", "all_chkTF" })
	public void declareSingleExcel(@BindingParam("dataGrid") Grid dataGrid)
			throws BiffException, IOException {
		try {

			// 判断是否数据传递的值是否有效
			if (!change.equals("全部但不含新增")
					&& !change.equals("全部")
					&& (single.equals("0") || single.equals("2")
							|| single.equals("3") || single.equals("4") || single
								.equals("1"))) {

				String[] message;
				int k = 0;
				String company = "";

				// 生成Excel
				String axis = dsbll.getFileName(change);
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
				int dataRow = 4;

				filename = nowtime + "_" + axis + "_" + spell + "_" + shebaoId
						+ filetype;
				absolutePath = FileOperate.getAbsolutePath();

				// 创建excel
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
				for (int j = 0; j < dataGrid.getRows().getChildren().size(); j++) {

					try {

						Checkbox ckb; // 获取checkbox
						try {
							ckb = (Checkbox) dataGrid.getCell(j, 16)
									.getChildren().get(0); // 获取checkbox
						} catch (Exception e) {
							ckb = null;
						}

						if (ckb != null) {
							// 判断是否选中.
							if (ckb.isChecked()) {

								Row row = (Row) dataGrid.getRows()
										.getChildren().get(j); // 获取Gird的行
								int id = ((EmSheBaoChangeModel) row.getValue())
										.getId();
								int emsc_tapr_id = ((EmSheBaoChangeModel) row
										.getValue()).getEmsc_tapr_id();
								int cid = ((EmSheBaoChangeModel) row.getValue())
										.getCid();

								// 选中几条数据
								k = k + 1;

								// 获取独立开户的公司名称
								if (j == 0 && single.equals("1")) {
									company = csbll
											.getCobaseinfo(
													" AND a.cid="
															+ String.valueOf(((EmSheBaoChangeModel) row
																	.getValue())
																	.getCid()))
											.get(0).getCoba_company();
								}

								// 申报社保(生成单项模板)
								message = dobll.declareSingleExcel(id,
										emsc_tapr_id, username, nowtime + "_"
												+ axis + "_" + spell + "_"
												+ shebaoId, cid);

								// 判断是否成功
								if (message[0].equals("1")) {
									// 插入excel
									if (change.equals("新增")) {
										label = new Label(0, i + dataRow,
												((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_idcard());
										sheet.addCell(label);
										label = new Label(1, i + dataRow,
												((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_name());
										sheet.addCell(label);
										label = new Label(
												6,
												i + dataRow,
												String.valueOf(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_radix()));
										sheet.addCell(label);
										label = new Label(
												3,
												i + dataRow,
												dsbll.hj(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_hj()));
										sheet.addCell(label);
										label = new Label(4, i + dataRow, "2");
										sheet.addCell(label);
										label = new Label(8, i + dataRow,
												((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_mobile());
										sheet.addCell(label);
										label = new Label(
												12,
												i + dataRow,
												dsbll.dm(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_yl()));
										sheet.addCell(label);
										label = new Label(
												13,
												i + dataRow,
												dsbll.ylt(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_yltype()));
										sheet.addCell(label);
										label = new Label(
												15,
												i + dataRow,
												dsbll.dm(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_sye()));
										sheet.addCell(label);
										label = new Label(
												14,
												i + dataRow,
												dsbll.dm(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_gs()));
										sheet.addCell(label);
										label = new Label(
												7,
												i + dataRow,
												dsbll.ls(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_hand()));
										sheet.addCell(label);
										label = new Label(
												5,
												i + dataRow,
												dsbll.folk(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_folk()));
										sheet.addCell(label);

									} else if (change.equals("调入")) {
										label = new Label(0, i + dataRow,
												((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_computerid());
										sheet.addCell(label);
										label = new Label(1, i + dataRow,
												((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_name());
										sheet.addCell(label);
										label = new Label(2, i + dataRow,
												((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_idcard());
										sheet.addCell(label);
										label = new Label(
												3,
												i + dataRow,
												String.valueOf(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_radix()));
										sheet.addCell(label);
										label = new Label(4, i + dataRow, "2");
										sheet.addCell(label);
										label = new Label(
												9,
												i + dataRow,
												dsbll.dm(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_yl()));
										sheet.addCell(label);
										label = new Label(
												10,
												i + dataRow,
												dsbll.ylt(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_yltype()));
										sheet.addCell(label);
										label = new Label(
												12,
												i + dataRow,
												dsbll.dm(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_sye()));
										sheet.addCell(label);
										label = new Label(
												11,
												i + dataRow,
												dsbll.dm(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_gs()));
										sheet.addCell(label);

									} else if (change.equals("停交")) {
										label = new Label(0, i + dataRow,
												((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_computerid());
										sheet.addCell(label);
										label = new Label(1, i + dataRow,
												((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_name());
										sheet.addCell(label);
										label = new Label(2, i + dataRow,
												((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_idcard());
										sheet.addCell(label);
										label = new Label(
												3,
												i + dataRow,
												dsbll.sr(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_stopreason()));
										sheet.addCell(label);

									} else if (change.equals("修改工资")) {
										label = new Label(0, i + dataRow,
												((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_computerid());
										sheet.addCell(label);

										if (((EmSheBaoChangeModel) row
												.getValue()).getEmsc_name()
												.length() > 11) {// 判断名字长度是否超过11位
											label = new Label(1, i + dataRow,
													((EmSheBaoChangeModel) row
															.getValue())
															.getEmsc_name()
															.substring(0, 11));
										} else {
											label = new Label(1, i + dataRow,
													((EmSheBaoChangeModel) row
															.getValue())
															.getEmsc_name());
										}

										sheet.addCell(label);
										label = new Label(2, i + dataRow,
												((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_idcard());
										sheet.addCell(label);
										label = new Label(3, i + dataRow, "0");
										sheet.addCell(label);
										label = new Label(
												4,
												i + dataRow,
												String.valueOf(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_radix()));
										sheet.addCell(label);

									} else if (change.equals("档案修改")) {
										label = new Label(0, i + dataRow,
												((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_computerid());
										sheet.addCell(label);
										label = new Label(1, i + dataRow,
												((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_name());
										sheet.addCell(label);
										label = new Label(2, i + dataRow,
												((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_idcard());
										sheet.addCell(label);
										label = new Label(
												3,
												i + dataRow,
												dsbll.dm(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_yl()));
										sheet.addCell(label);
										label = new Label(
												4,
												i + dataRow,
												dsbll.ylt(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_yltype()));
										sheet.addCell(label);
										label = new Label(
												6,
												i + dataRow,
												dsbll.dm(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_sye()));
										sheet.addCell(label);
										label = new Label(
												5,
												i + dataRow,
												dsbll.dm(((EmSheBaoChangeModel) row
														.getValue())
														.getEmsc_gs()));
										sheet.addCell(label);

									}

								}
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

				} else if (single.equals("3")) {
					label = new Label(2, 1, "464780");
					sheet.addCell(label);
					label = new Label(5, 1, "深圳中智经济技术合作有限公司（四）");
					sheet.addCell(label);

				} else if (single.equals("4")) {
					label = new Label(2, 1, "464781");
					sheet.addCell(label);
					label = new Label(5, 1, "深圳中智经济技术合作有限公司（五）");
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
					map.put("change", change);
					Window window = (Window) Executions.createComponents(
							"Emsc_Declare_ExcelList.zul", null, map);
					window.doModal();
					// 刷新
					sbDataList = dsbll.getEmSCData(top, sql, order, bjOwnmonth);
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

	public List<EmSheBaoChangeModel> getSbDataList() {
		return sbDataList;
	}

	public void setSbDataList(List<EmSheBaoChangeModel> sbDataList) {
		this.sbDataList = sbDataList;
	}

	public int getIfs() {
		return ifs;
	}

	public void setIfs(int ifs) {
		this.ifs = ifs;
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
