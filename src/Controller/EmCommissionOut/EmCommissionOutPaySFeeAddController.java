package Controller.EmCommissionOut;

import java.sql.SQLException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.EmCommissionOutPayBll;

public class EmCommissionOutPaySFeeAddController {
	
	EmCommissionOutPayBll bll = new EmCommissionOutPayBll();
	// 添加费用
		@Command("co_feeadd")
		public void co_feeadd(@BindingParam("gid") Label gid,
				@BindingParam("scid") Label scid,
				@BindingParam("ownmonth") Label ownmonth,
				@BindingParam("cabc_id") Label cabc_id,
				@BindingParam("name") Label name,
				@BindingParam("yl") Textbox yl, @BindingParam("jl") Textbox jl,
				@BindingParam("syu") Textbox syu, @BindingParam("gs") Textbox gs,
				@BindingParam("sye") Textbox sye,
				@BindingParam("house") Textbox house,
				@BindingParam("file") Textbox file, @BindingParam("fw") Textbox fw,
				@BindingParam("other") Textbox other,
				@BindingParam("remark") Textbox remark,
				@BindingParam("win") Window win) throws SQLException {
			String message = "0";
			message = bll.feeadd(gid.getValue(), scid.getValue(),
					ownmonth.getValue(), cabc_id.getValue(), name.getValue(),
					yl.getValue(), jl.getValue(),
					syu.getValue(), gs.getValue(), sye.getValue(),
					house.getValue(), file.getValue(), fw.getValue(),
					other.getValue(), remark.getValue());

			if (message.equals("1")) {

				Messagebox.show("添加成功！", "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, null);
				//Executions.sendRedirect("EmCommissionOutPay_AutList.zul");
				win.detach();
			} else {
				// 弹出提示
				Messagebox.show("添加失败！", "操作提示", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		}
}
