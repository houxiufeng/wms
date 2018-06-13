package com.home.wms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import com.home.wms.dto.EngineerVo;
import com.home.wms.dto.QueryDictParams;
import com.home.wms.dto.QueryEngineerParams;
import com.home.wms.entity.Dict;
import com.home.wms.entity.Engineer;
import com.home.wms.enums.DictType;
import com.home.wms.service.DictService;
import com.home.wms.service.EngineerService;
import com.home.wms.utils.AppContextManager;
import com.ktanx.common.model.PageList;
import com.ktanx.jdbc.command.entity.Select;
import com.ktanx.jdbc.persist.JdbcDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fitz on 2018/3/11.
 */
@Service
public class EngineerServiceImpl implements EngineerService {
	@Autowired
	private JdbcDao jdbcDao;

	@Autowired
	private DictService dictService;

	@Override
	public PageList<EngineerVo> findPageEngineer(QueryEngineerParams params) {
		Select<Engineer> s = jdbcDao.createSelect(Engineer.class);
		if (params.getOrganizationId() != null) {
			s.and("organizationId",params.getOrganizationId());
		}
		if (StringUtils.isNotBlank(params.getName())) {
			s.and("name","like",new Object[]{"%"+params.getName().trim()+"%"});
		}
		if (params.getStatus() != null) {
			s.and("status",params.getStatus());
		}
		PageList<Engineer> list =  s.orderBy("id").desc().pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
	    List<EngineerVo> vos = Lists.newArrayListWithCapacity(list.size());

	    QueryDictParams dictParams = new QueryDictParams();
		dictParams.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		dictParams.setType(DictType.ENGINEER_LEVEL.getValue());
		dictParams.setiDisplayLength(100);
		List<Dict> customerLevels = dictService.findPageDicts(dictParams);
		dictParams.setType(DictType.MAINTAIN_SKILL.getValue());
		List<Dict> maintainSkills = dictService.findPageDicts(dictParams);
	    for (Engineer item : list) {
	    	EngineerVo vo = new EngineerVo();
			BeanUtil.copyProperties(item, vo);
			vo.setLevelName(findDictName(customerLevels, item.getLevel()));
			if (StringUtils.isNotBlank(item.getSkill())) {
				String[] skills = item.getSkill().split(",");
				List<String> skillList = Lists.newArrayListWithCapacity(skills.length);
				for (String skillId : skills) {
					skillList.add(findDictName(maintainSkills, Longs.tryParse(skillId)));
				}
				vo.setSkillList(skillList);
			}
			vos.add(vo);
	    }
	    PageList newPageList = new PageList(vos, list.getPager());
	    return newPageList;
	}

	@Override
	public void saveEngineer(Engineer engineer) {
		engineer.setCreatedBy(AppContextManager.getCurrentUserInfo().getId());
		engineer.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
        jdbcDao.insert(engineer);
	}

	@Override
	public void updateEngineer(Engineer engineer) {
        jdbcDao.update(engineer);
	}

	@Override
	public void deleteEngineer(Long id) {
        jdbcDao.delete(Engineer.class, id);
	}

	@Override
	public Engineer getEngineerById(Long id) {
		return jdbcDao.get(Engineer.class, id);
	}
    @Override
    public Engineer getEngineerByUserId(Long userId) {
		Engineer engineer = new Engineer();
		engineer.setUserId(userId);
		return jdbcDao.querySingleResult(engineer);
    }
    public EngineerVo getEngineerVoByUserId(Long userId) {
		Engineer engineer =  this.getEngineerByUserId(userId);
	    EngineerVo engineerVo = null;
		if (engineer != null) {
			engineerVo = new EngineerVo();
			BeanUtil.copyProperties(engineer, engineerVo);
			engineerVo.setLevelName(dictService.findDictNamesByIds(engineer.getLevel().toString()));
			if (StringUtils.isNotBlank(engineer.getSkill())) {
				String skillStr = dictService.findDictNamesByIds(engineer.getSkill());
				if (StringUtils.isNotBlank(skillStr)) {
					String[] s = skillStr.split(",");
					engineerVo.setSkillList(Lists.newArrayList(s));
				}
			}
		}
		return engineerVo;
    }


	private String findDictName(List<Dict> levelList, Long dictId) {
		for (Dict dict : levelList) {
			if (dict.getId().longValue() == dictId.longValue()) {
				return dict.getName();
			}
		}
		return "";
	}

}
