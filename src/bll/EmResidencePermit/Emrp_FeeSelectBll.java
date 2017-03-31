package bll.EmResidencePermit;

import java.util.List;

import Model.EmCAFFeeInfoModel;
import Model.EmResidencePermitInfoModel;
import dal.EmResidencePermit.Emrp_FeeSelectDal;

public class Emrp_FeeSelectBll {
	private Emrp_FeeSelectDal dal=new Emrp_FeeSelectDal();
	public List<EmResidencePermitInfoModel> getFeeList(String str) {
		return dal.getFeeList(str);
	}
	
	//查询客服
	public List<String> getClient()
	{
		return dal.getClient();
	}
	
	//查询
	public List<EmResidencePermitInfoModel> getFeeInfoList(EmResidencePermitInfoModel model) {
		String str="";
		if(model.getOwnmonth()!=null&&!model.getOwnmonth().equals("")){
			str=str+" and a.ownmonth="+model.getOwnmonth();
		}
		if(model.getCoba_shortname()!=null&&!model.getCoba_shortname().equals(""))
		{
			str=str+" and coba_shortname='"+model.getCoba_shortname()+"'";
		}
		if(model.getEmba_name()!=null&&!model.getEmba_name().equals(""))
		{
			str=str+" and emba_name='"+model.getEmba_name()+"'";
		}
		if(model.getCoba_client()!=null&&!model.getCoba_client().equals(""))
		{
			str=str+" and coba_client='"+model.getCoba_client()+"'";
		}
		if(model.getErpi_payment_kind()!=null&&!model.getErpi_payment_kind().equals(""))
		{
			str=str+" and erpi_payment_kind='"+model.getErpi_payment_kind()+"'";
		}
		if(model.getErpi_payment_state()!=null&&!model.getErpi_payment_state().equals(""))
		{
			str=str+" and erpi_payment_state='"+model.getErpi_payment_state()+"'";
		}
		if(model.getErpi_cl_number()!=null&&!model.getErpi_cl_number().equals(""))
		{
			str=str+" and erpi_cl_number='"+model.getErpi_cl_number()+"'";
		}
		if(model.getErpi_wd_applicant()!=null&&!model.getErpi_wd_applicant().equals(""))
		{
			str=str+" and erpi_wd_applicant='"+model.getErpi_wd_applicant()+"'";
		}
		if(model.getFeestate()!=null&&!model.getFeestate().equals(""))
		{
			str=str+" and feestate='"+model.getFeestate()+"'";
		}
		if(model.getErpi_wd_loan_date()!=null&&!model.getErpi_wd_loan_date().equals(""))
		{
			str=str+" and erpi_wd_loan_date='"+model.getErpi_wd_loan_date()+"'";
		}
		if(model.getErpi_ri_date()!=null&&!model.getErpi_ri_date().equals(""))
		{
			str=str+" and erpi_ri_date='"+model.getErpi_ri_date()+"'";
		}
		str=str+" and erpi_fee is not null and erpi_fee>0";
		return getFeeList(str);
	}
	
	
	//查询费用明细
	public List<EmCAFFeeInfoModel> getFeeInfoList(String str) {
		return dal.getFeeInfoList(str);
	}
	
	//获取福利经办人
	public String getWDPeople()
	{
		return dal.getWDPeople();
	}
}
