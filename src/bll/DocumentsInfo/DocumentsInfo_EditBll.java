/**
 * @Classname DocumentsInfo_EditBll
 * @ClassInfo 材料模块修改页面
 * @author 张志强
 * @Date 2013-10-31
 */
package bll.DocumentsInfo;

import Model.DocumentsInfoAddModel;
import dal.DocumentsInfo.DocumentsInfo_EditDal;

public class DocumentsInfo_EditBll {
	private Integer y;
	private DocumentsInfo_EditDal data;
	private DocumentsInfoAddModel reg;
	
	public DocumentsInfo_EditBll(String doin_remark,String doin_content,int doin_id,int doin_type,int doin_state) {
		
		reg=new DocumentsInfoAddModel(doin_id,doin_type,doin_content, doin_remark,doin_state);
	}

	public int Dochek() {
		data = new DocumentsInfo_EditDal();

		int y = 0;
		try {
			y = data.editrol(reg);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

}
