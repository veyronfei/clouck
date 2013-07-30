<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="spotInstanceRequests"/>
<title>Spot Instance Requests</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li><a href="/app/accounts/${currentAccount.id}/ec2/spotInstanceRequests/versions">Spot Requests</a> <span class="divider">&gt;</span></li>
            <li class="active">
	            <i class="icon-time"></i>
	            <fmt:formatDate pattern="${datePattern}" value="${at}" />
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <div class="span13">
        <table id="spotInstanceRequestsTable" class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Region</th>
                    <th>Request ID</th>
                    <th>Max Price</th>
                    <th>AMI ID</th>
                    <th>Instance</th>
                    <th>Type</th>
                    <th>State</th>
                    <th>Status</th>
                    <th>Time Detected</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ec2Resources}" var="ec2Resource">
                 <tr>
                    <td>${ec2Resource.region}</td>
                    <td>
                        <a href="/app/accounts/${currentAccount.id}/ec2/spotInstanceRequests/${ec2Resource.id}?at=${at.time}">${ec2Resource.resource.spotInstanceRequestId}</a>
                    </td>
                    <td>$<fmt:formatNumber type="number" maxFractionDigits="3" value="${ec2Resource.resource.spotPrice}" /></td>
                    <td>${ec2Resource.resource.launchSpecification.imageId}</td>
                    <td>${ec2Resource.resource.instanceId}</td>
                    <td>${ec2Resource.resource.launchSpecification.instanceType}</td>
                    <td>${ec2Resource.resource.state}</td>
                    <td>${ec2Resource.resource.status.code}</td>
                    <td>
                        <fmt:formatDate pattern="${datePattern}" value="${ec2Resource.timeDetected}" />
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function() {
    loadSummaryDataTable('spotInstanceRequestsTable');
});
</script>
</body>
</html>
