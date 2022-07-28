package uz.pdp.apponlinemagazin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.apponlinemagazin.domain.Customer;
import uz.pdp.apponlinemagazin.domain.CustomerProduct;
import uz.pdp.apponlinemagazin.domain.Products;
import uz.pdp.apponlinemagazin.domain.enums.CustomerStatus;
import uz.pdp.apponlinemagazin.payload.CustomerDto;
import uz.pdp.apponlinemagazin.payload.CustomerProductDto;
import uz.pdp.apponlinemagazin.payload.Result;
import uz.pdp.apponlinemagazin.repository.CustomerProductRepository;
import uz.pdp.apponlinemagazin.repository.CustomerRepository;
import uz.pdp.apponlinemagazin.repository.ProductRepository;
import uz.pdp.apponlinemagazin.service.CustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerProductRepository customerProductRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public Result add(CustomerDto dto) {
        Customer customer = new Customer(
                dto.getFullName(),
                dto.getPhoneNumber(),
                dto.getTelegramNumber(),
                CustomerStatus.NEW,
                dto.isPaymentType()
        );

        List<CustomerProduct> customerProductList = new ArrayList<>();
        for (CustomerProductDto productDto : dto.getCustomerProductDtoList()) {
            Optional<Products> optionalProducts = productRepository.findById(productDto.getProductId());
            if (!optionalProducts.isPresent())
                return new Result("Product not found", false);
            Products products = optionalProducts.get();
            CustomerProduct customerProduct = new CustomerProduct(
                    products,
                    productDto.getCount(),
                    dto.isPaymentType() ? (products.getTransferPrice() * productDto.getCount()) : (products.getCashPrice() * productDto.getCount()),
                    customer
            );
            customerProductList.add(customerProduct);
        }
        customer.setCustomerProducts(customerProductList);
        Customer saveCustomer = customerRepository.save(customer);
        return new Result("Muvaffaqiyatli saqlandi", true, saveCustomer);
    }

    @Override
    public Result edet(String id, CustomerDto dto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent())
            return new Result("Customer topilmadi", false);
        Customer customer = optionalCustomer.get();
        customer.setFullName(dto.getFullName());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setPaymentType(dto.isPaymentType());
        customer.setTelegramNumber(dto.getTelegramNumber());

        List<CustomerProduct> customerProductList = new ArrayList<>();
        for (CustomerProductDto productDto : dto.getCustomerProductDtoList()) {
            Optional<Products> optionalProducts = productRepository.findById(productDto.getProductId());
            if (!optionalProducts.isPresent())
                return new Result("Product not found", false);
            Products productOne = optionalProducts.get();
            Optional<CustomerProduct> customerProduct = customerProductRepository.findById(productDto.getId());
            if (!optionalCustomer.isPresent())
                return new Result("Customer product topilmadi", false);
            CustomerProduct product = customerProduct.get();
            product.setCount(productDto.getCount());
            product.setProducts(productOne);
            product.setAmount(dto.isPaymentType() ? (productOne.getTransferPrice() * productDto.getCount()) : (productOne.getCashPrice() * productDto.getCount()));

            customerProductList.add(product);
        }
        customer.setCustomerProducts(customerProductList);
        Customer saveCustomer = customerRepository.save(customer);
        return new Result("Buyurtma o'zgartildi", true);
    }

    @Override
    public Result accepted(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent())
            return new Result("Customer topilmadi", false);
        Customer customer = optionalCustomer.get();
        customer.setStatus(CustomerStatus.ACCEPTED);
        customerRepository.save(customer);
        return new Result("Accepted customer zakaz", true);
    }

    @Override
    public Result canceled(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent())
            return new Result("Customer topilmadi", false);
        Customer customer = optionalCustomer.get();
        customer.setStatus(CustomerStatus.CANCELED);
        customerRepository.save(customer);
        return new Result("Buyurtma bekor qilindi", true);
    }

    @Override
    public Result done(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent())
            return new Result("Customer topilmadi", false);
        Customer customer = optionalCustomer.get();
        customer.setStatus(CustomerStatus.DONE);
        customerRepository.save(customer);
        return new Result("Buyurtma yuborildi", true);
    }

    @Override
    public Result delete(String id) {
        try {
            customerRepository.deleteById(id);
            return new Result("Buyurtma bekor qilindi", true);
        } catch (Exception e) {
            return new Result("Buyurtma topilmadi", false);
        }
    }

    @Override
    public List<CustomerDto> getAll() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer customer : customerList) {
            CustomerDto customerClientDto = new CustomerDto();

            customerClientDto.setId(customer.getId());
            customerClientDto.setFullName(customer.getFullName());
            customerClientDto.setPhoneNumber(customer.getPhoneNumber());
            customerClientDto.setPaymentType(customer.isPaymentType());
            customerClientDto.setCreatedAt(customer.getCreatedAt());
            customerClientDto.setStatus(customer.getStatus().name());
            List<CustomerProductDto> customerProductList = new ArrayList<>();
            Double totalCount = 0.;
            Double totalAmount = 0.;
            for (CustomerProduct customerProducts : customer.getCustomerProducts()) {
                CustomerProductDto customerProduct = new CustomerProductDto();
                customerProduct.setId(customerProducts.getId());
                customerProduct.setProductId(customerProducts.getProducts().getId());
                customerProduct.setProductName(customerProducts.getProducts().getName());
                customerProduct.setCount(customerProducts.getCount());
                customerProduct.setAmount(customerProducts.getAmount());
                customerProduct.setTotalPrice(customerProducts.getCount() * customerProducts.getAmount());
                totalCount = totalCount + customerProducts.getCount();
                totalAmount = totalAmount + (customerProducts.getAmount()*customerProducts.getCount());
                customerProductList.add(customerProduct);
            }
            customerClientDto.setCustomerProductDtoList(customerProductList);
            customerClientDto.setTotalCount(totalCount);
            customerClientDto.setTotalAmount(totalAmount);

            customerDtoList.add(customerClientDto);
        }
        return customerDtoList;
    }

    @Override
    public CustomerDto getOne(String id) {
        Optional<Customer> productOptional = customerRepository.findById(id);
        if (!productOptional.isPresent())
            return new CustomerDto();
        Customer customer = productOptional.get();

        CustomerDto customerClientDto = new CustomerDto();

        customerClientDto.setId(customer.getId());
        customerClientDto.setFullName(customer.getFullName());
        customerClientDto.setPhoneNumber(customer.getPhoneNumber());
        customerClientDto.setPaymentType(customer.isPaymentType());
        customerClientDto.setCreatedAt(customer.getCreatedAt());
        customerClientDto.setStatus(customer.getStatus().name());
        List<CustomerProductDto> customerProductList = new ArrayList<>();
        Double totalCount = 0.;
        Double totalAmount = 0.;

        for (CustomerProduct customerProducts : customer.getCustomerProducts()) {
            CustomerProductDto customerProduct = new CustomerProductDto();
            customerProduct.setId(customerProducts.getId());
            customerProduct.setProductId(customerProducts.getProducts().getId());
            customerProduct.setProductName(customerProducts.getProducts().getName());
            customerProduct.setCount(customerProducts.getCount());
            customerProduct.setAmount(customerProducts.getAmount());
            customerProduct.setTotalPrice(customerProducts.getCount() * customerProducts.getAmount());
            totalCount = totalCount + customerProducts.getCount();
            totalAmount = totalAmount + (customerProducts.getAmount()*customerProducts.getCount());
            customerProductList.add(customerProduct);
        }
        customerClientDto.setCustomerProductDtoList(customerProductList);
        customerClientDto.setTotalCount(totalCount);
        customerClientDto.setTotalAmount(totalAmount);
        
        return customerClientDto;

}
    

    @Override
    public List<CustomerDto> getDashboard(String status, Long startDate, Long endDate) {
        List<Customer> customerList = customerRepository.getCustomerByStatusAndBeetwenOrderTime(status, startDate, endDate);

        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer customer : customerList) {
            CustomerDto customerClientDto = new CustomerDto();

            customerClientDto.setId(customer.getId());
            customerClientDto.setFullName(customer.getFullName());
            customerClientDto.setPhoneNumber(customer.getPhoneNumber());
            customerClientDto.setPaymentType(customer.isPaymentType());
            customerClientDto.setCreatedAt(customer.getCreatedAt());
            customerClientDto.setStatus(customer.getStatus().name());
            List<CustomerProductDto> customerProductList = new ArrayList<>();
            Double totalCount = 0.;
            Double totalAmount = 0.;
            for (CustomerProduct customerProducts : customer.getCustomerProducts()) {
                CustomerProductDto customerProduct = new CustomerProductDto();
                customerProduct.setId(customerProducts.getId());
                customerProduct.setProductId(customerProducts.getProducts().getId());
                customerProduct.setProductName(customerProducts.getProducts().getName());
                customerProduct.setCount(customerProducts.getCount());
                customerProduct.setAmount(customerProducts.getAmount());
                customerProduct.setTotalPrice(customerProducts.getCount() * customerProducts.getAmount());
                totalCount = totalCount + customerProducts.getCount();
                totalAmount = totalAmount + (customerProducts.getAmount()*customerProducts.getCount());
                customerProductList.add(customerProduct);
            }
            customerClientDto.setCustomerProductDtoList(customerProductList);
            customerClientDto.setTotalCount(totalCount);
            customerClientDto.setTotalAmount(totalAmount);

            customerDtoList.add(customerClientDto);
        }
        return customerDtoList;
    }
}
