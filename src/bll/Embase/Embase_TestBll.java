package bll.Embase;

import dal.Embase.Embase_TestDal;
import Model.EmbaseModel;
import Util.Page;

/**
 * 描述：员工信息 -- 分页功能测试页面逻辑层
 * @author suhongyuan
 * @create 2016-06-15
 */
public class Embase_TestBll {
  //获取当前页面记录
  public Page<EmbaseModel> pagingSearchEmbase(String key,Page<EmbaseModel> p){
	  Embase_TestDal dal= new Embase_TestDal();
	return dal.pagingSearchEmbase(key, p);
  }
  //获取总记录数
  public int toalCount(){
	  Embase_TestDal dal= new Embase_TestDal();
     return dal.total();
  }
}
