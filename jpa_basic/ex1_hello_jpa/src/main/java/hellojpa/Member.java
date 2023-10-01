package hellojpa;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


//JPA를 사용하는 객체임을 명시 -> JPA가 관리
/** Sequence전략**/
/*@SequenceGenerator(name = "Member_SEQ_GENERATOR",
                    sequenceName = "MEMBER_SEQ", //매핑할 DB 시퀀스 이름
                    initialValue = 1, allocationSize = 1)
    //allocationSize 기본값 = 50
 */

/**Table 전략 **/
/*@TableGenerator(name = "MEMBER_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
 */

@Entity
public class Member {


    /**IDENTITY 전략**/
    //기본 키 생성을 DB에 위임
    //em.persist()시점에 INSERT SQL 실행 후 DB에서 식별자 조회
    /**
     * TABLE 전략
     **/
    //키 생성 전용 테이블 하나 만들어서 DB 시퀀스 흉내내는 전략
    //모든 DB에 적용 가능하나 성능이 떨어짐


    @Id //PK
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private long id;

    //@Column(name = "name", insertable = true, updatable = true)
    @Column(name = "USERNAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;
    //Member가 Many, Team이 One (관계 작성)
    //team의 PK값을 가지고 column을 join (매핑할 테이블 작성)

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    // @Column(name = "TEAM_ID")
   // private Long teamId;


    public Member() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}



//== Annotations ==//
    /**

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

    **/


