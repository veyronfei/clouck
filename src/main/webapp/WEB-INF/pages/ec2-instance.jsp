<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="instances"/>
<title>Instance</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li>
                <a href="${ctx}/accounts/${currentAccount.id}/ec2/instances/versions">Instances</a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                <i class="icon-time"></i>
                <a href="${ctx}/accounts/${currentAccount.id}/ec2/instances?at=${at.time}"><fmt:formatDate pattern="${datePattern}" value="${at}" /></a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                ${ec2Resource.resource.instanceId}
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
	<table class="table table-striped table-bordered table-hover">
	    <thead>
            <tr>
                <th width="20%">Instance ID:</th>
                <th width="30%">
                <c:choose>
                    <c:when test="${empty ec2Resource.tag}">
                        ${ec2Resource.resource.instanceId}
                    </c:when>
                    <c:otherwise>
                        ${ec2Resource.tag}
                        (${ec2Resource.resource.instanceId})
                    </c:otherwise>
                </c:choose>
                </th>
                <th width="20%">Time Detected:</th>
                <th width="30%"><fmt:formatDate pattern="${datePattern}" value="${ec2Resource.timeDetected}"/></th>
            </tr>
        </thead>
		<tbody>
			<tr>
				<td><strong>AMI:</strong></td>
				<td>${ec2Resource.resource.imageId}</td>
	            <td><strong>Zone:</strong></td>
	            <td>${ec2Resource.resource.placement.availabilityZone}</td>
			</tr>
	        <tr>
	            <td><strong>Security Groups:</strong></td>
	            <td><c:forEach items="${ec2Resource.resource.securityGroups}" var="securityGroup" varStatus="status">${securityGroup.groupName}<c:if test="${status.count != fn:length(ec2Resource.resource.securityGroups)}">,&nbsp;</c:if></c:forEach></td>
	            <td><strong>Type:</strong></td>
	            <td>${ec2Resource.resource.instanceType}</td>
	        </tr>
	        <tr>
	            <td><strong>State:</strong></td>
	            <td>${ec2Resource.resource.state.name}</td>
	            <td><strong>Owner:</strong></td>
	            <td>${currentAccount.accountNumber}</td>
	        </tr>
	        <tr>
	            <td><strong>VPC ID:</strong></td>
	            <td>${ec2Resource.resource.vpcId}</td>
	            <td><strong>Subnet ID:</strong></td>
	            <td>${ec2Resource.resource.subnetId}</td>
	        </tr>
	        <tr>
	            <td><strong>Source/Dest. Check:</strong></td>
	            <td>
	            <c:if test="${ec2Resource.resource.sourceDestCheck != null}">
					<c:choose>
					    <c:when test="${ec2Resource.resource.sourceDestCheck}">
					        enabled
					    </c:when>
					    <c:otherwise>
					        disabled
					    </c:otherwise>
					</c:choose>
	            </c:if>
	            </td>
                <td><strong>Virtualization:</strong></td>
                <td>${ec2Resource.resource.virtualizationType}</td>
	        </tr>
	        <tr>
                <td><strong>Placement Group:</strong></td>
                <td>${ec2Resource.resource.placement.groupName}</td>
                <td><strong>Reservation:</strong></td>
                <td>${ec2Resource.reservationId}</td>
            </tr>
            <tr>
                <td><strong>RAM Disk ID:</strong></td>
                <td>${ec2Resource.resource.ramdiskId}</td>
                <td><strong>Platform:</strong></td>
                <td>${ec2Resource.resource.platform}</td>
            </tr>
            <tr>
                <td><strong>Key Pair Name:</strong></td>
                <td>${ec2Resource.resource.keyName}</td>
                <td><strong>Kernel ID:</strong></td>
                <td>${ec2Resource.resource.kernelId}</td>
            </tr>
            <tr>
                <td><strong>Monitoring:</strong></td>
                <td>
                <c:choose>
                    <c:when test="${ec2Resource.resource.monitoring.state == 'enabled'}">
                        detailed
                    </c:when>
                    <c:otherwise>
		                basic
                    </c:otherwise>
                </c:choose>
                </td>
                <td><strong>AMI Launch Index:</strong></td>
                <td>${ec2Resource.resource.amiLaunchIndex}</td>
            </tr>
            <tr>
                <td><strong>Elastic IP:</strong></td>
                <td>${ec2Resource.resource.publicIpAddress}</td>
                <td><strong>Root Device:</strong></td>
                <td>${fn:replace(ec2Resource.resource.rootDeviceName, '/dev/', '')}</td>
            </tr>
            <tr>
                <td><strong>Root Device Type:</strong></td>
                <td>${ec2Resource.resource.rootDeviceType}</td>
                <td><strong>Tenancy:</strong></td>
                <td>${ec2Resource.resource.placement.tenancy}</td>
            </tr>
            <tr>
                <td><strong>IAM Role:</strong></td>
                <td>${fn:split(ec2Resource.resource.iamInstanceProfile.arn, '/')[1]}</td>
                <td><strong>Lifecycle:</strong></td>
                <td>
                <c:choose>
                    <c:when test="${ec2Resource.resource.instanceLifecycle != null}">
                        ${ec2Resource.resource.instanceLifecycle}
                    </c:when>
                    <c:otherwise>
                        normal
                    </c:otherwise>
                </c:choose>
                </td>
            </tr>
            <tr>
                <td><strong>EBS Optimized:</strong></td>
                <td>${ec2Resource.resource.ebsOptimized}</td>
                <td><strong>Block Devices:</strong></td>
                <td><c:forEach items="${ec2Resource.resource.blockDeviceMappings}" var="blockDeviceMapping" varStatus="status">${fn:replace(blockDeviceMapping.deviceName, '/dev/', '')}<c:if test="${status.count != fn:length(ec2Resource.resource.blockDeviceMappings)}"><br></c:if></c:forEach></td>
            </tr>
            <tr>
                <td><strong>Network Interfaces:</strong></td>
                <td><c:forEach items="${ec2Resource.resource.networkInterfaces}" var="networkInterface" varStatus="status">eth${networkInterface.attachment.deviceIndex}<c:if test="${status.count != fn:length(ec2Resource.resource.networkInterfaces)}"><br></c:if></c:forEach></td>
                <td><strong>Public DNS:</strong></td>
                <td>${ec2Resource.resource.publicDnsName}</td>
            </tr>
            <tr>
                <td><strong>Private DNS:</strong></td>
                <td>${ec2Resource.resource.privateDnsName}</td>
                <%-- TODO: Fixed it later, have no idea how does it look like --%>
                <td><strong>Product Codes:</strong></td>
                <td><c:forEach items="${ec2Resource.resource.productCodes}" var="productCode" varStatus="status">${productCode.productCodeId}<c:if test="${status.count != fn:length(ec2Resource.resource.productCodes)}"><br></c:if></c:forEach></td>
            </tr>
            <tr>
                <td><strong>Private IPs:</strong></td>
                <td>
	                <c:choose>
	                    <c:when test="${fn:length(ec2Resource.resource.networkInterfaces) == 0}">
	                        ${ec2Resource.resource.privateIpAddress}
	                    </c:when>
	                    <c:otherwise>
	                        <c:forEach items="${ec2Resource.resource.networkInterfaces}" var="networkInterface" varStatus="status">${networkInterface.privateIpAddress}<c:if test="${status.count != fn:length(ec2Resource.resource.networkInterfaces)}">,&nbsp;</c:if></c:forEach>
	                    </c:otherwise>
	                </c:choose>
                </td>
                <td><strong>Secondary Private IPs:</strong></td>
                <td>
                    <c:forEach items="${ec2Resource.resource.networkInterfaces}" var="networkInterface" varStatus="networkInterfaceStatus"><c:forEach items="${networkInterface.privateIpAddresses}" var="privateIpAddress" varStatus="status">${privateIpAddress.privateIpAddress}<c:if test="${networkInterfaceStatus.count != fn:length(ec2Resource.resource.networkInterfaces) || status.count != fn:length(networkInterface.privateIpAddresses)}">,&nbsp;</c:if></c:forEach></c:forEach>
                </td>
            </tr>
            <tr>
                <td><strong>Launch Time:</strong></td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm z" value="${ec2Resource.resource.launchTime}"/></td>
                <td><strong>State Transition Reason:</strong></td>
                <td>${ec2Resource.resource.stateReason.message}</td>
            </tr>
            <tr>
                <td><strong>Termination Protection:</strong></td>
                <td colspan="3">
                <c:choose>
                    <c:when test="${ec2Resource.terminationProtection}">
                        Enabled
                    </c:when>
                    <c:otherwise>
                        Disabled
                    </c:otherwise>
                </c:choose>
            </tr>
		</tbody>
	</table>
    <%@ include file="/common/tags.jspf"%>
    <%-- TODO: How to display uer data, does user care? --%>
</div>
</body>
</html>