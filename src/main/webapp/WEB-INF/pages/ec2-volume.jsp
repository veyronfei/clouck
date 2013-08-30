<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="volumes"/>
<title>Volume</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li>
                <a href="/accounts/${currentAccount.id}/ec2/volumes/versions">Volumes</a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                <i class="icon-time"></i>
                <a href="/accounts/${currentAccount.id}/ec2/volumes?at=${at.time}"><fmt:formatDate pattern="${datePattern}" value="${at}" /></a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                ${ec2Resource.resource.volumeId}
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
	<table class="table table-striped table-bordered table-hover">
	    <thead>
            <tr>
                <th width="20%">Volume ID:</th>
                <th width="30%">${ec2Resource.resource.volumeId}</th>
                <th width="20%">Time Detected:</th>
                <th width="30%"><fmt:formatDate pattern="${datePattern}" value="${ec2Resource.timeDetected}"/></th>
            </tr>
        </thead>
		<tbody>
			<tr>
				<td><strong>Capacity:</strong></td>
				<td>${ec2Resource.resource.size} GiB</td>
	            <td><strong>Snapshot:</strong></td>
	            <td>${ec2Resource.resource.snapshotId}</td>
			</tr>
	        <tr>
	            <td><strong>Created:</strong></td>
	            <td><fmt:formatDate pattern="${datePattern}" value="${ec2Resource.resource.createTime}" /></td>
	            <td><strong>Zone:</strong></td>
	            <td>${ec2Resource.resource.availabilityZone}</td>
	        </tr>
	        <tr>
	            <td><strong>State:</strong></td>
	            <td>${ec2Resource.resource.state}</td>
	            <td><strong>Attachment:</strong></td>
	            <td>
	                <c:forEach items="${ec2Resource.resource.attachments}" var="attachment">
                   ${attachment.instanceId}:${attachment.device} (${attachment.state})
                   </c:forEach>
	            </td>
	        </tr>
	        <tr>
	            <td><strong>Volume Type:</strong></td>
	            <td>${ec2Resource.resource.volumeType}</td>
	            <td><strong>IOPS:</strong></td>
	            <td>${ec2Resource.resource.iops}</td>
	        </tr>
	        <tr>
	            <td><strong>Product Codes:</strong></td>
	            <td><c:forEach items="${ec2Resource.productCodes}" var="productCode" varStatus="status">${productCode.productCodeId}<c:if test="${status.count != fn:length(ec2Resource.productCodes)}"><br></c:if></c:forEach></td>
	            <td><strong>Auto-Enable IO:</strong></td>
	            <td>
                <c:choose>
                    <c:when test="${ec2Resource.autoEnableIO}">
                        Enabled
                    </c:when>
                    <c:otherwise>
                        Disabled
                    </c:otherwise>
                </c:choose>
	            </td>
	        </tr>
		</tbody>
	</table>
	<%@ include file="/common/tags.jspf"%>
</div>
</body>
</html>