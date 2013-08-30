<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
    <title>Cloud Stage</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="icon" href=/images/favicon.ico"/>
    <link href="/styles/a.css" rel="stylesheet" type="text/css" />
</head>

<body>
    <a href="<c:url value="/j_spring_security_logout" />">Logout</a>
	<div id="mainFrame">
		<div id="mainContainer">
			<div id="header">
			</div>
			<div id="accountContainer">
			    Please notice that only ebs backed instance will be replicated.!!!!
				<form:form action="/addaccount" method="post" modelAttribute="addAccountForm">
			    <table>
			        <tr>
			            <td>
						    <form:label path="accessKey">access key</form:label>
			            </td>
                        <td>
							<form:input path="accessKey" />
                        </td>
			        </tr>
                    <tr>
                        <td>
                            <form:label path="secretKey">secret key</form:label>
                        </td>
                        <td>
                            <form:input path="secretKey" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                        
                            Region:Virginia
                            <%-- <form:select path="region">
                                <form:options items="${regions}" itemLabel="name" itemValue="id"/>
                            </form:select> --%>
                        </td>
                    </tr>
                    
                    <tr>
                        <td>
                            <form:label path="dstAccessKey">destination access key</form:label>
                        </td>
                        <td>
                            <form:input path="dstAccessKey" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:label path="dstSecretKey">destination secret key</form:label>
                        </td>
                        <td>
                            <form:input path="dstSecretKey" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
							<input type="submit" value="Next" />
                        </td>
                    </tr>
			    </table>
				</form:form>
			</div>
		</div>
		<!-- mainContainer -->
	</div>
	<!-- mainFrame -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script type="text/javascript">
	</script>
</body>
</html>
