package bll.Embase;

import impl.WorkflowCore.WfOperateImpl;

import java.sql.SQLException;
import java.util.List;

import service.WorkflowCore.WfOperateService;
import bll.CoCompact.CoCompactSA.CoCompactSA_OperateImpl;

import Model.EmPicModel;
import dal.Embase.EmPic_CheckDal;

public class EmPic_CheckBll {
	// 获取图片信息
	public List<EmPicModel> getpiclist(String gid) throws SQLException {
		EmPic_CheckDal dal = new EmPic_CheckDal();
		List<EmPicModel> list = dal.getpiclist(gid);
		return list;
	}
	
	// 获取图片信息
		public List<EmPicModel> getcopiclist(String gid) throws SQLException {
			EmPic_CheckDal dal = new EmPic_CheckDal();
			List<EmPicModel> list = dal.getcopiclist(gid);
			return list;
		}
	
	// 获取图片类型
		public List<EmPicModel> getempicclasslist(String cl) throws SQLException {
			EmPic_CheckDal dal = new EmPic_CheckDal();
			List<EmPicModel> list = dal.getempicclasslist(cl);
			return list;
		}
		
		// 获取图片类型
				public List<EmPicModel> getcopicclasslist(String cl) throws SQLException {
					EmPic_CheckDal dal = new EmPic_CheckDal();
					List<EmPicModel> list = dal.getcopicclasslist(cl);
					return list;
				}

	// 图片上传
	public String[] empic_add(String gid, String pic_class,String url,String addname)
			throws Exception {
		int result = 0;
		EmPic_CheckDal dal = new EmPic_CheckDal();
		String[] message = new String[5];
		result = dal.empic_add(gid,pic_class,url,addname);
		if (result != 0) {
			message[0] = "1";
			message[1] = "上传成功!";
			message[2] = String.valueOf(result);
		} else {
			message[0] = "0";
			message[1] = "上传失败!";
		}
		return message;
	}
	
	// 图片上传
		public String[] copic_add(String cid, String pic_class,String url,String addname)
				throws Exception {
			int result = 0;
			EmPic_CheckDal dal = new EmPic_CheckDal();
			String[] message = new String[5];
			result = dal.copic_add(cid,pic_class,url,addname);
			if (result != 0) {
				message[0] = "1";
				message[1] = "上传成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "上传失败!";
			}
			return message;
		}
}
