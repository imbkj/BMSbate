package service;

/*
 * Classname:快件信息接口；
 * 
 * Author；陈耀家；
 * 
 * Create date:2014-4-29
 */
public interface EmExpressService {
	/*
	 * @Methodname:新增个人快件信息
	 * 
	 * @input:
	 * expr_exct_id：收件地址信息表id；cid:单位id；gid：员工编号；expr_content:邮寄内容(邮寄物件)；expr_rank:快件等级(普通或者紧急);
	 * expr_class:快件类型（公司或者个人）;expr_addname:添加人
	 * 
	 * @out: 大于0：成功；小于0:失败；
	 */
	public Integer addEmbaseExpress(Integer expr_exct_id,Integer gid,String expr_content,
			String expr_rank);
	/*
	 * @Methodname:新增快件信息
	 * 
	 * @input:
	 * expr_exct_id：收件地址信息表id；cid:单位id；gid：员工编号；expr_content:邮寄内容(邮寄物件)；expr_rank:快件等级(普通或者紧急);
	 * expr_class:快件类型（公司或者个人）;expr_addname:添加人
	 * 
	 * @out: 大于0：成功；小于0:失败；
	 */
	public Integer addCobaseExpress(Integer expr_exct_id,Integer cid,String expr_content,
			String expr_rank);
}
