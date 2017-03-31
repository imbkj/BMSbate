package Util;

import java.util.LinkedList;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeNode;
import Util.CategoryModel;

public class CategoryTreeNode extends DefaultTreeNode<CategoryModel> {
	/**
	 * 
	 */
	int count;

	public CategoryTreeNode(CategoryModel cm, int count) {
		super(cm, new LinkedList<TreeNode<CategoryModel>>());
		this.count = count;
	}

	public Integer getId() {
		return getData().getId();
	}

	public String getDescription() {
		return getData().getDescription();
	}

	public String getLink() {
		return getData().getLink();
	}

	public int getCount() {
		return count;
	}

	public boolean isLeaf() {
		return getData() != null && getData().getChildren().isEmpty();
	}
}
