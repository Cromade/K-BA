const express = require('express');
const logger = require('morgan');
const bodyParser = require('body-parser');
const RouteIndex = require('./routes');

const app = express();

app.use(logger('dev'));
app.use(bodyParser.json());

app.use((req, res, next) => {
  res.set('Access-Control-Allow-Origin', 'http://localhost:4200');
  next();
});

RouteIndex.attach(app);

app.use((err, req, res, next) => {
    if(res.statusCode === 200) { // error handler cannot be return HTTP status 200
        res.status(500);
    }
    res.json({
        message: err.message
    });
});

module.exports = app;
