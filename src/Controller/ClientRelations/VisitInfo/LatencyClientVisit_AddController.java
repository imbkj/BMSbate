/*
 * 创建人：林少斌
 * 创建时间：2013-10-11
 * 用途：潜在客户拜访计划新增页面Controller
 */
package Controller.ClientRelations.VisitInfo;

import impl.UserInfoImpl;
import impl.SystemControl.Data_PopedomIpml;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Textbox;

import bll.ClientRelations.VisitInfo.VisitInfoBll;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.CoLatencyClient.CoLatencyClient_AddBll;

import service.DataPopedomService;
import service.UserInfoService;
import Model.CoLatencyClientModel;
import Model.LoginModel;
import Util.MonthListUtil;

public class LatencyClientVisit_AddController extends
		SelectorComposer<Component> {

	// 获取用户名、session
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();
	String userID = user.getUserid();
	String rolName = user.getRolName();
	
	private List<LoginModel> personList = new ArrayList<LoginModel>(); // 执行人
	private DataPopedomService dps = new Data_PopedomIpml("13", "");
	private VisitInfoBll viinBll = new VisitInfoBll();

	// 获取系统当前月份
	Date d = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	String ownmonth = sdf.format(d);

	// 获取拜访计划月份集
	String[] viinMonth;
	private MonthListUtil mlu = new MonthListUtil();

	// 获取潜在客户信息
	private List colaBaseList; // 潜在客户信息
	CoLatencyClient_AddBll colaBll = new CoLatencyClient_AddBll();
	// 查询潜在客户信息条件
	private String cola_company = "";
	private String cola_addname = "";
	private String cola_successlevel = "";
	private String colaStr = "";
	

	@Wire
	private Listbox lbMonth; // 拜访计划月份
	@Wire
	private Listbox lbCoLa; // 潜在客户
	@Wire
	private Combobox viin_person; // 主执行人
	@Wire
	private Chosenbox viin_subordinate; // 次执行人
	@Wire
	private Combobox viin_type; // 拜访方式
	@Wire
	private Combobox viin_class; // 拜访类型

	@Wire
	private Datebox begindate;
	
	@Wire
	private Datebox enddate;
	
	@Wire
	private Textbox viin_remark;
	
	
	private String viin_state; // 拜访计划状态
	
	private String cola_fllower;
	
	private Date cale_begindate;
	private Date cale_enddate;
	//private String viin_remark;

	// 构造函数
	public LatencyClientVisit_AddController() throws Exception {
		// 获取执行人
		personList = dps.getpidLoginlist();

		// 当前月份未来13个月(包含当前月份)
		//viinMonth = mlu.getMonthList(true, ownmonth, "f", 13);

		// 获取潜在客户信息
		colaBaseList = colaBll.getCoLatencyClientAllInfo("");
	}

	// 新增数据方法
	@Listen("onClick = #btSubmit")
	public void addBase() throws Exception {
		//Set<Listitem> chkLbMonth = lbMonth.getSelectedItems();
		Set<Listitem> chkLbCoLa = lbCoLa.getSelectedItems();
		String viinMonth = "";
		String colaID = "";
		String colaCompany = "";
		String[] chkS;
		int ycount = 0;
		int scount = 0;
		String message = "以下拜访计划新增失败：";
		viin_state = "0";
		
		//判断是否为经理，如果是viin_state直接插入1
		if(rolName.contains("经理")){
			viin_state="1";
		}

		// 判断必填项是否为空
		if (!viin_person.getValue().equals("")
				&& !viin_type.getValue().equals("")
				&& !viin_class.getValue().equals("")) {

			// 遍历拜访计划月份勾选项
		//	for (Listitem c : chkLbMonth) {
				//viinMonth = c.getValue();

				// 遍历潜在客户勾选项
				for (Listitem d : chkLbCoLa) {
					// 计算应新增数据数
					ycount = ycount + 1;
					// 获取潜在客户信息
					colaID = String.valueOf(((CoLatencyClientModel) d
							.getValue()).getCola_id());
					colaCompany = String.valueOf(((CoLatencyClientModel) d
							.getValue()).getCola_company());
					
					// 新增拜访计划
					chkS = viinBll.addVisitInfo(viin_person.getValue(), (String
							.valueOf(viin_subordinate.getSelectedObjects())
							.replace("[", "")).replace("]", ""), viin_type
							.getValue(), viin_class.getValue(), viinMonth,
							colaID, username, viin_state,begindate.getValue(),enddate.getValue(),viin_remark.getValue().toString());

					// 判断是否成功
					if (chkS[0].equals("1")) {
						// 计算实际成功数
						scount = scount + 1;
					} else {
						// 获取不成功的拜访计划
						message = message + colaCompany + " 公司--" + viinMonth
								+ "月份；";
					}
				//}
			}

			// 弹出提示并跳转页面
			if (scount == ycount) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							Executions.sendRedirect("visitinfo_select.zul"); // 跳转页面
						}
					}
				};
				// 弹出提示
				Messagebox.show("新增拜访计划成功!", "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
			} else {
				// 弹出提示
				Messagebox.show(message, "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		}
	else {

//			if (chkLbMonth.isEmpty()) {
//				Messagebox.show("请选择“拜访计划月份”！", "操作提示", Messagebox.OK,
//						Messagebox.ERROR);
//			}
			if (chkLbCoLa.isEmpty()) {
				Messagebox.show("请录入“拜访计划客户”！", "操作提示", Messagebox.OK,
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

	// 查询潜在客户数据
	@Command
	@NotifyChange("colaBaseList")
	public void searchColaInfo() {
		// 拼接sql语句
		colaStr = "";
		
		if (cola_fllower != "" && !cola_fllower.equals("")) {
			colaStr = colaStr + " and cola_follower like '%" + cola_fllower
					+ "%'";
		}
		
		if (cola_company != "" && !cola_company.equals("")) {
			colaStr = colaStr + " and cola_company like '%" + cola_company
					+ "%'";
		}
		if (cola_successlevel != "" && !cola_successlevel.equals("")) {

		}
		if (cola_addname != "" && !cola_addname.equals("")) {
			colaStr = colaStr + " and cola_addname like '%" + cola_addname
					+ "%'";
		}

		// 调用查询Bll方法
		colaBaseList = colaBll.getCoLatencyClientAllInfo(colaStr);
	}

	public Date getCale_begindate() {
		return cale_begindate;
	}

	public void setCale_begindate(Date cale_begindate) {
		this.cale_begindate = cale_begindate;
	}

	public Date getCale_enddate() {
		return cale_enddate;
	}

	public void setCale_enddate(Date cale_enddate) {
		this.cale_enddate = cale_enddate;
	}
	public String getCola_fllower() {
		return cola_fllower;
	}

	public void setCola_fllower(String cola_fllower) {
		this.cola_fllower = cola_fllower;
	}

	public String getCola_company() {
		return cola_company;
	}

	public void setCola_company(String cola_company) {
		this.cola_company = cola_company;
	}

	public String getCola_addname() {
		return cola_addname;
	}

	public void setCola_addname(String cola_addname) {
		this.cola_addname = cola_addname;
	}

	public String getCola_successlevel() {
		return cola_successlevel;
	}

	public void setCola_successlevel(String cola_successlevel) {
		this.cola_successlevel = cola_successlevel;
	}

	public List getColaBaseList() {
		return colaBaseList;
	}

	public void setColaBaseList(List colaBaseList) {
		this.colaBaseList = colaBaseList;
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

}
