package Controller.DocumentsInfo;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;

public class DocTestUpdateController {
	DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();

	String tid="28";
	
	// 提交事件
	@Command("Submit")
	public void Submit(@BindingParam("gd") Grid gd,
			@BindingParam("tb1") Textbox tb1) throws Exception {
		try {
			// 调用方法
			String[] message = docOC.UpsubmitDoc(gd,tid);
			
			Messagebox.show(message[1], "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);

		} catch (Exception e) {
			Messagebox.show("操作失败。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
}
