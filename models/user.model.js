const mongoose = require('mongoose');

const User = mongoose.model('users', {
  email: {
    type: String,
    required: true,
    unique: true
  },
  firstname: {
    type: String,
    required: true
  },
  lastname: {
    type: String,
    required: true
  },
  password: {
    type: String,
    required: true
  },
  authToken: {
    type: String
  }
});

module.exports = User