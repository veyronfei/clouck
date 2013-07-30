<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="keyPairs"/>
<title>Key Pairs</title>
</head>
<body>
<c:choose>
    <c:when test="${currentRegion.regions.name == null}">
        <c:set var="regionUrl"></c:set>
    </c:when>
    <c:otherwise>
        <c:set var="regionUrl">?region=${currentRegion.regions.name}</c:set>
    </c:otherwise>
</c:choose>
<div class="row-fluid">
    <div class="span13">
        <div class="span6">
	        <h4>Key Pairs @ ${at}</h4>
        </div>
        <div class="span1 offset5">
            <a class="btn" href="/app/accounts/${currentAccount.id}/ec2/versions/keyPairs${regionUrl}">${numOfEc2VersionMetas} versions</a>
        </div>
        <!-- <div id="instanceChart" style="width:100%; height:100px;"></div> -->
    </div>
</div>

<div class="row-fluid" style="padding-top: 20px;">
    <div class="span13">
        <table id="placementGroupsTable" class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <c:if test="${currentRegion.regions.name == null}">
	                    <th>Region</th>
                    </c:if>
                    <th>Key Pair Name</th>
                    <th>Fingerprint</th>
                    <th>Time Detected</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ec2Resources}" var="ec2Resource">
                <c:set var="resource" value="${ec2Resource.resource}"/>
                 <tr>
                    <c:if test="${currentRegion.regions.name == null}">
	                    <td>${ec2Resource.region}</td>
                    </c:if>
                    <td>
                        <a href="/app/accounts/${currentAccount.id}/ec2/keyPairs/${ec2Resource.id}${regionUrl}">${resource.keyName}</a>
                    </td>
                    <td>${resource.keyFingerprint}</td>
                    <td>
                        <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${ec2Resource.timeDetected}" />
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
		/* loadOverviewChart('instanceChart', 'instances', '${currentAccount.id}', '${millis}'); */
        $('#placementGroupsTable').dataTable({
            "sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
            "sPaginationType": "bootstrap",
            "oLanguage": {
                "sLengthMenu": "_MENU_ records per page"
            }
        });
	});
</script>
</body>
</html>
