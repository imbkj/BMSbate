package bll.EmBodyCheck;

import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import dal.EmBodyCheck.EmBcSetup_SelectDal;

public class Embc_SetupSellectBll {

	// 查询体检机构信息
	public List<EmBcSetupModel> getEmBcSetupInfo(String str) {
		EmBcSetup_SelectDal dal = new EmBcSetup_SelectDal();
		return dal.getEmBcSetupInfo(str);
	}

	// 查询体检机构
	public List<EmBcSetupModel> getEmBcSetupList(EmBcSetupModel em) {
		List<EmBcSetupModel> list = new ListModelList<>();
		EmBcSetup_SelectDal dal = new EmBcSetup_SelectDal();
		list = dal.getSetUpList(em);
		return list;
	}

	// 根据机构id查询机构的地址
	public List<EmBcSetupAddressModel> getEmBcSetupAddressInfo(int id) {
		List<EmBcSetupAddressModel> list = new ListModelList<>();
		EmBcSetup_SelectDal dal = new EmBcSetup_SelectDal();
		list = dal.getEmBcSetupAddressInfo(id);
		
		for (EmBcSetupAddressModel m : list) {
			if (m.getEbsa_ystate() != null && m.getEbsa_ystate() > 0) {
				m.setEbsa_ychecked(true);
			}
			if (m.getEbsa_istate() != null && m.getEbsa_istate() > 0) {
				m.setEbsa_ichecked(true);
			}
			if (m.getEbsa_w1() != null && m.getEbsa_w1() > 0) {
				m.setW1(true);
			}
			if (m.getEbsa_w2() != null && m.getEbsa_w2() > 0) {
				m.setW2(true);
			}
			if (m.getEbsa_w3() != null && m.getEbsa_w3() > 0) {
				m.setW3(true);
			}
			if (m.getEbsa_w4() != null && m.getEbsa_w4() > 0) {
				m.setW4(true);
			}
			if (m.getEbsa_w5() != null && m.getEbsa_w5() > 0) {
				m.setW5(true);
			}
			if (m.getEbsa_w6() != null && m.getEbsa_w6() > 0) {
				m.setW6(true);
			}
			if (m.getEbsa_w7() != null && m.getEbsa_w7() > 0) {
				m.setW7(true);
			}
		}
		return list;
	}

	// 根据机构id查询机构的地址
	public List<EmBcSetupAddressModel> getEmBcSetupAddress(String str) {
		EmBcSetup_SelectDal dal = new EmBcSetup_SelectDal();
		return dal.getEmBcSetupAddress(str);
	}

	// 获取机构名称
	public List<EmBcSetupModel> getEmBcSetupname(String str) {
		EmBcSetup_SelectDal dal = new EmBcSetup_SelectDal();
		return dal.getEmBcSetupname(str);
	}

}
