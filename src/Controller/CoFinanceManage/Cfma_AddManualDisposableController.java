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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.CoFinanceManage.Cfma_ManualDisposableDal;

import bll.CoFinanceManage.Cfma_ManualDisSpOperateBll;
import bll.CoFinanceManage.Cfma_ManualDisposableBll;
import bll.CoQuotation.CoQuotation_AddBll;

import Controller.EmResidencePermit.newExcelImpl;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoFinanceManualDisposableModel;
import Model.CoOfferListModel;
import Model.CoPclassModel;
import Model.CoProductModel;
import Util.UserInfo;

/**
 * 手动添加公司非标
 * 
 * @author Administrator
 * 
 */
public class Cfma_AddManualDisposableController {

	private CoBaseModel com = (CoBaseModel) Executions.getCurrent().getArg()
			.get("m");
	private Date ownmonthCom = new Date();
	private String[] monthList;
	private int ownmonth;
	private CoFinanceManualDisposableModel m = new CoFinanceManualDisposableModel();
	private Cfma_ManualDisposableBll bll = new Cfma_ManualDisposableBll();
	private List<CoCompactModel> cocos = new ArrayList<CoCompactModel>();
	private CoCompactModel cocosm=new CoCompactModel();
	private List<CoOfferListModel> copas = new ArrayList<CoOfferListModel>();
	private CoOfferListModel copasm =new CoOfferListModel();
	private List<CoOfferListModel> copasinfo = new ArrayList<CoOfferListModel>();
	private CoOfferListModel copasminfo =new CoOfferListModel();
	
	private String copr_type = "";// 产品类别
	private CoPclassModel cpcModel;// 前道产品科目类型
	private List<CoPclassModel> classList;
	private CoProductModel cpmodel;
	private List<CoProductModel> notselectList = new ListModelList<>();
	private String xl="";
	public Cfma_AddManualDisposableController() throws SQLException {
		
		

		// 装配所属月份的combox 只能是未来的5个月
//		ownmonth = Integer.parseInt(new SimpleDateFormat("yyyyMM")
//				.format(ownmonthCom));
//		m.setOwnmonth(ownmonth);
//		for (int i = 0; i < 5; i++) {
//			monthList.add(ownmonth + i);
//		}
//		
		
		monthList=bll.getOwnmonthlist(true);
		cocos = bll.getCocoinfo(com.getCid()); // 获取到合同信息
		
		
		
		//获取前道科目list
		CoQuotation_AddBll cqbll = new CoQuotation_AddBll();
		setClassList(cqbll.getclass(""));
		 

	}
	// 根据选择的合同查询可以添加非标的员工
	@Command
	@NotifyChange({"copasinfo"})
	public void getprinfo() {
		// 得到合同id ，查询可以添加非标的员工
		setXl("");
		//emssh= ems = bll.getCanAddDisEm(coco_id);
		//this.coco_id = coco_id;
	//	copas = bll.getCoofferListall();
		copasinfo=bll.getCoofferinfo(copasm.getColi_name().toString());
	

	}
	
	
	
	@Command 
	@NotifyChange("copas")
	public void setbjdlist()
	{
		copas = bll.getCoofferListall();
		
	}

	// 提交保存数据
	@Command
	public void commit(@BindingParam("addcoWin") Window window,
			@ContextParam(ContextType.VIEW) Component view,
			@BindingParam("cocoCom") Combobox combobox) {
		
		if(m.getOwnmonth()==0)
		{
			Messagebox.show("请选择所属月份", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
		else
		{
		Cfma_ManualDisposableDal dal = new Cfma_ManualDisposableDal();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ownmonth", m.getOwnmonth());
		
		if (copasminfo.getCpac_name()==null || copasminfo.getCpac_name().equals(""))
		{
			m.setCfmd_cpac_name("服务费");
		
		}
		else
		{
			m.setCfmd_cpac_name(copasminfo.getCpac_name());
		}
		m.setCfmd_copr_name(copasminfo.getColi_name());
		if (combobox.getSelectedItem() != null) {
			if (m.getCfmd_Receivable() != null
					&& !"".equals(m.getCfmd_Receivable())) {
				if (m.getCfmd_cpac_name() != null
						&& !"".equals(m.getCfmd_cpac_name())) {
					if (m.getCfmd_copr_name() != null
							&& !"".equals(m.getCfmd_copr_name())) {
						if (m.getCfmd_Reason() != null
								&& !"".equals(m.getCfmd_Reason())) {
							m.setCfmd_addname(UserInfo.getUsername()); // 设置添加人
							m.setCid(com.getCid());
							m.setCfmd_coco_id((Integer) combobox
									.getSelectedItem().getValue());
							if (m.getCfmd_Receivable().doubleValue() > 0) {
								System.out.println(1);
								m.setCfmd_state(2);
								
								m.setGid(0);
								
								int id = dal.addCoDispo(m);
								
								if (id > 0) {
									Binder bind = (Binder) view.getParent()
											.getAttribute("binder");
									bind.postCommand("colist", map);
									Messagebox.show("添加成功", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									window.detach();
								} else {
									Messagebox.show("添加失败", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
								}
							} else {
								// 提交数据，生成任务单
								Cfma_ManualDisSpOperateBll operabll = new Cfma_ManualDisSpOperateBll();
								m.setCfmd_state(4); // 状态4为审核中
								System.out.println(2);
								String str[] = operabll.addCoDispo(m, com);
								if (str[0].equals("1")) {
									Binder bind = (Binder) view.getParent()
											.getAttribute("binder");
									bind.postCommand("colist", map);
									Messagebox.show(str[1], "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									window.detach();
								} else {
									Messagebox.show(str[1], "操作提示",
											Messagebox.OK, Messagebox.ERROR);
								}
							}
						} else {
							Messagebox.show("收费原因不能为空", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					} else {
						Messagebox.show("福利产品不能为空", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					Messagebox.show("财务科目不能为空", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("应收费用不能为空", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择合同", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
		}
	}




	public String getXl() {
		return xl;
	}
	public void setXl(String xl) {
		this.xl = xl;
	}
	public List<CoOfferListModel> getCopasinfo() {
		return copasinfo;
	}
	public void setCopasinfo(List<CoOfferListModel> copasinfo) {
		this.copasinfo = copasinfo;
	}
	public CoOfferListModel getCopasminfo() {
		return copasminfo;
	}
	public void setCopasminfo(CoOfferListModel copasminfo) {
		this.copasminfo = copasminfo;
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


	public List<CoCompactModel> getCocos() {
		return cocos;
	}

	public void setCocos(List<CoCompactModel> cocos) {
		this.cocos = cocos;
	}

	public CoFinanceManualDisposableModel getM() {
		return m;
	}

	public void setM(CoFinanceManualDisposableModel m) {
		this.m = m;
	}


	public String[] getMonthList() {
		return monthList;
	}


	public void setMonthList(String[] monthList) {
		this.monthList = monthList;
	}


	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
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
