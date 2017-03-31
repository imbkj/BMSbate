package bll.Batchprocessing;

import Model.EmHouseChangeModel;
import dal.Batchprocessing.EmHouse_BathOperateDal;

public class EmHouse_BathOperateBll {
	private EmHouse_BathOperateDal dal = new EmHouse_BathOperateDal();

	// 公积金新增
	public int EmHouseAdd(EmHouseChangeModel m) {
		return dal.EmHouseAdd(m);
	}

	// 公积金数据导进临时表
	public int EmHouseUpload(EmHouseChangeModel m) {
		return dal.EmHouseUpload(m);
	}

	public Integer delEmHouseUpload(Integer hsup_id) {
		return dal.delEmHouseUpload(hsup_id);
	}

	public Integer UpdateEmHouseUpload(Integer hsup_id) {
		return dal.UpdateEmHouseUpload(hsup_id);
	}

	// 公积金调入
	public int EmHouseMoveIn(EmHouseChangeModel m) {
		return dal.EmHouseMoveIn(m);
	}

	// 公积金接管
	public int EmHouseTakeOver(EmHouseChangeModel m) {
		return dal.EmHouseTakeOver(m);
	}
}
