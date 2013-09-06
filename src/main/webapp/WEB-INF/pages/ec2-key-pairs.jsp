<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="keyPairs"/>
<title>Key Pairs</title>
</head>
<body>
<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li><a href="${ctx}/accounts/${currentAccount.id}/ec2/keyPairs/versions">Key Pairs</a> <span class="divider">&gt;</span></li>
            <li class="active">
                <i class="icon-time"></i>
                <fmt:formatDate pattern="${datePattern}" value="${at}" />
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <div class="span13">
        <table id="keyPairsTable" class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Region</th>
                    <th>Key Pair Name</th>
                    <th>Fingerprint</th>
                    <th>Time Detected</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ec2Resources}" var="ec2Resource">
                 <tr>
                    <td>${ec2Resource.region}</td>
                    <td>
                        <a href="${ctx}/accounts/${currentAccount.id}/ec2/keyPairs/${ec2Resource.id}?at=${at.time}">${ec2Resource.resource.keyName}</a>
                    </td>
                    <td>${ec2Resource.resource.keyFingerprint}</td>
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
        loadSummaryDataTable('keyPairsTable');
    });
</script>
</body>
</html>
