package service;

public interface ExcelService {

	// 写入excel
	public abstract void writeExcel() throws Exception;

	// 前道导出体检EXCEL文件
	public void exportExcel() throws Exception;

}
