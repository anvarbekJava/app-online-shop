package uz.pdp.apponlinemagazin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.apponlinemagazin.domain.Category;
import uz.pdp.apponlinemagazin.payload.CategoryDto;
import uz.pdp.apponlinemagazin.payload.Result;
import uz.pdp.apponlinemagazin.repository.CategoryRepository;
import uz.pdp.apponlinemagazin.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Result add(CategoryDto dto) {
        if (categoryRepository.existsByName(dto.getName()))
            return new Result("Bunday category mavjud", false);
        Category category = new Category();
        category.setName(dto.getName());

        if (dto.getOrder()!=null){
            category.setOrdered(dto.getOrder());
        }
        if (dto.getParentCategoryId()!=null){
            Optional<Category> optionalCategory = categoryRepository.findById(dto.getParentCategoryId());
            if (!optionalCategory.isPresent()){
                return new Result("Bunday ota categoriya mavjud emas!", false);
            }
            category.setParentCategory(optionalCategory.get());

        }
        categoryRepository.save(category);
        return new Result("Category saqlandi", true);
    }

    @Override
    public Result edet(Integer id, CategoryDto dto) {
        Optional<Category> optionalCategory =
                categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return new Result("Bunday category mavjud", false);
        Category category = optionalCategory.get();
        category.setName(dto.getName());
        if (dto.getParentCategoryId()!=null){
            Optional<Category> optionalParentCategory = categoryRepository.findById(dto.getParentCategoryId());
            if (!optionalParentCategory.isPresent()){
                return new Result("Bunday ota categoriya mavjud emas!", false);
            }
            category.setParentCategory(optionalParentCategory.get());
        }
        if (dto.getOrder()!=null){
            category.setOrdered(dto.getOrder());
        }

        categoryRepository.save(category);
        return new Result("Edet category", true);
    }

    @Override
    public Result enabledCategory(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return new Result("category topilmadi", false);
        Category category = optionalCategory.get();
        if (category.isEnabled()){
            category.setEnabled(false);
        }else {
            category.setEnabled(true);
        }
        categoryRepository.save(category);
        return new Result("O'zgartirildi", true);
    }

    @Override
    public List<Category> getAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList;
    }

    @Override
    public List<Category> get() {

        List<Category> categoryList = categoryRepository.getMainCategoryOrderedAndEnabledTrue();

        return categoryList;
    }

    @Override
    public Category getOne(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return new Category();
        return optionalCategory.get();
    }

    @Override
    public Result delete(Integer id) {
        try{
            categoryRepository.deleteById(id);
            return new Result("Deleted category", true);
        }catch (Exception e){
            return new Result("No deleted", false);
        }
    }
}
