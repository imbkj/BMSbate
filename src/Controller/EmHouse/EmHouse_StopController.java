package Controller.EmHouse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openxmlformats.schemas.officeDocument.x2006.docPropsVTypes.DateDocument;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_StopBll;
import bll.Embase.EmbaseLogin_AddBll;

import Model.EmHouseChangeModel;
import Model.EmHouseUpdateModel;
import Model.EmbaseModel;
import Util.ConstantsUtil;
import Util.DateStringChange;
import Util.RedirectUtil;
import Util.UserInfo;

public class EmHouse_StopController {
	private List<EmHouseUpdateModel> list = new ListModelList<>();
	private List<EmbaseModel> eblist = new ListModelList<>();
	private List<String> ownmonthList = new ArrayList<>();
	private EmHouseChangeModel ecm = new EmHouseChangeModel();
	private EmHouse_StopBll bll = new EmHouse_StopBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();
	private EmbaseLogin_AddBll abll = new EmbaseLogin_AddBll();
	private String username = UserInfo.getUsername();
	// private Window win = (Window) Path.getComponent("/winStop");
	private Integer eadaId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("embaId").toString());
	// private Integer tapr_id =
	// Integer.valueOf(Executions.getCurrent().getArg().get("id").toString());

	// private Integer eadaId = 10017;
	// private String ownmonth = ConstantsUtil.ownmonth;
	private Window winC;

	private String companyid;
	private Integer gjjfwownmonth; // 公积金服务起始月
	private Integer gjjtzownmont; // 公积金台账月
	private boolean house_ifStop;

	public EmHouse_StopController() {
		setEblist(eadaId);
		list = bll.getListById(eblist.get(0).getCid(), eblist.get(0).getGid());
		if (list.size() > 0) {

			ecm.setCid(eblist.get(0).getCid());
			ecm.setGid(eblist.get(0).getGid());
			ecm.setEmhc_company(list.get(0).getEmhu_company());
			ecm.setEmhc_shortname(list.get(0).getCoba_shortname());
			ecm.setEmhc_companyid(list.get(0).getEmhu_companyid());
			ecm.setEmhc_name(list.get(0).getEmhu_name());
			ecm.setEmhc_idcard(list.get(0).getEmhu_idcard());
			ecm.setEmhc_idcardclass(list.get(0).getEmhu_idcardclass());
			ecm.setEmhc_hj(list.get(0).getEmhu_hj());
			ecm.setEmhc_houseid(list.get(0).getEmhu_houseid());
			ecm.setEmhc_mobile(list.get(0).getEmhu_mobile());
			ecm.setEmhc_title(list.get(0).getEmhu_title());
			ecm.setEmhc_wifename(list.get(0).getEmhu_wifename());
			ecm.setEmhc_wifeidcard(list.get(0).getEmhu_wifeidcard());
			ecm.setEmhc_degree(list.get(0).getEmhu_degree());

			ecm.setEmhc_radix(list.get(0).getEmhu_radix());
			ecm.setEmhc_cpp(list.get(0).getEmhu_cpp());
			ecm.setEmhc_opp(list.get(0).getEmhu_opp());
			ecm.setEmhc_single(list.get(0).getEmhu_single());

			ecm.setEmhc_client(list.get(0).getCoba_client());

			ecm.setEmhc_change("停交");
			ecm.setEmhc_ifprogress(31);
			ecm.setEmhc_tid(0);
			ecm.setEmhc_addname(username);
			ecm.setOwnmonth(sbll.nowmonth2(ecm.getGid()));
			ownmonthList = bll.getOwnmonthList(ecm.getGid());
			ownmonthList = bll.getStopMonthList(ownmonthList);

		}
	}

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		winC = winD;
		if (list.size() == 0) {
			Messagebox.show("员工未参保公积金!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			winC.detach();
		} else {
			if (list.get(0).getEmhu_ifstop().equals(2)) {
				Messagebox.show("员工联网核查不通过,请等待福利部处理!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				winC.detach();
			} else if (list.get(0).getEmhu_ifstop().equals(3)) {
				Messagebox.show("员工有数据被退回,请修改重新发送或删除", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				winC.detach();
			} else if (list.get(0).getEmhu_ifstop().equals(1)) {
				Messagebox.show("员工已提交停缴!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				winC.detach();
			}
		}
		
	}

	@Command
	public void link() {
		RedirectUtil u = new RedirectUtil();
		u.indexAddTab("/Embase/Embase_yfzxinfo.zul?gid=" + ecm.getGid() + "",
				"雇员服务中心");
	}

	@Command("submit")
	public void submit() {
		/*
		 * Checkbox ckc = (Checkbox) winC.getFellow("iconfirm"); if
		 * (ckc.isChecked()) {
		 * ecm.setEmhc_ifdeclare(Integer.valueOf(ckc.getValue().toString())); }
		 * else { ecm.setEmhc_ifdeclare(0); }
		 */
		ecm.setEmhc_ifdeclare(0);
		if (ecm.getOwnmonth() == null) {
			Messagebox
					.show("请选择所属月份!", "操作提示", Messagebox.OK, Messagebox.ERROR);

		} else if (ecm.getEmhc_ifdeclare().equals(5)
				&& ecm.getEmhc_remark().equals("")) {
			Messagebox
					.show("请填写申请原因!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub

							if (Messagebox.Button.OK.equals(event.getButton())) {
								Date d = DateStringChange.StringtoDate(
										ecm.getStopmonth(), "yyyy-MM-dd");

								ecm.setOwnmonth(Integer.valueOf(DateStringChange
										.ownmonthAddoneMonth(DateStringChange
												.Stringtoownmonth(d))));
								Integer i = bll.stopEmhouse(ecm);

								if (i > 0) {
									Messagebox.show("操作成功!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									RedirectUtil util = new RedirectUtil();
									util.refreshEmUrl("/EmHouse/Emhouse_Index.zul");
								} else {
									Messagebox.show("操作失败!", "操作提示",
											Messagebox.OK, Messagebox.ERROR);

								}

							}
						}
					});
		}
	}

	public List<EmbaseModel> getEblist() {
		return eblist;
	}

	public void setEblist(Integer id) {
		this.eblist = bll.getembaseInfo(id);
	}

	public List<String> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(List<String> ownmonthList) {
		this.ownmonthList = ownmonthList;
	}

	public EmHouseChangeModel getEcm() {
		return ecm;
	}

	public void setEcm(EmHouseChangeModel ecm) {
		this.ecm = ecm;
	}

}
