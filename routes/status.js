const express = require('express');
const router = express.Router();

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

router.get('/status', (req, res) => {
    res.send({
        status: 'Ok'
    });
});

module.exports = router;