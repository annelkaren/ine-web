export const printDoughnutChart = function (w, x, y, z) {
    if ($("#doughnutChart").length) {
        var doughnutChartCanvas = $("#doughnutChart").get(0).getContext("2d");
        var doughnutPieData = {
          datasets: [{
            data: [w, x, y, z],
            backgroundColor: [
              "#F95F53",
              "#fff2d6",
              "#52CDFF",
              "#81DADA"
            ],
            borderColor: [
              "#F95F53",
              "#fff2d6",
              "#52CDFF",
              "#81DADA"
            ],
          }],
    
          // These labels appear in the legend and in the tooltips when hovering different arcs
          labels: [
            'No iniciado',
            'Captura 1',
            'Captura 2',
            'Validado',
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
              title: function(tooltipItem, data) {
                return data['labels'][tooltipItem[0]['index']];
              },
              label: function(tooltipItem, data) {
                return data['datasets'][0]['data'][tooltipItem['index']];
              }
            },
              
            backgroundColor: '#fff',
            titleFontSize: 14,
            titleFontColor: '#0B0F32',
            bodyFontColor: '#737F8B',
            bodyFontSize: 11,
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

  export const printLeaveReportChart = function (u, v, w, x, y, z) {
    if ($("#leaveReport").length) {
      var leaveReportChart = document.getElementById("leaveReport").getContext('2d');
      var leaveReportData = {
          labels: ["PAN","PRI", "PRD", "Morena", "Alianza", "CI"],
          datasets: [{
              label: 'Last week',
              data: [u, v, w, x, y, z],
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
                      color:"rgba(255,255,255,.05)",
                      zeroLineColor: "rgba(255,255,255,.05)",
                  },
                  ticks: {
                    beginAtZero: true,
                    autoSkip: true,
                    maxTicksLimit: 5,
                    fontSize: 10,
                    color:"#6B778C"
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
                  autoSkip: true,
                  maxTicksLimit: 7,
                  fontSize: 10,
                  color:"#6B778C"
                }
            }],
          },
          legend:false,
          
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