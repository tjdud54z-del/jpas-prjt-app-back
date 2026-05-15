
package com.spring.jpas.domain.user.command.application;

import com.spring.jpas.domain.user.command.domain.User;
import com.spring.jpas.domain.user.command.infra.UserRepository;
import com.spring.jpas.global.common.CommonParams;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
                params.getUserNo(),
                encodedPassword,
                params.getName(),
                params.getEmail(),
                params.getAddressMain(),
                params.getAddressSub(),
                LocalDate.parse(params.getBirthDate()),
                params.getGenderFlag(),
                params.getPhoneNumber()
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

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(params.getPassword());

        user.update(
            encodedPassword,
            params.getName(),
            params.getEmail(),
            params.getAddressMain(),
            params.getAddressSub(),
            LocalDate.parse(params.getBirthDate()),
            params.getGenderFlag(),
            params.getPhoneNumber()
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

    /** 프로필 이미지 추가 */
    public String saveProfileImage(MultipartFile file, Long userId) {

        try {
            if (file.isEmpty()) {
                throw new RuntimeException("파일이 없습니다.");
            }

            // 이미지 타입 체크
            if (!file.getContentType().startsWith("image")) {
                throw new RuntimeException("이미지 파일만 업로드 가능합니다.");
            }

            String uploadDir = System.getProperty("user.dir") + "/uploads/";

            // 파일명 생성
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path path = Paths.get(uploadDir + fileName);

            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            // DB 저장
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("유저 없음"));

            user.setProfileImagePath("/uploads/" + fileName);

            // 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            userRepository.save(user);

            return user.getProfileImagePath();

        } catch (Exception e) {
            throw new RuntimeException("이미지 업로드 실패", e);
        }
    }

}
