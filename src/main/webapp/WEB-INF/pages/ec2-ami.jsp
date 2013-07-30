<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="amis"/>
<title>AMI</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li>
                <a href="/app/accounts/${currentAccount.id}/ec2/amis/versions">AMIs</a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                <i class="icon-time"></i>
                <a href="/app/accounts/${currentAccount.id}/ec2/amis?at=${at.time}"><fmt:formatDate pattern="${datePattern}" value="${at}" /></a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                ${ec2Resource.resource.imageId}
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
	<table class="table table-striped table-bordered table-hover">
	    <thead>
            <tr>
                <th width="20%">AMI ID:</th>
                <th width="30%">${ec2Resource.resource.imageId}</th>
                <th width="20%">Time Detected:</th>
                <th width="30%"><fmt:formatDate pattern="${datePattern}" value="${ec2Resource.timeDetected}"/></th>
            </tr>
        </thead>
		<tbody>
	        <tr>
	            <td><strong>AMI Name:</strong></td>
                <td>${ec2Resource.resource.name}</td>
	            <td><strong>Owner:</strong></td>
	            <td>${ec2Resource.resource.ownerId}</td>
	        </tr>
	        <tr>
	            <td><strong>Source:</strong></td>
	            <td>${ec2Resource.resource.ownerId}/${ec2Resource.resource.name}</td>
	            <td><strong>Status:</strong></td>
	            <td>${ec2Resource.resource.state}</td>
	        </tr>
	        <tr>
	            <td><strong>State Reason:</strong></td>
	            <td>${ec2Resource.resource.stateReason.message}</td>
	            <td><strong>Platform:</strong></td>
	            <td>
	            <c:choose>
                    <c:when test="${empty ec2Resource.resource.platform}">
                    Other Linux
                    </c:when>
                    <c:otherwise>
                    ${ec2Resource.resource.platform}
                    </c:otherwise>
                </c:choose>
                </td>
	        </tr>
	        <tr>
	            <td><strong>Architecture:</strong></td>
	            <td>${ec2Resource.resource.architecture}</td>
	            <td><strong>Image Type:</strong></td>
	            <td>${ec2Resource.resource.imageType}</td>
	        </tr>
	        <tr>
                <td><strong>Description:</strong></td>
                <td>${ec2Resource.resource.description}</td>
                <td><strong>Root Device Name:</strong></td>
                <td>${ec2Resource.resource.rootDeviceName}</td>
            </tr>
            <tr>
                <td><strong>Root Device Type:</strong></td>
                <td>${ec2Resource.resource.rootDeviceType}</td>
                <td><strong>RAM Disk ID:</strong></td>
                <td>${ec2Resource.resource.ramdiskId}</td>
            </tr>
            <tr>
                <td><strong>Kernel ID:</strong></td>
                <td>${ec2Resource.resource.kernelId}</td>
                <td><strong>Product Codes:</strong></td>
                <td><c:forEach items="${ec2Resource.resource.productCodes}" var="productCode" varStatus="status">${productCode.productCodeId}<c:if test="${status.count != fn:length(ec2Resource.resource.productCodes)}">,&nbsp;</c:if></c:forEach></td>
            </tr>
            <tr>
                <td><strong>Block Devices:</strong></td>
                <td colspan="3"><c:forEach items="${ec2Resource.resource.blockDeviceMappings}" var="blockDeviceMapping" varStatus="status"><c:choose><c:when test="${empty blockDeviceMapping.ebs}">${blockDeviceMapping.deviceName}=${blockDeviceMapping.virtualName}</c:when><c:otherwise>${blockDeviceMapping.deviceName}=${blockDeviceMapping.ebs.snapshotId}:${blockDeviceMapping.ebs.volumeSize}:${blockDeviceMapping.ebs.deleteOnTermination}:${blockDeviceMapping.ebs.volumeType}</c:otherwise></c:choose><c:if test="${status.count != fn:length(ec2Resource.resource.blockDeviceMappings)}">,<br></c:if></c:forEach></td>
            </tr>
		</tbody>
	</table>
	<br>
	<table class="table table-striped table-bordered table-hover">
	    <thead>
	        <caption>
	            <h4>Permissions</h4>
	        </caption>
	    </thead>
	    <tbody>
	        <tr>
	            <td width="50%"><strong>Visibility</strong></td>
	            <td width="50%">
	            <c:choose>
                    <c:when test="${resource.public}">
                    Public
                    </c:when>
                    <c:otherwise>
                    Private
                    </c:otherwise>
                </c:choose>
	            </td>
	        </tr>
            <tr>
                <td><strong>AWS Account Number</strong></td>
                <td><c:forEach items="${ec2Resource.launchPermissions}" var="launchPermission" varStatus="status">${launchPermission.userId}<c:if test="${status.count != fn:length(ec2Resource.launchPermissions)}">,&nbsp;</c:if></c:forEach></td>
            </tr>
	    </tbody>
	</table>
	<%@ include file="/common/tags.jspf"%>
</div>
</body>
</html>