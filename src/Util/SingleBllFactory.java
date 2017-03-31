package Util;

import Model.CoHousingFundModel;
import bll.CoFinanceManage.Cfma_SelBll;
import bll.CoHousingFund.CoHousingFundZbBll;
import bll.CoHousingFund.CoHousingFund_PwdBll;

public class SingleBllFactory {

	private volatile static SingleBllFactory instance = null;

	// 声明dal
	private CoHousingFundZbBll chzb = null;
	private CoHousingFund_PwdBll cfpb = null;
	private Cfma_SelBll csb = null;

	private SingleBllFactory() {
		// 实例化bll
		chzb = new CoHousingFundZbBll();
		cfpb = new CoHousingFund_PwdBll();
		csb = new Cfma_SelBll();
	}

	public static SingleBllFactory getInstance() {
		// 判断实例是否存在
		if (instance == null) {
			// 如果不存在就上同步锁，保证线程安全
			synchronized (SingleBllFactory.class) {
				// 再次检查实例是否存在，如果还不存在就可以创建实例了
				if ((instance == null)) {
					instance = new SingleBllFactory();
				}
			}
		}
		return instance;
	}

	
	public Cfma_SelBll getCsb() {
		return csb;
	}

	public void setCsb(Cfma_SelBll csb) {
		this.csb = csb;
	}

	public CoHousingFundZbBll getChzb() {
		return chzb;
	}

	public void setChzb(CoHousingFundZbBll chzb) {
		this.chzb = chzb;
	}

	public CoHousingFund_PwdBll getCfpb() {
		return cfpb;
	}

	public void setCfpb(CoHousingFund_PwdBll cfpb) {
		this.cfpb = cfpb;
	}

}
