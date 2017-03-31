package Controller.EmCensus.EmDh;

public class EmDh_OpenFactoryImpl implements EmDh_OpenFactory {
	private String openType="";
	private EmDh_OpenZul openZul;
	public EmDh_OpenFactoryImpl(String openType)
	{
		this.openType=openType;
	}
	@Override
	public EmDh_OpenZul OpenZulFactory() {
		switch (openType) {
		case "备注":
			openZul=new EmDh_OpenRemarkImpl();
			break;
		case "联系员工":
			openZul=new EmDh_OpenLinkImpl();
			break;
		default:
			break;
		}
		return openZul;
	}


}
