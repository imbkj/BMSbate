package Controller.EmTax;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.PropersonTaxationModel;
import bll.LoginBll;
import bll.EmTax.PropersonTaxationBll;


public class PropersonTaxation_BigSelectController {
	private PropersonTaxationBll bll= new  PropersonTaxationBll();
	private LoginBll logbll =new LoginBll();
	private String coba_client="";
	private Integer logid=0;
    
	private Integer secusnum= 0;//派遣客户数
	private Integer sestanum= 0;//派遣员工数
	private Integer agcusnum= 0;//代理客户数
	private Integer agstanum= 0;//代理员工数
	
	private List<PropersonTaxationModel> list = new ArrayList<PropersonTaxationModel>();
	
	public PropersonTaxation_BigSelectController() {
		coba_client = Executions.getCurrent().getArg().get("coba_client").toString();
		System.out.println(coba_client);
		logid=logbll.getUserIDByname(coba_client);
		try {
			list=bll.findPropersonTaxationList(logid);
			total();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	//按查询条件总计人事
	public void total(){
			Integer sec= 0;
			Integer ses= 0;
			Integer agc= 0;
			Integer ags= 0;
			for(PropersonTaxationModel pt :list){
				if(pt.getCpct_type().trim().equals("派遣")){
					sec+= pt.getN();
					ses+=pt.getM();
				 }else if(pt.getCpct_type().trim().equals("代理")){
					agc+= pt.getN();
					ags+=pt.getM();
				}
			}
			setSecusnum(sec);
			setSestanum(ses);
			setAgcusnum(agc);
			setAgstanum(ags);
	}
	
	public List<PropersonTaxationModel> getList() {
		return list;
	}

	public void setList(List<PropersonTaxationModel> list) {
		this.list = list;
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
	
	
}
