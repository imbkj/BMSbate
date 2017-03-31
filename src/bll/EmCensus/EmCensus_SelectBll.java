package bll.EmCensus;

import java.util.List;

import dal.EmCensus.EmCensus_SelectDal;

import Model.EmCensusModel;
import Model.EmHJBorrowCardModel;
import Model.EmbaseModel;

public class EmCensus_SelectBll {
	EmCensus_SelectDal dal = new EmCensus_SelectDal();

	// 根据条件查找员工信息
	public List<EmbaseModel> getEmbaseInfo(String str) {
		return dal.getEmbaseInfo(str);
	}

	// 根据条件查找户口信息
	public List<EmCensusModel> getEmCensusInfo(String str) {
		return dal.getEmCensusInfo(str);
	}

	// 根据条件查找户口卡信息
	public List<EmHJBorrowCardModel> getEmHJBorrowCardInfo(String str) {
		return dal.getEmHJBorrowCardInfo(str);
	}

	// 根据条件查找户口卡表信息
	public List<EmHJBorrowCardModel> getEmHJBorrowCardInfos(String str) {
		return dal.getEmHJBorrowCardInfos(str);
	}

	// 获取中智集体户最大的户口编号
	public String getMaxHjNo() {
		return dal.getMaxHjNo();
	}

	// 获取独立户户最大的户口编号
	public String getMaxHjNos(String str, Integer cid) {
		return dal.getMaxHjNos(str, cid);
	}

	// 根据gid和cid 查询某员工已有户口编号
	public String getEmHjNo(Integer cid, Integer gid) {
		return dal.getEmHjNo(cid, gid);
	}

	// 根据gid查询员工的户口编号
	public String getHjNoByGid(Integer gid) {
		return dal.getHjNoByGid(gid);
	}

	// 查询合同id是否已经存在
	public boolean ifexist(String hjno) {
		return dal.ifexist(hjno);
	}

	public EmbaseModel getEmbaId(Integer gid) {
		return dal.getEmbaId(gid);
	}

	public boolean ifSyIsOut(Integer gid) {
		return dal.ifSyIsOut(gid);
	}

	public boolean ifHKKIsOut(Integer gid,String emhj_no) {
		return dal.ifHKKIsOut(gid,emhj_no);
	}
	// 查询家属
	public List<EmCensusModel> SelectEmCensusList(int gid) {
		return dal.SelectEmCensusList(gid);
	}
	
	public EmCensusModel getEmCensusId(Integer gid) {
		return dal.getEmCensusId(gid);
	}
}
