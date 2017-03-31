package bll.CoAgency;

import impl.CheckStringImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import service.CheckStringService;

import dal.CoAgency.CoAgencyBaseOperateDal;
import dal.CoAgency.CoAgencyBaseDal;
import Model.CoAgencyBaseModel;

public class BaseInfo_DisBll {
	// 查询所有机构
	public List<CoAgencyBaseModel> getCoAgencyBaseAll(String city) {
		CoAgencyBaseDal dal = new CoAgencyBaseDal();
		String str = checkExceptCity(city);
		List<CoAgencyBaseModel> BaseList = dal.getCoAgBaseList(str);
		return BaseList;
	}

	// 按条件查询机构
	public List<CoAgencyBaseModel> searchCoAgencyBase(String city, String name) {
		CoAgencyBaseDal dal = new CoAgencyBaseDal();
		String sql = checkCoagBaseSearchName(city, name);
		List<CoAgencyBaseModel> BaseList = dal.getCoAgBaseList(sql);
		return BaseList;
	}

	// 分配机构与城市，传入参数，返回message数组(0失败1成功2出错)
	public String[] DisBase(List<String[]> list, String addname) {
		String[] message = new String[2];
		try {
			CoAgencyBaseOperateDal opDal = new CoAgencyBaseOperateDal();
			int result = 0;
			int sum = list.size();
			for (String[] arg : list) {
				result += opDal.DisBase(arg[1], arg[0], addname);
			}

			if (result == sum) {
				message[0] = "1";
				message[1] = "总共分配机构数：" + sum + "；全部分配成功";
			} else if (result == 0) {
				message[0] = "0";
				message[1] = "总共分配机构数：" + sum + "；全部分配失败";
			} else {
				message[0] = "0";
				message[1] = "总共分配机构数：" + sum + "；其中有" + (sum - result)
						+ "间机构分配失败;";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "分配机构出错。";

		}
		return message;
	}

	// 传入城市名称，拼接sql语句
	private String checkExceptCity(String city) {
		StringBuilder str = new StringBuilder();
		if (!"".equals(city)) {
			str.append(" and coab_id not in(select cabc_coab_id from CoAgencyBaseCityRel ");
			str.append("where cabc_ppc_id=(select id from PubProCity where name='");
			str.append(city);
			str.append("') and cabc_state=1) ");
		}
		return str.toString();
	}

	// 委托机构查询拼接SQL
	private String checkCoagBaseSearchName(String city, String name) {
		CheckStringService ch = new CheckStringImpl();
		StringBuilder sql = new StringBuilder();
		sql.append(checkExceptCity(city));
		if (name != "" && name != null) {
			name = name.trim();
			if (ch.isChinese(name)) {
				sql.append(" and coab_name like '%");
				sql.append(name);
				sql.append("%' ");
			} else if (ch.isNum(name)) {
				sql.append(" and (coab_id='");
				sql.append(name);
				sql.append("' or coab_name like '%");
				sql.append(name);
				sql.append("%')");
			} else if (ch.isLetter(name)) {
				sql.append(" and (coab_namespell like '%");
				sql.append(name);
				sql.append("%' or coab_name like '%");
				sql.append(name);
				sql.append("%')");
			} else {
				sql.append(" and (coab_namespell like '%");
				sql.append(name);
				sql.append("%' or  coab_name like '%");
				sql.append(name);
				sql.append("%')");
			}
		}
		return sql.toString();
	}
}
