<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/views/layout/header.jsp" />
<link href="/resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

<style type="text/css">

.column {
	width: 100%;
	padding: 0 10px;
}

.card {
	height: 380px;
}

.card {
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
	/* this adds the "card" effect */
	padding: 16px;
	background-color: #f1f1f1;
}

@media screen and (max-width: 600px) {
	.column {
		width: 100%;
		display: block;
		margin-bottom: 20px;
	}
}
</style>

<script type="text/javascript">
$(document).ready(function() {
	//뒤로가기
	$("#back").click(function() {
		history.go(-1);
	});
	
});
</script>

<hr>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fas fa-arrow-left" id='back'></i>
 <br><br>
<h1 style="font-weight: bold;">&nbsp;&nbsp;리뷰</h1>
<hr>
<div class="container">

	<div class="row">
		<div class="column">
			<div class="card" id="review">
				<div class="card-text">
					<h5 style="font-weight: bold;">${MemberNick }</h5>(작성자가 준 별점 들어갈 예정)
					<hr>
					<p>${review.review }</p>
					<br><br><br>
					<p class="text-right">${review.reviewDate }</p>
					<hr>
					&nbsp;&nbsp;<i class="far fa-thumbs-up"></i>&nbsp;(좋아요 개수)
					<hr>
					<button style="color: #ff2f6e; font-weight: bold; border:none;">좋아요</button>
				</div>
			</div>

		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/views/layout/footer.jsp" />