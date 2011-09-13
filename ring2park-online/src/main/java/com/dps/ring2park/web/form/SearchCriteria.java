package com.dps.ring2park.web.form;

import java.io.Serializable;

/**
 * A backing bean for the main Location search form. Encapsulates the criteria
 * needed to perform a location search.
 */
public class SearchCriteria implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The user-provided search criteria for finding Parking Locations.
	 */
	private String searchString;

	/**
	 * The maximum page size of the Parking Location result list
	 */
	private int pageSize;

	/**
	 * The current page of the Parking Location result list.
	 */
	private int page;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}
