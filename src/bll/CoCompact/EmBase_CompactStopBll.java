package bll.CoCompact;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.CoCompact.CocompactDal;
import dal.CoProduct.cpd_List1Dal;
import dal.CoQuotation.CoofferDal;
import dal.CoQuotation.CoofferlistDal;
import dal.Embase.CoglistDal;

import Model.CoCompactModel;
import Model.CoOfferModel;
import Model.CoPclassModel;
import Model.CoProductModel;
import Model.CoglistModel;

public class EmBase_CompactStopBll {

	public List<CoCompactModel> getcompactlist(Integer cid) {
		List<CoCompactModel> list = new ListModelList<>();
		CocompactDal dal = new CocompactDal();
		CoCompactModel cm = new CoCompactModel();
		cm.setCid2(cid);
		cm.setDataState(1);
		list = dal.getCompactList(cm);
		return list;
	}

	public List<CoOfferModel> getquotelist(Integer cid, Integer coid) {
		List<CoOfferModel> list = new ListModelList<>();
		CoofferDal dal = new CoofferDal();
		list = dal.getCooffer(cid, coid);
		return list;
	}

	public List<CoPclassModel> getclasslist() {
		List<CoPclassModel> list = new ListModelList<>();
		cpd_List1Dal dal = new cpd_List1Dal();
		list = dal.getCoproductTypeList();
		return list;
	}

	public List<CoglistModel> getemplist(Integer cid, Integer coid,
			Integer coofId, String pclass, String name) {
		List<CoglistModel> list = new ListModelList<>();
		CoglistDal dal = new CoglistDal();
		list = dal.itemList(cid, coid, coofId, pclass, name, true);
		return list;
	}

	// 终止员工报价单项目
	public boolean stopItem(CoglistModel m) {
		boolean b = false;
		CoglistDal dal = new CoglistDal();
		Integer i = dal
				.updateCoglist(m, m.getCgli_id(), null, null, null, null);
		if (i > 0) {
			b = true;
		}
		return b;
	}
}
