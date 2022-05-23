const mongoose = require('mongoose');

const Activity = mongoose.model('activities', {
  title: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: true
  },
  video: {
    type: String,
    required: true
  },
  image: {
    type: String,
    required: true
  },
  idParameter: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "parameters",
    required: true
  },
});

module.exports = Activity