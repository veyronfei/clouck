<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="login-theme"/>
<title>Sign up</title>
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
    <form:form class="form-signin" method="POST" modelAttribute="signupForm">
        <h2 class="form-signin-heading">Sign up</h2>

        <form:input path="fullName" class="input-block-level" placeholder="Full Name"/>
        <FONT color="red"><form:errors path="fullName" /></FONT>

        <form:input path="email" class="input-block-level" placeholder="Email"/>
        <FONT color="red"><form:errors path="email" /></FONT>

        <form:password path="password" class="input-block-level" placeholder="Password"/>
        <FONT color="red"><form:errors  path="password" /></FONT>

        <p>
        <button class="btn btn-large btn-primary" type="submit">Sign up</button>
        <a href="${ctx}/login">Have an account? Log in.</a>
        </p>
    </form:form>
</div>

</body>
</html>