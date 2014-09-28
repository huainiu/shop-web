package com.b5m.bean.entity;

import com.b5m.dao.annotation.Column;

/**
 * @author echo
 */
public abstract class BaseSupplierWrap extends DomainObject {
	@Column(name = "suppliser_id")
	protected Long supplierId;

	protected Suppliser suppliser;

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Suppliser getSuppliser() {
		return suppliser;
	}

	public void setSuppliser(Suppliser suppliser) {
		this.suppliser = suppliser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((supplierId == null) ? 0 : supplierId.hashCode());
		result = prime * result + ((suppliser == null) ? 0 : suppliser.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseSupplierWrap other = (BaseSupplierWrap) obj;
		if (supplierId == null) {
			if (other.supplierId != null)
				return false;
		} else if (!supplierId.equals(other.supplierId))
			return false;
		if (suppliser == null) {
			if (other.suppliser != null)
				return false;
		} else if (!suppliser.equals(other.suppliser))
			return false;
		return true;
	}

}
