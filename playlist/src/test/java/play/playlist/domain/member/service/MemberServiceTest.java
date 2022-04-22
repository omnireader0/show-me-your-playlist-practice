package play.playlist.domain.member.service;
import play.playlist.domain.member.entity.Member
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import play.playlist.dto.UserInfo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class MemberServiceTest {

    private static final String uid = "abcd";
    private static final String email = "godric@naver.com";
    private static final String name = "가드릭";;

    @Autowired
    private MemberService memberService;

    @Test
    void 유저_저장_테스트(){
        UserInfo dto = memberService.register(email, name, uid);

        List<Member> members = memberService.findAll();
        assertThat(members.size()).isEqualTo(1);
        for(int i=0; i<members.size(); i++)
        {
            assertThat(members.get(i).getUid()).isEqualTo(uid);
            assertThat(members.get(i).getEmail()).isEqualTo(email);
            assertThat(members.get(i).getNickname()).isEqualTo(name);
        }
        Member member = memberService.findByUid(uid);

        assertThat(member.getUid()).isEqualTo(uid);

        assertThat(dto.getUid()).isEqualTo(uid);
        assertThat(dto.getEmail()).isEqualTo(email);
        assertThat(dto.getNickname()).isEqualTo(name);

    }

}