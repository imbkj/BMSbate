package bll.EmBenefit;

import Model.EmActyProduceModel;
import Model.EmActyProducetypeModel;
import Model.EmActyWarehouseModel;
import dal.EmBenefit.EmActy_NewOperateDal;

public class EmActy_NewOperateBll {
	private EmActy_NewOperateDal dal = new EmActy_NewOperateDal();

	// 新增或者修改库存信息
	public Integer EmActyWarehouse(EmActyWarehouseModel model) {
		return dal.EmActyWarehouse(model);
	}

	// 把福利信息更新为已采购
	public Integer buyEmWelfareInfo(String sortid) {
		return dal.buyEmWelfareInfo(sortid);
	}

	// 添加产品信息
	public int AddProduce(EmActyProduceModel m) {
		return dal.AddProduce(m);
	}

	// 添加产品分项信息
	public int AddProduceType(EmActyProducetypeModel m) {
		return dal.AddProduceType(m);
	}

}
