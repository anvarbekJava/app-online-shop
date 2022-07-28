package uz.pdp.apponlinemagazin.service;

import uz.pdp.apponlinemagazin.domain.Products;
import uz.pdp.apponlinemagazin.payload.ProductDto;
import uz.pdp.apponlinemagazin.payload.Result;

import java.util.List;

public interface ProductService {
    Result addProduct(ProductDto dto);

    Result edet(Integer id, ProductDto dto);


    List<Products> getAll();

    List<Products> getByCategoryId(Integer categoryId);

    List<Products> getCategoryId(Integer categoryId);

    Products getOne(Integer id);

    Result delete(Integer id);

    Result enabledProduct(Integer id);
}
