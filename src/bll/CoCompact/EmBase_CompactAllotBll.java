package bll.CoCompact;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.CoCompact.CocompactDal;
import dal.CoQuotation.CoofferDal;
import dal.CoQuotation.CoofferlistDal;
import dal.Embase.CoglistDal;
import dal.Embase.Embasedal;

import Model.CoCompactModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Model.CoglistModel;
import Model.EmbaseModel;
import Util.DateStringChange;
import Util.UserInfo;

public class EmBase_CompactAllotBll {

	public List<EmbaseModel> getEmbaseList(EmbaseModel em) {
		List<EmbaseModel> list = new ListModelList<>();
		List<CoglistModel> list2 = new ListModelList<>();
		Embasedal dal = new Embasedal();
		CoglistDal dal2 = new CoglistDal();
		list = dal.getEmbaseInfo(em);
		list2 = dal2.getEmpItemList(em.getCid());
		for (EmbaseModel m1 : list) {
			boolean start=false;
			if (m1.getGid().equals(137474)) {
				System.out.println("get");
			}
			for (CoglistModel m2 : list2) {
				if (m1.getGid().equals(m2.getGid())) {
					if (!start) {
						if (m1.getGid().equals(137474)) {
							System.out.println("start");
						}
						start=true;
					}else {
						if (!m1.getGid().equals(m2.getGid())) {
							break;
						}
					}
					if (m1.getCglist()==null) {
						if (m1.getGid().equals(137474)) {
							System.out.println("add");
						}
						m1.setCglist(new ListModelList<CoglistModel>());
						m1.getCglist().add(m2);
					}else {
						if (m1.getGid().equals(137474)) {
							System.out.println("add2");
						}
						m1.getCglist().add(m2);
					}	
				}
			}
			if (m1.getGid().equals(137474)) {
				System.out.println(m1.getCglist().size());
			}
		}

		return list;
	}

	public List<CoCompactModel> getCompactList(Integer cid) {
		List<CoCompactModel> list = new ListModelList<>();
		CocompactDal dal = new CocompactDal();

		list = dal.getCompactListByCid(cid);
		return list;
	}

	public List<CoOfferModel> getCofList(List<CoCompactModel> coList, boolean b) {
		List<CoOfferModel> list = new ListModelList<>();
		CoofferDal dal = new CoofferDal();
		list = dal.getCoofferByCocoList(coList, b);
		return list;
	}

	public boolean getEmpCoofferlist(Integer gid, Integer coliId) {
		boolean b = false;
		CoglistDal dal = new CoglistDal();
		List<CoglistModel> list = dal.getCoglistInfo(gid, coliId);
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	// 读取项目信息
	public List<CoOfferListModel> getColList(Integer cid,
			List<CoCompactModel> cl1, List<CoOfferModel> cl2, String name) {
		List<CoOfferListModel> list = new ListModelList<>();
		CoofferlistDal dal = new CoofferlistDal();
		CoOfferListModel cm = new CoOfferListModel();
		cm.setCid(cid);
		cm.setCcmList(cl1);
		cm.setComList(cl2);
		cm.setColi_name(name);
		cm.setProduct(true);
		cm.setAllot(true);
		cm.setColi_state(1);
		list = dal.getcoofferlist(cm);
		return list;
	}

	// 读取系统员工项目信息
	public List<CoglistModel> getcgList(Integer cid) {
		List<CoglistModel> list = new ListModelList<>();
		CoglistDal dal = new CoglistDal();
		list = dal.getcgList(cid);

		return list;
	}

	// 添加报价单
	public Integer addCoglist(List<EmbaseModel> ebList,
			List<CoOfferListModel> cofList) {
		Integer i = 0;
		Integer k = 0;
		CoglistDal dal = new CoglistDal();
		CoglistModel cm = new CoglistModel();

		boolean b = false;
		for (CoOfferListModel cfm : cofList) {
			if (cfm.isAddchecked()) {
				b = true;
				break;
			}

		}

		for (int j = 0; j < ebList.size(); j++) {
			if (ebList.get(j).isChecked()) {
				if (!b) {
					dal.resetItem(ebList.get(j).getGid(),
							Integer.valueOf(UserInfo.getDepID()));
				}

				for (int j2 = 0; j2 < cofList.size(); j2++) {
					if (cofList.get(j2).isChecked()
							|| cofList.get(j2).isAddchecked()) {

						cm.setGid(ebList.get(j).getGid());
						cm.setCid(ebList.get(j).getCid());
						cm.setCgli_coli_id(cofList.get(j2).getColi_id());

						cm.setCgli_startdate(Integer.valueOf(DateStringChange
								.DatetoSting(cofList.get(j2)
										.getCgli_startdate(), "yyyyMM")));
						cm.setCgli_stopdate(null);
						cm.setCgli_startdate2(Integer.valueOf(DateStringChange
								.DatetoSting(cofList.get(j2)
										.getCgli_startdate2(), "yyyyMM")));

						cm.setCgli_addname(UserInfo.getUsername());
						cm.setCgli_group_id(cofList.get(j2).getColi_group_id());
						cm.setCgli_group_count(cofList.get(j2)
								.getColi_group_count());

						i = dal.add(cm);
						k = k + i;
					}
				}
			}
		}

		return k;
	}
}
