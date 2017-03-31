/**
 * @Classname BaseInfo_AddBll
 * @ClassInfo 委托机构新增基本信息业务逻辑类
 * @author 李文洁
 * @Date 2014-10-9
 */
package bll.CoAgency;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Model.CoAgencyBaseCityRelModel;
import Model.CoAgencyBaseModel;
import Model.CoAgencyBaseServiceModel;
import Model.CoAgencyLinkmanModel;
import Util.pinyin4jUtil;
import dal.CoAgency.CoAgencyBaseOperateDal;

public class BaseInfo_OperateBll {
	private CoAgencyBaseOperateDal opDal = new CoAgencyBaseOperateDal();

	// 新增委托机构基本信息，传入参数，返回message数组。
	public String[] AddBase(CoAgencyBaseModel cabaModel,
			CoAgencyBaseServiceModel cabsModel,
			Map<String, CoAgencyBaseCityRelModel> cityMap,
			List<CoAgencyLinkmanModel> linkList, String username) {

		String[] message = new String[2];
		try {
			cabaModel.setCoab_namespell(pinyin4jUtil
					.getPinYinHeadChar(cabaModel.getCoab_name()));
			cabaModel.setType(1);
			cabaModel.setCoab_addname(username);
			// 录入机构基本信息
			int coab_id = opDal.AddBase(cabaModel);
			if (coab_id > 0) {
				Set<Map.Entry<String, CoAgencyBaseCityRelModel>> set = cityMap
						.entrySet();
				for (Iterator<Map.Entry<String, CoAgencyBaseCityRelModel>> it = set
						.iterator(); it.hasNext();) {
					Map.Entry<String, CoAgencyBaseCityRelModel> entry = (Map.Entry<String, CoAgencyBaseCityRelModel>) it
							.next();
					// 录入服务城市
					int cabc_id = DisBase(coab_id, entry.getKey(),
							entry.getValue(), username);
					if (cabc_id > 0) {
						// 录入机构服务约定
						AddBaseService(coab_id, cabc_id, cabsModel, username, 1);
						// 录入联系人
						AddLinkman(coab_id, cabc_id, linkList, username, 1);
						// 返回信息
						message[0] = "1";
						message[1] = "委托机构,新增成功。";
					} else {
						message[0] = "0";
						message[1] = "委托机构,录入服务城市时失败。";
					}
				}
			} else {
				message[0] = "0";
				message[1] = "委托机构,新增失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "委托机构,新增出错。";
		}
		return message;
	}

	// 新增受托机构基本信息，传入参数，返回message数组。
	public String[] AddStBase(CoAgencyBaseModel cabaModel,
			CoAgencyBaseServiceModel cabsModel,
			List<CoAgencyLinkmanModel> linkList, String username) {

		String[] message = new String[2];
		try {
			if (!opDal.existBaseService(cabaModel.getCoab_name())) {
				cabaModel.setCoab_namespell(pinyin4jUtil
						.getPinYinHeadChar(cabaModel.getCoab_name()));
				cabaModel.setType(2);
				cabaModel.setCoab_addname(username);
				// 录入机构基本信息
				int coab_id = opDal.AddBase(cabaModel);
				if (coab_id > 0) {
					// 录入机构服务约定
					AddBaseService(coab_id, 0, cabsModel, username, 2);
					// 录入联系人
					AddLinkman(coab_id, 0, linkList, username, 2);
					// 返回信息
					message[0] = "1";
					message[1] = "受托机构,新增成功。";
				} else {
					message[0] = "0";
					message[1] = "受托机构,新增失败。";
				}
			} else {
				message[0] = "0";
				message[1] = "系统已存在该受托机构,无法新增。";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "受托机构,新增出错。";
		}
		return message;
	}

	// 新增机构服务约定
	public void AddBaseService(int coab_id, int cabc_id,
			CoAgencyBaseServiceModel m, String username, int type) {
		m.setCoas_coab_id(coab_id);
		m.setCoas_cabc_id(cabc_id);
		m.setCoas_addname(username);
		m.setCoas_type(type);
		opDal.AddBaseService(m);
	}

	// 机构联系人批量新增
	public void AddLinkman(int coab_id, int cabc_id,
			List<CoAgencyLinkmanModel> linkList, String username, int type) {
		for (CoAgencyLinkmanModel m : linkList) {
			try {
				m.setCali_addname(username);
				m.setCabc_id(cabc_id);
				opDal.AddLinkman(coab_id, m, type);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 修改机构基本信息及服务约定(委托机构)
	public void UpBaseInfo(CoAgencyBaseModel cabaModel,
			CoAgencyBaseServiceModel cabsModel,
			CoAgencyBaseCityRelModel cabcModel, String username) {
		try {
			opDal.UpBase(cabaModel);
			opDal.UpBaseService(cabsModel);
			int cabc_ifdefault = cabcModel.isIfdefault() ? 1 : 0;
			opDal.DisBase(cabcModel.getCoab_id(), cabcModel.getCity(),
					cabc_ifdefault, cabcModel.getCabc_fee(), username);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 修改机构基本信息及服务约定(受托机构)
	public void UpBaseInfo(CoAgencyBaseModel cabaModel,
			CoAgencyBaseServiceModel cabsModel, String username) {
		try {
			opDal.UpBase(cabaModel);
			opDal.UpBaseService(cabsModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 分配城市与机构关系
	public int DisBase(int coab_id, String city, CoAgencyBaseCityRelModel m,
			String addname) {
		int cabc_ifdefault = m.isIfdefault() ? 1 : 0;
		return opDal.DisBase(coab_id, city, cabc_ifdefault, m.getCabc_fee(),
				addname);
	}

	// 取消城市与机构的分配关系
	public int DelDisBasefromCity(int coab_id, String city, String addname) {
		return opDal.DelDisBasefromCity(coab_id, city, addname);
	}

	// 设置城市默认机构
	public int SetDefaultAgency(int cabc_id) {
		return opDal.SetDefaultAgency(cabc_id);
	}
}
