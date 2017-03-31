package bll.EmBodyCheck;

import java.math.BigDecimal;
import java.util.List;

import dal.EmBodyCheck.EmbcItem_OperateDal;
import Model.EmBcItemGroupModel;
import Model.EmBodyCheckItemModel;
import Model.EmBodyCheckModel;

public class EmbcItem_OperateBll {
	EmbcItem_OperateDal dal=new EmbcItem_OperateDal();
	//新增体检项目
	public Integer EmBodyCheckItemAdd(EmBodyCheckItemModel m) {
		return dal.EmBodyCheckItemAdd(m);
	}
	
	//修改体检项目
	public Integer EmBodyCheckItemUpdate(EmBodyCheckItemModel m) {
		return dal.EmBodyCheckItemUpdate(m);
	}
	
	//体检项目组合新增
	public Integer ItemGroupAdd(EmBcItemGroupModel m) {
		return dal.ItemGroupAdd(m);
	}
	
	//体检项目组合修改
	public Integer modGroup(EmBcItemGroupModel em,List<EmBodyCheckItemModel> list){
		Integer i=0;
		EmbcItem_OperateDal dal = new EmbcItem_OperateDal();
		BigDecimal charge = new BigDecimal(0);
		BigDecimal discount = new BigDecimal(0);
		
		for (int j = 0; j < list.size(); j++) {
			charge=charge.add(list.get(j).getEbit_charge());
			discount=discount.add(list.get(j).getEbit_discount());
		}
		em.setEbig_charge(charge);
		em.setEbig_discount(discount);
		i=dal.ItemGroupEdit(em, list);
		return i;
	}
	
	//添加组合与项目的关系
	public int AddEmbcIGList(Integer ebit_id,Integer id,String addname){
		return dal.ItemGroupInfoAdd(ebit_id, id, addname);
	}
	
	//删除某个组合的关系信息
	public int delEmbcIGList(int eigl_ebig_id){
		return dal.delEmbcIGList(eigl_ebig_id);
	}
}
