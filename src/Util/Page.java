package Util;

import java.util.List;

public class Page<T> {
/**
 * 工具类，保存一次分页查询结果集的基本信息
 * @author 苏宏远
 * @created 2016-06-15
 * @param <T>
 */
 private int totalSize;//总记录数
 private int pageCount;//总页数，通过计算得出
 private int currentPageIndex;//当前页码
 private int pageSize;//每页记录数
 private List<T> dataList;//当前页记录
 
public Page() {
	super();
	this.totalSize = totalSize;
	this.pageCount = pageCount;
	this.currentPageIndex = currentPageIndex;
	this.pageSize = pageSize;
	this.dataList = dataList;
	//this.pageCount=totalSize / pageSize +(totalSize % pageSize==0 ? 0:1);
}

public int getTotalSize() {
	return totalSize;
}
public void setTotalSize(int totalSize) {
	this.totalSize = totalSize<=0 ? 0:totalSize;
}
public int getPageCount() {
	return pageCount;
}
public void setPageCount(int pageCount) {
	this.pageCount = pageCount;
}
public int getCurrentPageIndex() {
	return currentPageIndex;
}
public void setCurrentPageIndex(int currentPageIndex) {
	this.currentPageIndex = currentPageIndex<=0 ? 0:currentPageIndex;
}
public int getPageSize() {
	return pageSize;
}
public void setPageSize(int pageSize) {
	this.pageSize = pageSize <=0 ? 0:pageSize;
}
public List<T> getDataList() {
	return dataList;
}
public void setDataList(List<T> dataList) {
	this.dataList = dataList;
}
 
}
