/**
 * @Classname DocumentsLogController
 * @ClassInfo 材料交接记录页面Controller
 * @author 林少斌
 * @Date 2013-11-1
 */
package Controller.DocumentsInfo;

import impl.DocumentsInfoImpl;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import service.DocumentsInfoService;

public class DocumentsLogController {
	
	DocumentsInfoService docS = new DocumentsInfoImpl();
	private List docLogList;
	int dsin_id=(Integer)Executions.getCurrent().getArg().get("dsin_id"); //材料交接情况表id
	
	public DocumentsLogController() {
		//获取交接记录
		docLogList=docS.getDocLog(dsin_id);
	}

	public List getDocLogList() {
		return docLogList;
	}

	public void setDocLogList(List docLogList) {
		this.docLogList = docLogList;
	}
	
	
}
