package edu.itmo.pbks.io;

public class District {
	
	private String title;
	private Integer okato;
	private Integer code;
	
	public District(){
		
	}
	
	public District(String title, Integer okato, Integer code) {
		super();
		this.title = title;
		this.okato = okato;
		this.code = code;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getOkato() {
		return okato;
	}
	public void setOkato(Integer okato) {
		this.okato = okato;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	} 

}
