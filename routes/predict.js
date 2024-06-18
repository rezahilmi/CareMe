const express = require('express');
const router = express.Router();

const multer = require('multer');
const upload = multer();

const { predict } = require('../handler/predict');
const { verifyToken } = require('../handler/token');

router.post('/predict', verifyToken, upload.single('image'), predict);

module.exports = router