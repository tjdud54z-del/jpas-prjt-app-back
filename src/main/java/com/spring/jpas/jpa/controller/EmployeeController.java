
package com.spring.jpas.jpa.controller;

import com.spring.jpas.jpa.dto.EmployeeDto;
import com.spring.jpas.jpa.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jpa/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    
    /** 직원 등록 */
    @PostMapping
    public Long saveEmployee(
            @RequestBody EmployeeDto request) {
        return employeeService.saveEmployee(request);
    }

    /** 퇴사 */
    @PutMapping("/retire")
    public void retire(@RequestBody List<Long> employeeIds) {
        employeeService.retireEmployees(employeeIds);
    }

    /** 복직 */
    @PutMapping("/restore")
    public void restore(@RequestBody List<Long> employeeIds) {
        employeeService.restoreEmployees(employeeIds);
    }

}
