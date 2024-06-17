const express = require('express');
const oauth2Client = require('../object/oauth');
const bcrypt = require('bcrypt');
const firestore = require('../object/firestore');

const router = express.Router();

const {
    loginGoogle,
    loginGoogleCallback,
    logout,
} = require('../handler/authGoogle');
const {
    register,
    login,
} = require('../handler/auth');

router.post('/register', register);
router.post('/login', login);

router.get('/login/google', loginGoogle);
router.get('/login/google/callback', loginGoogleCallback);
router.get('/logout', logout);

router.get('/', (req, res) => {
    if (req.session.user) {
        res.json({
            result: 'success',
            data: req.session.user,
        });
    } else {
        res.json({
            result: 'success',
            endpointForLogin: '/login',
            endpointForLogout: '/logout'
        });
    }
});

module.exports = router;
