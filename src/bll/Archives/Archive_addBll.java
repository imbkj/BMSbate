package bll.Archives;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.Archives.EmArchiveDatumDal;
import dal.CoBase.CoBase_SelectDal;
import dal.Embase.Embasedal;

import Model.CoBaseModel;
import Model.EmArchiveDatumModel;
import Model.EmbaseModel;

public class Archive_addBll {
	// 查询公司列表
	public List<CoBaseModel> getCobaseList(String client, String name) {
		List<CoBaseModel> list = new ListModelList<CoBaseModel>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		try {
			list = dal.getCobaseByClient(client, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询客服列表
	public List<CoBaseModel> getClientList() {
		List<CoBaseModel> list = new ListModelList<CoBaseModel>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		try {
			list = dal.getClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工姓名列表
	public List<EmbaseModel> getEmbaseName(String client, String cid,
			String name) {
		List<EmbaseModel> list = new ListModelList<EmbaseModel>();
		Embasedal dal = new Embasedal();
		try {
			list = dal.getEmbaseByName(client, cid, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 员工信息列表
	public List<EmbaseModel> getEmbaselist(String client, String cid, String gid) {
		List<EmbaseModel> list = new ListModelList<EmbaseModel>();
		Embasedal dal = new Embasedal();
		try {
			list = dal.getEmbaseByCid(client, cid, gid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//查询业务是否完成
	public Integer checkDatum(Integer gid, Integer type) {
		Integer i = 0;
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		try {
			i = dal.checkData(gid, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

}
