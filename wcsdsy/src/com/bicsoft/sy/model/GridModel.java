package com.bicsoft.sy.model;

import java.util.List;
import com.bicsoft.sy.model.PageModel;;

public class GridModel {
	private int total;
	private int page;
	private int records;
	private List<?> rows;

	public GridModel(PageModel pageModel) {
		this.total = pageModel.getTotalPage();
		this.page = pageModel.getPage();
		this.records = pageModel.getTotalCount();
		this.rows = pageModel.getResult();
	}

	public GridModel(List<?> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRecords() {
		return this.records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public List<?> getRows() {
		return this.rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}
}
