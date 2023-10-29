package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//@Controller @ResponseBody
@RestController
    //위의 두 annotation을 합침
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;


    /**Versioin_1_회원조회**/
    @GetMapping("/api/v1/members")
    public List<Member> memberV1(){
        return memberService.findAll();
    }
    //엔티티의 정보들이 모두 외부에 노출됨
    //@JsonIgnore annotation을 통해 해당 필드의 외부 노출 막을 수 있음
    //->api를 사용하는 엔티티마다 annotation을 다르게 사용해야 하는 번거로움 발생
    //Entity에 프레젠테이션 계층을 위한 로직이 추가되고 양방향 의존관계가 생김
    //Entity 변경에 따라 API 스펙이 변경됨


    /**Version_2_회원조회**/
    @GetMapping("/api/v2/members")
    public Result memberV2(){
        List<Member> findmembers = memberService.findAll();
        List<MemberDto> collect = findmembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        //memberService에서 멤버들을 찾아 DTO로 변환해 return
        //member Entity에서 getName을 통해 이름을 꺼내와 MemberDto에 넣음
        //collect를 사용해 List type으로 변경
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        //v2의 결과 반환을 위한 껍데기 클래스
        private T data;
        //List를 바로 리턴하면 Json 배열 타입으로 반환되기 때문에 외부에 임의의 껍데기로 감싸줘야 유연성이 높아짐
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
        //이름을 받아 넘기는 Dto
    }



    /**Version_1_회원등록**/
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        //valid된 member type 파라미터를 받음
        //requestBody -> Json으로 온 데이터를 member로 바꿈

        Long id = memberService.join(member);
        //파라미터로 들어온 member을 memberService에 Join 후 아이디 저장
        return new CreateMemberResponse(id);
    }
    //Entity를 외부 노출시키는 방법 -> 큰 문제 야기



    /**Version_2_회원등록**/
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberv2(@RequestBody @Valid CreateMemberRequest request){
        //파라미터에서 Entity를 직접 받지 않고 별도의 DTO(data transfer object)사용

        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
        //v1 코드보다 복잡해지고 추가적인 클래스를 만들어야 하나
        //Entity의 변경에 따라 api 스펙의 변경이 일어나지 않음
        //api 스펙에 대해서 바로 인지 가능
    }


    /**회원 수정**/
    @PostMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request){
        //update용 DTO 생성 (UpdateMemberRequest)
        //response와 request를 분리 -> 수정과 등록의 api스펙이 다르기 때문

        memberService.update(id, request.getName());
        //pathVariable을 통해 넘어온 id와 request의 name을 인자로 전달해 update
        //변경감지 사용해 수정사항 저장

        //유지보수를 위해 따로 커맨드와 쿼리를 분리함
        Member findMember = memberService.findOne(id);
        return  new UpdateMemberResponse(findMember.getId(), findMember.getName());
        //UpdateMemberResponse class에서 AllArgs lombok annotation을 사용했기 때문에 모든 인자를 넘겨야 함
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        //v2_회원수정
        private Long id;
        private String name;
    }

    @Data
    static class UpdateMemberRequest{
        //v2_회원수정
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        //v2_회원등록 의 파라미터를 위한 클래스

        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;
        //등록된 id값 반환
        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
