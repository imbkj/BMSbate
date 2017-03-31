package Util;

import java.util.LinkedList;
import java.util.List;

public class CategoryModel {
	private Integer id;
	private String name;
	private String description;
	private String link;
	private CategoryModel child;

	private List<CategoryModel> children = new LinkedList<CategoryModel>();

	public CategoryModel(Integer id, String name, String description,
			String link) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.link = link;
	}

	public void addChild(CategoryModel child) {
		if (child != null)
			children.add(child);
	}

	public void removeChild(CategoryModel child) {
		if (child != null)
			children.remove(child);
	}

	public void removeChild(Integer childId) {
		if (id != null) {
			children.remove(id);
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<CategoryModel> getChildren() {
		return children;
	}

	public void setChildren(List<CategoryModel> children) {
		this.children = children;
	}

}
