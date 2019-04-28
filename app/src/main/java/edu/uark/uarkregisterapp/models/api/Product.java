package edu.uark.uarkregisterapp.models.api;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.fields.ProductFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;

public class Product implements ConvertToJsonInterface, LoadFromJsonInterface<Product>
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

	public Product setId(UUID id)
	{
		this.id = id;
		return this;
	}

	public Product setLookupCode(String lookupCode)
	{
		this.lookupCode = lookupCode;
		return this;
	}

	public Product setCount(int count)
	{
		this.count = count;
		return this;
	}

	public Product setCreatedOn(Date createdOn)
	{
		this.createdOn = createdOn;
		return this;
	}

	public Product setSold(int sold)
	{
		this.sold = sold;
		return this;
	}

	public Product setCost(double c)
	{
		this.cost = c;
		return this;
	}

	@Override
	public Product loadFromJson(JSONObject rawJsonObject)
	{
		String value = rawJsonObject.optString(ProductFieldName.ID.getFieldName());
		if (!StringUtils.isBlank(value)) {
			this.id = UUID.fromString(value);
		}

		this.lookupCode = rawJsonObject.optString(ProductFieldName.LOOKUP_CODE.getFieldName());
		this.count = rawJsonObject.optInt(ProductFieldName.COUNT.getFieldName());
		this.sold = rawJsonObject.optInt(ProductFieldName.SOLD.getFieldName());
		this.cost = rawJsonObject.optDouble(ProductFieldName.COST.getFieldName());

		value = rawJsonObject.optString(ProductFieldName.CREATED_ON.getFieldName());
		if (!StringUtils.isBlank(value)) {
			try {
				this.createdOn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return this;
	}

	@Override
	public JSONObject convertToJson() {
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put(ProductFieldName.ID.getFieldName(), this.id.toString());
			jsonObject.put(ProductFieldName.LOOKUP_CODE.getFieldName(), this.lookupCode);
			jsonObject.put(ProductFieldName.COUNT.getFieldName(), this.count);
			jsonObject.put(ProductFieldName.SOLD.getFieldName(), this.sold);
			jsonObject.put(ProductFieldName.COST.getFieldName(), this.cost);
			jsonObject.put(ProductFieldName.CREATED_ON.getFieldName(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)).format(this.createdOn));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	public Product()
	{
		this.count = -1;
		this.lookupCode = "";
		this.id = new UUID(0, 0);
		this.createdOn = new Date();
		this.sold = -1;
		this.cost = 0.0;
	}

	public Product(ProductTransition productTransition)
	{
		this.id = productTransition.getId();
		this.count = productTransition.getCount();
		this.sold = productTransition.getSold();
		this.createdOn = productTransition.getCreatedOn();
		this.lookupCode = productTransition.getLookupCode();
		this.cost = productTransition.getCost();
	}
}
