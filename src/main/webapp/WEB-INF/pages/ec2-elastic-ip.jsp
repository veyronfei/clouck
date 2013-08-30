<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="elasticIPs"/>
<title>Elastic IP</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li>
                <a href="/accounts/${currentAccount.id}/ec2/elasticIPs/versions">Elastic IPs</a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                <i class="icon-time"></i>
                <a href="/accounts/${currentAccount.id}/ec2/elasticIPs?at=${at.time}"><fmt:formatDate pattern="${datePattern}" value="${at}" /></a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                ${ec2Resource.resource.publicIp}
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
	<table class="table table-striped table-bordered table-hover">
	    <thead>
            <tr>
                <th width="20%">Address:</th>
                <th width="30%">${ec2Resource.resource.publicIp}</th>
                <th width="20%">Time Detected:</th>
                <th width="30%"><fmt:formatDate pattern="${datePattern}" value="${ec2Resource.timeDetected}"/></th>
            </tr>
        </thead>
		<tbody>
			<tr>
	            <td><strong>Instance ID:</strong></td>
	            <td>${ec2Resource.resource.instanceId}</td>
	            <td><strong>Scope:</strong></td>
	            <td>${ec2Resource.resource.domain}</td>
			</tr>
	        <tr>
	            <td><strong>Network Interface ID:</strong></td>
	            <td>${ec2Resource.resource.networkInterfaceId}</td>
	            <td><strong>Private IP Address:</strong></td>
	            <td>${ec2Resource.resource.privateIpAddress}</td>
	        </tr>
	        <tr>
	            <td><strong>Network Interface Owner:</strong></td>
	            <td>${ec2Resource.resource.networkInterfaceOwnerId}</td>
	            <td><strong>Allocation ID:</strong></td>
	            <td>${ec2Resource.resource.allocationId}</td>
	        </tr>
		</tbody>
	</table>
</div>
</body>
</html>