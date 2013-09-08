<%@ include file="/common/taglibs.jsp"%>
<html lang="en">
<head>
<%@include file="header.jspf"%>
<title>Clouck - <decorator:title /></title>
<decorator:head />

<style type="text/css">
html, body {
  height: 100%;
  /* The html and body elements cannot have any padding or margin. */
}

/* Wrapper for page content to push down footer */
#wrap {
    padding-top: 60px;
    min-height: 100%;
    height: auto !important;
    height: 100%;
    /* Negative indent footer by it's height */
    margin: 0 auto -60px;
}

/* Set the fixed height of the footer here */
#push, #footer {
    height: 60px;
}
#footer {
    background-color: #f5f5f5;
}

/* Lastly, apply responsive CSS fixes as necessary */
@media (max-width: 767px) {
    #footer {
        margin-left: -20px;
        margin-right: -20px;
        padding-left: 20px;
        padding-right: 20px;
  }
}

/* Custom page CSS
-------------------------------------------------- */
/* Not required for template or sticky footer method. */

.container {
    width: auto;
    max-width: 680px;
}
.container .credit {
      margin: 20px 0;
}

.sidebar-nav {
    padding: 9px 0;
    height: 500px;
}

@media ( max-width : 980px) {
    /* Enable use of floated navbar text */
    .navbar-text.pull-right {
        float: none;
        padding-left: 5px;
        padding-right: 5px;
    }
}
</style>
</head>

<body <decorator:getProperty property="body.id" writeEntireProperty="true"/>>
    <div id="wrap">
		<div class="navbar navbar-inverse navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container-fluid">
					<a class="brand" href="${ctx}/accounts/ec2/overview">Clouck</a>
					<div class="nav-collapse collapse">
						<ul class="nav">
							<li><a href="${ctx}/accounts/ec2/overview">EC2</a></li>
						</ul>
						<ul class="nav pull-right">
	                        <li id="fat-menu" class="dropdown">
	                            <a href="#" id="drop3" role="button" class="dropdown-toggle" data-toggle="dropdown">${currentUser.fullName}<b class="caret"></b></a>
	                            <ul class="dropdown-menu" role="menu" aria-labelledby="drop3">
	                                <li>
	                                    <a role="menuitem" tabindex="-1" href="${ctx}/accountSetting">Account Setting</a>
	                                </li>
	                                <li role="presentation">
	                                    <a role="menuitem" tabindex="-1" href="${ctx}/j_spring_security_logout">Logout</a>
	                                </li>
	                            </ul>
	                        </li>
	                    </ul>
					</div>
				</div>
			</div>
		</div>
		<div class="container-fluid" style="padding-left: 0px;padding-right: 0px;">
			<div class="row-fluid">
				<div class="span10">
				    <decorator:body />
				</div>
			</div>
		</div>
		<div id="push"></div>
	</div>
    <div id="footer">
        <div class="container">
            <p class="muted credit">&copy; Clouck 2013</p>
        </div>
    </div>
	<!-- Placed at the end of the document so the pages load faster -->
    <%@include file="footer.jspf"%>
	<!-- Modal -->
	<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <form:form modelAttribute="newAccount" action="${ctx}/accounts" method="POST" >
		    <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		        <h3 id="myModalLabel">Connect an account?</h3>
		    </div>
	        <div class="row-fluid">
			    <div class="modal-body span6">
			        <p>
			        <FONT color="red"><form:errors path="*" /></FONT>
			        </p>
			        <p>
			        <form:input path="name" placeholder="Account Name"/>
	                </p>
	                <p>
	                <form:input path="accessKeyId"  placeholder="Access Key Id"/>
	                </p>
	                <p>
	                <form:input path="secretAccessKey" placeholder="Secret Access Key"/>
			        </p>
			    </div>
                <div class="span6" style="margin-top: 40px">
                    <h4>We recommend you provide us read-only access to AWS services and resources.</h4>
                </div>
		    </div>
		    <div class="modal-footer">
                <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
		        <button class="btn btn-primary" type="submit">Connect Account</button>
		    </div>
		</form:form>
	</div>
</body>
</html>