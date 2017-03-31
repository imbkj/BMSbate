package Model;


import java.util.List;

import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;


public class ExcelFileModel {
	private String filename;
	private String exportPath;
	private String templetFileName;
	private String templetPath;
	private String[][] columnName;
	private List<?> list;

	private String RowIndex;
	private String CellIndex;

	private WritableCellFormat ColumnsWCF;
	private WritableFont ColumnsWf;

	private WritableCellFormat ContentWCF;
	private WritableFont ContentWf;

	

	public ExcelFileModel() {
		this.ColumnsWf=new WritableFont(WritableFont.createFont("微软雅黑"), 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
		this.ColumnsWCF=new WritableCellFormat(ColumnsWf);
		try {
			this.ColumnsWCF.setBackground(Colour.WHITE);
			this.ColumnsWCF.setBorder(Border.ALL, BorderLineStyle.THIN,
					Colour.BLACK);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getTempletFileName() {
		return templetFileName;
	}

	public void setTempletFileName(String templetFileName) {
		this.templetFileName = templetFileName;
	}

	public String getTempletPath() {
		return templetPath;
	}

	public void setTempletPath(String templetPath) {
		this.templetPath = templetPath;
	}

	public String getExportPath() {
		return exportPath;
	}

	public void setExportPath(String exportPath) {
		this.exportPath = exportPath;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public String getRowIndex() {
		return RowIndex;
	}

	public void setRowIndex(String rowIndex) {
		RowIndex = rowIndex;
	}

	public String getCellIndex() {
		return CellIndex;
	}

	public void setCellIndex(String cellIndex) {
		CellIndex = cellIndex;
	}

	public WritableCellFormat getColumnsWCF() {
		return ColumnsWCF;
	}

	public void setColumnsWCF(WritableCellFormat columnsWCF) {
		ColumnsWCF = columnsWCF;
	}

	public WritableFont getColumnsWf() {
		return ColumnsWf;
	}

	public void setColumnsWf(WritableFont columnsWf) {
		ColumnsWf = columnsWf;
	}

	public WritableCellFormat getContentWCF() {
		return ContentWCF;
	}

	public void setContentWCF(WritableCellFormat contentWCF) {
		ContentWCF = contentWCF;
	}

	public WritableFont getContentWf() {
		return ContentWf;
	}

	public void setContentWf(WritableFont contentWf) {
		ContentWf = contentWf;
	}

	public String[][] getColumnName() {
		return columnName;
	}

	public void setColumnName(String[][] columnName) {
		this.columnName = columnName;
	}

	
	
}
