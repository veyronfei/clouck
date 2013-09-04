<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="snapshots"/>
<title>Snapshots</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li><a href="${ctx}/accounts/${currentAccount.id}/ec2/snapshots/versions">Snapshots</a> <span class="divider">&gt;</span></li>
            <li class="active">
                <i class="icon-time"></i>
                <fmt:formatDate pattern="${datePattern}" value="${at}" />
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <div class="span13">
        <table id="snapshotsTable" class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Region</th>
                    <th>Name</th>
                    <th>Snapshot ID</th>
                    <th>Capacity</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Started</th>
                    <th>Progress</th>
                    <th>Time Detected</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ec2Resources}" var="ec2Resource">
                 <tr>
                    <td>${ec2Resource.region}</td>
                    <td>${ec2Resource.tag}</td>
                    <td>
                        <a href="${ctx}/accounts/${currentAccount.id}/ec2/snapshots/${ec2Resource.id}?at=${at.time}">${ec2Resource.resource.snapshotId}</a>
                    </td>
                    <td>${ec2Resource.resource.volumeSize} GiB</td>
                    <td>${ec2Resource.resource.description}</td>
                    <td>${ec2Resource.resource.state}</td>
                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${ec2Resource.resource.startTime}" /></td>
                    <td>${ec2Resource.resource.progress}</td>
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
        loadSummaryDataTable('snapshotsTable');
    });
</script>
</body>
</html>
