package bll.CoCompact;

import java.util.List;

import org.apache.poi.ss.formula.IStabilityClassifier;
import org.zkoss.zul.ListModelList;

import dal.CoBase.CoBase_SelectDal;
import dal.CoCompact.CocompactDal;
import dal.CoQuotation.CoofferDal;
import dal.CoQuotation.CoofferlistDal;
import dal.Embase.CoglistDal;
import dal.Embase.Embasedal;

import Controller.EmSheBaocard.newExcelImpl;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Model.CoglistModel;
import Model.EmbaseModel;
import Util.UserInfo;

public class Compact_EmpAllotBll {

	public List<CoBaseModel> SearchCobase(Integer cid, String company,
			String client, boolean fuzzy) {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		CoBaseModel cm = new CoBaseModel();
		cm.setFuzzy(fuzzy);
		cm.setCid(cid);
		cm.setCoba_company(company);
		cm.setCoba_client(client);
		cm.setCoba_servicestate(1);
		list = dal.getCoBaseInfo(cm, "cid,coba_company", true, 30,null);
		return list;
	}

	public List<CoBaseModel> SearchClient(Integer cid, String company,
			String client, boolean fuzzy) {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		CoBaseModel cm = new CoBaseModel();
		cm.setFuzzy(fuzzy);
		cm.setCid(cid);
		cm.setCoba_company(company);
		cm.setCoba_client(client);
		cm.setCoba_servicestate(1);
		list = dal.getCoBaseInfo(cm, "coba_client", true,100, null);
		return list;
	}

	public List<EmbaseModel> SearchEmbase(Integer cid, String name) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();
		EmbaseModel em = new EmbaseModel();
		em.setCid(cid);
		em.setEmba_name(name);
		em.setEmba_state(1);
		list = dal.getEmbaseInfo(em);
		return list;
	}

	public List<CoCompactModel> SearchCompact(Integer id, Integer cid) {
		List<CoCompactModel> list = new ListModelList<>();
		CocompactDal dal = new CocompactDal();
		CoCompactModel cm = new CoCompactModel();
		cm.setCid2(cid);
		cm.setCoco_id(id);
		cm.setDataState(1);
		list = dal.getCompactList(cm);
		return list;
	}

	public List<CoOfferModel> SearchCooffer(Integer id) {
		List<CoOfferModel> list = new ListModelList<>();
		CoofferDal dal = new CoofferDal();
		list = dal.getCoofferByCoid(id);
		return list;
	}

	public List<CoOfferListModel> SearchItem(Integer cid, Integer cocoId,
			Integer coofId, String name) {
		List<CoOfferListModel> list = new ListModelList<>();
		CoofferlistDal dal = new CoofferlistDal();
		list = dal.getcoofferlist(cid, cocoId, coofId, name);
		return list;
	}

	public boolean add(List<EmbaseModel> list, List<CoOfferListModel> list2) {
		boolean b = true;
		CoglistDal dal = new CoglistDal();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isChecked()) {
				dal.delEmp(list.get(i).getGid());
				for (int j = 0; j < list2.size(); j++) {
					if (list2.get(j).isChecked()) {
						CoglistModel cm = new CoglistModel();
						cm.setGid(list.get(i).getGid());
						cm.setCid(list.get(i).getCid());
						cm.setCgli_coli_id(list2.get(j).getColi_id());
						cm.setCgli_startdate(list2.get(j).getSt());
						cm.setCgli_startdate2(list2.get(j).getSt2());
						cm.setCgli_group_id(list2.get(j).getColi_group_id());
						cm.setCgli_group_count(list2.get(j)
								.getColi_group_count());
						Integer k = dal.add(cm);

						if (!(k > 0)) {
							b = false;
						}
					}
				}
			}
		}
		return b;
	}

}
