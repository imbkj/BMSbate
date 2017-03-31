package Util;

import impl.CoHousingFundPwdImpl;
import service.CoHousingFundPwdService;

/**
 * 密钥专办员单例
 * 
 * @author Administrator
 * 
 */
public class SingletonCoHousingFundPwd {

	private volatile static SingletonCoHousingFundPwd instance = null;

	// 声明bll
	private CoHousingFundPwdService chpi = null;

	private SingletonCoHousingFundPwd() {
		// 实例化bll
		chpi = new CoHousingFundPwdImpl();
	}

	public static SingletonCoHousingFundPwd getInstance() {
		// 判断实例是否存在
		if (instance == null) {
			// 如果不存在就上同步锁，保证线程安全
			synchronized (SingletonCoHousingFundPwd.class) {
				// 再次检查实例是否存在，如果还不存在就可以创建实例了
				if ((instance == null)) {
					instance = new SingletonCoHousingFundPwd();
				}
			}
		}
		return instance;
	}

	public CoHousingFundPwdService getChpi() {
		return chpi;
	}

}
