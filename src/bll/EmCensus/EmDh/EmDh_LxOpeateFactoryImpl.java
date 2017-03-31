package bll.EmCensus.EmDh;

public class EmDh_LxOpeateFactoryImpl implements EmDh_LxOpeateFactory{
	private EmDh_LxOpeate lxOperate;
	private String type;
	public EmDh_LxOpeateFactoryImpl(String type)
	{
		this.type=type;
	}
	@Override
	public EmDh_LxOpeate OpeateFactory() {
		switch (type) {
		case "交接材料":
			lxOperate=new EmDh_LxOpeateUpDataImpl();
			break;
		case "调户方式改变":
			lxOperate=new EmDh_LxOpeateReturnByDhTypeImpl();
			break;
		case "条件审核":
			lxOperate=new EmDh_LxOpeateConditionAuditImpl();
			break;
		case "信息预审":
			lxOperate=new EmDh_LxOpeateInfoAuditImpl();
			break;
		case "重置流程":
			lxOperate=new EmDh_LxOpeateReturnImpl();
			break;
		case "预审确认":
			lxOperate=new EmDh_LxOpeateAuditConfirmImpl();
			break;
		case "代理部受理":
			lxOperate=new EmDh_LxOpeateProxyImpl();
			break;
		case "上报材料":
			lxOperate=new EmDh_LxOpeateReportDataImpl();
			break;
		case "介绍信下达":
			lxOperate=new EmDh_LxOpeateIntroductionImpl();
			break;
		case "领取介绍信":
			lxOperate=new EmDh_LxOpeateTakeIntroductionImpl();
			break;
		default:
			break;
		}
		return lxOperate;
	}

}
