package dal.EmHouse;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmHouseChangeModel;
import Model.EmHouseSetupModel;

public class EmHouseSetupDal {

	// 查询列表
	public List<EmHouseSetupModel> getList() {
		List<EmHouseSetupModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select id,lastday,fwday,lastdayname,lastdaybj,lastdaynamebj,onair,onairname,reason,onairbj,onairnamebj,reasonbj,cwday,fallday,auditday,auditdayname,salay,salayname,"
				+ "y+'-'+m+'-'+convert(varchar(10),case when lastday>day(dbo.endofmonth(y+'-'+m+'-1',0)) then day(dbo.endofmonth(y+'-'+m+'-1',0)) else LastDay end)lastdate,"
				+ "y+'-'+m+'-'+convert(varchar(10),case when fwday>day(dbo.endofmonth(y+'-'+m+'-1',0)) then day(dbo.endofmonth(y+'-'+m+'-1',0)) else fwday end)fwdate"
				+ " from EmHouseSetup a"
				+ " inner join (select top 1 substring(convert(varchar(10),ownmonth),1,4)y,substring(convert(varchar(10),ownmonth),5,2)m from EmHouseUpdate)b on 1=1";
		try {
			list = db.find(sql, EmHouseSetupModel.class,
					dbconn.parseSmap(EmHouseSetupModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
