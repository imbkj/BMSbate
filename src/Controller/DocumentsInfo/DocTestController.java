package Controller.DocumentsInfo;

import java.sql.CallableStatement;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;//内联页controller
import Conn.dbconn;

public class DocTestController {

	DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	String tid = "0";

	//提交事件
	@Command("Submit1")
	public void Submit1(@BindingParam("gd") Grid gd,
			@BindingParam("tb1") Textbox tb1) throws Exception {
		try {
			// 调用方法
			String[] message = docOC.AddchkHaveTo(gd);

			// 判断材料必选项是否已选
			if (message[0].equals("1")) {
				// 新增业务数据，并返回业务表ID
				tid = String.valueOf(add(tb1.getValue())); // 测试用

				// 判断业务id是否为空
				if (!tid.equals("") && !tid.equals("0")) {
					// 调用内联页方法submitDoc(Grid gd)
					message=docOC.AddsubmitDoc(gd, tid);
				}
				
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}

		} catch (Exception e) {
			Messagebox.show("操作失败。", "操作提示", Messagebox.OK, Messagebox.ERROR);
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
}
