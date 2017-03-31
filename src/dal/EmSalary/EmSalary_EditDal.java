package dal.EmSalary;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Util.UserInfo;

import Conn.dbconn;

public class EmSalary_EditDal {
	private static dbconn conn = new dbconn();
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
	
	
	public int checkstate(int d_id)
	{
		int i=0;
		String sql = "select COUNT(*) as count from EmSalaryData where EmSalaryData.esda_id in(SELECT tbrb_data_id FROM TaskBatchRel_view WHERE taba_id = "+d_id+") and esda_payment_state=0";
		try {
			ResultSet rs = null;
			rs = conn.GRS(sql.toString());
			
			while (rs.next()) {
				
				i=rs.getInt("count");
		
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return i;
			
	}
	
	
	public boolean updateTaprid(int daid, int taprid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update TaskBatch set taba_tapr_id=? where taba_id=?";

		try {
			psmt = db.getpre(sql);

			psmt.setInt(1, taprid);
			psmt.setInt(2, daid);
			row = psmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}
	
	//审核工资
		public int audit(int esda_id) throws SQLException {
			
			int row=0;
			String sql="UPDATE EmSalaryData SET esda_payment_state=1  WHERE esda_id="+esda_id+"";
			System.out.println(sql);
			dbconn update = new dbconn();
			try {
					row = update.execQuery(sql);
			} 
			catch (Exception e) {
				System.out.println(e.toString());
			} 
				update.Close();
						
				return row;

		}
		
		//发放工资
		public int pay(int esda_id,String txt_date) throws SQLException {
			
			/*java.util.Date datetime=new java.util.Date();
			java.sql.Timestamp time=new java.sql.Timestamp(datetime.getTime());
			
			int row=0;
			String sql="UPDATE EmSalaryData SET esda_payment_state=2,esda_payment_date='"+time+"'  WHERE esda_id="+esda_id+"";
			
			String sql1="EmSalaryData_TXTAdd_P_lsb  " +esda_id+","+UserInfo.getUsername()+",0" ;
			
			
			System.out.println(sql);
			dbconn update = new dbconn();
			try {
					row = update.execQuery(sql);
					update.execQuery(sql1);
			} 
			catch (Exception e) {
				System.out.println(e.toString());
			} 
				update.Close();
						
				return row;*/
			
			try {	
				CallableStatement c = conn
						.getcall("EmSalaryData_TXTAdd_P_lsb(?,?,?,?)");
				c.setInt(1, esda_id);
				c.setString(2, UserInfo.getUsername());
				c.setString(3, txt_date);
				c.registerOutParameter(4, java.sql.Types.INTEGER);
				c.execute();

				if(c.getInt(4)==0){
					return 1;
				}
				else{
					return 0;
				}
			} catch (SQLException e) {
				return 0;
			}

		}
		
		//重发工资
		public int repay(int esda_id) throws SQLException {
			
			int row=0;
			String sql="UPDATE EmSalaryData SET esda_payment_state=3  WHERE esda_id="+esda_id+"";
			System.out.println(sql);
			dbconn update = new dbconn();
			try {
					row = update.execQuery(sql);
			} 
			catch (Exception e) {
				System.out.println(e.toString());
			} 
				update.Close();
						
				return row;

		}
		
		//获取电子工资单待发送邮件,gid,title
		
		
		public String[] getemailinfo(int esda_id)
		{
			String[] emailinfo = new String[8];
			
			
			String sql = "select cb.coba_gzemailtype, eb.emba_name,eb.emba_gz_email,eb.emba_gz_cemail,ed.* from  (select * from EmSalaryData where EmSalaryData.esda_id="+esda_id+" )  ed " +
					"left join EmBase eb on ed.gid=eb.gid left join cobase cb on cb.cid=eb.cid ";
			System.out.println(sql);
			try {
				ResultSet rs = null;
				rs = conn.GRS(sql.toString());
				
				while (rs.next()) {
					
					emailinfo[0]=rs.getInt("cid")+"";
					emailinfo[1]=rs.getInt("gid")+"";
					emailinfo[2]=rs.getString("emba_gz_email");
					emailinfo[3]=rs.getString("emba_gz_cemail");
					emailinfo[4]="您好,"+rs.getInt("ownmonth")+"email工资单";
					emailinfo[5]=rs.getString("emba_gz_cemail");
					emailinfo[6]=rs.getString("coba_gzemailtype");
					emailinfo[7]=rs.getInt("ownmonth")+"";
					
					}

			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			return emailinfo;
			
		}
		//获取工资邮件正文
		public String getemailcontenct(int cid,int esda_id,int ownmonth,int ifhtml)
		{
			java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
			StringBuilder emailcontenct = new StringBuilder();
			String sql1="SELECT csii_col,csii_item_name FROM View_CSII_CSIIDI WHERE csii_col='esda_pay' AND cid="+cid+" AND ownmonth="+ownmonth+"";
			
			String sql2="SELECT csii_id,csii_col,csii_item_name,ciin_id FROM View_CSII_CSIIDI WHERE csii_col<>'esda_pay' and csii_csd_state=1 AND cid="+cid+" AND ownmonth="+ownmonth+"";
			String sql = "select eb.cid, eb.emba_name,eb.emba_gz_email,eb.emba_gz_cemail,ed.* from  (select * from EmSalaryData where EmSalaryData.esda_id="+esda_id+" )  ed " +
					"left join EmBase eb on ed.gid=eb.gid ";
			System.out.println(sql1);
			System.out.println(sql);
			System.out.println(sql2);
			try {
				ResultSet rs = null;
				ResultSet rs1 = null;
				ResultSet rs2 = null;
				rs = conn.GRS(sql.toString());
				rs1 = conn.GRS(sql1.toString());
				rs2 = conn.GRS(sql2.toString());
				
				while (rs.next()) {
					
					if (ifhtml==0)
					{
						emailcontenct.append(rs.getString("emba_name"));
						emailcontenct.append("您好！以下是您-");
						emailcontenct.append(rs.getInt("ownmonth"));
						emailcontenct.append("-月份的工资明细:");
//						emailcontenct.append(rs2.getString("csii_item_name"));
//						emailcontenct.append(":");
//						emailcontenct.append(rs.getString(rs2.getString("csii_item_name")));
					int i=2;
					while (rs2.next()) {
						emailcontenct.append(rs2.getString("csii_item_name"));
						emailcontenct.append(":");
						emailcontenct.append(rs.getString(rs2.getString("csii_col")).substring(0, rs.getString(rs2.getString("csii_col")).length()-2));
						emailcontenct.append("|");
					}
					while (rs1.next()) {
						emailcontenct.append(rs1.getString("csii_item_name"));
						emailcontenct.append(rs.getString(rs1.getString("csii_col")).substring(0, rs.getString(rs1.getString("csii_col")).length()-2));
						emailcontenct.append("");//？remark
					}
					
					}
					if(ifhtml==1)
					{
					emailcontenct.append("<br />");
					emailcontenct.append(rs.getString("emba_name"));
					emailcontenct.append("您好！以下是您-");
					emailcontenct.append(rs.getInt("ownmonth"));
					emailcontenct.append("-月份的工资明细:");
					emailcontenct.append("<br />");
					emailcontenct.append("<table border=\"0\" cellpadding=\"6\" cellspacing=\"1\" bgcolor=\"#000000\"><tr><td align=\"center\" colspan=\"4\" bgcolor=\"#FFFFFF\">");
					emailcontenct.append(rs.getInt("ownmonth"));
					emailcontenct.append("月工资单(PRIVATE & CONFIDENTIAL)</td></tr>");
					emailcontenct.append("<tr><td align=\"left\" bgcolor=\"#FFFFFF\">姓名(Name)</td><td align=\"left\" colspan=\"3\" bgcolor=\"#FFFFFF\">");
					emailcontenct.append(rs.getString("emba_name"));
					emailcontenct.append("</td></tr>");
					emailcontenct.append("<br />");
					

				int i=2;
				while (rs2.next()) {
					i=i+1;
				if ((rs2.getRow()+2)-i!=0) {
					emailcontenct.append("<tr><td align=\"left\" bgcolor=\"#FFFFFF\">");
					emailcontenct.append(rs2.getString("csii_item_name"));
					emailcontenct.append("</td><td align=\"left\" bgcolor=\"#FFFFFF\">");
					emailcontenct.append(rs.getString(rs2.getString("csii_col")).substring(0, rs.getString(rs2.getString("csii_col")).length()-2));
				
					if (i%2==1)
					{
						emailcontenct.append("</td>");
					
					}
					if(i%2==0)
					{
						emailcontenct.append("</td></tr>");
					}
								
				} 
				else
				{
					emailcontenct.append("<tr><td align=\"left\" bgcolor=\"#FFFFFF\">");
					emailcontenct.append(rs2.getString("csii_item_name"));
					if (i%2==1)
					{
						emailcontenct.append("</td><td colspan=\"3\" align=\"left\" bgcolor=\"#FFFFFF\">");
					}
					if(i%2==0)
					{
						emailcontenct.append("</td><td align=\"left\" bgcolor=\"#FFFFFF\">");
						
					}
					//emailcontenct.append(rs.getString(rs2.getString("csii_col")));
					emailcontenct.append(rs.getString(rs2.getString("csii_col")).substring(0, rs.getString(rs2.getString("csii_col")).length()-2));
					emailcontenct.append("</td></tr>");
				}
				
				}
				while (rs1.next()) {
					emailcontenct.append("<tr><td align=\"left\" bgcolor=\"#FFFFFF\"><b>");
					emailcontenct.append(rs1.getString("csii_item_name"));
					emailcontenct.append("</b></td><td align=\"left\" colspan=\"3\" bgcolor=\"#FFFFFF\"><b>");
					emailcontenct.append(rs.getString(rs1.getString("csii_col")).substring(0, rs.getString(rs1.getString("csii_col")).length()-2));
					emailcontenct.append("</b></td></tr></table><br />");
					emailcontenct.append("");//？remark
				
				}
				
				}
				}

			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			return emailcontenct.toString();
			
		}
		
	
		//获取工资项目数据
//		public String getSalaryinfomoney(String col,int esda_id)
//		{
//			//EmSalaryData
//			String moneydate="";
//			String sql = "select "+col+" from EmSalaryData where esda_id="+esda_id+" ";
//			try {
//				ResultSet rs = null;
//				rs = conn.GRS(sql.toString());
//				
//				while (rs.next()) {
//					
//					moneydate=rs.getString(col);
//			
//				}
//
//			} catch (Exception e) {
//				System.out.println(e.toString());
//			}
//			return moneydate;
//			
//		}

}
