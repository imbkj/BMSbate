/**
 * @Classname DocumentsInfo_AddController
 * @ClassInfo 材料模块新增页面
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.DocumentsInfo.DocumentsInfo_AddBll;

public class DocumentsInfo_AddController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox doin_remark;
	@Wire
	private Textbox doin_content;
	@Wire
	private Combobox doin_type;

	DocumentsInfo_AddBll pb = new DocumentsInfo_AddBll();

	@Listen("onClick = #submitButton")
	public void submit() {
		if (doin_type.getValue().equals("")) {
			Messagebox
					.show("请选择材料类型!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (doin_content.getValue().equals("")) {
			Messagebox
					.show("请输入材料名称!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		//判断是否有重复材料
		pb.DocumentsInfo_AddBllCF(
				doin_content.getValue(),
				Integer.parseInt(doin_type.getSelectedItem().getValue()
						.toString()));
		if (pb.DochekCF() > 0) {
			Messagebox.show("该材料已存在，请误重复录入!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			//提交新增
			pb.DocumentsInfo_AddBllADD(
					doin_content.getValue(),
					doin_remark.getValue(),
					Integer.parseInt(doin_type.getSelectedItem().getValue()
							.toString()));
			// 判断是否插入数据********************start**************************************************************

			if (pb.Dochek() > 0) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							Executions.sendRedirect("DocumentsInfo_Add.zul");
						}
					}
				};
				Messagebox.show("提交成功!", "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
			} else {
				Messagebox.show("提交失败!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
			// ******************************end*****************************************************************
		}
	}
}
