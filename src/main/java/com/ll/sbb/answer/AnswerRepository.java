package com.ll.sbb.answer;

import com.ll.sbb.question.Question;
import com.ll.sbb.user.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    Page<Answer> findByQuestion(Question q, Pageable pageable);

    @Query("SELECT a FROM Answer a WHERE a.question = :question ORDER BY (SELECT COUNT(v) FROM a.voter v) DESC")
    Page<Answer> findByQuestionOrderByVoterCountDesc(@Param("question") Question question, Pageable pageable);

    Page<Answer> findByAuthor(Pageable pageable, SiteUser author);
}



