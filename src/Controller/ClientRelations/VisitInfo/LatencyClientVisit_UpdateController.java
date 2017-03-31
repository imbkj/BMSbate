/*
 * 创建人：林少斌
 * 创建时间：2013-10-12
 * 用途：潜在客户拜访计划修改页面Controller
 */
package Controller.ClientRelations.VisitInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import impl.UserInfoImpl;
import impl.SystemControl.Data_PopedomIpml;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.ClientRelations.VisitInfo.VisitInfoBll;

import service.DataPopedomService;
import service.UserInfoService;

import Model.LoginModel;
import Model.VisitInfoModel;
import Util.MonthListUtil;

public class LatencyClientVisit_UpdateController extends
		SelectorComposer<Component> {

	// 获取用户名、session
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();
	String userID = user.getUserid();

	private List<LoginModel> personList = new ArrayList<LoginModel>(); // 主执行人
	private List<LoginModel> subordinateList = new ArrayList<LoginModel>();// 次执行人
	private ListModelList<String> subordinateLML = new ListModelList<String>(); // 次执行人ListModelList<String>类型变量
	private DataPopedomService dps = new Data_PopedomIpml("13", ""); // new接口的实现类
	private VisitInfoBll viinBll = new VisitInfoBll();

	// 获取系统当前月份
	Date d = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	String ownmonth = sdf.format(d);

	// 获取拜访计划月份集
	String[] viinMonth;
	private MonthListUtil mlu = new MonthListUtil();

	int viinID = ((VisitInfoModel) Executions.getCurrent().getArg()
			.get("viinM")).getViin_id(); // 获取页面专递的拜访计划id
	String strSubordinate = ((VisitInfoModel) Executions.getCurrent().getArg()
			.get("viinM")).getViin_subordinate();// 获取页面专递的次执行人数据

	@Wire
	private Combobox viin_person; // 主执行人
	@Wire
	private Chosenbox viin_subordinate; // 次执行人
	@Wire
	private Combobox viin_type; // 拜访方式
	@Wire
	private Combobox viin_class; // 拜访类型
	@Wire
	private Combobox viin_month; // 拜访月份
	@Wire
	private Window w1;

	public LatencyClientVisit_UpdateController() {
		personList = dps.getpidLoginlist(); // 获取执行人
		viinMonth = mlu.getMonthList(true, ownmonth, "f", 13); // 当前月份未来13个月(包含当前月份)

		subordinateList = dps.getpidLoginlist(); // 获取次执行人下拉列表
		for (int i = 0; i < subordinateList.size(); i++) {
			subordinateLML.add(((LoginModel) subordinateList.get(i))
					.getLog_name().trim()); // 循环插入ListModelList<String>类型变量中
		}
	}

	@Command
	public void selViinSubordinate() {
		String viinSubordinate[] = strSubordinate.split(","); // 次执行人，用split()分开
		// 循环新增chosenbox子项
		for (String vs : viinSubordinate) {
			subordinateLML.addToSelection(vs.trim()); // addToSelection()方法只能通过ListModelList类型调用
		}
	}

	// 修改数据方法
	@Listen("onClick = #btSubmit")
	public void updateBase() throws Exception {

		// 判断必填项是否为空
		if (!viin_month.getValue().equals("")
				&& !viin_person.getValue().equals("")
				&& !viin_type.getValue().equals("")
				&& !viin_class.getValue().equals("")) {

			// 修改拜访计划
			String[] message = viinBll.updateVisitInfo(viinID, viin_person
					.getValue(), (String.valueOf(viin_subordinate
					.getSelectedObjects()).replace("[", "")).replace("]", ""),
					viin_type.getValue(), viin_class.getValue(), viin_month
							.getValue());

			// 弹出提示并跳转页面
			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// Executions.sendRedirect("vit_backList.zul");
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
		} else {

			if (viin_month.getValue().equals("")) {
				Messagebox.show("请选择“拜访计划月份”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
			if (viin_person.getValue().equals("")) {
				Messagebox.show("请录入“主执行人”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
			if (viin_type.getValue().equals("")) {
				Messagebox.show("请录入“拜访方式”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
			if (viin_class.getValue().equals("")) {
				Messagebox.show("请录入“拜访类型”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public String[] getViinMonth() {
		return viinMonth;
	}

	public void setViinMonth(String[] viinMonth) {
		this.viinMonth = viinMonth;
	}

	public List<LoginModel> getPersonList() {
		return personList;
	}

	public void setPersonList(List<LoginModel> personList) {
		this.personList = personList;
	}

	public ListModelList getSubordinateLML() {
		return subordinateLML;
	}

	public void setSubordinateLML(ListModelList subordinateLML) {
		this.subordinateLML = subordinateLML;
	}

	public List<LoginModel> getSubordinateList() {
		return subordinateList;
	}

	public void setSubordinateList(List<LoginModel> subordinateList) {
		this.subordinateList = subordinateList;
	}

}
