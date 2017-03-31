package Controller.CoCompact;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;
import bll.CoCompact.EmBase_CompactAllotBll;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Model.CoglistModel;
import Model.EmbaseModel;

public class Embase_CompactAllotController {
	// 员工列表
	private List<EmbaseModel> ebList = new ListModelList<>();
	private List<EmbaseModel> ebList2 = new ListModelList<>();
	// 报价单项目列表
	private List<CoOfferListModel> cfList = new ListModelList<>();
	private List<CoOfferListModel> cfList2 = new ListModelList<>();
	// 合同列表
	private List<CoCompactModel> coList = new ListModelList<>();
	// 报价单列表
	private List<CoOfferModel> cofList = new ListModelList<>();

	// 系统项目类型列表
	private List<CoglistModel> cglist = new ListModelList<>();

	private EmbaseModel ebm = new EmbaseModel();
	private CoOfferListModel cfm = new CoOfferListModel();

	private EmBase_CompactAllotBll bll = new EmBase_CompactAllotBll();

	private CoBaseModel cbm = (CoBaseModel) Executions.getCurrent().getArg()
			.get("model");
	private Integer empnum = 0;
	private Integer empItem = 0;
	private boolean EmpChecked = false;
	private boolean ProductChecked = false;
	private boolean ProductAddChecked = false;
	private Integer maxNum = 0;
	private String toolTips = "";

	private Integer num;
	private String selectItem;

	private Window win;

	public Embase_CompactAllotController() {

		ebm.setCid(cbm.getCid());
		ebm.setEmba_state(1);
		setCfList(cbm.getCid(), null, null, null);
		setCoList(cbm.getCid());
		if (coList.size() > 0) {
			setCofList(coList, true);
		}

		setEbList(ebm);
		if (ebList.size() > 1000) {
			ebm.setTop(true);
			ebm.setTopNum(300);
			maxNum = 300;
			toolTips = "由于该公司人数过多,已限制当前显示人数上限为:";
			setEbList(ebm);
		}
		cglist = bll.getcgList(cbm.getCid());
		selectItem = "全部";
		ebList2 = ebList;
		cfList2 = cfList;
	}
	
	@Command
	public void winCa(@BindingParam("a") Window w){
		win=w;
	}

	@Command
	@NotifyChange({ "ebList2", "cglist" })
	public void research() {
		if (maxNum > 0) {
			ebm.setTop(true);
			ebm.setTopNum(maxNum);
		} else {
			ebm.setTop(false);
			ebm.setTopNum(0);
		}
		cglist = bll.getcgList(cbm.getCid());
		setEbList(ebm);
		searchEmp();
	}

	@Command
	@NotifyChange("ebList2")
	public void searchEmp() {
		Combobox cb = (Combobox) win.getFellow("allot");
		if (cb.getSelectedItem()!=null) {
			num=Integer.valueOf(cb.getSelectedItem().getValue().toString());
		}
		
		ebList2 = new ListModelList<>();

		for (EmbaseModel em : ebList) {
			if (em.getGid().equals(137474)) {
				System.out.print(em.getCglist().size());
			}
			if (ebm.getEmba_wt() != null && em.getEmba_wt() != null
					&& !em.getEmba_wt().contains(ebm.getEmba_wt())) {
				continue;
			}

			if (ebm.getGid() != null
					&& !em.getGid().toString()
							.contains(ebm.getGid().toString())) {
				continue;
			}

			if (ebm.getEmba_name() != null) {
				if (ebm.getEmba_name().contains(",")) {
					boolean b = false;
					String[] s = ebm.getEmba_name().split(",");
					for (int i = 0; i < s.length; i++) {
						if (em.getEmba_name().trim().contains(s[i].trim())) {
							b = true;
							break;
						}
					}
					if (!b) {
						continue;
					}
				} else {

					if (!em.getEmba_name().contains(ebm.getEmba_name())
							&& !em.getEmba_spell().contains(ebm.getEmba_name())) {
						continue;
					}
				}

			}
			if (ebm.getEmba_idcard() != null
					&& !em.getEmba_idcard().contains(ebm.getEmba_idcard())) {
				continue;
			}
			
			if (num != null) {
				if (num.equals(0)) {
					if (selectItem.equals("全部")) {
						if (em.getCglist() != null) {
							if (em.getCglist().size() > 0) {
								continue;
							}
						}
					} else {
						
						if (em.getCglist() != null) {
							boolean b= false;
							for (CoglistModel m : em.getCglist()) {
								if (m.getColi_name().equals(selectItem)) {
									b=true;
									break;
								}
							}
							if (b) {
								continue;
							}
						}
					}
				} else if (num.equals(1)) {
					
					if (selectItem.equals("全部")) {
						if (em.getCglist() != null) {
							if (em.getCglist().size()<1) {
								continue;
							}
						} else {
							continue;
						}
					} else {
						boolean b = false;
						if (em.getCglist() != null) {
							for (CoglistModel m : em.getCglist()) {
								if (m.getColi_name().equals(selectItem)) {
									b = true;
									break;
								}
							}
						}
						if (!b) {
							continue;
						}
					}
				}

			}
			
			ebList2.add(em);
		}
	}

	@Command
	@NotifyChange({ "cfList2", "cofList" })
	public void searchProduct(@BindingParam("a") Boolean b) {
		boolean cocheck = false;
		boolean cfcheck = false;

		cfList2 = new ListModelList<>();
		for (CoOfferListModel coliM : cfList) {
			for (CoCompactModel cocoM : coList) {
				if (coliM.getCoId().equals(cocoM.getCoco_id())) {
					coliM.setCococheck(cocoM.isChecked());
				}
				cocheck = cocheck ? true : cocoM.isChecked();
			}
			for (CoOfferModel coofM : cofList) {
				if (coliM.getCoof_id().equals(coofM.getCoof_id())) {
					coliM.setCoofcheck(coofM.isChecked());
				}
				cfcheck = cfcheck ? true : coofM.isChecked();
			}

			if (cocheck && !coliM.isCococheck()) {
				continue;
			} else if (cfcheck && !coliM.isCoofcheck()) {
				continue;
			} else if (cfm.getColi_city() != null
					&& !coliM.getColi_city().contains(cfm.getColi_city())) {
				continue;
			} else if (cfm.getColi_name() != null
					&& !coliM.getColi_name().contains(cfm.getColi_name())) {
				continue;
			}
			cfList2.add(coliM);
		}
		if (b != null && b) {
			if (coList.size() > 0) {
				boolean b1 = true;
				for (CoCompactModel m : coList) {
					if (m.isChecked()) {
						b1 = false;
						break;
					}
				}
				setCofList(coList, b1);
			}
		}
	}

	@Command("checkAllEmp")
	@NotifyChange({ "ebList2", "empnum" })
	public void checkAllEmp() {
		empnum = 0;
		for (EmbaseModel em : ebList2) {
			if (EmpChecked) {
				empnum++;
			}
			em.setChecked(EmpChecked);
		}

	}

	@Command("checkEmp")
	@NotifyChange("empnum")
	public void checkEmp(@BindingParam("a") EmbaseModel em) {

		if (em.isChecked()) {
			empnum++;
		} else {
			empnum--;
		}
	}

	@Command
	@NotifyChange({ "empItem" })
	public void checkAP() {

		for (CoOfferListModel coofM : cfList2) {
			coofM.setChecked(ProductChecked);
			/*
			 * if (coofM.isChecked()) { Messagebox.show("已有项目[" +
			 * coofM.getColi_name() + "],不能重复补充.", "操作提示", Messagebox.OK,
			 * Messagebox.ERROR);
			 * 
			 * coofM.setChecked(false); return; }
			 */
			BindUtils.postNotifyChange(null, null, coofM, "checked");

		}
		empItem = 0;
		for (CoOfferListModel cm : cfList) {
			if (cm.isChecked()) {
				empItem++;
			}
		}
	}

	@Command
	public void updateInfo(@BindingParam("a") CoOfferListModel cm,
			@BindingParam("b") Integer type, @BindingParam("c") Object obj) {
		Datebox db;

		db = (Datebox) obj;

		if (type.equals(1)) {
			if (db.getValue() == null) {
				cm.setCgli_startdate2(null);
			} else {
				cm.setCgli_startdate2(db.getValue());
			}
		} else if (type.equals(2)) {
			if (db.getValue() == null) {
				cm.setCgli_startdate(null);
			} else {
				cm.setCgli_startdate(db.getValue());
			}
		}

	}

	// 复制起始日
	@Command("copysd")
	@NotifyChange("cfList2")
	public void copysd(@BindingParam("a") Integer cellIndex,
			@BindingParam("b") Date d, @BindingParam("rowIndex") Integer num) {

		for (int i = num; i < cfList2.size(); i++) {
			if (cellIndex.equals(1)) {
				cfList2.get(i).setCgli_startdate2(d);
			} else if (cellIndex.equals(2)) {
				cfList2.get(i).setCgli_startdate(d);
			}
		}
	}

	@Command
	@NotifyChange("empItem")
	public void checkproduct(@BindingParam("a") CoOfferListModel coliM) {
		for (CoOfferListModel m : cfList) {
			if (m.getColi_id() != coliM.getColi_id()
					&& m.getColi_copr_id().equals(coliM.getColi_copr_id())
					&& m.isChecked() && coliM.isChecked()) {

				Messagebox.show("已有项目[" + coliM.getColi_name() + "],不能重复补充.",
						"操作提示", Messagebox.OK, Messagebox.ERROR);

				coliM.setChecked(false);
				BindUtils.postNotifyChange(null, null, coliM, "checked");
				return;
			}
		}
		empItem = 0;
		for (CoOfferListModel cm : cfList) {
			if (cm.isChecked()) {
				empItem++;
			}
		}
	}

	public void reload() {
		EmbaseModel em = new EmbaseModel();
		em.setCid(cbm.getCid());
		em.setEmba_state(1);
		setEbList(em);
		setCfList(cbm.getCid(), null, null, null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		try {
			d = sdf.parse("2015-7-1");
			for (CoOfferListModel m : cfList) {
				m.setCgli_startdate(d);
				m.setCgli_startdate2(d);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		searchEmp();
		searchProduct(null);
		setEmpChecked(false);
		setProductChecked(false);
		empnum = 0;
		empItem = 0;

		ebList2 = ebList;
		cfList2 = cfList;
	}

	public boolean checkItem() {
		boolean b = true;
		for (EmbaseModel em : ebList) {
			if (em.isChecked()) {
				for (CoOfferListModel cm : cfList) {
					if (cm.isAddchecked()) {
						if (bll.getEmpCoofferlist(em.getGid(), cm.getColi_id())) {
							Messagebox.show(
									em.getEmba_name() + " 已有项目["
											+ cm.getColi_name() + "],不能重复补充.",
									"操作提示", Messagebox.OK, Messagebox.ERROR);
							return false;
						}
					}
				}
			}
		}
		return b;
	}

	public boolean checkSubmitInfo() {
		boolean b = false;

		for (EmbaseModel em : ebList) {
			if (em.isChecked()) {
				b = true;
				break;
			}
		}

		if (!b) {
			Messagebox.show("请选择员工!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return false;
		}

		b = false;
		for (CoOfferListModel cm : cfList) {
			if (cm.isChecked()) {
				if (cm.getCgli_startdate() == null) {

					Messagebox.show("请录入[" + cm.getColi_name() + "]收费起始时间",
							"操作提示", Messagebox.OK, Messagebox.ERROR);
					return false;
				}
				if (cm.getCgli_startdate2() == null) {
					Messagebox.show("请录入[" + cm.getColi_name() + "]服务起始时间",
							"操作提示", Messagebox.OK, Messagebox.ERROR);
					return false;
				}

				b = true;
			}
		}

		if (!b) {
			Messagebox
					.show("请选择报价单项目", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return false;
		}

		if (!checkItem()) {
			return false;
		}

		return true;
	}

	@Command
	@NotifyChange({ "ebList2", "cfList2", "EmpChecked", "ProductChecked" })
	public void submit(@BindingParam("a") Integer a) {
		for (CoOfferListModel cm : cfList) {
			if (a.equals(2)) {
				cm.setAddchecked(cm.isChecked());
			} else {
				cm.setAddchecked(false);
			}
		}
		if (!checkSubmitInfo()) {
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

							Integer i = bll.addCoglist(ebList, cfList);
							if (i > 0) {
								Messagebox.show("添加成功", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								reload();

							} else {
								Messagebox.show("添加失败", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
							}
						}
					}
				});

	}

	public List<EmbaseModel> getEbList() {
		return ebList;
	}

	public void setEbList(EmbaseModel em) {
		this.ebList = bll.getEmbaseList(em);
	}

	public List<EmbaseModel> getEbList2() {
		return ebList2;
	}

	public void setEbList2(List<EmbaseModel> ebList2) {
		this.ebList2 = ebList2;
	}

	public List<CoOfferListModel> getCfList() {
		return cfList;
	}

	public void setCfList(Integer cid, List<CoCompactModel> cl1,
			List<CoOfferModel> cl2, String name) {
		this.cfList = bll.getColList(cid, cl1, cl2, name);
	}

	public List<CoCompactModel> getCoList() {
		return coList;
	}

	public void setCoList(Integer cid) {
		this.coList = bll.getCompactList(cid);
	}

	public List<CoOfferModel> getCofList() {
		return cofList;
	}

	public void setCofList(List<CoCompactModel> list, boolean b) {
		this.cofList = bll.getCofList(list, b);
	}

	public EmbaseModel getEbm() {
		return ebm;
	}

	public void setEbm(EmbaseModel ebm) {
		this.ebm = ebm;
	}

	public CoOfferListModel getCfm() {
		return cfm;
	}

	public void setCfm(CoOfferListModel cfm) {
		this.cfm = cfm;
	}

	public Integer getEmpnum() {
		return empnum;
	}

	public void setEmpnum(Integer empnum) {
		this.empnum = empnum;
	}

	public List<CoOfferListModel> getCfList2() {
		return cfList2;
	}

	public void setCfList2(List<CoOfferListModel> cfList2) {
		this.cfList2 = cfList2;
	}

	public boolean isEmpChecked() {
		return EmpChecked;
	}

	public void setEmpChecked(boolean empChecked) {
		EmpChecked = empChecked;
	}

	public boolean isProductChecked() {
		return ProductChecked;
	}

	public void setProductChecked(boolean productChecked) {
		ProductChecked = productChecked;
	}

	public boolean isProductAddChecked() {
		return ProductAddChecked;
	}

	public void setProductAddChecked(boolean productAddChecked) {
		ProductAddChecked = productAddChecked;
	}

	public Integer getEmpItem() {
		return empItem;
	}

	public void setEmpItem(Integer empItem) {
		this.empItem = empItem;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public String getToolTips() {
		return toolTips;
	}

	public void setToolTips(String toolTips) {
		this.toolTips = toolTips;
	}

	public String getSelectItem() {
		return selectItem;
	}

	public void setSelectItem(String selectItem) {
		this.selectItem = selectItem;
	}

	public List<CoglistModel> getCglist() {
		return cglist;
	}

	public void setCglist(List<CoglistModel> cglist) {
		this.cglist = cglist;
	}

}
