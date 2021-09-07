package com.forsys.conga.comparedatatypes;

import java.util.List;

public class Model {
	
	public String field;
	public String datatype;
	public List<String> picklist;
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
	public List<String> getPicklist() {
		return picklist;
	}
	public void setPicklist(List<String> picklist) {
		this.picklist = picklist;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datatype == null) ? 0 : datatype.hashCode());
		result = prime * result + ((field == null) ? 0 : field.hashCode());
//		result = prime * result + ((picklist == null) ? 0 : picklist.hashCode());
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
		Model other = (Model) obj;
		if (datatype == null) {
			if (other.datatype != null)
				return false;
		} else if (!datatype.equals(other.datatype))
			return false;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
//		if (picklist == null) {
//			if (other.picklist != null)
//				return false;
//		} else if (!picklist.equals(other.picklist))
//			return false;
		return true;
	}
		

}
