package edu.uark.uarkregisterapp.models.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.Comparator;

public class ShoppingCart
{
    private ArrayList<ProductList> products;
    private double total;

    public ShoppingCart()
    {
        this.products = new ArrayList<ProductList>();
        this.total = 0;
    }

    void setTotal(double t)
        {this.total = t;}

    void addTotal(double t)
        {this.total = this.total + t;}

    double getTotal()
        {return this.total;}

    //iterates over arraylist and calculates total
    void updateTotal()
    {
        this.total = 0.0;
        Iterator<ProductList> iter = this.products.iterator();
        while(iter.hasNext())
        {
            ProductList arr_prod = iter.next();
            double price = arr_prod.getProduct().getPrice();
            this.total = this.total + price;
        }
    }

    public void addProduct(Product p)
    {
        ProductList add = new ProductList(p);
        this.products.add(add);
    }
    public Product removeProduct(Product p)
    {
        UUID in_id = p.getId();
        Iterator<ProductList> iter = this.products.iterator();
        while(iter.hasNext())
        {
            ProductList arr_prod = iter.next();
            UUID arr_id = arr_prod.getProduct().getId();
            if(arr_id.equals(in_id))
            {
                this.products.remove(arr_prod);
                return arr_prod.getProduct();
            }
        }
        return null;
    }
    //need to convert to products and return
    public ArrayList<Product> getProducts()
    {
        ArrayList<Product> out = new ArrayList<Product>();
        Iterator<ProductList> iter = this.products.iterator();
        while(iter.hasNext())
        {
            out.add(iter.next().getProduct());
        }
        return out;
    }

    static public class ProductComparator implements Comparator<ProductList>
    {
        public int compare(ProductList a, ProductList b)
        {
            if(a.getProduct().getId() == b.getProduct().getId())
            {
                return 0;
            }
            else
            {
                return 1;
            }
            /*else if(a.getId() > b.getId())
            {
                return 1;
            }
            else
            {
                return -1;
            }*/
        }
    }

    static public class ProductList
    {
        private Product product;
        private int quantity;

        ProductList()
        {
            this.product = new Product();
            this.quantity = 0;
        }

        ProductList(Product p)
        {
            this.product = p;
            this.quantity = 0;
        }

        public void setProduct(Product p)
        {
            this.product = p;
        }
        public void setQuantity(int q)
        {
            this.quantity = q;
        }
        public void addQuantity()
        {
            this.quantity = this.quantity + 1;
        }

        public Product getProduct()
        {
            return this.product;
        }
        public int getQuantity()
        {
            return this.quantity;
        }
    }
}
