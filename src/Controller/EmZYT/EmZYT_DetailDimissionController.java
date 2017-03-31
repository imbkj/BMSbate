package Controller.EmZYT;

import java.util.Date;

import org.zkoss.zk.ui.Executions;

import Model.EmZYTModel;
import Util.DateStringChange;

public class EmZYT_DetailDimissionController {
	private DateStringChange dsc = new DateStringChange();
	private String ylstop=((EmZYTModel)Executions.getCurrent().getArg().get("emztM")).getEmzt_ylstop();
	private String housestop=((EmZYTModel)Executions.getCurrent().getArg().get("emztM")).getEmzt_housestop();
	
	// 获取当前日期
	Date nowDate = new Date(); // 获取当前时间

	private String nowString = dsc.DatetoSting(nowDate, "yyyy") + "年"+ dsc.DatetoSting(nowDate, "MM") + "月"+ dsc.DatetoSting(nowDate, "d") + "日";
	private String ylstopString = dsc.DatetoSting(dsc.StringtoDate(ylstop, "yyyy-MM-dd"), "yyyy")+ "." + dsc.DatetoSting(dsc.StringtoDate(ylstop, "yyyy-MM-dd"), "MM");
	private String housestopString = dsc.DatetoSting(dsc.StringtoDate(housestop, "yyyy-MM-dd"), "yyyy")+ "." + dsc.DatetoSting(dsc.StringtoDate(housestop, "yyyy-MM-dd"), "MM");
	
	public String getNowString() {
		return nowString;
	}
	public void setNowString(String nowString) {
		this.nowString = nowString;
	}
	public String getYlstopString() {
		return ylstopString;
	}
	public void setYlstopString(String ylstopString) {
		this.ylstopString = ylstopString;
	}
	public String getHousestopString() {
		return housestopString;
	}
	public void setHousestopString(String housestopString) {
		this.housestopString = housestopString;
	}
	
	
}
