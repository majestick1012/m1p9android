const mongoose = require('mongoose');

const User = mongoose.model('users', {
  email: {
    type: String,
    required: [true, "Email is required"],
    unique: true
  },
  firstname: {
    type: String,
    required: [true, "Firstname is required"]
  },
  lastname: {
    type: String,
    required: [true, "Lastname is required"]
  },
  password: {
    type: String,
    required: [true, "Password is required"]
  },
  authToken: {
    type: String
  }
});

module.exports = User