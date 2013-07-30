<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="networkInterfaces"/>
<title>Network Interface</title>
</head>
<body>
<c:choose>
    <c:when test="${currentRegion.regions.name == null}">
        <c:set var="regionUrl" scope="request"></c:set>
    </c:when>
    <c:otherwise>
        <c:set var="regionUrl" scope="request">?region=${currentRegion.regions.name}</c:set>
    </c:otherwise>
</c:choose>
<!-- <div class="row">
  <div class="span3 offset9">
		<div class="btn-group">
			<button class="btn">Detail</button>
			<button class="btn">History</button>
		</div>
	</div>
</div> -->

<%-- <ul class="breadcrumb">
  <li><a href="#">EC2</a> <span class="divider">&#62;</span></li>
  <li><a href="/app/resources/volumes/${millis}/accounts/${currentAccount.id}">Volumes</a> <span class="divider">&#62;</span></li>
  <li class="active"> ${ec2Resource.resource.volumeId}</li>
</ul>
 --%>
<div class="row-fluid">
<%--     <div class="span3">
        <a class="btn" href="/app/ec2/instances/${millis}?account-id=${currentAccount.id}">&lt; Back</a>
    </div> --%>
    <div class="span6">
        <h4>EC2 Network Interface: ${ec2Resource.resource.imageId} @ <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${ec2Resource.timeDetected}" /></h4>
    </div>
    <div class="span1 offset5">
        <a class="btn" href="/app/accounts/${currentAccount.id}/ec2/versions/amis/${ec2Resource.uniqueId}${regionUrl}">${numOfEc2VersionMetas} versions</a>
    </div>
</div>

<div class="row-fluid">
	<table class="table table-striped table-bordered table-hover">
	    <thead>
            <tr>
                <th width="20%"></th>
                <th width="30%"></th>
                <th width="20%"></th>
                <th width="30%"></th>
            </tr>
        </thead>
		<tbody>
			<tr>
				<td><strong>AMI ID:</strong></td>
				<td>${ec2Resource.resource.imageId}</td>
	            <td><strong>AMI Name:</strong></td>
	            <td>${ec2Resource.resource.name}</td>
			</tr>
	        <tr>
	            <td><strong>Owner:</strong></td>
	            <td>${ec2Resource.resource.ownerId}</td>
	            <td><strong>Source:</strong></td>
	            <td>${ec2Resource.resource.ownerId}/${ec2Resource.resource.name}</td>
	        </tr>
	        <tr>
	            <td><strong>Status:</strong></td>
	            <td>${ec2Resource.resource.state}</td>
	            <td><strong>State Reason:</strong></td>
	            <td>${ec2Resource.resource.stateReason.message}</td>
	        </tr>
	        <tr>
	            <td><strong>Platform:</strong></td>
	            <td>${ec2Resource.resource.platform}</td>
	            <td><strong>Architecture:</strong></td>
	            <td>${ec2Resource.resource.architecture}</td>
	        </tr>
	        <tr>
	            <td><strong>Image Type:</strong></td>
	            <td>${ec2Resource.resource.imageType}</td>
                <td><strong>Description:</strong></td>
                <td>${ec2Resource.resource.description}</td>
	        </tr>
	        <tr>
                <td><strong>Root Device Name:</strong></td>
                <td>${ec2Resource.resource.rootDeviceName}</td>
                <td><strong>Root Device Type:</strong></td>
                <td>${ec2Resource.resource.rootDeviceType}</td>
            </tr>
            <tr>
                <td><strong>RAM Disk ID:</strong></td>
                <td>${ec2Resource.resource.ramdiskId}</td>
                <td><strong>Kernel ID:</strong></td>
                <td>${ec2Resource.resource.kernelId}</td>
            </tr>
            <tr>
                <td><strong>Product Codes:</strong></td>
                <td>
                <c:forEach items="${ec2Resource.resource.productCodes}" var="productCode">
                    ${productCode.productCodeId},&nbsp;
                </c:forEach>
                </td>
                <td><strong>Block Devices:</strong></td>
                <td>
                <c:forEach items="${ec2Resource.resource.blockDeviceMappings}" var="blockDeviceMapping">
                    ${blockDeviceMapping.deviceName}=${blockDeviceMapping.ebs.snapshotId}::${blockDeviceMapping.ebs.deleteOnTermination}:${blockDeviceMapping.ebs.volumeType},&nbsp;
                </c:forEach>
                </td>
            </tr>
		</tbody>
	</table>
</div>
</body>
</html>