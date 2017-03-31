package bll.EmBodyCheck;

import java.util.List;

import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import dal.EmBodyCheck.EmBcSetup_OperateDal;
import dal.EmBodyCheck.EmBcSetup_SelectDal;

public class EmBcSetup_OperateBll {

	// 体检机构新增
	public Integer EmBcSetupAdd(EmBcSetupModel m) {
		EmBcSetup_OperateDal dal = new EmBcSetup_OperateDal();
		return dal.EmBcSetupAdd(m);
	}

	// 体检机构信息更新
	public Integer EmBcSetupUpdate(EmBcSetupModel m) {
		EmBcSetup_OperateDal dal = new EmBcSetup_OperateDal();
		return dal.EmBcSetupUpdate(m);
	}

	// 更新机构地址
	public int UpdateEmBcSetupAddress(EmBcSetupAddressModel m) {
		EmBcSetup_OperateDal dal = new EmBcSetup_OperateDal();
		return dal.UpdateEmBcSetupAddress(m);
	}

	// 新增体检机构地址
	public int AddEmBcSetupAddress(EmBcSetupAddressModel m) {
		EmBcSetup_OperateDal dal = new EmBcSetup_OperateDal();
		return dal.AddEmBcSetupAddress(m);
	}

	// 检查体检机构是否存在
	public boolean checkEmbcSetUp(String name, Integer id) {
		boolean b = false;
		EmBcSetup_SelectDal dal = new EmBcSetup_SelectDal();
		EmBcSetupModel em = new EmBcSetupModel();
		em.setEbcs_hospital(name);
		if (id != null) {
			em.setCheckName(true);
			em.setEbcs_id(id);
		}
		List<EmBcSetupModel> list = dal.getSetUpList(em);
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	// 更新机构介绍文件
	public int UpdateEmBcSetupAddressFile(String ebsa_doc, Integer ebsa_id) {
		EmBcSetup_OperateDal dal = new EmBcSetup_OperateDal();
		return dal.UpdateEmBcSetupAddressFile(ebsa_doc, ebsa_id);
	}
}
