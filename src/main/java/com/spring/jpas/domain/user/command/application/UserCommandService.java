
package com.spring.jpas.domain.user.command.application;

import com.spring.jpas.domain.user.command.domain.User;
import com.spring.jpas.domain.user.command.infra.UserRepository;
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
public class UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /** 직번 MAX 발번 */
    private String generateNextUsersNo(String currentMax) {
        final String PREFIX = "user";
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
    public Long saveUser(CommonParams params) {

        // 직번 발번
        String currentMax = userRepository.findMaxUserNo();
        String nextEmployeeNo = generateNextUsersNo(currentMax);

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(params.getPassword());

        // 엔티티 생성
        User user = User.create(
                nextEmployeeNo,
                encodedPassword,
                params.getName(),
                params.getEmail(),
                params.getAddressMain(),
                params.getAddressSub(),
                LocalDate.parse(params.getBirthDate())
        );

        // 저장 로직시작
        return userRepository.save(user).getUserId();
    }

    /** 수정 */
    public void updateUser(CommonParams params) {
        User user = userRepository.findById(params.getUserId())
            .orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 userId=" + params.getUserId())
            );
        user.update(
            params.getPassword(),
            params.getName(),
            params.getEmail(),
            params.getAddressMain(),
            params.getAddressSub(),
            LocalDate.parse(params.getBirthDate())
        );
    }

    /** 삭제 (다건) */
    public void deleteUsers(CommonParams params) {
        userRepository.deleteAllByIdInBatch(params.getUserIds());
    }


    /** 일괄 탈퇴 처리 */
    @Transactional
    public void retireUsers(List<Long> userIds) {

        List<User> users =
                userRepository.findAllById(userIds);

        if (users.isEmpty()) {
            return;
        }

        for (User user : users) {
            if ("Y".equals(user.getActiveYn())) {
                user.retire();
            }
        }
    }

    /** 일괄 복구 처리 */
    @Transactional
    public void restoreUsers(List<Long> userIds) {

        List<User> users =
                userRepository.findAllById(userIds);

        if (users.isEmpty()) {
            return;
        }

        for (User user : users) {
            if ("N".equals(user.getActiveYn())) {
                user.restore();
            }
        }
    }
}
