package Controller.EmHouse;

import impl.WorkflowCore.WfOperateImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_bjImpl;
import bll.EmHouse.Emhouse_BjBll;
import bll.Embase.EmbaseLogin_AddBll;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmHouseBJModel;
import Model.EmHouseUpdateModel;
import Model.EmbaseModel;
import Model.emmonthModel;
import Util.CalendarDate;
import Util.DateStringChange;
import Util.DateUtil;
import Util.Log4jInit;
import Util.RedirectUtil;
import Util.UserInfo;

public class EmHouse_BjAddController {
	private EmHouseBJModel ejm = new EmHouseBJModel();
	private List<EmHouseUpdateModel> list = new ListModelList<>();
	private List<EmbaseModel> eblist = new ListModelList<>();
	private List<String> ownmonthlist = new ListModelList<>();
	private List<String> feeownmonglist = new ListModelList<>();

	private Window win;
	private Emhouse_BjBll bll = new Emhouse_BjBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();
	private Integer ownmonth;
	private EmbaseLogin_AddBll abll = new EmbaseLogin_AddBll();

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

	private Integer embaId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("embaId").toString());
	private Date d1;
	private Date d2;
	private String userId;
	private String username;
	private Integer gjjtzownmont; // 公积金台账月
	private boolean house_ifStop;

	private emmonthModel monthModel;

	public EmHouse_BjAddController() {

		eblist = bll.embaseinfo(embaId);
		userId = UserInfo.getUserid();
		username = UserInfo.getUsername();
		if (eblist.size() > 0) {

			list = bll.emhouseupdateList(eblist.get(0).getGid(), 0);

			if (list.size() > 0) {

				ejm.setCid(list.get(0).getCid());
				ejm.setGid(list.get(0).getGid());

				// 入职页面传入MODEL
				if (Executions.getCurrent().getArg().get("emmonthmodel") != null) {
					monthModel = (emmonthModel) Executions.getCurrent()
							.getArg().get("emmonthmodel");
					ejm.setEmhb_ifdeclare(0);
				} else {
					ejm.setEmhb_ifdeclare(4);
				}
				// 基数
				if (monthModel != null
						&& eblist.get(0).getEmba_house_radix() != null) {
					// 入职传入基数
					ejm.setEmhb_radix(eblist.get(0).getEmba_house_radix());
					ejm.setEmhb_total(eblist.get(0).getEmba_emhb_total());
					if (eblist.get(0).getEmba_emhb_feeownmonth() != null) {
						ejm.setEmhb_feemonth(Integer.valueOf(eblist.get(0)
								.getEmba_emhb_feeownmonth()));

					}
					ejm.setOnboard(true);
				} else {
					// 业务中心读在册基数

					ejm.setEmhb_radix(BigDecimal.valueOf(list.get(0)
							.getEmhu_radix()));
				}

				// 所属月份
				if (monthModel != null
						&& monthModel.getEmba_emhb_ownmonth() != null) {
					// 入职传入所属月份
					ownmonth = monthModel.getEmba_emhb_ownmonth();
					ejm.setOwnmonth(monthModel.getEmba_emhb_ownmonth());
				} else {
					// 业务中心计算所属月
					ejm.setOwnmonth(sbll.nowmonth2(ejm.getGid()));
				}
				ownmonthlist = bll.getOwnmonthList(ejm.getGid());

				feeownmonglist = sbll.ownmonthlist("fee", ejm.getCid(),
						ejm.getGid(), ownmonth);

				// 补缴原因
				if (monthModel != null) {
					ejm.setEmhb_reason(eblist.get(0).getEmba_emhb_reason());
				}

				// 补缴起始月
				if (monthModel != null
						&& monthModel.getGjjbjsownmonth() != null) {
					ejm.setEmhb_startmonth(monthModel.getGjjbjsownmonth());
					d1 = DateStringChange.StringtoDate(ejm.getEmhb_startmonth()
							.toString(), "yyyyMM");
				}

				// 补缴终止月
				if (monthModel != null
						&& monthModel.getGjjbjeownmonth() != null) {
					ejm.setEmhb_stopmonth(monthModel.getGjjbjeownmonth());
					d2 = DateStringChange.StringtoDate(ejm.getEmhb_stopmonth()
							.toString(), "yyyyMM");
				}

				ejm.setEmhb_company(list.get(0).getEmhu_company());
				ejm.setEmhb_name(list.get(0).getEmhu_name());
				ejm.setEmhb_companyid(list.get(0).getEmhu_companyid());
				ejm.setEmhb_houseid(list.get(0).getEmhu_houseid());
				ejm.setEmhb_idcard(list.get(0).getEmhu_idcard());
				ejm.setEmhb_cpp(list.get(0).getEmhu_cpp());
				ejm.setEmhb_opp(list.get(0).getEmhu_opp());

				ejm.setEmhb_single(sbll.houseSingle(ejm.getGid(),
						ejm.getOwnmonth()));

			}
		}

	}

	@Command("winC")
	@NotifyChange("ejm")
	public void winC(@BindingParam("a") Window winD) {
		win = winD;
		if (ejm.getGid() == null) {
			Messagebox.show("暂无公积金在册信息!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			win.detach();
			return;
		}
		EmHouseBJModel em = new EmHouseBJModel();
		em.setGid(ejm.getGid());
		// em.setDataState(3);

		em.setOnboard(ejm.getOnboard());
		/*
		 * Integer month = sbll.nowmonth2(em.getGid());
		 * 
		 * if (month > 0) { em.setOwnmonth(month); } else {
		 * Messagebox.show("员工所属合同公积金信息不完整", "操作提示", Messagebox.OK,
		 * Messagebox.ERROR); }
		 */
		em.setOwnmonth(ejm.getOwnmonth());

		List<EmHouseBJModel> bjList = bll.emhousebjList(em);
		if (bjList.size() > 0) {
			Messagebox.show("员工当月已有补缴数据!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			// win.detach();
			// return;
			Datebox d2 = (Datebox) win.getFellow("std");
			d2.setConstraint("after "
					+ DateStringChange.ownmonthAddoneMonth(ejm.getOwnmonth()
							.toString()) + "01");
		}

		Datebox d = (Datebox) win.getFellow("spd");
		d.setConstraint("before " + ejm.getOwnmonth() + "01");
		
	}

	@Command
	public void link() {
		RedirectUtil u = new RedirectUtil();
		u.indexAddTab("/Embase/Embase_yfzxinfo.zul?gid=" + ejm.getGid() + "",
				"雇员服务中心");
	}

	// 计算公积金所属月份
	public void GjjOwnmonth() {

		gjjtzownmont = abll.houseOwnmonth();
		house_ifStop = sbll.gjjOnair(ejm.getCid(), ejm.getGid(), gjjtzownmont,
				null); // 公积金接单日
		if (house_ifStop) {
			// 截单社保所属月份+1
			ejm.setOwnmonth(Integer.valueOf(DateStringChange
					.ownmonthAddoneMonth(gjjtzownmont.toString())));
		} else {
			ejm.setOwnmonth(gjjtzownmont);
		}

	}

	@Command
	@NotifyChange("ejm")
	public void sumtotal() {
		Datebox db1 = (Datebox) win.getFellow("std");
		Datebox db2 = (Datebox) win.getFellow("spd");
		// Doublebox db = (Doublebox) win.getFellow("radix");
		Double radix = 0.0;
		if (ejm.getEmhb_radix().compareTo(BigDecimal.ZERO) > 0) {
			radix = sbll.radixLimit(ejm.getEmhb_radix());
			// radix = db.getValue();
		}

		if (db1.getValue() != null && db2.getValue() != null && radix != null) {
			if (DateUtil.datediff(db1.getValue(), db2.getValue(), "M") <= 0) {

				BigDecimal bd = new BigDecimal(radix
						* ejm.getEmhb_cpp()
						* 2
						* (CalendarDate.calInterval(db1.getValue(),
								db2.getValue(), "m") + 1));

				ejm.setEmhb_total(bd.setScale(2, BigDecimal.ROUND_HALF_UP));
			} else {

				Messagebox.show("补缴终止月小于补缴起始月!", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}

	}

	@Command
	public void submit() {

		Datebox dbst = (Datebox) win.getFellow("std");
		Datebox dbsp = (Datebox) win.getFellow("spd");

		if (dbsp.getValue() != null && !dbsp.getValue().equals("")) {
			ejm.setEmhb_startmonth(Integer.valueOf(sdf.format(dbst.getValue())));
		} else {
			Messagebox.show("请选择补缴起始月.", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (dbst.getValue() != null && !dbst.getValue().equals("")) {
			if (Integer.valueOf(sdf.format(dbsp.getValue())) >= ejm
					.getOwnmonth()) {
				Messagebox.show("补缴终止月份不能大于等于所属月份.", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			} else {
				ejm.setEmhb_stopmonth(Integer.valueOf(sdf.format(dbsp
						.getValue())));
			}

		} else {
			Messagebox.show("请选择补缴终止月.", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (DateUtil.datediff(ejm.getEmhb_startmonth().toString(), ejm
				.getEmhb_stopmonth().toString(), "d") < 0) {
			Messagebox.show("补缴终止月小于补缴起始月!", "提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		if (ejm.getEmhb_feemonth() == null || ejm.getEmhb_feemonth().equals("")) {
			Messagebox.show("请输入收费月份!", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (ejm.getEmhb_single() == null || ejm.getEmhb_single().equals("")) {
			Messagebox.show("账户类型异常,请联系IT处理!", "提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {

							ejm.setEmhb_addname(UserInfo.getUsername());
							ejm.setEmhb_radix(ejm.getEmhb_radix().setScale(2,
									BigDecimal.ROUND_HALF_DOWN));
							if (ejm.getOnboard() != null && ejm.getOnboard()) {
								bll.bjDel(ejm.getGid());
							}
							Integer i = 0;
							if (ejm.getEmhb_ifdeclare().equals(4)) {
								i = bll.emhousebjAdd(ejm);
							} else {
								WfBusinessService wfbs = new EmHouse_bjImpl();
								WfOperateService wfs = new WfOperateImpl(wfbs);
								Object[] obj = { "新增补缴", ejm };
								String[] str = wfs.AddTaskToNext(obj,
										ejm.getOwnmonth() + "员工公积金补缴",
										ejm.getOwnmonth() + ejm.getEmhb_name()
												+ "(" + ejm.getGid()
												+ ")新增公积金补缴", 102,
										UserInfo.getUsername(), ejm.getEmhb_addname()+"添加数据",
										ejm.getCid(), "");
								if (str[0].equals("1")) {
									i = 1;
									Log4jInit.toLog("员工新增公积金补缴:"+ejm.getGid()+",添加人:"+UserInfo.getUsername());
									ejm.setEmhb_id(Integer.valueOf(str[3]
											.toString()));
									Object[] obj2 = { "跳过确认", ejm };
									wfs.SkipToNext(obj2,
											Integer.valueOf(str[2].toString()),
											username, "", ejm.getCid(), "");

								}
							}

							if (i > 0) {
								Grid gd = (Grid) win.getFellow("docGrid");
								DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();

								docOC.AddsubmitDoc(gd, i.toString());
								Messagebox.show("提交成功.", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
								RedirectUtil util = new RedirectUtil();
								util.refreshEmUrl("/EmHouse/Emhouse_Index.zul");
							} else {
								Messagebox.show("提交失败.", "提示", Messagebox.OK,
										Messagebox.ERROR);
							}

						}
					}
				});
	}

	public EmHouseBJModel getEjm() {
		return ejm;
	}

	public void setEjm(EmHouseBJModel ejm) {
		this.ejm = ejm;
	}

	public List<EmHouseUpdateModel> getList() {
		return list;
	}

	public void setList(List<EmHouseUpdateModel> list) {
		this.list = list;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public List<String> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(List<String> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

	public List<String> getFeeownmonglist() {
		return feeownmonglist;
	}

	public void setFeeownmonglist(List<String> feeownmonglist) {
		this.feeownmonglist = feeownmonglist;
	}

	public Date getD1() {
		return d1;
	}

	public void setD1(Date d1) {
		this.d1 = d1;
	}

	public Date getD2() {
		return d2;
	}

	public void setD2(Date d2) {
		this.d2 = d2;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
