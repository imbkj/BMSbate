package dal.EmBenefit;

import java.sql.SQLException;
import java.util.List;
import org.zkoss.zul.ListModelList;
import java.sql.Types;
import Conn.dbconn;
import Model.EmBenefitModel;

public class EmBenefitDal {

	// 查询列表
	public List<EmBenefitModel> getlist(String name) {
		List<EmBenefitModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select embf_id,embf_name,embf_cycle,embf_unit,embf_type,case embf_type when 1 then '是' else '否' end embf_type2,embf_start,embf_notice,convert(varchar(19),embf_addtime,120)embf_addtime,embf_addname,embf_mold"
				+ " from EmBenefit a where embf_state =1";
		if (name != null && !name.equals("")) {
			sql = sql + " and embf_name='" + name + "'";
		}
		sql += " order by embf_name";
		System.out.println(sql);
		try {
			list = db.find(sql, EmBenefitModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询单条信息
	public List<EmBenefitModel> getlistInfo(Integer id) {
		List<EmBenefitModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select embf_id,embf_name,embf_cycle,embf_unit,embf_type,embf_mold,embf_month,embf_field,embf_start,embf_notice,embf_addtime,embf_addname,embf_state"
				+ " from EmBenefit where embf_id=?";
		System.out.println(sql);
		try {
			list = db.find(sql, EmBenefitModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询聚焦名称列表
	public List<EmBenefitModel> getNamelist(String name) {
		List<EmBenefitModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		name = name.equals("") ? "%" + name + "%" : name;
		String sql = "select distinct embf_id,embf_name"
				+ " from EmBenefit where embf_state =1 and embf_name like ? order by embf_name";

		System.out.println(sql);
		try {
			list = db.find(sql, EmBenefitModel.class, null, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询名称列表
	public List<EmBenefitModel> getDisNamelist() {
		List<EmBenefitModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct  embf_name"
				+ " from EmBenefit where embf_state =1 order by embf_name";
		System.out.println(sql);
		try {
			list = db.find(sql, EmBenefitModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询名称是否有修改
	public List<EmBenefitModel> getNamelistMod(String name, String name2) {
		List<EmBenefitModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct top 10 embf_name"
				+ " from EmBenefit where embf_state =1 and embf_name like ? and embf_name!=?";
		System.out.println(sql);
		try {
			list = db.find(sql, EmBenefitModel.class, null, name, name2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 启动任务
	public Integer start(Integer id, String username) {
		Integer i = 0;
		dbconn db = new dbconn();

		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call EmBenefit_Start_P_py(?,?)}", Types.INTEGER, id,
					username).toString());
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	// 新增
	public Integer add(String name, String start, Integer type, String mold,
			Integer cycle, String unit, String month, String field,
			String addname) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "insert into EmBenefit("
				+ "embf_name,embf_start,embf_unit,embf_type,embf_cycle,embf_mold,embf_month,"
				+ "embf_field,embf_addtime,embf_addname,embf_state)"
				+ " values(?,?,?,?,?,?,?,?,getdate(),?,1)";
		try {
			i = db.insertAndReturnKey(sql, name, start, unit, type, cycle,
					mold, month, field, addname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	// 修改
	public Integer mod(Integer id, String name, String start, String notice,
			Integer type, String mold, Integer cycle, String unit,
			String month, String field, String username) {
		Integer i = 0;
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		if (!name.equals("") || !start.equals("") || !unit.equals("")
				|| type >= 0 || cycle >= 0) {
			sql.append("update EmBenefit set embf_modtime=getdate(),embf_modname=?");
			if (name != null && !name.equals("")) {
				sql.append(" ,embf_name='" + name + "'");
			}
			if (start != null && !start.equals("")) {
				sql.append(" ,embf_start='" + start + "'");
			} else {
				sql.append(" ,embf_start=null");
			}
			if (notice != null && !notice.equals("")) {
				sql.append(" ,embf_notice='" + notice + "'");
			} else {
				sql.append(" ,embf_notice=null");
			}

			if (unit != null && !unit.equals("")) {
				sql.append(" ,embf_unit='" + unit + "'");
			}

			if (type != null && type >= 0) {
				sql.append(" ,embf_type=" + type + "");
			}
			if (cycle != null && cycle >= 0) {
				sql.append(" ,embf_cycle=" + cycle + "");
			}

			if (mold != null && !mold.equals("")) {
				sql.append(" ,embf_mold='" + mold + "'");
			}
			if (month != null && !month.equals("")) {
				sql.append(" ,embf_month='" + month + "'");
			}
			if (field != null && !field.equals("")) {
				sql.append(" ,embf_field='" + field + "'");
			}

			sql.append(" where embf_id=?");

			try {
				i = db.updatePrepareSQL(sql.toString(), username, id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return i;
	}

	public Integer del(Integer id, String name) {
		Integer i = 0;
		dbconn db = new dbconn();

		try {
			i = Integer.valueOf(db
					.callWithReturn("{?=call EmBenefit_Del_P_py(?,?)}",
							Types.INTEGER, id, name).toString());
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	
}
