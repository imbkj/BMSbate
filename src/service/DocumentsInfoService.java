/**
 * @Classname DocumentsInfoService
 * @ClassInfo 材料模块接口（实现类impl.DocumentsInfoImpl）
 * @author 林少斌
 * @Date 2013-10-29
 */
package service;

import java.util.ArrayList;

import Model.DocumentsHandoverLogModel;
import Model.DocumentsInfoModel;
import Model.DocumentsSubmitInfoModel;

public interface DocumentsInfoService {
	/*
	 * @Methodname:新增页面显示材料数据
	 * 
	 * @input: puzu_id：模块页面表id；str：查询条件；
	 * 
	 * @out: DocumentsInfoModel；
	 */
	ArrayList<DocumentsInfoModel> getAddPageDoc(String puzu_id,String f_puzu_id, String dtype,
			String cgid, String str);

	/*
	 * @Methodname:修改页面显示材料数据
	 * 
	 * @input: puzu_id：模块页面表id；tid：业务表数据id；str：查询条件；
	 * 
	 * @out: DocumentsInfoModel；
	 */
	ArrayList<DocumentsSubmitInfoModel> getUpdatePageDoc(String puzu_id,
			String dtype, String cgid, String tid, String str);

	// 材料修改方法
	/*
	 * @Methodname:业务材料表修改方法
	 * 
	 * @input:
	 * dire_id：材料关系表id；tid：业务表数据id；ifsubmit：提交状态；count：份数；handover_people
	 * ：上一手交接人；addname：添加人
	 * 
	 * @out: int；
	 */
	String[] addDocSubmitInfo(String dire_id, String tid, String ifsubmit,
			String count, String handover_people, String addname);

	/*
	 * @Methodname:显示材料交接记录
	 * 
	 * @input: dsin_id：材料业务提交情况表id；
	 * 
	 * @out: DocumentsHandoverLogModel；
	 */
	ArrayList<DocumentsHandoverLogModel> getDocLog(int dsin_id);

	// 材料修改方法
	/*
	 * @Methodname:业务材料表修改方法
	 * 
	 * @input:
	 * dire_id：材料关系表id；tid：业务表数据id；ifsubmit：提交状态；count：份数；handover_people
	 * ：上一手交接人；addname：添加人；out_count：退回材料份数
	 * 
	 * @out: int；
	 */
	String[] addDocSubmitInfo(String dire_id, String tid, String ifsubmit,
			String count, String handover_people, String addname,
			String out_count);
	
	
	Integer createDocumentInfo(Integer puzu_id,Integer dsin_tid,String username);
	
	//获取材料状态对应ifsubmit
	/*
	 * @Methodname:获取指定模块的材料状态
	 * 
	 * @input: puzu_id：业务表数据id；ifsubmit：当前材料状态；type：类型 h 上一状态 f 下已状态；
	 * 
	 * @out: int；
	 */
	Integer getIfSubmit(Integer puzu_id,Integer ifsubmit,String type);
	
	/*
	 * @Methodname:修改页面显示材料数据
	 * 
	 * @input: puzu_id：模块页面表id；tid：业务表数据id；str：查询条件；
	 * 
	 * @out: DocumentsInfoModel；
	 */
	String[] deleteDoc(String puzu_id, String tid);
	
	/** 
	* @Title: addpageAddDocument 
	* @Description: TODO(新增材料时,当选择"其它"项目,可以根据录入的材料名称自动生成材料数据) 
	* @param name
	* @param puzuId
	* @param addname
	* @return
	* @return Integer    返回类型 
	* @throws 
	*/
	Integer addpageAddDocument(String name,Integer puzuId,String addname);
}
