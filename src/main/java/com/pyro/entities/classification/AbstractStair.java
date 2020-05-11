package com.pyro.entities.classification;

import com.pyro.entities.AbstractEntity;
import com.pyro.entities.Product;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStair extends AbstractEntity {

    public abstract String getName();

    public abstract List<Product> getAllProducts();

    public Product product;

    public Product getProduct(){
        return getAllProducts().get(0);
    }

    public String src;

    public void setProduct(){}

    public String getSrc(){
        return getProduct().getSrc();
    }

    public void setSrc(){};
}
