const express = require("express");
const Activity = require("../models/activity.model");
const guard = require("../middlewares/guard");

const router = express.Router();

// GET ALL
router.get("/all", guard, (req, res, next) => {
  Activity.find({})
    .then((result) => {
      if (!result) {
        return res.status(204).json({
          success: true,
          message: "Aucun resultat",
          data: [],
        });
      } else {
        fetchedParam = result;
        return res.status(200).json({
          success: true,
          message: "Activity fetched successfully!",
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

// GET BY ID
router.get("/:id", guard, (req, res, next) => {
  console.log(req.params.id);
  Activity.findById(req.params.id)
    .then((result) => {
      if (result) {
        return res.status(200).json({
          success: true,
          message: "Getting the activity successfully!",
          data: result,
        });
      } else {
        return res.status(204).json({
          success: true,
          message: "Aucun resultat",
          data: {},
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
