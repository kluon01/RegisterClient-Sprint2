package edu.uark.uarkregisterapp.models.api;

public class ShoppingCart
{
    private Product product;
    private double total;
    public int quantity;

    public ShoppingCart()
    {
        this.total = 0;
        this.quantity = 1;
    }

    public ShoppingCart(Product product)
    {
        // Clean copy of product object
        this.product = product;
       /* this.product.setCount(product.getCount());
        this.product.setCreatedOn(product.getCreatedOn());
        this.product.setId(product.getId());
        this.product.setLookupCode(product.getLookupCode());
        this.product.setPrice(product.getPrice());
        this.product.setSold(product.getSold());*/

        this.quantity = 1;
        this.total = quantity * product.getPrice();
    }

    public ShoppingCart(Product product, int quantity)
    {
        // Clean copy of product object
        this.product = product;
        /*this.product.setCount(product.getCount());
        this.product.setCreatedOn(product.getCreatedOn());
        this.product.setId(product.getId());
        this.product.setLookupCode(product.getLookupCode());
        this.product.setPrice(product.getPrice());
        this.product.setSold(product.getSold());*/

        this.quantity = quantity;
        this.total = quantity * product.getPrice();
    }

    public void increaseQuantity() { this.quantity++; }

    public Product getProduct() { return this.product; }

    public int getQuantity() { return this.quantity; }

}
