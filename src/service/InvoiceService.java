package service;

import java.util.List;

import Model.CoFinanceCollectModel;
import Model.CoInvoiceInfoModel;
import Model.CoInvoiceModel;
import Model.CoInvoiceRelationModel;

public interface InvoiceService {

	/**
	 * @Title: search
	 * @Description: TODO(查询发票)
	 * @param id
	 *            发票ID
	 * @return
	 * @return List<CoInvoiceModel> 返回类型
	 * @throws
	 */
	public List<CoInvoiceModel> search(Integer id);

	/**
	 * @Title: search
	 * @Description: TODO(查询发票)
	 * @param cm
	 * @return
	 * @return List<CoInvoiceModel> 返回类型
	 * @throws
	 */
	public List<CoInvoiceModel> search(CoInvoiceModel cm);

	/**
	 * @Title: searchDetail
	 * @Description: TODO(查询发票明细)
	 * @param id
	 *            发票明细ID
	 * @return
	 * @return List<CoInvoiceInfoModel> 返回类型
	 * @throws
	 */
	public List<CoInvoiceInfoModel> searchDetail(Integer id);

	/**
	 * @Title: searchRelation
	 * @Description: TODO(查询发票与收款关系)
	 * @param id
	 * @return
	 * @return List<CoInvoiceInfoModel> 返回类型
	 * @throws
	 */
	public List<CoInvoiceRelationModel> searchRelation(Integer id);

	/** 
	* @Title: createInvoiceId 
	* @Description: TODO(生成发票编号) 
	* @return
	* @return String    返回类型 
	* @throws 
	*/
	public String createInvoiceId();
	
	/**
	 * @Title: createInvoice
	 * @Description: TODO(生成发票记录)
	 * @param name
	 * @return
	 * @return List<CoInvoiceInfoModel> 返回类型
	 * @throws
	 */
	public List<CoInvoiceInfoModel> createInvoice(Integer cid);

	/**
	 * @Title: createInvoice
	 * @Description: TODO(根据多个账单号生成发票记录)
	 * @param list
	 * @return
	 * @return List<CoInvoiceInfoModel> 返回类型
	 * @throws
	 */
	public List<CoInvoiceInfoModel> createInvoice(List<String> list);
	
	/** 
	* @Title: createInvoice2 
	* @Description: TODO(根据收款列表ID生成发票记录) 
	* @param list
	* @return
	* @return List<CoInvoiceInfoModel>    返回类型 
	* @throws 
	*/
	public List<CoInvoiceInfoModel> createInvoice2(List<CoFinanceCollectModel> list);

	/**
	 * @Title: add
	 * @Description: TODO(添加发票)
	 * @param cm
	 * @param list
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer add(CoInvoiceModel cm, List<CoInvoiceInfoModel> list,
			List<CoFinanceCollectModel> list2);

	/**
	 * @Title: add
	 * @Description: TODO(添加发票明细)
	 * @param id
	 *            发票明细ID
	 * @param list
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer add(Integer id, List<CoInvoiceInfoModel> list);

	/**
	 * @Title: add
	 * @Description: TODO(添加发票明细)
	 * @param id
	 * @param cm
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer add(Integer id, CoInvoiceInfoModel cm);

	/**
	 * @Title: add
	 * @Description: TODO(关联发票和收款)
	 * @param inId
	 * @param iiId
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer add(Integer coinId, Integer cfcoId);

	/**
	 * @Title: mod
	 * @Description: TODO(修改发票信息)
	 * @param id
	 *            发票ID
	 * @param cm
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer mod(Integer id, CoInvoiceModel cm);

	/**
	 * @Title: mod
	 * @Description: TODO(修改发票信息明细)
	 * @param id
	 *            发票ID
	 * @param cm
	 * @param list
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer mod(Integer id, CoInvoiceModel cm,
			List<CoInvoiceInfoModel> list);

	/** 
	* @Title: mod 
	* @Description: TODO(修改发票信息) 
	* @param id
	* @param cm
	* @param list
	* @param list2
	* @return
	* @return Integer    返回类型 
	* @throws 
	*/
	public Integer mod(Integer id, CoInvoiceModel cm,
			List<CoInvoiceInfoModel> list, List<CoFinanceCollectModel> list2);

	/**
	 * @Title: mod
	 * @Description: TODO(修改发票明细)
	 * @param id
	 *            发票明细ID
	 * @param cm
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer mod(Integer id, CoInvoiceInfoModel cm);

	/**
	 * @Title: delInvoice
	 * @Description: TODO(删除发票)
	 * @param id
	 *            发票ID
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer delInvoice(Integer id);

	/**
	 * @Title: delDetail
	 * @Description: TODO(删除发票明细)
	 * @param id
	 *            发票明细ID
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer delDetail(Integer id);

	/**
	 * @Title: delDetail
	 * @Description: TODO(删除发票明细)
	 * @param cm
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer delDetail(CoInvoiceInfoModel cm);

	/**
	 * @Title: delRelation
	 * @Description: TODO(删除发票和收款关系)
	 * @param id
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer DelRelation(CoInvoiceRelationModel cm);

}
