package Controller.DocumentsInfo;

import impl.DocumentsInfoImpl;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import service.DocumentsInfoService;

import Model.DocumentsInfoModel;
import Model.DocumentsSubmitInfoModel;
import Util.UserInfo;

public class DocumentsInfo_OperationController {
	DocumentsInfoService docS = new DocumentsInfoImpl();
	String username = UserInfo.getUsername();

	/*
	 * @Methodname:新增页面检查是否已选择必选项
	 * 
	 * @input: gd：材料数据；
	 * 
	 * @out: message 1为成功，0为失败
	 */
	public String[] AddchkHaveTo(Grid gd) throws Exception {

		String[] message = new String[1];// 返回值

		try {
			// 插入业务数据前检查材料勾选完整性
			String docTip = "";
			String iht = "0";
			message[0] = "1";

			// 判断是否已选择必选项
			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				Checkbox ckb = (Checkbox) gd.getCell(i, 0).getChildren().get(0);

				docTip = "";

				Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行
				iht = String.valueOf(((DocumentsInfoModel) row.getValue())
						.getDire_ifhaveto());

				if (iht.equals("1") && !ckb.isChecked()) {
					docTip = String.valueOf(((DocumentsInfoModel) row
							.getValue()).getDoin_content());
					Messagebox.show("请选择“" + docTip + "”！", "操作提示",
							Messagebox.OK, Messagebox.ERROR);

					message[0] = "0";
					break;
				}
			}

			return message;

		} catch (Exception e) {
			Messagebox.show("检查出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			message[0] = "0";
			return message;
		}
	}

	/*
	 * @Methodname:新增页面提交材料数据
	 * 
	 * @input: gd：材料数据；tid：业务表数据id；
	 * 
	 * @out: message 1为成功，0为失败
	 */
	public String[] AddsubmitDoc(Grid gd, String tid) throws Exception {

		String[] message = new String[1];// 返回值

		try {

			message[0] = "1";
			String dire_id;
			String ifsubmit;
			String count = "";
			String pupi_state = "0";
			String handover_people = "";

			// 所有选项都插入数据库，不管是否选中
			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				Checkbox ckb = (Checkbox) gd.getCell(i, 0).getChildren().get(0); // 获取checkbox

				Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行
				dire_id = String.valueOf(((DocumentsInfoModel) row.getValue())
						.getDire_id());

				if (ckb.isChecked()) {// 判断是否选中
					// 判断是否已有上传文件
					pupi_state = String.valueOf(((DocumentsInfoModel) row
							.getValue()).getPupi_state());
					if (pupi_state.equals("1")) {
						ifsubmit = "1"; // ifsubmit = "2";(统一将有上传文件的材料数据状态也变更为1)
					} else {
						ifsubmit = "1";
					}
				} else {
					ifsubmit = "0";
				}

				// 获取页面的份数
				count = String.valueOf(((Intbox) row.getChildren().get(2)
						.getChildren().get(0)).getValue());

				// 获取页面的上一手交接人
				if (!ifsubmit.equals("2")) {
					handover_people = String.valueOf(((Combobox) row
							.getChildren().get(4).getChildren().get(0)
							.getChildren().get(0)).getValue());
				} else {
					handover_people = "";
				}

				// 调用接口的方法
				message = docS.addDocSubmitInfo(dire_id, tid, ifsubmit, count,
						handover_people, username);
			}

			return message;

		} catch (Exception e) {
			Messagebox.show("材料新增出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			message[0] = "0";
			return message;
		}
	}

	/*
	 * @Methodname:新增页面提交材料数据
	 * 
	 * @input: gd：材料数据；tid：业务表数据id；
	 * 
	 * @Author:陈耀家
	 * 
	 * @out: message 1为成功，0为失败
	 */
	public String[] AddsubmitDocInfo(Grid gd, String tid) throws Exception {

		String[] message = new String[1];// 返回值

		try {

			message[0] = "1";
			String dire_id;
			String ifsubmit;
			String handover_people = "";

			// 所有选项都插入数据库，不管是否选中
			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				Checkbox ckb = (Checkbox) gd.getCell(i, 0).getChildren().get(0); // 获取checkbox

				Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行
				dire_id = String.valueOf(((DocumentsInfoModel) row.getValue())
						.getDire_id());

				if (ckb.isChecked()) {// 判断是否选中
					ifsubmit = "1";
				} else {
					ifsubmit = "0";
				}
				// 调用接口的方法
				message = docS.addDocSubmitInfo(dire_id, tid, ifsubmit, "1",
						handover_people, username);
			}

			return message;

		} catch (Exception e) {
			Messagebox.show("材料新增出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			message[0] = "0";
			return message;
		}
	}

	/*
	 * @Methodname:新增页面提交材料数据+发放材料资料份数
	 * 
	 * @input: gd：材料数据；tid：业务表数据id；
	 * 
	 * @out: message 1为成功，0为失败
	 */
	public String[] AddsubmitDocout(Grid gd, String tid) throws Exception {

		String[] message = new String[1];// 返回值

		try {

			message[0] = "1";
			String dire_id;
			String ifsubmit;
			String count = "";
			String outcount = "";
			String pupi_state = "0";
			String handover_people = "";

			// 所有选项都插入数据库，不管是否选中
			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				Checkbox ckb = (Checkbox) gd.getCell(i, 0).getChildren().get(0); // 获取checkbox

				Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行
				dire_id = String.valueOf(((DocumentsInfoModel) row.getValue())
						.getDire_id());

				// System.out.println(row.getChildren().get(1).getChildren().get(0).getChildren().size());
				if (row.getChildren().get(1).getChildren().get(0).getChildren()
						.size() > 1) {
					Textbox tb = (Textbox) (row.getChildren().get(1)
							.getChildren().get(0).getChildren().get(row
							.getChildren().get(1).getChildren().get(0)
							.getChildren().size() - 1));
					dire_id=docS.addpageAddDocument(tb.getValue(), Integer
							.valueOf(((DocumentsInfoModel) row.getValue())
									.getDire_puzu_id()), UserInfo.getUsername()).toString();
				}

				// 获取页面的份数
				count = String.valueOf(((Intbox) row.getChildren().get(2)
						.getChildren().get(0)).getValue());
				// 获取页面收回的份数
				/*
				 * outcount = String.valueOf(((Intbox) row.getChildren().get(3)
				 * .getChildren().get(0)).getValue());
				 */
				if (ckb.isChecked()) {// 判断是否选中
					// 判断是否已有上传文件
					pupi_state = String.valueOf(((DocumentsInfoModel) row
							.getValue()).getPupi_state());
					if (pupi_state.equals("1")) {
						ifsubmit = "1"; // ifsubmit = "2";(统一将有上传文件的材料数据状态也变更为1)
					} else {
						ifsubmit = "1";
					}
				} else {
					ifsubmit = "0";

				}

				// 获取页面的上一手交接人
				if (!ifsubmit.equals("2")) {
					handover_people = String.valueOf(((Combobox) row
							.getChildren().get(4).getChildren().get(0)
							.getChildren().get(0)).getValue());
				} else {
					handover_people = "";
				}
				outcount = count;
				// 调用接口的方法
				message = docS.addDocSubmitInfo(dire_id, tid, ifsubmit, count,
						handover_people, username, outcount);
			}

			return message;

		} catch (Exception e) {
			Messagebox.show("材料新增出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			message[0] = "0";
			return message;
		}
	}

	/*
	 * @Methodname:修改页面检查是否已选择必选项
	 * 
	 * @input: gd：材料数据；
	 * 
	 * @out: message 1为成功，0为失败
	 */
	public String[] UpchkHaveTo(Grid gd) throws Exception {
		String[] message = new String[1];// 返回值

		try {

			// 插入业务数据前检查材料勾选完整性
			String docTip = "";
			String iht = "0";
			message[0] = "1";

			// 判断是否已选择必选项
			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				Checkbox ckb = (Checkbox) gd.getCell(i, 0).getChildren().get(0);

				docTip = "";

				Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行
				iht = String
						.valueOf(((DocumentsSubmitInfoModel) row.getValue())
								.getDire_ifhaveto());

				if (iht.equals("1") && !ckb.isChecked()) {
					docTip = String.valueOf(((DocumentsSubmitInfoModel) row
							.getValue()).getDoin_content());
					Messagebox.show("请选择“" + docTip + "”！", "操作提示",
							Messagebox.OK, Messagebox.ERROR);

					message[0] = "0";
					break;
				}
			}

			return message;

		} catch (Exception e) {
			Messagebox.show("检查出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			message[0] = "0";
			return message;
		}
	}

	/*
	 * @Methodname:修改页面提交材料数据
	 * 
	 * @input: gd：材料数据；
	 * 
	 * @out: message 1为成功，0为失败
	 */
	public String[] UpsubmitDoc(Grid gd, String tid) throws Exception {
		String[] message = new String[1];// 返回值

		try {
			// 调用检查是否已选择必选项方法
			message = UpchkHaveTo(gd);

			// 更新数据
			if (!message[0].equals("0")) {
				String dire_id;
				String ifsubmit;
				String count = "";
				String pupi_state = "0";
				String handover_people = "";
				// 所有选项都插入数据库，不管是否选中
				for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
					Checkbox ckb = (Checkbox) gd.getCell(i, 0).getChildren()
							.get(0); // 获取checkbox

					Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行
					dire_id = String.valueOf(((DocumentsSubmitInfoModel) row
							.getValue()).getDire_id());

					if (ckb.isChecked()) {// 判断是否选中
						// 判断是否已有上传文件
						pupi_state = String
								.valueOf(((DocumentsSubmitInfoModel) row
										.getValue()).getPupi_state());
						if (pupi_state.equals("1")) {
							ifsubmit = "1"; // ifsubmit =
											// "2";(统一将有上传文件的材料数据状态也变更为1)
						} else {
							ifsubmit = "1";
						}
					} else {
						ifsubmit = "0";
					}

					// 获取页面的份数
					count = String.valueOf(((Intbox) row.getChildren().get(2)
							.getChildren().get(0)).getValue());

					// 获取页面的上一手交接人
					if (!ifsubmit.equals("2")) {
						handover_people = String.valueOf(((Combobox) row
								.getChildren().get(4).getChildren().get(0)
								.getChildren().get(0)).getValue());
					} else {
						handover_people = "";
					}

					// 调用接口的方法
					message = docS.addDocSubmitInfo(dire_id, tid, ifsubmit,
							count, handover_people, username);
				}
			}
			return message;

		} catch (Exception e) {
			Messagebox.show("材料修改出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			message[0] = "0";
			return message;
		}
	}

	/*
	 * @Methodname:修改页面提交材料数据
	 * 
	 * @input: gd：材料数据；
	 * 
	 * @Author:陈耀家
	 * 
	 * @out: message 1为成功，0为失败
	 */
	public String[] UpsubmitDocInfo(Grid gd, String tid) throws Exception {
		String[] message = new String[1];// 返回值

		try {
			// 调用检查是否已选择必选项方法
			message = UpchkHaveTo(gd);

			// 更新数据
			if (!message[0].equals("0")) {
				String dire_id;
				String ifsubmit;
				String count = "";
				String pupi_state = "0";
				String handover_people = "";
				// 所有选项都插入数据库，不管是否选中
				for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
					Checkbox ckb = (Checkbox) gd.getCell(i, 0).getChildren()
							.get(0); // 获取checkbox

					Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行
					dire_id = String.valueOf(((DocumentsSubmitInfoModel) row
							.getValue()).getDire_id());

					if (ckb.isChecked()) {// 判断是否选中
						ifsubmit = "1";
					} else {
						ifsubmit = "0";
					}
					// 调用接口的方法
					message = docS.addDocSubmitInfo(dire_id, tid, ifsubmit,
							"1", handover_people, username);
				}
			}
			return message;

		} catch (Exception e) {
			Messagebox.show("材料修改出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			message[0] = "0";
			return message;
		}
	}

	/*
	 * @Methodname:修改页面提交材料数据+收回材料
	 * 
	 * @input: gd：材料数据；
	 * 
	 * @out: message 1为成功，0为失败
	 */
	public String[] UpsubmitDocout(Grid gd, String tid) throws Exception {
		String[] message = new String[1];// 返回值

		try {
			// 调用检查是否已选择必选项方法
			message = UpchkHaveTo(gd);

			// 更新数据
			if (!message[0].equals("0")) {
				String dire_id;
				String ifsubmit;
				String count = "";
				String outcount = "";
				String pupi_state = "0";
				String handover_people = "";
				// 所有选项都插入数据库，不管是否选中
				for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
					Checkbox ckb = (Checkbox) gd.getCell(i, 0).getChildren()
							.get(0); // 获取checkbox

					Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行
					dire_id = String.valueOf(((DocumentsSubmitInfoModel) row
							.getValue()).getDire_id());

					// 获取页面的份数
					count = String.valueOf(((Intbox) row.getChildren().get(2)
							.getChildren().get(0)).getValue());
					// 获取页面收回的份数
					outcount = String.valueOf(((Intbox) row.getChildren()
							.get(3).getChildren().get(0)).getValue());

					if (ckb.isChecked()) {// 判断是否选中
						// 判断是否已有上传文件
						pupi_state = String
								.valueOf(((DocumentsSubmitInfoModel) row
										.getValue()).getPupi_state());
						if (pupi_state.equals("1")) {
							ifsubmit = "1"; // ifsubmit =
											// "2";(统一将有上传文件的材料数据状态也变更为1)
						} else {
							ifsubmit = "1";
						}
					} else {
						ifsubmit = "0";
						outcount = "0";
					}

					// 获取页面的上一手交接人
					if (!ifsubmit.equals("2")) {
						handover_people = String.valueOf(((Combobox) row
								.getChildren().get(5).getChildren().get(0)
								.getChildren().get(0)).getValue());
					} else {
						handover_people = "";
					}

					// 调用接口的方法
					message = docS.addDocSubmitInfo(dire_id, tid, ifsubmit,
							count, handover_people, username, outcount);
				}
			}
			return message;

		} catch (Exception e) {
			Messagebox.show("材料修改出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			message[0] = "0";
			return message;
		}
	}

	/*
	 * @Methodname:交接页面提交材料数据
	 * 
	 * @input: gd：材料数据；
	 * 
	 * @out: message 1为成功，0为失败
	 */
	public String[] UpsubmitDocHO(Grid gd, String tid) throws Exception {
		String[] message = new String[1];// 返回值

		try {

			String dire_id;
			String ifsubmit;
			String count = "";
			String pupi_state = "0";
			String handover_people = "";
			// 所有选项都插入数据库，不管是否选中
			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				Checkbox ckb = (Checkbox) gd.getCell(i, 0).getChildren().get(0); // 获取checkbox

				Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行
				dire_id = String.valueOf(((DocumentsSubmitInfoModel) row
						.getValue()).getDire_id());

				// 获取材料状态
				ifsubmit = ckb.getValue();

				// 获取页面的份数
				count = String.valueOf(((Intbox) row.getChildren().get(2)
						.getChildren().get(0)).getValue());

				// 获取页面的上一手交接人
				if (!ifsubmit.equals("2")) {
					handover_people = String.valueOf(((Combobox) row
							.getChildren().get(4).getChildren().get(0)
							.getChildren().get(0)).getValue());
				} else {
					handover_people = "";
				}

				// 调用接口的方法
				message = docS.addDocSubmitInfo(dire_id, tid, ifsubmit, count,
						handover_people, username);
			}
			return message;

		} catch (Exception e) {
			Messagebox.show("材料修改出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			message[0] = "0";
			return message;
		}
	}

	/*
	 * @Methodname:删除材料数据
	 * 
	 * @input: puzu_id：模块id；tid：业务表数据id；
	 * 
	 * @out: message 1为成功，0为失败
	 */
	public String[] deleteDoc(String puzu_id, String tid) throws Exception {

		String[] message = new String[1];// 返回值

		try {
			message[0] = "1";

			// 调用接口的方法
			message = docS.deleteDoc(puzu_id, tid);

			return message;

		} catch (Exception e) {
			Messagebox.show("材料删除出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			message[0] = "0";
			return message;
		}
	}
}
