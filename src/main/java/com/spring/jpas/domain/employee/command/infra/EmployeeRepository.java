package com.spring.jpas.domain.employee.command.infra;

import com.spring.jpas.domain.employee.command.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUserNo(String employeeNo);

//    @Query(
//        value = """
//            SELECT MAX(EMP.EMPLOYEE_NO)
//            from   EMPLOYEE EMP
//            where  EMP.EMPLOYEE_NO LIKE 'JPAS_%'
//        """,
//       nativeQuery = true
//    )
//    String findMaxEmployeeNo();

    @Query("""
        select max(e.userNo)
        from   Employee e
        where  e.userNo like 'JPAS_%'
    """)
    String findMaxEmployeeNo();

}