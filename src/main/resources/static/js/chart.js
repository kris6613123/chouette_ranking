// chart 관련 js //

function makeLineChart( element, labels, dataList ) {
    let chart = new Chart( element, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [
                {
                    data: dataList,
                    // data: [
                    //     { x: '2023-12-01', y: 10 },
                    //     { x: '2023-12-06', y: 5 },
                    //     { x: '2024-01-01', y: 4 },
                    //     { x: '2024-02-01', y: 4 },
                    //
                    // ],
                    label: "Rank",
                    borderColor: "#D6A867",
                    borderWidth: 1.5
                }
            ],
        },
        options: {
            responsive: true,
            spanGaps: true, // null 건너뒤고 선을 이음
            plugins: {
                tooltip: {
                    backgroundColor: 'rgba(68,68,68,0.8)',
                    bodyFont: {color: '#000'},
                    titleFont: {}
                },
                legend: {
                    labels: {
                        font: {

                        }
                    }
                }
            },
            scales: {
                x: {
                    display: true,
                    type: 'time',
                    time: {
                        tooltipFormat: 'yyyy/MM/dd',
                        displayFormats: {
                            'day': 'dd MMM yyyy',
                            'week': 'mmm dd',
                            'month': 'MMM yyyy',
                            'quarter': 'mmm dd',
                            'year': 'mmm dd',
                        },
                        unit: 'month',
                        ticks: {
                            source: 'data'
                        }
                    },
                    ticks: {
                        font: {

                        }
                    }
                },
                y: {
                    offset: true,  //위아래 간격
                    reverse: true,
                    min: 1,
                    ticks: {
                        // stepSize: 5,
                        maxTicksLimit: 3,
                        precision: 0,
                        font: {

                        }
                    },
                    grid: {
                        // drawTicks: true,
                        color: "rgb(233,233,233)",
                        lineWidth: 0.2
                    }
                }
            }
        }
    });
    resizeChart(chart);
    chart.update();
}

function getLabels( num ) {
    let currentDate = new Date(2024, 1, 1);
    let labels = [];
    for (let i = 0; i < num; i++) {
        let year = currentDate.getFullYear();
        let month = currentDate.getMonth();
        if(i === 0) {
            let lastDay = new Date(year, month + 1, 0);
            labels.push(year + '-' + String( month + 1).padStart(2, '0') + '-' + lastDay.getDate());
        }
        labels.push(year + '-' + String( month + 1).padStart(2, '0') + '-01');
        currentDate.setMonth(currentDate.getMonth() - 1);
    }
    labels.reverse();
    return labels;
}

function getDataList( labels, resultList ) {
    let dataList = [];
    let currentDate = new Date();
    let lastDate = new Date(labels[0]);

    for( let i = 0; i < resultList.length; i++ ) {

        let gameDate = new Date(resultList[0].gameDttm);
        if (gameDate <= currentDate && gameDate >= lastDate) {
            let object = {
                x: resultList[i].gameDttm,
                y: resultList[i].totalRank
            }
            dataList.push(object);
        }
    }
    return dataList;
}
function updateChartByMonth( chart, resultList, num ) {
    let labels = getLabels( num );
    let dataList = getDataList( labels, resultList );

    chart.data.labels = labels;
    chart.data.datasets[0].data = dataList;
    resizeChart(chart);
    chart.update();
}
function resizeChart(chart) {
    const width = window.innerWidth;
    // const width = chart.width;
    if( width < 481 ) {
        chart.options.plugins.tooltip.bodyFont.size = 9;
        chart.options.plugins.tooltip.titleFont.size = 9;

        //label
        chart.options.plugins.legend.labels.font.size = 9;

        //x축
        chart.options.scales.x.ticks.font.size = 9;

        //y축
        chart.options.scales.y.ticks.font.size = 9;
    }
    else if( width < 769 ) {
        //tooltip
        chart.options.plugins.tooltip.bodyFont.size = 10;
        chart.options.plugins.tooltip.titleFont.size = 10;

        //label
        chart.options.plugins.legend.labels.font.size = 10;

        //x축
        chart.options.scales.x.ticks.font.size = 10;

        //y축
        chart.options.scales.y.ticks.font.size = 10;
    }
    else if( width < 1024 ) {
        console.log(width);
    }
    else if( width < 1441 ) {
        //tooltip
        chart.options.plugins.tooltip.bodyFont.size = 15;
        chart.options.plugins.tooltip.titleFont.size = 15;

        //label
        chart.options.plugins.legend.labels.font.size = 15;

        //x축
        chart.options.scales.x.ticks.font.size = 10;

        //y축
        chart.options.scales.y.ticks.font.size = 10;
    }
    else {
        //tooltip
        chart.options.plugins.tooltip.bodyFont.size = 20;
        chart.options.plugins.tooltip.titleFont.size = 20;

        //label
        chart.options.plugins.legend.labels.font.size = 20;

        //x축
        chart.options.scales.x.ticks.font.size = 15;

        //y축
        chart.options.scales.y.ticks.font.size = 15;
    }
}
// chart 관련 js //