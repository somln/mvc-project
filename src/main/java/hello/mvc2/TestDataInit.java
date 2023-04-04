package hello.mvc2;

import hello.mvc2.domain.item.Item;
import hello.mvc2.domain.item.ItemRepository;
import hello.mvc2.domain.member.Member;
import hello.mvc2.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**
     * 테스트용 데이터 추가
     */

    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
        memberRepository.save(new Member("test", "테스터", "test!"));

    }
}
