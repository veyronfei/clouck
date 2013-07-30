<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="loadBalancers"/>
<title>Load Balancers</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li><a href="/app/accounts/${currentAccount.id}/ec2/loadBalancers/versions">Load Balancers</a> <span class="divider">&gt;</span></li>
            <li class="active">
                <i class="icon-time"></i>
                <fmt:formatDate pattern="${datePattern}" value="${at}" />
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <div class="span13">
        <table id="loadBalancersTable" class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Region</th>
                    <th>Load Balancer Name</th>
                    <th>DNS Name</th>
                    <th>Port Configuration</th>
                    <th>Availability Zones</th>
                    <th>Time Detected</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ec2Resources}" var="ec2Resource">
                 <tr>
                    <td>${ec2Resource.region}</td>
                    <td>
                        <a href="/app/accounts/${currentAccount.id}/ec2/loadBalancers/${ec2Resource.id}?at=${at.time}">${ec2Resource.resource.loadBalancerName}</a>
                    </td>
                    <td>${ec2Resource.resource.DNSName}</td>
                    <td>
                    <c:forEach items="${ec2Resource.resource.listenerDescriptions}" var="listenerDescription">
                    ${listenerDescription.listener.loadBalancerPort} (${listenerDescription.listener.protocol}) forwarding to ${listenerDescription.listener.instancePort} (${listenerDescription.listener.instanceProtocol}, ${listenerDescription.listener.SSLCertificateId}),
                    </c:forEach>
                    </td>
                    <td>
                    <c:forEach items="${ec2Resource.resource.availabilityZones}" var="availabilityZone" varStatus="status">
                        ${availabilityZone}<c:if test="${status.count != fn:length(ec2Resource.resource.availabilityZones)}">,&nbsp;</c:if>
                    </c:forEach>
                    </td>
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
        loadSummaryDataTable('loadBalancersTable');
    });
</script>
</body>
</html>
