
package com.spring.jpas.domain.user.api;

import com.spring.jpas.domain.user.command.application.UserCommandService;
import com.spring.jpas.global.common.CommonParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jpa/users")
@RequiredArgsConstructor
public class UserCommandController {

    private final UserCommandService userCommandService;

    /** 직원 등록 */
    @PostMapping
    public Long saveUser(@RequestBody CommonParams params) {
        return userCommandService.saveUser(params);
    }

    /** 직원 수정 */
    @PutMapping
    public void updateUser(@RequestBody CommonParams params) {
        userCommandService.updateUser(params);
    }

    /** 직원 삭제 (다건) */
    @DeleteMapping
    public void deleteUsers(@RequestBody CommonParams params) {
        userCommandService.deleteUsers(params);
    }

    /** 탈퇴 */
    @PutMapping("/retire")
    public void retire(@RequestBody CommonParams params) {  userCommandService.retireUsers(params.getUserIds()); }

    /** 복구 */
    @PutMapping("/restore")
    public void restore(@RequestBody CommonParams params) { userCommandService.restoreUsers(params.getUserIds()); }

}
