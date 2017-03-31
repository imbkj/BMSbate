package bll.EmCensus.EmDh;

import Model.EmDhModel;

public class EmDh_LxOpeateReturnByDhTypeImpl implements EmDh_LxOpeate{
	/**
	 * 
	 * @author chenyaojia
	 * @Description:调户方式不同时重置流程
	 */
	@Override
	public String[] edit(EmDhModel m) {
		String[] str = new String[5];
		String sql = "";
		if (m.getEmdh_dhtype().equals("毕业生接收")) {
			sql = ",emdh_doc=20,emdh_dhtype='" + m.getEmdh_dhtype()
					+ "',emdh_state=1,emdh_zhtype='" + m.getEmdh_zhtype() + "'";
		} else {
			sql = ",emdh_doc=18,emdh_dhtype='" + m.getEmdh_dhtype()
					+ "',emdh_state=1,emdh_zhtype='" + m.getEmdh_zhtype() + "'";
		}
		final EmDh_LxOperateBll bll = new EmDh_LxOperateBll();
		str = bll.ReturnStep(m, sql);
		return str;
	}


}
