package bll.EmHouse;

import java.util.List;

import service.EmHouseService;
import Model.EmHouseChangeModel;
import Model.EmHouseCpp;
import Model.EmHouseErrModel;
import Model.EmHouseErrMonthModel;
import dal.EmHouse.EmHouseErrDal;
import dal.EmHouse.EmHouseErrMonthDal;

public class EmHouseImpl implements EmHouseService {

	@Override
	public boolean add(EmHouseChangeModel m) {
		// TODO 新增员工公积金数据
		if (same(m.getGid(), m.getEmhc_change())) {
			
		}
		return false;
	}

	@Override
	public boolean takeover(EmHouseChangeModel m) {
		// TODO 接管员工公积金数据
		return false;
	}

	@Override
	public boolean stopSingle(EmHouseChangeModel m) {
		// TODO 独立户解约
		return false;
	}

	@Override
	public boolean mod(EmHouseChangeModel m, Integer id) {
		// TODO 修改员工公积金数据
		return false;
	}

	@Override
	public boolean send(Integer id) {
		// TODO 重新发送员工公积金数据
		return false;
	}

	@Override
	public boolean radixChange(Double radix, String companyid, Integer gid) {
		// TODO 年度调基
		return false;
	}

	@Override
	public boolean radixSP(Double radix, String companyid, Integer gid) {
		// TODO 特殊调基
		return false;
	}

	@Override
	public boolean del(Integer id) {
		// TODO 删除数据
		return false;
	}

	@Override
	public void fixedData(Integer gid) {
		// TODO 校正员工在册数据

	}

	@Override
	public void fixedData(String idcard) {
		// TODO 校正员工在册数据

	}

	@Override
	public boolean same(Integer gid, String type) {
		// TODO 判断是否有同类员工未处理变更数据
		return false;
	}

	@Override
	public boolean power() {
		// TODO 是否禁止提交数据
		return false;
	}

	@Override
	public Integer emSingle(Integer gid) {
		// TODO 获取员工账户类型
		return null;
	}

	@Override
	public Integer tsday(Double cpp) {
		// TODO 获取托收日(中智户)
		return null;
	}

	@Override
	public Integer tsday(Integer cid) {
		// TODO 获取托收日(独立户)
		return null;
	}

	@Override
	public Integer lastday(Double cpp) {
		// TODO 获取截单日(中智户)
		return null;
	}

	@Override
	public Integer lastday(Integer cid) {
		// TODO 获取截单日(独立户)
		return null;
	}

	@Override
	public boolean gjjProduct(Integer gid) {
		// TODO 判断员工是否存在有效公积金项目
		return false;
	}

	@Override
	public List<EmHouseCpp> cpplist(Integer gid) {
		// TODO 获取员工公积金比例列表
		return null;
	}

	@Override
	public boolean financeBoforeList() {
		// TODO 查询当前月份是否有台前异常数据
		EmHouseErrMonthDal dal = new EmHouseErrMonthDal();
		List<EmHouseErrMonthModel> list=dal.getListByOwnmonth();
		if (list.size()>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean financeAfterList() {
		// TODO 查询当前月份是否有台后异常数据
		EmHouseErrDal dal = new EmHouseErrDal();
		List<EmHouseErrModel> list=dal.getListByOwnmonth();
		if (list.size()>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void financeBofore() {
		// TODO 根据台前异常数据身份证校正在册数据
		EmHouseErrMonthDal dal = new EmHouseErrMonthDal();
		dal.freshData();
	}

	@Override
	public void financeAfter() {
		// TODO 根据台后异常数据身份证校正在册数据
		
	}

}
