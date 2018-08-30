package cn.feezu.maintweb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

	private String webHost;

	private String tempFlePath;
	public String getWebHost() {
		return webHost;
	}

	public void setWebHost(String webHost) {
		this.webHost = webHost;
	}

	public String getTempFlePath() {
		return tempFlePath;
	}

	public void setTempFlePath(String tempFlePath) {
		this.tempFlePath = tempFlePath;
	}

}
