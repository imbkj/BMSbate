package bll.CoCompact;

import java.util.List;

import dal.CoCompact.CocompactDal;

import Model.CoCompactModel;

public class cocompact_indateBll {

	public List<CoCompactModel> getlist(Integer type,String client,String company,String userid) {
		CocompactDal dal = new CocompactDal();
		return dal.getCompactIndate(type,client,company,userid);
	}
	
	public List<CoCompactModel> getclientlist(Integer type,String userid) {
		CocompactDal dal = new CocompactDal();
		return dal.getclientlist(type,userid);
	}
}
