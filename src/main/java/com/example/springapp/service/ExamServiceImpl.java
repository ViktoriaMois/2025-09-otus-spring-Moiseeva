package com.example.springapp.service;

import com.example.springapp.dao.ExamDao;
import com.example.springapp.domain.Exam;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

@RequiredArgsConstructor
@Service
public class ExamServiceImpl implements ExamService {
    private final ExamDao DAO;
    private final MessageSource MSG;

    @Getter @Setter private String lang = "";
    @Getter @Setter private String studentName = "";
    @Getter @Setter private int questionsWanted;
    @Getter @Setter private int points = 0;

    @Value("${spring.web.locale}")
    private Locale locale;
    @Value("${exam.minPoints}")
    private int minPoints;
    @Value("${exam.maxPoints}")
    private int maxPoints;
    @Value("${exam.pointsPerQuestion}")
    private int pointsPerQuestion;
    @Value("${exam.minQuestions}")
    private int minQuestions;
    @Value("${exam.maxQuestions}")
    private int maxQuestions;
    @Value("${csv.path}")
    private String csv;

    @Override
    public void print() {
        Scanner sc = new Scanner(System.in);
        prepare(sc);
        List<Exam> examList = DAO.read(new ClassPathResource(csv));
        for (int i = 0; i < questionsWanted; i++) {
            out("main.question.string[" + i + "]", null);
            out("main.answers.string", null);
            String studentAnswer = readString(sc).toLowerCase();
            result(checkAnswer(examList.get(i), studentAnswer));
        }
        score();
    }

    private void prepare(Scanner sc) {
        out("main.student-name.string", null);
        setStudentName(readString(sc));

        out("main.question-amount.string", new Object[]{minQuestions});
        setQuestionsWanted(parseInt(readString(sc)));

        if (questionsWanted < minQuestions) {
            out("main.warning-less.string", new Object[]{minQuestions});
            throw new RuntimeException();
        } else if (questionsWanted > maxQuestions) {
            out("main.warning-more.string", new Object[]{maxQuestions});
            throw new RuntimeException();
        }
    }

    private void score() {
        out("main.score.string", new Object[]{studentName, points, maxPoints});
        if (points > minPoints) {
            out("main.success.string", null);
        } else {
            out("main.failure.string", null);
        }
    }

    @Override
    public void out(String message, Object[] obj) {
        System.out.println(MSG.getMessage(message, obj, locale));
    }

    @Override
    public String readString(Scanner sc) {
        if (!sc.hasNextLine()){
            return null;
        }
        return sc.nextLine();
    }

    @Override
    public boolean checkAnswer(Exam exam, String answer) {
        return exam.getRightAnswer().contains(answer);
    }

    @Override
    public void result(boolean right) {
        if (right) {
            this.points += pointsPerQuestion;
        }
    }
}
