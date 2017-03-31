package Controller.EmTax;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import Model.PropersonTaxationModel;
import bll.EmTax.PropersonTaxationBll;
import bll.LoginBll;

public class PropersonTaxationController {
    
	private PropersonTaxationBll bll =new PropersonTaxationBll();
	private LoginBll logbll =new LoginBll();
	
	private List<String> clientList=null;//客服列
	private String clientName="";//客服名
	
	private List<PropersonTaxationModel> list = new ArrayList<PropersonTaxationModel>();
	private List<PropersonTaxationModel> listAll = new ArrayList<PropersonTaxationModel>();
	private List<PropersonTaxationModel> listFinance = new ArrayList<PropersonTaxationModel>();
	private List<PropersonTaxationModel> listEnst = new ArrayList<PropersonTaxationModel>();
	private List<PropersonTaxationModel> listForex = new ArrayList<PropersonTaxationModel>();
	
	private Integer secusnum= 0;//派遣客户数
	private Integer sestanum= 0;//派遣员工数
	private Integer agcusnum= 0;//代理客户数
	private Integer agstanum= 0;//代理员工数
	
	private Integer taxcunum=0;//财税客户数
	private Integer taxtanum=0;//财税员工数
	
	private Integer secusnumall= 0;//派遣客户总数
	private Integer sestanumall= 0;//派遣员工总数
	private Integer agcusnumall= 0;//代理客户总数
	private Integer agstanumall= 0;//代理员工总数
	
	private Integer taxcunumall=0;//财税客户总数
	private Integer taxtanumall=0;//财税员工总数
	
	private Integer enstrcunum=0;//委托外地客户总数
	private Integer enstrstnum=0;//委托外地员工总数
	
	private Integer forexcunum=0;//外籍人汇总客户总数
	private Integer forexstnum=0;//外籍人汇总员工总数
	
	private Integer logid=0;
	
	
	public PropersonTaxationController() {
		clientList=bll.getClientList();
		try {
			list=bll.findBigPropersonTaxationList(3);//findPropersonTaxationList(3);
			total();
			listAll=bll.findPropersonTaxationList(3);
			totalAll();
			listFinance=bll.findPropersonTaxationFinancialList(21);
			taxtotal();
			
			listEnst=bll.getBigEntrustList(3);
			totalEnstAll();
			
			listForex=bll.getForexList(3);
			forextotal();
		} catch (SQLException e) {
		}
	}
	
	@Command("search")
	@NotifyChange({ "secusnum", "sestanum","taxcunum","taxtanum","enstrcunum","enstrstnum","forexcunum","forexstnum" })
	public void search(){
		list.clear();
		listFinance.clear();
		listEnst.clear();
		listForex.clear();
		try {
			logid=logbll.getUserIDByname(clientName);
			list.addAll(bll.findBigPropersonTaxationList(logid));
			total();
			listFinance.addAll(bll.findPropersonTaxationFinancialList(logid));
			taxtotal();
			listEnst.addAll(bll.getBigEntrustList(logid));
			totalEnstAll();
			listForex.addAll(bll.getForexList(logid));
			forextotal();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//按查询条件总计人事all
	public void totalAll(){
		Integer secall= 0;
		Integer sesall= 0;
		Integer agcall= 0;
		Integer agsall= 0;
		for(PropersonTaxationModel pt :listAll){
			if(pt.getCpct_type().trim().equals("派遣")){
				secall+= pt.getN();
				sesall+=pt.getM();
			}else if(pt.getCpct_type().trim().equals("代理")){
				agcall+= pt.getN();
				agsall+=pt.getM();
			}
		}
		
		setSecusnumall(secall);
		setSestanumall(sesall);
		setAgcusnumall(agcall);
		setAgstanumall(agsall);
	}
	//按查询条件总计人事
	public void total(){
		Integer sec= 0;
		Integer ses= 0;
		//Integer agc= 0;
		//Integer ags= 0;
		//Integer secall= 0;
		//Integer sesall= 0;
		//Integer agcall= 0;
		//Integer agsall= 0;
		for(PropersonTaxationModel pt :list){
			//if(pt.getCpct_type().trim().equals("派遣")){
				sec+= pt.getN();
				ses+=pt.getM();
				//secall+= pt.getN();
				//sesall+=pt.getM();
			//}else if(pt.getCpct_type().trim().equals("代理")){
				//agc+= pt.getN();
				//ags+=pt.getM();
				//agcall+= pt.getN();
				//agsall+=pt.getM();
			//}
		}
		setSecusnum(sec);
		setSestanum(ses);
		//setAgcusnum(agc);
		//setAgstanum(ags);
		
		//setSecusnumall(secall);
		//setSestanumall(sesall);
		//setAgcusnumall(agcall);
		//setAgstanumall(agsall);
	}
	//总计委托外地
	public void totalEnstAll(){
		Integer secnst= 0;
		Integer sesnst= 0;
		for(PropersonTaxationModel pt :listEnst){
			secnst+= pt.getN();
			sesnst+=pt.getM();
		}
		setEnstrcunum(secnst);
		setEnstrstnum(sesnst);
	}
	//按查询条件总计财税
	public void taxtotal(){
		Integer taxc=0;
		Integer taxs=0;
		Integer taxcall=0;
		Integer taxsall=0;
		for(PropersonTaxationModel pt :listFinance){
			if(pt.getCpct_type().trim().equals("财税")){
				taxc+= pt.getN();
				taxs+=pt.getM();
				taxcall+= pt.getN();
				taxsall+=pt.getM();
			}
		}
		setTaxcunum(taxc);
		setTaxtanum(taxs);
		
		setTaxcunumall(taxcall);
		setTaxtanumall(taxsall);
	}
	
	//按查询条件外籍人汇总
	public void forextotal(){
		Integer forexc=0;
		Integer forexs=0;
		for(PropersonTaxationModel pt :listForex){
			forexc+= pt.getN();
			forexs+=pt.getM();
		}
		setForexcunum(forexc);
		setForexstnum(forexs);
	}
	
	public List<String> getClientList() {
		return clientList;
	}
	public void setClientList(List<String> clientList) {
		this.clientList = clientList;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}


	public List<PropersonTaxationModel> getList() {
		return list;
	}


	public void setList(List<PropersonTaxationModel> list) {
		this.list = list;
	}

	public List<PropersonTaxationModel> getListFinance() {
		return listFinance;
	}

	public void setListFinance(List<PropersonTaxationModel> listFinance) {
		this.listFinance = listFinance;
	}

	public Integer getSecusnum() {
		return secusnum;
	}

	public void setSecusnum(Integer secusnum) {
		this.secusnum = secusnum;
	}

	public Integer getSestanum() {
		return sestanum;
	}

	public void setSestanum(Integer sestanum) {
		this.sestanum = sestanum;
	}

	public Integer getAgcusnum() {
		return agcusnum;
	}

	public void setAgcusnum(Integer agcusnum) {
		this.agcusnum = agcusnum;
	}

	public Integer getAgstanum() {
		return agstanum;
	}

	public void setAgstanum(Integer agstanum) {
		this.agstanum = agstanum;
	}

	public Integer getTaxcunum() {
		return taxcunum;
	}

	public void setTaxcunum(Integer taxcunum) {
		this.taxcunum = taxcunum;
	}

	public Integer getTaxtanum() {
		return taxtanum;
	}

	public void setTaxtanum(Integer taxtanum) {
		this.taxtanum = taxtanum;
	}

	public Integer getSecusnumall() {
		return secusnumall;
	}

	public void setSecusnumall(Integer secusnumall) {
		this.secusnumall = secusnumall;
	}

	public Integer getSestanumall() {
		return sestanumall;
	}

	public void setSestanumall(Integer sestanumall) {
		this.sestanumall = sestanumall;
	}

	public Integer getAgcusnumall() {
		return agcusnumall;
	}

	public void setAgcusnumall(Integer agcusnumall) {
		this.agcusnumall = agcusnumall;
	}

	public Integer getAgstanumall() {
		return agstanumall;
	}

	public void setAgstanumall(Integer agstanumall) {
		this.agstanumall = agstanumall;
	}

	public Integer getTaxcunumall() {
		return taxcunumall;
	}

	public void setTaxcunumall(Integer taxcunumall) {
		this.taxcunumall = taxcunumall;
	}

	public Integer getTaxtanumall() {
		return taxtanumall;
	}

	public void setTaxtanumall(Integer taxtanumall) {
		this.taxtanumall = taxtanumall;
	}

	public List<PropersonTaxationModel> getListAll() {
		return listAll;
	}

	public void setListAll(List<PropersonTaxationModel> listAll) {
		this.listAll = listAll;
	}

	public List<PropersonTaxationModel> getListEnst() {
		return listEnst;
	}

	public void setListEnst(List<PropersonTaxationModel> listEnst) {
		this.listEnst = listEnst;
	}

	public Integer getEnstrcunum() {
		return enstrcunum;
	}

	public void setEnstrcunum(Integer enstrcunum) {
		this.enstrcunum = enstrcunum;
	}

	public Integer getEnstrstnum() {
		return enstrstnum;
	}

	public void setEnstrstnum(Integer enstrstnum) {
		this.enstrstnum = enstrstnum;
	}

	public List<PropersonTaxationModel> getListForex() {
		return listForex;
	}

	public void setListForex(List<PropersonTaxationModel> listForex) {
		this.listForex = listForex;
	}

	public Integer getForexcunum() {
		return forexcunum;
	}

	public void setForexcunum(Integer forexcunum) {
		this.forexcunum = forexcunum;
	}

	public Integer getForexstnum() {
		return forexstnum;
	}

	public void setForexstnum(Integer forexstnum) {
		this.forexstnum = forexstnum;
	}
	
	
	
}
