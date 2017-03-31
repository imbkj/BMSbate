package bll.EmBaseCompact;

import impl.WorkflowCore.WfOperateImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.EmBaseCompact.EmBaseCompact_BaseDal;
import dal.EmBaseCompact.EmBaseCompact_UploadDal;

import service.WorkflowCore.WfOperateService;
import Model.EmBaseCompactListModel;
import Model.EmBaseCompactModel;
import Util.UserInfo;
import bll.CoCompact.CoCompactSA.CoCompact_UpOperateImpl;
import bll.EmBaseCompact.EmBaseCompact_OperateImpl;

public class EmBaseCompact_OperateBll {
	String username = UserInfo.getUsername();
	
	private ListModelList<EmBaseCompactListModel> al_base;// 劳动合同员工信息
	private ListModelList<EmBaseCompactListModel> al_base_cid;// 劳动合同公司编号
	
	// 劳动合同新签
	public String[] add_Emcompact(String cid, String gid, String embase1,
			String embase2, String embase3, String embase5, String embase6,
			String embase8, String embase9, String embase10, String embase11,
			String embase12, String embase13, String embase14, String embase15,
			String embase16, String embase17, String embase18, String embase19,
			String embase20, String embase21, String embase4,String compact_type) throws Exception {
		String[] message = new String[2];
		try {
			EmBaseCompact_BaseDal dal = new EmBaseCompact_BaseDal();
			al_base = new ListModelList<EmBaseCompactListModel>(dal.getEmBase_Base(Integer.parseInt(gid)));// 获取姓名

			Object[] obj = { "1", gid, cid, embase1, embase2, embase3, embase5,
					embase6, embase8, embase9, embase10, embase11, embase12,
					embase13, embase14, embase15, embase16, embase17, embase18,
					embase19, embase20, embase21, "", "0", "新签", embase4,compact_type };
			// 执行工作流
			
			WfOperateService wf = new WfOperateImpl(
					new EmBaseCompact_OperateImpl());
			message = wf.AddTaskToNext(obj, "劳动合同", al_base.get(0).getCompany()+ "--" +al_base.get(0).getName() + "--劳动合同新签", 10,
					username, "", Integer.parseInt(cid), "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "劳动合同新签，操作出错。";
		}
		return message;
	}

	// 劳动合同续签
	public String[] ren_emcompact(String cid, String gid, String embase1,
			String embase2, String embase3, String embase5, String embase6,
			String embase8, String embase9, String embase10, String embase11,
			String embase12, String embase13, String embase14, String embase15,
			String embase16, String embase17, String embase18, String embase19,
			String embase20, String embase21, String embase4,String compact_type) throws Exception {
		String[] message = new String[2];
		try {
			Object[] obj = { "8", gid, cid, embase1, embase2, embase3, embase5,
					embase6, embase8, embase9, embase10, embase11, embase12,
					embase13, embase14, embase15, embase16, embase17, embase18,
					embase19, embase20, embase21, "", "0", "续签", embase4,compact_type };
			// 执行工作流
			
			EmBaseCompact_BaseDal dal = new EmBaseCompact_BaseDal();
			al_base = new ListModelList<EmBaseCompactListModel>(dal.getEmBase_Base(Integer.parseInt(gid)));// 获取姓名

			WfOperateService wf = new WfOperateImpl(
					new EmBaseCompact_OperateImpl());
			message = wf.AddTaskToNext(obj, "劳动合同", al_base.get(0).getCompany()+"--"+al_base.get(0).getName() + "--劳动合同续签", 11,
					username, "", Integer.parseInt(cid), "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "劳动合同续签，操作出错。";
		}
		return message;
	}
	
	private EmBaseCompactModel reg;

	// 劳动合同修改
	public String[] edit_emcompact(String cid, String gid, String embase1,
			String embase2, String embase3, String embase5, String embase6,
			String embase8, String embase9, String embase10, String embase11,
			String embase12, String embase13, String embase14, String embase15,
			String embase16, String embase17, String embase18, String embase19,
			String embase20, String embase21, String embase4,String compact_type) throws Exception {
		String[] message = new String[2];
		try {
			String result ="0";
			reg = new EmBaseCompactModel(gid, cid, embase1, embase2, embase3, embase5,
					embase6, embase8, embase9, embase10, embase11, embase12,
					embase13, embase14, embase15, embase16, embase17, embase18,
					embase19, embase20, embase21, "", "0", "修改", embase4,compact_type);
			
			EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
			result = dal.Edit_Emcompact(reg);


			message[0] = result;
			message[1] = "劳动合同修改成功！";
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "劳动合同修改，操作出错。";
		}
		return message;
	}
	
	// 劳动合同签订
		public String[] qd_Emcompact(int ebcc_id, int ebcc_tapr_id,
				String qd_type,String qd_date) throws Exception {
			String[] message = new String[2];
			try {
				Object[] obj = { "11", ebcc_id, qd_type, username,qd_date };
				// 执行工作流
				
				EmBaseCompact_BaseDal dal = new EmBaseCompact_BaseDal();
				al_base_cid = new ListModelList<EmBaseCompactListModel>(dal.getEmBase_cid(ebcc_tapr_id));// 获取cid

				WfOperateService wf = new WfOperateImpl(
						new EmBaseCompact_OperateImpl());
				message = wf.PassToNext(obj, ebcc_tapr_id, username, "", Integer.parseInt(al_base_cid.get(0).getCid()), "");

			} catch (Exception e) {
				message[0] = "2";
				message[1] = "劳动合同签订，操作出错。";
			}
			return message;
		}

	// 劳动合同签回
	public String[] sign_Emcompact(int ebcc_id, int ebcc_tapr_id,
			String sign_date) throws Exception {
		String[] message = new String[2];
		try {
			Object[] obj = { "5", ebcc_id, sign_date, username };
			// 执行工作流
			
			EmBaseCompact_BaseDal dal = new EmBaseCompact_BaseDal();
			al_base_cid = new ListModelList<EmBaseCompactListModel>(dal.getEmBase_cid(ebcc_tapr_id));// 获取cid

			WfOperateService wf = new WfOperateImpl(
					new EmBaseCompact_OperateImpl());
			message = wf.PassToNext(obj, ebcc_tapr_id, username, "", Integer.parseInt(al_base_cid.get(0).getCid()), "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "劳动合同签回，操作出错。";
		}
		return message;
	}

	// 劳动合同归档
	public String[] filing_Emcompact(int ebcc_id, int ebcc_tapr_id,
			String filing_date) throws Exception {
		String[] message = new String[2];
		try {
			Object[] obj = { "6", ebcc_id, filing_date, username };
			// 执行工作流

			WfOperateService wf = new WfOperateImpl(
					new EmBaseCompact_OperateImpl());
			message = wf.PassToNext(obj, ebcc_tapr_id, username, "", 0, "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "劳动合同归档，操作出错。";
		}
		return message;
	}

	// 劳动合同签回
	public String[] sign_EmcompactSA(int ebcc_id, int ebcu_tapr_id,
			String sign_date) throws Exception {
		String[] message = new String[2];
		try {
			Object[] obj = { "4", ebcc_id, sign_date, username };
			// 执行工作流

			WfOperateService wf = new WfOperateImpl(
					new EmBaseCompactSA_OperateImpl());
			message = wf.PassToNext(obj, ebcu_tapr_id, username, "", 0, "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "劳动合同签回，操作出错。";
		}
		return message;
	}

	// 劳动合同归档
	public String[] filing_EmcompactSA(int ebcc_id, int ebcu_tapr_id,
			String filing_date) throws Exception {
		String[] message = new String[2];
		try {
			Object[] obj = { "5", ebcc_id, filing_date, username };
			// 执行工作流

			WfOperateService wf = new WfOperateImpl(
					new EmBaseCompactSA_OperateImpl());
			message = wf.PassToNext(obj, ebcu_tapr_id, username, "", 0, "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "劳动合同归档，操作出错。";
		}
		return message;
	}

	// 劳动合同非标上传
	public String[] UDocFileUpload(int cid, String emcompact_temp, String name,
			String url, String addname,String compact_type) throws Exception {

		String[] message = new String[5];
		try {
			Object[] obj = { "1", cid, emcompact_temp, name, url, addname,compact_type };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new EmBaseCompact_UpOperateImpl());
			message = wf.AddTaskToNext(obj, "劳动合同", cid + "劳动合同模板上传", 8,
					addname, "", cid, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "劳动合同模板上传，操作出错。";
		}
		return message;
	}

	// 劳动合同版本上传
	public String[] VerUpload(int cid, String emcompact_temp, String name,
			String url, String addname,String compact_type) throws Exception {

		String[] message = new String[5];
		try {
			EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
			dal.VerUpload(cid, emcompact_temp, name, url, addname,compact_type);
			message[0] = "1";
			message[1] = "劳动合同版本上传，操作成功。";
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "劳动合同版本上传，操作出错。";
		}
		return message;
	}

	// 公司合同版本上传
	public String[] CoVerUpload(int cid, String emcompact_temp, String name,
			String url, String addname,String coco_type) throws Exception {

		String[] message = new String[5];
		try {
			EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
			dal.CoVerUpload(cid, emcompact_temp, name, url, addname,coco_type);
			message[0] = "1";
			message[1] = "公司合同版本上传，操作成功。";
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "公司合同版本上传，操作出错。";
		}
		return message;
	}

	// Date类型转换String
	public String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String Date = sdf.format(d);
		return Date;
	}
}
