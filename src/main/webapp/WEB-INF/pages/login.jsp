<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="login-theme"/>
<title>Login</title>
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

.form-signin {
	max-width: 300px;
	padding: 19px 29px 29px;
	margin: 0 auto 20px;
	background-color: #fff;
	border: 1px solid #e5e5e5;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.form-signin .form-signin-heading,.form-signin .checkbox {
	margin-bottom: 10px;
}

.form-signin input[type="text"],.form-signin input[type="password"] {
	font-size: 16px;
	height: auto;
	margin-bottom: 15px;
	padding: 7px 9px;
}
</style>
</head>
<body>

<div class="container">
    <form action="/app/j_spring_security_check" class="form-signin" method="POST">
		<h2 class="form-signin-heading">Log in</h2>
	    <div style="margin: 0; padding: 0; display: inline">
            <c:if test="${param.error == 'true'}">
                <FONT color="red">Account not exists or the combination of email and password is not matched with our database</FONT>
            </c:if>
        </div>
		<input type="text" name="j_username" class="input-block-level" placeholder="Email">
		<input type="password" name="j_password" class="input-block-level" placeholder="Password">
		<button class="btn btn-large btn-primary" type="submit">Log in</button>
		<a href="/app/signUp">Need an account? Sign up.</a>
	</form>
</div>

</body>
</html>