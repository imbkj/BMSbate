package bll.CoProduct;

import impl.WorkflowCore.WfOperateImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.zkoss.zul.ListModelList;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import dal.CoProduct.cpd_addDal;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoPAccountModel;
import Model.CoPFeeclassModel;
import Model.CoPclassModel;
import Model.CoProductModel;
import Model.CopCompactModel;
import Model.PubProCityModel;
import Util.UserInfo;

public class cpd_addBll implements WfBusinessService {

	// 检查服务产品是否存在
	public boolean Exist(CoProductModel cop) throws SQLException {
		boolean isExist = false;
		cpd_addDal dal = new cpd_addDal();
		isExist = dal.Exist(cop);
		return isExist;
	}
	
	// 检查服务产品是否存在
		public boolean Existwt(CoProductModel cop) throws SQLException {
			boolean isExist = false;
			cpd_addDal dal = new cpd_addDal();
			isExist = dal.Existwt(cop);
			return isExist;
		}
		
	

	

	// 获取享受方式列表
	public List<String> getStandard() throws SQLException {
		cpd_addDal dal = new cpd_addDal();
		List<String> list = dal.getStandardList();
		return list;
	}

	public List<String> getStandardList1(Integer copc_id) {
		cpd_addDal dal = new cpd_addDal();
		return dal.getStandardList1(copc_id);
	}

	// 获取产品类型列表
	public List<CoPclassModel> getclass() throws SQLException {
		cpd_addDal dal = new cpd_addDal();
		List<CoPclassModel> list = dal.getclassList();
		return list;
	}

	// 获取所属科目列表
	public List<CoPAccountModel> getaccount() throws SQLException {
		cpd_addDal dal = new cpd_addDal();
		List<CoPAccountModel> list = dal.getaccountList();
		return list;
	}

	// 获取适用地列表
	public List<PubProCityModel> getcity() throws SQLException {
		cpd_addDal dal = new cpd_addDal();
		List<PubProCityModel> list = dal.getcityList();
		return list;
	}

	// 获取收费单位列表
	public List<CoProductModel> geteclass() throws SQLException {
		cpd_addDal dal = new cpd_addDal();
		List<CoProductModel> list = dal.geteclassList();
		return list;
	}
	
	//获取产品收费单位信息
	public List<CoProductModel> getfeeClass(Integer coprid,Integer coabId){
		cpd_addDal dal = new cpd_addDal();
		return dal.getfeeClass(coprid, coabId);
		
	}
	
	// 获取月/人收费单位列表 委托出产品用
	public List<CoProductModel> geteclassListt1() throws SQLException {
		cpd_addDal dal = new cpd_addDal();
		List<CoProductModel> list = dal.geteclassListt1();
		return list;
	}
	
	

	public List<CoPFeeclassModel> geteclass1() throws SQLException {
		cpd_addDal dal = new cpd_addDal();
		List<CoPFeeclassModel> list = dal.geteclassList1();
		return list;
	}

	public List<String> getcity(Set<String> city) {
		List<String> list = new ListModelList<String>();
		for (String i : city) {
			list.add(i);
		}
		return list;
	}

	// 新增服务产品
	public String[] cpdadd(CoProductModel cpmol, List<CoProductModel> list)
			throws SQLException {

		Object[] obj = { "1", cpmol, list };
		WfOperateService wfs = new WfOperateImpl(new cpd_addBll());
		String[] str = wfs.AddTaskToNext(obj, "产品新增",
				"\"" + cpmol.getCopr_name() + "\"产品新增", 78,
				UserInfo.getUsername(), "", 0, "");

		return str;
	}
	
	//更新委托机构最低人事产品金额
	public int editfee(CoAgencyBaseCityRelViewModel m) throws SQLException
	{
		cpd_addDal dal = new cpd_addDal();
		return dal.editfee(m);
	}
	

	// 新增服务产品状态变更
	public String[] updatestate(CoProductModel m) throws SQLException {

		Object[] obj = { "2", m };
		WfOperateService wfs = new WfOperateImpl(new cpd_addBll());
		String[] str = wfs.PassToNext(obj, m.getCopr_tapr_id(),
				UserInfo.getUsername(), m.getCopr_remark(), 0, "");

		return str;
	}

	// 新增收费单位和默认金额
	public int insertfee(CoProductModel cpmol) throws SQLException {
		int row = 0;
		cpd_addDal dal = new cpd_addDal();
		row = dal.insertfee(cpmol);
		return row;
	}

	// 获取所有委托机构列表
	public List<CoAgencyBaseCityRelViewModel> getCoagList() {
		cpd_addDal dal = new cpd_addDal();
		return dal.getCoagList();
	}

	// 获取产品详细信息
	public CoProductModel getCoproductInfo(Integer copr_id) {
		cpd_addDal dal = new cpd_addDal();
		return dal.getCoproductInfo(copr_id);
	}

	// 产品与合同类型关联新增
	public Integer CopComReladd(CopCompactModel m) {
		cpd_addDal dal = new cpd_addDal();
		return dal.CopComReladd(m);
	}
	
	// 产品与合同类型关联新增
		public Integer insertCopComRel(Integer cpct_id,Integer type) {
			cpd_addDal dal = new cpd_addDal();
			return dal.insertCopComRel(cpct_id,type);
		}

	// 删除产品与合同的关联
	public Integer deleteCopComRelByCpctId(Integer cpct_id) {
		cpd_addDal dal = new cpd_addDal();
		return dal.deleteCopComRelByCpctId(cpct_id);
	}

	// 合同类型新增
	public Integer CopCompactAdd(CopCompactModel m) {
		cpd_addDal dal = new cpd_addDal();
		return dal.CopCompactAdd(m);
	}

	// 合同类型修改
	public Integer CopCompactMod(CopCompactModel m) {
		cpd_addDal dal = new cpd_addDal();
		return dal.CopCompactMod(m);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String[] Operate(Object[] obj) {
		cpd_addDal dal = new cpd_addDal();
		String[] str = new String[5];

		if (obj[0].equals("1")) {
			// 新增
			
			CoProductModel m = (CoProductModel) obj[1];

			try {
				if (Exist(m))
				{
					str[0] = "0";
					str[1] = "产品已存在!";
				}
				else
				{
				
				Integer id = dal.cpdadd(m);

				if (id > 0) {
					List<CoProductModel> list = (List<CoProductModel>) obj[2];
					Integer row = 0;

					for (CoProductModel m1 : list) {
						if (m1.isIfU()) {

							m1.setCopr_id(id);
							m1.setCopr_addname(UserInfo.getUsername());

							// 是否锁定
							if (m1.isCpfr_lock1()) {
								m1.setCpfr_lock(1);
							} else {
								m1.setCpfr_lock(0);
							}
							row += insertfee(m1);
						}
					}

					if (row > 0) {
						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = id + "";
						str[3] = "CoProduct";
						str[4] = "提交新产品数据";
					} else {
						str[0] = "0";
						str[1] = "提交失败!";
					}
				
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}
		} else if (obj[0].equals("2")) {
			// 状态变更
			CoProductModel m = (CoProductModel) obj[1];

			try {
				Integer row = dal.updatestate(m);

				if (row > 0 && m.getCopr_state() == 2) {

					row = dal.CoproductModAccount(m.getCopr_id(),
							m.getCopr_cpac_id());

					m.setCopr_addname(UserInfo.getUsername());
					dal.Coproduct_Embenefit(m);

					if (row > 0) {
						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = m.getCopr_id() + "";
						str[3] = "CoProduct";
						str[4] = dal
								.getStateList(
										" and stateid=" + m.getCopr_state()
												+ " and type='prdadd'").get(0)
								.getOperate();
					} else {
						str[0] = "0";
						str[1] = "提交失败!";
					}
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
	
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}	
		
		
		}
		//委托机构的服务产品，非任务单
		else if (obj[0].equals("3"))
		{
			CoProductModel m = (CoProductModel) obj[1];
			m.setCopr_state(2);
			try {
				
				if (Existwt(m))
				{
					str[0] = "0";
					str[1] = "产品已存在!";
				}
				else
				{
				
				Integer id = dal.cpdadd(m);

				if (id > 0) {
					List<CoProductModel> list = (List<CoProductModel>) obj[2];
					Integer row = 0;

					for (CoProductModel m1 : list) {
						if (m1.isIfU()) {

							m1.setCopr_id(id);
							m1.setCopr_addname(UserInfo.getUsername());

							// 是否锁定
							if (m1.isCpfr_lock1()) {
								m1.setCpfr_lock(1);
							} else {
								m1.setCpfr_lock(0);
							}
							row += insertfee(m1);
						}
					}

					if (row > 0) {
						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = id + "";
						str[3] = "CoProduct";
						str[4] = "提交新产品数据";
					} else {
						str[0] = "0";
						str[1] = "提交失败!";
					}
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}
			
		}
		return str;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		cpd_addDal dal = new cpd_addDal();
		return dal.UpdateTaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}
}
