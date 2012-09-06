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
		 //��ȡ������
		 Iterator t = m.keySet().iterator();
		 
		 while ( t.hasNext() )  sum++;
		 return sum;
	 }//end getCount(HashMap m)
	 
	 public String[] getAll (HashMap m){
		 
		 if( m == null )
			 return null;
		 //getCount first
		 int sum = this.getCount(m);
		 //����һ��������װ
		 String[] str = new String[sum];
		 //��ȡ������
		 Iterator t = m.keySet().iterator();
		 //�����������
		 int i = 0 ;
		 //ȡ������
		 while( t.hasNext() )
			 
			 str[i++] = (String) t.next();
		 
		 return str;
	 }//end of getAll(HashMap)
	
}
