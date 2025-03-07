package JavaProject.Dayoung.infra.openai.config;

import JavaProject.Dayoung.domain.quiz.presentation.dto.request.SolveQuizRequest;
import JavaProject.Dayoung.infra.openai.domain.ChatGPTResponse;
import JavaProject.Dayoung.infra.openai.repository.ChatGPTRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TemplateConfig {

    @Value("${chatgpt.api-key}")
    private String openAiKey;

    @Autowired
    private RestTemplate template;

    public ChatGPTRequest promptMapping(String quiz, SolveQuizRequest request) {
        String question =
                "너는 나에게 존대말을 사용해야해" +
                        "너는 질문에 대한 답변을 듣고 나에게 정답률을 알려주고 질문의 답변에 대한 피드백을 해줘야해\s" +
                        "면접자의 답변이 옳고, 잘 설명하였다면 정답률을 80~100을 줘. 피드백에도 잘 했다고 적어줘야해 만약 면접자의 답변이 애매하면 40~79 점을 줘. 만약 면접자의 답변이 질문과 아예 관련이 없거나 전혀 틀렸다면 0~30점을 줘. 피드백도 해주고" +
                        "너의 답변의 형식은 반드시 이러해야해 정답률: 20%, 피드백: ... \s" +
                        "문제는 이러해 " + request.getQuiz() + "\s" +
                        "면접자의 답변은 이거야 " + request.getAnswer() + "\s" + "이제 나에게 '정답률: ??%, 피드백: ... ' 이러한 형식으로 답변을 줘"
            ;

        return new ChatGPTRequest("gpt-3.5-turbo", question);
    }

    public ResponseEntity<ChatGPTResponse> responseMapping(ChatGPTRequest gptRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiKey);

        HttpEntity<ChatGPTRequest> entity = new HttpEntity<>(gptRequest, headers);

        return template.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                entity,
                ChatGPTResponse.class
        );
    }

    public String correctRateMatcher(String responseContent) {

        Pattern correctRatePattern = Pattern.compile("정답률\\s*:?\\s*(\\d+)%");

        Matcher correctRateMatcher = correctRatePattern.matcher(responseContent);
        String correctRate = "";

        if (correctRateMatcher.find()) {
            correctRate = correctRateMatcher.group(1);
        }

        return correctRate;
    }

    public String feedbackMatcher(String responseContent) {

        Pattern feedbackPattern = Pattern.compile("피드백\\s*:\\s*(.*)");

        Matcher feedbackMatcher = feedbackPattern.matcher(responseContent);
        String feedback = "";

        if (feedbackMatcher.find()) {
            feedback = feedbackMatcher.group(1);
        }

        return feedback;
    }
}
