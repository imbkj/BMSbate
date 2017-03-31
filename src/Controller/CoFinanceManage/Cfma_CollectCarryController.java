package Controller.CoFinanceManage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoFinanceManage.Cfma_CollectCarryBll;
import Model.EmbaseModel;
import Util.UserInfo;

/**
 * 
 * @author 苏宏远
 * 结转
 *
 */
public class Cfma_CollectCarryController {
	private final String cfmb_number = Executions.getCurrent().getArg().get("Cfmb_number").toString();
    private final String company = Executions.getCurrent().getArg().get("Company").toString();
    private final String cfsa_cpac_name = Executions.getCurrent().getArg().get("Cfsa_cpac_name").toString();
    private final String coba_client = Executions.getCurrent().getArg().get("coba_client").toString();
    private final String owmno = Executions.getCurrent().getArg().get("owmno").toString();
    private final String billNo =Executions.getCurrent().getArg().get("billNo").toString();
    //private final String collect = Executions.getCurrent().getArg().get("Collect").toString();
    private String company_id;
    private String usname="";
    private String ownmonth;
    private String remak="";
    private BigDecimal recexpense;
    private String gid;
    private List<EmbaseModel> listEmbaseModel =new ListModelList<>();
    private  Cfma_CollectCarryBll bll=new  Cfma_CollectCarryBll();
    String caoname= UserInfo.getUsername();
    
    public Cfma_CollectCarryController(){
    	setCompany_id(cfmb_number.substring(2, 6));
    /**if(collect!=null&&!collect.equals("")){
    		setRecexpense(BigDecimal.valueOf(Double.parseDouble(collect)));
    	}else{
    		setRecexpense(BigDecimal.valueOf(0.00));
    	}*/
    	serch();
    }
    @Command("ownmon")
	public void ownmon(@BindingParam("own") Date d) {
		if (d != null) {
			ownmonth = new SimpleDateFormat("yyyyMM").format(d);
		} else {
			ownmonth = null;
		}
	}
    @Command("serch")
    @NotifyChange({"listEmbaseModel"})
    public void serch(){
    	StringBuilder strwhere = new StringBuilder();
		strwhere.append(" ");
		if(!company_id.isEmpty()){
			strwhere.append(" and cid="+company_id);
		}
		if(!usname.isEmpty()){
			strwhere.append(" and emba_name like '%"+usname+"%'");
		}
		System.out.println(strwhere.toString());
		listEmbaseModel=bll.EmbaseList(strwhere.toString());
    }
    @Command("Check")
    public void Check(@BindingParam("gid") String gid){
    	setGid(gid);
    }
    @Command("subimt")
    public void subimt(@BindingParam("win") Window win,@ContextParam(ContextType.VIEW) Component view){
    	if(recexpense!=null){
    	 if(ownmonth!=null){
    		if(gid!=null){
    			
    			 int a=bll.addCfma_CollectCarry(company_id, cfsa_cpac_name, cfmb_number, 
    					 coba_client, owmno, ownmonth, remak, recexpense, gid, caoname);
    			    if(a>0){
    			    	Binder bind = (Binder) view.getParent().getAttribute("binder");
    					bind.postCommand("refreshW", null);
    					win.detach();
    			    }else{
    			    	Messagebox.show("账单编号有问题，无法进行结转操作！", "操作提示", Messagebox.OK,
	    						Messagebox.NONE);
    			    }
    		   }else{
    			   Messagebox.show("请选择员工！", "操作提示", Messagebox.OK,
   						Messagebox.NONE);
    		   }
    		}else{
    		      Messagebox.show("请选择所属月份！", "操作提示", Messagebox.OK,
    						Messagebox.NONE);
    		}
    	}else{
    	     Messagebox.show("应收费用不能为空！", "操作提示", Messagebox.OK,
				Messagebox.NONE);
    	}
    }
    public String getCfmb_number() {
		return cfmb_number;
	}
	public String getCompany() {
		return company;
	}
	public String getCfsa_cpac_name() {
		return cfsa_cpac_name;
	}
	public String getCoba_client() {
		return coba_client;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public List<EmbaseModel> getListEmbaseModel() {
		return listEmbaseModel;
	}
	public void setListEmbaseModel(List<EmbaseModel> listEmbaseModel) {
		this.listEmbaseModel = listEmbaseModel;
	}
	public String getOwnmonth() {
		return ownmonth;
	}
	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}
	public String getUsname() {
		return usname;
	}
	public void setUsname(String usname) {
		this.usname = usname;
	}
	public String getOwmno() {
		return owmno;
	}
	public String getRemak() {
		return remak;
	}
	public void setRemak(String remak) {
		this.remak = remak;
	}
	public BigDecimal getRecexpense() {
		return recexpense;
	}
	public void setRecexpense(BigDecimal recexpense) {
		this.recexpense = recexpense==null?BigDecimal.ZERO:recexpense.setScale(2, RoundingMode.HALF_UP);
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getBillNo() {
		return billNo;
	}
	/**public String getCollect() {
		return collect;
	}*/
	

}
