package com.highd120.endstart.item;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ExtraCraftData {
    private Map<String, Integer> widthMap;
	private Map<String, String> recipeTemplate;
	private Map<String, List<String>> nbtFilter;
	private Map<String, Boolean> isPrint;
}
