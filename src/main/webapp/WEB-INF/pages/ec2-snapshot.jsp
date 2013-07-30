<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="snapshots"/>
<title>Snapshot</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li>
                <a href="/app/accounts/${currentAccount.id}/ec2/snapshots/versions">Snapshots</a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                <i class="icon-time"></i>
                <a href="/app/accounts/${currentAccount.id}/ec2/snapshots?at=${at.time}"><fmt:formatDate pattern="${datePattern}" value="${at}" /></a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                ${ec2Resource.resource.snapshotId}
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
	<table class="table table-striped table-bordered table-hover">
	    <thead>
            <tr>
                <th width="20%">Snapshot ID:</th>
                <th width="30%">${ec2Resource.resource.snapshotId}</th>
                <th width="20%">Time Detected:</th>
                <th width="30%"><fmt:formatDate pattern="${datePattern}" value="${ec2Resource.timeDetected}"/></th>
            </tr>
        </thead>
		<tbody>
			<tr>
				<td><strong>Status:</strong></td>
				<td>${ec2Resource.resource.state}</td>
	            <td><strong>Progress:</strong></td>
	            <td>${ec2Resource.resource.progress}</td>
			</tr>
	        <tr>
	            <td><strong>Volume:</strong></td>
	            <td>${ec2Resource.resource.volumeId}</td>
	            <td><strong>Capacity:</strong></td>
	            <td>${ec2Resource.resource.volumeSize} GiB</td>
	        </tr>
	        <tr>
	            <td><strong>Owner:</strong></td>
	            <td>${ec2Resource.resource.ownerId}</td>
	            <td><strong>Started:</strong></td>
	            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm z" value="${ec2Resource.resource.startTime}"/></td>
	        </tr>
	        <tr>
	            <td><strong>Description:</strong></td>
	            <td>${ec2Resource.resource.description}</td>
	            <td><strong>Product Codes::</strong></td>
	            <td><c:forEach items="${ec2Resource.productCodes}" var="productCode" varStatus="status">${productCode.productCodeId}<c:if test="${status.count != fn:length(ec2Resource.productCodes)}"><br></c:if></c:forEach></td>
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
                <td><c:forEach items="${ec2Resource.createVolumePermissions}" var="createVolumePermission" varStatus="status">${createVolumePermission.userId}<c:if test="${status.count != fn:length(ec2Resource.createVolumePermissions)}">,&nbsp;</c:if></c:forEach></td>
            </tr>
        </tbody>
    </table>
	<%@ include file="/common/tags.jspf"%>
</div>
</body>
</html>