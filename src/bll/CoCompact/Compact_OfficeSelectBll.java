package bll.CoCompact;

import java.util.List;

import Model.DocumentsInfoPubPicModel;

import dal.CoCompact.Compact_UploadDal;
import dal.CoCompact.OfficeSelectDal;

public class Compact_OfficeSelectBll {
	private OfficeSelectDal officeDal = new OfficeSelectDal();
	private List officeList;

	// 显示所有合同模板
	public List getofficeFile(int puof_tid,int sc,int puf_id) {
		officeList = officeDal.getofficeList(puof_tid,sc,puf_id);
		return officeList;
	}
	
	// 根据类型获取文件类型(type:1员工2公司)
	public List<DocumentsInfoPubPicModel> getoutcont(String coco_id)
			throws Exception {
		Compact_UploadDal dal = new Compact_UploadDal();
		List<DocumentsInfoPubPicModel> list = officeDal.getoutcont(coco_id);
		return list;
	}
}
