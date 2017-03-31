package Controller.CoFinanceManage;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import dal.CoFinanceManage.Cfma_ManualDisposableDal;

import Model.CoAgencyBaseModel;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoFinanceManualDisposableModel;
import Model.CoOfferListModel;
import Model.CoPclassModel;
import Model.CoProductModel;
import Model.EmbaseModel;
import bll.CoFinanceManage.Cfma_ManualDisSpOperateBll;
import bll.CoFinanceManage.Cfma_ManualDisposableBll;
import bll.CoQuotation.CoQuotation_AddBll;

/**
 * 手动添加员工非标
 * 
 * @author Administrator
 * 
 */
public class Cfma_AddEmManualDisposableController {
	private CoBaseModel com = (CoBaseModel) Executions.getCurrent().getArg()
			.get("m");
	private List<CoCompactModel> cocos = new ArrayList<CoCompactModel>();
	private List<CoOfferListModel> cocosinfo = new ArrayList<CoOfferListModel>();
	private Cfma_ManualDisposableBll bll = new Cfma_ManualDisposableBll();
	private List<EmbaseModel> ems;
	private List<EmbaseModel> emssh;
	private Cfma_ManualDisSpOperateBll operabll = new Cfma_ManualDisSpOperateBll();
	private CoFinanceManualDisposableModel m = new CoFinanceManualDisposableModel();
	//private List<String> copas = new ArrayList<String>();
	private Date ownmonthCom = new Date();
	private String[] monthList;
	private int ownmonth;
	private int coco_id = 0;
	private String emname;
	private String xl;
 
	
	private CoCompactModel cocosm=new CoCompactModel();
	private List<CoOfferListModel> copas = new ArrayList<CoOfferListModel>();
	private CoOfferListModel copasm =new CoOfferListModel();
	private CoOfferListModel copasminfo =new CoOfferListModel();
	
	private String copr_type = "";// 产品类别
	private CoPclassModel cpcModel;// 前道产品科目类型
	private List<CoPclassModel> classList;
	private CoProductModel cpmodel;
	private List<CoProductModel> notselectList = new ListModelList<>();
 
	
	public Cfma_AddEmManualDisposableController() throws SQLException {
		cocos = bll.getCocoinfo(com.getCid()); // 获取到合同信息
		// 装配所属月份的combox 只能是未来的5个月
//		ownmonth = Integer.parseInt(new SimpleDateFormat("yyyyMM")
//				.format(ownmonthCom));
//		m.setOwnmonth(ownmonth);
//		for (int i = 0; i < 5; i++) {
//			monthList.add(ownmonth + i);
//		}
		
		monthList=bll.getOwnmonthlist(true);
		
		cocos = bll.getCocoinfo(com.getCid()); // 获取到合同信息
		//copas = bll.getCoPA();
		//获取前道科目list
		CoQuotation_AddBll cqbll = new CoQuotation_AddBll();
		setClassList(cqbll.getclass(""));
		
		copas = bll.getCoofferListall();
	
		 
	}

	
	@Command
	@NotifyChange({"ems","copas"})
	public void setbjdlist(@BindingParam("coco_id") int coco_id)
	{
		this.coco_id = coco_id;
		emssh= ems = bll.getCanAddDisEm(coco_id);

		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		try {
			for (EmbaseModel m : emssh) {
				try {
					if (emname != null && !"".equals(emname)) {
						if (!emname.equals(m.getEmba_name()))
							continue;
					}
					list.add(m);
				}
				
				catch (Exception e) {
					e.printStackTrace();
				}
			} 
			ems=list;
		} catch (Exception e) {
			e.printStackTrace();
		}
	
			
		
	}

	
	// 根据选择的合同查询可以添加非标的员工getprinfo
	@Command
	@NotifyChange({"ems","copas"})
	public void searchEm(@BindingParam("coco_id") int coco_id) {
		// 得到合同id ，查询可以添加非标的员工
		
	
		this.coco_id = coco_id;
		emssh= ems = bll.getCanAddDisEm(coco_id);

		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		try {
			for (EmbaseModel m : emssh) {
				try {
					if (emname != null && !"".equals(emname)) {
						if (!emname.equals(m.getEmba_name()))
							continue;
					}
					list.add(m);
				}
				
				catch (Exception e) {
					e.printStackTrace();
				}
			} 
			ems=list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	

	}
	
	
	// 根据选择的合同查询可以添加非标的员工,"cocosinfo"
	@Command
	@NotifyChange({"cocosinfo","xl"})
	public void getprinfo() {
		// 得到合同id ，查询可以添加非标的员工
		setXl("");
	//	emssh= ems = bll.getCanAddDisEm(coco_id);
	//	this.coco_id = coco_id;
		//copas = bll.getCoofferListall();
		cocosinfo=bll.getCoofferinfo(copasm.getColi_name().toString());
	

	}
	
	
 
	
	
 
	// 检索
		@Command("search")
		@NotifyChange({ "notselectList"})
		public void search() throws Exception {
			try {
				String str = "";
				CoQuotation_AddBll bll = new CoQuotation_AddBll();
				if (!copr_type.isEmpty()) {
					str += " and copr_type='" + copr_type + "'";
				}
				if (cpcModel != null) {
					str += " and copr_copc_id=" + cpcModel.getCopc_id();
				}
	
				str +=" and cpcr_cpct_id=1";
				setNotselectList(new ListModelList<CoProductModel>(
						bll.getCoproductListzmj(str)));
			} catch (Exception e) {
				Messagebox.show("检索失败,请联系IT部门!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				e.printStackTrace();
			}
		}

	// 全选
	@Command
	public void allcheck(@BindingParam("check") Checkbox c,
			@BindingParam("rows") Rows rows) {
		if (rows.getChildren().size() > 0) {
			for (int i = 0; i < rows.getChildren().size(); i++) { // 获取到checkbox中被选择的对象
				Checkbox checkbox = (Checkbox) rows.getChildren().get(i)
						.getChildren().get(0);
				checkbox.setChecked(c.isChecked());
			}
		}
	}

	// 获取到选择的员工添加非标
	@Command
	public void submit(@BindingParam("rows") Rows rows,
			@ContextParam(ContextType.VIEW) Component view,
			@BindingParam("addWin") Window window) {
		if(m.getOwnmonth()==0)
		{
			Messagebox.show("请选择所属月份", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
		else
		{
		String[] str = null;
		boolean flag = false; // 防止多次弹出提示
		Cfma_ManualDisposableDal dal = new Cfma_ManualDisposableDal();
//		m.setCfmd_copr_name(cpmodel.getCopr_name());
//		m.setCfmd_cpac_name(cpmodel.getCpac_name());
//		
		if (copasminfo.getCpac_name()==null || copasminfo.getCpac_name().equals(""))
		{
		
			m.setCfmd_cpac_name("服务费");
		}
		else
		{
			m.setCfmd_cpac_name(copasminfo.getCpac_name());
		}
		m.setCfmd_copr_name(copasminfo.getColi_name());

		if (coco_id != 0) {
			if (m.getCfmd_cpac_name() != null
					&& !m.getCfmd_cpac_name().equals("")) {
				if (m.getCfmd_copr_name() != null
						&& !m.getCfmd_copr_name().equals("")) {
					if (m.getCfmd_Reason() != null
							&& !m.getCfmd_Reason().equals("")) {
						if (m.getCfmd_Receivable() != null) {
							if (rows.getChildren().size() > 0) {
								for (int i = 0; i < rows.getChildren().size(); i++) { // 获取到checkbox中被选择的对象
									Checkbox checkbox = (Checkbox) rows
											.getChildren().get(i).getChildren()
											.get(0);
									if (checkbox.isChecked() == true) { // 如果check被勾选，则对该员工生成添加非标的任务单
										EmbaseModel em = (EmbaseModel) checkbox
												.getValue();
										m.setCfmd_coco_id(coco_id);
										m.setGid(em.getGid());
										m.setCid(com.getCid());
										if (m.getCfmd_Receivable()
												.doubleValue() > 0) { // 如果输入的金额大于0,
											m.setCfmd_state(2);
										
											int id = dal.addCoDispo(m);
											if (id != 0) {
												flag = true;
											}
										} else {
											m.setCfmd_state(4);
											str = operabll.addEmDispo(m, em);
											if (str[0].equals("1")) {
												flag = true;
											} else {
												flag = false;
											}
										}
									}
								}

								if (flag) {
									Binder bind = (Binder) view.getParent()
											.getAttribute("binder");
									Map<String, Object> map = new HashMap<>();
									map.put("ownmonth", m.getOwnmonth());
									bind.postCommand("emlist", map);
									Messagebox.show("添加成功", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									window.detach();
								} else {
									Messagebox.show("添加失败", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
								}
							} else {
								Messagebox.show("未选择员工", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						} else {
							Messagebox.show("收费金额不能为空", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					} else {
						Messagebox.show("收费原因不能为空", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					Messagebox.show("福利项目不能为空", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("财务科目不能为空", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("未选择合同", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	}
	
	
 
	public String getXl() {
		return xl;
	}


	public void setXl(String xl) {
		this.xl = xl;
	}


	public List<CoOfferListModel> getCocosinfo() {
		return cocosinfo;
	}

	public void setCocosinfo(List<CoOfferListModel> cocosinfo) {
		this.cocosinfo = cocosinfo;
	}

	public CoOfferListModel getCopasminfo() {
		return copasminfo;
	}

	public void setCopasminfo(CoOfferListModel copasminfo) {
		this.copasminfo = copasminfo;
	}

	public String getEmname() {
		return emname;
	}

	public void setEmname(String emname) {
		this.emname = emname;
	}

	public String[] getMonthList() {
		return monthList;
	}

	public void setMonthList(String[] monthList) {
		this.monthList = monthList;
	}

	public CoCompactModel getCocosm() {
		return cocosm;
	}

	public void setCocosm(CoCompactModel cocosm) {
		this.cocosm = cocosm;
	}

	public List<CoOfferListModel> getCopas() {
		return copas;
	}

	public void setCopas(List<CoOfferListModel> copas) {
		this.copas = copas;
	}

	public CoOfferListModel getCopasm() {
		return copasm;
	}

	public void setCopasm(CoOfferListModel copasm) {
		this.copasm = copasm;
	}

	public Date getOwnmonthCom() {
		return ownmonthCom;
	}

	public void setOwnmonthCom(Date ownmonthCom) {
		this.ownmonthCom = ownmonthCom;
	}

	 
	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public CoFinanceManualDisposableModel getM() {
		return m;
	}

	public void setM(CoFinanceManualDisposableModel m) {
		this.m = m;
	}

	public List<EmbaseModel> getEms() {
		return ems;
	}

	public void setEms(List<EmbaseModel> ems) {
		this.ems = ems;
	}

	public List<CoCompactModel> getCocos() {
		return cocos;
	}

	public void setCocos(List<CoCompactModel> cocos) {
		this.cocos = cocos;
	}

	public CoBaseModel getCom() {
		return com;
	}

	public void setCom(CoBaseModel com) {
		this.com = com;
	}

	public String getCopr_type() {
		return copr_type;
	}

	public void setCopr_type(String copr_type) {
		this.copr_type = copr_type;
	}

	public CoPclassModel getCpcModel() {
		return cpcModel;
	}

	public void setCpcModel(CoPclassModel cpcModel) {
		this.cpcModel = cpcModel;
	}

	public List<CoPclassModel> getClassList() {
		return classList;
	}

	public void setClassList(List<CoPclassModel> classList) {
		this.classList = classList;
	}

	public CoProductModel getCpmodel() {
		return cpmodel;
	}

	public void setCpmodel(CoProductModel cpmodel) {
		this.cpmodel = cpmodel;
	}

	public List<CoProductModel> getNotselectList() {
		return notselectList;
	}

	public void setNotselectList(List<CoProductModel> notselectList) {
		this.notselectList = notselectList;
	}
	
	
}
