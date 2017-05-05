var data = [
{ t: '2017-05-05 19:28:54', w: 0, m: 0},
{ t: '2017-05-05 19:28:55', w: 0, m: 0},
{ t: '2017-05-05 19:28:56', w: 0, m: 0},
{ t: '2017-05-05 19:28:57', w: 0, m: 0},
{ t: '2017-05-05 19:28:58', w: 0, m: 0},
{ t: '2017-05-05 19:28:59', w: 0, m: 0},
{ t: '2017-05-05 19:29:00', w: 0, m: 0},
{ t: '2017-05-05 19:29:01', w: 0, m: 0},
{ t: '2017-05-05 19:29:02', w: 5, m: 5},
{ t: '2017-05-05 19:29:03', w: 5, m: 5},
{ t: '2017-05-05 19:29:05', w: 0, m: 30},
{ t: '2017-05-05 19:29:06', w: 0, m: 30},
{ t: '2017-05-05 19:29:07', w: 0, m: 30},
{ t: '2017-05-05 19:29:08', w: 5, m: 30},
{ t: '2017-05-05 19:29:09', w: 5, m: 30},
{ t: '2017-05-05 19:29:10', w: 10, m: 30},
{ t: '2017-05-05 19:29:11', w: 10, m: 30},
{ t: '2017-05-05 19:29:12', w: 10, m: 30},
{ t: '2017-05-05 19:29:13', w: 10, m: 30},
{ t: '2017-05-05 19:29:14', w: 15, m: 30},
{ t: '2017-05-05 19:29:15', w: 15, m: 30},
{ t: '2017-05-05 19:29:16', w: 30, m: 30}
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
