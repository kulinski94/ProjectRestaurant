package com.ittalents.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Quote implements Serializable
{

	private String code;
	private float price;
	//private Timestamp ts;

	public Quote(String code, float price, Timestamp ts)
	{
		super();
		this.code = code;
		this.price = price;
		//this.ts = ts;
	}

	public float getPrice()
	{
		return price;
	}

	public void setPrice(float price)
	{
		this.price = price;
	}

//	public Timestamp getTs()
//	{
//		return ts;
//	}
//
//	public void setTs(Timestamp ts)
//	{
//		this.ts = ts;
//	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

//	@Override
//	public String toString()
//	{
//		return "Quote [code=" + code + ", price=" + price + ", ts=" + ts + "]";
//	}
}
