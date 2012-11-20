package com.vibeit.api.model;

import android.provider.BaseColumns;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @author Andrii Kovalov
 */
@DatabaseTable(tableName = "category")
public class Category implements BaseColumns, Serializable {
	@DatabaseField(columnName = _ID, id = true)
	private int id;
	@DatabaseField(columnName = "name")
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Category category = (Category) o;

		if (id != category.id) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id;
	}
}