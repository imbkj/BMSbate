package Controller.EmHouse;

import impl.MessageImpl;
import impl.WorkflowCore.WfOperateImpl;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import service.MessageService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_EditBll;
import bll.EmHouse.EmHouse_EditImpl;

import Model.EmHouseChangeModel;
import Model.SysMessageModel;
import Model.loginroleModel;
import Util.DateStringChange;
import Util.StringFormat;
import Util.UserInfo;

public class Emhouse_MoveNewController {
	private List<EmHouseChangeModel> list = new ListModelList<>();
	private EmHouseChangeModel ecm = new EmHouseChangeModel();
	private Window win = (Window) Path.getComponent("/winMoveNew");
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());

	private boolean deadline = false;

	private EmHouse_EditBll bll = new EmHouse_EditBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();

	public Emhouse_MoveNewController() {
		ecm.setEmhc_id(id);
		list = bll.getChangeList(ecm);
		if (list.size() > 0) {
			ecm.setEmhc_companyid(list.get(0).getEmhc_companyid());
			ecm.setEmhc_company(list.get(0).getEmhc_company());
			ecm.setEmhc_tapr_id(list.get(0).getEmhc_tapr_id());
			ecm.setOwnmonth(list.get(0).getOwnmonth());
			ecm.setEmhc_name(list.get(0).getEmhc_name());
			ecm.setGid(list.get(0).getGid());
			ecm.setCid(list.get(0).getCid());
			deadline = bll.getlastDay(ecm.getGid(), list.get(0).getEmhc_cpp());
		}
	}

	@Command
	public void submit() {
		Textbox tb1 = (Textbox) win.getFellow("houseid");
		Label lbl1 = (Label) win.getFellow("companyid");
		Label lbl2 = (Label) win.getFellow("company");
		if (tb1 != null) {
			if (tb1.getValue().equals("")) {
				ecm.setEmhc_houseid(null);
				Messagebox.show("请输入个人公积金编号!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			} else {

				ecm.setEmhc_houseid(tb1.getValue());
				ecm.setEmhc_houseid(StringFormat.replaceSpace(ecm
						.getEmhc_houseid()));
				if (ecm.getEmhc_houseid().length() != 11) {
					Messagebox.show("公积金号异常.", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			}
			if (lbl1.getValue().equals("")) {
				// ecm.setEmhc_companyid(null);
				Messagebox.show("封存单位编号为空!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (lbl2.getValue().equals("")) {
				// ecm.setEmhc_company(null);
				Messagebox.show("封存单位名称为空!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (ecm.getEmhc_id() != null) {
				/*
				 * boolean b = sbll.gjjOnair(ecm.getCid(), ecm.getGid(),
				 * ecm.getOwnmonth(), ecm.getEmhc_single());
				 * ecm.setOwnmonth(sbll.nowmonth()); if (b) {
				 * ecm.setOwnmonth(Integer.valueOf(DateStringChange
				 * .ownmonthAddoneMonth(ecm.getOwnmonth().toString()))); }
				 */
				WfBusinessService wfbs = new EmHouse_EditImpl();
				WfOperateService wfs = new WfOperateImpl(wfbs);
				Object[] obj = { "新增转调入", ecm };

				String[] str = new String[5];
				if (ecm.getEmhc_tapr_id() != null) {
					str = wfs.StopTask(obj, ecm.getEmhc_tapr_id(), "系统", "");
				} else {
					str[0] = "1";
				}
				if (str[0].equals("1")) {

					str = wfs.AddTaskToNext(obj, "员工公积金变更", ecm.getOwnmonth()
							+ ecm.getEmhc_name() + "(" + ecm.getGid()
							+ ")调入公积金", 35, UserInfo.getUsername(),
							ecm.getEmhc_addname() + "添加数据", ecm.getCid(), "");
					ecm.setEmhc_id(Integer.valueOf(str[3].toString()));
				}

				// Integer i = bll.changeMove(ecm);
				if (str[0].equals("1")) {
					if (deadline) {
						email(ecm.getCid(), ecm.getGid(),
								ecm.getEmhc_company(), ecm.getEmhc_name());
					}
					bll.updateData(ecm.getGid());
					Messagebox.show("操作成功!", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("操作失败!", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("参数有误,请联系IT部处理!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		}

	}

	// 客服或中心在托收日3个工作日内提交数据时通知后道部门人员
	public void email(Integer cid, Integer gid, String company, String name) {
		// 参数解释，业务表名：tablename；业务表id:id
		MessageService msgservice = new MessageImpl("emhousechange", 0);

		String msgstr = "(" + cid.toString() + "," + gid.toString() + ")"
				+ company + name + "有公积金变更数据在托收日三个工作日内提交到后道.";

		String eTitle = "员工公积金数据操作提示";

		try {
			// 调用添加短信息方法

			List<loginroleModel> msglist = new ListModelList<>();
			msglist = bll.getuserlist("39,40,45,43");
			for (loginroleModel m : msglist) {
				SysMessageModel sysm = new SysMessageModel();
				sysm.setSyme_content(msgstr);// 短信内容
				sysm.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));// 发件人id
				sysm.setSymr_name(m.getLog_name());// 收件人姓名
				sysm.setSymr_log_id(m.getLog_id());// 收件人姓名id
				sysm.setEmail(1);
				sysm.setEmailtitle(eTitle);
				msgservice.Add(sysm);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public EmHouseChangeModel getEcm() {
		return ecm;
	}

	public void setEcm(EmHouseChangeModel ecm) {
		this.ecm = ecm;
	}

}
