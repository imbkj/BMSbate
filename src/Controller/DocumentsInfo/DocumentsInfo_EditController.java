/**
 * @Classname DocumentsInfo_EditController
 * @ClassInfo 材料模块修改页面
 * @author 张志强
 * @Date 2013-10-31
 */
package Controller.DocumentsInfo;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.DocumentsInfo.DocumentsInfo_EditBll;

public class DocumentsInfo_EditController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox doin_remark;
	@Wire
	private Label doin_id;
	@Wire
	private Combobox doin_type;
	@Wire
	private Combobox doin_state;
	@Wire
	private Textbox doin_content;
	private Integer cl_typ;
	private Integer cl_state;
	
	@Listen("onClick = #submitButton")
	public void submit() {
		//System.out.print(doin_type.getValue()=="员工材料");
		if (doin_content.getValue().equals("")) {
			Messagebox
					.show("请录入材料名称!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		if(doin_type.getValue().equals("员工材料"))
		{
			cl_typ=1;
		}
		if(doin_type.getValue().equals("公司材料")) {
			cl_typ=2;
		}
		if(doin_state.getValue().equals("禁用")) {
			cl_state=0;
		}
		else
		{
			cl_state=1;
		}
		DocumentsInfo_EditBll pb = new DocumentsInfo_EditBll(doin_remark.getValue(),doin_content.getValue(),Integer.parseInt(doin_id.getValue()),Integer.parseInt(cl_typ.toString()),Integer.parseInt(cl_state.toString()));

		// 判断是否插入数据********************start**************************************************************
		if (pb.Dochek() > 0) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						Executions.sendRedirect("DocumentsInfo_List.zul");
					}
				}
			};
			Messagebox.show("提交成功!", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		} else {
			Messagebox.show("提交失败!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
		// ******************************end*****************************************************************
	}
}
