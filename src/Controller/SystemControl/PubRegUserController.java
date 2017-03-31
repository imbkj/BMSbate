package Controller.SystemControl;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.zkoss.zk.ui.Component;
import javax.xml.soap.Text;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.SystemControl.PubRegUserBll;
import Model.DepartmentListModel;
import Util.MD5;

public class PubRegUserController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox name;
	@Wire
	private Textbox psd;
	@Wire
	private Textbox psd2;
	@Wire
	private Combobox sex;
	@Wire
	private Combobox teamleader;
	@Wire
	private Combobox dep_id;
	@Wire
	private Combobox role_id;
	@Wire
	private Textbox emwo_addtime;
	@Wire
	private Textbox phone;
	@Wire
	private Textbox mobile;
	@Wire
	private Textbox email;
	@Wire
	private Datebox intime;
	@Wire
	private Label psw_state;
	@Wire
	private Window win;
	@Wire
	private Window wins;

	PubRegUserBll bll = new PubRegUserBll();

	@Listen("onClick = #submitButton")
	public void submit() {
		if (psd2.getValue().equals("")
				|| !psd.getValue().equals(psd2.getValue())) {
			Messagebox.show("密码不一致，请重新输入密码!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		
		if (email.getValue().equals("")) {
			Messagebox.show("请录入正确的邮箱地址!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		if(phone.getValue().equals(""))
		{
			Messagebox.show("请填写直线电话号码!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		// 判断是否有重复材料
		bll.UserCF(name.getValue());
		if (bll.DochekCF() > 0) {
			Messagebox.show("该姓名已存在，请误重复录入!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			// Date类型转换String

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String Date = sdf.format(intime.getValue());

			MD5 getMD5 = new MD5();
			PubRegUserBll pb = new PubRegUserBll();
			pb.PubRegUserBllAdd(name.getValue(), (int) teamleader
					.getSelectedItem().getValue(), phone.getValue(), sex
					.getValue(), MD5.GetMD5Code(psd.getValue()), email
					.getValue(), mobile.getValue(), Date, ((DepartmentListModel) dep_id
					.getSelectedItem().getValue()).getDep_id(),(int) role_id.getSelectedItem().getValue());

			// 判断是否插入数据********************start**************************************************************

			if (pb.Dochek() > 0) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							Executions.sendRedirect("Pub_RegUser.zul");
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
