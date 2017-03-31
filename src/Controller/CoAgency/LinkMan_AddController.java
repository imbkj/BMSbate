/*
 * 创建人：林少斌
 * 创建时间：2013-9-17
 * 用途：委托机构联系人新增页面Controller
 */
package Controller.CoAgency;

import impl.UserInfoImpl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import service.UserInfoService;

import bll.CoAgency.Linkman_AddBll;

public class LinkMan_AddController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	private int coab_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("coab_id").toString());
	private int cabl_type = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cabl_type").toString());
	private int cabc_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cabc_id").toString());
	private Linkman_AddBll addBll = new Linkman_AddBll();
	private String chkVip; // 是否重要联系人
	// 获取用户名
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();
	@Wire
	private Window winLinkmanAdd;
	@Wire
	private Textbox cali_linkman; // 联系人组名
	@Wire
	private Checkbox cali_vip; // 是否重要联系人
	@Wire
	private Textbox cali_name; // 姓名
	@Wire
	private Textbox cali_ename; // 英文名
	@Wire
	private Textbox cali_mobile; // 手机
	@Wire
	private Textbox cali_tel; // 座机
	@Wire
	private Textbox cali_job; // 职位
	@Wire
	private Textbox cali_fax; // 传真
	@Wire
	private Datebox cali_birth; // 生日
	@Wire
	private Textbox cali_hobby; // 兴趣爱好
	@Wire
	private Textbox cali_email; // Email
	@Wire
	private Textbox cali_address; // 联系地址
	@Wire
	private Textbox cali_personality; // 个性描述
	@Wire
	private Textbox cali_remark; // 备注

	// 新增数据方法
	@Listen("onClick=#btSubmit")
	public void addBase() throws Exception {
		try {
			// 判断必填项是否为空
			if (!"".equals(coab_id)
					&& !cali_linkman.getValue().equals("")
					&& !cali_name.getValue().equals("")
					&& (!cali_mobile.getValue().equals("") || !cali_tel
							.getValue().equals(""))) {

				// 判断“是否重要联系人”是否选中
				if (cali_vip.isChecked()) {
					chkVip = "1";
				} else {
					chkVip = "0";
				}

				// 生日判断
				String birth = "";
				if (cali_birth.getValue() != null) {
					birth = addBll.DatetoSting(cali_birth.getValue());
				}

				// 调用新增方法
				String[] message = addBll.AddLinkmanBase(coab_id, cabc_id,
						cali_linkman.getValue(), cali_name.getValue(),
						cali_ename.getValue(), cali_job.getValue(),
						cali_tel.getValue(), cali_mobile.getValue(),
						cali_fax.getValue(), cali_email.getValue(), birth,
						cali_hobby.getValue(), cali_address.getValue(),
						cali_personality.getValue(), cali_remark.getValue(),
						chkVip, username, cabl_type);

				// 弹出提示并跳转页面
				if ("1".equals(message[0])) {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					winLinkmanAdd.detach();

				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			} else {
				if (cali_linkman.getValue().equals("")) {
					Messagebox.show("请录入“联系人组名”", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
				if (cali_name.getValue().equals("")) {
					Messagebox.show("请录入“姓名”", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
				if (cali_mobile.getValue().equals("")
						&& cali_tel.getValue().equals("")) {
					Messagebox.show("请录入“手机”或“座机”", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
