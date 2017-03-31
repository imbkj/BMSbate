/**
 * @Classname SocialInsuranceCalculator
 * @ClassInfo 计算社保及公积金缴纳额
 * @author 李文洁
 * @Date 2013-11-27
 */

package Util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dal.SocialInsurance.SocialInsuranceCalculatorDal;

import Model.SocialInsuranceClassInfoViewModel;

public class SocialInsuranceCalculator {

	private DecimalFormat df = new DecimalFormat("#.00");
	// 养老保险
	private boolean yl = true;
	// 医疗保险
	private boolean jl = true;
	// 大病医疗
	private boolean db = true;
	// 生育保险
	private boolean syu = true;
	// 失业保险
	private boolean sye = true;
	// 工伤保险
	private boolean gs = true;
	// 住房公积金
	private boolean zfgjj = true;
	// 补充公积金
	private boolean bcgjj = true;

	// 计算方式容器Map
	private Map<String, String[]> methodMap;

	public SocialInsuranceCalculator() {
		methodMap = new HashMap<String, String[]>();
	}

	/**
	 * @Methodname:计算社保各项目费用
	 * 
	 * @input: soin_id:算法ID；sbbasic:社保基数；date：开始时间
	 * 
	 * @out: List<SocialInsuranceClassInfoViewModel>；
	 */
	public List<SocialInsuranceClassInfoViewModel> getSbItemFee(int soin_id,
			BigDecimal sbbasic, Date date) {
		return sumSbItemFee(soin_id, sbbasic, date);
	}

	/**
	 * @Methodname:计算社保各项目费用
	 * 
	 * @input: sbbasic:社保基数；
	 * 
	 * @out: List<SocialInsuranceClassInfoViewModel>；
	 */
	public List<SocialInsuranceClassInfoViewModel> getSbItemFee(
			BigDecimal sbbasic, List<SocialInsuranceClassInfoViewModel> sblist) {
		return sumSbItemFee(sbbasic, sblist);
	}

	/**
	 * @Methodname:计算公积金各项目费用
	 * 
	 * @input: soin_id:算法ID；gjjbasic:公积金基数;gjjproprotion:公积金比例；date： 开始时间
	 * 
	 * @out: List<SocialInsuranceClassInfoViewModel>；
	 */
	public List<SocialInsuranceClassInfoViewModel> getGjjItemFee(int soin_id,
			BigDecimal gjjbasic, BigDecimal gjjproprotion, Date date) {
		return sumGjjItemFee(soin_id, gjjbasic, gjjproprotion, date);
	}

	/**
	 * @Methodname:计算社保及公积金总缴交额
	 * 
	 * @input: 
	 *         soin_id:算法ID；sbbasic:社保基数；gjjbasic:公积金基数;gjjproprotion:公积金比例；date：
	 *         开始时间
	 * 
	 * @out: BigDecimal[]; BigDecimal[0]:社保缴纳额;BigDecimal[1]公积金缴纳额；
	 */
	public BigDecimal[] getSbGjjFee(int soin_id, BigDecimal sbbasic,
			BigDecimal gjjbasic, BigDecimal gjjproprotion, Date date) {
		BigDecimal[] dec = new BigDecimal[2];
		dec[0] = new BigDecimal(df.format(sumSbFee(soin_id, sbbasic, date)));
		dec[1] = new BigDecimal(df.format(sumGjjFee(soin_id, gjjbasic,
				gjjproprotion, date)));
		return dec;
	}

	/**
	 * @Methodname:计算养老保险费用(含企业及个人)
	 * 
	 * @input: soin_id:算法ID；sbbasic:社保基数；date： 开始时间
	 * 
	 * @out: BigDecimal[0]:企业；BigDecimal[1]:个人
	 */
	public BigDecimal[] getYlFee(int soin_id, BigDecimal sbbasic, Date date) {
		BigDecimal[] ylfee = new BigDecimal[2];
		setAllItem(false);
		setYl(true);
		List<SocialInsuranceClassInfoViewModel> list = sumSbItemFee(soin_id,
				sbbasic, date);
		for (SocialInsuranceClassInfoViewModel m : list) {
			if ("养老保险".equals(m.getSicl_name())) {
				switch (m.getSicl_payunit()) {
				case "企业":
					ylfee[0] = m.getFee();
					break;
				case "个人":
					ylfee[1] = m.getFee();
					break;
				}
			}
		}
		return ylfee;
	}
	
	/**
	 * @Methodname:计算医疗保险费用(含企业及个人)
	 * 
	 * @input: soin_id:算法ID；sbbasic:社保基数；date： 开始时间
	 * 
	 * @out: BigDecimal[0]:企业；BigDecimal[1]:个人
	 */
	public BigDecimal[] getJlFee(int soin_id, BigDecimal sbbasic, Date date) {
		BigDecimal[] jlfee = new BigDecimal[2];
		setAllItem(false);
		setJl(true);
		List<SocialInsuranceClassInfoViewModel> list = sumSbItemFee(soin_id,
				sbbasic, date);
		for (SocialInsuranceClassInfoViewModel m : list) {
			if ("医疗保险".equals(m.getSicl_name())) {
				switch (m.getSicl_payunit()) {
				case "企业":
					jlfee[0] = m.getFee();
					break;
				case "个人":
					jlfee[1] = m.getFee();
					break;
				}
			}
		}
		return jlfee;
	}

	/**
	 * @Methodname:判断深圳本地公积金基数是否符合范围
	 * 
	 * @input: basic：基数
	 * 
	 * @out: 校验后的基数
	 */
	public BigDecimal checkGjjBasic(BigDecimal basic) {
		BigDecimal b = basic;
		try {
			List<SocialInsuranceClassInfoViewModel> gjjlist = getSiAlInfo(
					getSionId("深户员工", "深圳", "深圳中智经济技术合作有限公司"), new Date(),
					"公积金");
			SocialInsuranceClassInfoViewModel m = gjjlist.get(0);
			b = checkBasic(basic, m.getSiai_basic_ud(), m.getSiai_basic_dd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * @Methodname:获取算法ID(sion_id)
	 * 
	 * @Parameters:soin_title:算法名称；city:城市；coab_name:机构名称
	 */
	public int getSionId(String soin_title, String city, String coab_name) {
		SocialInsuranceCalculatorDal dal = new SocialInsuranceCalculatorDal();
		return dal.getSionId(soin_title, city, coab_name);
	}

	/**
	 * @Methodname:项目枚举
	 */
	public enum item {
		ylCp, ylOp, jlCp, jlOp, dbCp, dbOp, syuCp, syuOp, syeCp, syeOp, gsCp, gsOp, zfgjjCp, zfgjjOp, bcgjjCp, bcgjjOp
	};

	/**
	 * @Methodname:计算方式枚举
	 * 
	 * @remark:默认计算方式为：基数*比例(社保比例默认为字典库相应比例)；
	 * 
	 * @Parameters：reProprotion:以新比例代替默认比例；multiply:递乘
	 */
	public enum calculationMethod {
		replaceProprotion, multiply
	};

	/**
	 * @Methodname:设置项目的计算方式
	 * 
	 * @remark:默认计算方式为：基数*比例(社保比例默认为字典库相应比例)；
	 * 
	 */
	// @Parameters：item:项目；calculationMethod：计算方式
	// (reProprotion:以新比例n代替默认比例；multiply:递乘(基数*比例*n))； n:新参数；
	public void setCalculationMethod(item item,
			calculationMethod calculationMethod, String n) {
		String itemname = transformationItem(item);// 转换项目名称
		String method = transformationCalculationMethod(calculationMethod);// 转换计算方式
		methodMap.remove(itemname);
		methodMap.put(itemname, new String[] { method, n });
	}

	/**
	 * @Methodname:设置所有项目是否参与计算
	 */
	public void setAllItem(boolean b) {
		this.yl = b;
		this.jl = b;
		this.db = b;
		this.syu = b;
		this.sye = b;
		this.gs = b;
		this.zfgjj = b;
		this.bcgjj = b;
	}

	// 计算社保各项目缴交额(可自定义算法,传入算法标准)
	protected List<SocialInsuranceClassInfoViewModel> sumSbItemFee(
			BigDecimal sbbasic, List<SocialInsuranceClassInfoViewModel> sblist) {
		List<SocialInsuranceClassInfoViewModel> feelist = new ArrayList<SocialInsuranceClassInfoViewModel>();
		for (SocialInsuranceClassInfoViewModel m : sblist) {
			if (ifSum(m.getSicl_name())) {
				m.setFee(dealDecimal(
						checkBasic(
								calculate(
										m.getSicl_name(),
										m.getSicl_payunit(),
										checkBasic(sbbasic,
												m.getSiai_basic_ud(),
												m.getSiai_basic_dd()),
										m.getSiai_proportion()), m
										.getSiai_deposit_ud(), m
										.getSiai_deposit_dd()), m
								.getSide_decimal()));
				feelist.add(m);
			} else {
				m.setFee(BigDecimal.ZERO);
				feelist.add(m);
			}
		}
		return feelist;
	}

	// 计算社保各项目缴交额(可自定义算法)
	protected List<SocialInsuranceClassInfoViewModel> sumSbItemFee(int soin_id,
			BigDecimal sbbasic, Date date) {
		List<SocialInsuranceClassInfoViewModel> feelist = new ArrayList<SocialInsuranceClassInfoViewModel>();
		List<SocialInsuranceClassInfoViewModel> sblist = getSiAlInfo(soin_id,
				date, "社会保险");
		BigDecimal basic = new BigDecimal(0);
		for (SocialInsuranceClassInfoViewModel m : sblist) {
			if (ifSum(m.getSicl_name())) {
				m.setFee(dealDecimal(
						checkBasic(
								calculate(
										m.getSicl_name(),
										m.getSicl_payunit(),
										checkBasic(sbbasic,
												m.getSiai_basic_ud(),
												m.getSiai_basic_dd()),
										m.getSiai_proportion()), m
										.getSiai_deposit_ud(), m
										.getSiai_deposit_dd()), m
								.getSide_decimal()));
				// 获取基数
				basic = checkBasic(sbbasic, m.getSiai_basic_ud(),
						m.getSiai_basic_dd());
				m.setRadix(basic);
				feelist.add(m);
			} else {
				m.setFee(BigDecimal.ZERO);
				feelist.add(m);
			}
		}
		return feelist;
	}

	// 计算公积金各项目缴交额
	protected List<SocialInsuranceClassInfoViewModel> sumGjjItemFee(
			int soin_id, BigDecimal gjjbasic, BigDecimal gjjproprotion,
			Date date) {
		List<SocialInsuranceClassInfoViewModel> feelist = new ArrayList<SocialInsuranceClassInfoViewModel>();
		List<SocialInsuranceClassInfoViewModel> gjjlist = getSiAlInfo(soin_id,
				date, "公积金");
		BigDecimal basic = new BigDecimal(0);
		BigDecimal fee;
		for (SocialInsuranceClassInfoViewModel m : gjjlist) {
			if (ifSum(m.getSicl_name())) {
				// 获取基数
				basic = checkBasic(gjjbasic, m.getSiai_basic_ud(),
						m.getSiai_basic_dd());
				// 计算
				fee = calculate(basic, gjjproprotion);
				// 校验金额
				fee = dealDecimal(
						checkBasic(fee, m.getSiai_deposit_ud(),
								m.getSiai_deposit_dd()), m.getSide_decimal());

				m.setFee(fee);
				m.setRadix(basic);
				feelist.add(m);
			} else {
				m.setFee(BigDecimal.ZERO);
				feelist.add(m);
			}
		}
		return feelist;
	}

	// 计算社保缴交额
	protected BigDecimal sumSbFee(int soin_id, BigDecimal sbbasic, Date date) {
		List<SocialInsuranceClassInfoViewModel> sblist = getSiAlInfo(soin_id,
				date, "社会保险");
		BigDecimal sbfee = new BigDecimal(0);
		for (SocialInsuranceClassInfoViewModel m : sblist) {
			sbfee = sbfee.add(dealDecimal(
					checkBasic(
							calculate(
									checkBasic(sbbasic, m.getSiai_basic_ud(),
											m.getSiai_basic_dd()),
									m.getSiai_proportion()),
							m.getSiai_deposit_ud(), m.getSiai_deposit_dd()),
					m.getSide_decimal()));
		}
		return sbfee;
	}

	// 计算公积金缴交额
	protected BigDecimal sumGjjFee(int soin_id, BigDecimal gjjbasic,
			BigDecimal gjjproprotion, Date date) {
		List<SocialInsuranceClassInfoViewModel> gjjlist = getSiAlInfo(soin_id,
				date, "公积金");
		BigDecimal gjjfee = new BigDecimal(0);
		for (SocialInsuranceClassInfoViewModel m : gjjlist) {
			if (!"".equals(m.getSiai_proportion())) {
				gjjfee = gjjfee.add(dealDecimal(
						checkBasic(
								calculate(
										checkBasic(gjjbasic,
												m.getSiai_basic_ud(),
												m.getSiai_basic_dd()),
										gjjproprotion), m.getSiai_deposit_ud(),
								m.getSiai_deposit_dd()), m.getSide_decimal()));
			}
		}
		return gjjfee;
	}

	// 计算（基数*比例）
	protected BigDecimal calculate(BigDecimal basic, String proprotion) {
		if (proprotion == null || "".equals(proprotion)) {
			return BigDecimal.ZERO;
		} else {
			BigDecimal bdAdd = BigDecimal.ZERO;
			if (proprotion.contains("+")) {
				bdAdd = new BigDecimal(proprotion.substring(
						proprotion.indexOf("+") + 1, proprotion.length()));
			}
			BigDecimal bdproprotion = dealProprotion(proprotion);
			return basic.multiply(bdproprotion).add(bdAdd);
		}
	}

	// 计算（基数*比例）
	protected BigDecimal calculate(BigDecimal basic, BigDecimal proprotion) {
		if (proprotion == null || "".equals(proprotion)) {
			return BigDecimal.ZERO;
		} else {
			return basic.multiply(proprotion);
		}
	}

	// 计算（设置算法计算 默认为：基数*比例）
	protected BigDecimal calculate(String sicl_name, String sicl_payunit,
			BigDecimal basic, String proprotion) {
		String key = selCalculationMethod(sicl_name, sicl_payunit);
		BigDecimal bd = null;
		if (methodMap.containsKey(key)) {
			String method = methodMap.get(key)[0];
			String n = methodMap.get(key)[1];
			bd = calculate(method, basic, proprotion, n);
			methodMap.remove(key);
		} else {
			bd = calculate(basic, proprotion);
		}
		return bd;
	}

	// 根据自定义算法计算
	protected BigDecimal calculate(String method, BigDecimal basic,
			String proprotion, String n) {
		BigDecimal bd = null;
		switch (method) {
		case "replaceProprotion":
			bd = calculate(basic, n);
			break;
		case "multiply":
			bd = calculate(calculate(basic, proprotion), n);
			break;
		}
		return bd;
	}

	// 根据项目查找自定义算法名称
	protected String selCalculationMethod(String sicl_name, String sicl_payunit) {
		String methodName = "";
		switch (sicl_name) {
		case "养老保险":
			switch (sicl_payunit) {
			case "企业":
				methodName = "ylCp";
				break;
			case "个人":
				methodName = "ylOp";
				break;
			}
			break;
		case "医疗保险":
			switch (sicl_payunit) {
			case "企业":
				methodName = "jlCp";
				break;
			case "个人":
				methodName = "jlOp";
				break;
			}
			break;
		case "大病医疗":
			switch (sicl_payunit) {
			case "企业":
				methodName = "dbCp";
				break;
			case "个人":
				methodName = "dbOp";
				break;
			}
			break;
		case "生育保险":
			switch (sicl_payunit) {
			case "企业":
				methodName = "syuCp";
				break;
			case "个人":
				methodName = "syuOp";
				break;
			}
			break;
		case "工伤保险":
			switch (sicl_payunit) {
			case "企业":
				methodName = "gsCp";
				break;
			case "个人":
				methodName = "gsOp";
				break;
			}
			break;
		case "失业保险":
			switch (sicl_payunit) {
			case "企业":
				methodName = "syeCp";
				break;
			case "个人":
				methodName = "syeOp";
				break;
			}
			break;
		case "住房公积金":
			switch (sicl_payunit) {
			case "企业":
				methodName = "zfgjjCp";
				break;
			case "个人":
				methodName = "zfgjjOp";
				break;
			}
			break;
		case "补充公积金":
			switch (sicl_payunit) {
			case "企业":
				methodName = "bcgjjCp";
				break;
			case "个人":
				methodName = "bcgjjOp";
				break;
			}
			break;
		}
		return methodName;
	}

	// 上下限校验
	protected BigDecimal checkBasic(BigDecimal basic, BigDecimal u, BigDecimal d) {
		BigDecimal b = basic;
		if (basic.compareTo(d) == -1) {
			b = d;
		} else if (u.compareTo(new BigDecimal(0)) != 0) {
			if (basic.compareTo(u) == 1)
				b = u;
		}
		return b;
	}

	// 小数处理
	protected BigDecimal dealDecimal(BigDecimal fee, String decimal) {
		BigDecimal f = fee;
		switch (decimal) {
		case "四舍五入到元":
			f = f.setScale(0, BigDecimal.ROUND_HALF_UP);
			break;
		case "四舍五入到角":
			f = f.setScale(1, BigDecimal.ROUND_HALF_UP);
			break;
		case "四舍五入到分":
			f = f.setScale(2, BigDecimal.ROUND_HALF_UP);
			break;
		case "逢角分进元":
			f = f.setScale(0, BigDecimal.ROUND_UP);
			break;
		case "不进位偶数":

			break;
		case "见分进角":
			f = f.setScale(1, BigDecimal.ROUND_UP);
			break;
		case "见角进元":
			f = f.setScale(1, BigDecimal.ROUND_DOWN);
			f = f.setScale(0, BigDecimal.ROUND_UP);
			break;
		case "见角四舍五入到元":
			f = f.setScale(0, BigDecimal.ROUND_HALF_UP);
			break;
		case "进位到元":

			break;
		case "进位偶数":

			break;
		case "奇偶进位到元":

			break;
		case "舍去分":
			f = f.setScale(1, BigDecimal.ROUND_DOWN);
			break;
		case "舍去角":
			f = f.setScale(0, BigDecimal.ROUND_DOWN);
			break;
		case "本地三档公司医疗进位":
			f = f.setScale(2, BigDecimal.ROUND_HALF_UP).add(
					new BigDecimal("0.01"));
			break;
		case "本地二档公司医疗进位":
			f = f.setScale(2, BigDecimal.ROUND_HALF_UP).subtract(
					new BigDecimal("0.01"));
			break;
		}
		// f = fee.setScale(2, BigDecimal.ROUND_DOWN);
		return new BigDecimal(df.format(f));
	}

	// 比例处理
	public BigDecimal dealProprotion(String proprotion) {
		BigDecimal bdproprotion;
		try {
			if (proprotion.contains("+"))
				proprotion = proprotion.substring(0, proprotion.indexOf("+"));
			if (proprotion.contains("%")) {
				// 如果比例含有百分号，数值除以100；
				proprotion = proprotion.replace("%", "");
				bdproprotion = new BigDecimal(proprotion);
				bdproprotion = bdproprotion.divide(new BigDecimal("100"));
			} else {
				bdproprotion = new BigDecimal(proprotion);
			}
		} catch (Exception e) {
			bdproprotion = new BigDecimal(0);
		}
		return bdproprotion;
	}

	// 判断该项目是否需要计算
	protected boolean ifSum(String name) {
		boolean b = true;
		switch (name) {
		case "养老保险":
			if (!yl)
				b = false;
			break;
		case "医疗保险":
			if (!jl)
				b = false;
			break;
		case "大病医疗":
			if (!db)
				b = false;
			break;
		case "生育保险":
			if (!syu)
				b = false;
			break;
		case "工伤保险":
			if (!gs)
				b = false;
			break;
		case "失业保险":
			if (!sye)
				b = false;
			break;
		case "住房公积金":
			if (!zfgjj)
				b = false;
			break;
		case "补充公积金":
			if (!bcgjj)
				b = false;
			break;
		}
		return b;
	}

	// 查询所属项目的社保项目信息
	public List<SocialInsuranceClassInfoViewModel> getSiAlInfo(int soin_id,
			Date execdate, String classtype) {
		SocialInsuranceCalculatorDal dal = new SocialInsuranceCalculatorDal();
		List<SocialInsuranceClassInfoViewModel> list = dal.getSiAlInfo(soin_id,
				execdate, classtype);
		return list;
	}

	// 根据soin_id获取算法所有项目详细信息返回Map
	public Map<String, SocialInsuranceClassInfoViewModel> getSiAlInfo(
			int soin_id, Date execdate) {
		SocialInsuranceCalculatorDal dal = new SocialInsuranceCalculatorDal();
		return dal.getSiAlInfo(soin_id, execdate);
	}

	// 获取社保字典库项目类型
	protected Map<Integer, String[]> getSocialInsuranceClass() {
		SocialInsuranceCalculatorDal dal = new SocialInsuranceCalculatorDal();
		return dal.getSocialInsuranceClass();
	}

	// 转换计算方式
	protected String transformationCalculationMethod(
			calculationMethod calculationMethod) {
		String method = "default";
		switch (calculationMethod) {
		case replaceProprotion:
			method = "replaceProprotion";
			break;
		case multiply:
			method = "multiply";
			break;
		}
		return method;
	}

	// 转换项目名称
	protected String transformationItem(item item) {
		String name = "";
		switch (item) {
		case ylCp:
			name = "ylCp";
			break;
		case ylOp:
			name = "ylOp";
			break;
		case jlCp:
			name = "jlCp";
			break;
		case jlOp:
			name = "jlOp";
			break;
		case dbCp:
			name = "dbCp";
			break;
		case dbOp:
			name = "dbOp";
			break;
		case syuCp:
			name = "syuCp";
			break;
		case syuOp:
			name = "syuOp";
			break;
		case syeCp:
			name = "syeCp";
			break;
		case syeOp:
			name = "syeOp";
			break;
		case gsCp:
			name = "gsCp";
			break;
		case gsOp:
			name = "gsOp";
			break;
		case zfgjjCp:
			name = "zfgjjCp";
			break;
		case zfgjjOp:
			name = "zfgjjOp";
			break;
		case bcgjjCp:
			name = "bcgjjCp";
			break;
		case bcgjjOp:
			name = "bcgjjOp";
			break;
		}
		return name;
	}

	public void setYl(boolean yl) {
		this.yl = yl;
	}

	public void setJl(boolean jl) {
		this.jl = jl;
	}

	public void setSyu(boolean syu) {
		this.syu = syu;
	}

	public void setSye(boolean sye) {
		this.sye = sye;
	}

	public void setGs(boolean gs) {
		this.gs = gs;
	}

	public void setZfgjj(boolean zfgjj) {
		this.zfgjj = zfgjj;
	}

	public void setBcgjj(boolean bcgjj) {
		this.bcgjj = bcgjj;
	}

	public void setDb(boolean db) {
		this.db = db;
	}

}
