package bll.CoFinanceManage;

import java.math.BigDecimal;
import java.util.List;

import dal.CoFinanceManage.Cfma_CollectCarryDal;
import Model.EmbaseModel;

/**
 * 
 * @author suhongyuan
 *
 */
public class Cfma_CollectCarryBll {
	//根据当前公司的id获取公司的员工信息
	public List<EmbaseModel> EmbaseList(String str){
		 Cfma_CollectCarryDal dal= new Cfma_CollectCarryDal();
		 return dal.getEmbaseList(str);
	}
	//结转业务操作
	public int addCfma_CollectCarry(String company_id ,String cfsa_cpac_name,String cfmb_number,String coba_client,String owmno,
			   String ownmonth,String remak,BigDecimal recexpense,String gid,String caoname){
		 Cfma_CollectCarryDal dal= new Cfma_CollectCarryDal();
		 return dal.addCfma_CollectCarry(company_id, cfsa_cpac_name, cfmb_number, coba_client, owmno, ownmonth, remak, recexpense, gid, caoname);
	}
}
