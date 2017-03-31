package Conn;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JApplet;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import Util.MapStringChange;
import Util.ReadProperties;

public class dbconn {

	private String connstr = null;
	/**
	 * 连接
	 */
	private Connection conn = null;

	private Statement stmt = null;
	/**
	 * 结果集对象
	 */
	private ResultSet rs = null;
	/**
	 * 存储过程处理对象
	 */
	private CallableStatement csmt = null;
	/**
	 * 预处理对象
	 */
	private PreparedStatement psmt = null;
	/**
	 * 存储过程处理对象
	 */
	private CallableStatement cs;
	/**
	 * 数据源
	 */

	/**
	 * 事务超时事件
	 */
	private int queryTime;

	private DataSource ds = new DataSource();

	public void dspool() {
		ReadProperties rProp = new ReadProperties("/Conn/jdbcpool.properties");
		PoolProperties p = new PoolProperties();
		p.setUrl(rProp.getString("jpool.url"));
		p.setDriverClassName(rProp.getString("jpool.driverClassName"));
		p.setUsername(rProp.getString("jpool.username"));
		p.setPassword(rProp.getString("jpool.password"));
		p.setValidationQuery(rProp.getString("jpool.validationQuery"));
		p.setJmxEnabled(Boolean.valueOf(rProp.getString("jpool.jmxEnabled")));
		p.setTestWhileIdle(Boolean.valueOf(rProp
				.getString("jpool.testWhileIdle")));
		p.setTestOnBorrow(Boolean.valueOf(rProp.getString("jpool.testOnBorrow")));
		p.setValidationQuery(rProp.getString("jpool.validationQuery"));
		p.setValidationInterval(Long.valueOf(rProp
				.getString("jpool.validationInterval")));
		p.setTimeBetweenEvictionRunsMillis(Integer.valueOf(rProp
				.getString("jpool.timeBetweenEvictionRunsMillis")));
		p.setMaxActive(Integer.valueOf(rProp.getString("jpool.maxActive")));
		p.setInitialSize(Integer.valueOf(rProp.getString("jpool.initialSize")));
		p.setMaxWait(Integer.valueOf(rProp.getString("jpool.maxWait")));
		p.setRemoveAbandoned(Boolean.valueOf(rProp
				.getString("jpool.removeAbandoned")));
		p.setRemoveAbandonedTimeout(Integer.valueOf(rProp
				.getString("jpool.removeAbandonedTimeout")));
		p.setTestOnBorrow(Boolean.valueOf(rProp.getString("jpool.testOnBorrow")));
		p.setLogAbandoned(Boolean.valueOf(rProp.getString("jpool.logAbandoned")));
		p.setMinEvictableIdleTimeMillis(Integer.valueOf(rProp
				.getString("jpool.minEvictableIdleTimeMillis")));
		p.setMinIdle(Integer.valueOf(rProp.getString("jpool.minIdle")));
		p.setJdbcInterceptors(rProp.getString("jpool.jdbcInterceptors"));
		ds.setPoolProperties(p);
	}

	/**
	 * 生成一个JDBC封装对象
	 * 
	 * @param connectionProvider
	 *            连接提供代理
	 * @param sourceName
	 *            数据源名称,connectionProvider根据此名称切换数据源
	 * @param queryTime
	 *            事务处理超时时间 0:无超时
	 */

	public Connection getConn() throws Exception {

		if (connstr == null) {
			connstr = "/Conn/ciic.properties";
		}

		ReadProperties rProp = new ReadProperties(connstr);

		Class.forName(rProp.getString("jdbc.driverClassName"));
		String url = rProp.getString("jdbc.url");
		conn = DriverManager.getConnection(url,
				rProp.getString("jdbc.username"),
				rProp.getString("jdbc.password"));

		// dspool();
		// conn=ds.getConnection();
		return conn;
	}

	public Connection getExcelConn() throws Exception {
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String url = "jdbc:odbc:excelDB";
		String username = "";
		String password = "";
		Class.forName(driver);
		DriverManager.getConnection(url, username, password);
		return conn;
	}

	public Statement getStmt(Connection conn) throws SQLException {
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		return stmt;
	}

	public CallableStatement getcall(String str) {
		try {
			conn = getConn();
			csmt = conn.prepareCall("{call " + str + "}");
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		return csmt;
	}

	public PreparedStatement getpre(String str) {
		try {
			conn = getConn();
			psmt = conn.prepareStatement(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return psmt;
	}

	public ResultSet GRS(String sql) throws Exception {
		try {
			conn = getConn();
			stmt = getStmt(conn);
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
		}
		return rs;
	}

	public int execQuery(String sql) {
		int row = 0;
		try {
			conn = getConn();
			stmt = getStmt(conn);

			row = stmt.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return row;
	}

	public void createBatch(String sql, List[] list) throws SQLException {

		try {
			getConnection();
			conn.setAutoCommit(false);
			psmt = conn.prepareStatement(sql);
			psmt.clearBatch();
			for (int i = 0; i < list.length; i++) {

				for (int j = 0; j < list[i].size(); j++) {
					psmt.setObject(j + 1, list[i].get(j));
					psmt.setObject(j + 1, list[i].get(j));
				}
				psmt.addBatch();

				if ((i % 500 == 0 && i != 0) || i == (list.length - 1)) {
					psmt.executeBatch();
					conn.commit();
					psmt.clearBatch();

				}
			}
			psmt.executeBatch();
			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {

			Close();
		}

	}

	/**
	 * 统计语句查询 直接返回唯一值
	 * 
	 * @param sql
	 * @param objs
	 * @return
	 * @throws SQLException
	 */
	public Object findReturn(String sql, Object... objs) throws SQLException {
		// log.debug("Query return value:[" + sql + "] " + (objs != null ?
		// Arrays.deepToString(objs) : ""));

		try {
			getConnection();

			psmt = conn.prepareStatement(sql);
			psmt.setQueryTimeout(queryTime);

			if (objs != null) {
				Integer k = 0;
				for (int i = 0; i < objs.length; i++) {
					if (objs[i] != null) {

						if (objs[i].getClass().equals(ArrayList.class)) {
							Object o = objs[i];
							ArrayList list = (ArrayList) o;

							for (Integer j = 0; j < list.size(); j++) {

								psmt.setObject(k + 1, list.get(j));
								k++;
							}
						} else {
							psmt.setObject(k + 1, objs[i]);
							k++;
						}

					} else {

						psmt.setObject(k + 1, objs[i]);
						k++;
					}
				}
			}
			rs = psmt.executeQuery();
			if (rs.next())
				return rs.getObject(1);
			else
				return null;
		} finally {
			Close();
		}
	}

	/**
	 * 执行存储过程 首字段用于处理返回值 所以存储过程写法必须是 {?=call PRODUCENAME(?,?...,?)}
	 * 
	 * @param call
	 *            存储过程
	 * @param returnType
	 *            返回参数类型 [Types.XXXX]
	 * @param objs
	 *            参数列表
	 * @return
	 * @throws SQLException
	 */
	public Object callWithReturn(String call, int returnType, Object... objs)
			throws SQLException {
		// log.debug("Call return value:[" + call + "] " + (objs != null ?
		// Arrays.deepToString(objs) : ""));
		try {
			getConnection();

			cs = conn.prepareCall(call);
			cs.setQueryTimeout(queryTime);

			cs.registerOutParameter(1, returnType);
			Integer k = 0;

			for (int i = 0; i < objs.length; i++) {

				if (objs[i] != null) {

					if (objs[i].getClass().equals(ArrayList.class)) {
						Object o = objs[i];
						ArrayList list = (ArrayList) o;

						for (Integer j = 0; j < list.size(); j++) {

							cs.setObject(k + 2, list.get(j));
							k++;
						}
					} else {

						cs.setObject(k + 2, objs[i]);
						k++;
					}

				} else {
					cs.setObject(k + 2, objs[i]);
					k++;
				}
			}
			cs.execute();
			return cs.getObject(1);
		} finally {
			Close();
		}
	}

	/**
	 * 用于执行返回列表的存储过程 并映射到对象列表上
	 * 
	 * @param <X>
	 * @param clazz
	 *            映射对象
	 * @param sql
	 *            查询语句
	 * @param smap
	 *            映射配置表<字段名,类型>
	 * @param objs
	 *            参数列表
	 * @return
	 * @throws Exception
	 */
	public <X> List<X> call(Class<X> clazz, String sql,
			Map<String, Class<?>> smap, Object... objs) throws Exception {
		// log.debug("Call Query produce:[" + sql + "] " + (objs != null ?
		// Arrays.deepToString(objs) : ""));
		try {
			rs = call(sql, objs);

			List<X> list = new ArrayList<X>();

			while (rs != null && rs.next()) {
				X obj = returnObject(clazz, smap);

				list.add(obj);
			}

			return list;
		} catch (Exception e) {
			Close();
			throw e;
		} finally {
			Close();
		}
	}

	// 调用存储过程
	private ResultSet call(String sql, Object... params) throws SQLException {
		getConnection();

		cs = conn.prepareCall(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		cs.setQueryTimeout(queryTime);

		// cs.registerOutParameter(1, Types.REAL);
		Integer k = 0;
		for (int i = 0; i < params.length; i++) {
			if (params[i] != null) {

				if (params[i].getClass().equals(ArrayList.class)) {
					Object o = params[i];
					ArrayList list = (ArrayList) o;

					for (Integer j = 0; j < list.size(); j++) {

						cs.setObject(k + 1, list.get(j));
						k++;
					}
				} else {

					cs.setObject(k + 1, params[i]);
					k++;
				}

			} else {

				cs.setObject(k + 1, params[i]);
				k++;
			}

		}
		return cs.executeQuery();
	}

	/**
	 * 查询单个对象 并映射到对象上
	 * 
	 * @param <X>
	 * @param clazz
	 *            映射对象
	 * @param sql
	 *            查询语句
	 * @param smap
	 *            映射配置表<字段名,类型>
	 * @param objs
	 *            参数列表
	 * @return
	 * @throws Exception
	 */
	public <X> X get(Class<X> clazz, String sql, Map<String, Class<?>> smap,
			Object... objs) throws SQLException {
		// log.debug("Get Entity:[" + sql + "] " + (objs != null ?
		// Arrays.deepToString(objs) : ""));
		try {
			rs = query(sql, objs);

			if (rs != null && rs.next()) {
				X obj = returnObject(clazz, smap);

				return obj;
			} else {
				return null;
			}

		} catch (Exception e) {
			throw new SQLException(e);
		} finally {
			Close();
		}
	}

	// 查询数据库并返回结果到ResultSet
	private ResultSet query(String sql, Object... objs) throws Exception {
		getConnection();

		psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		psmt.setQueryTimeout(queryTime);

		if (objs != null) {
			Integer k = 0;
			for (int i = 0; i < objs.length; i++) {
				// System.out.println(objs[i]);
				if (objs[i] != null) {

					if (objs[i].getClass().equals(ArrayList.class)) {
						Object o = objs[i];
						ArrayList list = (ArrayList) o;

						for (Integer j = 0; j < list.size(); j++) {
							psmt.setObject(k + 1, list.get(j));
							k++;
						}
					} else {
						psmt.setObject(k + 1, objs[i]);
						k++;
					}

				} else {

					psmt.setObject(k + 1, objs[i]);
					k++;

				}

			}
		}
		return psmt.executeQuery();
	}

	/**
	 * 用于解析查询结果 并映射到对象
	 * 
	 * @param <X>
	 * @param clazz
	 *            映射对象
	 * @param smap
	 *            映射表
	 * @return
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws SQLException
	 * @throws IllegalAccessException
	 */
	private <X> X returnObject(Class<X> clazz, Map<String, Class<?>> smap)
			throws InstantiationException, SecurityException,
			NoSuchMethodException, SQLException, IllegalAccessException {
		X obj = clazz.newInstance();

		if (smap == null) {
			smap = dbconn.parseSmap(clazz);
		}

		for (Entry<String, Class<?>> en : smap.entrySet()) {
			try {
				boolean b = false;
				for (Integer i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					if (rs.getMetaData().getColumnName(i).equals(en.getKey())) {
						b = true;
						break;
					}
				}

				if (b) {

					Object value = rs.getObject(en.getKey());

					setField(obj, en.getKey(), en.getValue(), (en.getValue()
							.equals(String.class) ? (value != null ? value
							: null) : value));
				}

			} catch (IllegalArgumentException e1) {
				/*
				 * log.error("不正确的对象映射. 映射类:" + clazz.getName() + " 配置字段名:" +
				 * en.getKey() + " 类型:" + en.getValue().getName() + " 数据库字段类型:"
				 * + rs.getObject(en.getKey()).getClass().getName());
				 */

				if (en.getValue().getName().equals("int")
						&& rs.getObject(en.getKey()) == null) {
					System.out.println("不正确的对象映射. 映射类:" + clazz.getName()
							+ " 配置字段名:" + en.getKey() + " 类型:"
							+ en.getValue().getName()
							+ " 映射类型不支持空值,请检查数据库字段或修改对象映射类型");
				} else {
					System.out.println("不正确的对象映射. 映射类:" + clazz.getName()
							+ " 配置字段名:" + en.getKey() + " 类型:"
							+ en.getValue().getName() + " 数据库字段类型:"
							+ rs.getObject(en.getKey()).getClass().getName());
				}

			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}

		return obj;
	}

	/**
	 * 设置对象上字段的值 现在只支持简单的对象类型 String Integer Short Double 等标准对象
	 * 可以扩展这个方法用来支持一些比较复杂的对象格式
	 * 
	 * @param obj
	 *            映射的对象
	 * @param fieldname
	 *            字段名称 将调用它的set方法进行设置
	 * @param type
	 *            字段类型
	 * @param value
	 *            字段值
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected void setField(Object obj, String fieldname, Class<?> type,
			Object value) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		// type:MODEL,VALUE:数据库
		if (value != null && type != null) {

			if (value.getClass().equals(Timestamp.class)
					&& type.equals(String.class)) {

				Date d = (Date) value;
				Calendar c = new GregorianCalendar();
				c.setTime(d);
				String fm = "yyyy-MM-dd";

				if (c.get(Calendar.HOUR) > 0 || c.get(Calendar.MINUTE) > 0
						|| c.get(Calendar.SECOND) > 0) {
					fm = "yyyy-MM-dd HH:mm:ss";

				}

				SimpleDateFormat sdf = new SimpleDateFormat(fm);
				value = sdf.format(d);
			} else if (value.getClass().equals(java.sql.Date.class)
					&& type.equals(String.class)) {

				Date d = (Date) value;
				Calendar c = new GregorianCalendar();
				c.setTime(d);
				String fm = "yyyy-MM-dd";

				if (c.get(Calendar.HOUR) > 0 || c.get(Calendar.MINUTE) > 0
						|| c.get(Calendar.SECOND) > 0) {
					fm = "yyyy-MM-dd HH:mm:ss";

				}

				SimpleDateFormat sdf = new SimpleDateFormat(fm);
				value = sdf.format(d);

			} else if (type.equals(BigDecimal.class)) {
				if (value.getClass().equals(Integer.class)) {
					value = new BigDecimal((Integer) value);
				}
			} else if (type.equals(Integer.class)) {
				if (value.getClass().equals(Long.class)) {
					value = Integer.valueOf(value.toString());

				}
			} else if (type.equals(Boolean.class)) {
				if (value.getClass().equals(Integer.class)) {
					value = Integer.valueOf(value.toString()) > 0 ? true
							: false;
				}
			} else if (type.getName().equals("boolean")) {
				if (value.getClass().equals(Integer.class)) {
					value = Integer.valueOf(value.toString()) > 0 ? true
							: false;
				}
			} else if (type.getName().equals("java.util.Map")) {
				if (value.getClass().equals(String.class)) {
					value = MapStringChange.StringToMap(value.toString());
				}
			}else if (value.getClass().equals(BigDecimal.class) && type.equals(String.class)) {
				
				value= value.toString();
			}

		} else if (type != null) {
			if (type.equals(Boolean.class)) {
				value = false;
			} else if (type.getName().equals("boolean")) {
				value = false;
			}
		}

		Method method = obj.getClass().getMethod(setMethod(fieldname), type);
		method.invoke(obj, value);
	}

	/**
	 * 执行插入语句,并返回生成的主键
	 * 
	 * @param sql
	 *            插入语句
	 * @param objs
	 *            参数列表
	 * @return 插入语句返回的主键值
	 * @throws SQLException
	 */
	public int insertAndReturnKey(String sql, Object... objs)
			throws SQLException {
		int countRow = 0;
		int key = 0;

		// log.debug("Insert and return Id:[" + sql + "] " + (objs != null ?
		// Arrays.deepToString(objs) : ""));
		try {
			getConnection();

			conn.setAutoCommit(false);

			psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			psmt.setQueryTimeout(queryTime);

			if (objs != null) {
				Integer k = 0;
				for (int i = 0; i < objs.length; i++)
					if (objs[i] != null) {

						if (objs[i].getClass().equals(ArrayList.class)) {
							Object o = objs[i];
							ArrayList list = (ArrayList) o;

							for (Integer j = 0; j < list.size(); j++) {

								psmt.setObject(k + 1, list.get(j));
								k++;
							}
						} else {
							psmt.setObject(k + 1, objs[i]);
							k++;
						}
					} else {

						psmt.setObject(k + 1, objs[i]);
						k++;
					}
			}

			countRow = psmt.executeUpdate();
			if (countRow > 0) {
				ResultSet rs = psmt.getGeneratedKeys();
				if (rs.next())
					key = rs.getInt(1);
			}
			conn.commit();
		} catch (SQLException e) {
			countRow = 0;
			conn.rollback();
			closeConnection();
			throw e;
		} finally {
			if (conn != null) {
				conn.setAutoCommit(true);
			}
			Close();
		}
		return key;
	}

	/**
	 * 执行预编译SQL
	 * 
	 * @param sql
	 *            SQL语句
	 * @param objs
	 *            参数列表
	 * @return 执行影响条数
	 * @throws SQLException
	 */
	public int updatePrepareSQL(String sql, Object... objs) throws SQLException {
		// log.debug("Update:[" + sql + "]" + (objs != null ? objs.toString() :
		// ""));

		int countRow = 0;
		try {
			getConnection();

			conn.setAutoCommit(false);

			psmt = conn.prepareStatement(sql);
			psmt.setQueryTimeout(queryTime);
			if (objs != null) {
				Integer k = 0;
				for (int i = 0; i < objs.length; i++) {

					if (objs[i] != null) {

						if (objs[i].getClass().equals(ArrayList.class)) {
							Object o = objs[i];
							ArrayList list = (ArrayList) o;

							for (Integer j = 0; j < list.size(); j++) {

								psmt.setObject(k + 1, list.get(j));
								k++;
							}
						} else {
							psmt.setObject(k + 1, objs[i]);
							k++;
						}
					} else {

						psmt.setObject(k + 1, objs[i]);
						k++;
					}
				}
			}
			countRow = psmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			countRow = 0;
			conn.rollback();
			closeConnection();
			throw e;
		} finally {
			if (conn != null) {
				conn.setAutoCommit(true);
			}
			Close();
		}
		return countRow;
	}

	// 查找数据表信息并返回LIST
	public <X> List<X> find(String sql, Class<X> clazz,
			Map<String, Class<?>> smap, Object... objs) throws SQLException {
		try {
			// log.debug("Query:" + sql + " Class:" + clazz.getName() + (objs !=
			// null ? Arrays.deepToString(objs) : ""));

			rs = query(sql, objs);

			List<X> list = new ArrayList<X>();

			while (rs != null && rs.next()) {
				X obj = returnObject(clazz, smap);

				list.add(obj);
			}

			return list;
		} catch (Exception e) {

			throw new SQLException(e);
		} finally {
			Close();
		}
	}

	// 关闭连接
	public void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
			conn = null;
		} catch (Exception e) {
		}
	}

	// 获取连接
	public Connection getConnection() throws SQLException {
		// if (sourceName == null)
		// throw new SQLException("没有设置数据源");

		int Times = 0;
		while (conn == null || conn.isClosed()) {
			try {
				closeConnection();
				// conn = connectionProvider.getConnection(sourceName);
				conn = getConn();
				break;
			} catch (Exception sqle) {
				// log.error("error getConnection():" + sqle.getMessage(),sqle);
			} finally {
				if (Times > 2) {
					throw new SQLException("获取连接次数已经超过3次。不再尝试重新获取");
				}
				++Times;
			}
		}
		return conn;
	}

	// 关闭所有数据连接对象
	public void Close() throws SQLException {
		try {
			super.finalize();
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (psmt != null) {
				psmt.close();
				psmt = null;
			}

			if (stmt != null) {
				stmt.close();
				stmt = null;
			}

			if (cs != null) {
				cs.close();
				cs = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Throwable te) {
			te.printStackTrace();
		}
	}

	// 根据传入的model绑定字段
	public static Map<String, Class<?>> parseSmap(Class<?> clazz,
			String... paramNames) {
		Integer len;
		Field[] fields = clazz.getDeclaredFields();

		len = paramNames.length < 1 ? fields.length : paramNames.length;

		Map<String, Class<?>> smap = new HashMap<String, Class<?>>(len);

		if (paramNames.length > 0) {
			for (String name : paramNames) {
				try {
					Field field = clazz.getDeclaredField(name);
					smap.put(name, field.getType());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		} else {
			for (Field f : fields) {
				try {
					smap.put(f.getName(), f.getType());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		return smap;
	}

	/*
	 * public static Map<String, Class<?>> parseFullSmap(Class<?> clazz) {
	 * 
	 * /* Map<String, Class<?>> smap = new HashMap<String, Class<?>>(
	 * paramNames.length);
	 * 
	 * for (String name : paramNames) { try { Field field =
	 * clazz.getDeclaredField(name); smap.put(name, field.getType()); } catch
	 * (Exception e) { throw new RuntimeException(e); } }
	 * 
	 * //return smap; }
	 */

	public static String getMethod(String name) {
		return "get"
				+ name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
						.toUpperCase());
	}

	public static String setMethod(String name) {
		return "set"
				+ name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
						.toUpperCase());
	}

	public String getConnstr() {
		return connstr;
	}

	public void setConnstr(String connstr) {
		this.connstr = connstr;
	}

}
