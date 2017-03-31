/**
 * @Classname DocumentsInfo_AddBll
 * @ClassInfo 材料模块新增页面
 * @author 张志强
 * @Date 2013-10-31
 */
package bll.DocumentsInfo;

import Model.DocumentsInfoAddModel;
import dal.DocumentsInfo.DocumentsInfo_AddDal;

public class DocumentsInfo_AddBll {
	private Integer y;
	private Integer y1;
	private DocumentsInfo_AddDal data;
	private DocumentsInfoAddModel chk;
	private DocumentsInfo_AddDal data2;
	private DocumentsInfoAddModel add;
	
	public void DocumentsInfo_AddBllCF(String doin_content,int doin_type) {
		chk=new DocumentsInfoAddModel(0,doin_type,doin_content, "",1);
	}
	
	public void DocumentsInfo_AddBllADD(String doin_content,String doin_remark,int doin_type) {
		add=new DocumentsInfoAddModel(0,doin_type,doin_content, doin_remark,1);
	}

	public int Dochek() {
		data = new DocumentsInfo_AddDal();

		int y = 0;
		try {
			y = data.AddDocuments(add);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}
	
	public int DochekCF() {
		data2 = new DocumentsInfo_AddDal();

		int y1 = 0;
		try {
			y1 = data2.AddDocumentsCF(chk);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y1;
	}

	public void setY1(Integer y1) {
		this.y1 = y1;
	}
}
