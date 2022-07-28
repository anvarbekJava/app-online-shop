package uz.pdp.apponlinemagazin.service;

import uz.pdp.apponlinemagazin.domain.Category;
import uz.pdp.apponlinemagazin.payload.CategoryDto;
import uz.pdp.apponlinemagazin.payload.Result;

import java.util.List;

public interface CategoryService {
    Result add(CategoryDto dto);

    Result edet(Integer id, CategoryDto dto);

    List<Category> get();

    Category getOne(Integer id);

    Result enabledCategory(Integer id);

    List<Category> getAll();

    Result delete(Integer id);
}
