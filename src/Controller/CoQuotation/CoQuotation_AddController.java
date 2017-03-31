package Controller.CoQuotation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Window;

import bll.CoProduct.cpd_ListBll;
import bll.CoQuotation.CoQuotation_AddBll;

import Model.CoAgencyBaseModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Model.CoPclassModel;
import Model.CoProductModel;
import Model.CopCompactModel;
import Model.PubProCityModel;
import Util.UserInfo;

public class CoQuotation_AddController {

	private List<CoProductModel> notselectList = new ListModelList<>();
	private List<CoProductModel> selectList = new ListModelList<CoProductModel>();
	private List<CoOfferListModel> colList = new ListModelList<CoOfferListModel>();
	private ListModelList<PubProCityModel> cityList = new ListModelList<>();
	private ListModel<PubProCityModel> cityListModel;
	private List<CoPclassModel> classList;
	private ListModelList<CoPclassModel> sclasslist = new ListModelList<>();
	private List<CoAgencyBaseModel> agencyList = new ListModelList<CoAgencyBaseModel>();
	private List<String> coprList = new ListModelList<>();
	private List<CopCompactModel> cpctList;
	private CopCompactModel cpctModel = new CopCompactModel();

	private CoOfferModel cfModel = new CoOfferModel();
	private String coof_register = "";// 是否国内注册
	private Integer coof_cpct_id;
	
	// 是否梯级报价
	private String is_tjbj = "否";
	private Boolean vis_rsgm = false;
	private List<CoOfferModel> coofList;
	//绑定报价单model
	private CoOfferModel coofModel = new CoOfferModel();

	// 检索条件
	private String copr_type = "";// 产品类别
	private CoPclassModel cpcModel;// 前道产品科目类型
	private PubProCityModel ppcModel = new PubProCityModel();// 城市
	private CoAgencyBaseModel abModel = new CoAgencyBaseModel();// 委托机构

	// 接收页面传值
	private String cola_company;
	private int cola_id;
	private int cid;
	private int coco_id = 0;
	int coof_id = 0;

	String str = "";

	private boolean deptDis=true;
	
	// 初始化
	@SuppressWarnings("unchecked")
	public CoQuotation_AddController() throws Exception {
		if (UserInfo.getDepID().equals("6")) {
			deptDis=false;
		}
		try {
			CoQuotation_AddBll bll = new CoQuotation_AddBll();
			//获取城市
			setCityList(bll.getCityList());
			
			//?
			setCityListModel(new SimpleListModel<PubProCityModel>(getCityList()));
			//获取合同类型
			setCpctList(new cpd_ListBll().getCopCompactList(" and cpct_state=1"));
			//获取前道科目list
			setClassList(bll.getclass(""));
			
			coprList.add("");
			coprList.add("服务产品");
			coprList.add("福利产品");
			
			//合并合同类型代码和名称
			for (CopCompactModel m : cpctList) {
				if (m.getCpct_name() != null && !m.getCpct_name().isEmpty()) {
					m.setCpct_name(m.getCpct_shortname() + "("
							+ m.getCpct_name() + ")");
				} else {
					m.setCpct_name(m.getCpct_shortname());
				}
			}
			//国内注册
			coof_register = "1";
			//绑城市默认值
			for (PubProCityModel m : cityList) {
				if (m.getName().equals("s深圳")) {
					setPpcModel(m);
					break;
				}
			}

			/* 获取页面传值 */
			cola_company = Executions.getCurrent().getArg().get("colacompany")
					.toString();
			try {
				// 潜在客户报价
				cola_id = Integer.parseInt(Executions.getCurrent().getArg()
						.get("colaid").toString());
				//读报价单？？
				coofList = bll.getcoofList(cola_id, null);
			} catch (Exception e) {
			}

			try {
				// 公司报价
				cid = Integer.parseInt(Executions.getCurrent().getArg()
						.get("cid").toString());
				coco_id = Integer.parseInt(Executions.getCurrent().getArg()
						.get("coco_id").toString());
				if (cid>0)
				{
				coofList = bll.getcoofList(null, cid);
				}
				setCpctModel(bll.getCompactByCocoid(coco_id));
				
				if (cpctModel != null) {
					for (CopCompactModel m : cpctList) {
						if (m != null) {
							if (m.getCpct_id().equals(cpctModel.getCpct_id())) {
								setCpctModel(m);
							}
						}
					}
				}
				search();

			} catch (Exception e) {
				// TODO: handle exception
			}

			try {
				// 上一步返回报价
				selectList.clear();
				coof_id = Integer.parseInt(Executions.getCurrent().getArg()
						.get("coofid").toString());
				setSelectList((List<CoProductModel>) Executions.getCurrent()
						.getArg().get("selectList"));
				setCfModel((CoOfferModel) Executions.getCurrent().getArg()
						.get("cfModel"));
				
				
				for (CopCompactModel m : cpctList) {
					if (cfModel.getCoof_cpct_id().equals(m.getCpct_id())) {
						cpctModel = m;
						break;
					}
				}
				

				if (cfModel.getCoof_gm().length()>0)
				{
					 is_tjbj = "是";
					 vis_rsgm = true;
						cfModel.setCoof_min(coofModel.getCoof_max());
						cfModel.setCoof_max(0);
						cfModel.setCoof_gm(getGmStr());
				}
				else
				{
					is_tjbj = "否";
					vis_rsgm = false;
				}
				
				
			

				
				search();
				

			} catch (Exception e) {
				/* 报价单基本信息初始化 */
				cfModel.setCoof_name("");
				cfModel.setCoof_quotemode("");
				cfModel.setCoof_min(0);
				cfModel.setCoof_max(0);
			}

		} catch (Exception e) {
			Messagebox.show("加载页面失败,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	//获取产品列表
	@Command("initltb")
	public void initltb(@BindingParam("ltb") Listbox ltb) {
		ltb.setMultiple(true);
	}

	/**
	 * 控制人数规模的显示
	 * 
	 */
	@Command("handle_vis_rsgm")
	@NotifyChange({ "vis_rsgm" })
	public void handle_vis_rsgm() {
		if (is_tjbj.equals(("是"))) {
			vis_rsgm = true;
		} else {
			vis_rsgm = false;
		}
	}

	/**
	 * 合同类型变更，清空产品已选列表
	 * 
	 */
	//cmb=cplxcombobox,cmb=cplxcomboboxinfo
	@Command
	@NotifyChange({ "selectList" })
	public void copcompactChange(@BindingParam("cmb") Combobox coofname,
			@BindingParam("cmbinfo") Combobox coofnameinfo) {
		try {
			//清除已选项目
			selectList.clear();
			
			//如果是CS 就禁止下拉
			/*
			if (cpctModel.getCpct_id()==4 || cpctModel.getCpct_id()>5)
			{
				coofname.setVisible(false);
				coofnameinfo.setVisible(false);
			}
			else
			{
				coofname.setVisible(true);
				coofnameinfo.setVisible(true);
			}
			*/
			//绑定合同类型
			cfModel.setCoof_cpct_id(cpctModel.getCpct_id());
			System.out.println(cpctModel.getCpct_id());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Command
	@NotifyChange({ "selectList" })
	public void comboboxvis(@BindingParam("cmb") Combobox coofname,
			@BindingParam("cmbinfo") Combobox coofnameinfo) {
	
		//如果是CS或其他 就禁止下拉
		/*
		try{
			if (cpctModel.getCpct_id()!=null)
			{
				if (cpctModel.getCpct_id()==2)
				{
					coofname.setVisible(false);
					coofnameinfo.setVisible(false);
				}
				else
				{
					coofname.setVisible(true);
					coofnameinfo.setVisible(true);
				}
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		*/
	}

	/**
	 * 筛选产品类型列表
	 * 
	 */
	@Command("class_change")
	@NotifyChange({ "sclasslist" })
	public void class_change() {
		//清除产品科目
		sclasslist.clear();
		
		if (!copr_type.isEmpty()) {
			
			//根据产品类型遍历产品科目 
			for (CoPclassModel m : classList) {
				if (!copr_type.equals(m.getCopc_type1())) {
					continue;
				}

				sclasslist.add(m);
			}
			if (sclasslist.size() > 0) {
				sclasslist.add(0, null);
			}
		}
	}

	// 检索
	@Command("search")
	@NotifyChange({ "notselectList", "agencyList", "selectList" })
	public void search() throws Exception {
		try {
			str = "";
			CoQuotation_AddBll bll = new CoQuotation_AddBll();
			if (!copr_type.isEmpty()) {
				str += " and copr_type='" + copr_type + "'";
			}
			if (cpcModel != null) {
				str += " and copr_copc_id=" + cpcModel.getCopc_id();
			}
			if (cpctModel != null) {
				str += " and cpcr_cpct_id=" + cpctModel.getCpct_id();
			}
			if (ppcModel != null) {
				if (ppcModel.getId() != 0) {
					setAgencyList(new CoQuotation_AddBll()
							.getCoAgencyList(ppcModel.getId()));
					if (agencyList.size() > 0) {
						abModel = agencyList.get(0);
					} else {
						str += " and 1=0";
						agencyList.add(0, new CoAgencyBaseModel());
						abModel = agencyList.get(0);
					}
				}
			} else {
				str += " and 1=0";
				agencyList.clear();
				agencyList.add(0, new CoAgencyBaseModel());
				abModel = agencyList.get(0);
			}
			if (abModel.getCoab_id() != 0) {
				str += " and copr_cabc_id=" + abModel.getCoab_id();
			}
			if (selectList.size() > 0) {
				String str1 = "";
				for (int i = 0; i < selectList.size(); i++) {
					str1 += selectList.get(i).getCopr_id() + ",";
				}
				str1 = str1.substring(0, str1.length() - 1);
				str += " and a.copr_id not in(" + str1 + ")";
			}
			setNotselectList(new ListModelList<CoProductModel>(
					bll.getCoproductListzmj(str)));
		} catch (Exception e) {
			Messagebox.show("检索失败,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@Command("search1")
	@NotifyChange({ "notselectList", "agencyList" })
	public void search1() throws Exception {
		try {
			str = "";
			CoQuotation_AddBll bll = new CoQuotation_AddBll();
			if (!copr_type.isEmpty()) {
				str += " and copr_type='" + copr_type + "'";
			}
			if (cpcModel != null) {
				str += " and copr_copc_id=" + cpcModel.getCopc_id();
			}
			if (cpctModel != null) {
				str += " and cpcr_cpct_id=" + cpctModel.getCpct_id();
			}
			if (abModel.getCoab_id() != 0) {
				str += " and copr_cabc_id=" + abModel.getCoab_id();
			}
			if (selectList.size() > 0) {
				String str1 = "";
				for (int i = 0; i < selectList.size(); i++) {
					str1 += selectList.get(i).getCopr_id() + ",";
				}
				str1 = str1.substring(0, str1.length() - 1);
				str += " and a.copr_id not in(" + str1 + ")";
			}
			setNotselectList(new ListModelList<CoProductModel>(
					bll.getCoproductListzmj(str)));
		} catch (Exception e) {
			Messagebox.show("检索失败,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	// 单个添加
	@Command("addsingleselect")
	@NotifyChange({ "selectList", "notselectList" })
	public void addsingleselect(@BindingParam("selectitem") CoProductModel item) {
		try {
			if (!selectList.contains(item)) {
				selectList.add(item);
				notselectList.remove(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 单个移除
	@Command("removesingleselect")
	@NotifyChange({ "selectList", "notselectList" })
	public void removesingleselect(
			@BindingParam("selectitem") CoProductModel item) {
		try {
			notselectList.add(item);
			selectList.remove(item);
			search();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 全部添加
	@Command("allselect")
	@NotifyChange({ "selectList", "notselectList" })
	public void allselect() {
		try {
			if (notselectList.size() > 0) {
				selectList.addAll(notselectList);
				notselectList.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 全部移除
	@Command("allremove")
	@NotifyChange({ "selectList", "notselectList" })
	public void allremove() {
		try {
			if (selectList.size() > 0) {
				notselectList.addAll(selectList);
				selectList.clear();
			}
			search();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 报价单名称变更，读取之前的报价单
	 * 
	 */
	@Command
	@NotifyChange({ "cfModel", "is_tjbj", "vis_rsgm", "cpctModel",
			"notselectList", "agencyList" })
	public void coofNameChange(@BindingParam("coofname") String coofname) {
		CoQuotation_AddBll bll = new CoQuotation_AddBll();
		try {
			if (coofModel != null) {
				
				//cfModel为中间model
				cfModel = coofModel;
				// 梯级报价，人数规模
				// is_tjbj = "是";
				// vis_rsgm = true;
				if (cfModel.getCoof_gm()!=null && cfModel.getCoof_gm().length()>0)
				{
					 is_tjbj = "是";
					 vis_rsgm = true;
				}
				else
				{
					is_tjbj = "否";
					vis_rsgm = false;
				}
				
				
				cfModel.setCoof_min(coofModel.getCoof_max());
				cfModel.setCoof_max(0);
				cfModel.setCoof_gm(getGmStr());

				// 合同类型
				for (CopCompactModel m : cpctList) {
					if (cfModel.getCoof_cpct_id().equals(m.getCpct_id())) {
						cpctModel = m;
					}
				}

				// 产品
				setColList(bll.getCoOfferListList(cfModel.getCoof_id()));
				selectList.clear();
				for (CoOfferListModel m : colList) {
					CoProductModel m1 = new CoProductModel();

					m1.setCopr_id(m.getColi_copr_id());
					m1.setCopr_name(m.getColi_name());
					m1.setCity(m.getCity());
					m1.setCpcr_cpct_id(cpctModel.getCpct_id());

					selectList.add(m1);
				}
				search();

				cfModel.setCoof_id1(cfModel.getCoof_id());
				Clients.showNotification("读取历史报价单成功", null, null, null, 1000);

			} else {
				
				cfModel = new CoOfferModel();
				cfModel.setCoof_name(coofname);
				// cfModel.setCoof_quotemode("");
				// 梯级报价，人数规模
				// is_tjbj = "否";
				// vis_rsgm = false;
				cfModel.setCoof_min(0);
				cfModel.setCoof_max(0);
				cfModel.setCoof_cpct_id(cpctModel.getCpct_id());
				// 合同类型
				// cpctModel = null;
				// 产品
				// selectList.clear();
				// notselectList.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 下一步
	@Command("nextstep")
	public void nextstep(@BindingParam("win") Window win) {
		try {
			if (cfModel.getCoof_name() == null
					|| cfModel.getCoof_name().isEmpty()) {
				Messagebox.show("请输入报价单名称!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (cpctModel == null) {
				Messagebox.show("请选择合同类型!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (selectList.size() == 0) {
				Messagebox.show("请选择至少一个产品!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (is_tjbj.equals("是")
					&& (cfModel.getCoof_min().compareTo(cfModel.getCoof_max()) > 0 || cfModel
							.getCoof_min().equals(cfModel.getCoof_max()))) {
				Messagebox.show("人数规模填写错误:最大值应大于最小值!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				CoQuotation_AddBll bll = new CoQuotation_AddBll();
				CoOfferModel returnModel = new CoOfferModel();
				
				System.out.println(cpctModel.getCpct_id());
				
				// 报价单
				cfModel.setCoof_addname(UserInfo.getUsername());
				cfModel.setCoof_register(Integer.parseInt(coof_register));
				cfModel.setCoof_cola_id(cola_id);
				cfModel.setCoof_gm(getGmStr());
				cfModel.setCoof_state(0);
				cfModel.setCoof_id(coof_id);
				cfModel.setCid(cid);
				cfModel.setCoof_coco_id(coco_id);
				
				//添加报价单
				returnModel = bll.CoOfferAdd(cfModel);
				
				cfModel.setCoof_id(returnModel.getCoof_id());

				// 报价单项目
				colList.clear();
				for (int i = 0; i < selectList.size(); i++) {
					CoOfferListModel model = new CoOfferListModel();

					model.setColi_coof_id(returnModel.getCoof_id());
					model.setColi_copr_id(selectList.get(i).getCopr_id());
					model.setColi_addname(UserInfo.getUsername());
					model.setColi_coco_id(coco_id);
					
					if (coofModel != null) {
						model.setCoof_id(cfModel.getCoof_id1());
					}

					colList.add(model);
				}
				//?
				bll.CoOfferListTemModState(returnModel.getCoof_id(), 0);
				//新增报价单项目
				bll.CoOfferListAdd(colList);
				//?
				bll.CoOfferListTemDelete(returnModel.getCoof_id());
				// 删除报价单项目表中没有项目的大类及服务费
				bll.CoOfferListNoPclassDelete(returnModel.getCoof_id());
				
				//合并项目处理
				bll.NextDataHandle(returnModel.getCoof_id(),
						cfModel.getCoof_id1());
				
				win.detach();

				// 设置传参
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("coofid", returnModel.getCoof_id());
				map.put("colaid", cola_id);
				map.put("cid", cid);
				map.put("coco_id", coco_id);
				map.put("colacompany", cola_company);
				map.put("cfModel", cfModel);
				Window window = (Window) Executions.createComponents(
						"/CoQuotation/CoQuotation_editsec.zul", null, map);
				window.doModal();
			}
		} catch (Exception e) {
			Messagebox.show("跳转下一步失败,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * 获取人数规模的文字描述
	 * 
	 * @return
	 */
	public String getGmStr() {
		String gmStr = "";
		Integer min = cfModel.getCoof_min();
		Integer max = cfModel.getCoof_max();

		try {
			if (min != null && max != null) {
				if (min == 0 && max != 0) {
					gmStr = max + "人以内";
				} else if (min != 0 && max == 0) {
					gmStr = "大于" + min + "人";
				} else if (min != 0 && max != 0) {
					gmStr = min + "人到" + max + "人";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gmStr;
	}

	@Command("close")
	public void close() throws Exception {
		CoQuotation_AddBll bll = new CoQuotation_AddBll();
		//bll.ClearCoOffer();
	}

	public List<CoProductModel> getSelectList() {
		return selectList;
	}

	public void setSelectList(List<CoProductModel> selectList) {
		this.selectList = selectList;
	}

	public List<CoProductModel> getNotselectList() {
		return notselectList;
	}

	public void setNotselectList(List<CoProductModel> notselectList) {
		this.notselectList = notselectList;
	}

	public String getCoof_register() {
		return coof_register;
	}

	public void setCoof_register(String coof_register) {
		this.coof_register = coof_register;
	}

	public ListModelList<PubProCityModel> getCityList() {
		return cityList;
	}

	public void setCityList(ListModelList<PubProCityModel> cityList) {
		this.cityList = cityList;
	}

	public ListModel<PubProCityModel> getCityListModel() {
		return cityListModel;
	}

	public void setCityListModel(ListModel<PubProCityModel> cityListModel) {
		this.cityListModel = cityListModel;
	}

	public String getCopr_type() {
		return copr_type;
	}

	public void setCopr_type(String copr_type) {
		this.copr_type = copr_type;
	}

	public List<CoPclassModel> getClassList() {
		return classList;
	}

	public void setClassList(List<CoPclassModel> classList) {
		this.classList = classList;
	}

	public CoPclassModel getCpcModel() {
		return cpcModel;
	}

	public void setCpcModel(CoPclassModel cpcModel) {
		this.cpcModel = cpcModel;
	}

	public PubProCityModel getPpcModel() {
		return ppcModel;
	}

	public void setPpcModel(PubProCityModel ppcModel) {
		this.ppcModel = ppcModel;
	}

	public CoOfferModel getCfModel() {
		return cfModel;
	}

	public void setCfModel(CoOfferModel cfModel) {
		this.cfModel = cfModel;
	}

	public List<CoAgencyBaseModel> getAgencyList() {
		return agencyList;
	}

	public void setAgencyList(List<CoAgencyBaseModel> agencyList) {
		this.agencyList = agencyList;
	}

	public CoAgencyBaseModel getAbModel() {
		return abModel;
	}

	public void setAbModel(CoAgencyBaseModel abModel) {
		this.abModel = abModel;
	}

	public String getCola_company() {
		return cola_company;
	}

	public void setCola_company(String cola_company) {
		this.cola_company = cola_company;
	}

	public int getCola_id() {
		return cola_id;
	}

	public void setCola_id(int cola_id) {
		this.cola_id = cola_id;
	}

	public List<String> getCoprList() {
		return coprList;
	}

	public void setCoprList(List<String> coprList) {
		this.coprList = coprList;
	}

	public List<CopCompactModel> getCpctList() {
		return cpctList;
	}

	public void setCpctList(List<CopCompactModel> cpctList) {
		this.cpctList = cpctList;
	}

	public CopCompactModel getCpctModel() {
		return cpctModel;
	}

	public void setCpctModel(CopCompactModel cpctModel) {
		this.cpctModel = cpctModel;
	}

	public String getIs_tjbj() {
		return is_tjbj;
	}

	public void setIs_tjbj(String is_tjbj) {
		this.is_tjbj = is_tjbj;
	}

	public Boolean getVis_rsgm() {
		return vis_rsgm;
	}

	public void setVis_rsgm(Boolean vis_rsgm) {
		this.vis_rsgm = vis_rsgm;
	}

	public List<CoOfferModel> getCoofList() {
		return coofList;
	}

	public void setCoofList(List<CoOfferModel> coofList) {
		this.coofList = coofList;
	}

	public CoOfferModel getCoofModel() {
		return coofModel;
	}

	public void setCoofModel(CoOfferModel coofModel) {
		this.coofModel = coofModel;
	}

	public final List<CoOfferListModel> getColList() {
		return colList;
	}

	public final int getCid() {
		return cid;
	}

	public final int getCoco_id() {
		return coco_id;
	}

	public final void setColList(List<CoOfferListModel> colList) {
		this.colList = colList;
	}

	public final void setCid(int cid) {
		this.cid = cid;
	}

	public final void setCoco_id(int coco_id) {
		this.coco_id = coco_id;
	}

	public final ListModelList<CoPclassModel> getSclasslist() {
		return sclasslist;
	}

	public final void setSclasslist(ListModelList<CoPclassModel> sclasslist) {
		this.sclasslist = sclasslist;
	}

	public boolean isDeptDis() {
		return deptDis;
	}

	public void setDeptDis(boolean deptDis) {
		this.deptDis = deptDis;
	}
	
	
}
