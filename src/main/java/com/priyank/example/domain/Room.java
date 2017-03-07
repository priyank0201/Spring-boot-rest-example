package com.priyank.example.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "Room")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public final class Room {
	
	@Id
	private Long id;

    private Type type;

    private Integer price;
    
    public Room( ) {}

    public Room( Type type,  Integer price) {
        this.type = type;
        this.price = price;
    }

    public enum Type {

        SINGLE_ROOM,
        
        DOUBLE_ROOM

    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public Integer getPrice() {
		return price;
	}
  
}
