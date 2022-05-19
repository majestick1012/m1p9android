const express = require("express");
const Activity = require("../models/activity.model");

const router = express.Router();

// GET ALL
router.get("/all", (req, res, next) => {
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
});

// GET BY ID
router.get("/:id", (req, res, next) => {
  console.log(req.params.id);
  Activity.findById(req.params.id).then((result) => {
    if (result) {
      return res.status(200).json({
        success: true,
        message: "Getting the activity successfully!",
        data: result
      });
    } else {
      return res.status(204).json({
        success: true,
        message: "Aucun resultat",
        data: {},
      });
    }
  });
});

module.exports = router;
