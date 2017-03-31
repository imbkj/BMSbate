package bll.EmCommissionOut;
import dal.EmCommissionOut.EmCmmissionyearchangeupdateDal;

public class EmCmmissionyearchangeupdateBll {

	/**
	 * @param args
	 */
	private EmCmmissionyearchangeupdateDal dal = new EmCmmissionyearchangeupdateDal();
	
	//更新年调数据
	public int updateyeardata(String sqlstr)
	{
		int i=0;
		i=dal.updateyeardata(sqlstr);
		return i;
	}

}
