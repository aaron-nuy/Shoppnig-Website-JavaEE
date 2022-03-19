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


<div id="confirm">

	<div id="dimmer"></div>
	
	<div id="prompt">
	
		<div>Are you sure you want to delete this item?</div>
		
		<div>
			<button id="buttonYes">Yes</button>
			<button id="buttonNo">No</button>
		</div>
	
	</div>

</div>


<table id="tAdd">
	<tbody id="addingRow">
		<tr>
		<td>
			<input type="text" placeholder="Product Name" name="pname"/>
		</td>
		<td>
			<input type="text" placeholder="Product Description" name="pdesc"/>
		</td>
		<td>
			<input type="text" placeholder="Product Price" name="pprice"/>
		</td>
		<td>
			<input type="button" value="Add" id="bAdd" onclick="addProduct(this)"/>
		</td>
	</tr>
	</tbody>
</table>



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
			Delete
		</td>
		<td>
			Edit
		</td>
	</tr>
	
	<c:forEach var="product" items="${productList}">
		<tr>
			<td class="editable"><c:out value="${product.getName()}"/></td>
			<td class="editable"><c:out value="${product.getDescription()}"/></td>
			<td class="editable"><c:out value="${product.getPrice()}"/></td>
			<td class="button" id="bDelete" onclick="deleteRow(this)">Delete</td>
			<td class="button" id="bEdit" onclick="editRow(this)">Edit</td>
			<input type="hidden" value="${product.getId()}" class="productID"/>
		</tr>
	</c:forEach>
	
	</tbody>
</table>

	<script >
	
		var website = window.location.origin;
		var editing = false;
		var lastEditedRow = null;
		var default_bg_color;
		var correct_pattern = true;
		var default_text_color = getComputedStyle(document.querySelector(":root")).getPropertyValue("--sec-text-color");
		
		function sendPost(data,servlet,message){
			$.ajax({
				  type: "POST",
				  url: website + "/Site/" + servlet,
				  data: data,
				  success: message + "Sucsessfully",
				  dataType: "json"
				});
		}
	
		
		function confirm(conf){
			
		}
		
	
		function deleteRow(elementID){
			
 			let product_id = elementID.parentNode.getElementsByTagName("input")[0].value;
			let row = elementID.parentNode;
			
			
			let data = { productID : product_id };
			
			$("#confirm").css("display","flex");
			
			document.getElementById("buttonYes").onclick = () => {
					$("#confirm").css("display","none");
					
					sendPost(data,"DeleteProduct","Deleted");

					$("#confirm").css("display","none");
					row.parentNode.removeChild(row); 			
			};
			
			document.getElementById("buttonNo").onclick = () => {
				$("#confirm").css("display","none");	
		};

				 
		}
		
		function editRow(elementID){
			let product_id = elementID.parentNode.getElementsByTagName("input")[0].value;
			let row = elementID.parentNode;
	    	let cells = row.querySelectorAll(".editable");
			

		    if(editing){
		        if(lastEditedRow != row){
			    	let lastRowCells = lastEditedRow.querySelectorAll(".editable");
			    	lastRowCells.forEach(function(cell) {
			    		$(cell).attr("contentEditable","false");	
			    	});  
			   	    cells.forEach(function(cell) {
			    		$(cell).attr("contentEditable","true");	
			    	});
			   	    lastEditedRow.querySelector("#bEdit").innerHTML= "Edit";
			        elementID.innerHTML = "Apply";

			    	lastEditedRow=row;
		        }
		        else{
			        
				   	let productN = $(cells[0]).html();
				   	let productD = $(cells[1]).html();
				    let productP = $(cells[2]).html();
				        
				    const pattern1 = /^\d+(\.\d+)?$/;
				    let c1 = pattern1.test(productP);
				    const pattern2 = /^.+$/;
				    let c2 = pattern2.test(productN);
				    const pattern3 = /^.+$/;
				    let c3 = pattern3.test(productD);
				    
				    correct_pattern = c1 && c2 && c3;
				    
				    if(correct_pattern){
				    	cells.forEach(function(cell) {
				    		$(cell).attr("contentEditable","false");	
				    	});  	        
				        elementID.innerHTML = "Edit";
				    	$(row).css("background-color", default_bg_color);
				    	$(row).css("transform", "scale(1)");
				        
				        editing = false;
				        
				        let data = {
				        	productName: productN,
				        	productDescription: productD,
				        	productPrice: productP,
				        	productID: product_id
				        };
				        
				        sendPost(data,"UpdateProduct","Updated");
				        
				    }
				    else{    	
				    	$(row).css("background-color", "rgb(210,10,105)");
				    	$(row).css("transform", "scale(1.05)");
				    }
		        }	     
		    }
		    else {	
		    	default_bg_color = getComputedStyle(row).getPropertyValue("background-color");
		    	cells.forEach(function(cell) {
		    		$(cell).attr("contentEditable","true");	
		    	});
		        elementID.innerHTML = "Apply";
		        
		        editing = true;
		    	lastEditedRow=row;
		    }
		}
		
		
		function addProduct(element){
			let addingRow = $("#addingRow");
			
			let productN = $("input[name='pname']").val();
			let productD = $("input[name='pdesc']").val();
			let productP = $("input[name='pprice']").val();
			
		    const pattern1 = /^\d+(\.\d+)?$/;
		    let c1 = pattern1.test(productP);
		    const pattern2 = /^.+$/;
		    let c2 = pattern2.test(productN);
		    const pattern3 = /^.+$/;
		    let c3 = pattern3.test(productD);
		    
		    let correct = c1 && c2 && c3;
		    
		    if(correct){
				$("input[name='pname']").val("");
				$("input[name='pdesc']").val("");
				$("input[name='pprice']").val("");
				
				$("input[name='pname']").css("background-color", "#ddd");
				$("input[name='pdesc']").css("background-color", "#ddd");
				$("input[name='pprice']").css("background-color", "#ddd");
				
				$("input[name='pname']").css("transform", "scale(1)");
				$("input[name='pdesc']").css("transform", "scale(1)");
				$("input[name='pprice']").css("transform", "scale(1)");
				
				let userID = ${userID};
				
				
				let data = {
					productName : productN,
					productDescription : productD,
					productPrice : productP,
					supplierID: userID
				};
				
				sendPost(data,"AddProduct","Added");
				
				
				let tbody = $("#tBody");
				
				tbody.append("<tr><td class='editable'>"+ productN +"</td><td class='editable'>"+ productD +"</td><td class='editable'>"+ productP +"</td><td class='button' id='bDelete' onclick='deleteRow(this)'>Delete</td><td class='button' id='bEdit' onclick='editRow(this)'>Edit</td><input type='hidden' value='0' class='productID'/></tr>");
				

				
		    }
		    else{
				$("input[name='pname']").css("background-color", "rgb(210,10,105)");
				$("input[name='pdesc']").css("background-color", "rgb(210,10,105)");
				$("input[name='pprice']").css("background-color", "rgb(210,10,105)");
				
				$("input[name='pname']").css("transform", "scale(1.05)");
				$("input[name='pdesc']").css("transform", "scale(1.05)");
				$("input[name='pprice']").css("transform", "scale(1.05)");
		    }

		}
		
	</script>
</body>
</html>