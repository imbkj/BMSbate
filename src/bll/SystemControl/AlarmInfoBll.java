package bll.SystemControl;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.SystemControl.AlarmClassDal;
import dal.SystemControl.AlarmInfoDal;

import Model.AlarmClassModel;
import Model.AlarmInfoModel;

public class AlarmInfoBll {

	/** 
	* @Title: getAlarmInfoList 
	* @Description: TODO(按预警类型查询预警信息) 
	* @param id
	* @return
	* @return List<AlarmInfoModel>    返回类型 
	* @throws 
	*/
	public List<AlarmInfoModel> getAlarmInfoList(Integer id) {
		List<AlarmInfoModel> list = new ListModelList<AlarmInfoModel>();
		AlarmInfoDal dal = new AlarmInfoDal();
		list = dal
				.getAlarmInfo(
						"select *,case alin_state when 1 then '生效' else '取消' end alin_stateName,case alin_warning when 1 then '是' else '否' end alin_warningName from AlarmInfo a inner join AlarmClass b on a.alin_alcl_id=b.alcl_id where alin_alcl_id=?",
						id);
		return list;
	}

	/** 
	* @Title: getAlarmInfoListById 
	* @Description: TODO(通过项目ID查询预警信息) 
	* @param id
	* @return
	* @return List<AlarmInfoModel>    返回类型 
	* @throws 
	*/
	public List<AlarmInfoModel> getAlarmInfoListById(Integer id) {
		List<AlarmInfoModel> list = new ListModelList<AlarmInfoModel>();
		AlarmInfoDal dal = new AlarmInfoDal();
		list = dal
				.getAlarmInfo(
						"select *,case alin_state when 1 then '生效' else '取消' end alin_stateName,case alin_warning when 1 then '是' else '否' end alin_warningName from AlarmInfo a inner join AlarmClass b on a.alin_alcl_id=b.alcl_id where alin_id=?",
						id);
		return list;
	}

	/** 
	* @Title: getAlarmInfoListByParam 
	* @Description: TODO(查询预警信息) 
	* @param i
	* @param n
	* @param c
	* @param w
	* @param s
	* @return
	* @return List<AlarmInfoModel>    返回类型 
	* @throws 
	*/
	public List<AlarmInfoModel> getAlarmInfoListByParam(Integer i, String n,
			String c, String w, String s) {
		List<AlarmInfoModel> list = new ListModelList<AlarmInfoModel>();
		AlarmInfoDal dal = new AlarmInfoDal();

		String p_n = n.equals("") ? "%" : n + "%";
		String p_c = c.equals("") ? "%" : c + "%";
		String p_w = w.equals("") ? "%" : w + "%";
		String p_s = s.equals("") ? "%" : s + "%";

		String sql = "select *,case alin_state when 1 then '生效' else '取消' end alin_stateName,case alin_warning when 1 then '是' else '否' end alin_warningName from AlarmInfo a inner join AlarmClass b on a.alin_alcl_id=b.alcl_id where  alin_alcl_id = ? and isnull(alin_name,'') like ? and isnull(alin_content,'') like ? and isnull(alin_warning,'') like ? and isnull(alin_state,'') like ?";
		list = dal.getAlarmInfo(sql, i, p_n, p_c, p_w, p_s);
		return list;
	}

	/** 
	* @Title: getAlarmInfoDisNameByAlclId 
	* @Description: TODO(查询聚焦预警项目名称) 
	* @param id
	* @return
	* @return List<AlarmInfoModel>    返回类型 
	* @throws 
	*/
	public List<AlarmInfoModel> getAlarmInfoDisNameByAlclId(Integer id) {
		List<AlarmInfoModel> list = new ListModelList<AlarmInfoModel>();
		AlarmInfoDal dal = new AlarmInfoDal();
		list = dal
				.getAlarmInfo(
						"select distinct alin_name from AlarmInfo where alin_alcl_id = ?",
						id.toString());
		return list;
	}

	/** 
	* @Title: addAlarmInfo 
	* @Description: TODO(新增预警项目) 
	* @param id
	* @param name
	* @param w
	* @param url
	* @param sql
	* @param content
	* @param addname
	* @return
	* @return String[]    返回类型 
	* @throws 
	*/
	public String[] addAlarmInfo(Integer id, String name, Integer w,
			String url, String sql, String content, String addname) {
		String[] message = new String[2];
		message = judgeName(id, name, 0);
		if (message[0] == null) {
			int result = 0;
			AlarmInfoModel am = new AlarmInfoModel();
			AlarmInfoDal dal = new AlarmInfoDal();
			am.setAlin_alcl_id(id);
			am.setAlin_name(name);
			am.setAlin_warning(w);
			am.setAlin_url(url);
			am.setAlin_sql(sql);
			am.setAlin_content(content);
			am.setAlin_addname(addname);

			result = dal.AddAlarmInfo(am);

			if (result > 0) {
				dal.addRule();
				message[0] = "1";
				message[1] = "新增类型成功!";
			} else {
				message[0] = "0";
				message[1] = "新增类型失败!";
			}
		}

		return message;
	}

	/** 
	* @Title: modAlarmInfo 
	* @Description: TODO(修改预警项目) 
	* @param id
	* @param alclid
	* @param name
	* @param w
	* @param url
	* @param sql
	* @param content
	* @param state
	* @param addname
	* @return
	* @return String[]    返回类型 
	* @throws 
	*/
	public String[] modAlarmInfo(Integer id, Integer alclid, String name,
			Integer w, String url, String sql, String content, Integer state,
			String addname) {
		String[] message = new String[2];
		message = judgeName(id, name, id);
		if (message[0] == null) {
			int result = 0;
			AlarmInfoModel am = new AlarmInfoModel();
			AlarmInfoDal dal = new AlarmInfoDal();
			am.setAlin_id(id);
			am.setAlin_alcl_id(alclid);
			am.setAlin_name(name);
			am.setAlin_warning(w);
			am.setAlin_url(url);
			am.setAlin_sql(sql);
			am.setAlin_state(state);
			am.setAlin_content(content);
			am.setAlin_addname(addname);

			result = dal.ModAlarmInfo(am);

			if (result > 0) {
				message[0] = "1";
				message[1] = "修改项目成功!";
			} else {
				message[0] = "0";
				message[1] = "修改项目失败!";
			}
		}

		return message;
	}

	/** 
	* @Title: judgeName 
	* @Description: TODO(判断预警项目名称唯一性) 
	* @param id
	* @param name
	* @param i
	* @return
	* @return String[]    返回类型 
	* @throws 
	*/
	public String[] judgeName(Integer id, String name, Integer i) {
		String[] message = new String[2];

		AlarmInfoDal dal = new AlarmInfoDal();
		List<AlarmInfoModel> list = new ListModelList<AlarmInfoModel>();
		try {
			if (!name.equals("") && id > 0) {
				String sql;
				if (i > 0) {
					sql = "select * from AlarmInfo where alin_state=1 and alin_id!=? and  alin_alcl_id=? and alin_name=?";
					list = dal.getAlarmInfo(sql, i, id, name);
				} else {
					sql = "select * from AlarmInfo where alin_state=1 and alin_alcl_id=? and alin_name=?";
					list = dal.getAlarmInfo(sql, id, name);
				}

				if (!list.isEmpty()) {
					message[0] = "4";
					message[1] = "预警类型已存在，无法重复添加。";
				}
			} else {

				message[0] = "3";
				message[1] = "请输入预警类型名称。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "系统预警，添加类型出错。";
			e.printStackTrace();
		}

		return message;
	}

}
