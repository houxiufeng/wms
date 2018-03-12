package com.home.wms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.home.wms.dto.CurrentUserInfo;
import com.home.wms.dto.ProductVo;
import com.home.wms.dto.QueryProductParams;
import com.home.wms.entity.Branch;
import com.home.wms.entity.Product;
import com.home.wms.service.ProductService;
import com.home.wms.utils.AppContextManager;
import com.ktanx.common.model.PageList;
import com.ktanx.jdbc.command.entity.Select;
import com.ktanx.jdbc.persist.JdbcDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fitz on 2018/3/6.
 */
@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private JdbcDao jdbcDao;
	private static Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public PageList<Product> findPageProducts(QueryProductParams params) {
		Select<Product> s = jdbcDao.createSelect(Product.class);
		if (params.getOrganizationId() != null) {
			s.and("organizationId",params.getOrganizationId());
		}
		if (StringUtils.isNotBlank(params.getName())) {
			s.and("name","like",new Object[]{"%"+params.getName().trim()+"%"});
		}
		if (StringUtils.isNotBlank(params.getModel())) {
			s.and("model","like",new Object[]{"%"+params.getModel().trim()+"%"});
		}
		return s.orderBy("id").desc().pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
	}

	@Override
	public void save(Product product) {
		CurrentUserInfo currentUserInfo = AppContextManager.getCurrentUserInfo();
		product.setCreatedBy(currentUserInfo.getId());
		product.setOrganizationId(currentUserInfo.getOrganizationId());
        jdbcDao.insert(product);
	}

	@Override
	public void update(Product product) {
		jdbcDao.update(product);
	}

	@Override
	public void delete(Long id) {
		jdbcDao.delete(Product.class, id);
	}

	@Override
	public Product getById(Long id) {
		return jdbcDao.get(Product.class, id);
	}

	@Override
	public ProductVo getVoById(Long id) {
		Product product = jdbcDao.get(Product.class, id);
		ProductVo vo = new ProductVo();
		BeanUtil.copyProperties(product, vo);
		return vo;
	}

	@Override
	public List<Product> findByConditions(Product product) {
		return jdbcDao.queryList(product);
	}
}
