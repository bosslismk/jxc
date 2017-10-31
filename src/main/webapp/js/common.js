function getChecks(){
		var arrChk=$("input[name='ck_id']:checked");
		var ids=new Array();
		for (var i=0;i<arrChk.length;i++)
	    {
	         ids[i]=(arrChk[i].value);
	    }
		return ids;
	}
function createPagination(data,targetUrl){
	var pagination="";
	pagination+='<center>';
	pagination+='<ul class="pagination">';
	if(data.currentPage==1){
		pagination+="<li class='disabled'><a href='javascript:void(0)'>首页</a></li>";
	}else{
		pagination+="<li><a href=\"javascript:ajaxGetList('"+targetUrl+"?page=1&"+data.params+"','"+targetUrl+"')\">首页</a></li>"
	}
	if(data.currentPage>1){
		pagination+=("<li><a href=\"javascript:ajaxGetList('"+targetUrl+"?page="+(data.currentPage-1)+"&"+data.params+"','"+targetUrl+"')\">上一页</a></li>");			
	}
	for(var i=data.currentPage-2;i<=data.currentPage+2;i++){
		if(i<1||i>data.totalPage){
			continue;
		}
		if(i==data.currentPage){
			pagination+=("<li  class='active' > <a href='javascript:void(0)'>"+i+"</a> </li>");		
		}else{
			pagination+=("<li><a href=\"javascript:ajaxGetList('"+targetUrl+"?page="+i+"&"+data.params+"','"+targetUrl+"')\">"+i+"</a></li>");	
		}
	}
	if(data.currentPage<data.totalPage){
		pagination+=("<li><a href=\"javascript:ajaxGetList('"+targetUrl+"?page="+(data.currentPage+1)+"&"+data.params+"','"+targetUrl+"')\">下一页</a></li>");		
	}
	if(data.currentPage==data.totalPage){
		pagination+=("<li class='disabled'><a href='javascript:void(0)'>尾页</a></li>");
	}else{
		pagination+=("<li><a href=\"javascript:ajaxGetList('"+targetUrl+"?page="+data.totalPage+"&"+data.params+"','"+targetUrl+"')\">尾页</a></li>");
	}
	pagination+=("</ul>");
	pagination+='</center>';
	$('#data-table').after(pagination);
 
}
function getDateByMillisecond(s){
	var dateTime=new Date(s);
	return dateTime.getFullYear()+"-"+( dateTime.getMonth()+1)+"-"+ dateTime.getDate()+" "+ dateTime.getHours()+":"+ dateTime.getMinutes() +":"+ dateTime.getSeconds() ;
}