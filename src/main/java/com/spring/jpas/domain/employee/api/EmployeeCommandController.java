
package com.spring.jpas.domain.employee.api;

import com.spring.jpas.domain.employee.command.dto.EmployeeDto;
import com.spring.jpas.domain.employee.command.application.EmployeeCommandService;
import com.spring.jpas.global.common.CommonParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jpa/employees")
@RequiredArgsConstructor
public class EmployeeCommandController {

    private final EmployeeCommandService employeeCommandService;

    /** 직원 등록 */
    @PostMapping
    public Long saveEmployee(@RequestBody CommonParams params) {
        return employeeCommandService.saveEmployee(params);
    }

    /** 직원 수정 */
    @PutMapping
    public void updateEmployee(@RequestBody CommonParams params) {
        employeeCommandService.updateEmployee(params);
    }

    /** 직원 삭제 (다건) */
    @DeleteMapping
    public void deleteEmployees(@RequestBody CommonParams params) {
        employeeCommandService.deleteEmployees(params);
    }

    /** 퇴사 */
    @PutMapping("/retire")
    public void retire(@RequestBody CommonParams params) {  employeeCommandService.retireEmployees(params.getUserIds()); }

    /** 복직 */
    @PutMapping("/restore")
    public void restore(@RequestBody CommonParams params) { employeeCommandService.restoreEmployees(params.getUserIds()); }

}
