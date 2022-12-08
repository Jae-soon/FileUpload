package com.ll.re_fileupload.app.article.repository;

import com.ll.re_fileupload.app.fileUpload.entity.GenFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenFileRepository extends JpaRepository<GenFile, Long> {
}
