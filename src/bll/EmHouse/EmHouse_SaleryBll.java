package bll.EmHouse;

import impl.WorkflowCore.WfOperateImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import dal.CoHousingFund.CoHousingFund_ListDal;
import dal.EmHouse.EmHouseSetupDal;
import dal.EmHouse.EmHouseUpdateDal;
import dal.Embase.Embasedal;
import Model.EmHouseCpp;
import Model.EmHouseSetupModel;
import Model.EmHouseUpdateModel;
import Model.EmbaseModel;
import Model.SocialInsuranceClassInfoViewModel;
import Util.SocialInsuranceCalculator;
import Util.UserInfo;

public class EmHouse_SaleryBll {
	
	//读取年调限制
	public boolean Salery(){
		boolean b= false;
		EmHouseSetupDal dal = new EmHouseSetupDal();
		List<EmHouseSetupModel> list = dal.getList();
		if (list.size()>0) {
			if (list.get(0).getSalay().equals(1)) {
				b=true;
			}
		}
		return b;
	}
	
	//读取在册数据
	public EmHouseUpdateModel getinfo(Integer embaId){
		EmHouseUpdateModel em = new EmHouseUpdateModel();
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();
		list = dal.getEmBaseById(embaId);
		if (list.size()>0) {
			EmHouseUpdateDal dal2 = new EmHouseUpdateDal();
			List<EmHouseUpdateModel> list2 = dal2.getListByGid(list.get(0).getGid());
			if (list2.size()>0) {
				em=list2.get(0);
			}
		}

		return em;
	}
	
	//读取公积金比例
	public List<EmHouseCpp> getCppList(){
		List<EmHouseCpp> list =new ListModelList<>();
		CoHousingFund_ListDal dal = new CoHousingFund_ListDal();
		list=dal.getcpplist();
		return list;
	}
	
	
	//更新年调数据
	public Integer update(EmHouseUpdateModel em){
		Integer i=0;
		
		Date d = new Date();
		//计算上下限
		SocialInsuranceCalculator sm = new SocialInsuranceCalculator();
		Integer id = sm.getSionId("深户员工", "深圳", "深圳中智经济技术合作有限公司");
		
		List<SocialInsuranceClassInfoViewModel> list = sm.getGjjItemFee(id,
				new BigDecimal(em.getEmhu_radix()),
				new BigDecimal(em.getEmhu_cpp()), d);
		BigDecimal fee = list.get(0).getFee();
		BigDecimal radix = list.get(0).getRadix();

		radix = radix.setScale(2, BigDecimal.ROUND_HALF_UP);
		em.setEmhu_radix(radix.doubleValue());
		
		WfBusinessService wfbs = new EmHouse_EditImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = {"年度调基",em};
		String[] str=wfs.AddTaskToNext(obj, "员工公积金年调", em.getOwnmonth()
				+ em.getEmhu_name() + "(" + em.getGid() + ")公积金年度调基", 122, UserInfo.getUsername(), "", em.getCid(), "");
		i = Integer.valueOf(str[3]);
		return i;
	}
}
