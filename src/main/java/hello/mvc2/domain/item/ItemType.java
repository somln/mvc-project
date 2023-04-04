package hello.mvc2.domain.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ItemType {

    BOOK("도서"), FOOD("식품"), ETC("기타");
    private final String description;

}
