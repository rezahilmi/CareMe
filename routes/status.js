const express = require('express');
const router = express.Router();

router.get('/', (req, res) => {
    return res.json({
        status: 'success',
        message: 'Wellcome to CareMe API',
        endpoint: {
            register: 'POST /register',
            login: 'POST /login',
            predict: 'POST /predict',
            history: 'GET /history',
            specificHistory: 'GET /history/{historyId}',
        }
    })
});

router.get('/status', (req, res) => {
    return res.send({
        status: 'success',
    });
});

module.exports = router;