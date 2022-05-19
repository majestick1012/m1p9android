const res = require('express/lib/response');
const mongoose = require('mongoose');
const db = require('./config/db');
const path = require('path');
const jwt = require('jsonwebtoken');
const cors = require('cors');

var express = require('express');
var app = express();
var bodyParser = require('body-parser');

const swaggerUi = require('swagger-ui-express');
const swaggerDocument = require('./swagger.json');

// Variables d'environnement
const mode = process.env.NODE_ENV || "Development";
const PORT = process.env.PORT || 3000;

// Routes
const userRoutes = require("./routes/user.route");
const parameterRoutes = require("./routes/parameter.route");
const activityRoutes = require("./routes/activity.route");

// ExpressJS
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static(__dirname + '/public'));
app.use(cors());
app.use(bodyParser.json());
// API
app.use("/api/user", userRoutes);
app.use("/api/parameter", parameterRoutes);
app.use("/api/activity", activityRoutes);
// Swagger
app.use('/docs', swaggerUi.serve);
app.get('/docs', swaggerUi.setup(swaggerDocument));

app.get('/', function (req, res) {
  res.sendFile(path.join(__dirname + '/public/index.html'));
});

app.listen(PORT, '0.0.0.0', function loadserver() {
  console.log('Mode: ' + mode);
  console.log(`Launching the app ${process.env.APP_NAME}`);
  console.log('Listening on port: ' + PORT);
  console.log('Connecting to ' + mode + ' database...');
});