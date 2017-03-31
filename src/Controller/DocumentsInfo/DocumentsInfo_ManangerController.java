/**
 * @Classname DocumentsInfo_ManangerController
 * @ClassInfo 材料模块列表分配页面
 * @author 张志强
 * @Date 2013-10-31
 */
package Controller.DocumentsInfo;

import java.sql.SQLException;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.DocumentsListModel;
import Model.PubZulListModel;
import bll.DocumentsInfo.DocumentsInfo_EditBll;
import bll.DocumentsInfo.DocumentsInfo_ManangerBll;
import bll.DocumentsInfo.DocumentsInfo_ManangerAddBll;

public class DocumentsInfo_ManangerController extends
		SelectorComposer<Component> {
	DocumentsInfo_ManangerBll bll = new DocumentsInfo_ManangerBll();
	private ListModelList<DocumentsListModel> documentslist;// 获取材料

	private ListModelList<PubZulListModel> zullist;// 获取材料类别

	// private ListModelList<DocumentsListModel> doclist;// 获取已分配的材料
	private List doclist;

	@Init
	public void init() throws SQLException {
		documentslist = new ListModelList<DocumentsListModel>(
				bll.getdocumentslist());

		zullist = new ListModelList<PubZulListModel>(bll.getzullist());
	}

	int iht = 0;
	int x = 0;
	int ty_in = 0;

	// 查询已分配材料
	@Command("zulsubmit")
	@NotifyChange("doclist")
	public void submit(@BindingParam("tb1") Combobox tb1) throws Exception {
		// doclist = new
		// ListModelList<DocumentsListModel>(bll.getdoclist(Integer.parseInt(tb1.getSelectedItem().getValue().toString())));
		doclist = bll.getdoclist(Integer.parseInt(tb1.getSelectedItem()
				.getValue().toString()));
	}

	// 分配材料
	@Command("Submit")
	public void submit(@BindingParam("lb") Listbox lb,
			@BindingParam("tb1") Combobox tb1) throws Exception {

		if (tb1.getValue().equals("")) {
			Messagebox
					.show("请选择所属模块!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		List<Listitem> items = lb.getItems();
		for (Listitem item : items) {
			if (item.isSelected()) {
				iht = 1;
			} else {
				iht = 0;
			}
			if (((DocumentsListModel) item.getValue()).getDoin_type().equals(
					"员工材料")) {
				ty_in = 1;
			} else {
				ty_in = 2;
			}

			DocumentsInfo_ManangerAddBll pb = new DocumentsInfo_ManangerAddBll(
					((DocumentsListModel) item.getValue()).getDoin_content(),
					ty_in, iht,
					((DocumentsListModel) item.getValue()).getDoin_id(), tb1
							.getSelectedItem().getValue().toString());

			if (pb.Dochek() > 0) {
				x = x + 1;
			}
			;
		}
		if (x > 0) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						Executions.sendRedirect("DocumentsInfo_Mananger.zul");
					}
				}
			};
			Messagebox.show("提交成功!", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		} else {
			Messagebox.show("提交失败!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 删除
	@Command("delete")
	@NotifyChange("doclist")
	public void delete(@BindingParam("tb1") final Combobox tb1,
			@BindingParam("id") Integer dire_id) throws NumberFormatException,
			SQLException {

		DocumentsInfo_ManangerBll eBll = new DocumentsInfo_ManangerBll();
		// 调用删除方法
		String[] message = eBll.docDelete(dire_id);

		
		if ("1".equals(message[0])) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						// 刷新
						doclist = bll.getdoclist(Integer.parseInt(tb1.getSelectedItem()
								.getValue().toString()));
					}
				}
			};
			Messagebox.show("提交成功!", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		} else {
			Messagebox.show("提交失败!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
		
	}

	public ListModelList<DocumentsListModel> getDocumentslist() {
		return documentslist;
	}

	public void setDocumentslistt(
			ListModelList<DocumentsListModel> documentslist) {
		this.documentslist = documentslist;
	}

	public ListModelList<PubZulListModel> getZullist() {
		return zullist;
	}

	public void setZullist(ListModelList<PubZulListModel> zullist) {
		this.zullist = zullist;
	}

	public List getDoclist() {
		return doclist;
	}

	public void setDoclist(List doclist) {
		this.doclist = doclist;
	}

	/*
	 * public ListModelList<DocumentsListModel> getDoclist() { return doclist; }
	 * 
	 * public void setDoclist(ListModelList<DocumentsListModel> doclist) {
	 * this.doclist = doclist; }
	 */

}
