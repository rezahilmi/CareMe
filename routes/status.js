const express = require('express');
const router = express.Router();

const timeLog = (req, res, next) => {
    console.log('Time: ', Date.now().toLocaleString());
    next();
};
router.use(timeLog);

router.get('/status', (req, res) => {
    res.send({
        status: 'Ok'
    });
});

module.exports = router;