package bll.EmHouse;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.EmHouse.EmHouseChangeDal;

import Model.EmHouseChangeModel;

public class EmHouse_QueryListBll {
	// 客服查询列表
	public List<EmHouseChangeModel> getList(EmHouseChangeModel em) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		EmHouseChangeDal dal = new EmHouseChangeDal();

		String ownmonth = em.getOwnmonth() != null ? em.getOwnmonth()
				.toString() : null;
		String single = em.getEmhc_single() != null ? em.getEmhc_single()
				.toString() : null;
		list = dal.getListByParams(ownmonth, em.getEmhc_company(),
				em.getEmhc_name(), em.getEmhc_change(), em.getDep_name(),
				em.getEmhc_client(), em.getEmhc_ifdeclare2(), single);

		return list;
	}

	public List<EmHouseChangeModel> getInfoList(Integer id) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		EmHouseChangeDal dal = new EmHouseChangeDal();
		try {
			list = dal.getInfoById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询特殊信息
	public List<EmHouseChangeModel> getList(Integer tid) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		EmHouseChangeDal dal = new EmHouseChangeDal();

		list = dal.getInfoByTid(tid);

		return list;
	}

}
