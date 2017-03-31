package Controller.EmHouse;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import Model.CoglistModel;
import Model.EmHouseChangeModel;
import Model.EmHouseCpp;
import Model.EmbaseModel;
import Model.Emcontactinfo;
import Model.PubCodeConversionModel;
import Model.emmonthModel;
import Util.DateStringChange;
import Util.Log4jInit;
import Util.RedirectUtil;
import Util.StringFormat;
import Util.UserInfo;
import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_EditBll;
import bll.EmHouse.EmHouse_MoveInBll;
import bll.EmHouse.EmHouse_addBll;
import bll.Embase.Emba_Contactbll;
import bll.Embase.EmbaseListBll;
import bll.Embase.EmbaseLogin_AddBll;
import bll.SystemControl.SystLogInfoBll;

//雇员福利部入职页面
public class EmHouse_addController {
	private EmHouseChangeModel ecm = new EmHouseChangeModel();
	private List<EmHouseCpp> cpList = new ListModelList<>();
	private List<EmbaseModel> eblist = new ListModelList<>();
	private List<CoglistModel> cglist = new ListModelList<>();
	private List<PubCodeConversionModel> listPC = new ListModelList<>();
	private List<String> ownmonthList = new ArrayList<>();
	private EmHouse_addBll bll = new EmHouse_addBll();
	private EmHouse_MoveInBll mbll = new EmHouse_MoveInBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();
	private EmHouse_EditBll ebll = new EmHouse_EditBll();
	private Emcontactinfo emcont = new Emcontactinfo();
	private EmbaseModel emmodel = new EmbaseModel();
	private EmbaseListBll embabll = new EmbaseListBll();
	private Emba_Contactbll ecbll = new Emba_Contactbll();
	private EmbaseLogin_AddBll abll = new EmbaseLogin_AddBll();
	private SystLogInfoBll logBll = new SystLogInfoBll();
	private Integer embaId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("embaId").toString());

	private Integer gjjfwownmonth; // 公积金服务起始月
	private Integer gjjtzownmont; // 公积金台账月
	private boolean house_ifStop;

	private String username = UserInfo.getUsername();

	private Window winC;

	private boolean dis = true;
	private emmonthModel monthModel;
	private boolean radixRead = false;
	private boolean flashWindow = false;
	private String error = "";
	private Integer tsday;

	public EmHouse_addController() {

		setEblist(embaId);
		setListPC();
		EmbaseLogin_AddBll embll = new EmbaseLogin_AddBll();

		emmodel = embll.embaseinfo(embaId).get(0);
		// emmodel = embabll.getEmbaseInfo("and a.emba_id=" + embaId + "");

		if (!"".equals(emmodel.getEmba_title())) {
			ecm.setEmhc_title(emcont.getEmzt_title());
		}

		if (!"".equals(emmodel.getEmba_wifename())) {
			ecm.setEmhc_wifename(emmodel.getEmba_wifename());
		}

		if (!"".equals(emmodel.getEmba_wifeidcard())) {
			ecm.setEmhc_wifeidcard(emmodel.getEmba_wifeidcard());
		}

		if (!"".equals(emmodel.getEmba_title())) {
			ecm.setEmhc_title(emmodel.getEmba_title());
		}
		if (!"".equals(emmodel.getEmba_mobile())) {
			ecm.setEmhc_mobile(emmodel.getEmba_mobile());
		}

		// ecm.setEmhc_wifename(emcont.getEmzt_m_name());
		// ecm.setEmhc_wifeidcard(emcont.getEmzt_m_idcard());
		ecm.setEmhc_mobile(emmodel.getEmba_mobile());

		if (eblist.size() > 0) {
			cglist = ebll.gjjItemInfo(eblist.get(0).getGid());
			if (cglist.size() > 0) {
				if (cglist.get(0).getCoco_house() != null
						&& !cglist.get(0).getCoco_house().equals("")) {

					ecm.setCid(eblist.get(0).getCid());
					ecm.setGid(eblist.get(0).getGid());
					if (eblist.get(0).getEmba_house_radix() != null) {
						ecm.setEmhc_radix(eblist.get(0).getEmba_house_radix()
								.doubleValue());
					}
					if (eblist.get(0).getEmba_gjjidcard() != null
							&& !eblist.get(0).getEmba_gjjidcard().equals("")) {
						ecm.setEmhc_idcard(eblist.get(0).getEmba_gjjidcard());
					} else {
						ecm.setEmhc_idcard(eblist.get(0).getEmba_idcard());
					}

					ecm.setEmhc_company(eblist.get(0).getCoba_company());
					ecm.setEmhc_shortname(eblist.get(0).getCoba_shortname());
					ecm.setEmhc_name(eblist.get(0).getEmba_gjjuname());

					ecm.setEmhc_mobile(eblist.get(0).getEmba_mobile());
					ecm.setEmhc_wifename(eblist.get(0).getEmba_wifename());
					ecm.setEmhc_wifeidcard(eblist.get(0).getEmba_wifeidcard());

					if (Executions.getCurrent().getArg().get("emmonthmodel") != null) {
						monthModel = (emmonthModel) Executions.getCurrent()
								.getArg().get("emmonthmodel");
						radixRead = true;

						ecm.setOwnmonth(monthModel.getEmba_emhb_ownmonth());
						if (eblist.get(0).getEmba_house_cpp() != null
								&& !eblist.get(0).getEmba_house_cpp()
										.equals("")) {
							ecm.setEmhc_cpp2(String.valueOf(((int) (eblist.get(
									0).getEmba_house_cpp() * 100)))
									+ "%");
							gjjtzownmont = monthModel.getGjjtzownmont();
							gjjfwownmonth = monthModel.getGjjfwownmonth();
						}

						if (eblist.get(0).getEmba_houseid() != null) {
							ecm.setEmhc_houseid(eblist.get(0).getEmba_houseid());
						}
						ecm.setEmhc_ifdeclare(0);
						ecm.setConfirm(false);
					} else {
						GjjOwnmonth();
						ecm.setEmhc_houseid(null);
						dis = false;
						ecm.setEmhc_ifdeclare(4);
						ecm.setConfirm(true);
					}

					ownmonthList.add(ecm.getOwnmonth().toString());
					if (monthModel == null) {
						ownmonthList.add(DateStringChange
								.ownmonthAddoneMonth(ecm.getOwnmonth()
										.toString()));
					}

					setCpList(ecm.getCid(), ecm.getGid(), ecm.getOwnmonth());
					for (EmHouseCpp m : cpList) {
						if (ecm.getEmhc_cpp2() != null
								&& ecm.getEmhc_cpp2().equals(m.getCpName())) {
							ecm.setEmhc_companyid(m.getCompanyid());
							ecm.setJc(m.getJc());
						}
					}
					emcont = ecbll.getemcontactmodel(ecm.getGid());
					if (emcont != null) {
						if (!"".equals(emcont.getEmzt_title())
								&& emcont.getEmzt_title() != null) {
							ecm.setEmhc_title(emcont.getEmzt_title());
						}
						if (!"".equals(emcont.getEmzt_m_name())
								&& emcont.getEmzt_m_name() != null) {
							ecm.setEmhc_wifename(emcont.getEmzt_m_name());
						}
						if (!"".equals(emcont.getEmzt_m_idcard())
								&& emcont.getEmzt_m_idcard() != null) {
							ecm.setEmhc_wifeidcard(emcont.getEmzt_m_idcard());
						}

						ecm.setEmhc_mobile(emmodel.getEmba_mobile());
					}
				}
			}
		}

		if (!ebll.gjjItem(eblist.get(0).getGid())) {
			error = error.equals("") ? "员工未选择公积金项目!" : error;
			flashWindow = true;

		}

		if (bll.houseupdate(eblist.get(0).getGid(), ecm.getEmhc_idcard(),
				ecm.getOwnmonth())) {
			error = error.equals("") ? "员工已参保!" : error;
			flashWindow = true;

		}

		List<EmHouseChangeModel> ehcmList = new ListModelList<>();
		EmHouseChangeModel em = new EmHouseChangeModel();

		em.setGid(eblist.get(0).getGid());
		if (eblist.get(0).getEmba_gjjidcard() != null
				&& !eblist.get(0).getEmba_gjjidcard().equals("")) {
			em.setEmhc_idcard(eblist.get(0).getEmba_gjjidcard());
		} else {
			em.setEmhc_idcard(eblist.get(0).getEmba_idcard());
		}

		em.setEmhc_houseid(eblist.get(0).getEmba_houseid());
		em.setEmhc_ifdeclare(3);

		Integer month = sbll.nowmonth2(em.getGid());

		if (month > 0) {

			if (em.getOwnmonth() == null) {
				em.setOwnmonth(month);
			}

		} else {

			error = error.equals("") ? "员工所属合同公积金信息不完整!" : error;
			flashWindow = true;

		}

		ehcmList = ebll.getChangeList(em);
		if (ehcmList.size() > 0) {

			error = error.equals("") ? "员工有未处理退回记录." : error;
			flashWindow = true;

		}

	}

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		winC = winD;
		RedirectUtil util = new RedirectUtil();
		if (flashWindow) {
			Messagebox.show(error, "操作提示", Messagebox.OK, Messagebox.ERROR);
			util.refreshEntryThirdUrl("/EmHouse/Emhouse_Index.zul");
			util.refreshEmUrl("/EmHouse/Emhouse_Index.zul");
		}

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

	@Command
	public void link() {
		RedirectUtil u = new RedirectUtil();
		u.indexAddTab("/Embase/Embase_yfzxinfo.zul?gid=" + ecm.getGid() + "",
				"雇员服务中心");
	}

	@Command
	@NotifyChange("ecm")
	public void updateCompanyid() {

		for (EmHouseCpp em : cpList) {
			if (em.getCpName().equals(ecm.getEmhc_cpp2())) {
				ecm.setEmhc_companyid(em.getCompanyid());
				ecm.setJc(em.getJc());
			}
		}
	}

	@Command
	@NotifyChange("dis")
	public void must() {
		if (ecm.getEmhc_houseid() != null
				&& (ecm.getEmhc_houseid().equals(0) || ecm.getEmhc_houseid()
						.equals("0"))) {
			ecm.setEmhc_houseid(null);
		}
		if (ecm.getEmhc_houseid() != null && !ecm.getEmhc_houseid().equals("")) {
			dis = false;
		} else {
			dis = true;
		}
	}

	@Command("submit")
	public void submit() {

		Combobox cp = (Combobox) winC.getFellow("cpp");
		Combobox op = (Combobox) winC.getFellow("opp");

		house_ifStop = sbll.gjjOnair(ecm.getCid(), ecm.getGid(), gjjtzownmont,
				null); // 公积金接单日

		if (house_ifStop) {

			if (monthModel != null
					&& monthModel.getEmba_emhb_ownmonth() <= Integer
							.valueOf(DateStringChange.getOwnmonth())) {
				Messagebox.show("已过公积金的截单日，请退单或等待后道开放操作。", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

		} else if (monthModel != null
				&& monthModel.getEmba_emhb_ownmonth() < Integer
						.valueOf(DateStringChange.getOwnmonth())) {

			Messagebox.show("已过公积金的截单日，请退单或等待后道开放操作。", "提示", Messagebox.OK,
					Messagebox.ERROR);
			return;

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
		if (ecm.getEmhc_radix() == null || ecm.getEmhc_radix() == 0) {
			Messagebox
					.show("缴存金额不正确!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (ecm.getEmhc_houseid() != null && !ecm.getEmhc_houseid().equals("")) {
			ecm.setEmhc_houseid(StringFormat.replaceSpace(ecm.getEmhc_houseid()));
			if (ecm.getEmhc_houseid().length() != 11) {
				Messagebox.show("公积金号异常.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		} else {
			if (ecm.getEmhc_hj() == null || ecm.getEmhc_hj().equals("")) {
				Messagebox.show("请选择户籍!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (ecm.getEmhc_title() == null || ecm.getEmhc_title().equals("")) {
				Messagebox.show("请选择职称!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (ecm.getEmhc_degree() == null || ecm.getEmhc_degree().equals("")) {
				Messagebox.show("请选择学历!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			
		}

		if (ecm.getEmhc_mobile() != null && !ecm.getEmhc_mobile().equals("")) {
			if (ecm.getEmhc_mobile().length() != 11) {
				Messagebox.show("手机号码为11位!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}

		ecm.setEmhc_cpp(Double.valueOf(cp.getSelectedItem().getValue()
				.toString()));
		ecm.setEmhc_opp(Double.valueOf(op.getSelectedItem().getValue()
				.toString()));

		ecm.setEmhc_single(bll.getsingle(ecm.getGid()));
		ecm.setEmhc_addname(username);

		if (bll.houseupdate(ecm.getGid(), ecm.getEmhc_idcard(),
				ecm.getOwnmonth())) {
			Messagebox.show("员工已参保!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (ecm.getEmhc_cpp() > 0.12) {
			Messagebox.show("公积金比例范围是[0.05-0.12].", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		
		if (ecm.getEmhc_houseid() != null
				&& !ecm.getEmhc_houseid().equals("")) {
			ecm.setEmhc_change("调入");
		}else {
			ecm.setEmhc_change("新增");
		}
		if (ebll.houseChangeSame2(ecm)) {
			Messagebox.show("员工当月已有未处理新增调入数据.", "操作提示", Messagebox.OK, Messagebox.ERROR);
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
							if (ecm.getEmhc_houseid() != null
									&& (ecm.getEmhc_houseid().equals(0) || ecm
											.getEmhc_houseid().equals("0"))) {
								ecm.setEmhc_houseid(null);
							}
							if (ecm.getEmhc_computerid() == null
									|| ecm.getEmhc_computerid().equals("")) {
								ecm.setEmhc_computerid("000000000");
							}
							Integer i = 0;
						
							if (ecm.getEmhc_houseid() != null
									&& !ecm.getEmhc_houseid().equals("")) {
								i = mbll.moveinEmhouse(ecm);
								logBll.addLog(null, ecm.getCid(), ecm.getGid(), "员工公积金", "员工调入公积金", UserInfo.getUsername());
								Log4jInit.toLog("员工调入公积金:"+ecm.getGid()+",添加人:"+UserInfo.getUsername());
							} else {
								i = bll.addEmhouse(ecm);
								logBll.addLog(null, ecm.getCid(), ecm.getGid(), "员工公积金", "员工新增公积金", UserInfo.getUsername());
								Log4jInit.toLog("员工新增公积金:"+ecm.getGid()+",添加人:"+UserInfo.getUsername());
							}

							if (i > 0) {
								
								Messagebox.show("操作成功!", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);

								RedirectUtil util = new RedirectUtil();
								util.refreshEntryThirdUrl("/EmHouse/Emhouse_Index.zul");
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

	public void setCpList(Integer cid, Integer gid, Integer ownmonth) {
		this.cpList = bll.getCpp(cid, gid, ownmonth);
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

	public void setOwnmonthList(Integer cid, Integer gid, Integer ownmonth) {
		this.ownmonthList = sbll.ownmonthlist("update", cid, gid, ownmonth);
	}

	public List<EmbaseModel> getEblist() {
		return eblist;
	}

	public void setEblist(Integer id) {
		this.eblist = bll.getembaseInfo(id);
	}

	public boolean isDis() {
		return dis;
	}

	public void setDis(boolean dis) {
		this.dis = dis;
	}

	public boolean isRadixRead() {
		return radixRead;
	}

	public void setRadixRead(boolean radixRead) {
		this.radixRead = radixRead;
	}

}
