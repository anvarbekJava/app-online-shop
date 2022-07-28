package uz.pdp.apponlinemagazin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.apponlinemagazin.domain.Attachment;
import uz.pdp.apponlinemagazin.domain.Category;
import uz.pdp.apponlinemagazin.domain.ProductProperties;
import uz.pdp.apponlinemagazin.domain.Products;
import uz.pdp.apponlinemagazin.payload.ProductDto;
import uz.pdp.apponlinemagazin.payload.ProductPropertiesDto;
import uz.pdp.apponlinemagazin.payload.Result;
import uz.pdp.apponlinemagazin.repository.AttachmentRepository;
import uz.pdp.apponlinemagazin.repository.CategoryRepository;
import uz.pdp.apponlinemagazin.repository.ProductPropertiesRepository;
import uz.pdp.apponlinemagazin.repository.ProductRepository;
import uz.pdp.apponlinemagazin.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductPropertiesRepository productPropertiesRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Result addProduct(ProductDto dto) {
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if(!optionalCategory.isPresent())
            return new Result("Topilmadi category", false);
        Category category = optionalCategory.get();

        Products products = new Products(
              dto.getName(),
              category,
              dto.getSize(),
              dto.getCashPrice(),
              dto.getTransferPrice()
        );
        if (dto.getAttachmentId()!=null) {
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(dto.getAttachmentId());
            if (!optionalAttachment.isPresent())
                return new Result("Attachment topilmadi", false);
            Attachment attachment = optionalAttachment.get();
            products.setAttachment(attachment);
        }
        List<ProductProperties> propertiesList = new ArrayList<>();
        for (ProductPropertiesDto propertiesDto : dto.getPropertiesList()) {
            ProductProperties properties = new ProductProperties(
                propertiesDto.getKey(),
                propertiesDto.getValue(),
                products
            );
            propertiesList.add(properties);
        }
       products.setProductProperties(propertiesList);
       productRepository.save(products);
        return new Result("Product saqlandi",true);
    }

    @Override
    public Result edet(Integer id, ProductDto dto) {
        Optional<Products> optionalProducts = productRepository.findById(id);
        if (!optionalProducts.isPresent())
            return new Result("Product topilmadi", false);
        Products products = optionalProducts.get();

        if (dto.getAttachmentId()!=null) {
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(dto.getAttachmentId());
            if (!optionalAttachment.isPresent())
                return new Result("Attachment topilmadi", false);
            Attachment attachment = optionalAttachment.get();
            products.setAttachment(attachment);
        }
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if(!optionalCategory.isPresent())
            return new Result("Topilmadi category", false);
        Category category = optionalCategory.get();
        products.setName(dto.getName());
        products.setSize(dto.getSize());
        products.setCashPrice(dto.getCashPrice());
        products.setCategory(category);
        products.setTransferPrice(dto.getTransferPrice());
        products.setEnabled(dto.isEnabled());

       List<ProductProperties> propertiesList = new ArrayList<>();
        for (ProductPropertiesDto propertiesDto : dto.getPropertiesList()) {
            Optional<ProductProperties> optionalProductProperties = productPropertiesRepository.findById(propertiesDto.getId());
            if (!optionalProductProperties.isPresent())
                return new Result("Product properties topilmadi", false);
            ProductProperties properties = optionalProductProperties.get();
            properties.setKey(propertiesDto.getKey());
            properties.setValue(propertiesDto.getValue());
            properties.setProduct(products);
            propertiesList.add(properties);
        }
        products.setProductProperties(propertiesList);
        productRepository.save(products);
        return new Result("Product saqlandi",true);
    }

    @Override
    public List<Products> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Products> getByCategoryId(Integer categoryId) {
        return productRepository.getProductByCategory(categoryId);
    }

    @Override
    public List<Products> getCategoryId(Integer categoryId) {
        return productRepository.findAllByCategory_ParentCategoryIdAndEnabledTrue(categoryId);
    }

    @Override
    public Products getOne(Integer id) {
        Optional<Products> optionalProducts = productRepository.findById(id);
        if(!optionalProducts.isPresent())
            return null;
        return optionalProducts.get();
    }

    @Override
    public Result delete(Integer id) {
        try {
            productRepository.deleteById(id);
            return new Result("Delete product", true);
        }catch (Exception e){
            return new Result("No deleted product", false);
        }

    }

    @Override
    public Result enabledProduct(Integer id) {
        Optional<Products> optionalProducts = productRepository.findById(id);
        if (!optionalProducts.isPresent())
            return new Result("Product nort found", false);
        Products products = optionalProducts.get();
        if (products.isEnabled()){
            products.setEnabled(false);
        }else {
            products.setEnabled(true);
        }
        productRepository.save(products);
        return new Result("Success enabled", true);
    }
}
