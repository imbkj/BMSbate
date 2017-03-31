package bll.CoCompact.CoCompactSA;

import impl.WorkflowCore.WfOperateImpl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import service.WorkflowCore.WfOperateService;
import Model.CI_Insurant_ListModel;
import Model.CoCompactSAModel;
import bll.CoCompact.CoCompact_OperateImpl;
import bll.CoCompact.CoCompactSA.CoCompactSA_OperateImpl;
import dal.CoCompact.CoCompactSA.CoCompactSA_OperateDal;
import dal.CoCompact.CoCompactSA.Compact_BcUploadDal;

public class CoCompactSA_OperateBll {

	// 文件上传
	public String[] DocFileUpload(int coco_id, int gid, String doin_id, String url,
			String size, String addname, int coli_id, String co_type)
			throws Exception {

		String[] message = new String[5];
		try {
			Object[] obj = { "0", coco_id, gid, doin_id, url, size, addname,
					coli_id, co_type };
			// 执行工作流
			
			Compact_BcUploadDal dal = new Compact_BcUploadDal();
			List<CI_Insurant_ListModel> list = dal.getcid(coco_id);
			
			WfOperateService wf = new WfOperateImpl(
					new CoCompactSA_OperateImpl());
			message = wf.AddTaskToNext(obj, "公司合同补充协议",list.get(0).getCoba_company()+"-补充协议", 4, addname,
					"", Integer.parseInt(list.get(0).getCid()), "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "公司合同补充协议上传，操作出错。";
		}
		return message;
	}

	// 公司合同上传
	public String[] UDocFileUpload(int coco_id, String tarp_id, String doin_id,
			String url, String size, String addname) throws Exception {

		Compact_BcUploadDal dal = new Compact_BcUploadDal();
		List<CI_Insurant_ListModel> list = dal.getcola_id(coco_id);
		
		String[] message = new String[5];
		try {
			Object[] obj = { "10", coco_id, tarp_id, doin_id, url, size, addname };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new CoCompact_OperateImpl());
			
			message = wf.PassToNext(obj, Integer.parseInt(tarp_id), addname, "", Integer.parseInt(list.get(0).getCid()), "");
			System.out.println(message[1]);
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "公司合同上传，操作出错。";
		}
		return message;
	}

	// 公司合同签回
	public String[] signCoCompactSA(int ccsa_tapr_id, int ccsa_id,
			String ccsa_returndate, String ccsa_signdate,
			String ccsa_signplace, String username,int cid) throws Exception {

		String[] message = new String[5];
		try {
			CoCompactSAModel m = new CoCompactSAModel();
			m.setCcsa_returndate(ccsa_returndate);
			m.setCcsa_signdate(ccsa_signdate);
			m.setCcsa_signplace(ccsa_signplace);
			Object[] obj = { "2", ccsa_id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new CoCompactSA_OperateImpl());
			message = wf.PassToNext(obj, ccsa_tapr_id, username, "", cid, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "公司合同补充协议签回，操作出错。";
		}
		return message;
	}

	// 公司合同归档
	public String[] returnCoCompactSA(int ccsa_tapr_id, int ccsa_id,
			String ccsa_filedate, String ccsa_fileid, String ccsa_chs_copies,
			String ccsa_en_copies, String ccsa_remark, String username,int cid) {

		String[] message = new String[5];
		try {
			CoCompactSAModel m = new CoCompactSAModel();
			m.setCcsa_filedate(ccsa_filedate);
			m.setCcsa_fileid(ccsa_fileid);
			m.setCcsa_chs_copies(ccsa_chs_copies);
			m.setCcsa_en_copies(ccsa_en_copies);
			m.setCcsa_remark(ccsa_remark);
			Object[] obj = { "3", ccsa_id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new CoCompactSA_OperateImpl());
			message = wf.PassToNext(obj, ccsa_tapr_id, username, "", cid, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "公司合同补充协议归档，操作出错。";
		}
		return message;
	}

	// 合同补充协议修改
	public boolean UpCoCompactSA(String ccsa_inuredate, String ccsa_remark,
			int ccsa_id) {
		String str = "";
		String[] message = new String[2];
		CoCompactSAModel model = new CoCompactSAModel();
		model.setCcsa_inuredate(ccsa_inuredate);
		model.setCcsa_remark(ccsa_remark);
		model.setCcsa_id(ccsa_id);
		CoCompactSA_OperateDal dal = new CoCompactSA_OperateDal();
		boolean flag = dal.UpCoCompactSA(model);

		return flag;
	}

	// Date类型转换String
	public String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String Date = sdf.format(d);
		return Date;
	}
}
