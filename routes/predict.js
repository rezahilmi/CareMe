const express = require('express');
const router = express.Router();

const { predict } = require('../handler/predict');
const { verifyToken } = require('../handler/token');

router.post('/predict', verifyToken, predict);

module.exports = router