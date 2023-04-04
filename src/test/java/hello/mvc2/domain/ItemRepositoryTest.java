package hello.mvc2.domain;

import hello.mvc2.domain.item.Item;
import hello.mvc2.domain.item.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save() {
        Item savedItem = itemRepository.save(new Item("itemA", 10000, 10));
        Item findItem= itemRepository.findById(savedItem.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        Item itemA = itemRepository.save(new Item("itemA", 10000, 10));
        Item itemB = itemRepository.save(new Item("itemB", 20000, 20));
        List<Item> result = itemRepository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void update() {
        Item itemA = itemRepository.save(new Item("itemA", 10000, 10));
        Item updateParam = new Item("itemB", 20000, 20);

        itemRepository.update(itemA.getId(), updateParam);

        assertThat(itemA.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(itemA.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(itemA.getQuantity()).isEqualTo(updateParam.getQuantity());

    }
}