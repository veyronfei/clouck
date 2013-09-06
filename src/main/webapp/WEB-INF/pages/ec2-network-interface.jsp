<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="networkInterfaces"/>
<title>Network Interface</title>
</head>
<body>

<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li>
                <a href="${ctx}/accounts/${currentAccount.id}/ec2/networkInterfaces/versions">Network Interfaces</a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                <i class="icon-time"></i>
                <a href="${ctx}/accounts/${currentAccount.id}/ec2/networkInterfaces?at=${at.time}"><fmt:formatDate pattern="${datePattern}" value="${at}" /></a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                ${ec2Resource.resource.networkInterfaceId}
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <table class="table table-striped table-bordered table-hover">
        <thead>
            <tr>
                <th width="20%">Network Interface ID:</th>
                <th width="30%">
                <c:choose>
                    <c:when test="${empty ec2Resource.tag}">
                        ${ec2Resource.resource.networkInterfaceId}
                    </c:when>
                    <c:otherwise>
                        ${ec2Resource.tag}
                        (${ec2Resource.resource.networkInterfaceId})
                    </c:otherwise>
                </c:choose>
                </th>
                <th width="20%">Time Detected:</th>
                <th width="30%"><fmt:formatDate pattern="${datePattern}" value="${ec2Resource.timeDetected}"/></th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><strong>Subnet:</strong></td>
                <td>${ec2Resource.resource.subnetId}</td>
                <td><strong>VPC:</strong></td>
                <td>${ec2Resource.resource.vpcId}</td>
            </tr>
            <tr>
                <td><strong>Zone:</strong></td>
                <td>${ec2Resource.resource.availabilityZone}</td>
                <td><strong>MAC Address:</strong></td>
                <td>${ec2Resource.resource.macAddress}</td>
            </tr>
            <tr>
                <td><strong>Description:</strong></td>
                <td>${ec2Resource.resource.description}</td>
                <td><strong>Security Groups:</strong></td>
                <td><c:forEach items="${ec2Resource.resource.groups}" var="securityGroup" varStatus="status">${securityGroup.groupName}<c:if test="${status.count != fn:length(ec2Resource.resource.groups)}">,&nbsp;</c:if></c:forEach></td>
            </tr>
            <tr>
                <td><strong>Owner:</strong></td>
                <td>${ec2Resource.resource.ownerId}</td>
                <td><strong>Status:</strong></td>
                <td>${ec2Resource.resource.status}</td>
            </tr>
            <tr>
                <td><strong>Private IP:</strong></td>
                <td>${ec2Resource.resource.privateIpAddress}</td>
                <td><strong>Private DNS:</strong></td>
                <td>${ec2Resource.resource.privateDnsName}</td>
            </tr>
            <tr>
                <td><strong>Public DNS:</strong></td>
                <td></td>
                <td><strong>Secondary Private IPs:</strong></td>
                <td><c:forEach items="${ec2Resource.resource.privateIpAddresses}" var="privateIpAddress" varStatus="status">${privateIpAddress.privateIpAddress}<c:if test="${status.count != fn:length(ec2Resource.resource.privateIpAddresses)}">,&nbsp;</c:if></c:forEach></td>
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
                <td><strong>Attachment ID:</strong></td>
                <td>${ec2Resource.resource.attachment.attachmentId}</td>
            </tr>
            <tr>
                <td><strong>Instance:</strong></td>
                <td>${ec2Resource.resource.attachment.instanceId}</td>
                <td><strong>Attachment Owner:</strong></td>
                <td>${ec2Resource.resource.attachment.instanceOwnerId}</td>
            </tr>
            <tr>
                <td><strong>Device Index:</strong></td>
                <td>${ec2Resource.resource.attachment.deviceIndex}</td>
                <td><strong>Attachment Status:</strong></td>
                <td>${ec2Resource.resource.attachment.status}</td>
            </tr>
            <tr>
                <td><strong>Delete On Termination:</strong></td>
                <td>${ec2Resource.resource.attachment.deleteOnTermination}</td>
                <td><strong>Public IPs:</strong></td>
                <td>
                <c:forEach items="${ec2Resource.resource.privateIpAddresses}" var="privateIpAddress" varStatus="status">${privateIpAddress.association.publicIp}<c:if test="${status.count != fn:length(ec2Resource.resource.privateIpAddresses)}">,&nbsp;</c:if></c:forEach>
                </td>
            </tr>
            <tr>
                <td><strong>IP Owner ID:</strong></td>
                <td>${ec2Resource.resource.association.ipOwnerId}</td>
                <td><strong>Allocation ID:</strong></td>
                <td>${ec2Resource.resource.association.allocationId}</td>
            </tr>
            <tr>
                <td><strong>Association ID:</strong></td>
                <td colspan="3">${ec2Resource.resource.association.associationId}</td>
            </tr>
        </tbody>
    </table>
    <%@ include file="/common/tags.jspf"%>
</div>
</body>
</html>