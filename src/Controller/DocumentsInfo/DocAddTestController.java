/**
 * @Classname DocAddTestController
 * @ClassInfo 材料模块新增页面显示例子
 * @author 林少斌
 * @Date 2013-10-30
 */
package Controller.DocumentsInfo;

import java.sql.CallableStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Conn.dbconn;
import Model.DocumentsInfoModel;

import impl.DocumentsInfoImpl;
import impl.SystemControl.Data_PopedomIpml;
import service.DataPopedomService;
import service.DocumentsInfoService;
import Util.UserInfo;

public class DocAddTestController {
	DocumentsInfoService docS = new DocumentsInfoImpl();
	private List docList; // 材料List
	private List handoverList;// 上一手交接人
	private DataPopedomService dps = new Data_PopedomIpml("42", "林少斌");
	
	String username = UserInfo.getUsername();

	String puzu_id = "1";
	String gid = "10001";
	String tid = "0";

	public DocAddTestController() {

		// 获取材料数据 参数puzu_id(PubZul表id),gid,特殊情况加SQL WHERE语句
		docList = docS.getAddPageDoc(puzu_id, gid, "");

		// 获取上一手交接人下拉框
		handoverList = dps.getLoginlist();
	}

	// 提交事件
	@Command("Submit")
	public void Submit(@BindingParam("gd") Grid gd,
			@BindingParam("tb1") Textbox tb1) throws Exception {

		try {

			// 插入业务数据前检查材料勾选完整性
			String docTip = "";
			String iht = "0";
			// 判断是否已选择必选项
			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				Checkbox ckb = (Checkbox) gd.getCell(i, 0).getChildren().get(0);

				docTip = "";

				Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行
				iht = String
						.valueOf(((DocumentsInfoModel) row.getValue())
								.getDire_ifhaveto());

				if (iht.equals("1") && !ckb.isChecked()) {
					docTip = String.valueOf(((DocumentsInfoModel) row
							.getValue()).getDoin_content());
					Messagebox.show("请选择“" + docTip + "”！", "操作提示",
							Messagebox.OK, Messagebox.ERROR);
					break;
				}
			}

			// 插入玩业务表后返回业务表ID，并插入材料数据
			if (docTip.equals("")) {

				// 新增业务数据，并返回业务表ID
				tid = String.valueOf(add(tb1.getValue())); // 测试用

				// 获取业务表ID后新增材料数据
				if (!tid.equals("0")) {

					String dire_id;
					String ifsubmit;
					String count = "";
					String pupi_state = "0";
					String handover_people = "";
					// 所有选项都插入数据库，不管是否选中
					for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
						Checkbox ckb = (Checkbox) gd.getCell(i, 0)
								.getChildren().get(0); // 获取checkbox

						Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行
						dire_id = String
								.valueOf(((DocumentsInfoModel) row
										.getValue()).getDire_id());

						if (ckb.isChecked()) {// 判断是否选中
							// 判断是否已有上传文件
							pupi_state = String
									.valueOf(((DocumentsInfoModel) row
											.getValue()).getPupi_state());
							if (pupi_state.equals("1")) {
								ifsubmit = "2";
							} else {
								ifsubmit = "1";
							}
						} else {
							ifsubmit = "0";
						}

						// 获取页面的份数
						count = String.valueOf(((Intbox) row.getChildren()
								.get(2).getChildren().get(0)).getValue());

						// 获取页面的上一手交接人
						if (!ifsubmit.equals("2")) {
							handover_people = String.valueOf(((Combobox) row
									.getChildren().get(4).getChildren().get(0))
									.getValue());
						} else {
							handover_people = "";
						}

						// 调用接口的方法
						docS.addDocSubmitInfo(dire_id, tid, ifsubmit, count,
								handover_people, username);
					}
				}

				// 弹出提示
				Messagebox.show("OK!", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}

		} catch (Exception e) {
			Messagebox.show("修改出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 弹出已有上传文件的页面
	@Command("ShowDoc")
	public void ShowDoc(@BindingParam("gid") int gid) {

		Map dsinMap = new HashMap();
		dsinMap.put("gid", gid);
		Window window = (Window) Executions.createComponents(
				"DocEmployee_Upload.zul", null, dsinMap);
		window.doModal();
	}
	
	// 表格每行checkbox全选
	@Command("gdallselect")
	public void gdallselect(@BindingParam("grid") Grid gd,
			@BindingParam("check") boolean check) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			Checkbox ckb = (Checkbox) gd.getCell(i, 0).getChildren().get(0);
			ckb.setChecked(check);
		}
	}

	// 测试用
	public int add(String content) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn.getcall("DocAddTest_p_lsb(?,?)");
			c.setString(1, content);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);
		} catch (Exception e) {
			return 0;
		}
	}

	public List getHandoverList() {
		return handoverList;
	}

	public void setHandoverList(List handoverList) {
		this.handoverList = handoverList;
	}

	public List getDocList() {
		return docList;
	}

	public void setDocList(List docList) {
		this.docList = docList;
	}
}
