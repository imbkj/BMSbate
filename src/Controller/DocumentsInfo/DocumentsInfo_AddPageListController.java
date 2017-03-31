/**
 * @Classname DocumentsInfo_UpPageListController
 * @ClassInfo 业务新增页面材料显示页Controller
 * @author 林少斌
 * @Date 2013-11-6
 */
package Controller.DocumentsInfo;

import impl.DocumentsInfoImpl;
import impl.SystemControl.Data_PopedomIpml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Window;

import service.DataPopedomService;
import service.DocumentsInfoService;
import Util.UserInfo;

public class DocumentsInfo_AddPageListController {
	DocumentsInfoService docS = new DocumentsInfoImpl();
	private List docList; // 材料List
	private List handoverList;// 上一手交接人

	String username = UserInfo.getUsername();
	String puzu_id = String.valueOf(Executions.getCurrent().getArg()
			.get("puzu_id"));
	String f_puzu_id = null;
	String gid = String.valueOf(Executions.getCurrent().getArg().get("gid"));
	String dtype = String.valueOf(Executions.getCurrent().getArg()
			.get("doc_type")); // 材料类型：员工(g)还是公司(c)
	// 生成交接人下拉框时使用
	String pUsername = ""; // 用户登录名
	String pUserId = ""; // 用户登录id
	private DataPopedomService dps; // 权限接口

	public DocumentsInfo_AddPageListController() {
		// 是否有需读取的模块id
		if (Executions.getCurrent().getArg().get("f_puzu_id") != null) {
			f_puzu_id = String.valueOf(Executions.getCurrent().getArg()
					.get("f_puzu_id"));
		}

		if (Executions.getCurrent().getArg().get("pUsername") != null) {
			pUsername = String.valueOf(Executions.getCurrent().getArg()
					.get("pUsername")); // 用户登录名
		}
		if (Executions.getCurrent().getArg().get("pUserId") != null) {
			pUserId = String.valueOf(Executions.getCurrent().getArg()
					.get("pUserId")); // 用户登录id
		}
		dps = new Data_PopedomIpml(pUserId, pUsername); // 权限接口

		// 获取材料数据 参数puzu_id(PubZul表id),材料类型,gid,特殊情况加SQL WHERE语句
		docList = docS.getAddPageDoc(puzu_id, f_puzu_id, dtype, gid, "");

		// 获取上一手交接人下拉框
		handoverList = dps.getLoginlist();
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
	@Command("docAllselect")
	public void docAllselect(@BindingParam("grid") Grid gd,
			@BindingParam("check") boolean check) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			Checkbox ckb = (Checkbox) gd.getCell(i, 0).getChildren().get(0);
			ckb.setChecked(check);
		}
	}

	// 复制上一手交接人
	@Command("copylp")
	public void copylp(@BindingParam("grid") Grid gd,
			@BindingParam("rowIndex") Integer num) {
		Integer cellNum = 4;

		Combobox cb1 = (Combobox) gd.getCell(num, cellNum).getChildren().get(0)
				.getChildren().get(0);
		if (cb1.getValue() != null) {
			for (int i = num + 1; i < gd.getRows().getChildren().size(); i++) {

				if (gd.getCell(i, cellNum) != null) {

					Combobox cb2 = (Combobox) gd.getCell(i, cellNum)
							.getChildren().get(0).getChildren().get(0);

					// if (db2.getValue() == null) {
					Checkbox ck = (Checkbox) gd.getCell(i, 0).getChildren()
							.get(0);
					if (ck.isChecked())
						cb2.setValue(cb1.getValue());
					// }

				}
			}
		} /*
		 * else { for (int i = num + 1; i < gd.getRows().getChildren().size();
		 * i++) {
		 * 
		 * } }
		 */
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
