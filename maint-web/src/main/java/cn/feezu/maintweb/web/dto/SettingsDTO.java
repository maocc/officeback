package cn.feezu.maintweb.web.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingsDTO {

	private Map<String, SettingContent> paramSet;
}
