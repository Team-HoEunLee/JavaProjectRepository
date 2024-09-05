package JavaProject.Dayoung.domain.user.service;

import JavaProject.Dayoung.domain.user.entity.User;
import JavaProject.Dayoung.domain.user.facade.UserFacade;
import JavaProject.Dayoung.domain.user.presentation.dto.response.RankListResponse;
import JavaProject.Dayoung.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetScoreRankService {

    private final UserRepository userRepository;
    private final UserFacade userFacade;

    public RankListResponse execute() {
        List<User> user =  userRepository.findTop10ByOrderByScoreDesc();

        User currentUser = userFacade.getCurrentUser();

        List<RankListResponse.RankResponse> rankListResponses = userRepository.findTop10ByOrderByScoreDesc()
            .stream()
            .map(RankListResponse.RankResponse::of)
            .collect(Collectors.toList());

        return new RankListResponse(rankListResponses, currentUser.getName(), currentUser.getScore());
    }
}
