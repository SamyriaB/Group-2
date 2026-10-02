package com.group2.geektext.repository;

import com.group2.geektext.model.BookComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCommentRepository extends JpaRepository<BookComment, Integer> {
}
