const express = require("express");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const User = require("../models/user.model");

const router = express.Router();

// LOGIN
router.post("/login", (req, res, next) => {
  let fetchedUser;

  User.findOne({ email: req.body.email })
    .then((user) => {
      if (!user) {
        return res.status(401).json({
          success: false,
          message: "Auth failed! No such user",
          data: {},
        });
      }

      fetchedUser = user;
      return bcrypt.compare(req.body.password, user.password);
    })
    .then((result) => {
      if (!result) {
        return res.status(401).json({
          success: false,
          message: "Auth failed! Incorrect password",
          data: {},
        });
      }

      // CREATING THE JSON WEBTOKEN WITH SIGNATURE AND KEY
      const token = jwt.sign(
        { email: fetchedUser.email, userId: fetchedUser._id },
        process.env.SECRET_BASE,
        { expiresIn: "8h" }
      );

      // SAVE TOKEN IN DB
      fetchedUser.authToken = token;
      User.updateOne(
        { _id: fetchedUser._id },
        {
          $set: {
            authToken: token,
          },
        }
      ).then((result) => {
        if (result) {
          res.status(200).json({
            expiresIn: 28800,
            success: true,
            message: "Auth success!",
            data: {
              _id: fetchedUser._id,
              email: fetchedUser.email,
              firstname: fetchedUser.firstname,
              lastname: fetchedUser.lastname,
            },
          });
        } else {
          res.status(401).json({
            success: false,
            message: "Auth failed!",
            data: {},
          });
        }
      });
    })
    .catch((e) => {
      console.log(e);
    });
});

// LOGOUT
router.post("/logout", (req, res, next) => {
  User.updateOne(
    { _id: req.body.id },
    {
      $set: {
        authToken: null,
      },
    }
  )
    .then((result) => {
      if (result) {
        res.status(200).json({ message: "Logout successful!" });
      } else {
        res.status(500).json({ message: "Error logout user" });
      }
    })
    .catch((e) => {
      console.log(e);
    });
});

module.exports = router;
