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
          return res.status(200).json({
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
          return res.status(401).json({
            success: false,
            message: "Auth failed!",
            data: {},
          });
        }
      });
    })
    .catch((e) => {
      console.log(e);
      return res.status(500).json({
        success: false,
        message: err,
      });
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
        return res.status(200).json({
          success: true,
          message: "Logout successful!",
        });
      } else {
        return res.status(500).json({
          success: false,
          message: "Error logout user",
        });
      }
    })
    .catch((e) => {
      console.log(e);
      return res.status(500).json({
        success: false,
        message: err,
      });
    });
});

// SIGNUP
router.post("/signup", (req, res, next) => {
  bcrypt.hash(req.body.password, 10).then((hash) => {
    const user = new User({
      email: req.body.email,
      firstname: req.body.firstname,
      lastname: req.body.lastname,
      password: hash,
      authToken: null,
    });

    User.findOne({ email: req.body.email })
      .then((user1) => {
        if (user1) {
          return res.status(401).json({
            success: false,
            message: "User Already Exist",
          });
        }

        user.save().then((result) => {
          if (!result) {
            return res.status(400).json({
              success: false,
              message: "Error Creating User",
            });
          }

          return res.status(201).json({
            success: true,
            message: "User created",
            data: {
              _id: result._id,
              email: result.email,
              firstname: result.firstname,
              lastname: result.lastname,
            },
          });
        });
      })
      .catch((err) => {
        console.log(err);
        return res.status(500).json({
          success: false,
          message: err,
        });
      });
  });
});

module.exports = router;
