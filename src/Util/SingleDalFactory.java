package Util;

import dal.CoFinanceManage.Cfma_SelDal;
import dal.CoHousingFund.CoHousingFundZbDal;
import dal.CoHousingFund.CoHousingFund_PwdDal;

public class SingleDalFactory {
	private volatile static SingleDalFactory instance = null;

	// 声明dal
	private CoHousingFundZbDal chzd = null;
	private CoHousingFund_PwdDal cfpd = null;
	private Cfma_SelDal csd = null;

	private SingleDalFactory() {
		// 实例化dal
		chzd = new CoHousingFundZbDal();
		cfpd = new CoHousingFund_PwdDal();
		csd = new Cfma_SelDal();
	}

	public static SingleDalFactory getInstance() {
		// 判断实例是否存在
		if (instance == null) {
			// 如果不存在就上同步锁，保证线程安全
			synchronized (SingleDalFactory.class) {
				// 再次检查实例是否存在，如果还不存在就可以创建实例了
				if ((instance == null)) {
					instance = new SingleDalFactory();
				}
			}
		}
		return instance;
	}
	
	
	
	public Cfma_SelDal getCsd() {
		return csd;
	}

	public void setCsd(Cfma_SelDal csd) {
		this.csd = csd;
	}

	public CoHousingFund_PwdDal getCfpd() {
		return cfpd;
	}

	public void setCfpd(CoHousingFund_PwdDal cfpd) {
		this.cfpd = cfpd;
	}

	public CoHousingFundZbDal getChzd() {
		return chzd;
	}

	public void setChzd(CoHousingFundZbDal chzd) {
		this.chzd = chzd;
	}

}
