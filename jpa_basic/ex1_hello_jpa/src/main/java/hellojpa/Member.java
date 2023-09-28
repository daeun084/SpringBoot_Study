package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
//JPA를 사용하는 객체임을 명시 -> JPA가 관리
public class Member {
    @Id //PK
    private long id;
    private String name;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


}
