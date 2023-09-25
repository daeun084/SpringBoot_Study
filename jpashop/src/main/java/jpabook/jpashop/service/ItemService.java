package jpabook.jpashop.service;

import jpabook.jpashop.domain.Item.Book;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, Book param){ //param : 준영속상태
        Item findItem = itemRepository.findOne(itemId);
        //id를 기반으로 실제 DB에 있는 영속성 Entity를 찾아옴
        findItem.setPrice(param.getPrice());
        findItem.setId(param.getId());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());
        findItem.setCategories(param.getCategories());

        //해당 코드가 종료되면 @Transactional에 의해 Transactional이 커밋됨
        //-> JPA는 flush를 날림 : 영속성 Entity 중 변경된 부분을 찾음
        //바뀐 부분에 대해 update쿼리를 DB에 날림 -> update
        // ::변경감지:://

    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }


}
