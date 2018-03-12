package com.home.wms.dto;

import com.home.wms.entity.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fitz on 2017/6/10.
 */
public class PermissionTree {
	private Permission treeNode;
	private List<Permission> subNodes;

	public Permission getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(Permission treeNode) {
		this.treeNode = treeNode;
	}

	public List<Permission> getSubNodes() {
		return subNodes;
	}

	public void setSubNodes(List<Permission> subNodes) {
		this.subNodes = subNodes;
	}

	public void addSubNode(Permission permission) {
		if (this.subNodes == null) {
			this.subNodes = new ArrayList<Permission>();
		}
		this.subNodes.add(permission);
	}
}
