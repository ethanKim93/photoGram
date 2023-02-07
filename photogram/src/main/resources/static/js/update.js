// jsp form에 put 메소드를 사용할 수 없기 때문에 js를 사용
// (1) 회원정보 수정
/*function update(userId,event) {
	alert("update");
	console.log(event);
}*/

//jquery는 header.js 에 정의 해놓음
function update(userId,event) {
	event.preventDefault(); //폼태그 액션을 막기!
	let data = $("#profileUpdate").serialize(); // profileUpdate는  jsp의 orm에서 정의된 ID 값
	console.log(data);
	$.ajax({
		type:"put", 
		url:`/api/user/${userId}`,
		data:data, 
		contentType :"application/x-www-form-urlencoded;charset=utf-8",
		dataType:"json"
	}).done(res=>{ // HttpStatus 상태코드 200번대
			console.log("update 성공",res);
			locatuon.href = `/user/${userId}`;
	}).fail(error=>{  // HttpStatus 상태코드 200번대가 아닐때 
		alert(error.responseJSON,data.name)
		console.log("update 실패",data);
	});
}