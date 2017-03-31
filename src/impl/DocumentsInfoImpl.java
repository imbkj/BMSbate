/**
 * @Classname DocumentsInfoImpl
 * @ClassInfo 材料模块实现类（实现类service.DocumentsInfoService）
 * @author 林少斌
 * @Date 2013-10-29
 */
package impl;

import java.util.ArrayList;

import dal.DocumentsInfo.Documents_SelectDal;
import dal.DocumentsInfo.Documents_OperateDal;

import service.DocumentsInfoService;

import Model.DocumentsHandoverLogModel;
import Model.DocumentsInfoModel;
import Model.DocumentsSubmitInfoModel;
import Util.UserInfo;

public class DocumentsInfoImpl implements DocumentsInfoService {

	Documents_SelectDal dsDal = new Documents_SelectDal();
	Documents_OperateDal doDal = new Documents_OperateDal();

	// 新增页面显示材料数据
	public ArrayList<DocumentsInfoModel> getAddPageDoc(String puzu_id,
			String f_puzu_id, String dtype, String cgid, String str) {
		// 拼接sql语句
		String zid;
		if (f_puzu_id != null && !"".equals(f_puzu_id)
				&& !"null".equals(f_puzu_id)) {
			zid = f_puzu_id;
		} else {
			zid = puzu_id;
		}

		String sql = " AND puzu_id=" + zid;
		sql = sql + " " + str;
		ArrayList<DocumentsInfoModel> docInfo = dsDal.getAddPageDocList(dtype,
				cgid, sql, puzu_id);
		return docInfo;
	}

	// 修改页面显示材料数据
	public ArrayList<DocumentsSubmitInfoModel> getUpdatePageDoc(String puzu_id,
			String dtype, String cgid, String tid, String str) {
		// 拼接sql语句puzu_id, tid
		String sql = " AND dire_puzu_id=" + puzu_id + " AND dsin_tid=" + tid;
		sql = sql + " " + str;
		ArrayList<DocumentsSubmitInfoModel> docInfo = dsDal
				.getUpdatePageDocList(dtype, cgid, sql);
		return docInfo;
	}

	// 业务材料表修改方法
	public String[] addDocSubmitInfo(String dire_id, String tid,
			String ifsubmit, String count, String handover_people,
			String addname) {

		int result = 0;
		String[] message = new String[2];

		// 检查数据有效性
		if (!dire_id.equals("") && !tid.equals("") && !ifsubmit.equals("")
				&& !count.equals("") && !addname.equals("")) {
			// 判断份数是否为0
			if (count.equals("0")) {
				count = "1";
			}

			result = doDal.addDocSubmitInfo(dire_id, tid, ifsubmit, count,
					handover_people, addname);
		}
		if (result > 0) {
			message[0] = "1";
			message[1] = "操作成功!";
		} else {
			message[0] = "0";
			message[1] = "操作失败!";
		}

		return message;
	}

	// 业务材料表修改方法
	public String[] addDocSubmitInfo(String dire_id, String tid,
			String ifsubmit, String count, String handover_people,
			String addname, String out_count) {

		int result = 0;
		String[] message = new String[2];

		// 检查数据有效性
		if (!dire_id.equals("") && !tid.equals("") && !ifsubmit.equals("")
				&& !count.equals("") && !addname.equals("")) {
			// 判断份数是否为0
			if (count.equals("0")) {
				count = "1";
			}

			// 判断退回材料份数是否为空
			if ("".equals(out_count)) {
				out_count = "0";
			}

			result = doDal.addDocSubmitInfo(dire_id, tid, ifsubmit, count,
					handover_people, addname, out_count);
		}
		if (result > 0) {
			message[0] = "1";
			message[1] = "操作成功!";
		} else {
			message[0] = "0";
			message[1] = "操作失败!";
		}

		return message;
	}

	// 显示材料交接记录
	public ArrayList<DocumentsHandoverLogModel> getDocLog(int dsin_id) {
		// 拼接sql语句
		String sql = " AND dhlo_dsin_id=" + String.valueOf(dsin_id);
		ArrayList<DocumentsHandoverLogModel> docLog = dsDal.getDocLogList(sql);
		return docLog;
	}

	@Override
	public Integer createDocumentInfo(Integer puzu_id, Integer dsin_tid,
			String username) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = doDal.addBlankInfo(puzu_id, dsin_tid, username);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public Integer getIfSubmit(Integer puzu_id, Integer ifsubmit, String type) {

		int result = 0;
		try {
			result = doDal.getIfSubmit(puzu_id, ifsubmit, type);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public String[] deleteDoc(String puzu_id, String tid) {
		int result = 0;
		String[] message = new String[2];

		result = doDal.deleteDoc(puzu_id, tid);

		if (result > 0) {
			message[0] = "1";
			message[1] = "操作成功!";
		} else {
			message[0] = "0";
			message[1] = "操作失败!";
		}

		return message;
	}

	@Override
	public Integer addpageAddDocument(String name, Integer puzuId,
			String addname) {
		// TODO Auto-generated method stub
		Integer result = doDal.addDoc(name, puzuId, addname);

		return result;
	}

}
