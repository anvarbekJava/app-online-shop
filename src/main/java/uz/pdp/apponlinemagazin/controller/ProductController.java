package uz.pdp.apponlinemagazin.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apponlinemagazin.domain.Products;
import uz.pdp.apponlinemagazin.payload.ProductDto;
import uz.pdp.apponlinemagazin.payload.Result;
import uz.pdp.apponlinemagazin.service.ProductService;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@SecurityRequirement(name = "javainuseapi")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping
    public HttpEntity<?> add(@RequestBody ProductDto dto){
        Result result = productService.addProduct(dto);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edet(@PathVariable Integer id, @RequestBody ProductDto dto){
        Result result = productService.edet(id, dto);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);
    }

   @GetMapping
    public HttpEntity<?> getAll(){
        List<Products> productsList = productService.getAll();
        return ResponseEntity.ok(productsList);
   }
   // Product by category id native query
   @GetMapping("/category/{categoryId}")
    public HttpEntity<?> getByCategoryId(@PathVariable Integer categoryId){
        List<Products> productsList = productService.getByCategoryId(categoryId);
        return ResponseEntity.ok(productsList);
   }
//    // PRODUCT GET BY CATEGORY ID IN JPA QUERY
//   @GetMapping("/category/jpa/{categoryId}")
//    public HttpEntity<?> getCategoryId(@PathVariable Integer categoryId){
//        List<Products> list = productService.getCategoryId(categoryId);
//        return ResponseEntity.ok(list);
//   }

   // PRODUCT GET BY ID
   @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id){
        Products products = productService.getOne(id);
        return ResponseEntity.ok(products);
   }
    //DELETE PRODUCT BY ID
   @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        Result result = productService.delete(id);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);
   }

   @PutMapping("/enabled/{id}")
    public HttpEntity<?> enabledProduct(@PathVariable Integer id){
        Result result = productService.enabledProduct(id);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);
   }
}
