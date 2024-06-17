const jwt = require('jsonwebtoken');

const verifyToken = (req, res, next) => {
    // const authHeader = req.headers['authorization'];
    // const token = authHeader && authHeader.split(' ')[1];
    // if (token == null) return res.sendStatus(401);
    // jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, decoded) => {
    //     if (err) return res.sendStatus(403);
    //     req.username = decoded.username;
    //     next();
    // });
    const token = req.headers.authorization;
    if (!token) {
        return res.status(401).json({ message: 'No token provided' });
    }

    jwt.verify(token, process.env.JWT_SECRET, (err, decoded) => {
        if (err) {
            console.error('Failed to authenticate token:', err);
            return res.status(403).json({
                message: 'Failed to authenticate token'
            });
        }

        req.userId = decoded.userId;
        next();
    });
}

module.exports = {
    verifyToken
}