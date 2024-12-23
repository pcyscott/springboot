package com.mysite.sbb;

import com.mysite.sbb.Answer.Answer;
import com.mysite.sbb.Answer.AnswerRepository;
import com.mysite.sbb.Question.Question;
import com.mysite.sbb.Question.QuestionRepository;
import com.mysite.sbb.Question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class Jumptospring1ApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;

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

        Question q3 = new Question();
        q3.setSubject("세번째 질문");
        q3.setContent("세번째 답변");
        q3.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q3);  // 두번째 질문 저장
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

    @Test//findBySubjectLike
    void testJpa5(){
        List<Question> questionList = this.questionRepository.findBySubjectLike("sbb%");
        Question q = questionList.get(0);
        assertEquals( "sbb가 무엇인가요?",q.getSubject());
    }

    @Test//Modifyquestion
    void testJpa6(){
        Optional<Question> oq = this.questionRepository.findById(1L);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        q.setSubject("수정된 제목");
        this.questionRepository.save(q);
    }

    @Test//deletequestion
    void testJpa7(){
        assertEquals(2L,this.questionRepository.count());
        Optional<Question> oq = this.questionRepository.findById(1L);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        this.questionRepository.delete(q);
        assertEquals(1L,this.questionRepository.count());
    }

    @Test//addAnswer
    void testJpa8(){
        Optional<Question> oq = this.questionRepository.findById(1L);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);
        a.setCreateTime(LocalDateTime.now());
        this.answerRepository.save(a);
    }

    @Test//findAnswer
    void testJpa9(){
        Optional<Answer> oa = this.answerRepository.findById(1L);
        assertTrue(oa.isPresent());
        Answer a = oa.get();
        assertEquals(2L,a.getQuestion().getId());
    }

    @Transactional
    @Test//findQuestionbyAnswer
    void testJpa10(){
        Optional<Question> oq = this.questionRepository.findById(2L);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        assertEquals(1L,answerList.size());
        assertEquals("네 자동으로 생성됩니다.",answerList.get(0).getContent());
    }

    @Test//making many
    void testJpa11(){
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용무";
            this.questionService.create(subject, content);
        }
    }
}
