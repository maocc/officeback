package cn.feezu.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdConfig {
	 @Value("${groupServerId}")
	 private static Integer id ;
	 private static IdWorker instance= null;;
		
	 public static IdWorker getInstance(){
		    if (null==id||id==0) {
				  id=1;
			}
	    	if (instance==null) {
	    		instance=new IdWorker(id);
			}
	        return instance;
	    }
 
	 public static void main(String[] args) {
		 for (int i = 0; i < 100; i++) {
			System.out.println(IdConfig.getInstance().nextId());
		}
	}

}
