package com.dhee.vo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/*
 * create table book
	(
		id int primary key,		--ͼ���ţ�����
		name varchar(100) not null unique,	--ͼ�����ƣ�uniqueԼ��
		author varchar(40) not null,		--����
		price double not null,			--�۸�
		image varchar(100),			--ͼƬ����
		description varchar(255),		--����
		category_id int,		--ͼ�����ͱ��
		
	);

 */
@Entity
@Table(name="books")
public class BooksVo {

	private int id;
	private String name;
	private String author;
	private double price;
	private String image;
	private String description;
	private int category_id;
	
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	@Id
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/*public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}*/
	
	
	
	
}
