package Util;

import org.zkoss.zul.Messagebox;

public class CoHousingFundUtil {

	/**
	 * 根据按钮内容和申报类型判断弹出的页面
	 * 
	 * @param label
	 * @param addtype
	 * @return
	 */
	public static String getUrl(String label, String addtype) {
		String url = "";
		try {
			if (addtype.equals("缴存登记")) {
				
				switch (label) {
				case "申报详情":
					url = "/CoHousingFund/CoHousingFund_AddInfo.zul";
					break;
				case "材料详情":
					url = "/CoHousingFund/CoHousingFund_DocInfo.zul";
					break;
				case "状态变更":
					url = "/CoHousingFund/CoHousingFund_AddOperate.zul";
					break;
				case "更新合同":
					url = "/CoHousingFund/CoHousingFund_UpdateCompact.zul";
					break;
				case "重新提交":
					url = "";
					break;
				case "退还材料":
					url = "/CoHousingFund/CoHousingFund_DocBack.zul";
					break;
				default:
					break;
				}
			} else if (addtype.equals("账户接管")) {
				switch (label) {
				case "申报详情":
					url = "/CoHousingFund/CoHousingFund_AddInfo.zul";
					break;
				case "材料详情":
					url = "/CoHousingFund/CoHousingFund_DocInfo.zul";
					break;
				case "状态变更":
					url = "/CoHousingFund/CoHousingFund_TranOperate.zul";
					break;
				case "更新合同":
					url = "/CoHousingFund/CoHousingFund_UpdateCompact.zul";
					break;
				case "重新提交":
					url = "";
					break;
				case "退还材料":
					url = "/CoHousingFund/CoHousingFund_DocBack.zul";
					break;
				default:
					break;
				}
			} else if (addtype.equals("信息变更")) {
				switch (label) {
				case "申报详情":
					url = "/CoHousingFund/CoHousingFund_InforChangeInfo.zul";
					break;
				case "材料详情":
					url = "/CoHousingFund/CoHousingFund_DocInfo.zul";
					break;
				case "状态变更":
					url = "/CoHousingFund/CoHousingFund_InforChangeOperate.zul";
					break;
				case "更新合同":
					url = "";
					break;
				case "重新提交":
					url = "";
					break;
				case "退还材料":
					url = "/CoHousingFund/CoHousingFund_DocBack.zul";
					break;
				default:
					break;
				}
			} else if (addtype.equals("比例调整")) {
				switch (label) {
				case "申报详情":
					url = "/CoHousingFund/CoHousingFund_RadixCppInfo.zul";
					break;
				case "材料详情":
					url = "/CoHousingFund/CoHousingFund_DocInfo.zul";
					break;
				case "状态变更":
					url = "/CoHousingFund/CoHousingFund_RadixCppOperate.zul";
					break;
				case "更新合同":
					url = "";
					break;
				case "重新提交":
					url = "";
					break;
				case "退还材料":
					url = "/CoHousingFund/CoHousingFund_DocBack.zul";
					break;
				default:
					break;
				}
			} else if (addtype.equals("降低比例")) {
				switch (label) {
				case "申报详情":
					url = "/CoHousingFund/CoHousingFund_RadixLowInfo.zul";
					break;
				case "材料详情":
					url = "/CoHousingFund/CoHousingFund_DocInfo.zul";
					break;
				case "状态变更":
					url = "/CoHousingFund/CoHousingFund_RadixLowOperate.zul";
					break;
				case "更新合同":
					url = "";
					break;
				case "重新提交":
					url = "";
					break;
				case "退还材料":
					url = "/CoHousingFund/CoHousingFund_DocBack.zul";
					break;
				default:
					break;
				}
			} else if (addtype.equals("缓缴")) {
				switch (label) {
				case "申报详情":
					url = "/CoHousingFund/CoHousingFund_RadixHjInfo.zul";
					break;
				case "材料详情":
					url = "/CoHousingFund/CoHousingFund_DocInfo.zul";
					break;
				case "状态变更":
					url = "/CoHousingFund/CoHousingFund_RadixHjOperate.zul";
					break;
				case "更新合同":
					url = "";
					break;
				case "重新提交":
					url = "";
					break;
				case "退还材料":
					url = "/CoHousingFund/CoHousingFund_DocBack.zul";
					break;
				default:
					break;
				}
			} else if (addtype.equals("降低比例/缓缴提前终止")) {
				switch (label) {
				case "申报详情":
					url = "/CoHousingFund/CoHousingFund_TerminationInfo.zul";
					break;
				case "材料详情":
					url = "/CoHousingFund/CoHousingFund_DocInfo.zul";
					break;
				case "状态变更":
					url = "/CoHousingFund/CoHousingFund_TerminationOperate.zul";
					break;
				case "更新合同":
					url = "";
					break;
				case "重新提交":
					url = "";
					break;
				case "退还材料":
					url = "/CoHousingFund/CoHousingFund_DocBack.zul";
					break;
				default:
					break;
				}
			} else if (addtype.equals("登记注销")) {
				switch (label) {
				case "申报详情":
					url = "/CoHousingFund/CoHousingFund_CancelInfo.zul";
					break;
				case "材料详情":
					url = "/CoHousingFund/CoHousingFund_DocInfo.zul";
					break;
				case "状态变更":
					url = "/CoHousingFund/CoHousingFund_CancelOperate.zul";
					break;
				case "更新合同":
					url = "";
					break;
				case "重新提交":
					url = "";
					break;
				case "退还材料":
					url = "/CoHousingFund/CoHousingFund_DocBack.zul";
					break;
				default:
					break;
				}
			} else if (addtype.equals("合同终止")) {
				switch (label) {
				case "申报详情":
					url = "/CoHousingFund/CoHousingFund_SurrenderInfo.zul";
					break;
				case "材料详情":
					url = "/CoHousingFund/CoHousingFund_DocInfo.zul";
					break;
				case "状态变更":
					url = "/CoHousingFund/CoHousingFund_SurrenderOperate.zul";
					break;
				case "更新合同":
					url = "";
					break;
				case "重新提交":
					url = "";
					break;
				case "退还材料":
					url = "/CoHousingFund/CoHousingFund_DocBack.zul";
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("获取页面出错：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
		return url;
	}
}
