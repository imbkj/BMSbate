package Controller.DocumentsInfo;

import java.sql.CallableStatement;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import Conn.dbconn;

public class DocTestAUController {
	DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	String tid = "995";//可以为空白
	String doType = "a";//不能为空，必须填 a 或 u

	// 提交事件
	@Command("Submit1")
	public void Submit1(@BindingParam("gd") Grid gd,
			@BindingParam("tb1") Textbox tb1) throws Exception {
		try {
			// a是新增；u是修改
			String[] message=new String[1];
			
			
			// 新增
			if ("a".equals(doType)) {
				// 调用方法
				message = docOC.AddchkHaveTo(gd);

				// 判断材料必选项是否已选
				if (message[0].equals("1")) {
					// 新增业务数据，并返回业务表ID
					tid = String.valueOf(add(tb1.getValue())); // 测试用

					// 判断业务id是否为空
					if (!tid.equals("") && !tid.equals("0")) {
						// 调用内联页方法submitDoc(Grid gd)
						message = docOC.AddsubmitDoc(gd, tid);
						
					}

				}
			} else if ("u".equals(doType)) {
				// 调用方法
				message = docOC.UpsubmitDoc(gd, tid);
			}
			Messagebox.show(message[1], "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);

			// 修改

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

	public String getDoType() {
		return doType;
	}

	public void setDoType(String doType) {
		this.doType = doType;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
	
	
}
