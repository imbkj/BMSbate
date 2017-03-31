package bll.SystemControl;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Executions;

import dal.SystemControl.SystLogDal;
import Model.SystLogModel;
import Util.GetIpUtil;

/**
 * 
 * @author suhongyua 系统日志操作
 */
public class SystLogInfoBll {
	// 添加系统日志
	SystLogDal dal = new SystLogDal();

	public int addSystLog(SystLogModel m) throws SQLException {
		return dal.addSystLogInfo(m);
	}

	public List<SystLogModel> getSystLogList(String str) {
		return dal.getSystLogInfo(str);
	}

	public int addLog(String IP, Integer cid, Integer gid, String type,
			String content, String addname) {
		if (IP == null) {
			HttpServletRequest request = (HttpServletRequest) Executions
					.getCurrent().getNativeRequest();
			IP = GetIpUtil.getIpAddr(request);
		}
		return dal.addLog(IP, cid, gid, type, content, addname);
	}

	public int addLog(String IP, Integer tid, Integer cid, Integer gid,
			String type, String content, String addname) {
		if (IP == null) {
			HttpServletRequest request = (HttpServletRequest) Executions
					.getCurrent().getNativeRequest();
			IP = GetIpUtil.getIpAddr(request);
		}
		return dal.addLog(IP, tid, cid, gid, type, content, addname);
	}
}
