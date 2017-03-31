package Controller.Embase;

import impl.CheckStringImpl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.East;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.CheckStringService;
import bll.CoServePromise.CoServePromiseSelectBll;
import bll.EmSalary.EmSalary_SalarySelBll;
import bll.EmZYT.EmZYT_OperateBll;
import bll.Embase.EmBase_OnBoardBll;
import bll.Embase.Emba_Contactbll;
import bll.Embase.EmbaseLogin_AddBll;
import bll.Embase.EmbaseNotinListBll;
import bll.SystemControl.SystLogInfoBll;
import bll.CoBase.CoBase_SelectBll;

import Model.CoBaseModel;
import Model.CoBaseServePromiseModel;
import Model.CoCompactCoofferModel;
import Model.CoCompactModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Model.CoglistModel;
import Model.EmZYTModel;
import Model.EmbaseModel;
import Model.EmbaseNotInModel;
import Model.Emcontactinfo;
import Model.PubNationalityModel;
import Model.SystLogModel;
import Util.DateStringChange;
import Util.EntitiesComparedUtils;
import Util.IdcardUtil;
import Util.UserInfo;

public class EmBase_AddController {

	private CoBaseModel cbm = null;
	private EmbaseModel ebm = new EmbaseModel();
	private EmbaseModel oldebm = new EmbaseModel();
	private EmbaseModel ebmnotin;
	private List<EmbaseModel> list = new ListModelList<>();
	private DateStringChange dsc = new DateStringChange();

	// 报价单ID列表
	private Emcontactinfo emcont = new Emcontactinfo();
	// 生成报价单信息
	private List<CoOfferListModel> cflList = new ListModelList<CoOfferListModel>();

	private EmBase_OnBoardBll bll = new EmBase_OnBoardBll();

	private List<CoBaseServePromiseModel> coplist = new ArrayList<CoBaseServePromiseModel>();
	private CoServePromiseSelectBll copbll = new CoServePromiseSelectBll();

	private Window winc;

	private Object embaId;
	private String winId = "winAdd"
			+ java.util.concurrent.ThreadLocalRandom.current().nextLong(1, 999);

	private List<PubNationalityModel> pnList;// 国籍
	private boolean mod = false;
	private Integer cid = 0;
	private String company = "";
	private String id = "";
	private String sd1 = "";
	private String sd2 = "";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	private Emba_Contactbll ecbll = new Emba_Contactbll();
	private Textbox tbidcard;
	private Textbox tbidcardclass;
	private Textbox tbphone;
	private Textbox tbmobile;
	private Datebox dbbirth;
	private Datebox dbinciic;
	private Grid gd;
	private Boolean ifembasenotin;
	private int embanotin_id = 0;
	private Integer emba_id;

	// 合同报价单列表
	private List<CoCompactModel> compactlist = new ListModelList<>();
	private List<CoOfferModel> quotelist = new ListModelList<>();
	private List<CoOfferListModel> itemlist = new ListModelList<>();
	private List<CoCompactCoofferModel> productlist = new ListModelList<>();
	private List<CoOfferListModel> cityList = new ListModelList<>();

	private String compactid;
	private String compactTips;
	private String quotename;
	private String itemclass;
	private String itemName;
	private String city;

	// 智翼通接口传入值
	private EmZYTModel emztM;
	private Date birthDate = null;// 生日
	private Date inDate = null;// 入职时间
	private Integer way;

	// 预增接口
	private List<EmbaseNotInModel> embasenotinlist = new ArrayList<>();
	private EmbaseNotinListBll embasenotinbll = new EmbaseNotinListBll();

	public EmBase_AddController() {

		// 入口2：员工列表 gid;embaid way:0 //入口3：任务单 embaid;tapr_id;读取

		if (Executions.getCurrent().getArg().get("embaId") != null) {
			embaId = Executions.getCurrent().getArg().get("embaId");

			if (Executions.getCurrent().getArg().get("daid") != null
					|| Executions.getCurrent().getArg().get("cid") != null
					|| Executions.getCurrent().getArg().get("embanotin_id") != null) {
				embanotin_id = Integer.parseInt(Executions.getCurrent()
						.getArg().get("embanotin_id").toString());
				cid = Integer.parseInt(Executions.getCurrent().getArg()
						.get("cid").toString());
				ifembasenotin = true;
				EmbaseLogin_AddBll notinbll = new EmbaseLogin_AddBll();
				ebmnotin = notinbll.getEmbaseLoginInfo(embanotin_id).get(0);

			}
		}

		else {

			// 入口4：智翼通 type：1
			try {
				way = Integer.parseInt(Executions.getCurrent()
						.getParameter("way").toString());
			} catch (Exception e) {
				way = 0;
			}

			try {
				// 获取智翼通接口model数据
				if (way == 1) {
					emztM = (EmZYTModel) Executions.getCurrent().getAttribute(
							"emztM");
					embaId = Executions.getCurrent().getAttribute("embaId");
				} else {
					emztM = (EmZYTModel) Executions.getCurrent().getArg()
							.get("emztM");
					if (emztM.getEmba_id() != 0) {
						embaId = emztM.getEmba_id();
					}
				}
			} catch (Exception e) {
				emztM = null;
			}

			// 任务单
			if (Executions.getCurrent().getArg().get("daid") != null) {
				embaId = Integer.parseInt(Executions.getCurrent().getArg()
						.get("daid").toString());
			}

			// 入口1：入职第一步 cid type:0
			if (Executions.getCurrent().getArg().get("cobaM") != null) {
				cbm = (CoBaseModel) Executions.getCurrent().getArg()
						.get("cobaM");
			}

			if (cbm != null) {
				cid = cbm.getCid();
				// System.out.println("cid:" + cid);
				company = cbm.getCoba_company();
				ebm.setCid(cid);
				ebm.setCoba_company(cbm.getCoba_company());

				if (ebm.getEmba_idcardclass() == null
						|| ebm.getEmba_idcardclass().equals("")) {
					ebm.setEmba_idcardclass("身份证");
					ebm.setEmba_nationality("中国");
				}

				// ebm.setEmba_emsb_foreigner("否");
				ebm.setEmba_sb_place("本地");
				ebm.setEmba_house_place("本地");
			}

			// 入口5：员工预增第一步 type：2

			try {
				cid = Integer.parseInt(Executions.getCurrent().getArg()
						.get("cid").toString());
				embanotin_id = Integer.parseInt(Executions.getCurrent()
						.getArg().get("embanotin_id").toString());
				CoBase_SelectBll cobasebll = new CoBase_SelectBll();
				cbm = cobasebll.getCobaseinfoforall(cid.toString())
						.get(0);
				embasenotinlist = embasenotinbll
						.getembanotinListall(embanotin_id);

				if (embasenotinlist.size() > 0) {

					ifembasenotin = true;

					if (ebmnotin != null) {
						EmbaseLogin_AddBll notinbll = new EmbaseLogin_AddBll();
						ebmnotin = notinbll.getEmbaseLoginInfo(embanotin_id)
								.get(0);

						notinbll.EmbaseloginUpdatestate(embanotin_id, 1);

						ebmnotin.setEmba_id(ebmnotin.getEmba_type());

						Integer i = bll.modInfo(ebmnotin);

					}
					// 读取预增信息
					ebm.setEmba_name(embasenotinlist.get(0).getEmba_name());
					ebm.setEmba_idcard(embasenotinlist.get(0).getEmba_idcard());
					ebm.setEmba_idcardclass(embasenotinlist.get(0)
							.getEmba_idcardclass());

					ebm.setEmba_sex(embasenotinlist.get(0).getEmba_sex());
					ebm.setEmba_phone(embasenotinlist.get(0).getEmba_phone());
					ebm.setEmba_mobile(embasenotinlist.get(0).getEmba_mobile());
					ebm.setEmba_email(embasenotinlist.get(0).getEmba_email());

					ebm.setEmba_fileinclass(embasenotinlist.get(0)
							.getEmba_fileinclass());
					ebm.setEmba_filedebts(embasenotinlist.get(0)
							.getEmba_filedebts());
					ebm.setEmba_filehj(embasenotinlist.get(0).getEmba_filehj());
					ebm.setEmba_excompanystate(embasenotinlist.get(0)
							.getEmba_excompanystate());
					ebm.setCid(cid);
					idcardinfo();

				} else {
					ifembasenotin = false;
				}
			} catch (Exception e) {
				ifembasenotin = false;
			}

		}

		// 如果embaId不为空,获取员工入职任务单流程ID
		if (embaId != null && !embaId.equals("")) {
			List<EmbaseModel> ebList = bll.embaseinfo(Integer.valueOf(embaId
					.toString()));
			if (ebList.size() > 0) {
				ebm.setEmba_tapr_id(ebList.get(0).getEmba_tapr_id());

			}
		}

		// 获取国籍
		EmSalary_SalarySelBll esBll = new EmSalary_SalarySelBll();
		pnList = esBll.getPubNationalityList("");

		if (emztM != null && way != 1) {
			// 判断是否已经操作过新增
			// 调用智翼通接口数据携带过来方法
			getEmZYTInfo();
			if (embaId != null && 0 != (int) embaId) {
				mod = true;
			}
			ebm.setEmba_sb_place("本地");
			ebm.setEmba_house_place("本地");
		}

		if (embaId != null) {
			mod = true;
			setList(Integer.valueOf(embaId.toString()));
			if (list.size() > 0) {
				ebm = list.get(0);
				oldebm=(EmbaseModel)ebm.clone();
				if (ebm.getEmba_state() > 0) {
					cid = ebm.getCid();
					// ebm.setEmba_emsb_foreigner("否");
					company = ebm.getCoba_company();
				} else {
					if (Executions.getCurrent().getArg().get("cxrz") != null) {
						cid = Integer.parseInt(Executions.getCurrent().getArg()
								.get("cxrz").toString());
					}
				}

			}
		}

		if (ebm.getGid() != null) {
			emcont = ecbll.getemcontactmodel(ebm.getGid());
		}

		// 智翼通接口数据携带档案情况
		if (emztM != null) {
			emcont.setEmzt_iffileservice(getEmZYTifFile());
		}

		coplist = copbll.getPromiseList("and cid=" + cid);
		// System.out.println(emztM.getCid());
		// System.out.println("1111");
		//
		// System.out.println(cid);
		initQueryInfo();
		List<CoglistModel> l = bll.coglistInfo(ebm.getGid());
		for (CoglistModel m : l) {
			CoOfferListModel m1 = new CoOfferListModel();
			m1.setCoof_id(m.getCoof_id());
			m1.setColi_id(m.getCgli_coli_id());
			m1.setCoof_name(m.getCoof_name());
			m1.setColi_copr_id(m.getColi_copr_id());
			m1.setColi_pclass(m.getColi_pclass());
			m1.setCity(m.getCity());
			m1.setColi_name(m.getColi_name());
			m1.setColi_fee2(m.getColi_fee2());
			m1.setColi_group_count(m.getColi_group_count());
			m1.setColi_group_id(m.getColi_group_id());
			m1.setColi_parid(m.getColi_parid());
			m1.setColi_cpfc_name(m.getColi_cpfc_name());
			m1.setCoco_shebao(m.getCoco_shebao());
			m1.setCoco_house(m.getCoco_house());
			m1.setCoco_cpp(m.getCoco_cpp());
			m1.setCopc_id(m.getCopc_id());
			m1.setCoco_compacttype(m.getCoco_compacttype());

			m1.setSt(m.getCgli_startdate());
			m1.setSt2(m.getCgli_startdate2());
			m1.setChecked(true);
			cflList.add(m1);
		}
		sort();
	}

	/**
	 * @Title: initQueryInfo
	 * @Description: TODO(初始化报价单项目查询条件列表)
	 * @return void 返回类型
	 * @throws
	 */
	public void initQueryInfo() {
		if (cid != null && !cid.equals("")) {
			compactlist = bll.getCompactByCid(cid);
			if (compactlist.size() > 0) {
				if (compactlist.size() == 1) {
					compactid = compactlist.get(0).getCoco_compactid();
					quotelist = bll.getCoofferByCoId(cid, compactlist.get(0)
							.getCoco_id());
					productlist = bll.getcoofferMenu(quotelist);

					List<CoOfferModel> l = new ListModelList<>();
					if (quotelist.size() > 0) {
						if (quotelist.size() == 1) {
							l.add(quotelist.get(0));
							quotename = quotelist.get(0).getCoof_name();
						} else {
							for (CoOfferModel m : quotelist) {
								l.add(m);
							}
						}
					}
					cityList = bll.itemCity(cid, compactlist, l, null);
					itemlist = bll.getCoofferlistBylist(cid, compactlist.get(0)
							.getCoco_id(), l, null, null, null);

				} else {
					quotelist = bll.getCoofferByCid(cid);
					productlist = bll.getcoofferMenu(quotelist);
					cityList = bll.itemCity(cid, null, null, null);
				}

			}
		}
	}

	@Command
	@NotifyChange({ "quotelist", "productlist", "itemlist", "quotename",
			"itemclass", "cityList", "city" })
	public void updateCompact() {
		Combobox cb = (Combobox) winc.getFellow("compactid");
		Integer id = null;
		List<CoOfferModel> l = new ListModelList<>();
		List<CoCompactModel> l2 = new ListModelList<>();
		if (cb.getSelectedItem() != null) {

			CoCompactModel m = cb.getSelectedItem().getValue();
			id = m.getCoco_id();
			l2.add(m);
			quotelist = bll.getCoofferByCoId(cid, id);
			quotename = "";
			if (quotelist.size() > 0) {
				if (quotelist.size() == 1) {
					l.add(quotelist.get(0));
					quotename = quotelist.get(0).getCoof_name();
				} else {
					for (CoOfferModel m1 : quotelist) {
						l.add(m1);
					}
				}
			}
			itemclass = "";
			city = "";
			productlist = bll.getcoofferMenu(l);
			cityList = bll.itemCity(cid, l2, l, null);
		}

		itemlist = bll.getCoofferlistBylist(cid, id, l, null, city, itemName);
	}

	@Command
	@NotifyChange({ "productlist", "itemlist", "itemclass", "cityList" })
	public void updateQuote() {
		Combobox cbcompact = (Combobox) winc.getFellow("compactid");
		Combobox cb = (Combobox) winc.getFellow("quoteid");
		Integer id = null;
		List<CoOfferModel> l = new ListModelList<>();
		List<CoCompactModel> l2 = new ListModelList<>();
		if (cbcompact.getSelectedItem() != null) {
			id = ((CoCompactModel) cbcompact.getSelectedItem().getValue())
					.getCoco_id();
			CoCompactModel m = cbcompact.getSelectedItem().getValue();
			l2.add(m);
		}

		if (cb.getSelectedItem() != null) {

			CoOfferModel m = cb.getSelectedItem().getValue();
			l.add(m);
			itemclass = "";
			city = "";
			productlist = bll.getcoofferMenu(quotelist);
			cityList = bll.itemCity(cid, l2, l, null);
		}
		itemlist = bll.getCoofferlistBylist(cid, id, l, null, city, itemName);
	}

	@Command
	@NotifyChange({ "itemlist", "cityList" })
	public void updateProductClass() {
		Combobox cbcompact = (Combobox) winc.getFellow("compactid");
		Combobox cbquote = (Combobox) winc.getFellow("quoteid");
		Combobox cb = (Combobox) winc.getFellow("pclassid");
		Integer id = null;
		List<CoOfferModel> l = new ListModelList<>();
		List<CoCompactCoofferModel> l2 = new ListModelList<>();
		List<CoCompactModel> l3 = new ListModelList<>();
		if (cbcompact.getSelectedItem() != null) {
			id = ((CoCompactModel) cbcompact.getSelectedItem().getValue())
					.getCoco_id();
			CoCompactModel m = cbcompact.getSelectedItem().getValue();
			l3.add(m);
		}
		if (cbquote.getSelectedItem() != null) {
			CoOfferModel m = cbquote.getSelectedItem().getValue();
			l.add(m);
		}

		if (cb.getSelectedItem() != null) {
			CoCompactCoofferModel m = cb.getSelectedItem().getValue();
			l2.add(m);

		}
		city = "";
		cityList = bll.itemCity(cid, l3, l, l2);
		itemlist = bll.getCoofferlistBylist(cid, id, l, l2, city, itemName);
	}

	@Command
	@NotifyChange({ "itemlist" })
	public void updateCity() {
		Combobox cbcompact = (Combobox) winc.getFellow("compactid");
		Combobox cbquote = (Combobox) winc.getFellow("quoteid");
		Combobox cbclass = (Combobox) winc.getFellow("pclassid");
		Integer id = null;
		List<CoOfferModel> l = new ListModelList<>();
		List<CoCompactCoofferModel> l2 = new ListModelList<>();
		List<CoCompactModel> l3 = new ListModelList<>();
		if (cbcompact.getSelectedItem() != null) {
			id = ((CoCompactModel) cbcompact.getSelectedItem().getValue())
					.getCoco_id();
			CoCompactModel m = cbcompact.getSelectedItem().getValue();
			l3.add(m);
		}
		if (cbquote.getSelectedItem() != null) {
			CoOfferModel m = cbquote.getSelectedItem().getValue();
			l.add(m);
		}

		if (cbclass.getSelectedItem() != null) {
			CoCompactCoofferModel m = cbclass.getSelectedItem().getValue();
			l2.add(m);
		}

		itemlist = bll.getCoofferlistBylist(cid, id, l, l2, city, itemName);
	}

	@Command
	@NotifyChange({ "itemlist" })
	public void updateItem() {
		Combobox cbcompact = (Combobox) winc.getFellow("compactid");
		Combobox cbquote = (Combobox) winc.getFellow("quoteid");
		Combobox cbclass = (Combobox) winc.getFellow("pclassid");
		Integer id = null;
		List<CoOfferModel> l = new ListModelList<>();
		List<CoCompactCoofferModel> l2 = new ListModelList<>();
		if (cbcompact.getSelectedItem() != null) {
			id = ((CoCompactModel) cbcompact.getSelectedItem().getValue())
					.getCoco_id();
		}
		if (cbquote.getSelectedItem() != null) {
			CoOfferModel m = cbquote.getSelectedItem().getValue();
			l.add(m);
		}

		if (cbclass.getSelectedItem() != null) {
			CoCompactCoofferModel m = cbclass.getSelectedItem().getValue();
			l2.add(m);
		}

		itemlist = bll.getCoofferlistBylist(cid, id, l, l2, city, itemName);

	}

	// 智翼通接口携带档案情况
	public String getEmZYTifFile() {
		String result = "";
		String emztIfFile = emztM.getEmzt_iffile();
		if (emztM != null) {
			if (emztIfFile != null && !"".equals(emztIfFile)
					&& !"NULL".equals(emztIfFile)) {
				// 包含下面的字眼表示无档案保管
				if (emztIfFile.indexOf("不") != -1
						|| emztIfFile.indexOf("无") != -1
						|| emztIfFile.indexOf("没") != -1) {
					result = "否";
				} else {
					result = "是";
				}
			} else {
				result = "否";
			}
		}
		return result;
	}

	// 将智翼通接口数据携带过来
	public void getEmZYTInfo() {
		if (emztM != null) {
			if (emztM.getEmba_id() != 0) {
				embaId = emztM.getEmba_id();
			}

			// 姓名
			if (!"".equals(emztM.getEmzt_t_name())
					&& emztM.getEmzt_t_name() != null) {
				ebm.setEmba_name(emztM.getEmzt_t_name());
			} else {
				ebm.setEmba_name(emztM.getEmzt_name());
			}

			// 身份证
			if (!"".equals(emztM.getEmzt_t_idcard())
					&& emztM.getEmzt_t_idcard() != null) {
				ebm.setEmba_idcard(emztM.getEmzt_t_idcard());
			} else {
				ebm.setEmba_idcard(emztM.getEmzt_idcard());
			}

			ebm.setEmba_sb_place("本地");
			ebm.setEmba_house_place("本地");
			ebm.setEmba_sex(emztM.getEmzt_sex()); // 性别
			ebm.setEmba_phone(emztM.getEmzt_tel()); // 家庭电话
			ebm.setEmba_mobile(emztM.getEmzt_mobile()); // 手机
			ebm.setEmba_folk(emztM.getEmzt_folk()); // 民族
			ebm.setEmba_computerid(emztM.getEmzt_computerid()); // 社保电脑号
			ebm.setEmba_houseid(emztM.getEmzt_houseid()); // 公积金号
			ebm.setEmba_marital(emztM.getEmzt_marital()); // 婚姻状况
			ebm.setEmba_education(emztM.getEmzt_education()); // 学历
			ebm.setEmba_hjadd(emztM.getEmzt_hjadd()); // 户籍地址
			ebm.setEmba_fileplace(emztM.getEmzt_fileplace()
					+ emztM.getEmzt_ofileplace()); // 原档案存放地
			ebm.setEmba_email(emztM.getEmzt_email()); // email
			ebm.setEmba_zytid(emztM.getEmzt_zgid()); // 智翼通员工编号
			ebm.setEmba_zytwtgid(emztM.getEmzt_wtgid()); // 智翼通委托员工编号

			if (emztM.getEmzt_idcardclass().equals("身份证")
					&& ("".equals(ebm.getEmba_birth()) || ebm.getEmba_birth() == null)) {
				setBirthDate(IdcardUtil.getBirthByIdCard2(emztM
						.getEmzt_idcard()));// 生日
			}
			if (emztM.getEmzt_idcardclass().equals("身份证")
					&& ("".equals(ebm.getEmba_sex()) || ebm.getEmba_sex() == null)) {
				String sex ="";
				sex = IdcardUtil.getGenderByIdCard(emztM.getEmzt_idcard());
				ebm.setEmba_sex(sex.equals("未知") ? "" : sex);
			}
			if ("".equals(ebm.getEmba_indate()) || ebm.getEmba_indate() == null) {
				setInDate(dsc
						.StringtoDate(emztM.getEmzt_uptime(), "yyyy-MM-dd"));// 入职时间
			}

		}
	}

	@Command
	public void readInfo(@BindingParam("a") Window win) {
		if (winc == null) {
			winc = win;
		}

		tbidcard = (Textbox) winc.getFellow("idcard");
		tbidcardclass = (Textbox) winc.getFellow("idcardclass");
		dbinciic = (Datebox) winc.getFellow("inciicdate");
		dbbirth = (Datebox) winc.getFellow("birth");
		tbphone = (Textbox) winc.getFellow("phone");
		tbmobile = (Textbox) winc.getFellow("mobile");
		gd = (Grid) winc.getFellow("coofferlist");
		if (ebm.getEmba_birth() != null) {
			try {
				dbbirth.setValue(sdf2.parse(ebm.getEmba_birth()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (ebm.getEmba_indate() != null) {
			try {
				dbinciic.setValue(sdf2.parse(ebm.getEmba_indate()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (UserInfo.getDepID().equals("2") || UserInfo.getDepID().equals("6")) {
			if (coplist.size() <= 0) {
				winc.detach();
				Messagebox.show("请先在公司业务中心添加此公司的雇员服务中心约定!", "提示",
						Messagebox.OK, Messagebox.ERROR);

				// win.detach();

				// return;

			} else if (coplist.get(0).getCosp_enty_caliname() == null
					|| coplist.get(0).getCosp_enty_caliname().equals("")) {

				winc.detach();
				Messagebox.show("请先在公司业务中心--公司的雇员服务中心--更新入职联系人!", "提示",
						Messagebox.OK, Messagebox.ERROR);
			}
		}

	}

	@Command("changeNationality")
	@NotifyChange("ebm")
	public void changeNationality() {
		if (tbidcardclass.getValue().equals("身份证")) {
			// 只要证件类型是身份证，国籍自动默认为中国
			ebm.setEmba_nationality("中国");
		} else {
			// 只要证件类型是身份证，国籍自动默认为中国
			ebm.setEmba_nationality("");
		}
	}

	@Command
	@NotifyChange("ebm")
	public void idcardinfo() {
		try {
			String sex = "";
			if (tbidcardclass.getValue().equals("身份证")) {
				if (ebm.getEmba_idcard() != null
						&& IdcardUtil.validateCard(ebm.getEmba_idcard())) {
					sex = IdcardUtil.getGenderByIdCard(ebm.getEmba_idcard());
					ebm.setEmba_sex(sex.equals("未知") ? "" : sex);

					dbbirth.setValue(IdcardUtil.getBirthByIdCard2(ebm
							.getEmba_idcard()));

					// 只要证件类型是身份证，国籍自动默认为中国
					ebm.setEmba_nationality("中国");
				} else {
					ebm.setEmba_sex("");
					dbbirth.setValue(null);
				}

			} else if (tbidcardclass.getValue().equals("港澳证")) {
				if (ebm.getEmba_idcard() != null
						&& !IdcardUtil.validateCard(ebm.getEmba_idcard())) {
					ebm.setEmba_sex("");
					dbbirth.setValue(null);
				}
			} else if (tbidcardclass.getValue().equals("台胞证")) {
				if (ebm.getEmba_idcard() != null
						&& IdcardUtil.validateCard(ebm.getEmba_idcard())) {
					String[] idcardInfo = IdcardUtil.validateIdCard10(ebm
							.getEmba_idcard());
					sex = idcardInfo[1].equals("N") ? "" : idcardInfo[1];
					ebm.setEmba_sex(idcardInfo[1].equals("M") ? "男" : "女");
				} else {
					ebm.setEmba_sex("");
					dbbirth.setValue(null);
				}
			} else {
				ebm.setEmba_sex("");
				dbbirth.setValue(null);
			}
		} catch (Exception e) {

		}
	}

	public void checkIn() {
		if (ebm != null && ebm.getEmba_idcard() != null
				&& !ebm.getEmba_idcard().equals("")) {

			if (bll.judgeEmba(ebm.getEmba_idcard())) {
				Messagebox.show("该员工已存在.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}

	}

	@Command
	@NotifyChange("cflList")
	public void delItem(@BindingParam("a") CoOfferListModel m) {
		if (m.getColi_group_count() > 1) {
			for (int i = 0; i < cflList.size(); i++) {
				if (cflList.get(i).getColi_group_id()
						.equals(m.getColi_group_id())) {
					cflList.remove(cflList.get(i));
					i--;
				}
			}

		} else {
			cflList.remove(m);
		}

		sort();
	}

	@Command
	@NotifyChange("cflList")
	public void checkItem(@BindingParam("a") CoOfferListModel m) {
		System.out.print(m.isChecked());
		if (m.isChecked()) {
			if (m.getCgli_startdate() != null
					&& !m.getCgli_startdate().equals("")
					&& m.getCgli_startdate2() != null
					&& !m.getCgli_startdate2().equals("")) {
				boolean b = false;
				// 删除已有已选项目
				Integer groupId = 0;
				for (int i = 0; i < cflList.size(); i++) {
					if (m.getColi_id().equals(cflList.get(i).getColi_id())) {
						b = true;
						groupId = cflList.get(i).getColi_group_id();
						cflList.remove(i);
						break;
					}
				}
				for (int i = 0; i < cflList.size(); i++) {
					if (cflList.get(i).getColi_group_id().equals(groupId)) {
						cflList.remove(i);
						i--;
					}
				}
				// 添加项目
				cflList.add((CoOfferListModel) m.clone());
				// 寻找组合项目
				if (!b) {

					if (!m.getColi_group_count().equals(1)) {
						for (CoOfferListModel m2 : itemlist) {
							if (!m2.getColi_id().equals(m.getColi_id())
									&& m2.getColi_group_id().equals(
											m.getColi_group_id())) {
								boolean b2 = false;
								for (CoOfferListModel m1 : cflList) {
									if (m2.getColi_id().equals(m1.getColi_id())) {
										b2 = true;
									}
								}
								if (!b2) {
									m2.setChecked(!m.isChecked());
									m2.setCgli_startdate(m.getCgli_startdate());
									m2.setCgli_startdate2(m
											.getCgli_startdate2());
									m2.setSt(m.getSt());
									m2.setSt2(m.getSt2());
									cflList.add((CoOfferListModel) m2.clone());
									m2.setChecked(true);
									BindUtils.postNotifyChange(null, null, m2,
											"checked");
								}
							}
						}
					}
				}
			} else {
				m.setChecked(false);
				BindUtils.postNotifyChange(null, null, m, "checked");
			}
		} else {

			for (int i = 0; i < cflList.size(); i++) {
				if (cflList.get(i).getColi_id().equals(m.getColi_id())) {
					for (int j = 0; j < cflList.size(); j++) {
						if (cflList.get(j).getColi_group_id()
								.equals(m.getColi_group_id())) {
							cflList.remove(j);
							j--;
							for (CoOfferListModel m2 : itemlist) {
								if (!m2.getColi_id().equals(m.getColi_id())
										&& m2.getColi_group_id().equals(
												m.getColi_group_id())) {
									m2.setChecked(false);
									BindUtils.postNotifyChange(null, null, m2,
											"checked");
								}
							}
						}
					}
					return;
				}
			}

		}
		sort();
	}

	public void sort() {
		Collections.sort(cflList, new Comparator<CoOfferListModel>() {
			public int compare(CoOfferListModel arg0, CoOfferListModel arg1) {
				Integer k = 0;
				Integer i = arg0.getColi_pclass().compareTo(
						arg1.getColi_pclass());

				if (i.equals(0)) {
					Integer j = arg0.getColi_group_id().compareTo(
							arg1.getColi_group_id());
					if (j.equals(0)) {
						Integer j2 = arg1.getColi_group_count().compareTo(
								arg0.getColi_group_count());
						if (j2.equals(0)) {
							k = arg0.getColi_name().compareTo(
									arg1.getColi_name());
						} else {
							k = j2;
						}

					} else {
						k = j;
					}

				} else {
					k = i;
				}
				return k;
			}
		});

	}

	@Command
	@NotifyChange("cflList")
	public void checkItemAll(@BindingParam("a") Checkbox cb) {

		for (CoOfferListModel m : itemlist) {
			if (cb.isChecked()) {
				if (m.getCgli_startdate() != null
						&& m.getCgli_startdate2() != null) {
					m.setChecked(cb.isChecked());
					boolean b = false;
					for (CoOfferListModel m1 : cflList) {
						if (m.getColi_id().equals(m1.getColi_id())) {
							// b = true;
							cflList.remove(m1);

						}
					}
					/*
					 * if (!b) { cflList.add((CoOfferListModel) m.clone()); }
					 */
					cflList.add((CoOfferListModel) m.clone());
					BindUtils.postNotifyChange(null, null, m, "checked");
				}
			} else {
				m.setChecked(cb.isChecked());
				boolean b = false;
				/*
				 * for (CoOfferListModel m1 : cflList) { if
				 * (m.getColi_id().equals(m1.getColi_id())) { b = true;
				 * 
				 * } }
				 * 
				 * if (b) { cflList.remove(m); }
				 */
				for (int i = 0; i < cflList.size(); i++) {
					if (m.getColi_id().equals(cflList.get(i).getColi_id())) {
						cflList.remove(i);
						i--;
					}
				}
				BindUtils.postNotifyChange(null, null, m, "checked");
			}

		}
		sort();
	}

	@Command
	@NotifyChange("cflList")
	public void updateSt2(@BindingParam("a") CoOfferListModel m,
			@BindingParam("b") Datebox d) {
		m.setCgli_startdate2(d.getValue());
		m.setSt2(Integer.valueOf(sdf.format(m.getCgli_startdate2())));
		checkItem(m);
	}

	@Command
	@NotifyChange("cflList")
	public void updateSt(@BindingParam("a") CoOfferListModel m,
			@BindingParam("b") Datebox d) {
		m.setCgli_startdate(d.getValue());
		m.setSt(Integer.valueOf(sdf.format(m.getCgli_startdate())));
		checkItem(m);
	}

	@Command
	@NotifyChange("cflList")
	public void copydate1(@BindingParam("a") CoOfferListModel m) {
		Grid gd = (Grid) winc.getFellow("gdItem");
		Datebox b = (Datebox) gd.getCell(itemlist.indexOf(m), 5).getChildren()
				.get(0);
		m.setCgli_startdate2(b.getValue());
		for (int i = itemlist.indexOf(m) + 1; i < itemlist.size(); i++) {
			if (m.getCgli_startdate2() != null
					&& !m.getCgli_startdate2().equals("")) {
				itemlist.get(i).setSt2(
						Integer.valueOf(sdf.format(m.getCgli_startdate2())));
				itemlist.get(i).setCgli_startdate2(m.getCgli_startdate2());
				Datebox db = (Datebox) gd.getCell(i, 5).getChildren().get(0);
				db.setValue(m.getCgli_startdate2());
				checkItem(m);
			}
		}
	}

	@Command
	@NotifyChange("cflList")
	public void copydate2(@BindingParam("a") CoOfferListModel m) {
		Grid gd = (Grid) winc.getFellow("gdItem");
		Datebox b = (Datebox) gd.getCell(itemlist.indexOf(m), 6).getChildren()
				.get(0);
		m.setCgli_startdate(b.getValue());
		for (int i = itemlist.indexOf(m) + 1; i < itemlist.size(); i++) {
			if (m.getCgli_startdate() != null
					&& !m.getCgli_startdate().equals("")) {
				itemlist.get(i).setSt(
						Integer.valueOf(sdf.format(m.getCgli_startdate())));
				itemlist.get(i).setCgli_startdate(m.getCgli_startdate());
				Datebox db = (Datebox) gd.getCell(i, 6).getChildren().get(0);
				db.setValue(m.getCgli_startdate());
				checkItem(m);
				/*
				 * BindUtils.postNotifyChange(null, null, itemlist.get(i),
				 * "cgli_startdate");
				 */
			}
		}
	}

	// 判断date控件的值是否为空
	public boolean DateEmpty(Datebox db) {
		boolean a = false;
		if (db.getValue() != null) {
			if (db.getValue().equals("")) {
				a = true;
			}
		} else {
			a = true;
		}
		return a;
	}

	@Command
	public void selectItem() {
		East east = (East) winc.getFellow("items");
		east.setOpen(true);

	}

	@Command
	public void closeItem() {
		East e = (East) winc.getFellow("items");
		e.setOpen(false);
	}

	/**
	 * @Title: ClearPama
	 * @Description: TODO(重置全局变量)
	 * @return void 返回类型
	 * @throws
	 */
	public void ClearPama() {
		id = "";
		sd1 = "";
		sd2 = "";
	}

	// 检查特殊条件
	public String specialCheck() {
		String str = null;
		boolean b = false; // af,fs-2必选签订劳动合同
		boolean b_2 = false; //FS-4签订聘用合同
		boolean b_3 = false;
		boolean b1 = false;
		boolean b2 = false;
		boolean b3 = false;
		for (CoOfferListModel m : cflList) {
			if (m.getCoco_compacttype().equals("AF")
					|| m.getCoco_compacttype().equals("FS-2")) {
				b = true;
			}
			if (m.getColi_name().equals("签订劳动合同")) {
				b1 = true;
			}
			if (m.getCoco_compacttype().equals("FS-4")) {
				b_2=true;
				if (m.getColi_name().equals("签订聘用合同")) {
					b2 = true;
				}
			}
			if (m.getColi_pclass().equals("商业保险")) {
				if (m.getColi_name().contains("M") && !m.getColi_name().contains("MC") && !m.getColi_name().contains("FM")) {
					b_3=true;
				}
				if(m.getColi_name().contains("P7")){
					b3=true;
				}
			}
			
		}
		if (b) {
			if (!b1) {
				str = "请选择[签订劳动合同]!";
			}
		}
		if (b_2) {
			if (!b2) {
				str = "请选择[签订聘用合同]!";
			}
		}
		if (b_3) {
			if (!b3) {
				str = "请选择[P7-意外B型]!";
			}
		}

		return str;
	}

	/**
	 * @throws SQLException 
	 * @Title: btnSubmit
	 * @Description: TODO(提交页面)
	 * @param db
	 * @param gd
	 * @return void 返回类型
	 * @throws
	 */
	@Command("btnSubmit")
	@NotifyChange("ebm")
	public void btnSubmit() throws SQLException {

		Boolean j = false;
		// 初始化判断属性
		ClearPama();
		// 判断是否选择档案委托

		if (ebmnotin != null) {
			EmbaseLogin_AddBll notinbll = new EmbaseLogin_AddBll();
			ebmnotin = notinbll.getEmbaseLoginInfo(embanotin_id).get(0);

			notinbll.EmbaseloginUpdatestate(1, embanotin_id);

			ebmnotin.setEmba_id(ebmnotin.getEmba_type());

			Integer i = bll.modInfo(ebmnotin);

		}

		CheckStringService ch = new CheckStringImpl();
		if (ebm.getEmba_mobile()!=null && !ch.isNum(ebm.getEmba_mobile().toString()))
		{
			Messagebox.show("手机号码格式有误！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		
		
		if (emcont.getEmzt_iffileservice() == null) {
			Messagebox.show("请选择是否有档案委托", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (ebm.getEmba_name() == null) {
			Messagebox.show("请输入姓名", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (tbidcard.getValue() == null || tbidcard.getValue().equals("")) {
			Messagebox.show("请输入身份证", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			if (ebm.getEmba_idcardclass() != null) {
				if (ebm.getEmba_idcardclass().equals("身份证")) {
					if (ebm.getEmba_idcard() != null
							&& !ebm.getEmba_idcard().isEmpty()) {
						ebm.setEmba_idcard(ebm.getEmba_idcard().trim());
						// 检查身份证号码合法性
						if (!IdcardUtil.validateCard(ebm.getEmba_idcard())) {
							Messagebox.show("身份证不合法,请检查是否正确!", "ERROR",
									Messagebox.OK, Messagebox.ERROR);
							return;
						}
					} else {
						Messagebox.show("身份证加载错误,请联系IT部!", "ERROR",
								Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
				if (bll.haveIDcard(ebm.getCid(), ebm.getGid(),
						ebm.getEmba_idcard(), ebm.getEmba_idcardclass())) {
					Messagebox.show("身份证已存在!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			} else {
				Messagebox.show("请选择身份证类型!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}

		if (ebm.getEmba_nationality() == null
				|| ebm.getEmba_nationality().equals("")) {
			Messagebox.show("请选择国籍!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (ebm.getEmba_idcardclass().equals("身份证")) {
			// 生日
			if (dbbirth.getValue() != null && !dbbirth.getValue().equals("")) {
				ebm.setEmba_birth(sdf2.format(dbbirth.getValue()));
			} else {
				Messagebox.show("请选择出生日期!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			// 性别
			if (ebm.getEmba_sex() == null || ebm.getEmba_sex().equals("")) {
				Messagebox.show("请选择性别!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}

		// 判断入职日期
		if (DateEmpty(dbinciic)) {
			Messagebox.show("请选择入职时间", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			ebm.setEmba_indate(DateFormat.getDateInstance().format(
					dbinciic.getValue()));
		}

		// 联系方式
		/*
		if (tbphone.getValue() == null && tbmobile.getValue() == null) {
			Messagebox.show("请输入电话或手机联系方式", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		} else {
			if (tbphone.getValue() != null) {
				ebm.setEmba_phone(tbphone.getValue());
			}
			if (tbmobile.getValue() != null) {
				ebm.setEmba_mobile(tbmobile.getValue());
			}

		}
		*/

		// 根据特殊条件判断是否有项目未选
		String s = specialCheck();
		if (s != null) {
			Messagebox.show(s, "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		// 判断是否勾选项目
		for (CoOfferListModel m : cflList) {
			id = id + "," + m.getColi_id();
			sd1 = sd1 + "," + m.getSt();
			sd2 = sd2 + "," + m.getSt2();
		}
		if (id.length() > 0)
			id = id.substring(1);
		if (sd1.length() > 0)
			sd1 = sd1.substring(1);
		if (sd2.length() > 0)
			sd2 = sd2.substring(1);

		if (cflList.size() < 1) {
			Messagebox.show("请选择员工的报价单项目", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			for (CoOfferListModel m1 : cflList) {
				if (m1.getColi_name().equals("社会保险服务")
						&& ebm.getEmba_sb_place().equals("本地")
						&& (m1.getCoco_shebao() == null || m1.getCoco_shebao()
								.equals(""))) {
					Messagebox.show("合同未选择社保开户状态,请联系IT更新合同信息.", "操作提示",
							Messagebox.OK, Messagebox.ERROR);
					return;
				}
				if (m1.getColi_name().equals("住房公积金服务")
						&& ebm.getEmba_house_place().equals("本地")) {
					if (m1.getCoco_house() == null
							|| m1.getCoco_house().equals("")) {
						Messagebox.show("合同未选择公积金开户状态,请联系IT更新合同信息.", "操作提示",
								Messagebox.OK, Messagebox.ERROR);
						return;
					}

					if (emztM != null) {

						Double cpp = 0.00;
						Double cpp2 = 0.00;
						try {
							cpp = Double.valueOf(emztM.getEmzt_housecpp()
									.replace("%", ""));
						} catch (Exception e) {
							// TODO: handle exception
						}
						if (m1.getCoco_cpp() != null
								&& !m1.getCoco_cpp().equals("")) {

							if (!m1.getCoco_cpp().equals("浮动比例")) {
								try {
									DecimalFormat df = new DecimalFormat(
											"####0.00");
									cpp2 = Double.valueOf(df.format((Double
											.valueOf(m1.getCoco_cpp()) * 100)));

								} catch (Exception e) {
									// TODO: handle exception
								}
								System.out.println(cpp);
								System.out.println(cpp2);
								System.out.println(cpp.equals(cpp2));
								if (!cpp.equals(cpp2)) {
									Messagebox.show("委托单公积金比例与合同比例不一致", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
									return;
								}
							}
						} else {
							Messagebox.show("合同比例未录入", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
							return;
						}

					}
				}
				for (CoOfferListModel m2 : cflList) {
					if (m1.getColi_copr_id().equals(m2.getColi_copr_id())
							&& m1.getColi_pclass().equals(m2.getColi_pclass())
							&& m1.getColi_name().equals(m2.getColi_name())
							&& !m1.getColi_id().equals(m2.getColi_id())
							&& m2.isChecked()) {
						Messagebox.show("[" + m1.getColi_name()
								+ "]被重复选择,请取消多余选项.", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				}

			}
			/*
			 * Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
			 * Messagebox.Button.OK, Messagebox.Button.CANCEL },
			 * Messagebox.QUESTION, new EventListener<Messagebox.ClickEvent>() {
			 * 
			 * @Override public void onEvent(ClickEvent event) throws Exception
			 * { // TODO Auto-generated method stub
			 * 
			 * if (Messagebox.Button.OK.equals(event.getButton())) {
			 */
			// 根据国籍判断是否外籍人参加社保
			if ("中国".equals(ebm.getEmba_nationality())) {
				ebm.setEmba_emsb_foreigner("否");
			} else {
				ebm.setEmba_emsb_foreigner("是");
			}
			/*
			if (!(ebm.getEmba_mobile() != null && !ebm.getEmba_mobile()
							.equals(""))) {
				Messagebox.show("请输入手机号码", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			*/
			ebm.setEmba_addname(UserInfo.getUsername());

			Integer k = 0;
			ebm.setEmba_idcard(ebm.getEmba_idcard().replaceAll(" ", "")
					.replaceAll("[\\t\\n\\r]", ""));
			if (mod) {
				if (emcont.getId() == 0) {
					if (emcont.getEmzt_iffileservice() != null
							&& !emcont.getEmzt_iffileservice().equals("")) {

						ebm.setEmzt_iffileservice(emcont
								.getEmzt_iffileservice());
					} else {
						ebm.setEmzt_iffileservice(null);
					}
				} else {
					if (emcont.getEmzt_iffileservice() != null
							&& !emcont.getEmzt_iffileservice().equals("")) {

						ebm.setEmzt_iffileservice(emcont
								.getEmzt_iffileservice());
					} else {
						ebm.setEmzt_iffileservice(null);
					}
				}
				k = bll.modEmbase(ebm, id, sd1, sd2);
				
				 
				SystLogModel logm=new SystLogModel();
				 SystLogInfoBll logBll = new SystLogInfoBll();
				String strlog="";
				 try {strlog = EntitiesComparedUtils.OldToNewReflect(oldebm,ebm);
					 
					   } 
				 catch  (NoSuchMethodException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					   }
				 logm.setContent(strlog); //修改内容或新增内容
				 logm.setAddname(UserInfo.getUsername());
					//m.setIP(ip);
				 logm.setGID("");
				 logm.setClass1("员工信息");
				 logBll.addSystLog(logm); 

			} else {
				if (emcont.getId() == 0) {
					if (emcont.getEmzt_iffileservice() != null
							&& !emcont.getEmzt_iffileservice().equals("")) {

						ebm.setEmzt_iffileservice(emcont
								.getEmzt_iffileservice());
					} else {
						ebm.setEmzt_iffileservice(null);
					}
				} else {
					if (emcont.getEmzt_iffileservice() != null
							&& !emcont.getEmzt_iffileservice().equals("")) {

						ebm.setEmzt_iffileservice(emcont
								.getEmzt_iffileservice());
					} else {
						ebm.setEmzt_iffileservice(null);
					}
				}

				k = bll.addEmbase(ebm, id, sd1, sd2);
				
				ebm.setEmba_id(k);
				
				SystLogModel logm=new SystLogModel();
				 SystLogInfoBll logBll = new SystLogInfoBll();
				String strlog="";
				 try {strlog = EntitiesComparedUtils.NewReflect(ebm);
					 
					   } 
				 catch  (NoSuchMethodException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					   }
				 logm.setContent(strlog); //修改内容或新增内容
				 logm.setAddname(UserInfo.getUsername());
					//m.setIP(ip);
				 logm.setGID("");
				 logm.setClass1("员工信息");
				 logBll.addSystLog(logm); 
			}

			if (k > 0) {

				if (embasenotinlist.size() > 0)// 判断是否需要更新到embanotin
				{

					EmbaseLogin_AddBll embanotinbll = new EmbaseLogin_AddBll();
					// EmbaseModel embanotinm = new
					// EmbaseModel();
					// embanotinm = (EmbaseModel)
					// ebm.clone();
					//
					// embanotinm.setEmba_type(k);
					// embanotinm.setEmba_id(embanotin_id);
					embanotinbll.EmbaseloginUpdatezc(k, embanotin_id);
				}

				if (emztM != null) {

					if (mod == false) {// 首次新增员工信息，如果不是首次新增，接口页面已经将emba_id赋值了
						emztM.setEmba_id(k);// 赋值emba_id
					}
					// 社保基数
					BigDecimal ylradix;
					try {// 重新赋值，防止智翼通委托单数据格式不对报错
						ylradix = new BigDecimal(emztM.getEmzt_ylradix());
					} catch (Exception e) {
						ylradix = new BigDecimal("0");
					}
					emztM.setEmzt_ylradix(String.valueOf(ylradix));

					// 公积金基数
					BigDecimal houseradix;
					try {// 重新赋值，防止智翼通委托单数据格式不对报错
						houseradix = new BigDecimal(emztM.getEmzt_houseradix());
					} catch (Exception e) {
						houseradix = new BigDecimal("0");
					}
					emztM.setEmzt_houseradix(String.valueOf(houseradix));

					// 公积金比例
					Double house_cpp;
					try {// 重新赋值，防止智翼通委托单数据格式不对报错
						house_cpp = Double.valueOf(emztM.getHouse_cpp());
					} catch (Exception e) {
						house_cpp = Double.valueOf("0");
					}
					emztM.setHouse_cpp(String.valueOf(house_cpp));
					emztM.setHouse_opp(String.valueOf(house_cpp));
					EmZYT_OperateBll zytObll = new EmZYT_OperateBll();
					zytObll.upEmZYTembaId(emztM);

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("ebm", ebm);
					map.put("emztM", emztM);
					BindUtils.postGlobalCommand(null, null, "embaNext", map);

				} else {

					Map map = new HashMap();
					map.put("embanotin_id", embanotin_id);
					// map.put("emba_id", embanotin_id);
					map.put("cid", cid);
					map.put("ebm", ebm);
					map.put("emztM", emztM);
					Window window = (Window) Executions.createComponents(
							"../Embase/EmBase_Entry.zul", null, map);

					window.doModal();
					winc.detach();

				}

				if (!mod) {/*
							 * Messagebox.show("新增员工成功", "操作提示", Messagebox.OK,
							 * Messagebox.INFORMATION);
							 */
				}

			} else {
				Messagebox
						.show("操作失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}

			// }
			// }
			// });

		}

	}

	/**
	 * @Title: btnSubmit
	 * @Description: TODO(提交页面)
	 * @param db
	 * @param gd
	 * @return void 返回类型
	 * @throws
	 */
	@Command("btnSubmitselect")
	public void btnSubmitselect() {

		// Boolean j = false;
		// // 初始化判断属性
		// ClearPama();
		// // 判断是否选择档案委托
		//
		// if (emcont.getEmzt_iffileservice() == null) {
		// Messagebox.show("请选择是否有档案委托", "操作提示", Messagebox.OK,
		// Messagebox.ERROR);
		// return;
		// }
		//
		// if (ebm.getEmba_name() == null) {
		// Messagebox.show("请输入姓名", "操作提示", Messagebox.OK, Messagebox.ERROR);
		// return;
		// }
		//
		// if (tbidcard.getValue() == null || tbidcard.getValue().equals("")) {
		// Messagebox.show("请输入身份证", "操作提示", Messagebox.OK, Messagebox.ERROR);
		// return;
		// } else {
		// if (ebm.getEmba_idcardclass().equals("身份证")) {
		// if (ebm.getEmba_idcard() != null
		// && !ebm.getEmba_idcard().isEmpty()) {
		// // 检查身份证号码合法性
		// if (!IdcardUtil.validateCard(ebm.getEmba_idcard())) {
		// Messagebox.show("身份证不合法,请检查是否正确!", "ERROR",
		// Messagebox.OK, Messagebox.ERROR);
		// return;
		// }
		// } else {
		// Messagebox.show("身份证加载错误,请联系IT部!", "ERROR", Messagebox.OK,
		// Messagebox.ERROR);
		// return;
		// }
		// }
		// if (bll.haveIDcard(ebm.getCid(), ebm.getGid(),
		// ebm.getEmba_idcard(), ebm.getEmba_idcardclass())) {
		// Messagebox.show("身份证已存在!", "ERROR", Messagebox.OK,
		// Messagebox.ERROR);
		// return;
		// }
		//
		// }
		//
		// if (ebm.getEmba_nationality() == null
		// || ebm.getEmba_nationality().equals("")) {
		// Messagebox.show("请选择国籍!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		// return;
		// }
		//
		// // 生日
		// if (dbbirth.getValue() != null && !dbbirth.getValue().equals("")) {
		// ebm.setEmba_birth(sdf2.format(dbbirth.getValue()));
		// } else {
		// Messagebox.show("请选择出生日期!", "ERROR", Messagebox.OK,
		// Messagebox.ERROR);
		// return;
		// }
		//
		// // 性别
		// if (ebm.getEmba_sex() == null || ebm.getEmba_sex().equals("")) {
		// Messagebox.show("请选择性别!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		// return;
		// }
		//
		// // 判断入职日期
		// if (DateEmpty(dbinciic)) {
		// Messagebox.show("请选择入职时间", "操作提示", Messagebox.OK, Messagebox.ERROR);
		// return;
		// } else {
		// ebm.setEmba_indate(DateFormat.getDateInstance().format(
		// dbinciic.getValue()));
		// }
		//
		// // 联系方式
		// if (tbphone.getValue() == null && tbmobile.getValue() == null) {
		// Messagebox.show("请输入电话或手机联系方式", "操作提示", Messagebox.OK,
		// Messagebox.ERROR);
		// return;
		// } else {
		// if (tbphone.getValue() != null) {
		// ebm.setEmba_phone(tbphone.getValue());
		// }
		// if (tbmobile.getValue() != null) {
		// ebm.setEmba_mobile(tbmobile.getValue());
		// }
		//
		// }
		//
		// // 根据特殊条件判断是否有项目未选
		// String s = specialCheck();
		// if (s != null) {
		// Messagebox.show(s, "操作提示", Messagebox.OK, Messagebox.ERROR);
		// return;
		// }
		//
		// // 判断是否勾选项目
		// for (CoOfferListModel m : cflList) {
		// id = id + "," + m.getColi_id();
		// sd1 = sd1 + "," + m.getSt();
		// sd2 = sd2 + "," + m.getSt2();
		// }
		// if (id.length() > 0)
		// id = id.substring(1);
		// if (sd1.length() > 0)
		// sd1 = sd1.substring(1);
		// if (sd2.length() > 0)
		// sd2 = sd2.substring(1);
		//
		// if (cflList.size() < 1) {
		// Messagebox.show("请选择员工的报价单项目", "操作提示", Messagebox.OK,
		// Messagebox.ERROR);
		// } else {
		// for (CoOfferListModel m1 : cflList) {
		// for (CoOfferListModel m2 : cflList) {
		// if (m1.getColi_copr_id().equals(m2.getColi_copr_id())
		// && m1.getColi_pclass().equals(m2.getColi_pclass())
		// && m1.getColi_name().equals(m2.getColi_name())
		// && !m1.getColi_id().equals(m2.getColi_id())
		// && m2.isChecked()) {
		// Messagebox.show("[" + m1.getColi_name()
		// + "]被重复选择,请取消多余选项.", "操作提示", Messagebox.OK,
		// Messagebox.ERROR);
		// return;
		// }
		// }
		//
		// }
		//
		// // 根据国籍判断是否外籍人参加社保
		// if ("中国".equals(ebm.getEmba_nationality())) {
		// ebm.setEmba_emsb_foreigner("否");
		// } else {
		// ebm.setEmba_emsb_foreigner("是");
		// }
		//
		// ebm.setEmba_addname(UserInfo.getUsername());
		//
		// Integer k = 0;
		// if (mod) {
		// if (emcont.getId() == 0) {
		// if (emcont.getEmzt_iffileservice() != null
		// && !emcont.getEmzt_iffileservice().equals("")) {
		//
		// ebm.setEmzt_iffileservice(emcont
		// .getEmzt_iffileservice());
		// } else {
		// ebm.setEmzt_iffileservice(null);
		// }
		// } else {
		// if (emcont.getEmzt_iffileservice() != null
		// && !emcont.getEmzt_iffileservice().equals("")) {
		//
		// ebm.setEmzt_iffileservice(emcont
		// .getEmzt_iffileservice());
		// } else {
		// ebm.setEmzt_iffileservice(null);
		// }
		// }
		// k = bll.modEmbase(ebm, id, sd1, sd2);
		//
		// } else {
		// if (emcont.getId() == 0) {
		// if (emcont.getEmzt_iffileservice() != null
		// && !emcont.getEmzt_iffileservice().equals("")) {
		//
		// ebm.setEmzt_iffileservice(emcont
		// .getEmzt_iffileservice());
		// } else {
		// ebm.setEmzt_iffileservice(null);
		// }
		// } else {
		// if (emcont.getEmzt_iffileservice() != null
		// && !emcont.getEmzt_iffileservice().equals("")) {
		//
		// ebm.setEmzt_iffileservice(emcont
		// .getEmzt_iffileservice());
		// } else {
		// ebm.setEmzt_iffileservice(null);
		// }
		// }
		// //k = bll.addEmbase(ebm, id, sd1, sd2);
		// //ebm.setEmba_id(k);
		//
		// }
		//
		// if (k > 0) {
		//
		// if (embasenotinlist.size() > 0)// 判断是否需要更新到embanotin
		// {
		//
		// EmbaseLogin_AddBll embanotinbll = new EmbaseLogin_AddBll();
		// // EmbaseModel embanotinm = new
		// // EmbaseModel();
		// // embanotinm = (EmbaseModel)
		// // ebm.clone();
		// //
		// // embanotinm.setEmba_type(k);
		// // embanotinm.setEmba_id(embanotin_id);
		// embanotinbll.EmbaseloginUpdatezc(k, embanotin_id);
		// }
		//
		// if (emztM != null) {
		//
		// if (mod == false) {// 将emba_id更新到智翼通表中
		// emztM.setEmba_id(k);// 赋值emba_id
		// // 社保基数
		// BigDecimal ylradix;
		// try {
		// ylradix = new BigDecimal(emztM.getEmzt_ylradix());
		// } catch (Exception e) {
		// ylradix = new BigDecimal("0");
		// }
		// emztM.setEmzt_ylradix(String.valueOf(ylradix));
		//
		// // 公积金基数
		// BigDecimal houseradix;
		// try {
		// houseradix = new BigDecimal(
		// emztM.getEmzt_houseradix());
		// } catch (Exception e) {
		// houseradix = new BigDecimal("0");
		// }
		// emztM.setEmzt_houseradix(String.valueOf(houseradix));
		//
		// // 公积金比例
		// Double house_cpp;
		// try {
		// house_cpp = Double
		// .valueOf(emztM.getEmzt_housecpp());
		// } catch (Exception e) {
		// house_cpp = Double.valueOf("0");
		// }
		// emztM.setEmzt_housecpp(String.valueOf(house_cpp));
		// emztM.setEmzt_houseopp(String.valueOf(house_cpp));
		//
		// EmZYT_OperateBll zytObll = new EmZYT_OperateBll();
		// zytObll.upEmZYTembaId(emztM);
		//
		// }
		//
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("ebm", ebm);
		// map.put("emztM", emztM);
		// BindUtils.postGlobalCommand(null, null, "embaNext", map);
		//
		// } else {

		Map map = new HashMap();
		map.put("embanotin_id", embanotin_id);
		// map.put("emba_id", embanotin_id);
		map.put("cid", cid);
		map.put("ebm", ebm);
		map.put("emztM", emztM);
		Window window = (Window) Executions.createComponents(
				"../Embase/EmBase_Entryselect.zul", null, map);

		window.doModal();
		winc.detach();

		// }

		// if (!mod) {/*
		// * Messagebox.show("新增员工成功", "操作提示", Messagebox.OK,
		// * Messagebox.INFORMATION);
		// */
		// }
		//
		// } else {
		// Messagebox
		// .show("操作失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
		// }

	}

	@Command
	public void cancel() {
		Messagebox.show("确认取消入职?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							if (bll.cancelOnboard(ebm.getEmba_id(),
									ebm.getGid(), ebm.getEmba_tapr_id())) {
								Messagebox.show("操作成功!", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								winc.detach();
							}
						}
					}
				});
	}

	public int getEmbanotin_id() {
		return embanotin_id;
	}

	public void setEmbanotin_id(int embanotin_id) {
		this.embanotin_id = embanotin_id;
	}

	public int getEmba_id() {
		return emba_id;
	}

	public void setEmba_id(int emba_id) {
		this.emba_id = emba_id;
	}

	public Boolean getIfembasenotin() {
		return ifembasenotin;
	}

	public void setIfembasenotin(Boolean ifembasenotin) {
		this.ifembasenotin = ifembasenotin;
	}

	public EmbaseModel getEbm() {
		return ebm;
	}

	public void setEbm(EmbaseModel ebm) {
		this.ebm = ebm;
	}

	public CoBaseModel getCbm() {
		return cbm;
	}

	public void setCbm(CoBaseModel cbm) {
		this.cbm = cbm;
	}

	public List<EmbaseModel> getList() {
		return list;
	}

	public Emcontactinfo getEmcont() {
		return emcont;
	}

	public void setEmcont(Emcontactinfo emcont) {
		this.emcont = emcont;
	}

	public void setList(Integer id) {
		this.list = bll.embaseinfo(id);
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public List<PubNationalityModel> getPnList() {
		return pnList;
	}

	public void setPnList(List<PubNationalityModel> pnList) {
		this.pnList = pnList;
	}

	public String getWinId() {
		return winId;
	}

	public void setWinId(String winId) {
		this.winId = winId;
	}

	public List<CoCompactModel> getCompactlist() {
		return compactlist;
	}

	public void setCompactlist(List<CoCompactModel> compactlist) {
		this.compactlist = compactlist;
	}

	public List<CoOfferModel> getQuotelist() {
		return quotelist;
	}

	public void setQuotelist(List<CoOfferModel> quotelist) {
		this.quotelist = quotelist;
	}

	public List<CoCompactCoofferModel> getProductlist() {
		return productlist;
	}

	public void setProductlist(List<CoCompactCoofferModel> productlist) {
		this.productlist = productlist;
	}

	public List<CoOfferListModel> getItemlist() {
		return itemlist;
	}

	public void setItemlist(List<CoOfferListModel> itemlist) {
		this.itemlist = itemlist;
	}

	public String getCompactid() {
		return compactid;
	}

	public void setCompactid(String compactid) {
		this.compactid = compactid;
	}

	public String getQuotename() {
		return quotename;
	}

	public void setQuotename(String quotename) {
		this.quotename = quotename;
	}

	public String getItemclass() {
		return itemclass;
	}

	public void setItemclass(String itemclass) {
		this.itemclass = itemclass;
	}

	public String getCompactTips() {
		return compactTips;
	}

	public void setCompactTips(String compactTips) {
		this.compactTips = compactTips;
	}

	public List<CoOfferListModel> getCflList() {
		return cflList;
	}

	public void setCflList(List<CoOfferListModel> cflList) {
		this.cflList = cflList;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public List<CoOfferListModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<CoOfferListModel> cityList) {
		this.cityList = cityList;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
