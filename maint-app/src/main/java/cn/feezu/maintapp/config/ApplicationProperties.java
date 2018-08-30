package cn.feezu.maintapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

	private String webHost;

	public String getWebHost() {
		return webHost;
	}

	public void setWebHost(String webHost) {
		this.webHost = webHost;
	}

}
