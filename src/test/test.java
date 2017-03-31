package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import dal.EmSocialinPaper.EmSocialinPaperOperateDal;

import Model.EmSocialinPaperModel;
import Util.RegexUtil;

public class test {

	
	public void test1() {
		EmSocialinPaperModel m = new EmSocialinPaperModel();
		m.setEspa_id(42);
		m.setEspa_state(2);
		m.setEspa_backname("潘浪宇");
		m.setEspa_backreason("aaa");
		System.out.println(new EmSocialinPaperOperateDal().Back(m));
	}

	public boolean test2(String pstr) {
		String a = "1033";
		Pattern p = Pattern.compile(pstr);
		Matcher m = p.matcher(a);
		boolean b = m.find();
		return b;
	}
}
