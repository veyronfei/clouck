<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="keyPairs"/>
<title>Key Pair</title>
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
  <li><a href="/resources/volumes/${millis}/accounts/${currentAccount.id}">Volumes</a> <span class="divider">&#62;</span></li>
  <li class="active"> ${ec2Resource.resource.volumeId}</li>
</ul>
 --%>
<div class="row-fluid">
<%--     <div class="span3">
        <a class="btn" href="/ec2/instances/${millis}?account-id=${currentAccount.id}">&lt; Back</a>
    </div> --%>
    <div class="span6">
        <h4>EC2 Key Pair: ${ec2Resource.resource.keyName} @ <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${ec2Resource.timeDetected}" /></h4>
    </div>
    <div class="span1 offset5">
        <a class="btn" href="/accounts/${currentAccount.id}/ec2/versions/keyPairs/${ec2Resource.uniqueId}${regionUrl}">${numOfEc2VersionMetas} versions</a>
    </div>
</div>

<div class="row-fluid">
	<table class="table table-striped table-bordered table-hover">
	    <thead>
            <tr>
                <th width="30%"></th>
                <th width="70%"></th>
            </tr>
        </thead>
		<tbody>
			<tr>
				<td><strong>Key Pair Name:</strong></td>
				<td>${ec2Resource.resource.keyName}</td>
			</tr>
			<tr>
	            <td><strong>Fingerprint:</strong></td>
	            <td>${ec2Resource.resource.keyFingerprint}</td>
			</tr>
		</tbody>
	</table>
</div>
</body>
</html>