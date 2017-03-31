package bll.Embase;

import java.util.List;
import org.zkoss.zul.ListModelList;

import dal.CoBase.CoBase_SelectDal;
import dal.CoQuotation.CoofferDal;
import dal.Embase.CoglistDal;
import dal.Embase.Embasedal;
import Model.CoBaseModel;
import Model.CoOfferModel;
import Model.CoglistModel;
import Model.EmbaseModel;

public class EmQutationBll {

	public List<EmbaseModel> embaseList(Integer id) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();

		list = dal.getEmBaseById(id);

		return list;
	}

	public List<CoOfferModel> coofferInfo(Integer gid) {
		List<CoOfferModel> list = new ListModelList<>();
		CoofferDal dal = new CoofferDal();
		list = dal.empCooffer(gid);
		return list;
	}

	public List<EmbaseModel> embaseListInfo(Integer cid, String name) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();
		EmbaseModel em = new EmbaseModel();
		em.setCid(cid);
		em.setEmba_name(name);
		list = dal.getEmbaseInfoByPama(em);
		return list;

	}

	public List<CoBaseModel> cobaseListInfo(String name) {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		list = dal.getInfoListByName("%" + name + "%");
		return list;
	}

	public List<CoglistModel> coListInfo(Integer cid, Integer gid, String name) {
		List<CoglistModel> list = new ListModelList<>();
		CoglistDal dal = new CoglistDal();
		list = dal.getcoglist(cid, gid, name);
		return list;
	}

	public Integer addCoglist(CoglistModel cm) {
		Integer i = 0;
		CoglistDal dal = new CoglistDal();
		i = dal.add(cm);
		return i;
	}

	public Integer modCoglist(CoglistModel cm) {
		Integer i = 0;
		CoglistDal dal = new CoglistDal();
		i = dal.mod(cm);
		return i;
	}

	public Integer DelCoglist(CoglistModel cm) {
		Integer i = 0;
		CoglistDal dal = new CoglistDal();
		i = dal.del(cm.getCgli_id());
		return i;
	}

}
