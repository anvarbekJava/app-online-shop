package uz.pdp.apponlinemagazin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apponlinemagazin.payload.CustomerDto;
import uz.pdp.apponlinemagazin.payload.Result;
import uz.pdp.apponlinemagazin.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody CustomerDto dto){
        Result result = customerService.add(dto);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edet(@PathVariable String id, @RequestBody CustomerDto dto){
        Result result = customerService.edet(id,dto);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);
    }
    @PutMapping("/accepted/{id}")
    public HttpEntity<?> accepted(@PathVariable String id){
        Result result = customerService.accepted(id);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);
    }

    @PutMapping("/canceled/{id}")
    public HttpEntity<?> canceled(@PathVariable String id){
        Result result = customerService.canceled(id);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);
    }

    @PutMapping("/done/{id}")
    public HttpEntity<?> done(@PathVariable String id){
        Result result = customerService.done(id);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable String id){
        Result result  = customerService.delete(id);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);
    }

    @GetMapping
    public HttpEntity<?> getAll(){
        List<CustomerDto> customerList = customerService.getAll();
        return ResponseEntity.ok(customerList);
    }
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable String id){
        CustomerDto customerDto = customerService.getOne(id);
        return ResponseEntity.ok(customerDto);
    }

    @GetMapping("/dashboard/{status}")
    public HttpEntity<?> getDashboard(@PathVariable String status, @RequestParam(name = "startDate") Long startDate, @RequestParam(name = "endDate") Long endDate){
        List<CustomerDto> customerClientList = customerService.getDashboard(status, startDate, endDate);
        return ResponseEntity.ok(customerClientList);
    }

}
