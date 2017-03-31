package bll.CoAgency;
import dal.CoAgency.CoAgencyBaseOperateDal;
import Model.CoAgencyBaseModel;
public class BaseInfo_UpBll {
	// 更新委托机构基本信息，传入参数，返回message数组(0失败1成功2出错3省份有误4城市有误)
	public String[] UpdateBase(int coab_id, String coab_province,
			String coab_city, String coab_setuptype, String coab_source,
			String coab_capital, String coab_regaddress,
			String coab_companymanager, String coab_coaddress,
			String coab_zipcode, String coab_business, String coab_sendinvoice,
			String coab_payday, String coab_paymon, String coab_accountbank,
			String coab_accountnum, String coab_hz, String coab_remark,
			String coab_client, String coab_addname) {
		String[] message = new String[2];
		try {
			message = existUpBase(coab_province, coab_city);
			if (message[0] == null) {
				CoAgencyBaseModel m = new CoAgencyBaseModel();
				int result = 0;
				CoAgencyBaseOperateDal opDal = new CoAgencyBaseOperateDal();
				String sendinvoice = "";
				if (coab_sendinvoice.equals("是")) {
					sendinvoice = "1";
				} else if (coab_sendinvoice.equals("否")) {
					sendinvoice = "0";
				}
				m.setCoab_id(coab_id);
				m.setCoab_province(coab_province);
				m.setCoab_city(coab_city);
				m.setCoab_setuptype(coab_setuptype);
				m.setCoab_source(coab_source);
				m.setCoab_capital(coab_capital);
				m.setCoab_regaddress(coab_regaddress);
				m.setCoab_companymanager(coab_companymanager);
				m.setCoab_coaddress(coab_coaddress);
				m.setCoab_zipcode(coab_zipcode);
				m.setCoab_business(coab_business);
				m.setCoab_sendinvoice(sendinvoice);
				m.setCoab_payday(coab_payday);
				m.setCoab_paymon(coab_paymon);
				m.setCoab_accountbank(coab_accountbank);
				m.setCoab_accountnum(coab_accountnum);
				m.setCoab_hz(coab_hz);
				m.setCoab_remark(coab_remark);
				m.setCoab_client(coab_client);
				m.setCoab_addname(coab_addname);
				result = opDal.UpBase(m);
				if (result == 0) {
					message[0] = "1";
					message[1] = "更新机构成功!";
				} else if(result == -110){
					message[0] = "0";
					message[1] = "数据并未改动，无需提交！";
				}else{
					message[0] = "0";
					message[1] = "更新机构失败!";
				}
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "委托机构，更新出错。";
		}
		return message;
	}

	// 检测新增委托机构基本信息，录入数据的正确性
	private String[] existUpBase(String coab_province, String coab_city) {
		String[] message = new String[2];
		try {

			if (coab_province == "" || coab_province == null) {
				message[0] = "3";
				message[1] = "请选择委托机构所属省份。";
			} else if (coab_city == "" || coab_city == null) {
				message[0] = "4";
				message[1] = "请选择委托机构所属城市。";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "委托机构，更新出错。";
		}
		return message;
	}
}
