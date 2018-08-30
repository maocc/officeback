package cn.feezu.maintapp.config;

import java.io.IOException;

import javax.annotation.PreDestroy;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class EhcacheConfiguration {
	@PreDestroy
	public void destory() {
		if (cacheManager() != null && cacheManager() instanceof EhCacheCacheManager) {
			EhCacheCacheManager cacheManager = (EhCacheCacheManager) cacheManager();
			cacheManager.getCacheManager().shutdown();
		}
	}

	@Bean
	public CacheManager cacheManager(){

        EhCacheCacheManager cacheManager = new EhCacheCacheManager();

        net.sf.ehcache.CacheManager ehcacheManager = null;
        try {
            ehcacheManager = net.sf.ehcache.CacheManager.create(new ClassPathResource("ehcache.xml").getURL());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置底层的CacheManager
        cacheManager.setCacheManager(ehcacheManager);

		return cacheManager;
	}

}
