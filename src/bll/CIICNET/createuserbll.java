package bll.CIICNET;
import Model.EmbaseModel;
import dal.CIICNET.createuserdal;

import dal.Embase.Embasedal;
import java.util.List;

public class createuserbll {
	private createuserdal createuserd = new createuserdal();
	private Embasedal emdal =new Embasedal();

	/**
	 * 检查是否有账号
	 */
	  
	public int checkuser(String idcard)
	{
		
		return createuserd.checkuser(idcard);
	}
	

	/**
	 * 创建账号
	 * @throws Exception 
	 */
	public int createuser(EmbaseModel m) throws Exception
	{
		return createuserd.createuser(m);
	}
	
	/**
	 * 获取网上中智员工信息
	 */
	public List<EmbaseModel> getembasemodel(int gid)
	{
		return createuserd.getEmBaseById(gid);
	}
	
	/**
	 * 更新信息
	 */
	public Integer updateembase(EmbaseModel m)
	{
		return emdal.modInfo(m);
	}
	
	/**
	 * 更新BMS创建账号状态
	 */
	

}
