<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<style><%@include file="homestyle.css"%></style>
	<style>	
	.hide {
		display: none;
	}
	</style>
	<title>Din - Supplier Login</title>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
	<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
</head>

<body>
    <div id="header_wrapper">
        <div id="header">
            <form action="Login" method="post">
                <li>Email<br><input type="email" name="email" required></li>
                <li>Password<br><input type="password" name="password" required><a href="">Forgotten account?</a></li>
                <li><input type="submit" value="Log In"></li>
            </form>
        </div>
    </div>

    <div id="wrapper">
    
    	<div id="account_choice">   
	    	<div>
			   	<p>
			   	You are a
			    </p>
	    	</div>
	    	<div id="buttons">
		    	<button onclick="supplier()">Supplier</button>
		    	<button onclick="user()">User</button>
	    	</div>
    	</div>
    
        <div id="div2" class="hide" >
        <form action="Create" method="post">
            <h1>Create an account</h1>

            <li><input type="text" placeholder="First Name" id="Firstname" name="fName" required></li>
            
            <li><input type="text" placeholder="Surname" id="surname" name="lName" required></li>
            
            
            <li><input type="email" placeholder="Example@domain.com" name="cEmail" required></li>
            
            <li><input type="text" placeholder="Supplier ID" name ="suppID" id="regNum" required></li>
            <li><input type="password" placeholder="New password" name="cPassword" required></li>
            <p>Birthday</p>

            <li id="choices">
            <span>
                <input type="date" name="date" required>
            </span>

            <span id="gender">
			    <input type="radio" name="sex" id="m" value="f" required><label for="f">Female</label>
			    <input type="radio" name="sex" id="m" value="m"><label for="m">Male</label>
            </span>
            </li>
			
            <li id="terms">
                By clicking Create an account, you agree to our <a href="">Terms</a> and that <br>you have read our <a href="">Data Policy</a>, including our <a href="">Cookie Use</a>.
            </li>

            <li><input type="submit" value="Create an account"></li>

        </form>
        </div>
    </div>

	<script>
	

	function user(){
	    var firstDiv = document.getElementById("account_choice");
	    var secondDiv = document.getElementById("div2");
	    var reg = document.getElementsByName("suppID");
		
	    
	    reg[0].style.display = "none";
	    reg[0].value="none";
	    firstDiv.parentNode.removeChild(firstDiv);
	    secondDiv.style.display = "inline" ; 
	}

	function supplier(){
	    var firstDiv = document.getElementById("account_choice");
	    var secondDiv = document.getElementById("div2");
	    firstDiv.parentNode.removeChild(firstDiv);

	    document.getElementsByName("suppID").value = "";

	    secondDiv.style.display = "inline" ; 
	}
	
	</script>


</body>
</html>
