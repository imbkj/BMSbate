package bll.EmBenefit;

public class EmActy_NewOperateFactoryImpl implements EmActy_NewOperateFactory{
	private EmActy_NewOperateService service;
	@Override
	public EmActy_NewOperateService operateFactory(String type) {
		switch (type) {
		case "下一步":
			service=new EmActy_NewOperateServicePassToNextImpl();
			break;
		case "退回指定步骤":
			service=new EmActy_NewOperateServiceReturnToNImpl();
			break;
		case "退回上一步":
			service=new EmActy_NewOperateServiceReturnToPreImpl();
			break;
		case "跳过下一步":
			service=new EmActy_NewOperateServiceSkipToNextImpl();
			break;
		case "跳到指定步骤":
			service=new EmActy_NewOperateServiceSkipToNImpl();
			break;
		default:
			break;
		}
		return service;
	}
}
