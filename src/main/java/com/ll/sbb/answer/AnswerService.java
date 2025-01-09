package com.ll.sbb.answer;

import com.ll.sbb.DataNotFoundException;
import com.ll.sbb.question.Question;
import com.ll.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;


    public Answer create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        this.answerRepository.save(answer);
        return answer;
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        this.answerRepository.save(answer);
    }

    public Page<Answer> getList(Question q, int page, String property) {
        List<Sort.Order> sorts = new ArrayList<>();

        if(!property.equals("voters")) {
            sorts.add(Sort.Order.desc(property));
        }

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        //TODO: 추천순 오류 수정해야함
        if(property.equals("voter")) {
            return this.answerRepository.findByQuestionOrderByVoterCountDesc(q, pageable);
        }
        return this.answerRepository.findByQuestion(q, pageable);
    }

    public Page<Answer> getListByAuthor(int page, SiteUser user) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.answerRepository.findByAuthor(pageable, user);
    }
}
