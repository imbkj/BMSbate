/**
 * @Classname cityDal
 * @ClassInfo 城市/省份/地区数据库访问类
 * @author 李文洁
 * @Date 2013-9-12
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Model.PubProCityModel;
import Model.PubProvinceModel;
import Model.PubRegionModel;
import Util.pinyin4jUtil;

import Conn.dbconn;

public class PubCityDal {
	private static dbconn conn = new dbconn();

	// 数据集
	// 查询地区表数据sql
	private ResultSet rsRegion() throws Exception {
		String sql = "select id,name from PubRegion order by name";
		ResultSet rs = conn.GRS(sql);
		return rs;
	}

	// 查询省份表数据sql
	private ResultSet rsPro() throws Exception {
		String sql = "select id,name,Regionid from PubProvince order by name";
		ResultSet rs = conn.GRS(sql);
		return rs;
	}

	// 查询城市表数据sql
	private ResultSet rsCity() throws Exception {
		String sql = "select id,name,provinceid from PubProCity order by name";
		ResultSet rs = conn.GRS(sql);
		return rs;
	}

	// 根据地区名称查询省份名称，返回列表
	private ResultSet rsProbyRegionName(String str) throws Exception {
		String sql = "select id,name,Regionid from PubProvince where Regionid=(select id from PubRegion where name=?) order by name";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setString(1, str);
		ResultSet rs = psmt.executeQuery();
		return rs;
	}

	// 根据城市查询省份表数据集
	private ResultSet rsProbyName(String str) throws Exception {
		String sql = "select id,name,Regionid from PubProvince where id=(select provinceid from PubProCity where name=?) order by name";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setString(1, str);
		ResultSet rs = psmt.executeQuery();
		return rs;
	}

	// 根据省份查询城市据数据集
	private ResultSet rsCitybyName(String str) throws Exception {
		String sql = "select id,name,provinceid from PubProCity where provinceid=(select id from PubProvince where name=?) order by name";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setString(1, str);
		ResultSet rs = psmt.executeQuery();
		return rs;
	}

	// 静态数据查询
	// 获取地区表所有数据
	public ArrayList<PubRegionModel> getRegionAll() throws Exception {
		ArrayList<PubRegionModel> region = new ArrayList<PubRegionModel>();
		ResultSet rs = rsRegion();
		try {
			while (rs.next()) {
				region.add(new PubRegionModel(rs.getInt("id"), rs
						.getString("name")));
			}
		} catch (Exception e) {

		}
		return region;
	}

	// 仅获取地区名称
	public ArrayList<String> getRegionName() throws Exception {
		ArrayList<String> name = new ArrayList<String>();
		ResultSet rs = rsRegion();
		try {
			while (rs.next()) {
				name.add(rs.getString("name"));
			}
		} catch (Exception e) {

		}
		return name;
	}

	// 获取省份表所有数据
	public ArrayList<PubProvinceModel> getProvinceAll() throws Exception {
		ArrayList<PubProvinceModel> province = new ArrayList<PubProvinceModel>();
		ResultSet rs = rsPro();
		try {
			while (rs.next()) {
				province.add(new PubProvinceModel(rs.getInt("id"), rs
						.getString("name"), rs.getInt("Regionid")));
			}
		} catch (Exception e) {

		}
		return province;
	}

	// 仅获取省份名称
	public ArrayList<String> getProvinceName() throws Exception {
		ArrayList<String> name = new ArrayList<String>();
		ResultSet rs = rsPro();
		try {
			while (rs.next()) {
				name.add(rs.getString("name"));
			}
		} catch (Exception e) {

		}
		return name;
	}

	// 获取城市表所有数据
	public ArrayList<PubProCityModel> getCityAll() throws Exception {
		ArrayList<PubProCityModel> city = new ArrayList<PubProCityModel>();
		ResultSet rs = rsCity();
		try {
			while (rs.next()) {
				city.add(new PubProCityModel(rs.getInt("id"), rs
						.getString("name"), rs.getInt("provinceid")));
			}
		} catch (Exception e) {

		}
		return city;
	}

	// 仅获取城市名称
	public ArrayList<String> getCityName() throws Exception {
		ArrayList<String> name = new ArrayList<String>();
		ResultSet rs = rsCity();
		try {
			while (rs.next()) {
				name.add(rs.getString("name"));
			}
		} catch (Exception e) {

		}
		return name;
	}

	// 获取城市名称及城市名称的首字母(返回值0：带首字母的城市名称，如：s深圳;1:城市名称)
	public ArrayList<String[]> getCityNameAddPy() throws Exception {
		ArrayList<String[]> name = new ArrayList<String[]>();
		ResultSet rs = rsCity();
		try {
			while (rs.next()) {
				name.add(new String[] {
						pinyin4jUtil.getHeadCharPinYin(rs.getString("name")),
						rs.getString("name") });
			}
		} catch (Exception e) {

		}
		return name;
	}

	// 动态数据查询
	// 根据地区名称查询省份名称，返回列表
	public ArrayList<String> getProvinceNameByRegion(String str)
			throws Exception {
		ArrayList<String> name = new ArrayList<String>();
		ResultSet rs = rsProbyRegionName(str);
		try {
			while (rs.next()) {
				name.add(rs.getString("name"));
			}
		} catch (Exception e) {

		}
		return name;
	}

	// 根据城市查询省份名称列表
	public ArrayList<String> getProvinceName(String str) throws Exception {
		ArrayList<String> name = new ArrayList<String>();
		ResultSet rs = rsProbyName(str);
		try {
			while (rs.next()) {
				name.add(rs.getString("name"));
			}
		} catch (Exception e) {

		}
		return name;
	}

	// 根据省份查询城市名称列表
	public ArrayList<String> getCityName(String str) throws Exception {
		ArrayList<String> name = new ArrayList<String>();
		ResultSet rs = rsCitybyName(str);
		try {
			while (rs.next()) {
				name.add(rs.getString("name"));
			}
		} catch (Exception e) {

		}
		return name;
	}
	
	public Integer getCityId(String name)
	{
		Integer id=0;
		String sql="select * from PubProCity where name='"+name+"'";
		dbconn db=new dbconn();
		try{
			ResultSet rs=db.GRS(sql);
			while(rs.next())
			{
				id=rs.getInt("id");
			}
		}catch(Exception e)
		{
			
		}
		return id;
	}
}
