package Util;

import service.CoHousingFundZbService;
import impl.CoHousingFundZbImpl;

/**
 * 专办员单例
 * 
 * @author Administrator
 * 
 */
public class SingletonCoHousingFundZb {
	private volatile static SingletonCoHousingFundZb instance = null;

	// 声明bll
	private CoHousingFundZbService chzi = null;

	private SingletonCoHousingFundZb() {
		// 实例化bll
		chzi = new CoHousingFundZbImpl();
	}

	public static SingletonCoHousingFundZb getInstance() {
		// 判断实例是否存在
		if (instance == null) {
			// 如果不存在就上同步锁，保证线程安全
			synchronized (SingletonCoHousingFundZb.class) {
				// 再次检查实例是否存在，如果还不存在就可以创建实例了
				if ((instance == null)) {
					instance = new SingletonCoHousingFundZb();
				}
			}
		}
		return instance;
	}

	// 返回实例

	public CoHousingFundZbService getChzi() {
		return chzi;
	}

	public void setChzi(CoHousingFundZbService chzi) {
		this.chzi = chzi;
	}

}
