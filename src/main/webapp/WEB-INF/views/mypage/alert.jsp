<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<jsp:include page="/WEB-INF/views/layout/header.jsp" />
<jsp:include page="/WEB-INF/views/mypage/mypageMenu.jsp" />

<script type="text/javascript">
window.onload = function() {
	alert('${msg}'); 
	document.location.href = '${url}';
};
</script>


<jsp:include page="/WEB-INF/views/layout/footer.jsp" />