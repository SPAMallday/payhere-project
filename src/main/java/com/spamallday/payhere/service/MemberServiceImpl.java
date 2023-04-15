package com.spamallday.payhere.service;

import com.spamallday.payhere.dto.MemberDto;
import com.spamallday.payhere.dto.OwnerDto;
import com.spamallday.payhere.entity.Member;
import com.spamallday.payhere.entity.Owner;
import com.spamallday.payhere.exception.CustomErrorCode;
import com.spamallday.payhere.exception.CustomException;
import com.spamallday.payhere.repository.MemberRepository;
import com.spamallday.payhere.repository.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.regex.Pattern;

@Slf4j
@Service
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    MemberServiceImpl(MemberRepository memberRepository, OwnerRepository ownerRepository) {
        this.memberRepository = memberRepository;
        this.ownerRepository = ownerRepository;
    }

    // 입력 휴대폰 번호 자릿수 및 숫자 여부 확인
    // 앞자리가 01X가 아닐 수 있나?
    @Override
    public void numberDigitValidation(MemberDto memberDto) {
        String number = memberDto.getNumber();

        if (!Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", number)) {
            throw new CustomException(CustomErrorCode.PHONE_NUM_ERROR);
        }
    }

    // DB에 중복되는 휴대폰 번호가 있는지 확인
    @Override
    public void numberDuplicatedValidation(MemberDto memberDto) {
        Member member = memberRepository.findByNumber(memberDto.getNumber());

        if (member != null) throw new CustomException(CustomErrorCode.PHONE_NUM_DUP_ERROR);
    }

    // 회원가입
    @Override
    public void saveMember(MemberDto memberDto) {
        // 암호화
        memberDto.encrypt();
        // 회원가입 유형이 있다면 구분하지만 현재는 사장님만 있어서 MemberDto에서 Entity로 변환
        ownerRepository.save(memberDto.toOwnerEntity());
    }
}
