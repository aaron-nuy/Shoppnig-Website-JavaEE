<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<style><%@include file="loggedinhome.css"%></style>
	<style>	
	.hide {
		display: none;
	}
	td:nth-child(1),td:nth-child(2),td:nth-child(3),td:nth-child(4),td:nth-child(5) {
	width: 20%;
}
	</style>
	<title>Din - Home</title>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
	<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
	<%@ page import="java.util.ArrayList"%>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="ISO-8859-1">
</head>
<body>


<table>
	<tbody id="tBody">
	<tr>
		<td>
			First Name
		</td>
		<td>
			Last Name
		</td>
		<td>
			Email
		</td>
		<td>
			Registration Number
		</td>
		<td>
			ID
		</td>
		<td>
			Delete User
		</td>
	</tr>
	
	<c:forEach var="supplier" items="${userList}">
		<tr>
			<td class="editable"><c:out value="${supplier.getFirstName()}"/></td>
			<td class="editable"><c:out value="${supplier.getLastName()}"/></td>
			<td class="editable"><c:out value="${supplier.geteMail()}"/></td>
			<td class="editable"><c:out value="${supplier.getRegNumber()}"/></td>
			<td class="editable" id="supplier_id"><c:out value="${supplier.getId()}"/></td>
			<td class="button" id="bDelete" onclick="deleteUser(this)">Delete</td>
		</tr>
	</c:forEach>
	
	</tbody>
</table>

	<script >
	
		var website = window.location.origin;
		
		
		
		function sendPost(data,servlet,message){
			$.ajax({
				  type: "POST",
				  url: website + "/Site/" + servlet,
				  data: data,
				  success: message + "Sucsessfully",
				  dataType: "json"
				});
		}
		
		
		function deleteUser(element){
			let user_id = element.parentNode.querySelector("#supplier_id").innerHTML;
			console.log(user_id);
			let data = {
				userID : user_id
			}
			
			sendPost(data,"DeleteUser","Deleted");

			let row = element.parentNode;
			row.parentNode.removeChild(row);
		}
	
	</script>
</body>
</html>