package bll.EmPay;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.EmPayBackLogModel;
import Model.EmPayModel;
import Model.EmbaseModel;
import Util.UserInfo;
import dal.EmPay.EmPa_OperateDal;
import dal.EmPay.EmPay_ListDal;
import dal.EmTax.EmTax_OperateDal;

public class EmPa_OperateBll {
	private final EmPa_OperateDal dal = new EmPa_OperateDal();
	private final DecimalFormat myformat = new DecimalFormat("0.00");
	private EmPa_Calculate calculate = new Empa_CalculateIpml();

	// 支付新增
	public int EmPayAdd(EmPayModel m) {
		if (m.getEmpa_paytype().equals("个税发放")) {
			BigDecimal decm = calculate.Calculate(m.getEmpa_fee());
			String tax = myformat.format(decm);
			BigDecimal bigtax = new BigDecimal(tax);
			m.setEmpa_tax(bigtax);
			BigDecimal afftertax = m.getEmpa_fee().subtract(bigtax);
			m.setEmpa_aftertax(afftertax);
		} else {
			m.setEmpa_tax(BigDecimal.ZERO);
			m.setEmpa_aftertax(m.getEmpa_fee());
		}
		return dal.EmPayAdd(m);
	}

	// 判断是否有重复数据
	public boolean empayRepeat(EmbaseModel m) {
		boolean b = false;
		EmPay_ListDal dal = new EmPay_ListDal();
		List<EmPayModel> list = new ListModelList<>();
		list = dal.getList(m.getGid(), m.getEmba_class(),
				Integer.parseInt(changedate(m.getOwnmonth())));
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	public int batchAdd(List<EmbaseModel> list, String number) {
		int i = 0;
		String username =UserInfo.getUsername() ;
		
		for (EmbaseModel m : list) {
			EmPayModel model = new EmPayModel();
			model.setEmpa_state(1);
			model.setGid(m.getGid());
			model.setCid(m.getCid());
			model.setOwnmonth(Integer.parseInt(changedate(m.getOwnmonth())));
			model.setOwnmonthend(Integer.parseInt(changedate(m.getOwnmonthend())));
			model.setEmpa_fee(m.getEmba_fee());
			model.setEmpa_class(m.getEmba_class());
			model.setEmpa_account(m.getEmba_gz_account());
			model.setEmpa_bank(m.getEmba_gz_bank());
			model.setEmpa_addname(username);
			model.setEmpa_remark(m.getEmba_remark());
			model.setEmpa_paytype(m.getEmba_paytype());
			model.setEmpa_payclass(m.getEmba_payclass());
			model.setEmpa_name(m.getEmba_name());
			model.setEmpa_ba_name(m.getEmba_ba_name());
			model.setEmpa_number(number);
			model.setEmpa_paymenttype(m.getEmba_paymenttype());
			int k = EmPayAdd(model);
			if (k > 0) {
				if (model.getRemark() != null && !model.getRemark().equals("")) {
					dal.AddRemark(k, model.getRemark());
				}
				i = i + 1;
			}
		}
		if (i > 0) {
			String logcontent = "添加了：" + i + "条支付数据，支付号为：" + number
					+ "的支付数据，添加人:" + username;
			dal.AddEmpayLog(number, logcontent);
		}
		return i;
	}

	// 审批
	public int Approval(EmPayModel model) {
		if (model.getApprovalType() == null
				|| model.getApprovalType().equals("")) {
			return -1;
		} else {
			int k = 0;
			EmPa_ApprovalCreateFactory factory = new EmPa_ApprovalCreateFactory(
					model);
			EmPa_Approval approval = factory.getApproval();
			if (approval != null) {
				k = approval.Approval();
				return k;
			} else {
				return -2;
			}
		}
	}

	// 插入处理步骤
	public int AddEmpayTask(int task_step, String task_name, String task_number) {
		return dal.AddEmpayTask(task_step, task_name, task_number);
	}

	// 退回
	public int back(EmPayBackLogModel m) {
		EmPa_back back = new EmPa_BackImpl();
		return back.empayBack(m);
	}

	private String changedate(Date d) {
		String dateString = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		if (d != null && !d.equals("")) {
			dateString = formatter.format(d);
		}
		return dateString;
	}
	
	//删除员工数据
	public Integer del(Integer id){
		return dal.del(id);
	}
}
