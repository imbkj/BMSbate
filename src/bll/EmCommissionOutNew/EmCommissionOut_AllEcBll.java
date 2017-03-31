package bll.EmCommissionOutNew;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dal.EmCommissionOutNew.EmCommissionOut_AllEcDal;

public class EmCommissionOut_AllEcBll {
	EmCommissionOut_AllEcDal dal = new EmCommissionOut_AllEcDal();

	// Date类型转换String
	public String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String Date = sdf.format(d);
		return Date;
	}

	// 更新二次确认实际时间
	public int ecaut(String ecoc_id, String yl_cpdate, String yl_opdate,
			String sye_cpdate, String sye_opdate, String gs_cpdate,
			String gs_opdate, String jl_cpdate, String jl_opdate,
			String syu_cpdate, String syu_opdate, String house_cpdate,
			String house_opdate, String fw_date, String file_date,
			String other_date) throws SQLException {
		return dal.ecaut(ecoc_id, yl_cpdate, yl_opdate, sye_cpdate, sye_opdate,
				gs_cpdate, gs_opdate, jl_cpdate, jl_opdate, syu_cpdate,
				syu_opdate, house_cpdate, house_opdate, fw_date, file_date,
				other_date);
	}

	// 更新二次确认实际时间
	public int ecdiaut(String ecoc_id, String yl_cpdate, String yl_opdate,
			String sye_cpdate, String sye_opdate, String gs_cpdate,
			String gs_opdate, String jl_cpdate, String jl_opdate,
			String syu_cpdate, String syu_opdate, String house_cpdate,
			String house_opdate, String fw_date, String file_date,
			String other_date) throws SQLException {
		return dal.ecdiaut(ecoc_id, yl_cpdate, yl_opdate, sye_cpdate, sye_opdate,
				gs_cpdate, gs_opdate, jl_cpdate, jl_opdate, syu_cpdate,
				syu_opdate, house_cpdate, house_opdate, fw_date, file_date,
				other_date);
	}
}
