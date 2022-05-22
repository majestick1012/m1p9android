const jwt = require('jsonwebtoken');

module.exports = (req, res, next) => {
  try {
    const token = req.headers.authorization.split(" ")[1];
    const decodedToken = jwt.verify(token, process.env.SECRET_BASE);
    req.userData = {
      username: decodedToken.username,
      userId: decodedToken.userId
    };
    next();
  } catch (error) {
    res.status(403).json({
      success: false, 
      message: "Authentication information is missing or invalid" 
    });
  }
}