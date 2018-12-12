package com.tdh.gps.console.model;

import java.io.Serializable;

/**
 * 
 * @ClassName: Role  
 * @Description: (角色信息)  
 * @author wxf
 * @date 2018年12月5日 下午5:21:03  
 *
 */
public class Role implements Serializable {

	/**
	 * @Fields serialVersionUID : (版本编号)
	 */
	private static final long serialVersionUID = -2684028085415482889L;

	public Role(String name) {
		this.name = name;
	}

	public Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
