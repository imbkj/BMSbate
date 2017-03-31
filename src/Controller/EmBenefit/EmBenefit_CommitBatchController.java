package Controller.EmBenefit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import Model.EmActyProduceModel;
import Model.EmActyProducetypeModel;
import Model.EmActySupProductInfoModel;
import Model.EmActySupplierInfoModel;
import Model.EmWelfareModel;
import Util.UserInfo;
import bll.EmBenefit.EmBenefit_commitInfoBll;

public class EmBenefit_CommitBatchController {
	private EmWelfareModel ewm = new EmWelfareModel();
	private List<EmActySupplierInfoModel> easuList = new ListModelList<>();
	private List<EmActySupProductInfoModel> easpList = new ListModelList<>();
	private String username = UserInfo.getUsername();
	private EmBenefit_commitInfoBll bll = new EmBenefit_commitInfoBll();

	private List<EmWelfareModel> list = (List<EmWelfareModel>) Executions
			.getCurrent().getArg().get("list");
	private int embf_id = 0;
	private Window win = (Window) Path.getComponent("/winEmpBatch");
	private List<String> stlist = bll.getCopStandard();
	private Date need;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String emwf_remark;
	private List<EmActyProduceModel> plist = bll.getEmActyProduce();
	private List<EmActyProducetypeModel> ptlist = new ArrayList<EmActyProducetypeModel>();
	private EmActyProduceModel pmodel=new EmActyProduceModel();
	private EmActyProducetypeModel ptm=new EmActyProducetypeModel();
	private boolean visType=false;
	
	public EmBenefit_CommitBatchController() {
		if (Executions.getCurrent().getArg().get("embf_id") != null) {
			embf_id = (Integer) Executions.getCurrent().getArg().get("embf_id");
		}
		if (embf_id > 0) {
			easpList = bll.getProductList(embf_id);
		}
		setEasuList();
		ewm.setEmwf_amount(1);
	}

	@Command("searchPd")
	@NotifyChange("easpList")
	public void searchPd(@BindingParam("a") Combobox cb) {
		if (cb.getSelectedItem() != null) {
			setEaspList(Integer.valueOf(cb.getSelectedItem().getValue()
					.toString()));
			Combobox cb1 = (Combobox) win.getFellow("pd");
			cb1.setValue("");
		}

	}

	@Command("selectPd")
	@NotifyChange({"ptlist","pmodel","ptm","visType"})
	public void selectPd(@BindingParam("a") Combobox cb,@BindingParam("pt") Combobox pt) {
		if (cb.getSelectedItem() != null) {
			pmodel=cb.getSelectedItem().getValue();
			ewm.setEmwf_gift_id(pmodel.getProd_id());
			ptlist=pmodel.getPtypeList();
			pt.setValue("");
			ptm=null;
			if(ptlist.size()>0)
			{
				visType=true;
			}
			else
			{
				visType=false;
			}
		}
	}
	
	@Command()
	@NotifyChange("ptm")
	public void selectPtd(@BindingParam("pt") Combobox pt) {
		if (pt.getSelectedItem() != null) {
			ptm=pt.getSelectedItem().getValue();
		}
	}

	@Command("submit")
	@NotifyChange("list")
	public void submit() {
		if (pmodel==null||pmodel.getProd_name()==null) {
			Messagebox.show("请选择产品名称", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if(ptm==null||ptm.getPrty_name()==null)
		{
			Messagebox.show("请选择产品类型", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if(ewm.getEmwf_producenum()==null||ewm.getEmwf_producenum()<=0)
		{
			Messagebox.show("请填写产品数量", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							if (need != null) {
								ewm.setEmwf_need(sdf.format(need));
							}
							if (emwf_remark != null && emwf_remark.equals("")) {
								ewm.setEmwf_remark(emwf_remark);
							}
							if (pmodel!=null&&pmodel.getProd_id()!=null) {
								ewm.setEmwf_prod_id(pmodel.getProd_id());
							}
							if(ptm!=null&&ptm.getPrty_id()!=null)
							{
								ewm.setEmwf_prty_id(ptm.getPrty_id());
							}
							if(ptm!=null&&ptm.getPrty_name()!=null)
							{
								ewm.setEmwf_produce(ptm.getPrty_name());
							}
							boolean b = true;
							Integer i = 0;
							for (int j = 0; j < list.size(); j++) {
								if (list.get(j).isChecked()) {
									ewm.setEmwf_id(list.get(j).getEmwf_id());
									i = bll.mod(ewm);
									if (i <= 0) {
										b = false;
									}
									ewm.setEmwf_id(null);
								}
							}

							if (b) {
								Messagebox.show("操作成功", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
							} else {
								Messagebox.show("操作失败", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}

				});
	}

	public EmWelfareModel getEwm() {
		return ewm;
	}

	public void setEwm(EmWelfareModel ewm) {
		this.ewm = ewm;
	}

	public Date getNeed() {
		return need;
	}

	public void setNeed(Date need) {
		this.need = need;
	}

	public List<EmActySupplierInfoModel> getEasuList() {
		return easuList;
	}

	public void setEasuList() {
		EmActySupplierInfoModel em = new EmActySupplierInfoModel();
		em.setSupp_state(1);
		this.easuList = bll.getSuppList(em);
	}

	public List<EmActySupProductInfoModel> getEaspList() {
		return easpList;
	}

	public void setEaspList(Integer supp_id) {
		EmActySupProductInfoModel em = new EmActySupProductInfoModel();
		em.setProd_state(1);
		em.setBstate(true);
		// em.setProd_supid(supp_id);
		this.easpList = bll.getSupProductList(em);
	}

	public void setEaspList(List<EmActySupProductInfoModel> easpList) {
		this.easpList = easpList;
	}

	public List<String> getStlist() {
		return stlist;
	}

	public void setStlist(List<String> stlist) {
		this.stlist = stlist;
	}

	public String getEmwf_remark() {
		return emwf_remark;
	}

	public void setEmwf_remark(String emwf_remark) {
		this.emwf_remark = emwf_remark;
	}

	public List<EmActyProduceModel> getPlist() {
		return plist;
	}

	public void setPlist(List<EmActyProduceModel> plist) {
		this.plist = plist;
	}

	public List<EmActyProducetypeModel> getPtlist() {
		return ptlist;
	}

	public void setPtlist(List<EmActyProducetypeModel> ptlist) {
		this.ptlist = ptlist;
	}

	public EmActyProduceModel getPmodel() {
		return pmodel;
	}

	public void setPmodel(EmActyProduceModel pmodel) {
		this.pmodel = pmodel;
	}

	public EmActyProducetypeModel getPtm() {
		return ptm;
	}

	public void setPtm(EmActyProducetypeModel ptm) {
		this.ptm = ptm;
	}

	public boolean isVisType() {
		return visType;
	}

	public void setVisType(boolean visType) {
		this.visType = visType;
	}
	
}
