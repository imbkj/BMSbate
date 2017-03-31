package bll.EmPay;

import service.ExcelService;
import Model.EmPayModel;

public class OutWipeExcelImpl implements ExcelService {
	private EmPayModel model;

	public OutWipeExcelImpl(EmPayModel model) {
		model = model;
	}

	@Override
	public void writeExcel() throws Exception {

	}

}
