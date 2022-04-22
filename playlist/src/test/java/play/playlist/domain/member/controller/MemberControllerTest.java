package play.playlist.domain.member.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import play.playlist.domain.member.service.MemberService;

import javax.servlet.Filter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebAppConfiguration
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
//@Slf4j
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    private static final String uid = "213";
    private static final String email = "GODRIC@daum.com";
    private static final String name = "가드릭";

    @Autowired
    private MemberService memberService;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Filter springSecurityFilterChain;


    @Test
    void 로그인_테스트() throws Exception {
        memberService.register(email, name, uid);

        ResultActions resultActions = mockMvc.perform(
                get("/members/me")
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("uid").value(uid))
                .andExpect(jsonPath("email").value(email))
                .andExpect(jsonPath("name").value(name));

    }
}
