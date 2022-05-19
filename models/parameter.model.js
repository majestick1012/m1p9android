const mongoose = require('mongoose');

const Parameter = mongoose.model('parameters', {
  difficulty: {
    type: Number,
    required: true,
  }
});

module.exports = Parameter