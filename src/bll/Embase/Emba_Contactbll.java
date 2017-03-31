package bll.Embase;

import java.util.List;

import Model.Emcontactinfo;
import dal.Embase.Emba_Contactdal;

public class Emba_Contactbll {

	// 获取联系记录model
	public Emcontactinfo getemcontactmodel(Integer gid) {
		Emba_Contactdal dal = new Emba_Contactdal();
		return dal.getemcontactmodel(gid);
	}

	// 修改一条记录
	public Integer addembacontactinfo(Emcontactinfo em) {
		Emba_Contactdal dal = new Emba_Contactdal();
		return dal.addembacontactinfo(em);
	}

	/**
	 * 修改基本信息表
	 * 
	 * @param em
	 * @return 更新结果
	 */
	public boolean updateEmbaInfo(Emcontactinfo em) {
		boolean flag = true;
		Emba_Contactdal dal = new Emba_Contactdal();
		int row = dal.updateEmbaInfo(em);
		if (row > 0) {
			flag = false;
		}
		return flag;
	}

	public int updateEmcontent(Emcontactinfo em) {
		Emba_Contactdal dal = new Emba_Contactdal();
		return dal.updateEmcontent(em);
	}
}
