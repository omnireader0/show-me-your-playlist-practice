package play.playlist.domain.member.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import play.playlist.domain.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import play.playlist.domain.member.dao.MemberRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService implements UserDetailsService {
    @Autowired
    private final MemberRepository memberRepository;

    @Override
    public Member loadUserByUsername(String uid) throws UsernameNotFoundException {
        return memberRepository.findByUid(uid)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("해당 회원이 존재하지 않습니다.");
                });
    }

    @Transactional
    public Member register(String uid, String email, String nickname) {
        Member member = Member.builder()
                .uid(uid)
                .email(email)
                .nickname(nickname)
                .build();
        memberRepository.save(member);
        return member;
    }

}
