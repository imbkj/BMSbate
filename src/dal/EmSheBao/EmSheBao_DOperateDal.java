/*
 * 创建人：林少斌
 * 创建时间：2014-1-2
 * 用途：社保变更数据处理Dal
 */
package dal.EmSheBao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import Conn.dbconn;

import Model.EmSheBaoChangeModel;
import Model.EmShebaoBJModel;
import Model.EmShebaoChangeForeignerModel;
import Model.EmShebaoChangeSZSIModel;
import Model.EmShebaoDeclareOKModel;
import Model.EmShebaoErrModel;
import Model.EmShebaoSZSIFileModel;
import Model.EmShebaoUpdateModel;
import Util.UserInfo;

public class EmSheBao_DOperateDal {
	private static dbconn conn = new dbconn();

	// 社保变更数据申报
	public int declareSingle(EmSheBaoChangeModel m) {
		try {

			CallableStatement c = conn
					.getcall("EMSI_DeclareSingle_P_lsb(?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsc_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 外籍人新参保数据申报
	public int declareForSingle(EmShebaoChangeForeignerModel m) {
		try {

			CallableStatement c = conn
					.getcall("EMSI_DeclareForSingle_P_lsb(?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsc_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 外籍人新参保数据申报成功
	public int declareForSingleSuccess(EmShebaoChangeForeignerModel m) {
		try {

			CallableStatement c = conn
					.getcall("EMSI_DeclareForSuccess_P_lsb(?,?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsc_computerid());
			c.setString(3, m.getEmsc_addname());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保外籍人新参保批量申报(外籍人新参保)
	public int declareForSingleExcel(EmShebaoChangeForeignerModel m) {
		try {

			CallableStatement c = conn
					.getcall("EMSI_DeclareForSingleExcel_P_lsb(?,?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsc_excelfile());
			c.setString(3, m.getEmsc_addname());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保交单变更数据申报
	public int declareSZSISingle(EmShebaoChangeSZSIModel m) {
		try {

			CallableStatement c = conn
					.getcall("EMSI_DeclareSZSISingle_P_lsb(?,?,?)");
			c.setInt(1, m.getEscs_id());
			c.setString(2, m.getEscs_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保交单变更数据福利上传PDF文件
	public int declareSZSIUpload(EmShebaoChangeSZSIModel m) {
		try {

			CallableStatement c = conn
					.getcall("EMSI_DeclareSZSIUpload_P_lsb(?,?,?,?)");
			c.setInt(1, m.getEscs_id());
			c.setString(2, m.getEscs_uploadfile());
			c.setString(3, m.getEscs_addname());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保交单变更数据客服下载PDF文件
	public int declareSZSIDownload(EmShebaoChangeSZSIModel m) {
		String sql = "UPDATE EmShebaoChangeSZSI SET escs_ifdeclare=7 where escs_ifdeclare=2 AND escs_id="
				+ m.getEscs_id();
		try {
			System.out.println(sql);
			PreparedStatement psmt = conn.getpre(sql);
			psmt.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 社保交单变更数据返回中心收集材料
	public int declareSZSIBackTOCenter(EmShebaoChangeSZSIModel m) {
		String sql = "UPDATE EmShebaoChangeSZSI SET escs_ifnet=0,escs_ifdeclare="
				+ m.getEscs_ifdeclare()
				+ " where escs_ifdeclare=8 and escs_ifnet=1 AND escs_id="
				+ m.getEscs_id();
		try {
			System.out.println(sql);
			PreparedStatement psmt = conn.getpre(sql);
			psmt.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 社保交单变更数据申报成功
	public int declareSZSISingleSuccess(EmShebaoChangeSZSIModel m) {
		try {

			CallableStatement c = conn
					.getcall("EMSI_DeclareSZSISuccess_P_lsb(?,?,?)");
			c.setInt(1, m.getEscs_id());
			c.setString(2, m.getEscs_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保变更数据申报(生成单项模板)
	public int declareSingleExcel(EmSheBaoChangeModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_DeclareSingleExcel_P_lsb(?,?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsc_excelfile());
			c.setString(3, m.getEmsc_addname());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 撤销社保申报
	public int declareCancel(EmSheBaoChangeModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_DeclareCancel_P_lsb(?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsc_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 撤销社保申报(外籍人)
	public int declareForCancel(EmShebaoChangeForeignerModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_DeclareForCancel_P_lsb(?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsc_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 退回社保申报
	public int declareReturn(EmSheBaoChangeModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_DeclareReturn_P_lsb(?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsc_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 退回社保外籍人新参保申报(外籍人)
	public int declareForReturn(EmShebaoChangeForeignerModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_DeclareForReturn_P_lsb(?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsc_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 退回社保交单变更申报(交单)
	public int declareSZSIReturn(EmShebaoChangeSZSIModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_DeclareSZSIReturn_P_lsb(?,?,?)");
			c.setInt(1, m.getEscs_id());
			c.setString(2, m.getEscs_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保申报变为未申报
	public int declareFirstStep(EmSheBaoChangeModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_DeclareFirstStep_P_lsb(?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsc_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保外籍人新参保变为未申报(外籍人)
	public int declareForFirstStep(EmShebaoChangeForeignerModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_DeclareForFirstStep_P_lsb(?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsc_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保社保交单变更变为未申报(交单)
	public int declareSZSIFirstStep(EmShebaoChangeSZSIModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_DeclareSZSIFirstStep_P_lsb(?,?,?)");
			c.setInt(1, m.getEscs_id());
			c.setString(2, m.getEscs_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 变更数据手动申报
	public int declareChangeState(EmSheBaoChangeModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_DeclareChangeState_P_lsb(?,?,?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsc_ifdeclare());
			c.setString(3, m.getEmsc_addname());
			c.setString(4, m.getEmsc_flag());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 退回变更数据并终止任务单
	public int returnStopChange(EmSheBaoChangeModel m) {
		try {

			CallableStatement c = conn.getcall("WfCoreStopTask_P_lwj(?,?,?,?)");
			c.setInt(1, m.getEmsc_tapr_id());
			c.setString(2, UserInfo.getUsername());
			c.setString(3, m.getEmsc_remark());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();

			// 更新数据状态
			if (c.getInt(4) == 1) {
				m.setEmsc_ifdeclare(String.valueOf(3));
				return updateDeclare(m);
			} else {
				return 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 更新社保变更数据
	public int updateDeclare(EmSheBaoChangeModel m) {
		int result;
		String sql = "update EmSheBaoChange SET emsc_ifdeclare="
				+ m.getEmsc_ifdeclare()
				+ ",emsc_DeclareTime=GETDATE(),emsc_DeclareName='"
				+ UserInfo.getUsername() + "' where id=" + m.getId();
		try {

			conn.execQuery(sql);
			result = 1;

		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}

		return result;
	}

	// 社保变更数据添加备忘
	public int declareAddFlag(EmSheBaoChangeModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_DeclareAddFlag_P_lsb(?,?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsc_flagname());
			c.setString(3, m.getEmsc_flag());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保交单变更数据添加备忘
	public int declareSZSIAddFlag(EmShebaoChangeSZSIModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_DeclareSZSIAddFlag_P_lsb(?,?,?,?)");
			c.setInt(1, m.getEscs_id());
			c.setString(2, m.getEscs_flagname());
			c.setString(3, m.getEscs_flag());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保变更变更为已申报
	public int[] declareSuccess(EmShebaoDeclareOKModel m) {
		int[] result = { 0, 0 };
		try {
			System.out.println(m.getId());
			System.out.println(m.getEsdo_addname());
			CallableStatement c = conn
					.getcall("EMSI_DeclareSuccess_P_lsb(?,?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEsdo_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			result[0] = c.getInt(3);
			result[1] = c.getInt(4);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return result;
		}
	}

	// 插入社保已审核的文件
	public int uploadDeclareOK(EmShebaoDeclareOKModel m) {
		int result;

		String sql = "insert into EmShebaoDeclareOK (ownmonth,esdo_name,esdo_computerid,esdo_idcard,esdo_hj,esdo_radix,esdo_yl,esdo_yltype,esdo_gs,esdo_sye,esdo_oktime,esdo_class,esdo_addtime,esdo_addname,esdo_filename) values("
				+ m.getOwnmonth()
				+ ",'"
				+ m.getEsdo_name()
				+ "','"
				+ m.getEsdo_computerid()
				+ "','"
				+ m.getEsdo_idcard()
				+ "','"
				+ m.getEsdo_hj()
				+ "',"
				+ m.getEsdo_radix()
				+ ",'"
				+ m.getEsdo_yl()
				+ "','"
				+ m.getEsdo_yltype()
				+ "','"
				+ m.getEsdo_gs()
				+ "','"
				+ m.getEsdo_sye()
				+ "','"
				+ m.getEsdo_oktime()
				+ "','"
				+ m.getEsdo_class()
				+ "',getdate(),'"
				+ m.getEsdo_addname()
				+ "','"
				+ m.getEsdo_filename() + "')";
		try {
			conn.execQuery(sql);
			result = 0;
		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	/*
	 * public int uploadDeclareOK(String sheetname, String filename, String
	 * ownmonth, String username, String fileAllname) { int result;
	 * 
	 * String sql =
	 * "insert into EmShebaoDeclareOK (ownmonth,esdo_name,esdo_computerid,esdo_idcard,esdo_hj,esdo_radix,esdo_yl,esdo_yltype,esdo_gs,esdo_sye,esdo_oktime,esdo_class,esdo_addtime,esdo_addname,esdo_filename) select '"
	 * + ownmonth +
	 * "',[姓名],[电脑号],[身份证号],[户口],[工资],[养老],[医疗],[工伤],[失业],[提交日期],[处理类型],getdate(),'"
	 * + username + "','" + filename +
	 * "' from opendatasource ('Microsoft.ACE.OLEDB.12.0','data source=" +
	 * fileAllname + ";Extended properties=excel 5.0;')...[" + sheetname +
	 * "$] where [序号] is not null"; try { System.out.println(sheetname);
	 * System.out.println(filename); System.out.println(fileAllname);
	 * System.out.println(sql); conn.execQuery(sql); result = 0;
	 * 
	 * updateDeclareOK(filename);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); result = 1; }
	 * 
	 * return result; }
	 */

	// 插入更新导入系统的社保已审核的文件的数据
	public int updateDeclareOK(String filename) {
		int result;
		String sql = "update EmShebaoDeclareOK set  esdo_yltype=case esdo_yltype when '基本医疗保险一档' then '一档' when '基本医疗保险二档' then '二档' when '未参加' then '不参加' when '基本医疗保险三档' then '三档' else esdo_yltype end,esdo_hj=case esdo_hj when '深户' then '市内城镇' when '非深户城镇' then '市外城镇' when '非深户农村' then '市外农村' else esdo_hj end,esdo_yl=replace(esdo_yl,'未参加','不参加'),esdo_gs=replace(esdo_gs,'未参加','不参加'),esdo_sye=replace(esdo_sye,'未参加','不参加') where esdo_filename='"
				+ filename + "' ";
		try {

			conn.execQuery(sql);
			result = 0;

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	// 上传社保台账前数据
	public int uploadFinanceMonth(EmShebaoUpdateModel m, String filename) {
		int result;

		try {
			CallableStatement c = conn
					.getcall("EMSI_AddSZSIMonth_P_lsb(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getOwnmonth());
			c.setString(2, m.getEsiu_name());
			c.setString(3, m.getEsiu_computerid());
			c.setString(4, m.getEsiu_lbid());
			c.setString(5, m.getSex());
			c.setString(6, m.getEsiu_idcard());
			c.setString(7, m.getEsiu_hj());
			c.setInt(8, m.getEsiu_radix());
			c.setString(9, m.getEsiu_yl());
			c.setString(10, m.getEsiu_yltype());
			c.setString(11, m.getEsiu_gs());
			c.setString(12, m.getEsiu_sye());
			c.setString(13, m.getEsiu_syu());
			c.setString(14, filename);
			c.setInt(15, m.getEsiu_single());
			c.setString(16, UserInfo.getUsername());
			c.setString(17, "未处理");
			c.setInt(18, m.getEsiu_shebaoid());
			c.setString(19, m.getEsiu_company());
			c.registerOutParameter(20, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(20);

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	/*
	 * // 上传社保台账前数据(批量) public int uploadFinanceMonth(String sheetname, String
	 * filename, String ownmonth, String username, String fileAllname, String
	 * ifsingle, String shebaoid, String company) { int result;
	 * 
	 * String sql =
	 * "insert into EmShebaoSZSIMonth (ownmonth,name,computerid,lbid,sex,idcard,hj,radix,yl,yltype,gs,sye,syu,filename,single,addtime,addname,state,shebaoid,company) select '"
	 * + ownmonth +
	 * "',[姓名],[电脑号],[社会保障卡号],[性别],[身份证号],[户口],[工资],[养老],[医疗],[工伤],[失业],[生育],'"
	 * + filename + "','" + ifsingle + "',GETDATE(),'" + username + "','未处理','"
	 * + shebaoid + "','" + company +
	 * "' from opendatasource ('Microsoft.ACE.OLEDB.12.0','data source=" +
	 * fileAllname + ";Extended properties=excel 5.0;')...[" + sheetname +
	 * "$] where [生育] is not null";
	 * 
	 * try {
	 * 
	 * conn.execQuery(sql); result = 0;
	 * 
	 * updateFinanceMonth(filename);
	 * 
	 * updateFinanceMonthSP(filename);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); result = 1; }
	 * 
	 * return result; }
	 */
	// 更新导入系统的社保台账前的文件的数据
	public int updateFinanceMonth(String filename) {
		int result;
		String sql = "update EmShebaoSZSIMonth set yltype=case yltype when '基本医疗保险一档' then '一档' when '基本医疗保险二档' then '二档' when '未参加' then '不参加' when '基本医疗保险三档' then '三档' else yltype end,hj=case hj when '深户' then '市内城镇' when '非深户城镇' then '市外城镇' when '非深户农村' then '市外农村' else hj end,yl=replace(yl,'未参加','不参加'),gs=replace(gs,'未参加','不参加'),sye=replace(sye,'未参加','不参加') where filename='"
				+ filename + "' ";
		try {

			conn.execQuery(sql);
			result = 0;

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	/*
	 * // 上传社保台账后数据 public int uploadFinance(String sheetname1, String
	 * sheetname2, String filename1, String filename2, String ownmonth, String
	 * username, String fileAllname1, String fileAllname2, String ifsingle,
	 * String shebaoid, String company) { int result, result_szsifee;
	 * 
	 * try {
	 * 
	 * // 导入台账后单位正常在册人员名单 result_szsifee = uploadFinanceSZSIFee(sheetname1,
	 * filename1, fileAllname1); if (result_szsifee == 0) { // 导入台账后“五险合一”数据
	 * result = uploadFinanceSZSI(sheetname2, ownmonth, ifsingle, username,
	 * filename2, fileAllname2, shebaoid, company);
	 * 
	 * if (result_szsi == 0) { // 导入台账后补交数据 result_szsibj =
	 * uploadFinanceSZSIBJ(sheetname2, ownmonth, ifsingle, username, filename2,
	 * fileAllname2); result = result_szsibj; } else { result = 1; }
	 * 
	 * } else { result = 1; }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); result = 1; }
	 * 
	 * return result; }
	 */

	// 导入台账后单位正常在册人员名单
	public int uploadFinanceSZSIFee(EmShebaoUpdateModel m, String filename) {
		int result;

		try {
			CallableStatement c = conn
					.getcall("EMSI_AddSZSIFee_P_lsb(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getOwnmonth());
			c.setString(2, m.getEsiu_name());
			c.setString(3, m.getEsiu_computerid());
			c.setString(4, m.getEsiu_lbid());
			c.setString(5, m.getSex());
			c.setString(6, m.getEsiu_idcard());
			c.setString(7, m.getEsiu_hj());
			c.setInt(8, m.getEsiu_radix());
			c.setString(9, m.getEsiu_yl());
			c.setString(10, m.getEsiu_yltype());
			c.setString(11, m.getEsiu_gs());
			c.setString(12, m.getEsiu_sye());
			c.setString(13, m.getEsiu_syu());
			c.setString(14, filename);
			c.setInt(15, m.getEsiu_shebaoid());
			c.registerOutParameter(16, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(16);

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	/*
	 * // 导入台账后单位正常在册人员名单(批量) public int uploadFinanceSZSIFee(String sheetname,
	 * String filename, String fileAllname) { int result;
	 * 
	 * String sql =
	 * "insert into EmShebaoSZSIfee (name,computerid,lbid,sex,idcard,hj,radix,yl,yltype,gs,sye,syu,filename) select [姓名],[电脑号],[社会保障卡号],[性别],[身份证号],[户口],[工资],[养老],[医疗],[工伤],[失业],[生育],'"
	 * + filename +
	 * "' from opendatasource ('Microsoft.ACE.OLEDB.12.0','data source=" +
	 * fileAllname + ";Extended properties=excel 5.0;')...[" + sheetname +
	 * "$] where [生育] is not null"; //System.out.println(sql); try {
	 * 
	 * conn.execQuery(sql); result = 0;
	 * 
	 * } catch (Exception e) { e.printStackTrace(); result = 1; }
	 * 
	 * return result; }
	 */

	// 导入台账后“五险合一”数据
	public int uploadFinanceSZSI(EmShebaoUpdateModel m, String filename) {
		int result;

		try {
			CallableStatement c = conn
					.getcall("EMSI_AddSZSI_P_lsb(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getOwnmonth());
			c.setString(2, m.getEsiu_name());
			c.setString(3, m.getEsiu_computerid());
			c.setBigDecimal(4, m.getEsiu_total());
			c.setBigDecimal(5, m.getEsiu_totalop());
			c.setBigDecimal(6, m.getEsiu_totalcp());
			c.setBigDecimal(7, m.getEsiu_ylop());
			c.setBigDecimal(8, m.getEsiu_ylcp());
			c.setBigDecimal(9, m.getEsiu_syeop());
			c.setBigDecimal(10, m.getEsiu_jlop());
			c.setBigDecimal(11, m.getEsiu_jlcp());
			c.setBigDecimal(12, m.getEsiu_gscp());
			c.setBigDecimal(13, m.getEsiu_syecp());
			c.setBigDecimal(14, m.getEsiu_syucp());
			c.setString(15, filename);
			c.setInt(16, m.getEsiu_single());
			c.setString(17, UserInfo.getUsername());
			c.setString(18, "未处理");
			c.setInt(19, m.getEsiu_shebaoid());
			c.setString(20, m.getEsiu_company());
			c.registerOutParameter(21, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(21);

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	/*
	 * // 导入台账后“五险合一”数据(批量) public int uploadFinanceSZSI(String sheetname,
	 * String ownmonth, String ifsingle, String username, String filename,
	 * String fileAllname, String shebaoid, String company) { int result;
	 * 
	 * String sql =
	 * "insert into EmShebaoSZSI (ownmonth,computerid,name,total,totalop,totalcp,ylop,ylcp,syeop,jlop,jlcp,gscp,syecp,syucp,filename,single,addtime,addname,state,shebaoid,company) select '"
	 * + ownmonth +
	 * "',[个人编号],[姓名],cast([应收合计] as smallmoney),cast([个人合计] as smallmoney),cast([单位合计] as smallmoney),cast([养老个人交] as smallmoney),cast([养老单位交] as smallmoney),cast([失业个人交] as smallmoney),cast([医疗个人交] as smallmoney),cast([医疗单位交] as smallmoney),cast([工伤单位交] as smallmoney),cast([失业单位交] as smallmoney),cast([生育单位交] as smallmoney),'"
	 * + filename + "','" + ifsingle + "',getdate(),'" + username + "','未处理','"
	 * + shebaoid + "','" + company +
	 * "' from opendatasource ('Microsoft.ACE.OLEDB.12.0','data source=" +
	 * fileAllname + ";Extended properties=excel 5.0;')...[" + sheetname +
	 * "$] where [工伤保险基数] is not null"; //System.out.println(sql); try {
	 * 
	 * conn.execQuery(sql); result = 0;
	 * 
	 * } catch (Exception e) { e.printStackTrace(); result = 1; }
	 * 
	 * return result; }
	 */

	// 导入台账后补交数据
	public int uploadFinanceSZSIBJ(EmShebaoBJModel m, String filename) {
		int result;

		try {
			CallableStatement c = conn
					.getcall("EMSI_AddSZSIbj_P_lsb(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getOwnmonth());
			c.setString(2, m.getEmsb_name());
			c.setString(3, m.getEmsb_computerid());
			c.setString(4, m.getEmsb_m1());
			c.setBigDecimal(5, m.getEmsb_total());
			c.setBigDecimal(6, m.getEssb_zh());
			c.setBigDecimal(7, m.getEssb_gj());
			c.setBigDecimal(8, m.getEmsb_totalop());
			c.setBigDecimal(9, m.getEmsb_totalcp());
			c.setString(10, filename);
			c.setInt(11, m.getEmsb_single());
			c.setString(12, UserInfo.getUsername());
			c.registerOutParameter(13, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(13);

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	/*
	 * // 导入台账后补交数据(批量) public int uploadFinanceSZSIBJ(String sheetname, String
	 * ownmonth, String ifsingle, String username, String filename, String
	 * fileAllname) { int result;
	 * 
	 * String sql =
	 * "insert into EmShebaoSZSIBJ (ownmonth,essb_computerid,essb_name,essb_month,essb_total,essb_zh,essb_gj,essb_totalop,essb_totalcp,essb_single,essb_addtime,essb_addname,essb_filename) select "
	 * + ownmonth +
	 * ",[个人编号],[姓名],[起始年月],cast([补交总金额] as smallmoney),cast([补交专户] as smallmoney),cast([补交总共济] as smallmoney),cast([个人交] as smallmoney),cast([单位交] as smallmoney),'"
	 * + ifsingle + "',GETDATE(),'" + username + "','" + filename +
	 * "' from opendatasource ('Microsoft.ACE.OLEDB.12.0','data source=" +
	 * fileAllname + ";Extended properties=excel 5.0;')...[" + sheetname +
	 * "$] where [序号] is not null"; // System.out.println(sql); try {
	 * 
	 * conn.execQuery(sql); result = 0;
	 * 
	 * } catch (Exception e) { e.printStackTrace(); result = 1; }
	 * 
	 * return result; }
	 */

	// 更新导入系统的社保台账前的文件的数据
	public int updateFinanceMonthSP(String filename) {
		try {
			CallableStatement c = conn.getcall("EMSI_FeeExcelinMonth_P_lsb(?)");
			c.setString(1, filename);
			c.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 更新导入系统的社保台账后的文件的数据
	public int updateFinanceSP(String filename1, String filename2) {
		try {
			CallableStatement c = conn.getcall("EMSI_FeeExcelin_P_lsb(?,?)");
			c.setString(1, filename1);
			c.setString(2, filename2);
			c.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 记录台账后上传文件
	public int addShebaoSZSIFile(EmShebaoSZSIFileModel m) {
		try {

			CallableStatement c = conn
					.getcall("EMSI_SZSIFileAdd_P_lsb(?,?,?,?,?,?)");
			c.setString(1, m.getEssf_shebaoid());
			c.setInt(2, m.getEssf_type());
			c.setString(3, m.getEssf_filename());
			c.setString(4, m.getEssf_fileurl());
			c.setString(5, m.getEssf_addname());
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 删除所有台账前数据
	public int deleteSZSIMonth() {
		int result;
		String sql = "delete from EmShebaoSZSIMonth";
		try {

			conn.execQuery(sql);
			result = 0;

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	// 删除所有台账后数据
	public int deleteSZSI() {
		int result;
		String sql = "delete from EmShebaoSZSI";
		try {

			conn.execQuery(sql);
			result = 0;

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	// 删除台账前数据
	public int deleteSZSIMonth(String shebaoid) {
		int result;
		String sql = "delete from EmShebaoSZSIMonth where shebaoid=" + shebaoid;
		try {

			conn.execQuery(sql);
			result = 0;

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	// 删除台账后数据
	public int deleteSZSI(String shebaoid) {
		int result;
		String sql = "delete from EmShebaoSZSI where shebaoid=" + shebaoid;
		try {

			conn.execQuery(sql);
			result = 0;

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	// 台账前逻辑检查
	public int chkErrSZSIMonth(String username) {
		try {
			CallableStatement c = conn.getcall("EMSI_ShebaoErrMonth_P_lsb(?)");
			c.setString(1, username);
			c.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 台账后逻辑检查
	public int chkErrSZSI(String username) {
		try {
			CallableStatement c = conn.getcall("EMSI_ShebaoErr_P_lsb(?)");
			c.setString(1, username);
			c.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 删除台账前逻辑检查
	public int delErrSZSIMonth(String ownmonth) {
		int result;
		String sql = "delete from EmShebaoErrMonth where ownmonth=" + ownmonth;
		try {

			conn.execQuery(sql);
			result = 0;

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	// 删除台账后逻辑检查
	public int delErrSZSI(String ownmonth) {
		int result;
		String sql = "delete from EmShebaoErr where ownmonth=" + ownmonth;
		try {

			conn.execQuery(sql);
			result = 0;

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	// 根据社保编号检查是否已有上传的台账
	public boolean existsSZSIByShebaoID(int shebaoid) {
		boolean b = false;
		String sql = "select cou=count(distinct shebaoid) from EmShebaoSZSI where shebaoid=? and shebaoid<>167120";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, shebaoid);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			if (rs.getInt("cou") == 0) {
				b = true;
			}
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		return b;
	}

	// 删除社保台账后导入数据
	public int deleteSZSIFinance(int shebaoid) {
		int row = 0;
		String sql = " DELETE EmShebaoSZSI WHERE shebaoid=?";
		Object[] objs = { shebaoid };
		try {
			row = conn.updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	// 删除社保台账前导入数据
	public int deleteSZSIFinanceMonth(int shebaoid) {
		int row = 0;
		String sql = " DELETE EmShebaoSZSIMonth WHERE shebaoid=?";
		Object[] objs = { shebaoid };
		try {
			row = conn.updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	// 处理逻辑检查数据(台后)
	public int declareSheBaoErr(EmShebaoErrModel m) {
		try {
			CallableStatement c = conn
					.getcall("EmSheBaoErrDeclare_P_lsb(?,?,?,?,?,?,?)");

			c.setInt(1, m.getId());
			c.setInt(2, m.getEmse_Normal());
			c.setString(3, m.getEmse_Note());
			c.setString(4, m.getEmse_Solve());
			c.setString(5, m.getEmse_Remark());
			c.setString(6, UserInfo.getUsername());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 处理逻辑检查数据(台前)
	public int declareSheBaoMonthErr(EmShebaoErrModel m) {
		try {
			CallableStatement c = conn
					.getcall("EmSheBaoMonthErrDeclare_P_lsb(?,?,?,?,?,?,?)");

			c.setInt(1, m.getId());
			c.setInt(2, m.getEmse_Normal());
			c.setString(3, m.getEmse_Note());
			c.setString(4, m.getEmse_Solve());
			c.setString(5, m.getEmse_Remark());
			c.setString(6, UserInfo.getUsername());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 处理调走数据
	public int updateSheBaoIfstop(Integer gid) {
		int result;
		String sql = "update emshebaoupdate set esiu_ifstop=2 where gid=" + gid;
		try {

			conn.execQuery(sql);
			result = 0;

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}

	// 处理大学生医疗
	public int updateSheBaoyltype(Integer gid) {
		int result;
		String sql = "update EmShebaoSZSI set yltype='大学生' where gid=" + gid;
		try {

			conn.execQuery(sql);
			result = 0;

		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
		}

		return result;
	}
}
