package MPD.sys.Driver;

import java.util.HashMap;
import java.util.Iterator;

import MPD.sys.Core._SQLDriver;

/**
 * 
 * 实现抽象类SQLDriver的OracleSqlDriver;
 * 加强了对于OracleSql语句的支持
 * @author max
 * @date 2012-8-1
 *
 */
public class OracleSql_Driver extends _SQLDriver {

	@Override
	public void where(String whereSql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void where(HashMap<String, String> whereItems) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void field(String field) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void field(String[] fields) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void table(String table) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void table(String[] tables) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String createSql() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _SQLDriver subSql() {
		// TODO Auto-generated method stub
		return null;
	}
	

}// end of class OracleSqlDriver
