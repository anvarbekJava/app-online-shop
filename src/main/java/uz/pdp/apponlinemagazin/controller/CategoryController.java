package uz.pdp.apponlinemagazin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apponlinemagazin.domain.Category;
import uz.pdp.apponlinemagazin.payload.CategoryDto;
import uz.pdp.apponlinemagazin.payload.Result;
import uz.pdp.apponlinemagazin.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody CategoryDto dto){
        Result result = categoryService.add(dto);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }


    @PutMapping("/edet/{id}")
    public HttpEntity<?> edet(@PathVariable Integer id, @RequestBody CategoryDto dto){
        Result result = categoryService.edet(id, dto);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);
    }

    //Cateogry ni statusini o'zgartirish

    @PutMapping("/enabled/{id}")
    public HttpEntity<?> enabledCategory(@PathVariable Integer id){
        Result result = categoryService.enabledCategory(id);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);
    }


    @GetMapping
    public HttpEntity<?> getAll(){
        List<Category> categoryList = categoryService.getAll();
        return ResponseEntity.ok(categoryList);
    }
    //Assosiy bolimga category royhati true va parentCategory == null

    @GetMapping("/main")
    public HttpEntity<?> getMainCategory(){
        List<Category> dtoList = categoryService.get();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id){
        Category category = categoryService.getOne(id);
        return ResponseEntity.ok(category);
    }

   @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        Result result = categoryService.delete(id);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);
   }
}
