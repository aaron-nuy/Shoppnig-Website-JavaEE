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


<div id="cardInfo">
</div>


	<form action="Buy" method="post" id="formInfo">
	
		<div class="form-container">
        <div class="field-container">
            <label for="name">Name</label>
            <input id="name" maxlength="20" type="text" placeholder="John Doe" required>
        </div>
        <div class="field-container">
            <label for="cardnumber">Card Number</label>
            <input id="cardnumber" type="text" pattern="[0-9]*" inputmode="numeric" placeholder="0000 0000 0000 0000" required>
        </div>
        <div class="field-container">
            <label for="expirationdate">Expiration (mm/yy)</label>
            <input id="expirationdate" type="text" pattern="[0-9]*" inputmode="numeric" placeholder="MM/YY" required>
        </div>
        <div class="field-container">
            <label for="securitycode">Security Code</label>
            <input id="securitycode" type="text" pattern="[0-9]*" inputmode="numeric" placeholder="XXX" required>
        </div>
 		</div>
		<input id="userID" type="hidden" value="${userID}" name="userID"/>
		<input id="products" type="hidden" value="" name="products"/>
		<input id="sub" type="submit" value="Buy"/>
	
	</form>





<table>
	<tbody id="tBody">
	<tr>
		<td>
			Name
		</td>
		<td>
			Description
		</td>
		<td>
			Price
		</td>
		<td>
			Shop
		</td>
	</tr>
	
	<c:forEach var="product" items="${productList}">
		<tr>
			<td class="editable"><c:out value="${product.getName()}"/></td>
			<td class="editable"><c:out value="${product.getDescription()}"/></td>
			<td class="editable"><c:out value="${product.getPrice()}"/></td>
			<td class="button" id="addToCart" onclick="addToCart(this)">Add to cart</td>
			<td class="added">&#10060</td>
			<input type="hidden" value="${product.getId()}" class="productID"/>
		</tr>
	</c:forEach>
	
	<button id="buyButton">Buy</button>
	
	
	</tbody>
</table>

	<script >


	
	let products = [];
	let checked =  '&#9989';
	let notChecked = '&#10060';
	$("#buyButton").attr("disabled",true);
	
	function addToCart(element){
		

		
		let product = element.parentNode;
		let added = product.querySelector(".added");
		let productID = product.querySelector(".productID").value;

		
		if(checked == '&#' + added.innerHTML.charCodeAt(0).toString() ){
			added.innerHTML = notChecked;
			let index = products.indexOf(productID);
			products.splice(index,1);
		}
		else{
			added.innerHTML = checked;
			products.push(productID);
		}
		
		$("#buyButton").attr("disabled",products.length <= 0);
	}
	
	
	$("#buyButton").click( () => {
		
		$("#cardInfo").css("display", "inline");
		$("#formInfo").css("display", "inline");
		$("#tBody").css("display","none");
		$('#buyButton').css("display","none");
		
		
		let productsString;
		
		products.forEach( product => {
			if(product == products[0])
				productsString = product;
			else
				productsString += ' ' + product;
		});
		
		
		$('#products').val(productsString);
		
		console.log($('#products').val());
		
	});
		
	</script>
</body>
</html>