package bll.EmCommissionOut;

import java.sql.SQLException;
import java.util.List;

import Model.EmCommissionOutPayDifModel;
import dal.EmCommissionOut.EmCommissionOutPayDifDal;
/**
 * 
 * @author 苏宏远
 * 2016-05-26
 */
public class EmCommissionOutPayDifBll {
//委托对帐未办反馈导入导出,按月份查找有差异数据，并添加到差异记录表
	public void addEmCommissionOutPayDifInfo(String ownmon,String ecop_cabc) throws SQLException, Exception{
		EmCommissionOutPayDifDal dal =new EmCommissionOutPayDifDal();
		dal.addEmCommissionOutPayDif(ownmon, ecop_cabc);
	}
	//委托对帐未办反馈导出
	public List<EmCommissionOutPayDifModel> getExportList(String ownmon,String ecop_cabc,String coab_name) throws SQLException{
		EmCommissionOutPayDifDal dal =new EmCommissionOutPayDifDal();
		return dal.getEmCommissionOutPayDifList(ownmon, ecop_cabc,coab_name);
	}
	//委托对帐未办反馈导入反馈信息更新
	public Integer updateEmCommissionOutPayDif(String ownmon,String ecop_cabc ,String ecod_remark,String yl,String sye,String gs,String syu,String jl,String house,String other,String fuwu,String file){
		EmCommissionOutPayDifDal dal =new EmCommissionOutPayDifDal();
		return dal.upEmCommissionOutPayDif(ownmon, ecop_cabc, ecod_remark,yl,sye,gs,syu,jl,house,other,fuwu,file);
	}
	
	
}
