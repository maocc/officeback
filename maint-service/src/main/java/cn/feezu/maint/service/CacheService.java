package cn.feezu.maint.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.feezu.maint.util.CacheUtils;

@Service
public class CacheService {
	
	final static Logger logger = LoggerFactory.getLogger(CacheService.class);
	
	
	 //这里的单引号不能少，否则会报错，被识别是一个对象;
    public static final String CACHE_KEY = "'loginCount_'";
    /**
     * value属性表示使用哪个缓存策略，缓存策略在ehcache.xml
     */
    public static final String TOOL_CACHENAME = "tool";
    

   /**
    * 查询数据.
    * @param userid
    * @param count
    * @return
    */
    @Cacheable(value=TOOL_CACHENAME,key=CACHE_KEY+"+#userid")
    public int countByUserId(Long userid){
    	logger.info("没有走缓存！"+userid);
        return 0;
    }
   
    /**
     * 更新緩存
     * @param userId
     * @param count
     * @return
     */
    @CachePut(value = TOOL_CACHENAME,key = CACHE_KEY+"+#userId")
    public int update(Long userId,int count) {
        return count;
    }


    /**
     * 删除数据.
     * @param id
     */
    @CacheEvict(value = TOOL_CACHENAME,key = CACHE_KEY+"+#userId")
    public void delete(Long userId){
    	logger.info("清除缓存"+userId);
    }
    
    public void deleteAll(){
    	CacheUtils.clearCache(TOOL_CACHENAME);
    }
}
