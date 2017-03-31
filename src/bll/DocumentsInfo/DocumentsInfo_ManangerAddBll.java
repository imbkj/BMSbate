/**
 * @Classname DocumentsInfo_ListBll
 * @ClassInfo 材料模块列表分配页面
 * @author 张志强
 * @Date 2013-10-31
 */
package bll.DocumentsInfo;

import Model.DocumentsInfoAddModel;
import dal.DocumentsInfo.DocumentsInfo_ManangerAddDal;

public class DocumentsInfo_ManangerAddBll {
	private Integer y;
	private DocumentsInfo_ManangerAddDal data;
	private DocumentsInfoAddModel reg;
	
	public DocumentsInfo_ManangerAddBll(String doin_content,Integer doin_type,Integer iht,Integer doin_id,String zul_id) {
		reg=new DocumentsInfoAddModel(doin_id, doin_type, doin_content, zul_id, iht);
	}

	public int Dochek() {
		data = new DocumentsInfo_ManangerAddDal();

		int y = 0;
		try {
			y = data.Addmang(reg);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y;
	
	}

	public void setY(Integer y) {
		this.y = y;
	}
}
