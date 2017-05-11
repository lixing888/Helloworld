<html>
<head>
<link rel="stylesheet" href="css/style3.css">
<script type="text/javascript" src="static/js/jquery-1.9.1.min.js"></script>
<title>新闻搜索</title>
<script type="text/javascript">   

function submit(){
	var keyword = $("#keyword").val();
	$.ajax({
		type:'post',
		data:{"keyword":keyword},
		url:'bjjduserinfo/searchByKeyWord',
		async:false,
		success:function(data){
			alert("111");
			alert(data);
		}
	})
}
function subform(){   
 if (document.zl_form.keyword.value==""){
  alert("请输入关键字！");
  document.zl_form.keyword.focus();
  return false;
 }     
 return true;       
}
</script>
</head>
<body bgcolor="#F0F6E2">
<form name="zl_form"  target="_new" method="post" action="/BJJD/bjjduserinfo/searchByKeyWord.do">
  <table width="600" bgcolor="#F0F6E2">
    <tr> 
      <td colspan="4" height="10">  </td>
    </tr>
    <tr> 
      <td width="20%">输入查询关键字:</td>
      <td align="left" width="65%"> 
        <input size="50" type="text" id="keyword" name="keyword" style="font-size: 9pt">
        <input type="button" name="submit" onclick="submit()" value="搜索" style="font-size: 9pt">
      </td> 
    </tr>
<!--<tr> 
      <td colspan="2" height="9" align="left"><br>
        <font color="red" size="+1">说明：如果有多个查询条件，中间用</font><font size="+2">+</font><font color="red" size="+1">隔开。如:1+2+3+4...</font>
      </td>
    </tr>  -->
  </table>		 
</form> 
  </body> 
  </html> 