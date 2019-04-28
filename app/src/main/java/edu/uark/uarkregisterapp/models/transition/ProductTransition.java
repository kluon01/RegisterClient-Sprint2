package edu.uark.uarkregisterapp.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.UUID;

import edu.uark.uarkregisterapp.commands.converters.ByteToUUIDConverterCommand;
import edu.uark.uarkregisterapp.commands.converters.UUIDToByteConverterCommand;
import edu.uark.uarkregisterapp.models.api.Product;

public class ProductTransition implements Parcelable
{
	private UUID id;
	private String lookupCode;
	private int count;
	private Date createdOn;
	private int sold;
	private double cost;

	public UUID getId() { return this.id; }
	public String getLookupCode() { return this.lookupCode; }
	public int getCount() { return this.count; }
	public Date getCreatedOn() { return this.createdOn; }
	public int getSold() { return this.sold; }
	public double getCost() { return this.cost; }

	public ProductTransition setId(UUID id)
	{
		this.id = id;
		return this;
	}

	public ProductTransition setLookupCode(String lookupCode)
	{
		this.lookupCode = lookupCode;
		return this;
	}

	public ProductTransition setCount(int count)
	{
		this.count = count;
		return this;
	}

	public ProductTransition setCreatedOn(Date createdOn)
	{
		this.createdOn = createdOn;
		return this;
	}

	public ProductTransition setSold(int sold)
	{
		this.sold = sold;
		return this;
	}

	public ProductTransition setCost(double c)
	{
		this.cost = c;
		return this;
	}

	@Override
	public void writeToParcel(Parcel destination, int flags)
	{
		destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.id).execute());
		destination.writeString(this.lookupCode);
		destination.writeInt(this.count);
		destination.writeInt(this.sold);
		destination.writeLong(this.createdOn.getTime());
		destination.writeDouble(this.cost);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ProductTransition> CREATOR = new Creator<ProductTransition>()
	{
		public ProductTransition createFromParcel(Parcel productTransitionParcel)
		{
			return new ProductTransition(productTransitionParcel);
		}

		public ProductTransition[] newArray(int size) {
			return new ProductTransition[size];
		}
	};

	public ProductTransition()
	{
		this.count = -1;
		this.id = new UUID(0, 0);
		this.createdOn = new Date();
		this.lookupCode = StringUtils.EMPTY;
		this.sold = -1;
		this.cost = 0.0;
	}

	public ProductTransition(Product product)
	{
		this.id = product.getId();
		this.count = product.getCount();
		this.createdOn = product.getCreatedOn();
		this.lookupCode = product.getLookupCode();
		this.sold = product.getSold();
		this.cost = product.getCost();
	}

	private ProductTransition(Parcel productTransitionParcel)
	{
		this.id = (new ByteToUUIDConverterCommand()).setValueToConvert(productTransitionParcel.createByteArray()).execute();
		this.lookupCode = productTransitionParcel.readString();
		this.count = productTransitionParcel.readInt();
		this.sold = productTransitionParcel.readInt();
		this.cost = productTransitionParcel.readDouble();
		this.createdOn = new Date();
		this.createdOn.setTime(productTransitionParcel.readLong());
	}
}
