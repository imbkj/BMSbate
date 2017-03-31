package bll.SocialInsurance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import dal.EmSheBao.Emsi_SelDal;
import dal.SocialInsurance.Algorithm_RegisteredDataDal;
import dal.SocialInsurance.SocialInsuranceCalculatorDal;

import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmShebaoUpdateModel;
import Model.SocialInsuranceClassInfoViewModel;
import Util.SocialInsuranceCalculator;
import Util.SocialInsuranceEmCommissionOut;
import Util.SocialInsuranceCalculator.calculationMethod;
import Util.SocialInsuranceCalculator.item;

public class Algorithm_RegisteredDataBll {

	// 更新外地在册明细数据及变更表数据
	public String[] upRegData(int soin_id, int sial_id, Date sial_execdate) {
		String[] message = new String[2];
		message[0] = "1";
		message[1] = "更新在册数据成功。";
		try {
			Algorithm_InfoBll selbll = new Algorithm_InfoBll();

			// 设置算法
			SocialInsuranceEmCommissionOut siEc = new SocialInsuranceEmCommissionOut();
			if (siEc.setCalculator(soin_id, sial_execdate, "委托对账", "委托对账")) {
				// 处理在册表数据
				// 获取在册表数据
				List<EmCommissionOutFeeDetailChangeModel> list = selbll
						.getRegDataFeeDetailBySoinId(sial_id);
				System.out.println(list.size());
				System.out.println(sial_id);
				// 重新计算金额
				list = siEc.getEmCommissionOutItemFee(list);
				// 更新数据库
				Algorithm_RegisteredDataDal opDal = new Algorithm_RegisteredDataDal();
				for (EmCommissionOutFeeDetailChangeModel m : list) {
					opDal.upRegData(m);  
				}
	
				// 处理变更表数据
				// 获取变更表数据
				list = selbll.getRegDataFeeDetailChangeBySoinId(sial_id);
				// 重新计算金额
				list = siEc.getEmCommissionOutItemFee(list);
				// 更新数据库
				for (EmCommissionOutFeeDetailChangeModel m : list) {
					opDal.upRegDataChange(m);
				}
			} else {
				message[0] = "0";
				message[1] = "更新在册数据失败，无法获取算法。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "更新在册数据出错。";
		}
		return message;
	}
	public String[] insertchangeDate(int ecou_id)
	{
		String[] message = new String[2];
		message[0] = "1";
		message[1] = "插入变更成功！";
		Algorithm_RegisteredDataDal opDal = new Algorithm_RegisteredDataDal();
		opDal.insertchangeDate(ecou_id);
		
		
		
		
		return message;
		
	}
	

	// 更新本地社保在册表数据
	public String[] upLocalRegData(int soin_id, int sial_id,
			Date sial_execdate, List<EmShebaoUpdateModel> euList) {
		String[] message = new String[2];
		message[0] = "0";
		message[1] = "更新在册数据失败，无法获取算法。";
		try {
			// 获取社保算法
			SocialInsuranceCalculatorDal dal = new SocialInsuranceCalculatorDal();
			List<SocialInsuranceClassInfoViewModel> siList = dal.getSiAlInfo(
					soin_id, sial_execdate, "社会保险");
			// 循环计算社保并更新数据库
			Algorithm_RegisteredDataDal opDal = new Algorithm_RegisteredDataDal();
			for (EmShebaoUpdateModel m : euList) {
				if (sumSbItemFee(m, siList)) {
					opDal.upLocalRegData(m);
				}
			}
			message[0] = "1";
			message[1] = "更新在册数据成功。";
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "更新在册数据出错。";
			e.printStackTrace();
		}
		return message;
	}

	// 计算社保各项目费用
	private boolean sumSbItemFee(EmShebaoUpdateModel m,
			List<SocialInsuranceClassInfoViewModel> siList) {
		boolean bool = false;
		SocialInsuranceCalculator si = new SocialInsuranceCalculator();
		try {
			// 设置算法计算项目
			if ("不参加".equals(m.getEsiu_yl())) {
				si.setYl(false);
			}
			if ("不参加".equals(m.getEsiu_gs())) {
				si.setGs(false);
			}
			if ("不参加".equals(m.getEsiu_sye())) {
				si.setSye(false);
			}
			if ("不参加".equals(m.getEsiu_syu())) {
				si.setSyu(false);
			}
			if ("不参加".equals(m.getEsiu_yltype()) || "大学生".equals(m.getEsiu_yltype())) {
				si.setJl(false);
			}
			si.setDb(false);
			// 获取工伤系数及失业下浮比例
			Emsi_SelDal selDal = new Emsi_SelDal();
			String[] proportion = selDal.getItemProportionByGid(m.getGid());
			if (proportion != null) {
				// 设置工伤公司缴交部分算法
				si.setCalculationMethod(item.gsCp,
						calculationMethod.replaceProprotion, proportion[0]);
				// 设置失业保险公司缴交部分算法
				si.setCalculationMethod(item.syeCp, calculationMethod.multiply,
						proportion[1]);
			}
			// 计算社保项目
			List<SocialInsuranceClassInfoViewModel> list = si.getSbItemFee(
					new BigDecimal(m.getEsiu_radix()), siList);
			setSbItemFee(m, list);
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 社保项目赋值
	private void setSbItemFee(EmShebaoUpdateModel m,
			List<SocialInsuranceClassInfoViewModel> list) {
		try {
			for (SocialInsuranceClassInfoViewModel item : list) {
				switch (item.getSicl_name()) {
				case "养老保险":
					switch (item.getSicl_payunit()) {
					case "企业":
						m.setEsiu_ylcp(item.getFee());
						break;
					case "个人":
						m.setEsiu_ylop(item.getFee());
						break;
					}
					break;
				case "医疗保险":
					switch (item.getSicl_payunit()) {
					case "企业":
						m.setEsiu_jlcp(item.getFee());
						break;
					case "个人":
						m.setEsiu_jlop(item.getFee());
						break;
					}
					break;
				case "生育保险":
					switch (item.getSicl_payunit()) {
					case "企业":
						m.setEsiu_syucp(item.getFee());
						break;
					case "个人":
						m.setEsiu_syuop(item.getFee());
						break;
					}
					break;
				case "工伤保险":
					switch (item.getSicl_payunit()) {
					case "企业":
						m.setEsiu_gscp(item.getFee());
						break;
					case "个人":
						m.setEsiu_gsop(item.getFee());
						break;
					}
					break;
				case "失业保险":
					switch (item.getSicl_payunit()) {
					case "企业":
						m.setEsiu_syecp(item.getFee());
						break;
					case "个人":
						m.setEsiu_syeop(item.getFee());
						break;
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
