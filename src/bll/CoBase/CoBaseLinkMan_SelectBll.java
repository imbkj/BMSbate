/**
 * @Classname CoBaseLinkMan_SelectBll
 * @ClassInfo 公司联系人数据库显示类
 * @author 林少斌
 * @Date 2013-11-29
 *  */
package bll.CoBase;

import java.util.ArrayList;

import Model.CoAgencyLinkmanModel;
import Model.CoBaseLinkFamilyModel;
import dal.CoBase.CoBaseLinkMan_SelectDal;

;

public class CoBaseLinkMan_SelectBll {
	private CoBaseLinkMan_SelectDal dal = new CoBaseLinkMan_SelectDal();

	// 根据cali_id获取联系人基本信息
	public CoAgencyLinkmanModel getLinkModel(int cali_id) {
		return dal.getLinkModel(cali_id);
	}

	// 根据cali_id获取联系人家属信息
	public ArrayList<CoBaseLinkFamilyModel> getLinkFamilyModel(int cali_id) {
		return dal.getLinkFamilyModel(cali_id);
	}

	// 根据cid获取联系人基本信息
	public ArrayList<CoAgencyLinkmanModel> getLinkmanByCid(int cid) {
		return dal.getLinkmanByCid(cid);
	}

	// 根据cid获取联系人基本信息
	public ArrayList<CoAgencyLinkmanModel> getLinkmanByCid(int cid, int state) {
		return dal.getLinkmanByCid(cid, state);
	}
}
