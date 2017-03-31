package bll.Archives;

import dal.Archives.EmArchiveDatumDal;
import Model.EmArchiveRemarkModel;

public class EmArchiveDatumRemarkBll {

	public Integer add(EmArchiveRemarkModel em){
		Integer i=0;
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		i=dal.addremark(em);
		return i;
	}
}
