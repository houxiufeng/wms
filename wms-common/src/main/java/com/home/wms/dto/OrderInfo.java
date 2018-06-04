package com.home.wms.dto;

import com.home.wms.entity.Branch;
import com.home.wms.entity.BranchProduct;
import com.home.wms.entity.Product;
import com.home.wms.entity.Torder;

import java.io.Serializable;

/**
 * Created by fitz on 2018/6/2.
 */
public class OrderInfo implements Serializable{
    private OrderVo order;
    private BranchVo branch;
    private BranchProduct branchProduct;
    private ProductVo product;

    public OrderVo getOrder() {
        return order;
    }

    public void setOrder(OrderVo order) {
        this.order = order;
    }

    public BranchVo getBranch() {
        return branch;
    }

    public void setBranch(BranchVo branch) {
        this.branch = branch;
    }

    public BranchProduct getBranchProduct() {
        return branchProduct;
    }

    public void setBranchProduct(BranchProduct branchProduct) {
        this.branchProduct = branchProduct;
    }

    public ProductVo getProduct() {
        return product;
    }

    public void setProduct(ProductVo product) {
        this.product = product;
    }
}
