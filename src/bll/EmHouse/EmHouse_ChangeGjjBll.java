package bll.EmHouse;

import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.EmHouseChangeGJJModel;
import dal.EmHouse.EmHouseChangeGjjDal;
import dal.EmHouse.EmHouse_ChangeGjjDal;

public class EmHouse_ChangeGjjBll {

	// 查询公积金交单申报信息
	public List<EmHouseChangeGJJModel> getEmHouse_ChangeGjjInfo(String str) {
		EmHouse_ChangeGjjDal dal = new EmHouse_ChangeGjjDal();
		return dal.getEmHouse_ChangeGjjInfo(str);
	}

	// 查询公积金交单申报所属月列表
	public List<EmHouseChangeGJJModel> getOwnmonthList() {
		EmHouse_ChangeGjjDal dal = new EmHouse_ChangeGjjDal();

		return dal.ownmonthList();
	}

	// 查询交单变更关联数据
	public List<EmHouseChangeGJJModel> getGjjReList(Integer id) {
		List<EmHouseChangeGJJModel> list = new ListModelList<>();
		EmHouse_ChangeGjjDal dal = new EmHouse_ChangeGjjDal();
		EmHouseChangeGJJModel em = new EmHouseChangeGJJModel();
		em.setEhcg_tid(id);
		list = dal.searchList(em);
		return list;
	}

	public List<EmHouseChangeGJJModel> getEmHouse_ChangeGjj(Integer id,String company,
			String name, Integer ownmonth, Integer ifdeclare,Integer state) {
		EmHouse_ChangeGjjDal dal = new EmHouse_ChangeGjjDal();
		EmHouseChangeGJJModel em = new EmHouseChangeGJJModel();
		em.setEhcg_id(id);
		em.setEhcg_company(company);
		em.setEhcg_name(name);
		em.setOwnmonth(ownmonth);
		em.setEhcg_ifdeclare(ifdeclare);
		em.setState(state);
		return dal.getEmHouse_ChangeGjj(em);
	}
	
	public List<EmHouseChangeGJJModel> getEmHouse_ChangeGjj(String idList){
		EmHouse_ChangeGjjDal dal = new EmHouse_ChangeGjjDal();
		EmHouseChangeGJJModel em = new EmHouseChangeGJJModel();
		
		return dal.getEmHouse_ChangeGjj(em);
	}

	// 更新公积金交单申报
	public int UpdateEmHouse_ChangeGjjInfo(String id, String declareid) {
		EmHouse_ChangeGjjDal dal = new EmHouse_ChangeGjjDal();
		return dal.UpdateEmHouse_ChangeGjjInfo(id, declareid);
	}
	
	//修改公积金交单信息
	public Integer modInfo(EmHouseChangeGJJModel em,Integer id){
		EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();
		Integer i = dal.Mod(em, em.getEhcg_id());
		return i;
	}
}
