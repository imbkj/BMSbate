/*
 * 创建人：林少斌
 * 创建时间：2013-9-24
 * 用途：委托机构联系人删除页面Controller
 */
package Controller.CoAgency;

import impl.UserInfoImpl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;
import service.UserInfoService;
import bll.CoAgency.Linkman_DelBll;

public class LinkMan_DeleteController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	private Linkman_DelBll delLMBll = new Linkman_DelBll();
	int cali_id = (Integer) Executions.getCurrent().getArg().get("cali_id"); // 联系人id
	int coab_id = (Integer) Executions.getCurrent().getArg().get("coab_id"); // 委托机构id
	int cabc_id = (Integer) Executions.getCurrent().getArg().get("cabc_id");
	// 获取用户名
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();

	@Wire
	private Textbox cali_delreason; // 删除原因
	@Wire
	private Window w1;

	// 删除数据方法
	@Listen("onClick=#btSubmit")
	public void delete() throws Exception {
		if (!"".equals(coab_id) && !"".equals(cali_id)
				&& !cali_delreason.getValue().equals("")) {
			// 调用方法
			String[] message = delLMBll.DelLinkmanBase(coab_id, cali_id,
					cabc_id, username, cali_delreason.getValue());

			// 弹出提示并跳转页面
			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// Executions.sendRedirect("BaseInfo_UpdateList.zul");
							// //跳转页面
							w1.detach();
						}
					}
				};
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} else if (cali_delreason.getValue().equals("")) {
			// 弹出提示
			Messagebox.show("请录入”删除原因“！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			// 弹出提示
			Messagebox.show("参数错误！", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
}
