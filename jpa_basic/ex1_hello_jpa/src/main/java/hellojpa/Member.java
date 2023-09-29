package hellojpa;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
//JPA를 사용하는 객체임을 명시 -> JPA가 관리
public class Member {
    @Id //PK
    private long id;

    //@Column(name = "name", insertable = true, updatable = true)
    @Column(name = "name", columnDefinition = "varchar(100) default 'EMPTY'")
    private String username;
    //객체 필드 이름은 username이나 DB에는 name으로 저장

    //@Column()
    //private BigDecimal age1;
    //아주 큰 숫자나 소수점 쓸 때 사용

    private Integer age;
    //DB에서 Integer와 가장 유사한 타입으로 만들어짐

    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    //enum 맵핑 사용하는 annotation

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    //날짜 타입 매핑
    //Type 3가지 -> DATE, TIME, TIMESTAMP

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;
    //DB에 큰  컨텐츠를 넣음을 명시

    @Transient
    private int tmp;
    //DB와 맵핑하고 싶지 않을 때 사용 (메모리에서만 사용)


    public Member() {
    }

}
