package bll.Archives;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.Archives.EmArchiveSetupDal;

import Model.EmArchiveSetupModel;

public class Archive_addFeeBll {

	public List<EmArchiveSetupModel> getList(EmArchiveSetupModel em) {
		List<EmArchiveSetupModel> list = new ListModelList<>();
		EmArchiveSetupDal dal = new EmArchiveSetupDal();

		list = dal.getSetUpList(em);
		return list;
	}
}
