package bll.CoCompact.CoCompactSA;

import java.util.List;

import Model.DocumentsInfoModel;
import Model.DocumentsInfoPubPicModel;
import dal.CoCompact.CoCompactSA.Compact_BcUploadDal;

public class Compact_BcUploadBll {
	// 根据类型获取文件类型(1员工2公司)
			public List<DocumentsInfoModel> getDocClassByType(int type)
					throws Exception {
				Compact_BcUploadDal dal = new Compact_BcUploadDal();
				List<DocumentsInfoModel> list = dal.getDocClassByType(type);
				return list;

			}

			// 根据类型获取文件类型(type:1员工2公司)
			public List<DocumentsInfoPubPicModel> getPicById(int id, int type)
					throws Exception {
				Compact_BcUploadDal dal = new Compact_BcUploadDal();
				List<DocumentsInfoPubPicModel> list = dal.getPicById(id, type);
				return list;
			}

			// 根据ID获取PubPic表url信息
			public String getPicUrl(int id) throws Exception {
				Compact_BcUploadDal dal = new Compact_BcUploadDal();
				String url = dal.getPicUrl(id);
				return url;
			}

			// 文件上传
			public boolean DocFileUpload(int cid, int gid, String doin_id, String url,
					String size, String addname) {
				Compact_BcUploadDal dal = new Compact_BcUploadDal();
				Integer i =dal.DocFileUpload(cid, gid, doin_id, url, size, addname,0,null);
				if (i>0) {
					return true;
				}else {
					return false;
				}
				
			}
}
