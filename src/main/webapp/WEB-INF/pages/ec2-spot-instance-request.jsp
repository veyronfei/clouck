<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="spotInstanceRequests"/>
<title>Spot Instance Request</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li>
                <a href="${ctx}/accounts/${currentAccount.id}/ec2/spotInstanceRequests/versions">Spot Requests</a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                <i class="icon-time"></i>
                <a href="${ctx}/accounts/${currentAccount.id}/ec2/spotInstanceRequests?at=${at.time}"><fmt:formatDate pattern="${datePattern}" value="${at}" /></a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                ${ec2Resource.resource.spotInstanceRequestId}
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
	<table class="table table-striped table-bordered table-hover">
	    <thead>
            <tr>
                <th width="20%">Request ID:</th>
                <th width="30%">${ec2Resource.resource.spotInstanceRequestId}</th>
                <th width="20%">Time Detected:</th>
                <th width="30%"><fmt:formatDate pattern="${datePattern}" value="${ec2Resource.timeDetected}"/></th>
            </tr>
        </thead>
		<tbody>
			<tr>
				<td><strong>AMI:</strong></td>
				<td>${ec2Resource.resource.launchSpecification.imageId}</td>
	            <td><strong>Request Persistence:</strong></td>
	            <td>${ec2Resource.resource.type}</td>
			</tr>
	        <tr>
	            <td><strong>Instance Type:</strong></td>
	            <td>${ec2Resource.resource.launchSpecification.instanceType}</td>
	            <td><strong>Availability Zone:</strong></td>
	            <td>${ec2Resource.resource.launchSpecification.placement.availabilityZone}</td>
	        </tr>
	        <tr>
	            <td><strong>Monitoring Enabled:</strong></td>
	            <td>${ec2Resource.resource.launchSpecification.monitoringEnabled}</td>
	            <td><strong>Availability Zone Group:</strong></td>
	            <td>${ec2Resource.resource.availabilityZoneGroup}</td>
	        </tr>
	        <tr>
	            <td><strong>Maximum Price:</strong></td>
	            <td>$<fmt:formatNumber type="number" maxFractionDigits="3" value="${ec2Resource.resource.spotPrice}" /></td>
	            <td><strong>Launch Group:</strong></td>
	            <td>${ec2Resource.resource.launchGroup}</td>
	        </tr>
            <tr>
                <td><strong>Request Valid From:</strong></td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm z" value="${ec2Resource.resource.validFrom}" /></td>
                <td><strong>Request Valid Until:</strong></td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm z" value="${ec2Resource.resource.validUntil}" /></td>
            </tr>
            <tr>
                <td><strong>Key Pair Name:</strong></td>
                <td>${ec2Resource.resource.launchSpecification.keyName}</td>
                <td><strong>Security Group(s):</strong></td>
                <td><c:forEach items="${ec2Resource.resource.launchSpecification.allSecurityGroups}" var="securityGroup" varStatus="status">${securityGroup.groupId}<c:if test="${status.count != fn:length(ec2Resource.resource.launchSpecification.allSecurityGroups)}">,&nbsp;</c:if></c:forEach></td>
            </tr>
            <tr>
                <td><strong>Status Code:</strong></td>
                <td>${ec2Resource.resource.status.code}</td>
                <td><strong>Status Update Time:</strong></td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm z" value="${ec2Resource.resource.status.updateTime}" /></td>
            </tr>
            <tr>
                <td><strong>Status Message:</strong></td>
                <td colspan="3">${ec2Resource.resource.status.message}</td>
            </tr>
            <tr>
                <td><strong>Created:</strong></td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm z" value="${ec2Resource.resource.createTime}" /></td>
                <td><strong>State:</strong></td>
                <td>${ec2Resource.resource.state}</td>
            </tr>
            <tr>
                <td><strong>State Reason:</strong></td>
                <%--TODO: Don't know what's this --%>
                <td></td>
                <td><strong>Instance:</strong></td>
                <td>${ec2Resource.resource.instanceId}</td>
            </tr>
            <tr>
                <td><strong>Subnet:</strong></td>
                <td>${ec2Resource.resource.launchSpecification.subnetId}</td>
                <td><strong>Product Description:</strong></td>
                <td>${ec2Resource.resource.productDescription}</td>
            </tr>
            <tr>
                <td><strong>Kernel ID:</strong></td>
                <td>${ec2Resource.resource.launchSpecification.kernelId}</td>
                <td><strong>RAM Disk ID:</strong></td>
                <td>${ec2Resource.resource.launchSpecification.ramdiskId}</td>
            </tr>
            <tr>
                <td><strong>Launched availability zone:</strong></td>
                <td>${ec2Resource.resource.launchedAvailabilityZone}</td>
                <td><strong>IAM Role:</strong></td>
                <td>${fn:split(ec2Resource.resource.launchSpecification.iamInstanceProfile.arn, '/')[1]}</td>
            </tr>
		</tbody>
	</table>
</div>
</body>
</html>