package dal.EmHouse;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmCensusModel;
import Model.EmHouseSetupModel;

public class Emhouse_InstallDal {	
	//查询公积金设置信息
	public EmHouseSetupModel getEmHouseSetupInfo()
	{
		ResultSet rs = null;
		EmHouseSetupModel model=new EmHouseSetupModel();
		dbconn db = new dbconn();
		String sql="select top 1 * from EmHouseSetup order by id desc";			
		try {
			rs=db.GRS(sql);
			while(rs.next())
			{
				model.setId(rs.getInt("id"));
				model.setLastday(rs.getInt("LastDay"));
				model.setLastdayname(rs.getString("LastDayName"));
				model.setLastdaybj(rs.getInt("LastDayBJ"));
				model.setLastdaynamebj(rs.getString("LastDayNameBJ"));
				model.setOnair(rs.getInt("OnAir"));
				model.setOnairname(rs.getString("OnAirName"));
				model.setReason(rs.getString("Reason"));
				model.setOnairbj(rs.getInt("OnAirBJ"));
				model.setOnairnamebj(rs.getString("OnAirNameBJ"));
				model.setReasonbj(rs.getString("ReasonBJ"));
				model.setCwday(rs.getInt("CwDay"));
				model.setFallday(rs.getInt("FallDay"));
				model.setAuditday(rs.getInt("auditday"));
				model.setAuditdayname(rs.getString("AuditDayName"));
				model.setSalay(rs.getInt("Salay"));
				model.setSalayname(rs.getString("SalayName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return model;
	}
	
	//户口新增
	public Integer EmHouseSetupUpdate(EmHouseSetupModel m) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
				.parseInt(db.callWithReturn(
				"{?=call EmHouseSetup_Update_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				Types.INTEGER, m.getId(), m.getLastday(),m.getLastdayname(),m.getAuditday(),
				m.getAuditdayname(),m.getLastdaybj(),m.getLastdaynamebj(),m.getSalay(),
				m.getSalayname(),m.getOnair(),m.getOnairname(),m.getReason(),m.getOnairbj(),
				m.getOnairnamebj(),m.getReasonbj()).toString());
			} catch (Exception e) {
				e.printStackTrace();
		}
		return i;
	}
}
