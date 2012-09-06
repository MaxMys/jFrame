package MOB.sys.Helper;

import java.util.HashMap;
import java.util.Iterator;

import MOB.sys.Core._Helper;


public class HashMap_Helper extends _Helper{
	
	 
	 public HashMap_Helper(){
		 
		 super();
		 
	 }// end of HashMapHelper();
	 
	 public int getCount(HashMap m) {
		 
		 int sum = 0 ;
		 
		 if( m == null)
			 return 0;
		 //获取迭代器
		 Iterator t = m.keySet().iterator();
		 
		 while ( t.hasNext() )  sum++;
		 return sum;
	 }//end getCount(HashMap m)
	 
	 public String[] getAll (HashMap m){
		 
		 if( m == null )
			 return null;
		 //getCount first
		 int sum = this.getCount(m);
		 //创建一个容器来装
		 String[] str = new String[sum];
		 //获取迭代器
		 Iterator t = m.keySet().iterator();
		 //定义迭代变量
		 int i = 0 ;
		 //取出数据
		 while( t.hasNext() )
			 
			 str[i++] = (String) t.next();
		 
		 return str;
	 }//end of getAll(HashMap)
	
}
