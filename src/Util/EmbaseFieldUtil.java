package Util;

public class EmbaseFieldUtil {

	public String getfield(String field) {
		String name = "";

		switch (name) {
		case "emba_name":
			name = "姓名";
			break;
		case "emba_sex":
			name = "性别";
			break;
		case "emba_folk":
			name = "民族";
			break;
		case "emba_native":
			name = "户口所在地";
			break;
		case "emba_idcard":
			name = "身份证号";
			break;
		case "emba_birth":
			name = "出生日期";
			break;
		case "emba_marital":
			name = "婚姻状况";
			break;
		case "emba_school":
			name = "毕业院校";
			break;
		case "emba_specialty":
			name = "专业";
			break;
		case "emba_graduation":
			name = "毕业时间";
			break;
		case "emba_degree":
			name = "文化程度";
			break;
		case "emba_hjadd":
			name = "户籍地址";
			break;
		case "emba_hjtype":
			name = "户口类型";
			break;
		case "emba_address":
			name = "现居住地";
			break;
		case "emba_phone":
			name = "家庭电话";
			break;
		case "emba_mobile":
			name = "手机号码";
			break;
		case "emba_ep":
			name = "紧急联系人及联系方式";
			break;
		case "emba_email":
			name = "E-mail";
			break;
		case "emba_party":
			name = "政治面貌";
			break;
		case "emba_status":
			name = "身份";
			break;
		case "emba_fileplace":
			name = "档案存放单位";
			break;
		case "emba_fileinclass":
			name = "是否愿意转入中智托管";
			break;
		case "emba_filereason":
			name = "不转入中智托管原因";
			break;
		case "emba_filedebts":
			name = "是否有欠费";
			break;
		case "emba_filedebtsmonth":
			name = "档案费用缴至哪个月份";
			break;
		case "emba_filehj":
			name = "户口是否托管在人才";
			break;
		case "emba_computerid":
			name = "社保电脑号";
			break;
		case "emba_hand":
			name = "利手 （习惯使用哪只手）";
			break;
		case "emba_sbcard":
			name = "是否需要办理社保卡";
			break;
		case "emba_houseid":
			name = "个人公积金编号";
			break;
		case "emba_excompanystate":
			name = "原单位是否已封存";
			break;
		case "emba_wifename":
			name = "配偶姓名(未婚者不填)";
			break;
		case "emba_wifeidcard":
			name = "配偶身份证号码(未婚者不填)";
			break;
		case "emba_gzbank":
			name = "工资-个人银行信息";
			break;
		case "emba_housecode":
			name = "房屋地址编码(非深户必填项)";
			break;
		case "emba_housetime":
			name = "入住时间";
			break;
		case "emba_housetype":
			name = "住所类别";
			break;
		case "emba_houseclass":
			name = "居住方式";
			break;
		case "emba_title":
			name = "职称";
			break;
		case "emba_skilllevel":
			name = "配偶姓名(未婚者不填)";
			break;
		case "emba_worktime":
			name = "参加工作时间";
			break;
		case "emba_sztime":
			name = "来深时间";
			break;
		case "emba_hjtime":
			name = "户口进入深圳时间(深户)";
			break;
		case "emba_regtype":
			name = "就业类型";
			break;
		case "emba_compactlimit":
			name = "劳动合同年限";
			break;
		case "emba_compactstart":
			name = "劳动合同开始日期";
			break;
		case "emba_compactend":
			name = "劳动合同结束日期";
			break;
		case "emba_companystart":
			name = "本单位工作起始日期";
			break;
		case "emba_station":
			name = "工作岗位(中文)";
			break;
		case "emba_birthcontrol":
			name = "广东省流动人口避孕节育情况报告单 编号(非深户已婚育龄女性)";
			break;
		case "emba_photonum":
			name = "相片图像号(非深户必填)";
			break;
		case "emba_sbinfor":
			name = "商保-子女、配偶信息";
			break;
		case "emba_sbbank":
			name = "商保-个人银行信息(商保索赔)";
			break;
		case "emba_hospital":
			name = "体检医院";
			break;
		case "emba_bctime":
			name = "体检时间";
			break;
		case "emba_bcaddress":
			name = "体检地址";
			break;
		case "emba_workinfor":
			name = "工作简历";
			break;
		case "emba_family":
			name = "家庭主要成员";
			break;
		default:
			name = "有必填项";
			break;
		}

		return name;
	}
}
