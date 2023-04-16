package com.spamallday.payhere.service;

import com.spamallday.payhere.entity.Member;
import com.spamallday.payhere.exception.CustomErrorCode;
import com.spamallday.payhere.exception.CustomException;
import com.spamallday.payhere.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String num) throws UsernameNotFoundException, CustomException {
        Member member = memberRepository.findByNumber(num);
        // 해당 아이디의 유저가 없을 때
        if (member == null) {
            throw new CustomException(CustomErrorCode.NO_USER_ERROR);
        }
        return createUserDetails(member);
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Member member) {
        return new User(member.getNumber(), member.getPassword(), member.getAuthorities());
    }
}