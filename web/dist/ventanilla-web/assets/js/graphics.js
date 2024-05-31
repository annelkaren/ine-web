export const printDoughnutChart = function (w, x) {
  if ($("#doughnutChart").length) {
    var doughnutChartCanvas = $("#doughnutChart").get(0).getContext("2d");
    var doughnutPieData = {
      datasets: [{
        data: [w, x],
        backgroundColor: [
          "#52CDFF",
          "#1F3BB3",
          
        ],
        borderColor: [
          "#52CDFF",
          "#1F3BB3",
        ],
      }],

      // These labels appear in the legend and in the tooltips when hovering different arcs
      labels: [
        'Faltantes',
        'Terminadas'
      ]
    };
    var doughnutPieOptions = {
      cutoutPercentage: 50,
      animationEasing: "easeOutBounce",
      animateRotate: true,
      animateScale: false,
      responsive: true,
      maintainAspectRatio: true,
      showScale: true,
      legend: false,
      legendCallback: function (chart) {
        var text = [];
        text.push('<div class="chartjs-legend"><ul class="justify-content-center">');
        for (var i = 0; i < chart.data.datasets[0].data.length; i++) {
          text.push('<li><span style="background-color:' + chart.data.datasets[0].backgroundColor[i] + '">');
          text.push('</span>');
          if (chart.data.labels[i]) {
            text.push(chart.data.labels[i]);
          }
          text.push('</li>');
        }
        text.push('</div></ul>');
        return text.join("");
      },

      layout: {
        padding: {
          left: 0,
          right: 0,
          top: 0,
          bottom: 0
        }
      },
      tooltips: {
        callbacks: {
          title: function (tooltipItem, data) {
            return data['labels'][tooltipItem[0]['index']];
          },
          label: function (tooltipItem, data) {
            return data['datasets'][0]['data'][tooltipItem['index']];
          }
        },

        backgroundColor: '#fff',
        titleFontSize: 14,
        titleFontColor: '#0B0F32',
        bodyFontColor: '#737F8B',
        bodyFontSize: 14,
        displayColors: false
      }
    };
    var doughnutChart = new Chart(doughnutChartCanvas, {
      type: 'doughnut',
      data: doughnutPieData,
      options: doughnutPieOptions
    });
    document.getElementById('doughnut-chart-legend').innerHTML = doughnutChart.generateLegend();
  }
}

export const printLeaveReportChart = function (q, r, s, t, u, v, w, x, y, z, a, b) {
  if ($("#leaveReport").length) {
    var leaveReportChart = document.getElementById("leaveReport").getContext('2d');
    var leaveReportData = {
      labels: ["("+q+") PAN", "("+r+") PRI", "("+s+") PRD", "("+t+") PT", "("+u+") PVEM", "("+v+") MC", "("+w+") PSI", "("+x+") Morena", "("+y+") Alianza", "("+z+") FM", "("+a+") Coalisión 1", "("+b+") Coalición 2"],
      datasets: [{
        label: 'Votos',
        data: [q, r, s, t, u, v, w, x, y, z, a, b],
        backgroundColor: "#52CDFF",
        borderColor: [
          '#52CDFF',
        ],
        borderWidth: 0,
        fill: true, // 3: no fill

      }]
    };

    var leaveReportOptions = {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        yAxes: [{
          gridLines: {
            display: true,
            drawBorder: false,
            color: "rgba(255,255,255,.05)",
            zeroLineColor: "rgba(255,255,255,.05)",
          },
          ticks: {
            beginAtZero: true,
            autoSkip: true,
            maxTicksLimit: 5,
            fontSize: 12,
            color: "#6B778C"
          }
        }],
        xAxes: [{
          barPercentage: 0.5,
          gridLines: {
            display: false,
            drawBorder: false,
          },
          ticks: {
            beginAtZero: false,
            autoSkip: false,
            maxTicksLimit: 7,
            fontSize: 12,
            color: "#6B778C"
          }
        }],
      },
      legend: false,

      elements: {
        line: {
          tension: 0.4,
        }
      },
      tooltips: {
        backgroundColor: 'rgba(31, 59, 179, 1)',
      }
    }
    var leaveReport = new Chart(leaveReportChart, {
      type: 'bar',
      data: leaveReportData,
      options: leaveReportOptions
    });
  }
}

export const printPieChart = function (q, r, s, t, u, v, w, x, y, z, a, b) {
  if ($("#pieChart").length) {
    var doughnutPieData = {
      datasets: [{
        data: [q, r, s, t, u, v, w,x,y,z,a,b],
        backgroundColor: [
          'rgba(54, 162, 235, 0.5)',
          'rgba(254, 0, 0, 0.5)',
          'rgba(255, 206, 86, 0.5)',
          'rgba(255, 87, 51, 0.5)',
          'rgba(80, 183, 71, 0.5)',
          'rgba(255, 159, 64, 0.5)',
          'rgba(153, 102, 255, 0.5)',
          'rgba(88, 24, 69, 0.5)',
          'rgba(0, 164, 172, 0.5)',
          'rgba(236,98,160, 0.5)',
          'rgba(153,153,153, 0.5)',
          'rgba(7,55,99, 0.5)'
        ],
        borderColor: [
          'rgba(54, 162, 235, 1)',
          '#FE0000',
          'rgba(255, 206, 86, 1)',
          'rgba(255, 87, 51, 1)',
          '#50B747',
          'rgba(255, 159, 64, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(88, 24, 69, 1)',
          '#00A4AC',
          '#EC62A0',
          '#999999',
          '#073763'
        ],
      }],
  
      // These labels appear in the legend and in the tooltips when hovering different arcs
      labels: ["("+q+") PAN", "("+r+") PRI", "("+s+") PRD", "("+t+") PT", "("+u+") PVEM", "("+v+") MC", "("+w+") PSI", "("+x+") Morena", "("+y+") Alianza", "("+z+") FM", "("+a+") Coalisión 1", "("+b+") Coalición 2"],
    };
    var doughnutPieOptions = {
      responsive: true,
      animation: {
        animateScale: true,
        animateRotate: true
      }
    };
    var pieChartCanvas = $("#pieChart").get(0).getContext("2d");
    var pieChart = new Chart(pieChartCanvas, {
      type: 'pie',
      data: doughnutPieData,
      options: doughnutPieOptions
    });
  }
}