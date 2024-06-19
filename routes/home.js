const express = require('express');
const router = express.Router();

const { getInfo } = require('../handler/home');

router.get('/', getInfo);

module.exports = router;