const express = require('express');
const router = express.Router();

const { verifyToken } = require('../handler/token');
const { getAllHistory, getHistoryById } = require('../handler/history');

router.get('/history', verifyToken, getAllHistory);
router.get('/history/:historyId', verifyToken, getHistoryById);

module.exports = router;