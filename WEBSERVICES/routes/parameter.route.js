const express = require("express");
const Parameter = require("../models/parameter.model");

const router = express.Router();

// GET ALL
router.get("/all", (req, res, next) => {
  Parameter.find({})
    .then((result) => {
      if (!result) {
        return res.status(204).json({
          success: true,
          message: "Aucun resultat",
          data: [],
        });
      } else {
        return res.status(200).json({
          success: true,
          message: "Parameters fetched successfully!",
          data: result,
        });
      }
    })
    .catch((err) => {
      if (err.name === "ValidationError") {
        return res.status(400).json({
          success: false,
          message: err.message,
        });
      }
      return res.status(500).json({
        success: false,
        message: err,
      });
    });
});

module.exports = router;
