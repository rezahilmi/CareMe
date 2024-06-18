const jwt = require('jsonwebtoken');

const verifyToken = (req, res, next) => {
    const authHeader = req.headers.authorization;
    if (!authHeader) {
        return res.status(401).json({
            status: 'failed',
            message: 'No token provided',
        });
    }
    const [type, token] = authHeader.split(' ');
    if (type != 'Bearer') {
        return res.status(401).json({
            status: 'failed',
            message: 'Needs a Bearer type token',
        });
    }

    jwt.verify(token, process.env.JWT_SECRET, (err, decodedUser) => {
        if (err) {
            if (err.name == 'TokenExpiredError') {
                return res.status(403).json({
                    status: 'failed',
                    message: 'Token expired please login again',
                });
            }
            return res.status(403).json({
                status: 'failed',
                message: 'Authentication token failed',
            });
        }

        req.user = decodedUser;
        // console.log('USER DI JWT', req.user);
        next();
    });
}

const createToken = (payload) => {
    const { name, email, password } = payload;
    const token = jwt.sign({
        name,
        email,
        password,
    }, process.env.JWT_SECRET, { expiresIn: '1d' });
    return token;
}

module.exports = {
    verifyToken,
    createToken,
}