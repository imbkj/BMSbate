package dal.EmCensus;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;

import Conn.dbconn;
import Model.EmCensusModel;
import Model.EmDhModel;
import Model.EmHJBorrowCardModel;

public class EmCensus_OperateDal {
	//户口新增
	public Integer EmCensusAdd(EmCensusModel m) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
				.parseInt(db.callWithReturn(
					"{?=call EmCensus_Add_cyj(?,?,?,?,?,?,?,?,?,?,?)}",
					Types.INTEGER, m.getGid(), m.getCid(),m.getEmhj_name(),m.getEmhj_idcard(),
					m.getEmhj_addname(),timechange(m.getEmhj_addtime()),m.getEmhj_type(),m.getEmhj_in_class(),
					m.getEmhj_case(),m.getEmhj_place(),m.getEmhj_remark()).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return i;
		}
	
	//户口新增(调户后)
	public Integer EmCensusInfoAdd(EmCensusModel m) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
			.parseInt(db.callWithReturn(
				"{?=call EmCensusInfo_Add_cyj(?,?,?,?,?,?,?,?)}",
				Types.INTEGER, m.getGid(), m.getCid(),m.getEmhj_name(),m.getEmhj_idcard(),
				m.getEmhj_addname(),m.getEmhj_type(),m.getEmhj_in_class(),m.getEmhj_intype()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	//更新落户申请信息
	public int UpdateEmCensusInfo(EmCensusModel model,String str){
		int k=0;
		try {
			dbconn db = new dbconn();
			java.util.Date now=new java.util.Date();
			String sql = "update EmCensus set emhj_lasttime='"+timechange(now)+"'";
				   sql=sql+str;
				   sql=sql+" where emhj_id="+model.getEmhj_id();
				   k=db.execQuery(sql);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return k;
		}
	
	
	///更新档案业务表的任务单id
	public boolean updateTaprid(int id, int taprid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmCensus set emhj_taprid=? where emhj_id=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, taprid);
			psmt.setInt(2, id);
			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}
	
	//新增借卡
	public Integer EmHJBorrowCardAdd(EmHJBorrowCardModel model) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
			.parseInt(db.callWithReturn(
			"{?=call EmHJBorrowCard_ADD_CYJ(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				Types.INTEGER, model.getEhbc_grhk(),model.getEhbc_sy(),model.getEhbc_syfy(),model.getEhbc_outime(),
				model.getEhbc_backtime(),model.getEhbc_handin_name(),model.getEhbc_tableid(),model.getEhbc_class(),
				model.getEhbc_fee(),model.getEhbc_remark(),model.getEhbc_state(),model.getEhbc_wtname(),
				model.getEhbc_wtidcard(),model.getEhbc_wtfeetype(),model.getEhbc_tablechid(),model.getEhbc_case()).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		return i;
	}
	
	//更新借卡信息表
	public int UpdateEmHJBorrowCardInfo(EmHJBorrowCardModel model,String str){
		int k=0;
		try {
			dbconn db = new dbconn();
			java.util.Date now=new java.util.Date();
			String sql = "update EmHJBorrowCard set ehbc_lasttime='"+timechange(now)+"'";
				   sql=sql+str;
				   sql=sql+" where ehbc_id="+model.getEhbc_id();
				   k=db.execQuery(sql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return k;
	}
	
	//更新借卡表的任务单id
	public boolean updateborrowTaprid(int id, int taprid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmHJBorrowCard set ehbc_tarpid=? where ehbc_id=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, taprid);
			psmt.setInt(2, id);
			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}
	
	//时间格式转换
	private Date timechange(java.util.Date d)
	{
		Date da=null;
		if(d!=null&&!d.equals(""))
		{
			java.sql.Date date=new java.sql.Date(d.getTime());
			da=date;
		}
		return da;
	}
}
