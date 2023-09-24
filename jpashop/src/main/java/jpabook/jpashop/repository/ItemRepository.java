package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;
    //constructor injection

    public void save(Item item){
        if(item.getId() == null)
            em.persist(item);
            //id가 없다는 것은 새로 생성한 객체라는 뜻이므로 persist(신규등록)
        else
            em.merge(item);
            //이미 DB에 있는 정보이므로 update처럼 merge기능 적용
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
