package com.forsys.conga.comparedatatypes;

public class Model2 {
	
	public String field;
	public String datatype;
	public Integer rownum;
	public Integer cellnum;
	public String c1DataType;
	public String a1DataType;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public Integer getRownum() {
		return rownum;
	}
	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}
	public Integer getCellnum() {
		return cellnum;
	}
	public void setCellnum(Integer cellnum) {
		this.cellnum = cellnum;
	}
	
	public String getC1DataType() {
		return c1DataType;
	}
	public void setC1DataType(String c1DataType) {
		this.c1DataType = c1DataType;
	}
	public String getA1DataType() {
		return a1DataType;
	}
	public void setA1DataType(String a1DataType) {
		this.a1DataType = a1DataType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Model2 other = (Model2) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		return true;
	}
	
	
	

}
