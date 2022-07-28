package uz.pdp.apponlinemagazin.service;

import uz.pdp.apponlinemagazin.payload.CustomerDto;
import uz.pdp.apponlinemagazin.payload.Result;

import java.util.List;

public interface CustomerService {
    Result  add(CustomerDto dto);

    Result accepted(String id);

    Result canceled(String id);

    Result done(String id);

    Result delete(String id);

    Result edet(String id, CustomerDto dto);

    List<CustomerDto> getAll();

    CustomerDto getOne(String id);

    List<CustomerDto> getDashboard(String status, Long startDate, Long endDate);
}
