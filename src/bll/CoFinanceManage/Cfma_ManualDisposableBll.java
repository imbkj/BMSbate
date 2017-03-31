package bll.CoFinanceManage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoFinanceManualDisposableModel;
import Model.CoOfferListModel;
import Model.EmbaseModel;
import Util.DateStringChange;
import Util.MonthListUtil;

import dal.CoFinanceManage.Cfma_ManualDisposableDal;

/**
 * 手动添加非标
 * 
 * @author Administrator
 * 
 */
public class Cfma_ManualDisposableBll {

	private Cfma_ManualDisposableDal dal = new Cfma_ManualDisposableDal();

	public CoBaseModel getCoBase(int cid) {
		return dal.getCoBase(cid);
	}

	// 获得公司非标列表信息
	public List<CoFinanceManualDisposableModel> getCoFinanceManualDisposable(
			int cid, Date ownmonth) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		int month = Integer.parseInt(sdf.format(ownmonth));
		return dal.getCoFinanceManualDisposable(cid, month);
	}
	
	// 获取过去3个月的所属月份数组
	public String[] getOwnmonthlist(boolean exNow) {
		String[] month = null;
		try {
			String nowMonth = DateStringChange.ownmonthAdd(DateStringChange
					.DatetoSting(new Date(), "yyyyMM"),-2);
			
			month = MonthListUtil.getMonthList(exNow, nowMonth, "f", 6);
		} catch (Exception e) {

		}
		return month;
	}

	


	// 获得员工非标列表信息
	public List<CoFinanceManualDisposableModel> getEmFinanceManualDisposable(
			int cid, Date ownmonth) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		int month = Integer.parseInt(sdf.format(ownmonth));
		return dal.getEmFinanceManualDisposable(cid, month);
	}

	// 获取合同信息
	public List<CoCompactModel> getCocoinfo(int cid) {

		return dal.getCocoinfo(cid);
	}

	public List<CoOfferListModel> getCoofferList(Integer cid) {
		return dal.getCoofferList(cid);
	}
	
	public List<CoOfferListModel> getCoofferinfo(String copr_name) {
		return dal.getCoofferList(copr_name);
	}
	
	public List<CoOfferListModel> getCoofferListall() {
		return dal.getCoofferListall();
	}
	// 获取财务科目
	public List<String> getCoPA() {
		return dal.getCoPA();
	}
	// 获取财务科目
	public List<String> getCoPA(Integer cid) {
		return dal.getCoPA(cid);
	}


	// 根据cfmd_id 查询公司信息
	public CoBaseModel getCoInfo(int cfmd_id) {
		return dal.getCoInfo(cfmd_id);
	}

	// 根据cfmd_id 查询公司非标审核信息
	public CoFinanceManualDisposableModel getCheckCoInfo(int cfmd_id) {
		return dal.getCheckCoInfo(cfmd_id);

	}
	
	//根据coco_id查询可以添加非标的公司
	public List<EmbaseModel> getCanAddDisEm(int coco_id){
		return dal.getCanAddDisEm(coco_id);
	}
	
	//根据gid查询员工信息
	public EmbaseModel getEminfo(int gid){
		return dal.getEminfo(gid);
	}

	//根据登录人查询可操作的数据
	public List<CoFinanceManualDisposableModel> getPlCheckInfo(String name){
		
		return dal.getPlCheckInfo(name);
	}
	
	//根据登录人获取到登录人部门的人员
	public List<String> getclients(String name){
		return dal.getclients(name);
	}
}
