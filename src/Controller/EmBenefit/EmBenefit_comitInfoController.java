package Controller.EmBenefit;

import java.text.ParseException;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmBenefit.EmBenefit_commitInfoBll;
import bll.EmBenefit.EmWelfare_ListBll;

import Model.CoAgencyLinkmanModel;
import Model.EmActyProduceModel;
import Model.EmActyProducetypeModel;
import Model.EmActySupProductInfoModel;
import Model.EmActySupplierInfoModel;
import Model.EmWelfareModel;
import Util.UserInfo;

public class EmBenefit_comitInfoController {
	private EmWelfareModel ewm = new EmWelfareModel();
	private List<EmWelfareModel> list = new ListModelList<>();
	private List<EmActySupplierInfoModel> easuList = new ListModelList<>();
	private List<EmActySupProductInfoModel> easpList = new ListModelList<>();
	private List<CoAgencyLinkmanModel> linkmanList = new ListModelList<>();

	private String username = UserInfo.getUsername();
	private Window win = (Window) Path.getComponent("/winEmpinfo");
	private EmBenefit_commitInfoBll bll = new EmBenefit_commitInfoBll();
	private Integer daid = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Date need;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private boolean link = false;
	private List<String> stlist=bll.getCopStandard();
	
	private List<EmActyProduceModel> plist = bll.getEmActyProduce();
	private List<EmActyProducetypeModel> ptlist = new ArrayList<EmActyProducetypeModel>();
	private EmActyProduceModel pmodel=new EmActyProduceModel();
	private EmActyProducetypeModel ptm=new EmActyProducetypeModel();
	private boolean visType=false;

	public EmBenefit_comitInfoController() {
		ewm.setEmwf_id(daid);
		setList(ewm);
		ewm.setEmwf_company(list.get(0).getEmwf_company());
		ewm.setEmwf_name(list.get(0).getEmwf_name());
		ewm.setEmwf_intime(list.get(0).getEmwf_intime());
		ewm.setEmwf_idcard(list.get(0).getEmwf_idcard());
		ewm.setEmbf_name(list.get(0).getEmbf_name());
		ewm.setEmwf_need(list.get(0).getEmwf_need());
		ewm.setEmwf_paykind(list.get(0).getEmwf_paykind());
		ewm.setEmwf_delivery(list.get(0).getEmwf_delivery());
		ewm.setEmwf_family(list.get(0).getEmwf_family());
		ewm.setEmwf_standard(list.get(0).getEmwf_standard());
		ewm.setSuppName(list.get(0).getSuppName());
		ewm.setProductName(list.get(0).getProductName());
		ewm.setEmwf_amount(list.get(0).getEmwf_amount());
		ewm.setEmwf_charge(list.get(0).getEmwf_charge());
		ewm.setEmwf_prod_id(list.get(0).getEmwf_prod_id());
		ewm.setEmwf_produce(list.get(0).getEmwf_produce());
		ewm.setEmwf_producenum(list.get(0).getEmwf_producenum());
		ewm.setEmwf_prty_id(list.get(0).getEmwf_prty_id());
		if(list.get(0).getEmwf_prod_id()!=null)
		{
			pmodel=bll.getEmActyProduceInfo(list.get(0).getEmwf_prod_id());
		}
		if(list.get(0).getEmwf_prty_id()!=null)
		{
			ptm=bll.getEmActyProduceTypeInfo(list.get(0).getEmwf_prty_id());
		}
		ptlist=pmodel.getPtypeList();
		if(list.get(0).getEmwf_produce()!=null||(ptlist!=null&&ptlist.size()>0))
		{
			visType=true;
		}
		setEasuList();
		setEaspList(list.get(0).getEmwf_embf_id());
		if (easpList.size() == 1) {
			ewm.setProductName(easpList.get(0).getProd_name());
			ewm.setEmwf_gift_id(easpList.get(0).getProd_id());
		}

		if (ewm.getEmwf_need() != null && !ewm.getEmwf_need().equals("")) {
			try {
				need = sdf.parse(ewm.getEmwf_need());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		linkmanList=bll.getLinkInfo(list.get(0).getGid());
		if (linkmanList.size()>0 && linkmanList.size()==1) {
			ewm.setLinkId(linkmanList.get(0).getCali_id());
			ewm.setLinkman(linkmanList.get(0).getCali_name());
			ewm.setMobile(linkmanList.get(0).getCali_mobile());
			ewm.setAddress(linkmanList.get(0).getCali_address());
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

	@Command
	@NotifyChange({"link","ewm"})
	public void disLink() {
		if (ewm.getEmwf_delivery() != null) {
			if (ewm.getEmwf_delivery().equals("公司送货上门")) {
				link = true;
			} else {
				link = false;
				ewm.setLinkId(null);
				ewm.setLinkman(null);
				ewm.setMobile(null);
				ewm.setAddress(null);
			}
		}
	}
	
	@Command
	@NotifyChange("ewm")
	public void modLinkList(@BindingParam("a") CoAgencyLinkmanModel m){
		if (m!=null) {
			ewm.setLinkId(m.getCali_id());
			ewm.setLinkman(m.getCali_name());
			ewm.setMobile(m.getCali_mobile());
			ewm.setAddress(m.getCali_address());
		}
	}

	@Command("submit")
	@NotifyChange("list")
	public void submit() {
		if (pmodel==null||pmodel.getProd_name()==null) {
			Messagebox.show("请选择产品名称", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if((ptm==null||ptm.getPrty_name()==null)&&!pmodel.getProd_name().equals("面包卷"))
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
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							if (need != null) {
								ewm.setEmwf_need(sdf.format(need));
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
							// ewm.setEmwf_state(3);
							Integer i = bll.mod(ewm);
							if (i > 0) {
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

	public List<EmWelfareModel> getList() {
		return list;
	}

	public void setList(EmWelfareModel em) {
		this.list = bll.getList(em);
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
		em.setTypes(list.get(0).getEmbf_mold());
		this.easuList = bll.getSuppList(em);
	}

	public List<EmActySupProductInfoModel> getEaspList() {
		return easpList;
	}

	public void setEaspList(Integer id) {
		this.easpList = bll.getProductList(id);
	}

	public boolean isLink() {
		return link;
	}

	public void setLink(boolean link) {
		this.link = link;
	}

	public List<CoAgencyLinkmanModel> getLinkmanList() {
		return linkmanList;
	}

	public void setLinkmanList(List<CoAgencyLinkmanModel> linkmanList) {
		this.linkmanList = linkmanList;
	}

	public List<String> getStlist() {
		return stlist;
	}

	public void setStlist(List<String> stlist) {
		this.stlist = stlist;
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
