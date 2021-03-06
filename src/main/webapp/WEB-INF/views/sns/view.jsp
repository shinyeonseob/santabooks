<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/WEB-INF/views/layout/header.jsp" />
<link href="/resources/rating/css/star-rating.css" rel="stylesheet">
<script type="text/javascript" src="/resources/rating/js/star-rating.js"></script>
<link href="/resources/css/common.css" rel="stylesheet">

<style type="text/css">
body{
	background-color:#F7F7F4;
}

#subBtn {
	background-color: rgba(20, 121, 87, .25);
	border: none;
	width: 200px;
	color: black;
}

#reviewCard{
	width: 300px;
    height: 300px;
    left:50px;
    
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
	padding: 16px;
	background-color: #f1f1f1;
}

#reviewCard:hover {
    border-color: #147957;
    outline: 0;
    box-shadow: 0 0 0 0.2rem rgba(20, 121, 87,.0);
}

#writeBtn{
	background-color:rgba(20, 121, 87,.25); 
	border:none; 
	color: black;
	width: 200px;
}

#reviewWrite{
	background-color:rgba(20, 121, 87,.25); 
	border:none; 
	color: black;
}

#cancel{
	background-color: #f1f1f1;
	border:none; 
	color: black;
}

.starR{
  background: url('http://miuu227.godohosting.com/images/icon/ico_review.png') no-repeat right 0;
  background-size: auto 100%;
  width: 30px;
  height: 30px;
  display: inline-block;
  text-indent: -9999px;
  cursor: pointer;
}
.starR.on{background-position:0 0;}

</style>

<script type="text/javascript">

	
	
	$(document).ready(function() {

		//리뷰 작성
		$("#reviewWrite").click(function() {

			var memberNo = "";
			memberNo = $(this).attr("data-memberNo");

			$("#writeForm").submit();
		});
		
		var bookNo = $("#bookNo").val();
		drawStars($("#grade").val());
		
		if($("#grade").val() != ""){
			$("#removeScore").addClass("active");
		}

		// 별점주는 기능
		$(".rating-stars").on("click", function() {
			$.ajax({
				type : "POST",
				url : "/sns/grade/add",
				data : {
					"bookNo" : bookNo,
					"grade" : $("#rating-system").val()
				},
				dataType : "json",
				success : function(res) {
					drawStars(res.grade.grade);
					$("#removeScore").addClass("active");
				},
				error : function(e) {
					$("#loginModal").modal();
					console.log(e);
				}
			});
		});
		// 별점 취소하는 기능
		$("#removeScore").on("click", function() {
			$.ajax({
				type : "POST",
				url : "/sns/grade/remove",
				data : {
					"bookNo" : bookNo,
				},
				dataType : "json",
				success : function(res) {
					if (res.grade != null) {
						drawStars(res.grade.grade);
					} else {
						drawStars(0);
					}
					$("#removeScore").removeClass("active");
				},
				error : function(e) {
					$("#loginModal").modal();
					console.log(e);
				}
			});
		});

	});
	
	// 별점 그리기
	function drawStars(grade) {
		$("#starSpan").html("");
		for (var i = 1; i <= 5; i++) {
		
			if (i <= grade) {
				$("#starSpan").html($("#starSpan").html() + "<img src='/resources/images/novel/star-fill.svg' class='icon'>");
			} else {
				if (grade - (i - 1) < 1) {
					switch (parseInt(grade * 10) - ((i- 1) * 10)) {
					case 4:
					case 5:
					case 6:
					case 7:
						$("#starSpan").html($("#starSpan").html() + "<img src='/resources/images/novel/star-half.svg' class='icon'>");
						continue;
						break;
					case 8:
					case 9:
						$("#starSpan").html($("#starSpan").html() + "<img src='/resources/images/novel/star-fill.svg' class='icon'>");
						continue;
						break;
					default:
						break;
					}
				}
				$("#starSpan").html($("#starSpan").html() + "<img src='/resources/images/novel/star.svg' class='icon'>");
			}
		}
	}
	
	// 공개/비공개 체크 값
	function check() {

		if ($("input:checkbox[id='privacy']").is(":checked")) {

			$("#privacy").attr("value", 1);

		} else {
			$("#privacy").attr("value", 0);

		}
	}

</script>

<input type="hidden" value="${review.feedNo }" name="feedNo" />
<div class="container">
	<img style="height: 200px; width: 200px;" src="/resources/images/${review.bookName}.jpg">
	
	<br>
	<h1 style="font-weight: bold;">${review.bookName }</h1>
	<h5 style="color: #696969; font-weight: bold;">${review.bookWriter }</h5>
	<hr>
	
	<div style="position: relative;">
	<p style="font-weight: bold;">평점</p>
	<span id="starSpan" style="position: absolute; top: -2px; left: 40px;"></span>
	</div>
	<hr>
	
	<div style="position: relative;">
		<c:choose>
			<c:when test="${0 eq subNo }">
				<button class="btn btn-secondary" id="subBtn"
					onclick="location.href='/subscribe/agree'">구독 하기</button>
			</c:when>
			<c:when test="${null eq subNo }">
				<button class="btn btn-secondary" id="subBtn"
					onclick="location.href='/subscribe/agree'">구독 하기</button>
			</c:when>
			<c:otherwise>
				<button class="btn btn-secondary" id="subBtn"
					onclick="location.href='/mypage/subInfo'">구독 하기</button>
			</c:otherwise>
		</c:choose>
	</div>
	<hr>
	
	<c:if test="${not empty login }">
	<div class="card" style="height: 70px; margin-bottom: 10px;">
		<div class="card-text" style="margin: 16px;">
			${MemberNick }님의 생각을 글로 적어보세요. &nbsp;&nbsp;&nbsp;&nbsp;
			<button class="btn btn-secondary" id="writeBtn" data-toggle="modal"
				data-target="#writeModal">리뷰 작성</button>
		</div>
	</div>
	</c:if>
	
	<div class="column">
		<div class="card" id="bookInfo" style="height: 990px;">
			<div class="card-text">
				<div style="padding: 0px 20px 0px 20px;">
					<h4 style="font-weight: bold;">책 정보</h4>
					<ul>
						<li>책이름 : ${review.bookName }</li>
						<li>작가 : ${review.bookWriter }</li>
						<li>출판사 : ${review.bookPublisher }</li>
						<li>출판년도 : ${review.publishingYear }</li>
					</ul>
				</div>
				<hr>
				
				<div style="padding: 0px 20px 0px 20px;">
				<h4 style="font-weight: bold;">책 내용</h4>
				${review.bookContent }
				</div>
				<hr>
				
				<div style=" position: relative; padding: 0px 20px 0px 20px;">
					<h4 style="font-weight: bold; display: contents;">리뷰</h4>

					<a href="/sns/reviewall?bookNo=${review.bookNo }"
						style="color: #ff2f6e; text-decoration: none; font-weight: bold; position: absolute; right: 20px;">더보기</a>

				</div>
				
				<br>
				<div class="row">
				<c:forEach items="${list }" var="list">
				<c:if test="${list.privacy eq 1}">
						<div class="column"onclick="location.href='/sns/detailview?feedNo=${list.feedNo}'" 
						style="width: 300px; height: 300px; padding: 4px;margin-right: 65px;">
							<div class="card" id="reviewCard">
								<div class="card-text">
									<p>${list.review }</p>
									<br><br><br><br><br><br><br><br>
									<div class="text-right">${list.reviewDate }</div>
								</div>
							</div>
						</div>
				</c:if>		
				</c:forEach>
				</div>
				<br><hr>
				
				<div style="padding: 0px 20px 0px 20px;">
					<h4 style="font-weight: bold;">별점</h4>
					<input type="hidden" id="bookNo" value="${review.bookNo }">
					<input type="hidden" id="grade" value="${review.grade }">
					<input name="rating" id="rating-system" type="text" class="rating rating-loading" data-size="lg" style="vertical-align: top;" value="${grade.grade }">
				</div>
				
			</div>
		</div>
	</div>

</div>

<!-- writeModal -->
<form action="/sns/write" method="post" id="writeForm">
	<div class="modal fade" id="writeModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel" style="font-weight: bold;">리뷰 작성</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>

				<div class="modal-body">
					<input type="hidden" value="${review.feedNo }" id="feedNo" name="feedNo" />
					<input type="hidden" value="${review.memberNo }" id="memberNo" name="memberNo" /> 
					<input type="hidden" value="${review.bookNo }" id="bookNo" name="bookNo" />
					<input type="hidden" value="${review.bookName }" id="bookName" name="bookName" />
					<input type="hidden" value="${review.bookWriter }" id="bookWriter" name="bookWriter" />
					<input type="hidden" value="${review.bookPublisher }" id="bookPublisher" name="bookPublisher" />
					<input type="hidden" value="${review.publishingYear }" id="publishingYear" name="publishingYear" />
					<input type="hidden" value="${review.bookContent }" id="bookContent" name="bookContent" />
					<input type="hidden" value="${review.genreNo }" id="genreNo" name="genreNo" />
					 
					<h5>${review.bookName }</h5>
					<hr>
					<textarea type="text" name="review" id="review"
						placeholder="이 작품에 대한 생각을 표현해 주세요." required="required"
						style="border: none; width: 460px; height: 200px;"></textarea>
					<hr>
					<p><strong>공개 / 비공개</strong></p>
					<div class="custom-control custom-checkbox">
						<input type="checkbox" id="privacy" name ="privacy" class="custom-control-input"  onclick="check()">
						<label class="custom-control-label" for="privacy">공개</label>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary" id="reviewWrite" data-memberNo="${review.memberNo }">작성</button>
					<button type="button" class="btn btn-default" data-dismiss="modal"
						id="cancel">취소</button>
				</div>
			</div>
		</div>
	</div>
</form>

	<!-- loginModal -->
	<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="loginModalLabel">로그인</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <span>로그인이 필요한 기능입니다!</span>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>


<jsp:include page="/WEB-INF/views/layout/footer.jsp" />