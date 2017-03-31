package bll.CoQuotation;

import dal.CoQuotation.CoofferOperateDal;
import Model.CoOfferModel;
import service.WorkflowCore.WfBusinessService;

public class CoofferImpl implements WfBusinessService {
	private CoofferOperateDal dal = new CoofferOperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = new String[5];
		try {
			String type = (String) obj[0];
			CoOfferModel model = (CoOfferModel) obj[1];
			if (type == "1" || type.equals("1")) {
				String info = (String) obj[2];
				int k = dal.updateCooferState(model.getCoof_id());
				if (k > 0) {
					str[0] = "1";
					str[1] = "提交成功";
					str[2] = model.getCoof_id() + "";
					str[3] = "CoOffer";
					str[4] = info;
				} else {
					str[0] = "0";
					str[1] = "提交失败";
				}
			} else if (type == "2" || type.equals("2")) {
				int k = dal.UpdateCooffer(model);
				if (k > 0) {
					str[0] = "1";
					str[1] = "提交成功";
					str[2] = model.getCoof_id() + "";
					str[3] = "CoOffer";
					str[4] = "报价单审核";
				} else {
					str[0] = "0";
					str[1] = "提交失败";
				}
			}
		} catch (Exception e) {
			System.out.print("错误：" + e.getMessage());
		}
		return str;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		CoOfferModel model = (CoOfferModel) obj[1];
		Integer k = dal
				.CooferBack(model.getCoof_id(), model.getCoof_backcase());
		String[] str = new String[5];
		if (k > 0) {
			str[0] = "1";
			str[1] = "退回成功";
			str[2] = model.getCoof_id() + "";
			str[3] = "CoOffer";
			str[4] = "报价单审核退回";
		} else {
			str[0] = "0";
			str[1] = "退回失败";
		}
		return str;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		CoOfferModel model = (CoOfferModel) obj[1];
		Integer k = dal
				.CooferBack(model.getCoof_id(), model.getCoof_backcase());
		String[] str = new String[5];
		if (k > 0) {
			str[0] = "1";
			str[1] = "退回成功";
			str[2] = model.getCoof_id() + "";
			str[3] = "CoOffer";
			str[4] = "报价单审核退回";
		} else {
			str[0] = "0";
			str[1] = "退回失败";
		}

		return str;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		// TODO Auto-generated method stub
		return dal.updateCooferTaprid(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
