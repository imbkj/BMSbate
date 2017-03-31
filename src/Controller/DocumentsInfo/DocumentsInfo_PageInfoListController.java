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
import org.zkoss.zul.Grid;
import org.zkoss.zul.Window;

import service.DataPopedomService;
import service.DocumentsInfoService;
import Util.UserInfo;

public class DocumentsInfo_PageInfoListController {
	DocumentsInfoService docS = new DocumentsInfoImpl();
	private List docList; // 材料List

	String username = UserInfo.getUsername();
	String puzu_id = String.valueOf(Executions.getCurrent().getArg()
			.get("puzu_id"));
	String gid = String.valueOf(Executions.getCurrent().getArg().get("gid"));
	String dtype = String.valueOf(Executions.getCurrent().getArg().get("doc_type"));	//材料类型：员工(g)还是公司(c)
	//生成交接人下拉框时使用
	String pUsername=String.valueOf(Executions.getCurrent().getArg().get("pUsername")); //用户登录名
	String pUserId=String.valueOf(Executions.getCurrent().getArg().get("pUserId")); //用户登录id
	private DataPopedomService dps = new Data_PopedomIpml(pUserId, pUsername);	//权限接口
	
	public DocumentsInfo_PageInfoListController() {
		// 获取材料数据 参数puzu_id(PubZul表id),材料类型,gid,特殊情况加SQL WHERE语句
		docList = docS.getAddPageDoc(puzu_id,dtype, gid, "","");	
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

	public List getDocList() {
		return docList;
	}

	public void setDocList(List docList) {
		this.docList = docList;
	}
}
