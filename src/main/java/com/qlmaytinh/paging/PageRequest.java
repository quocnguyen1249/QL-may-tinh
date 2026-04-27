package com.qlmaytinh.paging;

import com.qlmaytinh.sort.Sorter;

import lombok.Builder;
@Builder
public class PageRequest implements Pageble{
	private Integer page;
	private Sorter sorter;
	private Integer maxPageItem;
	@Override
	public Integer getPage() {
		return this.page;
	}
	@Override
	public Integer getOffset() {
		if(this.page != null && this.maxPageItem != null)
			return (this.page - 1 ) * this.maxPageItem;
		return null;
	}
	@Override
	public Integer getLimit() {
		return this.maxPageItem;
	}
	@Override
	public Sorter getSorter() {
		if(this.sorter != null) 
			return this.sorter;
		return null;
	}
	
}