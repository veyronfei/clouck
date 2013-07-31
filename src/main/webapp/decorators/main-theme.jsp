<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
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
    <c:set var="currentServiceMenu" scope="request"><decorator:getProperty property="meta.serviceMenu"/></c:set>
    <c:set var="currentResourceMenu" scope="request"><decorator:getProperty property="meta.resourceMenu"/></c:set>
    <div id="wrap">
		<div class="navbar navbar-inverse navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container-fluid">
					<a class="brand" href="/app/accounts/${currentAccount.id}/ec2/overview">Clouck</a>
					<div class="nav-collapse collapse">
						<ul class="nav">
							<li class="<c:if test="${currentServiceMenu == 'ec2'}">active</c:if>"><a href="/app/accounts/${currentAccount.id}/ec2/overview">EC2</a></li>
	<%-- 						<li class="<c:if test="${currentServiceMenu == 'vpc'}">active</c:if>"><a href="#">VPC</a></li>
							<li class="<c:if test="${currentServiceMenu == 'iam'}">active</c:if>"><a href="#">IAM</a></li> --%>
						</ul>
						<ul class="nav pull-right">
	                        <li id="fat-menu" class="dropdown">
	                            <a href="#" id="drop3" role="button" class="dropdown-toggle" data-toggle="dropdown">${currentUser.fullName}<b class="caret"></b></a>
	                            <ul class="dropdown-menu" role="menu" aria-labelledby="drop3">
	                                <li>
	                                    <a role="menuitem" tabindex="-1" href="/app/accounts"">Account Setting</a>
	                                </li>
	                                <li role="presentation">
	                                    <a role="menuitem" tabindex="-1" href="/app/j_spring_security_logout">Logout</a>
	                                </li>
	                            </ul>
	                        </li>
	                    </ul>
<%-- 						<ul class="nav pull-right">
							<li id="fat-menu" class="dropdown">
							    <a href="#" id="drop3" role="button" class="dropdown-toggle" data-toggle="dropdown">${currentRegion.shortDesc}<b class="caret"></b></a>
								<ul class="dropdown-menu" role="menu" aria-labelledby="drop3">
									<c:forEach var="region" items="${regions}">
										<li role="presentation">
										    <a role="menuitem" tabindex="-1" href="#" onclick="changeRegion('${currentAccount.id}', '${region.regions.name}')">${region.desc}</a>
										</li>
									</c:forEach>
								</ul>
							</li>
						</ul> --%>
	                    <ul class="nav pull-right">
	                        <li id="fat-menu" class="dropdown">
	                            <a href="#" id="drop3" role="button" class="dropdown-toggle" data-toggle="dropdown">${currentAccount.name}<b class="caret"></b></a>
	                            <ul class="dropdown-menu" role="menu" aria-labelledby="drop3">
	                                <c:forEach var="account" items="${accounts}">
	                                    <li role="presentation">
	                                        <a role="menuitem" tabindex="-1" href="/app/accounts/${account.id}/ec2/overview">${account.name}</a>
	                                    </li>
	                                </c:forEach>
	                                <li>
		                            <c:choose>
		                                <c:when test="${currentAccount.id != demoAccountId}">
		                                    <a href="javascript:addNewAccount(true)">Add a new account</a>
		                                </c:when>
		                                <c:otherwise>
		                                    <a href="javascript:addNewAccount(false)">Add a new account</a>
		                                </c:otherwise>
		                            </c:choose>
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
				<div class="span2" style="width: 14%;">
					<div class="well sidebar-nav">
						<ul class="nav nav-list" style="padding-left: 35px;padding-right: 15px;">
							<li class="<c:if test="${currentResourceMenu == 'overview'}">active</c:if>">
							    <a href="/app/accounts/${currentAccount.id}/ec2/overview">Overview</a>
							</li>
							<li class="nav-header">INSTANCES</li>
							<li class="<c:if test="${currentResourceMenu == 'instances'}">active</c:if>">
							    <a href="/app/accounts/${currentAccount.id}/ec2/instances/versions">Instances</a>
							</li>
							<li class="<c:if test="${currentResourceMenu == 'spotInstanceRequests'}">active</c:if>">
	                            <a href="/app/accounts/${currentAccount.id}/ec2/spotInstanceRequests/versions">Spot Requests</a>
	                        </li>
							<li class="nav-header">IMAGES</li>
							<li class="<c:if test="${currentResourceMenu == 'amis'}">active</c:if>">
							    <a href="/app/accounts/${currentAccount.id}/ec2/amis/versions">AMIs</a>
							</li>
							<li class="nav-header">ELASTIC BLOCK STORE</li>
							<li class="<c:if test="${currentResourceMenu == 'volumes'}">active</c:if>">
							    <a href="/app/accounts/${currentAccount.id}/ec2/volumes/versions">Volumes</a>
							</li>
							<li class="<c:if test="${currentResourceMenu == 'snapshots'}">active</c:if>">
							    <a href="/app/accounts/${currentAccount.id}/ec2/snapshots/versions">Snapshots</a>
							</li>
							<li class="nav-header">NETWORK & SECURITY</li>
							<li class="<c:if test="${currentResourceMenu == 'securityGroups'}">active</c:if>">
							    <a href="/app/accounts/${currentAccount.id}/ec2/securityGroups/versions">Security Groups</a>
							</li>
							<li class="<c:if test="${currentResourceMenu == 'elasticIPs'}">active</c:if>">
							    <a href="/app/accounts/${currentAccount.id}/ec2/elasticIPs/versions">Elastic IPs</a>
							</li>
							<li class="<c:if test="${currentResourceMenu == 'placementGroups'}">active</c:if>">
							    <a href="/app/accounts/${currentAccount.id}/ec2/placementGroups/versions">Placement Groups</a>
							</li>
							<li class="<c:if test="${currentResourceMenu == 'loadBalancers'}">active</c:if>">
							    <a href="/app/accounts/${currentAccount.id}/ec2/loadBalancers/versions">Load Balancers</a>
							</li>
							<li class="<c:if test="${currentResourceMenu == 'keyPairs'}">active</c:if>">
							    <a href="/app/accounts/${currentAccount.id}/ec2/keyPairs/versions">Key Pairs</a>
							</li>
							<li class="<c:if test="${currentResourceMenu == 'networkInterfaces'}">active</c:if>">
							    <a href="/app/accounts/${currentAccount.id}/ec2/networkInterfaces?at=${millis}">Network Interfaces</a>
							</li>
						</ul>
					</div>
				</div>
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
	    <form:form modelAttribute="newAccount" action="/app/accounts" method="POST" >
		    <div class="modal-header">
		        <c:if test="${currentAccount.id != demoAccountId}">
			        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		        </c:if>
		        <h3 id="myModalLabel">Haven't connect an account?</h3>
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
		        <c:if test="${currentAccount.id != demoAccountId}">
		            <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
		        </c:if>
		        <button class="btn btn-primary" type="submit">Connect Account</button>
		    </div>
		</form:form>
	</div>
    <script type="text/javascript">
    $(document).ready(function() {
        <c:if test="${currentAccount.id == demoAccountId}">
        addNewAccount(false);
        </c:if>
    });
    </script>
</body>
</html>