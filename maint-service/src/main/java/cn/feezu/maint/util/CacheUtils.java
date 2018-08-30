package cn.feezu.maint.util;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public class CacheUtils {
	private CacheUtils() {
    }

    private static CacheUtils instance;

    static {
        // 静态块，初始化实例
        instance = new CacheUtils();
    }
    public static CacheUtils getInstance() {
        return instance;
    }
    private static final CacheManager getCacheManager(){
    	return SpringContextUtils.getBean(CacheManager.class);
    }
	/**
	 * 清除指定的缓存
	 * @param cacheName 缓存名称
	 */
	public static void clearCache(String cacheName){
	        Cache cache = getCacheManager().getCache(cacheName);
	        if(cache == null){
	        	return;
	        }
	        cache.clear();
	}
	/**
	 * 清除指定的缓存
	 * @param cacheName 缓存名称
	 * @param key 缓存的key
	 */
	public static void clearCache(String cacheName,String key){
	        Cache cache = getCacheManager().getCache(cacheName);
	        cache.evict(key);
	}
	/**
	 * 创建缓存
	 * @param cacheName
	 * @param key
	 * @param object
	 */
	public static void createCache(String cacheName,String key,Object object){
        Cache cache = getCacheManager().getCache(cacheName);
        cache.put(key, object);
	}
	/**
	 * 判断是否缓存了数据
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static boolean hasCache(String cacheName,String key){
        Cache cache = getCacheManager().getCache(cacheName);
        if(cache == null){
        	return false;
        }
        return true;
	}
	/**
	 * 获取缓存
	 * @param cacheName
	 * @param key
	 * @param entityClass
	 * @return
	 */
	public static <T> T getCache(String cacheName,String key,Class<T> entityClass){
        Cache cache = getCacheManager().getCache(cacheName);
		if(hasCache(cacheName,key)){
        }
		return cache.get(key, entityClass);
	}
	/**
	 * 获取所有缓存
	 * @return
	 */
	public static Collection<String> getAllCache(){
		 
		 return  getCacheManager().getCacheNames();
	}
	/**
	 * 获取对应cache的所有key
	 * @param cache
	 * @return
	 */
	public static List<?> getCacheKeys(Cache cache){
		 
		 return  null;//cache.getKeys();
	}
	/**
	 * 获取所有cache和对应的所有的key
	 * @return
	 */
	public static Map<String ,List<?>> getAllCacheAndKeys(){
		Collection<String> cacheNames = getCacheManager().getCacheNames();
		Map<String ,List<?>> map = new HashMap<String ,List<?>>();
		for(String cacheName:cacheNames){
			map.put(cacheName, getCacheKeys(getCacheManager().getCache(cacheName)));
		}
		return map;
	}
	/**
	 * 获取cache详情
	 * @param cache
	 * @return
	 */
	public static Map<String,Object> getCacheParams(Cache cache){
		Map<String,Object> params = new HashMap<String,Object>();
		
/*		params.put("cacheSize", cache.getSize());
		params.put("memoryStoreSize", cache.getStatistics().getLocalHeapSize());
		params.put("cacheHits", cache.getStatistics().cacheHitCount());
		params.put("cacheMisses", cache.getStatistics().cacheMissCount());
		 */
		return params;
	}

}
