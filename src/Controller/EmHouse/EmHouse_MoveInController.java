package Controller.EmHouse;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoglistModel;
import Model.EmHouseChangeModel;
import Model.EmHouseCpp;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import Util.CheckString;
import Util.DateStringChange;
import Util.RedirectUtil;
import Util.StringFormat;
import Util.UserInfo;
import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_EditBll;
import bll.EmHouse.EmHouse_MoveInBll;
import bll.Embase.EmbaseLogin_AddBll;

public class EmHouse_MoveInController {
	private EmHouseChangeModel ecm = new EmHouseChangeModel();
	private List<EmHouseCpp> cpList = new ListModelList<>();
	private List<EmbaseModel> eblist = new ListModelList<>();
	private List<CoglistModel> cglist = new ListModelList<>();
	private List<PubCodeConversionModel> listPC = new ListModelList<>();
	private List<String> ownmonthList = new ArrayList<>();
	private EmHouse_MoveInBll bll = new EmHouse_MoveInBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();
	private EmHouse_EditBll ebll = new EmHouse_EditBll();
	private EmbaseLogin_AddBll abll = new EmbaseLogin_AddBll();

	private CheckString cs = new CheckString();
	private Integer eadaId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("embaId").toString());

	private String username = UserInfo.getUsername();

	private Window winC;
	private String companyid;

	private Integer gjjfwownmonth; // 公积金服务起始月
	private Integer gjjtzownmont; // 公积金台账月
	private boolean house_ifStop;

	public EmHouse_MoveInController() {
		setEblist(eadaId);
		ecm.setCid(eblist.get(0).getCid());
		ecm.setGid(eblist.get(0).getGid());
		ecm.setEmhc_company(eblist.get(0).getCoba_company());
		ecm.setEmhc_shortname(eblist.get(0).getCoba_shortname());
		ecm.setEmhc_name(eblist.get(0).getEmba_name());
		if (eblist.get(0).getEmba_gjjidcard() != null
				&& !eblist.get(0).getEmba_gjjidcard().equals("")) {
			ecm.setEmhc_idcard(eblist.get(0).getEmba_gjjidcard());
		} else {
			ecm.setEmhc_idcard(eblist.get(0).getEmba_idcard());
		}
		cglist = ebll.gjjItemInfo(eblist.get(0).getGid());
		if (cglist.size() > 0) {
			if (cglist.get(0).getCoco_house() != null
					&& !cglist.get(0).getCoco_house().equals("")) {
				setCpList(eblist.get(0).getCid(), eblist.get(0).getGid());

				for (EmHouseCpp m : cpList) {
					if (ecm.getEmhc_cpp2() != null
							&& ecm.getEmhc_cpp2().equals(m.getCpName())) {
						ecm.setEmhc_companyid(m.getCompanyid());
						ecm.setJc(m.getJc());
					}
				}

				ecm.setEmhc_excompany(eblist.get(0).getEmba_excompany());
				ecm.setEmhc_excompanyid(eblist.get(0).getEmba_excompanyid());
				ecm.setEmhc_mobile(eblist.get(0).getEmba_mobile());
				ecm.setEmhc_wifename(eblist.get(0).getEmba_wifename());
				ecm.setEmhc_wifeidcard(eblist.get(0).getEmba_wifeidcard());

				setOwnmonthList();
				GjjOwnmonth();
				setListPC();
			}
		}
	}

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		winC = winD;

		RedirectUtil util = new RedirectUtil();
		if (bll.houseupdate2(ecm.getGid(), ecm.getEmhc_idcard())) {
			Messagebox.show("该员工已参保!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			util.refreshEntryThirdUrl("/EmHouse/Emhouse_Index.zul");
			util.refreshEmUrl("/EmHouse/Emhouse_Index.zul");
		}

		if (!ebll.gjjItem(eblist.get(0).getGid())) {
			Messagebox.show("员工未选择公积金项目!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			util.refreshEntryThirdUrl("/EmHouse/Emhouse_Index.zul");
			util.refreshEmUrl("/EmHouse/Emhouse_Index.zul");
		}

		if (cglist.size() > 0) {
			if (cglist.get(0).getCoco_house() == null
					|| cglist.get(0).getCoco_house().equals("")) {
				Messagebox.show("合同未选择开户类型!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				util.refreshEntryThirdUrl("/EmHouse/Emhouse_Index.zul");
				util.refreshEmUrl("/EmHouse/Emhouse_Index.zul");
				return;
			}
		}
		List<EmHouseChangeModel> ehcmList = new ListModelList<>();
		EmHouseChangeModel em = new EmHouseChangeModel();
		// em.setSameInfo(true);
		em.setGid(eblist.get(0).getGid());
		em.setEmhc_idcard(eblist.get(0).getEmba_idcard());
		em.setEmhc_houseid(eblist.get(0).getEmba_houseid());
		em.setEmhc_ifdeclare(3);
		Integer month = sbll.nowmonth2(em.getGid());

		if (month > 0) {
			em.setOwnmonth(month);
		} else {
			Messagebox.show("员工所属合同公积金信息不完整", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			util.refreshEntryThirdUrl("/EmHouse/Emhouse_Index.zul");
			util.refreshEmUrl("/EmHouse/Emhouse_Index.zul");
		}

		ehcmList = ebll.getChangeList(em);
		if (ehcmList.size() > 0) {
			Messagebox.show("员工当月有未处理退回记录.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			util.refreshEntryThirdUrl("/EmHouse/Emhouse_Index.zul");
			util.refreshEmUrl("/EmHouse/Emhouse_Index.zul");
		}
		
	}

	@Command
	public void link() {
		RedirectUtil u = new RedirectUtil();
		u.indexAddTab("/Embase/Embase_yfzxinfo.zul?gid=" + ecm.getGid() + "",
				"雇员服务中心");
	}

	// 计算公积金所属月份
	public void GjjOwnmonth() {

		// gjjtzownmont = abll.houseOwnmonth();
		gjjtzownmont = Integer.valueOf(DateStringChange.getOwnmonth());
		gjjfwownmonth = abll.getownmonth(ecm.getGid());

		house_ifStop = sbll.gjjOnair(ecm.getCid(), ecm.getGid(), gjjtzownmont,
				null); // 公积金接单日

		if (house_ifStop) {
			// 截单社保所属月份+1
			ecm.setOwnmonth(Integer.valueOf(DateStringChange
					.ownmonthAddoneMonth(gjjtzownmont.toString())));
		} else {
			if (gjjfwownmonth >= gjjtzownmont) {
				ecm.setOwnmonth(gjjfwownmonth);
			} else {
				ecm.setOwnmonth(gjjtzownmont);
			}

		}

	}

	@Command("setpp")
	@NotifyChange("ecm")
	public void setpp() {

		for (EmHouseCpp em : cpList) {
			if (em.getCpName().equals(ecm.getEmhc_cpp2())) {
				ecm.setEmhc_companyid(em.getCompanyid());
				ecm.setJc(em.getJc());
			}
		}

	}

	@Command("submit")
	public void submit() {
		Combobox cp = (Combobox) winC.getFellow("cpp");
		Combobox op = (Combobox) winC.getFellow("opp");

		if (ecm.getEmhc_houseid() == null) {
			Messagebox.show("请输入个人公积金编号!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		} else {
			ecm.setEmhc_houseid(StringFormat.replaceSpace(ecm.getEmhc_houseid()));
			if (ecm.getEmhc_houseid().length() != 11) {
				Messagebox.show("公积金号异常.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}
		if (cp.getSelectedItem() == null) {
			Messagebox.show("请选择单位缴交比例!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		if (op.getSelectedItem() == null) {
			Messagebox.show("请选择个人缴交比例!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		if (ecm.getOwnmonth() == null) {
			Messagebox
					.show("请选择所属月份!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (ecm.getEmhc_mobile() != null && !ecm.getEmhc_mobile().equals("")) {
			if (ecm.getEmhc_mobile().length() != 11) {
				Messagebox.show("手机号码为11位!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}

		if (ecm.getEmhc_radix() == null || ecm.getEmhc_radix() == 0) {
			Messagebox
					.show("缴存金额不正确!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		ecm.setEmhc_cpp(Double.valueOf(cp.getSelectedItem().getValue()
				.toString()));
		ecm.setEmhc_opp(Double.valueOf(op.getSelectedItem().getValue()
				.toString()));

		if (ecm.getEmhc_cpp() > 0.12) {
			Messagebox.show("公积金比例范围是[0.05-0.12].", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		
		ecm.setEmhc_change("调入");
		if (ebll.houseChangeSame2(ecm)) {
			Messagebox.show("员工当月已有未处理调入数据.", "操作提示", Messagebox.OK, Messagebox.ERROR);
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
							ecm.setEmhc_ifdeclare(4);
							ecm.setEmhc_single(bll.getsingle(ecm.getGid()));
							ecm.setEmhc_addname(username);
							ecm.setConfirm(true);
							Integer i = bll.moveinEmhouse(ecm);

							if (i > 0) {
								Messagebox.show("操作成功!", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								RedirectUtil util = new RedirectUtil();
								util.refreshEmUrl("/EmHouse/Emhouse_Index.zul");
							} else {
								Messagebox.show("操作失败!", "操作提示", Messagebox.OK,
										Messagebox.ERROR);

							}

						}
					}
				});

	}

	public EmHouseChangeModel getEcm() {
		return ecm;
	}

	public void setEcm(EmHouseChangeModel ecm) {
		this.ecm = ecm;
	}

	public List<EmHouseCpp> getCpList() {
		return cpList;
	}

	public void setCpList(Integer cid, Integer gid) {
		this.cpList = bll.getCpp(cid, gid);
	}

	public List<EmbaseModel> getEblist() {
		return eblist;
	}

	public void setEblist(Integer id) {
		this.eblist = bll.getembaseInfo(id);
	}

	public List<PubCodeConversionModel> getListPC() {
		return listPC;
	}

	public void setListPC() {
		this.listPC = bll.getpcInfo();
	}

	public List<String> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList() {
		this.ownmonthList = sbll.ownmonthlist("update", ecm.getCid(),
				ecm.getGid(), null);
	}

}
