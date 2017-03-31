package bll.EmFinanceManage;

import java.math.BigDecimal;
import java.util.List;

import Model.SbGatherModel;
import dal.EmFinanceManage.SbGatherDal;

public class SbGatherBll {
	private SbGatherDal dal = new SbGatherDal();

	public List<SbGatherModel> getSbGatherList(String str, String ownmonth,
			String ifsingle) {
		List<SbGatherModel> list = dal.getSbGatherList(str, ownmonth, ifsingle);
		List<SbGatherModel> listoal = getSbGatherList(Integer
				.parseInt(ownmonth));
		for (SbGatherModel m : listoal) {
			for (SbGatherModel model : list) {
				if (m.getCid() == model.getCid()) {
					if (m.getCfsa_total() != null) {
						if (model.getCfsa_total() == null) {
							model.setCfsa_total(BigDecimal.ZERO);
						}
						model.setCfsa_total(model.getCfsa_total().add(
								m.getCfsa_total()));
						continue;
					}
				}
			}
		}
		return list;
	}

	public List<SbGatherModel> getSbGatherList(int ownmonth) {
		return dal.getCfsa_total(ownmonth);
	}

	// 获取中智户或者委托户总额
	public BigDecimal[] getSbTotalInfo(String ownmonth) {
		return dal.getSbTotalInfo(ownmonth);
	}

	// 获取公积金收款
	public List<SbGatherModel> getHhouseGatherList(String str, String ownmonth,
			String ifsingle) {
		return dal.getHhouseGatherList(str, ownmonth, ifsingle);
	}

	// 获取中智户或者委托户总额
	public BigDecimal[] getHsTotalInfo(String ownmonth) {
		return dal.getHsTotalInfo(ownmonth);
	}

	// 获取公积金补缴收款
	public List<SbGatherModel> getHhouseBjGatherList(String str,
			String ownmonth, String ifsingle) {
		return dal.getHhouseBjGatherList(str, ownmonth, ifsingle);
	}

	// 获取公积金补缴中智户或者委托户总额
	public BigDecimal[] getHsBjTotalInfo(String ownmonth) {
		return dal.getHsBjTotalInfo(ownmonth);
	}

}
