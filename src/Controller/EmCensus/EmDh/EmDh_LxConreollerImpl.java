package Controller.EmCensus.EmDh;

import Model.EmDhModel;
import bll.EmCensus.EmDh.EmDh_LxOpeate;
import bll.EmCensus.EmDh.EmDh_LxOpeateFactory;
import bll.EmCensus.EmDh.EmDh_LxOpeateFactoryImpl;

public class EmDh_LxConreollerImpl implements EmDh_LxConreoller{
	private String type;
	public EmDh_LxConreollerImpl(String type)
	{
		this.type=type;
	}
	@Override
	public String[] edit(EmDhModel m) {
		EmDh_LxOpeateFactory factory=new EmDh_LxOpeateFactoryImpl(type);
		EmDh_LxOpeate lxOperate=factory.OpeateFactory();
		String[] str = lxOperate.edit(m);
		return str;
	}

}
