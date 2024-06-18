const express = require('express');
const { predict } = require('../handler/predict');
const router = express.Router();

router.get('/', (req, res) => {
    return res.json({
        status: 'success',
        message: 'Wellcome to CareMe API',
        endpoint: {
            register: 'POST /register',
            login: 'POST /login',
            predict: 'POST /predict',
        }
    })
});

router.get('/status', (req, res) => {
    return res.send({
        status: 'success'
    });
});

module.exports = router;