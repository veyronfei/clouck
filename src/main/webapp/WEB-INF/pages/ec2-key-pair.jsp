<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="keyPairs"/>
<title>Key Pair</title>
</head>
<body>

<div class="row-fluid">
    <div class="span13">
        <ul class="breadcrumb">
            <li>
                <a href="${ctx}/accounts/${currentAccount.id}/ec2/keyPairs/versions">Key Pairs</a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                <i class="icon-time"></i>
                <a href="${ctx}/accounts/${currentAccount.id}/ec2/keyPairs?at=${at.time}"><fmt:formatDate pattern="${datePattern}" value="${at}" /></a>
                <span class="divider">&gt;</span>
            </li>
            <li class="active">
                ${ec2Resource.resource.keyName}
            </li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <table class="table table-striped table-bordered table-hover">
        <thead>
            <tr>
                <th width="20%">Key Pair Name:</th>
                <th width="30%">${ec2Resource.resource.keyName}</th>
                <th width="20%">Time Detected:</th>
                <th width="30%"><fmt:formatDate pattern="${datePattern}" value="${ec2Resource.timeDetected}"/></th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><strong>Fingerprint:</strong></td>
                <td colspan="3">${ec2Resource.resource.keyFingerprint}</td>
            </tr>
        </tbody>
    </table>
</div>



</body>
</html>