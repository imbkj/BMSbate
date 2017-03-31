package bll;

import dal.PrintDal;

/**
 * 
 * @author 苏宏远
 * @create 2016-08-31
 */
public class PrintBll {
//查询是否存在记录
private	PrintDal pdal=new PrintDal();
public int isExit(String sql){
	return pdal.ISExit(sql);
}
}
