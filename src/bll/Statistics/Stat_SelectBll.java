package bll.Statistics;

import java.util.List;

import Model.StatisticsResultModel;
import dal.Statistics.Stat_SelectDal;

public class Stat_SelectBll {
	Stat_SelectDal dal = new Stat_SelectDal();

	// 获取统计数据(按log_id)
	public List<StatisticsResultModel> getStatResult(Integer log_id,Integer puzu_id, String str) {
		return dal.getStatResult(log_id,puzu_id, str);
	}
}
