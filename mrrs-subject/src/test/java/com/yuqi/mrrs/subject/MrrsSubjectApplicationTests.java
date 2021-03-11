package com.yuqi.mrrs.subject;

import com.yuqi.mrrs.subject.dto.ReviewDto;
import com.yuqi.mrrs.subject.service.ReviewService;
import com.yuqi.mrrs.subject.service.SubjectService;
import com.yuqi.mrrs.subject.service.UserReviewHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
class MrrsSubjectApplicationTests {

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserReviewHistoryService userReviewHistoryService;

    String[] subject={"1362416886705917953","1362417733414260738","1367773423448219649","1367798377191432194","1367804401851949057","1367809755419631617","1367817498587160578","1367823252878647298","1369191573859430401","1369192263730163714","1369193062166261761"};
    String[] user={"1369147365043843073","1369147364070764545","1369147363219320833","1369147362367877121","1369147361445130242","1369147360656601090","1369147359725465601","1369147358932742145","1369147357632507905","1369147352762920961"};

    @Test
    void contextLoads() {
        Random random = new Random();
        for(int i=0;i<user.length;i++){
            for(int j=0;j<4;j++) {
                ReviewDto dto = new ReviewDto();
                dto.setUserId(user[i]);
                dto.setSubject(Long.parseLong(subject[random.nextInt(subject.length)]));
                dto.setUserName("Robot" + i);
                dto.setContent("emmmm 还行吧" + random.nextInt());
                dto.setRating(String.valueOf(random.nextInt(5) + 1));
                reviewService.addReview(dto);
            }
        }
    }

    @Test
    void TestLike(){
        userReviewHistoryService.getRecommendSubject(1369147352762920961l);
    }

}
