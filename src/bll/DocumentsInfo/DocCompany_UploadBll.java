package bll.DocumentsInfo;

import java.util.List;

import Model.DocumentsInfoModel;
import Model.DocumentsInfoPubPicModel;
import dal.DocumentsInfo.Document_UploadDal;

public class DocCompany_UploadBll {
	// 根据类型获取文件类型(1员工2公司)
	public List<DocumentsInfoModel> getDocClassByType(int type)
			throws Exception {
		Document_UploadDal dal = new Document_UploadDal();
		List<DocumentsInfoModel> list = dal.getDocClassByType(type);
		return list;

	}

	// 根据类型获取文件类型(type:1员工2公司)
	public List<DocumentsInfoPubPicModel> getPicById(int id, int type)
			throws Exception {
		Document_UploadDal dal = new Document_UploadDal();
		List<DocumentsInfoPubPicModel> list = dal.getPicById(id, type);
		return list;
	}

	// 根据ID获取PubPic表url信息
	public String getPicUrl(int id) throws Exception {
		Document_UploadDal dal = new Document_UploadDal();
		String url = dal.getPicUrl(id);
		return url;
	}

	// 文件上传
	public boolean DocFileUpload(int cid, int gid, int doin_id, String url,
			String size, String addname) {
		Document_UploadDal dal = new Document_UploadDal();
		return dal.DocFileUpload(cid, gid, doin_id, url, size, addname);
	}
}
