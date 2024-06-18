const express = require('express');
const router = express.Router();

const {
    loginGoogle,
    loginGoogleCallback,
} = require('../handler/authGoogle');
const {
    register,
    login,
} = require('../handler/auth');

router.post('/register', register);
router.post('/login', login);

router.get('/login/google', loginGoogle);
router.get('/login/google/callback', loginGoogleCallback);

module.exports = router;
