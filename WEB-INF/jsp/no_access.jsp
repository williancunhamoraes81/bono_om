<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />

<link rel="stylesheet" href="<%= response.encodeURL(request.getContextPath()+ "/resources/css/jquery-ui.css") %>" rel="stylesheet" type="text/css">
<script language="javascript" charset="ISO-8859-1" type="text/javascript" src="<%= response.encodeURL(request.getContextPath()+ "/resources/js/jquery-3.6.0.min.js") %>"></script>
<link rel="stylesheet" href="<%= response.encodeURL(request.getContextPath() + "/resources/css/css_bono.css")  %>" rel="stylesheet" type="text/css">

</script>
 
</head>
<body> 
 
 <div class="todo">
	<div class="conteudo">
		<div class="centro" id="centro">
			<div class="miolo">
				<div id="content">
					<div class="content-inner">	
						Acesso não autorizado
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
 
<form id="register" name="main" method="get" action='<%=request.getAttribute("nextURI")%>'>
<input type="hidden" name="process" value="0">
</form>
</body>
</html> 