/**
 * @Classname DocumentsInfo_ListController
 * @ClassInfo 材料模块列表显示页面
 * @author 张志强
 * @Date 2013-10-31
 */
package Controller.DocumentsInfo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import Model.DocumentsListModel;
import bll.DocumentsInfo.DocumentsInfo_ListBll;

public class DocumentsInfo_ListController {
	DocumentsInfo_ListBll bll = new DocumentsInfo_ListBll();

	private ListModelList<DocumentsListModel> documentslist;// 获取材料列表
	
	@Init
	public void init() throws SQLException {
		documentslist = new ListModelList<DocumentsListModel>(bll.getdocumentslist());
	}

	private Map map = new HashMap();
	@Command
	public void documents_edit(@BindingParam("rol") final DocumentsListModel myDao) {
		// 专递参数rol_id
		map.put("myDao",myDao);
		Window window = (Window) Executions.createComponents(
				"DocumentsInfo_Edit.zul", null, map);
		window.doModal();
	}
	
	//查询已分配材料
		@Command("search")
		@NotifyChange("documentslist")
		public void submit(@BindingParam("tb1") Textbox tb1) throws Exception {
			documentslist = new ListModelList<DocumentsListModel>(bll.getdocumentslist(tb1.getValue()));
		}
	
	public ListModelList<DocumentsListModel> getDocumentslist() {
		return documentslist;
	}

	public void setDocumentslist(ListModelList<DocumentsListModel> documentslist) {
		this.documentslist = documentslist;
	}
}
