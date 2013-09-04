<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="serviceMenu" content="ec2"/>
<meta name="resourceMenu" content="keyPairs"/>
<title>Key Pairs History</title>
<style type="text/css">
.dataTables_wrapper .row-fluid:first-child {
    display: none;
}
</style>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="id-filter">Region:</div>
        <select id="region" class="span9">
        <c:forEach var="region" items="${regions}">
            <option value="${region.regions.name}">${region.desc}</option>
        </c:forEach>
        </select>
    </div>
    <div class="span3">
        <div class="id-filter">Key Pair Name:</div>
        <input id="uniqueId" type="text"class="input-medium"/>
    </div>
    <div class="span3">
        <div class="id-filter">Ending:</div>
        <div id="datetimepicker" class="input-append date">
            <input id="endingDateTime" class="input-medium" data-format="yyyy-MM-dd hh:mm:ss" type="text"></input>
            <span class="add-on"> <i data-time-icon="icon-time" data-date-icon="icon-calendar"> </i></span>
        </div>
    </div>
    <div class="span2" style="display: inline;">
        <button id ="filter" class="btn btn-primary" type="button" onclick="javascript:filterVersions('${currentAccount.id}', 'instances')">Filter</button>
        <button class="btn btn-primary" type="button" onclick="javascript:clearFilter('${currentAccount.id}', 'instances')">Clear</button>
    </div>
</div>

<div class="row-fluid">
	<table class="table table-striped table-hover">
		<thead>
			<tr>
			    <c:if test="${currentRegion.regions.name == null}">
                    <th width="10%">Region</th>
                </c:if>
				<th width="10%">Time Detected</th>
				<th width="40%">Events</th>
				<th width="10%">Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ec2VersionMetaReps}" var="ec2VersionMetaRep">
			<tr>
			    <c:if test="${currentRegion.regions.name == null}">
					<td>${ec2VersionMetaRep.region.shortDesc}</td>
                </c:if>
				<td>${ec2VersionMetaRep.timeDetected}</td>
				<td>
				<c:forEach items="${ec2VersionMetaRep.reps}" var="rep">
				${rep.message}<br>
				</c:forEach>
				</td>
				<td>
				    <a class="btn" href="${ctx}/accounts/${currentAccount.id}/ec2/keyPairs?at=${ec2VersionMetaRep.millis}${regionUrl}">Browse</a>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        /* loadOverviewChart('instanceChart', 'instances', '${currentAccount.id}', '${millis}'); */
/*         $('#instancesHistoryTable').dataTable({
            "sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
            "sPaginationType": "bootstrap",
            "oLanguage": {
                "sLengthMenu": "_MENU_ records per page"
            },
            "aaSorting": [[0,'desc']]
        }); */
    });
</script>
</body>
</html>
