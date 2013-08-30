<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="securityGroups"/>
<title>Security Group</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li>
                <a href="/accounts/${currentAccount.id}/ec2/securityGroups/versions">Security Groups</a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                <i class="icon-time"></i>
                <a href="/accounts/${currentAccount.id}/ec2/securityGroups?at=${at.time}"><fmt:formatDate pattern="${datePattern}" value="${at}" /></a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                ${ec2Resource.resource.groupId}
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
	<table class="table table-striped table-bordered table-hover">
	    <thead>
            <tr>
                <th width="20%">Group ID:</th>
                <th width="30%">${ec2Resource.resource.groupId}</th>
                <th width="20%">Time Detected:</th>
                <th width="30%"><fmt:formatDate pattern="${datePattern}" value="${ec2Resource.timeDetected}"/></th>
            </tr>
        </thead>
		<tbody>
			<tr>
				<td><strong>Group Name:</strong></td>
				<td>${ec2Resource.resource.groupName}</td>
                <td><strong>Group Description:</strong></td>
                <td>${ec2Resource.resource.description}</td>
			</tr>
	        <tr>
	            <td><strong>VPC ID:</strong></td>
	            <td colspan="3">${ec2Resource.resource.vpcId}</td>
	        </tr>
		</tbody>
	</table>

    <c:set var="ipPermissions" value="${ec2Resource.resource.ipPermissions}"/>
    <c:set var="title" value="Inbound"/>
    <%@ include file="ec2-security-in-out-bound.jspf"%>

    <c:set var="ipPermissions" value="${ec2Resource.resource.ipPermissionsEgress}"/>
    <c:set var="title" value="Outbound"/>
    <%@ include file="ec2-security-in-out-bound.jspf"%>
</div>
</body>
</html>