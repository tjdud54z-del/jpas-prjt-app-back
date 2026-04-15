
package com.spring.jpas.jpa.service;

import com.spring.jpas.jpa.dto.EmployeeDto;
import com.spring.jpas.jpa.entity.Employee;
import com.spring.jpas.jpa.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    /** 직원 등록 */
    @Transactional
    public Long saveEmployee(EmployeeDto req) {

        Employee employee = new Employee(
                req.getEmployeeNo(),
                req.getPassword(),
                req.getName(),
                req.getEmail(),
                req.getDepartmentCode(),
                req.getPositionCode(),
                req.getHireDate()
        );

        employeeRepository.save(employee);
        return employee.getEmployeeId();
    }


    /** 일괄 퇴사 처리 */
    @Transactional
    public void retireEmployees(List<Long> employeeIds) {

        List<Employee> employees =
                employeeRepository.findAllById(employeeIds);

        if (employees.isEmpty()) {
            return;
        }

        for (Employee employee : employees) {
            if ("Y".equals(employee.getActiveYn())) {
                employee.retire();
            }
        }
    }

    /** 일괄 복직 처리 */
    @Transactional
    public void restoreEmployees(List<Long> employeeIds) {

        List<Employee> employees =
                employeeRepository.findAllById(employeeIds);

        if (employees.isEmpty()) {
            return;
        }

        for (Employee employee : employees) {
            if ("N".equals(employee.getActiveYn())) {
                employee.restore();
            }
        }
    }
}
