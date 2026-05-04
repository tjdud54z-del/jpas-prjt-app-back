
package com.spring.jpas.domain.employee.command.application;

import com.spring.jpas.domain.employee.command.domain.Employee;
import com.spring.jpas.domain.employee.command.dto.EmployeeDto;
import com.spring.jpas.domain.employee.command.infra.EmployeeRepository;
import com.spring.jpas.global.common.CommonParams;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class EmployeeCommandService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    /** 직번 MAX 발번 */
    private String generateNextEmployeeNo(String currentMax) {
        final String PREFIX = "JPAS_";
        final int PAD_LENGTH = 4;

        if (currentMax == null) {
            return PREFIX + String.format("%0" + PAD_LENGTH + "d", 1);
        }

        // JPAS_0001 → 0001 → 1
        String numberPart = currentMax.replace(PREFIX, "");
        int next = Integer.parseInt(numberPart) + 1;

        return PREFIX + String.format("%0" + PAD_LENGTH + "d", next);
    }

    /** 저장 */
    public Long saveEmployee(CommonParams params) {

        // 직번 발번
        String currentMax = employeeRepository.findMaxEmployeeNo();
        String nextEmployeeNo = generateNextEmployeeNo(currentMax);

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(params.getPassword());

        // 엔티티 생성
        Employee employee = Employee.create(
                nextEmployeeNo,
                encodedPassword,
                params.getName(),
                params.getEmail(),
                params.getDepartmentCode(),
                params.getPositionCode(),
                LocalDate.parse(params.getHireDate())
        );

        // 저장 로직시작
        return employeeRepository.save(employee).getUserId();
    }

    /** 수정 */
    public void updateEmployee(CommonParams params) {
        Employee employee = employeeRepository.findById(params.getUserId())
            .orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 employeeId=" + params.getUserId())
            );
        employee.update(
            params.getPassword(),
            params.getName(),
            params.getEmail(),
            params.getDepartmentCode(),
            params.getPositionCode(),
            LocalDate.parse(params.getHireDate())
        );
    }

    /** 삭제 (다건) */
    public void deleteEmployees(CommonParams params) {
        employeeRepository.deleteAllByIdInBatch(params.getUserIds());
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
