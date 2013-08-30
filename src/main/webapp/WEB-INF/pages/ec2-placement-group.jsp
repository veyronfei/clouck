<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="placementGroups"/>
<title>Placement Group</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li>
                <a href="/accounts/${currentAccount.id}/ec2/placementGroups/versions">Placement Groups</a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                <i class="icon-time"></i>
                <a href="/accounts/${currentAccount.id}/ec2/placementGroups?at=${at.time}"><fmt:formatDate pattern="${datePattern}" value="${at}" /></a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                ${ec2Resource.resource.groupName}
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
	<table class="table table-striped table-bordered table-hover">
	    <thead>
            <tr>
                <th width="20%">Group Name:</th>
                <th width="30%">${ec2Resource.resource.groupName}</th>
                <th width="20%">Time Detected:</th>
                <th width="30%"><fmt:formatDate pattern="${datePattern}" value="${ec2Resource.timeDetected}"/></th>
            </tr>
        </thead>
		<tbody>
			<tr>
	            <td><strong>Strategy:</strong></td>
	            <td>${ec2Resource.resource.strategy}</td>
	            <td><strong>State:</strong></td>
	            <td>${ec2Resource.resource.state}</td>
	        </tr>
		</tbody>
	</table>
</div>
</body>
</html>