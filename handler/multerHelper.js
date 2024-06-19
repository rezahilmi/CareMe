const multer = require('multer');

const errorHandling = (err, req, res, next) => {
    if (err instanceof multer.MulterError) {
        return res.status(400).json({
            status: 'failed',
            message: 'Wrong field name',
        });
    }
    next();
};

module.exports = { errorHandling }
