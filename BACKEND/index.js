const path = require('path');
const jwt = require('jsonwebtoken');
const cors = require('cors');

var express = require('express');
var app = express();
var bodyParser = require('body-parser');

// Variables d'environnement
const mode = process.env.NODE_ENV || "Development";
const PORT = process.env.PORT || 3000;

// Routes
const userRoutes = require("./routes/user.route");

// ExpressJS
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static(__dirname + '/public'));
app.use(cors());
app.use(bodyParser.json());
app.use("/api/user", userRoutes);

app.get('/', function (req, res) {
  res.sendFile(path.join(__dirname + '/public/index.html'));
});

app.listen(PORT, '0.0.0.0', function loadserver() {
  console.log('Mode: ' + mode);
  console.log(`Launching the app ${process.env.APP_NAME}`);
  console.log('Listening on port: ' + PORT);
  console.log('Connecting to ' + mode + ' database...');
});