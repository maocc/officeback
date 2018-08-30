package cn.feezu.common.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;

public class Pagination<T> {  
    
    private long total;  
      
    private int pagesize;  
      
    private List<T> list = Collections.emptyList();  
      
    private int offset;  
      
    private int limit;  
      
    private int page;  
      
    private int startPage;
    private int endPage;
    //遍历辅助

    private List<Integer> listNumber= new ArrayList<Integer>();
    public Pagination() {  
        this(1, 20);  
    }  
      
    public Pagination(int page) {  
        this(page, 20);  
    }  
      
    public Pagination(int page, int limit) {  
        setPage(page);  
        setLimit(limit);  
        setPage();
    }  
    
    public Pagination(int page,int limit,Page<T> pages){
    	   setPage(page);  
           setLimit(limit); 
           setList(pages.getContent());
           setTotal(pages.getTotalElements());
           setPage();
          
    }
    public Pagination(Page<T> pages){
    	this(pages.getNumber()+1, pages.getSize()); 
 	   	setPage(page);  
        setLimit(limit); 
        setList(pages.getContent());
        setTotal(pages.getTotalElements());
        setPage();
       
 } 
    public void setPage() {  
    	startPage =1;
    	endPage = pagesize;
    	if(pagesize > 5){
    		if(page - 2 <= 0 ){
    			startPage=1;
    			endPage =5;
    		}else if(page + 2  > pagesize){
    			startPage = pagesize -4;
    			endPage = pagesize;
    		}else if((page  - 2 > 0 ) && (page - 2 < pagesize)){
    			startPage = page -2;
    			endPage = page+2;
    		}
    	}
    	 
    	for(int i=startPage;i<=endPage;i++){
    		listNumber.add(i);
    	}
    }  
    public void setPage(int page) {  
        if (page < 0) {  
            page = 1;  
        }  
        this.page = page;  
        onInit();  
    }  
      
    public void setLimit(int limit) {  
        if (limit < 1) {  
            limit = 15;  
        }  
        this.limit = limit;  
        onInit();  
        setPage();
    }  
      
    protected void onInit() {  
        offset = (page - 1) * limit;  
    }  
      
    protected void onSetRowsize() {  
        pagesize = (int) (total / limit);  
        if (total % limit > 0) {  
            pagesize ++;  
        }  
        if (page > pagesize) {  
            page = pagesize;  
            onInit();  
        }  
    }  
      
    protected void onSetList() {  
        if (list == null || list.isEmpty()) {  
            total = 0;  
            page = 1;  
            offset = 0;  
        }  
    }  
  
    public long getTotal() {  
        return total;  
    }  
  
    public void setTotal(long rowsize) {  
        this.total = rowsize;  
        onSetRowsize();  
    }  
  
    public int getPagesize() {  
        return pagesize;  
    }  
  
    public List<T> getList() {  
        return list;  
    }  
      
    public void setList(List<T> list) {  
        this.list = list;  
        onSetList();  
        setPage();
    }  
  
    public int getOffset() {  
        return offset;  
    }  
  
    public int getLimit() {  
        return limit;  
    }  
  
    public int getPage() {  
        return page;  
    }

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public List<Integer> getListNumber() {
		return listNumber;
	}

	public void setListNumber(List<Integer> listNumber) {
		this.listNumber = listNumber;
	}


}
