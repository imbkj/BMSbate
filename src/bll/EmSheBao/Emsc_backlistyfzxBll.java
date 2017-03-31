package bll.EmSheBao;

import java.util.List;

import Model.EmSheBaoChangeModel;
import dal.EmSheBao.Emsc_backlistyfzxDal;
public class Emsc_backlistyfzxBll {

	public Emsc_backlistyfzxDal dal = new Emsc_backlistyfzxDal();
	/**
	 * 获取退回信息
	 */
	public List<EmSheBaoChangeModel> getEmscData(String where) {
	
		return dal.getEmscData(where);
	}
	
	
}
