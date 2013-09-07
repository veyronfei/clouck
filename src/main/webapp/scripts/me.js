var highChart, oTable;
var options = {
    chart: {
        type: 'areaspline',
        plotBorderColor: '#666',
        plotBorderWidth: 1
//        spacingTop: 5,
//        spacingBottom: 5,
//        spacingLeft: 5,
//        spacingRight: 5
    },
    credits: {
        enabled: false
    },
    legend: {
        enabled: false
    },
    xAxis: {
        type: 'datetime',
        minPadding: 0,
        maxPadding: 0,
        dateTimeLabelFormats: {
            millisecond: '%H:%M',
            second: '%H:%M',
            minute: '%H:%M',
            hour: '%H:%M',
            day: '%e. %b',
            week: '%e. %b',
            month: '%b \'%y',
            year: '%Y'
        }
    },
    yAxis: {
        title: null,
        allowDecimals: false
    },
    title: null
};

function filterVersions(accountId, resourceType, ctx) {
	var startingDateTime = $("#endingDateTime").val();
	var uniqueId = $("#uniqueId").val().trim();
	$("#uniqueId").val(uniqueId);
	var region = $("#region option:selected").val();
	var query = "";
	var hasValue = false;
	if (startingDateTime) {
		var time = Date.parse(startingDateTime);
		query += "?ending=" + time;
		hasValue = true;
	}
	if (uniqueId) {
		if (hasValue) {
			query += "&uniqueId=" + uniqueId;
		} else {
			query += "?uniqueId=" + uniqueId;
			hasValue = true;
		}
	}
	if (region) {
		if (hasValue) {
			query += "&region=" + region;
		} else {
			query += "?region=" + region;
		} 
	}
	oTable.fnReloadAjax(ctx + '/rest/dataTable/accounts/' + accountId + '/ec2/versions/' + resourceType + query);
}

function loadSummaryDataTable(summaryDataTableId) {
    $('#' + summaryDataTableId).dataTable({
    	"bLengthChange": false,
        "sPaginationType": "bootstrap",
    });
}

function clearFilter(accountId, resourceType, ctx) {
	$("#endingDateTime").val('');
	$("#uniqueId").val('');
	$("#region")[0].selectedIndex = 0;
	oTable.fnReloadAjax(ctx + '/rest/dataTable/accounts/' + accountId + '/ec2/versions/' + resourceType);
}

function addNewAccount(showClose) {
    if (showClose) {
    	$("#myModal").modal({});
    } else {
    	$("#myModal").modal({
    		backdrop: 'static',
    		keyboard: false
    	});
    }
}

function changeRegion(accountId, regionEndpoint) {
	if (regionEndpoint) {
		window.location.href = "/accounts/" + accountId + "/ec2/overview?region=" + regionEndpoint;
	} else {
		window.location.href = "/accounts/" + accountId + "/ec2/overview";
	}
}

function deleteAccount(accountId) {
	$.ajax({
		url: '/rest/accounts/' + accountId,
		type: 'DELETE',
		success: function(result) {
		}
	});
	window.location.href = "/accounts";
}

function loadPage(variableName, currentAccountId, timeStamp) {
    oTable = $('#table_id111').dataTable();
    var d = new Date();
    d.setTime(timeStamp);
    var curr_min = d.getMinutes();
    var curr_hour = d.getHours();
    var curr_date = d.getDate();
    var curr_month = d.getMonth() + 1; // Months are zero based
    var curr_year = d.getFullYear();
    $('#datepicker11').datetimepicker({
        onClose: function() {
            var dateObject = $(this).datepicker('getDate111');
            if (dateObject != null) {
                timeStamp = dateObject.getTime();
                createChart(variableName, timeStamp, currentAccountId);
            }
        },
        hour:curr_hour,
        minute:curr_min
    });
    $('#datepicker11').val(curr_date + "/" + curr_month + "/" + curr_year + " " + curr_hour + ":" + curr_min);
//    
//    oTable = $('#table_id').dataTable({
//        "bProcessing": true,
//        "bServerSide": true,
//            "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
//                $('td:eq(2)', nRow).html('<a href="view.php?comic=' + nRow[2] + '">' +
//                        nRow[2] + '</a>');
//                return nRow;
//            }
//    });
    
    //loadSortableTable(variableName, currentAccountId, timeStamp);
    createChart(variableName, timeStamp, currentAccountId);
}

function showAllRegions(variableName, millisecond, currentAccountId) {
    oTable.fnReloadAjax('/rest/dataTable/' + variableName + '/' + millisecond + '/accounts/' + currentAccountId);
}

function showCurrentRegion(ec2VersionId) {
    oTable.fnReloadAjax('/rest/dataTable/' + ec2VersionId);
}

function createChart(variableName, timeStamp, currentAccountId) {
    $.getJSON('/rest/highCharts/' + variableName + '/' + timeStamp + '/accounts/' + currentAccountId, function(data) {
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'container',
                type: 'column'
                //height: 200,
                //width: 900
            },
            title: {
                text: 'Recent 20 ' + variableName + ' History'
            },
            xAxis: {
                categories : data.categories
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Total ' + variableName
                },
                stackLabels: {
                    enabled: true,
                    style: {
                        fontWeight: 'bold',
                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                    }
                }
            },
            tooltip: {
                useHTML: true,
                formatter: function() {
                    return '<b>' + this.x + '</b><br/>' +
                        this.series.name + ': ' + this.y + '<br/>' +
                        'Total: ' + this.point.stackTotal + '<br/>'+
                        '<a href="#" onclick="showCurrentRegion(\'' + this.point.id + '\')">Show selected region</a>' + '<br/>'+
                        '<a href="#" onclick="showAllRegions(\'' + variableName + '\',\'' +  this.point.millisecond + '\',\'' + currentAccountId + '\')">Show all regions</a>';
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
                    },
                    pointWidth: 20
                },
                series: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    point: {
                        events: {
                            click: function() {
                                showCurrentRegion(this.id);
                            }
//                            click: function() {
//                                console.log(this);
//                                for (var i = 0; i < this.series.data.length; i++) {
//                                    this.series.data[i].update({ color: '#ECB631' }, true, false);
//                                }
//                                this.update({ color: '#f00' }, true, false)
//                            }
                        }
                    }
                }
            },
            series : data.series
        });
    });
}

function loadOverviewChart(chartId, variableName, currentAccountId, millis, regionEndpoint) {
	var url = '/rest/chart-data/' + variableName + '/' + millis + '?account-id=' + currentAccountId;
	if (regionEndpoint) {
		url = url + '&region=' + regionEndpoint;
	}

    $.getJSON(url, function(data) {
        options.chart.renderTo = chartId;
        options.series = data.series;
//        options.chart.events = {
//            click : function() {location.href = '/ec2/' + variableName + '/' + millis + '?account-id=' + currentAccountId + '&region=' + regionEndpoint}
//        };
        var chart = new Highcharts.Chart(options);
    });
}