package cn.feezu.maint.assign.entity;

import java.util.Map;

import lombok.Data;
@Data
public class CheckListDTO {
	/**
     * 檢查項目
     */
    private Map<String,Map<String,Object>> contents;
    
}
