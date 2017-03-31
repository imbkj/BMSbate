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

public class DocumentsInfo_AUPageListController {
	DocumentsInfoService docS = new DocumentsInfoImpl();
	private List docList; // 材料List
	private List handoverList;// 上一手交接人

	String username = UserInfo.getUsername();
	String puzu_id = String.valueOf(Executions.getCurrent().getArg()
			.get("puzu_id"));
	String f_puzu_id;
	String gid = String.valueOf(Executions.getCurrent().getArg().get("gid"));
	String doType = String.valueOf(Executions.getCurrent().getArg()
			.get("doType")); // 操作类型：新增(a)还是修改(u)

	// 生成交接人下拉框时使用
	String pUsername = String.valueOf(Executions.getCurrent().getArg()
			.get("pUsername")); // 用户登录名
	String pUserId = String.valueOf(Executions.getCurrent().getArg()
			.get("pUserId")); // 用户登录id

	String doc_type = String.valueOf(Executions.getCurrent().getArg()
			.get("doc_type")); // 材料类型：员工(g)还是公司(c)
	String tid = String.valueOf(Executions.getCurrent().getArg().get("tid")); // 业务表数据id
	String o_log_url = String.valueOf(Executions.getCurrent().getArg()
			.get("log_url")); // o_log_url：第一次获取到页面路径的前部分
	String log_url; // log_url：获取到页面路径的前部分
	String lh_people;// 上一手交接人默认值

	private DataPopedomService dps = new Data_PopedomIpml(pUserId, pUsername); // 权限接口

	public DocumentsInfo_AUPageListController() {
		System.out.println(1);
		System.out.println(doType);
		
		if ("a".equals(doType)) {// 新增
			// 是否有需读取的模块id
			f_puzu_id = String.valueOf(Executions.getCurrent().getArg()
					.get("f_puzu_id"));

			// 获取材料数据 参数puzu_id(PubZul表id),材料类型,gid,特殊情况加SQL WHERE语句
			docList = docS.getAddPageDoc(puzu_id, f_puzu_id, doc_type, gid, "");

		} else {// 修改
			// 获取上一手交接人默认值
			lh_people = String.valueOf(Executions.getCurrent().getArg()
					.get("lh_people"));
			if (lh_people.equals("null") || lh_people == null) {
				lh_people = "";
			}

			// 获取材料数据 参数puzu_id(PubZul表id),gid,tid(业务表id),特殊情况加SQL WHERE语句
			docList = docS.getUpdatePageDoc(puzu_id, doc_type, gid, tid, "");

		}

		// 获取上一手交接人下拉框
		handoverList = dps.getLoginlist();
	}

	// 弹出已有上传文件的页面
	@Command("ShowDoc")
	public void ShowDoc() {
		log_url = o_log_url;
		log_url = log_url + "DocEmployee_Upload.zul";
		Map dsinMap = new HashMap();
		dsinMap.put("gid", gid);
		Window window = (Window) Executions.createComponents(log_url, null,
				dsinMap);
		window.doModal();
		log_url = "";
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

					Checkbox ck = (Checkbox) gd.getCell(i, 0).getChildren()
							.get(0);
					if (ck.isChecked())
						cb2.setValue(cb1.getValue());

				}
			}
		}
	}

	// 弹出材料交接记录页面
	@Command("ShowDocLog")
	public void ShowDocLog(@BindingParam("dsin_id") int dsin_id) {
		log_url = o_log_url;
		log_url = log_url + "DocumentsLog.zul";
		Map dsinMap = new HashMap();
		dsinMap.put("dsin_id", dsin_id);
		Window window = (Window) Executions.createComponents(log_url, null,
				dsinMap);
		window.doModal();
		log_url = "";
	}

	@Command
	public void onclickck(@BindingParam("ck") Checkbox ck) {
		if (ck.isChecked()) {
			ck.setChecked(false);
		} else {
			ck.setChecked(true);
		}
	}

	public List getDocList() {
		return docList;
	}

	public void setDocList(List docList) {
		this.docList = docList;
	}

	public List getHandoverList() {
		return handoverList;
	}

	public void setHandoverList(List handoverList) {
		this.handoverList = handoverList;
	}

	public String getLh_people() {
		return lh_people;
	}

	public void setLh_people(String lh_people) {
		this.lh_people = lh_people;
	}

	public String getDoType() {
		return doType;
	}

	public void setDoType(String doType) {
		this.doType = doType;
	}

}
