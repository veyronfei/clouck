<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="elasticIPs"/>
<title>Elastic IPs</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li><a href="/app/accounts/${currentAccount.id}/ec2/elasticIPs/versions">Elastic IPs</a> <span class="divider">&gt;</span></li>
            <li class="active">
                <i class="icon-time"></i>
                <fmt:formatDate pattern="${datePattern}" value="${at}" />
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <div class="span13">
        <table id="elasticIPsTable" class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Region</th>
                    <th>Address</th>
                    <th>Instance ID</th>
                    <th>ENI ID</th>
                    <th>Scope</th>
                    <th>Time Detected</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ec2Resources}" var="ec2Resource">
                <c:set var="resource" value="${ec2Resource.resource}"/>
                 <tr>
                    <td>${ec2Resource.region}</td>
                    <td>
                        <a href="/app/accounts/${currentAccount.id}/ec2/elasticIPs/${ec2Resource.id}?at=${at.time}">${resource.publicIp}</a>
                    </td>
                    <td>${resource.instanceId}</td>
                    <td>${resource.networkInterfaceId}</td>
                    <td>${resource.domain}</td>
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
        loadSummaryDataTable('elasticIPsTable');
    });
</script>
</body>
</html>
