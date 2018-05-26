package com.home.wms.dto;

import com.home.wms.entity.Engineer;

import java.util.List;

/**
 * Created by fitz on 2018/3/11.
 */
public class EngineerVo extends Engineer {
	private String levelName;
	private List<String> skillList;

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public List<String> getSkillList() {
		return skillList;
	}

	public void setSkillList(List<String> skillList) {
		this.skillList = skillList;
	}
}
