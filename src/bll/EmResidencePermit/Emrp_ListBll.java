package bll.EmResidencePermit;

import java.util.List;

import dal.EmResidencePermit.Emrp_ListDal;

import Model.CoOnlineRegisterInfoModel;
import Model.EmRegistrationInfoModel;
import Model.EmResidencePermitChangeModel;
import Model.EmResidencePermitInfoModel;

public class Emrp_ListBll {

	// 获取员工列表
	public List<CoOnlineRegisterInfoModel> getEmbaseList() {
		return new Emrp_ListDal().getEmbaseList();
	}

	// 获取申报列表
	public List<EmResidencePermitInfoModel> getEmrpList(String str) {
		return new Emrp_ListDal().getEmrpList(str);
	}

	// 获取状态列表
	public List<EmResidencePermitInfoModel> getStateList(String str) {
		return new Emrp_ListDal().getStateList(str);
	}

	// 获取状态详细列表
	public List<EmResidencePermitInfoModel> getStateInfoList(String str) {
		return new Emrp_ListDal().getStateInfoList(str);
	}

	// 根据主键id获取居住证详情
	public EmResidencePermitInfoModel getEmrpInfo(Integer erpi_id) {
		return new Emrp_ListDal().getEmrpInfo(erpi_id);
	}

	public EmResidencePermitInfoModel getEmrpInfoModel(Integer erpi_id) {
		return new Emrp_ListDal().getEmrpInfoModel(erpi_id);
	}

	// 获取状态变更记录列表
	public List<EmResidencePermitInfoModel> getStateRelList(String str,
			Integer erpi_id) {
		return new Emrp_ListDal().getStateRelList(str, erpi_id);
	}

	// 获取居住证转换列表
	public List<EmResidencePermitChangeModel> getErpcList(String str) {
		return new Emrp_ListDal().getErpcList(str);
	}

	public EmResidencePermitInfoModel getEmResidencePermitInfo(Integer gid) {
		Emrp_ListDal dal = new Emrp_ListDal();
		return dal.getEmResidencePermitInfo(gid);
	}

	// 查询员工是否有为办理完成的居住证新办信息
	public EmResidencePermitInfoModel getResidencePermit(Integer gid,String str)
	{
		Emrp_ListDal dal = new Emrp_ListDal();
		return dal.getEmResidencePermit(gid,str);
	}
	
	// 根据gid查询员工就业登记信息
	public EmRegistrationInfoModel getEmRegistrationInfo(Integer gid) {
		Emrp_ListDal dal = new Emrp_ListDal();
		return dal.getEmRegistrationInfo(gid);
	}
	
	// 根据cid查询公司就业登记信息
	public CoOnlineRegisterInfoModel getCoOnlineRegisterInfo(Integer cid) {
			Emrp_ListDal dal = new Emrp_ListDal();
			return dal.getCoOnlineRegisterInfo(cid);
		}
}
