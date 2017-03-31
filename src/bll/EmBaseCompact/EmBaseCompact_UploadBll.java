package bll.EmBaseCompact;

import java.util.List;

import Model.DocumentsInfoModel;
import Model.DocumentsInfoPubPicModel;
import dal.CoCompact.Compact_UploadDal;

public class EmBaseCompact_UploadBll {
	// 根据类型获取文件类型(1员工2公司)
			public List<DocumentsInfoModel> getDocClassByType(int type)
					throws Exception {
				Compact_UploadDal dal = new Compact_UploadDal();
				List<DocumentsInfoModel> list = dal.getDocClassByType(type);
				return list;

			}

			// 根据类型获取文件类型(type:1员工2公司)
			public List<DocumentsInfoPubPicModel> getPicById(int id, int type)
					throws Exception {
				Compact_UploadDal dal = new Compact_UploadDal();
				List<DocumentsInfoPubPicModel> list = dal.getPicById(id, type);
				return list;
			}

			// 根据ID获取PubPic表url信息
			public String getPicUrl(int id) throws Exception {
				Compact_UploadDal dal = new Compact_UploadDal();
				String url = dal.getPicUrl(id);
				return url;
			}

			// 文件上传
			public boolean DocFileUpload(int cid, int gid, String doin_id, String url,
					String size, String addname) {
				Compact_UploadDal dal = new Compact_UploadDal();
				return dal.DocFileUpload(cid, gid, doin_id, url, size, addname);
			}
}
