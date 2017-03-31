package dal.EmCommissionOut.Standard;

import Conn.dbconn;
import Model.WtServiceStandardrelationModel;
import Model.wtoutFeeModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.ListModelList;


public class wtoutFeeDal {
	
	dbconn db = new dbconn();

	//根据cid获取list
		public List<wtoutFeeModel> getmodellist(String strwhere)
		{
			
			List<wtoutFeeModel> list = new ArrayList<>();
			dbconn db = new dbconn();
			String sql = "SELECT a.coab_id as coba_id,d.wtss_id,wtss_title,coba_company,wtot_feetitle,wtot_tapr_id,cabc_ppc_id,city,province,coab_namespell,a.cid,coba_shortname,a.wtot_feeid,wtot_feetitle,wtot_fee,wtot_addname,wtot_addtime,wtot_examinenamekf," +
					"wtot_examinetimefk,wtot_editname,CONVERT(varchar(10),wtot_edittime,120) wtot_edittime,wtot_remark,wtot_backremark,wtot_examinenameqg,wtot_examinetimeqg," +
					"wtot_state,a.coab_id,wtss_city,case wtot_state when 0 then '未审核' " +
					"when 1 then '部门经理已审' when 2 then '全国项目部已审' when 3 then '已生效' " +
					"when 4 then '退回'  when 5 then '禁止' end wtot_statestr	,coab_name,sumnum,wtot_sbownmonth,wtot_gjjownmonth FROM [wtoutFee] a " +
					"inner join (select * from cobase ) b on a.cid=b.cid " +
					"inner join (select * from CoAgencyBaseCityRel_view) c on c.cabc_id=a.coab_id " +
					"inner join (select * from WtServiceStandardrelation) d on a.Wtot_feeid=d.wtot_feeid " +
					"inner join (select * from WtServiceStandard ) e on d.wtss_id=e.wtss_id " +
					"left join (SELECT ecou_ecos_id,count(*) sumnum  from EmCommissionOut WHERE ecou_addtype not IN ('离职','补缴','取消') GROUP by ecou_ecos_id ) f on a.Wtot_feeid=f.ecou_ecos_id "+
					"where 1=1 "
					+ strwhere
					+ " order by Wtot_feeid desc";
			System.out.print(sql);
			try {
				list = db.find(sql, wtoutFeeModel.class,
						dbconn.parseSmap(wtoutFeeModel.class));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return  list;
			
			
			
		}
		
		
		//根据wtotchangeid获取list
				public List<wtoutFeeModel> getmodelchangelist(String strwhere)
				{
					
					List<wtoutFeeModel> list = new ArrayList<>();
					dbconn db = new dbconn();
					String sql = "SELECT wtot_feechangeid,a.coab_id as coba_id,d.wtss_id,coba_company,wtot_feetitle,wtot_tapr_id,cabc_ppc_id,city,province,coab_namespell,a.cid,coba_shortname,a.wtot_feeid,wtot_feetitle,wtot_fee,wtot_addname,wtot_addtime,wtot_examinenamekf," +
							"wtot_examinetimefk,wtot_editname,CONVERT(varchar(10),wtot_edittime,120) wtot_edittime,wtot_remark,wtot_backremark,wtot_examinenameqg,wtot_examinetimeqg,wtot_comfdate, " +
							"wtot_state,a.coab_id,wtss_city,case wtot_state when 0 then '未审核' " +
							"when 1 then '部门经理已审' when 2 then '全国项目部已审' when 3 then '已生效' " +
							"when 4 then '退回' end wtot_statestr	,coab_name FROM [wtoutFeechange] a " +
							"inner join (select * from cobase ) b on a.cid=b.cid " +
							"inner join (select * from CoAgencyBaseCityRel_view) c on c.cabc_id=a.coab_id " +
							"inner join (select * from WtServiceStandardrelation) d on a.Wtot_feeid=d.wtot_feeid where 1=1 "
							+ strwhere
							+ " order by Wtot_feeid desc";
					System.out.print(sql);
					try {
						list = db.find(sql, wtoutFeeModel.class,
								dbconn.parseSmap(wtoutFeeModel.class));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return  list;
					
					
					
				}
		
		//插入mode
		public Integer Wtfeeadd(wtoutFeeModel m) {
			dbconn db = new dbconn();
			Integer row = 0;

			try {
				row = Integer.parseInt(db.callWithReturn(
						"{?=call wtoutFee_ADD_p_zmj" +
						"(?,?,?,?,?,?,?," +
						"?,?,?,?,?,?,?)}",
						Types.INTEGER,
						m.getWtss_id(),
						m.getCid(),
						m.getWtot_feetitle(),
						m.getWtot_fee(),
						m.getWtot_addname(),
						m.getWtot_examinenamekf(),
						m.getWtot_editname(),
						m.getWtot_remark(),
						m.getWtot_examinenameqg(),
						m.getWtot_state(),
						m.getCoba_id(),
						m.getWtss_city(),m.getWtot_sbownmonth(),m.getWtot_gjjownmonth()).toString()							
						);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return row;
		}
		
		//插入mode
		public Integer Wtfeeachangedd(wtoutFeeModel m) {
			dbconn db = new dbconn();
			Integer row = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dates=sdf.format(m.getWtot_comfdate());
			try {
				row = Integer.parseInt(db.callWithReturn(
						"{?=call wtoutFeechange_ADD_p_zmj" +
						"(?,?,?,?,?,?,?," +
						"?,?,?,?,?,?)}",
						Types.INTEGER,
						m.getWtot_feeid(),
						m.getCid(),
						m.getWtot_feetitle(),
						m.getWtot_fee(),
						m.getWtot_addname(),
						m.getWtot_examinenamekf(),
						m.getWtot_editname(),
						m.getWtot_remark(),
						m.getWtot_examinenameqg(),
						m.getWtot_state(),
						m.getCoba_id(),
						m.getWtss_city(),dates).toString()
						
						);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return row;
		}
		


		
		//变更状态
		public Integer Wtfeeupdate(wtoutFeeModel m) {
			dbconn db = new dbconn();
			Integer row = 0;

			try {
				row = Integer.parseInt(db.callWithReturn(
						"{?=call wtoutFee_update_p_zmj" +
						"(?,?,?,?,?,?,?," +
						"?,?,?,?,?,?)}",
						Types.INTEGER,
						m.getWtss_id(),
						m.getWtot_feeid(),
						m.getWtot_fee(),
						m.getWtot_examinenamekf(),
						m.getWtot_editname(),
						m.getWtot_remark(),
						m.getWtot_backremark(),
						m.getWtot_examinenameqg(),
						m.getWtot_state(),
						m.getCoba_id(),
						m.getWtss_city(),m.getWtot_sbownmonth(),m.getWtot_gjjownmonth()).toString()							
						);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return row;
		}
		
		
		//变更状态
		public Integer Wtfeechangeupdate(wtoutFeeModel m) {
			dbconn db = new dbconn();
			Integer row = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dates=sdf.format(m.getWtot_comfdate());
			try {
				row = Integer.parseInt(db.callWithReturn(
						"{?=call wtoutFeechange_update_p_zmj" +
						"(?,?,?,?,?,?,?," +
						"?,?,?,?,?,?)}",
						Types.INTEGER,
						m.getWtot_feechangeid(),
						m.getWtot_feeid(),
						m.getWtot_fee(),
						m.getWtot_examinenamekf(),
						m.getWtot_editname(),
						m.getWtot_remark(),
						m.getWtot_backremark(),
						m.getWtot_examinenameqg(),
						m.getWtot_state(),
						m.getWtot_ifview(),
						m.getCoba_id(),
						m.getWtss_city(),dates).toString()							
						);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return row;
		}
		
			
		//查询获取model
		public Integer Wtfeelistselect(wtoutFeeModel m)
		{
			dbconn db = new dbconn();
			return null;
			
		}
		
		//删除
		public Integer WtServiceStandarddelete(wtoutFeeModel m)
		{
			return null;
			
		}
		
 
		
		//更新任务单状态
		public boolean UpdateTaprid(int daid, int taprid) {
			dbconn db = new dbconn();
			PreparedStatement psmt = null;
			int row = 0;
			String sql = "update wtoutFee set wtot_tapr_id=? where Wtot_feeid=?";

			try {
				psmt = db.getpre(sql);

				psmt.setInt(1, taprid);
				psmt.setInt(2, daid);
				row = psmt.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return row > 0 ? true : false;
		}
		//更新任务单状态
		public boolean UpdatechangeTaprid(int daid, int taprid) {
			dbconn db = new dbconn();
			PreparedStatement psmt = null;
			int row = 0;
			String sql = "update wtoutFeechange set wtot_tapr_id=? where Wtot_feechangeid=?";

			try {
				psmt = db.getpre(sql);

				psmt.setInt(1, taprid);
				psmt.setInt(2, daid);
				row = psmt.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return row > 0 ? true : false;
		}
		}
		 
