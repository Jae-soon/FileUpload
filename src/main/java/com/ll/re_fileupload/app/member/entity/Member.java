package com.ll.re_fileupload.app.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.re_fileupload.app.common.config.BaseConfig;
import com.ll.re_fileupload.app.common.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.File;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Member extends BaseEntity {
    @Column(unique = true)
    private String username;
    @JsonIgnore // 순환참조를 위한 방법 -> 해당 property는 null로 생성
    private String password;
    private String email;
    private String profileImg;

    public Member(long id) {
        super(id);
    }

    public void removeProfileImgOnStorage() {
        if (profileImg == null || profileImg.trim().length() == 0) return;

        String profileImgPath = getProfileImgPath();

        new File(profileImgPath).delete();
    }

    private String getProfileImgPath() {
        return BaseConfig.GET_FILE_DIR_PATH + "/" + profileImg;
    }

    public String getProfileImgUrl() {
        if ( profileImg == null ) return null;

        return "/gen/" + profileImg;
    }
}