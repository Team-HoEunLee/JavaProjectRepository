package JavaProject.Dayoung.domain.quiz.service;

import JavaProject.Dayoung.domain.quiz.presentation.dto.response.QuizListResponse;
import JavaProject.Dayoung.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryQuizService {

    private final QuizRepository quizRepository;

    public List<QuizListResponse> getQuizList() {//List<Area> area, List<Level> level, IsSolved isSolved
        /*
        return quizRepository.findQuizById(1L)
                .stream()
                .map(QuizListResponse::new)
                .collect(Collectors.toList());

         */
        return null;
    }
}
