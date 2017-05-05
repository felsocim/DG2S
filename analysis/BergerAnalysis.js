var data = [
{ t: '2017-05-05 21:43:56', w: 0, m: 0},
{ t: '2017-05-05 21:43:58', w: 10, m: 5},
{ t: '2017-05-05 21:43:59', w: 10, m: 5},
{ t: '2017-05-05 21:44:00', w: 10, m: 5},
{ t: '2017-05-05 21:44:01', w: 10, m: 5},
{ t: '2017-05-05 21:44:01', w: 10, m: 15}
	],
config = {
      data: data,
      xkey: 't',
      ykeys: ['w', 'm'],
      labels: ['Wood', 'Marble'],
      fillOpacity: 0.6,
      hideHover: 'auto',
      behaveLikeLine: true,
      resize: true,
      pointFillColors:['#ffffff'],
      pointStrokeColors: ['black'],
      lineColors:['gray','red']
  }; config.element = 'line-chart';
new Morris.Line(config);
