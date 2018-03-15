package com.home.wms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import com.home.wms.dto.QueryDictParams;
import com.home.wms.dto.QueryVendorParams;
import com.home.wms.dto.VendorVo;
import com.home.wms.entity.Branch;
import com.home.wms.entity.Dict;
import com.home.wms.entity.Vendor;
import com.home.wms.enums.DictType;
import com.home.wms.service.DictService;
import com.home.wms.service.VendorService;
import com.home.wms.utils.AppContextManager;
import com.ktanx.common.model.PageList;
import com.ktanx.jdbc.command.entity.Select;
import com.ktanx.jdbc.persist.JdbcDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fitz on 2018/3/11.
 */
@Service
public class VendorServiceImpl implements VendorService {
	@Autowired
	private JdbcDao jdbcDao;

	@Autowired
	private DictService dictService;

	@Override
	public PageList<VendorVo> findPageVendors(QueryVendorParams params) {
		Select<Vendor> s = jdbcDao.createSelect(Vendor.class);
		if (params.getOrganizationId() != null) {
			s.and("organizationId",params.getOrganizationId());
		}
		if (StringUtils.isNotBlank(params.getName())) {
			s.and("name","like",new Object[]{"%"+params.getName().trim()+"%"});
		}
		if (params.getStatus() != null) {
			s.and("status",params.getStatus());
		}
		PageList<Vendor> list =  s.orderBy("id").desc().pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
	    List<VendorVo> vos = Lists.newArrayListWithCapacity(list.size());

	    QueryDictParams dictParams = new QueryDictParams();
		dictParams.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		dictParams.setType(DictType.VENDOR_LEVEL.getValue());
		dictParams.setiDisplayLength(100);
		List<Dict> customerLevels = dictService.findPageDicts(dictParams);
		dictParams.setType(DictType.MAINTAIN_SKILL.getValue());
		List<Dict> maintainSkills = dictService.findPageDicts(dictParams);
	    for (Vendor item : list) {
	    	VendorVo vo = new VendorVo();
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
	public void saveVendor(Vendor vendor) {
		vendor.setCreatedBy(AppContextManager.getCurrentUserInfo().getId());
		vendor.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
        jdbcDao.insert(vendor);
	}

	@Override
	public void updateVendor(Vendor vendor) {
        jdbcDao.update(vendor);
	}

	@Override
	public void deleteVendor(Long id) {
        jdbcDao.delete(Vendor.class, id);
	}

	@Override
	public Vendor getVendorById(Long id) {
		return jdbcDao.get(Vendor.class, id);
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
