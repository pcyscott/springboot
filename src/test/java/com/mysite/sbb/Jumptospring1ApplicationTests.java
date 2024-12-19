package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Jumptospring1ApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void testJpa() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);  // 두번째 질문 저장
    }

    @Test//Findall
    void testJpa1(){
        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test//Findbyid
    void testJpa2(){
        Optional<Question> oq = this.questionRepository.findById(1L);
        if(oq.isPresent()) {
            Question q = oq.get();
            assertEquals("sbb가 무엇인가요?", q.getSubject());
        }
    }

    @Test//Findbysubject
    void testJpa3(){
        Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(1L,q.getId());
    }

    @Test//Findbysubjectandcontent
    void testJpa4(){
        Question q = this.questionRepository.findBySubjectAndContent(
                "sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertEquals(1L,q.getId());
    }

    @Test//Findbysubjectandcontent
    void testJpa5(){
        List<Question> questionList = this.questionRepository.findBySubjectLike("sbb%");
        Question q = questionList.get(0);
        assertEquals( "sbb가 무엇인가요?",q.getSubject());
    }
}
